package network.analyse;

import network.analyse.listener.UDPListener;
import network.analyse.listener.UDPPrintListener;

public abstract class AnalyseUDP implements UDPListener {
	
	protected UDPPrintListener udppl;

	public void ajouterListenerPrintUDP(UDPPrintListener tcppl) {
		this.udppl = tcppl;
	}
	

}
