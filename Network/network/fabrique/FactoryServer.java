package network.fabrique;

import network.networkserver.NetworkServer;
import network.networkserver.NetworkServerTCP;
import network.networkserver.NetworkServerUDP;


class FactoryServer {
	
	public NetworkServer getServer(String nameServer) {
		if (nameServer.equals("UDP")) {
			return new NetworkServerUDP(null);
		}
		if (nameServer.equals("TCP")) {
			return new NetworkServerTCP(null);
		}
		return null;
	}

}
