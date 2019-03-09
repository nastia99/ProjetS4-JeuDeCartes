package network.analyse;

import network.analyse.listener.TCPListener;
import network.analyse.listener.TCPPrintListener;
import network.analyse.observer.ObserverPrint;

public abstract class AnalyseTCP extends ObserverPrint implements TCPListener {
	
	protected TCPPrintListener tcppl;

	protected void ajouterListenerPrintTCP(TCPPrintListener tcppl) {
		this.tcppl = tcppl;
	}


}
