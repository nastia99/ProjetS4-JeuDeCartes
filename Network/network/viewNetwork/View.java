package network.viewnetwork;

import network.networkengine.NetworkController;
import network.tcp.controller.NetworkControllerTCP;
import network.udp.controller.NetworkControllerUDP;


public interface View {
	
	void setController(NetworkControllerUDP networkControllerService, NetworkControllerTCP networkControllerTCP, NetworkController networkController);
	
}
