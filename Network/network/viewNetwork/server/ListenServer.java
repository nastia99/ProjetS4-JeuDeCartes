package network.viewnetwork.server;

import java.util.ArrayList;

import network.networkserver.NetworkServer;
import network.tcp.server.ClientHandler;

public interface ListenServer {
	
	void serverNotification(ArrayList<NetworkServer> listServer, String nbPlayerWait, String nbPlayerConnected,
			ArrayList<ClientHandler> wait, ArrayList<ClientHandler> connected);


}
