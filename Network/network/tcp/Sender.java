package network.tcp;

import java.net.Socket;

public abstract class Sender {
	
	private Socket socketClient;
	private HandleMessage hm;
	
	protected Sender(Socket socketClient) {
		this.socketClient = socketClient;
		this.hm = new HandleMessage(this);
	}
	
	public void sendMessageTCP(String message) {
		addBufferMessage(message);
	}
	
	protected void addBufferMessage(String message) {
		getHm().addBuffer(message);
	}
	
	public Socket getSocket() {
		return socketClient;
	}
	protected void setSocketClient(Socket socketClient) {
		this.socketClient = socketClient;
	}

	public HandleMessage getHm() {
		return hm;
	}

	public void setHm(HandleMessage hm) {
		this.hm = hm;
	}
	
}
