package network.udp;

public interface  Multicast extends Runnable {

	void run();

	void stopService();
	boolean getRunning();

	
}
