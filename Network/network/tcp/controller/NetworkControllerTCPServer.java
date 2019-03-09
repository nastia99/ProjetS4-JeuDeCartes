package network.tcp.controller;


import java.util.logging.Logger;
import network.listenercore.IGameClientAction;
import network.networkengine.NetworkControllerServer;
import network.networkserver.NetworkServer;
import network.networkserver.NetworkServerTCP;
import network.tcp.server.ThreadPoolServerTCP;
import network.utilitaire.IpMachine;
import network.viewnetwork.server.NetworkServerView;


public class NetworkControllerTCPServer implements Runnable, NetworkControllerTCP{
	
	private NetworkServer ns;
	private String ipAddress = new IpMachine().getIpMachine();
	private NetworkControllerServer ncs;
	private static Logger logger = Logger.getLogger(NetworkControllerTCPServer.class.toString());
	

	public NetworkControllerTCPServer(NetworkControllerServer ncs) {
		this.ncs = ncs;
		this.init();
		ns = new NetworkServerTCP(this);
	}
	
	public boolean networkServerTCPisRunning() {
		return ns.isRunning();
	}
	
	public NetworkServer getNetworkServer() {
		return ns;
	}
	
	private void init(){
		this.startNetworkServer();
	}
	
	public void run() {
		// Do nothing Not implemented
		throw new UnsupportedOperationException();
	}
	
	public NetworkServerView getViewServer() {
		return ncs.getFacade().getNsv();
	}
	
	private void startNetworkServer(){
		//TODO A voir si a supprimer
	}
	
	public ThreadPoolServerTCP getThreadPool() {
		return ((NetworkServerTCP) ns).getOnlyPool();
	}
	
	public ThreadPoolServerTCP getOnlyThreadPool() {
		return ((NetworkServerTCP) ns).getOnlyPool();
	}
	
	public void shutdownNetworkServer(){
		((NetworkServerTCP) ns).stopNetworkServer();
	}
	
	public void startListenTCP(int port, IGameClientAction igca) {
		((NetworkServerTCP) ns).createThreadTCP(port,igca);
	}
	
	public void stopListenTCP() {
		((NetworkServerTCP) ns).stopThreadTCP();
	}

	
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}


	@Override
	public String getPortLocal() {
		return ns.getPortListen();
	}

	public void sendMessageTCP(int port,String message) {
		((NetworkServerTCP) ns).getOnlyPool().getAllClient(port).sendMessageTCP(message);
	}

	public void notifyLostConnection() {
		ncs.getFacade().lostConnextion();
		
	}


}
