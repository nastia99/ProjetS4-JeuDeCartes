package network.networkengine;

import network.tcp.controller.NetworkControllerTCP;
import network.udp.controller.NetworkControllerUDP;
import network.viewnetwork.View;

public interface Controller {
	
	void createNetwork();
	NetworkControllerUDP getControllerUDP();
	NetworkControllerTCP getControllerTCP();
	void sendMulticast(String message);
	boolean getRunningTCP();
	View getView();


}
