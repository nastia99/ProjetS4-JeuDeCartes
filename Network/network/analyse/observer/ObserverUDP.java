package network.analyse.observer;

import network.analyse.listener.UDPListener;

public class ObserverUDP {
	
	protected UDPListener udpl;

	public void ajouterListenerUDP(UDPListener udpl) {
		this.udpl = udpl;
	}
	
}
