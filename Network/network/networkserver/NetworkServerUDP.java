package network.networkserver;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import network.analyse.AnalyseUDP;
import network.fabrique.FactorySevice;
import network.udp.Multicast;
import network.udp.MulticastReceiver;


public class NetworkServerUDP implements NetworkServer {
	
		private FactorySevice fse = new FactorySevice();
		private boolean running;
		private int mcastPort = 7777;
		private InetAddress mcastAddr;
		private Thread socketUDP;
		private HashMap<String,Multicast> multicastEetR = new HashMap<>();
		private final ExecutorService workers = Executors.newCachedThreadPool();
		private static final String LISTEN = "Listen";  
		private AnalyseUDP audp;
		private String nameService = "UDP";
		private static Logger logger = Logger.getLogger(NetworkServerUDP.class.toString());
		
	public NetworkServerUDP(AnalyseUDP audp) {
		this.audp = audp;
		this.init();
	}
	
	private void init(){
		this.running = true;
		mcastPort = 7777;
	    mcastAddr = null;

	    try {
	      mcastAddr = InetAddress.getByName("224.7.7.7");
	    } catch (UnknownHostException uhe) {
	    	logger.log(Level.SEVERE, "Probleme obtention adresse multicast", uhe);
	    	System.exit(1);
	    }
	   
	    startNetworkServer();
	}
	
	
	public Multicast getReceveir() {
		return multicastEetR.get(LISTEN);
	}

	public Thread getSocketUDP() {
		return socketUDP;
	}


	public void startNetworkServer() {
		 this.running = true;
		multicastEetR.put(LISTEN, fse.getService("MulticastReceptionner",mcastPort,mcastAddr,null));
		((MulticastReceiver) multicastEetR.get(LISTEN)).ajouterListenerUDP(audp);
		workers.submit(multicastEetR.get(LISTEN));
	}
	

	public void sendMessage(String msg) {
		if(this.running) {
			multicastEetR.put("Send",fse.getService("MulticastEnvoyer",mcastPort,mcastAddr,msg));
			workers.submit(multicastEetR.get("Send"));
		}else {
			//Ignore Cannot send message when server UDP is deconnect
		}
				
			
	}
	
	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public String getMAddr() {
		return mcastAddr.toString();
	}
	
	public int getMPort() {
		return mcastPort;
	}

	@Override
	public String getEtat() {
		if(this.getReceveir().getRunning())
		{
			return "Listen";
		} else return "Down";
	}

	@Override
	public String getNameService() {
		return this.nameService;
	}

	@Override
	public String getAdressIp() {
		return this.getMAddr();
	}

	@Override
	public String getPortListen() {
		return ""+this.getMPort();
	}

}
