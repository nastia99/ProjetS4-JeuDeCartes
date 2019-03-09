package network.networkengine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import network.udp.controller.NetworkControllerUDP;


public abstract class NetworkController implements Controller {
	
	protected NetworkControllerUDP ncudp = null;
	protected final ExecutorService workers = Executors.newCachedThreadPool();

	public void downUDP() {
		ncudp.NetworkServerDown();
	}
	
	public void sendMulticast(String message) {
		ncudp.sendMessage(message);
	}
	
	public abstract void startUDP();
	
	public NetworkControllerUDP getControllerUDP() {
		return this.ncudp;
	}
}
