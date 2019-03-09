package network.fabrique;

import java.net.InetAddress;

import network.udp.Multicast;
import network.udp.MulticastPublisher;
import network.udp.MulticastReceiver;

public class FactorySevice {
	
	public Multicast getService(String nameService, int port, InetAddress addr, String msg) {
		if (nameService.equals("MulticastReceptionner")) {
			return new MulticastReceiver(port, addr);
		}
		if (nameService.equals("MulticastEnvoyer")) {
			return new MulticastPublisher(port, addr,msg);
		}
		return null;
	}

}
