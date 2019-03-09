package network.networkengine;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.facade.server.FacadeServer;
import network.networkserver.NetworkServer;
import network.networkserver.NetworkServerTCP;
import network.tcp.controller.NetworkControllerTCP;
import network.tcp.controller.NetworkControllerTCPServer;
import network.tcp.server.ClientHandler;
import network.udp.controller.NetworkControllerUDP;
import network.utilitaire.IpMachine;
import network.viewnetwork.View;



public class NetworkControllerServer extends NetworkController implements Runnable {
	
	private String ipAddress = null;
	private boolean running = true;
	private Thread process = null;
	private NetworkControllerTCPServer nctcp = null;
	private FacadeServer fs;
	private static Logger logger = Logger.getLogger(NetworkControllerServer.class.toString());
	
	@Override
	public void createNetwork() {
		//TODO	
	}
	
	


	public NetworkControllerServer(FacadeServer f) {
		super();
		this.fs = f;
		ipAddress = new IpMachine().getIpMachine();
		this.init();	
	}


	private void init() {
		this.startNetworkController();
	}

	private void startNetworkController() {
		
		synchronized(this) {
			this.nctcp = new NetworkControllerTCPServer(this);
			this.startUDP();
			
			try {
				this.wait(1000);
			} catch (InterruptedException e1) {
			logger.log(Level.SEVERE,"Interruption start network controller",e1);
				Thread.currentThread().interrupt();
			}
			
			workers.submit(this);
			
		}
	}
	
	@Override
	public void run() {
		
		while(running && !Thread.currentThread().isInterrupted()) {
			notificationServer();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				running = false;
				logger.log(Level.SEVERE, "Interruption Network controller server", e);
				Thread.currentThread().interrupt();
			}
		}
		
	}
	
	public void notificationServer() {
		ArrayList<NetworkServer> list = new ArrayList<>();
		list.add(this.getControllerUDP().getNetworkServer());
		list.add(this.nctcp.getNetworkServer() );
		String nbPlayerWait = ""+0;
		String nbPlayerConnected = ""+0;
		ArrayList<ClientHandler> wait = new ArrayList<>();
		ArrayList<ClientHandler> connected = new ArrayList<>();
			if(this.nctcp.getNetworkServer().isRunning()) {
				nbPlayerWait = ""+((NetworkServerTCP) this.nctcp.getNetworkServer()).getOnlyPool().getNbConnected();
				nbPlayerConnected = ""+((NetworkServerTCP) this.nctcp.getNetworkServer()).getOnlyPool().getNbWait();
				connected = ((NetworkServerTCP) this.nctcp.getNetworkServer()).getOnlyPool().getClientsConnectGame();
				wait = ((NetworkServerTCP) this.nctcp.getNetworkServer()).getOnlyPool().getClientWait();
			}
		fs.getNsv().serverNotification(list,nbPlayerWait,nbPlayerConnected,wait,connected);
	}
	
	public int getNbClientConnected() {
		return ((NetworkServerTCP) this.nctcp.getNetworkServer()).getOnlyPool().getNbWait();
	}
	
	public int getNbClientWaiting() {
		return ((NetworkServerTCP) this.nctcp.getNetworkServer()).getOnlyPool().getNbConnected();
	}
	
	//GETTER SETTER
	
	public Thread getProcess() {
		return process;
	}

	public void setProcess(Thread process) {
		this.process = process;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public String getIpAdress() {
		return ipAddress;
	}
	
	@Override
	public NetworkControllerTCP getControllerTCP() {
		return this.nctcp;
	}
	
	public boolean getRunningTCP() {
		return this.nctcp.networkServerTCPisRunning();
	}
	
	public boolean getRunningUDP() {
		return this.ncudp.networkServerUDPisRunning();
	}

	
	public void stopListenTCP() {
		nctcp.stopListenTCP();
		notificationServer();
	}
	
	@Override
	public void startUDP() {
		this.ncudp = new NetworkControllerUDP(this,fs.getAsudp());
		workers.submit(this.ncudp);
		notificationServer();
	}


	public void annoncerDesPartie() {
		// Do nothing Not implemented
		throw new UnsupportedOperationException();
	}


	public FacadeServer getFacade() {
		return this.fs;
	}


	@Override
	public View getView() {
		// TODO Supprimer
		return null;
	}
			
}




