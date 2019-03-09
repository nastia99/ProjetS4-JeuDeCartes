package network.tcp.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.analyse.client.AnalyseClientTCP;
import network.listenercore.IGameServerAction;
import network.tcp.client.ClientTCP;
import network.utilitaire.IpMachine;
import network.viewnetwork.ViewClient;

public class NetworkControllerTCPClient implements Runnable, NetworkControllerTCP{

	private int port;
	private Socket socket;
	private IpMachine addr = new IpMachine();
	private HashMap<String,ClientTCP> service = new HashMap<>();
	private final ExecutorService workers = Executors.newCachedThreadPool();
	private boolean connect =false;
	private boolean waitlatency = false;
	private double latency = -10;
	private AnalyseClientTCP actcp;
	private static Logger logger = Logger.getLogger(NetworkControllerTCPClient.class.toString());
	
	public NetworkControllerTCPClient(String adressIp, int port, ViewClient v,IGameServerAction igsa){
		this.port = port;
		actcp = new AnalyseClientTCP(this,v,igsa);
		this.init(adressIp);
	}
	
	private void init(String adressIp) {
		InetAddress serveur = null;
			if(adressIp.equals("localhost")) {
				
				try {
					serveur = InetAddress.getByName(addr.getIpMachine());
				} catch (UnknownHostException e) {
					logger.log(Level.WARNING, "UnknowHost", e);
				}
			}
			else {
				try {
					serveur = InetAddress.getByName(adressIp);
				} catch (UnknownHostException e) {
					logger.log(Level.WARNING, "UnknowHost", e);
				}
			}
		
			
			try {
				socket = new Socket(serveur, port);
			} catch (IOException e) {
				logger.log(Level.WARNING, "IOException", e);
			}
			this.connect = true;
		
	/*	
		try {
			socket.setSoTimeout(0);
		} catch (SocketException e1) {
			System.err.println("Unable to set acceptor timeout value.");
		}*/
		
		getService().put("Read", new ClientTCP(socket,this,actcp));
		workers.submit(this);
		this.startRead();
		
	}
	
	public void run() {
		if(socket.isConnected()) {
			logger.log(Level.INFO, "Client connected at "+socket.getRemoteSocketAddress()+"on port local "+socket.getLocalPort()+" on port "+socket.getPort());
		}
	}


	private void startRead(){
		workers.submit(getService().get("Read"));

		/* Decommenter pour avoir la latence
			this.latency();
		
		 */
	}
		
	public void deconnexion() {
		workers.submit(
				() -> {
					getService().get("Read").arretReceiver();

					try {
						socket.close();
						logger.log(Level.INFO, "Client deconnexion "+socket.isClosed());
						
						connect = false;
					} catch (IOException e1) {
						logger.log(Level.WARNING, "Erreur lors de la deconnection de la socket Client TCP",e1);
					}

					workers.shutdown();
					workers.shutdownNow();
				}
		);
	}
	

	
	public void sendMessageTCP(String message) {
		getService().get("Read").sendMessageTCP(message);
	}
	
	public Socket getSocket() {
		return this.socket;
	}

	public boolean isConnectToServer() {
		return this.connect;
	}

	public String getNameServer() {
		return socket.getInetAddress().toString();
	}

	public double getLatency(){
		return latency;
	}
	
	private void latency(){
		workers.submit(
				() -> {
					while(connect) {
						double before = System.currentTimeMillis();
						sendMessageTCP("GET_LATENCY");
						while(!getService().get("Read").getWaitLatency()) {
							//To get Latency (time of this loop)
						}
						double after = System.currentTimeMillis();
						latency = after - before;
						getService().get("Read").setWaitLatency(false);
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							logger.log(Level.WARNING, "Interruption thread recuperation Latence",e);
							Thread.currentThread().interrupt();
						}
					}
					latency = -1;
				}
		);
	}

	public HashMap<String,ClientTCP> getService() {
		return service;
	}

	public void setService(HashMap<String,ClientTCP> service) {
		this.service = service;
	}

	public AnalyseClientTCP getAnalyse() {
		return actcp;
	}

	public boolean isWaitlatency() {
		return waitlatency;
	}

	public void setWaitlatency(boolean waitlatency) {
		this.waitlatency = waitlatency;
	}

	@Override
	public String getPortLocal() {
		return ""+socket.getLocalPort();
	}

	@Override
	public boolean networkServerTCPisRunning() {
		return this.connect;
	}
	
	

}
	

