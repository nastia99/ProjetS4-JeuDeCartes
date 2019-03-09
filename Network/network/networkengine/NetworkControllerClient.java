package network.networkengine;

import java.util.logging.Level;
import java.util.logging.Logger;
import network.facade.client.FacadeClient;
import network.listenercore.IGameServerAction;
import network.tcp.controller.NetworkControllerTCP;
import network.tcp.controller.NetworkControllerTCPClient;
import network.udp.controller.NetworkControllerUDP;
import network.utilitaire.IpMachine;
import network.viewnetwork.ViewClient;

public class NetworkControllerClient extends NetworkController {
	
	private String ipAddress = null;
	private NetworkControllerTCPClient client = null;
	private ViewClient v;
	private FacadeClient fc;
	private static Logger logger = Logger.getLogger(NetworkControllerClient.class.toString());
	
	@Override
	public void createNetwork() {
		// TODO Auto-generated method stub
		
	}
	
	public NetworkControllerClient(FacadeClient fc) {
		super();
		this.fc = fc;
		setIpAddress(new IpMachine().getIpMachine());
		this.init();	
	}
	
	private void init() {
		this.startNetworkController();
	}
	
	public FacadeClient getFacade() {
		return fc;
	}

	private void startNetworkController() {
		synchronized(this) {
			v = new ViewClient(this.ncudp,this.client,this);
			workers.submit(v);
			try {
				this.wait();
			} catch (InterruptedException e) {
				logger.log(Level.SEVERE, "Interruption startNetwork", e);
				Thread.currentThread().interrupt();
			}
			
			this.ncudp = new NetworkControllerUDP(this,fc.getAcusp());
			v.setController(this.ncudp, this.client,this);
			downUDP();
			this.notifyAll();
		}
	}
	

	@Override
	public void startUDP() {
		this.ncudp = new NetworkControllerUDP(this,fc.getAcusp());
		v.setController(this.ncudp, this.client,this);
		workers.submit(this.ncudp);
	}
	
	@Override
	public void sendMulticast(String message) {
		ncudp.sendMessage(message);
	}
	
	public void sendTCP(String message) {
		client.sendMessageTCP(message);
	}
	
	public void connectTCPClient(String addr, int port,IGameServerAction igsa) {
		client = new NetworkControllerTCPClient(addr,port,v,igsa);
		v.setController(this.ncudp, this.client,this);
	}
	
	public void deconnectTCPClient(){
		client.deconnexion(); 
	}

	@Override
	public NetworkControllerTCP getControllerTCP() {
		return this.client;
	}

	@Override
	public ViewClient getView() {
		return this.v;
	}

	@Override
	public boolean getRunningTCP() {
		if(this.client != null) {
			return this.getControllerTCP().networkServerTCPisRunning();
		}
		else return false;
		
	}
	
	public boolean getRunningUDP() {
		return this.ncudp.networkServerUDPisRunning();
	}

	public String getIpAddress() {
		return ipAddress;
	}

	private void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}






}
