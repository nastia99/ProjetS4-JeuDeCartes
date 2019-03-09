package network.tcp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Logger;
import network.analyse.client.AnalyseClientTCP;
import network.tcp.Sender;
import network.tcp.controller.NetworkControllerTCPClient;

public class ClientTCP extends Sender implements Runnable{
	
	private boolean running = true;
	private int compteurListen = 0;
	private boolean waitLatency = false;
	private NetworkControllerTCPClient ctcp;
	private static Logger logger = Logger.getLogger(ClientTCP.class.toString());

    public ClientTCP(Socket socket, NetworkControllerTCPClient c, AnalyseClientTCP actcp){
    	super(socket);
		getHm().setListener(actcp);
        ctcp = c;
		this.init();
		try {
			socket.setSoTimeout(0);
		} catch (SocketException e1) {
			logger.log(java.util.logging.Level.WARNING,"Unable to set acceptor timeout value.");
		}
	}

	private void init() {
		getHm().sendBuffer();
	}
	
	public void run() {
		BufferedReader userInput = null;
		running = true;
		String data = null;
		try {
			userInput = new BufferedReader(new InputStreamReader(getSocket().getInputStream()));
			while (running) {
		
				while((data = userInput.readLine()) != null) {
					
					getHm().addBufferReceiver(data);
					
					setCompteurListen(getCompteurListen() + 1);
				}
				
					this.running = false;
				
			}
			
			
			ctcp.deconnexion();
			
			
		} catch (IOException ioe) {
			//Ignored
		}
		try {
			if (userInput != null) {
				userInput.close();
				logger.log(java.util.logging.Level.INFO,"Arret Thread Read TCP Client");
			}
		} catch (IOException ioe2) {
			// Ignored
		}
		}
		
	
	
	public void arretReceiver() {
		this.running = false;
		getHm().arretBuffer();
	}
	
	public void startReceiver(Socket s) {
		setSocketClient(s);
	}
	
	public boolean getWaitLatency() {
		return this.waitLatency;
	}
	
	public void setWaitLatency(boolean b) {
		this.waitLatency = b;
	}

	private int getCompteurListen() {
		return compteurListen;
	}

	private void setCompteurListen(int compteurListen) {
		this.compteurListen = compteurListen;
	}

	@Override
	public Socket getSocket() {
		return ctcp.getSocket();
	}
	
	public boolean getRunning() {
		return this.running;
	}

}
	
