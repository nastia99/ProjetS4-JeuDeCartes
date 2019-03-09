package network.analyse.listener;

import java.net.Socket;

public interface TCPPrintListener {
	
	void afficherMessage(String message);
	void afficherMessage(String message, Socket s,boolean sOrR);
	
}
