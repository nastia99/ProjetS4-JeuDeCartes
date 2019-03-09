package network.fabrique;

import network.facade.Facade;
import network.facade.client.FacadeClient;
import network.facade.server.FacadeServer;
import network.networkengine.Controller;
import network.networkengine.NetworkControllerClient;
import network.networkengine.NetworkControllerServer;

public class FactoryController {
	
	public Controller getController(String name, Facade f) {
		if (name.equals("Server")) {
			return new NetworkControllerServer((FacadeServer) f);
		}
		if (name.equals("Client")) {
			return new NetworkControllerClient((FacadeClient) f);
			
		}
		else {
			return null;
		}
		
	}

}
