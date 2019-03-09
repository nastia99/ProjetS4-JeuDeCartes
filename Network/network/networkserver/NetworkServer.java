package network.networkserver;

public interface NetworkServer {
	
	boolean isRunning();
	String getEtat();
	String getNameService();
	String getAdressIp();
	String getPortListen();
}
