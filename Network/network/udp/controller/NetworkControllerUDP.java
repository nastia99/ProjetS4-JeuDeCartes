package network.udp.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import network.analyse.AnalyseUDP;
import network.networkengine.NetworkController;
import network.networkserver.NetworkServer;
import network.networkserver.NetworkServerUDP;
import network.udp.Multicast;


public class NetworkControllerUDP implements Runnable{
	
	private NetworkServer ns;

	private NetworkController nc;
	private static Logger logger = Logger.getLogger(NetworkControllerUDP.class.toString());
	
	public NetworkControllerUDP(NetworkController nc, AnalyseUDP audp) {
		this.setNc(nc);
		ns = new NetworkServerUDP(audp);
	}
	
	public void init(){
		//Not implemented
	}
	
	public NetworkServer getNetworkServer() {
		return ns;
	}
	
	@Override
	public void run() {
			//Not implemented
		}
		
	
	public void NetworkServerDown() {
		if(ns.isRunning()) {
			((NetworkServerUDP) ns).getReceveir().stopService();
			((NetworkServerUDP) ns).setRunning(false);
		} else logger.log(Level.WARNING,"NetworkUDP already disconnect" );
	}
	
	public boolean networkServerUDPisRunning() {
		return ns.isRunning();
	}
	
	public void sendMessage(String message) {
		((NetworkServerUDP) ns).sendMessage(message);	
	}
	
	public String getAddr() {
		return ((NetworkServerUDP) ns).getMAddr();
	}
	
	public int getPort() {
		return ((NetworkServerUDP) ns).getMPort();
	}
	
	public NetworkController getNc() {
		return nc;
	}

	private void setNc(NetworkController nc) {
		this.nc = nc;
	}
}
