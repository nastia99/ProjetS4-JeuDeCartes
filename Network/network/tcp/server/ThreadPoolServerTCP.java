package network.tcp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.analyse.server.AnalyseServeurTCP;
import network.listenercore.IGameClientAction;
import network.networkserver.NetworkServerTCP;

public class ThreadPoolServerTCP implements Runnable{
		
		private HashMap<Integer,ClientHandler> handlerClientsConnected = new HashMap<>();
		private HashMap<Integer,ClientHandler> handlerClientsWaiting = new HashMap<>();
		private ArrayList<ClientHandler> listClientsWait = new ArrayList<>();
		private ArrayList<ClientHandler> listClientsGame = new ArrayList<>();
		private String name = null;
		private ServerSocket listenSocket;
		private boolean keepRunning = true;
		private final ExecutorService workers = Executors.newCachedThreadPool();
		private int port;
		private AnalyseServeurTCP as;
		private NetworkServerTCP nstcp;
		private static Logger logger = Logger.getLogger(ThreadPoolServerTCP.class.toString());

		
		public int getPort() {
			return this.port;
		}

		public HashMap<Integer,ClientHandler> getClientHandle(){
			return handlerClientsConnected;
		}
		
		public ThreadPoolServerTCP(final int port, NetworkServerTCP networkServerTCP, IGameClientAction igca) {
			this.port = port;
			this.nstcp = networkServerTCP;
			as =new AnalyseServeurTCP(this,nstcp.getViewServer(),igca);
			this.init();
		}
		
		private void init() {
			
			if (port <= 0 || port > 65536) {
				logger.log(Level.INFO,"Port value must be in (0, 65535].");
				System.exit(1);
			}
			
			Runtime.getRuntime().addShutdownHook(new Thread(() -> ThreadPoolServerTCP.this.shutdown()));

			try {
				this.setListenSocket(new ServerSocket(port));
			} catch (IOException e) {
				logger.log(Level.WARNING,"An exception occurred while creating the listen socket: "+ e.getMessage(),e);
				
				System.exit(1);
			}		
		}

		@Override
		public void run() {
			// Set a timeout on the accept so we can catch shutdown requests
			/*
			try {
				this.getListenSocket().setSoTimeout(0);
			} catch (SocketException e1) {
				System.err.println("Unable to set acceptor timeout value.  The server may not shutdown gracefully.");
			}
			*/
			logger.log(Level.INFO, "Accepting incoming connections on port " + this.getListenSocket().getLocalPort());
		
			this.name = ""+this.getListenSocket().getLocalPort();
		
			while (this.keepRunning) {
				try {
					final Socket clientSocket = this.getListenSocket().accept();
					if(clientSocket.isConnected()) {
						logger.log(Level.INFO,"Accepted connection from " + clientSocket.getRemoteSocketAddress());
						ClientHandler handler = new ClientHandler(clientSocket, this,as);
						handler.ajouterListenerPrintTCP(this.nstcp.getViewServer());
					
						handlerClientsWaiting.put(clientSocket.getPort(), handler);
						listClientsWait.add(handler);
						this.workers.submit(handler);
					}
				} catch (IOException te) {
					// Ignored, timeouts will happen every 1 second
				} 
			}	
		
		}
		
		public void clearConnection(int port) {
			
			
						ClientHandler chc = handlerClientsConnected.get(port);
						ClientHandler chw = handlerClientsWaiting.get(port);
						
						
						if(chc == null) {
							handlerClientsWaiting.remove(port);
							int i = 0;
							while(i<listClientsWait.size())
							{
								if(listClientsWait.get(i).getPortClient() == chw.getPortClient()) {
								
									listClientsWait.remove(i);
								}
								i++;
							}
						}
						else {
							handlerClientsConnected.remove(port);
							int i =0;
							while(i<listClientsGame.size())
							{
								if(listClientsGame.get(i).getPortClient() == chc.getPortClient()) {
								
									listClientsGame.remove(i);
								}
								i++;
							}
						}
		}
		
		public void stopConnectionClientWainting(int port) {
			ClientHandler ch = handlerClientsWaiting.get(port);
			int i =0;
			while(i<listClientsWait.size())
			{
				if(listClientsWait.get(i).getPortClient() == ch.getPortClient()) {
				
					listClientsWait.remove(i);
				}
				i++;
			}
			ch.stopClient();
			handlerClientsWaiting.remove(port);
		}
		
		public void stopConnectionClientConnected(int port) {
			ClientHandler ch = handlerClientsConnected.get(port);
			int i =0;
			while(i<listClientsGame.size())
			{
				if(listClientsGame.get(i).getPortClient() == ch.getPortClient()) {
				
					listClientsGame.remove(i);
				}
				i++;
			}
			ch.stopClient();
			handlerClientsConnected.remove(port);
		}
		
		public void clearWaitConnection() {
			while(!listClientsWait.isEmpty()) {
				handlerClientsWaiting.remove(listClientsWait.get(0).getPortClient());
				listClientsWait.get(0).stopClient();
				listClientsWait.remove(0);
			}
		}
		
		public void clearConnectedConnection() {
			while(!listClientsGame.isEmpty()) {
				handlerClientsConnected.remove(listClientsGame.get(0).getPortClient());
				listClientsGame.get(0).stopClient();
				listClientsGame.remove(0);
			}
		}

		/**
		 * Shuts down this server.  Since the main server thread will time out every 1 second,
		 * the shutdown process should complete in at most 1 second from the time this method is invoked.
		 */
		public void shutdown() {
						stopAcceptConnection();							
		}
		
		private void stopAcceptConnection() {
			keepRunning = false;
			
			clearConnectedConnection();
			clearWaitConnection();
			
			
			if(!getListenSocket().isClosed()) {
				logger.log(Level.INFO, "Shutting down the server on port "+getListenSocket().getLocalPort());
				
				try {
					getListenSocket().close();
				} catch (IOException e) {
					logger.log(Level.SEVERE, "IOException"+getListenSocket().getLocalPort(),e);
				}
				logger.log(Level.INFO, "Stopped accepting incoming connections on "+getListenSocket().getLocalPort());
				setListenSocket(null);
			}
			
		}
			
		public String getNameThreadPoolTCP() {
			return name;
		}
		
		public ClientHandler getClientConnected(int port) {
			return handlerClientsConnected.get(port);
		}
		
		public ClientHandler getClientWaiting(int port) {
			return handlerClientsWaiting.get(port);
		}

		private ServerSocket getListenSocket() {
			return listenSocket;
		}

		private void setListenSocket(ServerSocket listenSocket) {
			this.listenSocket = listenSocket;
		}

		public ArrayList<ClientHandler> getClientsConnectGame() {
			return listClientsGame;
		}
		
		public void addGameConnection(int portClient) {
			listClientsGame.add(handlerClientsWaiting.get(portClient));
			handlerClientsConnected.put(portClient,	handlerClientsWaiting.get(portClient));
			handlerClientsWaiting.remove(portClient);
			int i = 0;
			while(i<listClientsWait.size()) {
				if(listClientsWait.get(i).getPortClient() == portClient) {
					listClientsWait.remove(i);
				}else {
					i++;
				}
			}
		}
	
		public ClientHandler getAllClient(int portClient) {
			int i = 0;
			while(i<listClientsGame.size() ) {
				if(listClientsGame.get(i).getPortClient() == portClient) {
					return listClientsGame.get(i);
				}
				i++;
			}
			return handlerClientsWaiting.get(portClient);
		}

		public int getNbWait() {
			return listClientsGame.size();
		}

		public int getNbConnected() {
			return listClientsWait.size();
		}
		
		public ArrayList<ClientHandler> getClientWait(){
			return this.listClientsWait;
		}

		public void notifyLostConnection() {
			nstcp.notifyLostConnection();
			
		}

}


