package network.tcp.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.analyse.AnalyseTCP;
import network.analyse.listener.TCPPrintListener;
import network.tcp.Sender;


public class ClientHandler extends Sender implements Runnable {
		
	
		private boolean running = true;
		private ThreadPoolServerTCP server;
		private int compteurListen = 0;
		private TCPPrintListener tcppl;
		private static final Logger LOGGER = Logger.getLogger( ClientHandler.class.getName() );
		
		public ClientHandler(final Socket clientSocket, ThreadPoolServerTCP server,AnalyseTCP atcp) {
			super(clientSocket);
			this.server = server;
			this.getHm().setListener(atcp);
		}
		
		@Override
		public void run() {

			BufferedReader userInput = null;
			DataOutputStream userOutput = null;

			
			getHm().sendBuffer();
			 
			 try {
					getClientSock().setSoTimeout(0);
				} catch (SocketException e1) {
					LOGGER.info("Unable to set acceptor timeout value.");
				}
			
			try {
				userInput = new BufferedReader(new InputStreamReader(this.getClientSock().getInputStream()));
				userOutput = new DataOutputStream(this.getClientSock().getOutputStream());
				
				
				while (running) {
					
					String data = null;
					while((data = userInput.readLine()) != null) {
						getHm().addBufferReceiver(data);
					}
						this.running = false;			
				}
				
				getHm().arretBuffer();
				server.clearConnection(getSocket().getPort());
				LOGGER.info("Serveur deconnexion du Client "+getClientSock().isClosed()+" "+getClientSock().getPort());
				
			} catch (IOException ioe) {
				//IOException
			}
			try {
				if (userInput != null) {
					userInput.close();
				}
				if (userOutput != null) {
					userOutput.close();
				}
				LOGGER.info("Lost connection to " + this.getClientSock().getRemoteSocketAddress());
		
				server.notifyLostConnection();
				
			} catch (IOException ioe2) {
				// Ignored
			}
		}

		public void stopClient() {
						this.running = false;
						try {
							getSocket().close();
						} catch (IOException e) {
							LOGGER.log(Level.SEVERE, "SocketCloseIOException");
						}
		}
		
		public int getPortClient(){
			return getClientSock().getPort();
		}

		public int getCompteurListen() {
			return compteurListen;
		}

		public void setCompteurListen(int compteurListen) {
			this.compteurListen = compteurListen;
		}

		private Socket getClientSock() {
			return getSocket();
		}

		@Override
		public void sendMessageTCP(String message) {
			this.addBufferMessage(message);
			tcppl.afficherMessage(message, this.getSocket(),true);
		}
		
		protected void ajouterListenerPrintTCP(TCPPrintListener tcppl) {
			this.tcppl = tcppl;
		}
		
	}