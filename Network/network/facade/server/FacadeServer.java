package network.facade.server;

import java.util.ArrayList;

import network.analyse.server.AnalyseServerUDP;
import network.analyse.utilitaire.Encoder;
import network.fabrique.FactoryController;
import network.facade.Facade;
import network.listenercore.GameNotification;
import network.listenercore.IGameClientAction;
import network.listenercore.IGameInformation;
import network.networkengine.NetworkControllerServer;
import network.tcp.controller.NetworkControllerTCPServer;
import network.tcp.server.ClientHandler;
import network.utilitaire.IpMachine;
import network.viewnetwork.server.NetworkServerView;

public class FacadeServer implements Facade, GameNotification {
	
	private FactoryController fse = new FactoryController();
	private NetworkControllerServer nc;
	private AnalyseServerUDP asudp;
	private IGameInformation igi;
	private Encoder e = new Encoder();
	private NetworkServerView nsv;
	private IGameClientAction igca;
	
	public FacadeServer(IGameClientAction igca, IGameInformation igi) {
		setNsv(new NetworkServerView(false));  
		this.setAsudp(new AnalyseServerUDP(getNc(),igca,igi));
		asudp.ajouterListenerPrintUDP(this.getNsv());
		setNc((NetworkControllerServer) fse.getController("Server", this));
		this.getAsudp().setNc(getNc());
		this.igca = igca;
	}
	
	@Override
	public void shutdowNetwork() {
		getNc().downUDP();
		stopSearchGame();
		NetworkControllerTCPServer nctcp = (NetworkControllerTCPServer) getNc().getControllerTCP();
		nctcp.shutdownNetworkServer();
		System.exit(0);
	}
	
	@Override
	public int shutdowNetworkTCP() {
		stopSearchGame();
		NetworkControllerTCPServer nctcp = (NetworkControllerTCPServer) getNc().getControllerTCP();
		nctcp.shutdownNetworkServer();
		return 0;
	}
	
	@Override
	public void startNetworkNewGame(int port) {
		NetworkControllerTCPServer nctcp = (NetworkControllerTCPServer) getNc().getControllerTCP();
		nctcp.startListenTCP(port,igca);	
	}
	
	public void stopSearchGame() {
		NetworkControllerTCPServer nctcp = (NetworkControllerTCPServer) getNc().getControllerTCP();
		nctcp.stopListenTCP();
	}

	public AnalyseServerUDP getAsudp() {
		return asudp;
	}


	private void setAsudp(AnalyseServerUDP asudp) {
		this.asudp = asudp;
	}

	public IGameInformation getIgameInformation() {
		return igi;
	}


	public void setIgameInformation(IGameInformation igi) {
		this.igi = igi;
	}

	public NetworkServerView getNsv() {
		return nsv;
	}

	public void setNsv(NetworkServerView nsv) {
		this.nsv = nsv;
	}

	public NetworkControllerServer getNc() {
		return nc;
	}

	public void setNc(NetworkControllerServer nc) {
		this.nc = nc;
	}
	
	public void sendBroadcast(String message) {
		ArrayList<ClientHandler> lh = ((NetworkControllerTCPServer) getNc().getControllerTCP()).getOnlyThreadPool().getClientsConnectGame();
		int i = 0;
		while(i<lh.size()) {
			lh.get(i).sendMessageTCP(message);
			i++;
		}
	}
	

	@Override
	public void acceptPlayer(String connectionID, int playerPort) {
		ArrayList<String> message = new ArrayList<>();
		message.add("AC");
		message.add(connectionID);
		String msg = e.splitTxt(message);
		((NetworkControllerTCPServer) getNc().getControllerTCP()).getOnlyThreadPool().getClientWaiting(playerPort).sendMessageTCP(msg);
		((NetworkControllerTCPServer) getNc().getControllerTCP()).getOnlyThreadPool().addGameConnection(playerPort);
				
			
	}

	@Override
	public void refusePLayer(String connectionID, int playerPort) {
		ArrayList<String> message = new ArrayList<>();
		message.add("RC");
		message.add(connectionID);
		String msg = e.splitTxt(message);
		((NetworkControllerTCPServer) getNc().getControllerTCP()).getOnlyThreadPool().getAllClient(playerPort).sendMessageTCP(msg);
		((NetworkControllerTCPServer) getNc().getControllerTCP()).getOnlyThreadPool().stopConnectionClientWainting(playerPort);
		((NetworkControllerTCPServer) getNc().getControllerTCP()).getOnlyThreadPool().clearConnection(playerPort);
	}

	@Override
	public void initializeGame(String playerList, int gameID) {
		ArrayList<String> message = new ArrayList<>();
		message.add("IP");
		message.add(playerList);
		message.add(""+gameID);
		String msg = e.splitTxt(message);
		sendBroadcast(msg);
	}
	
	
	@Override
	public void initializeRound(String gameDirection, int gameID, int roundID) {
		ArrayList<String> message = new ArrayList<>();
		message.add("IM");
		message.add(gameDirection);
		message.add(""+gameID);
		message.add(""+roundID);
		String msg = e.splitTxt(message);
		sendBroadcast(msg);
	}

	@Override
	public void distributeHand(String playerHand, int gameID, int roundID, int playerPort) {
		ArrayList<String> message = new ArrayList<>();
		message.add("DM");
		message.add(playerHand);
		message.add(""+gameID);
		message.add(""+roundID);
		String msg = e.splitTxt(message);
		ClientHandler c = ((NetworkControllerTCPServer) nc.getControllerTCP()).getOnlyThreadPool().getClientConnected(playerPort);
		c.sendMessageTCP(msg);
	}


	@Override
	public void distributeCard(String playerCard, int gameID, int roundID, int turnID, int playerPort) {
		ArrayList<String> message = new ArrayList<>();
		message.add("FC");
		message.add(playerCard);
		message.add(""+gameID);
		message.add(""+roundID);
		message.add(""+turnID);
		String msg = e.splitTxt(message);
		ClientHandler c = ((NetworkControllerTCPServer) nc.getControllerTCP()).getOnlyThreadPool().getClientConnected(playerPort);
		c.sendMessageTCP(msg);
	}

	@Override
	public void indicateError(String errorCode, int gameID, int roundID, int turnID, int playerPort) {
		ArrayList<String> message = new ArrayList<>();
		message.add("IE");
		message.add(errorCode);
		message.add(""+gameID);
		message.add(""+roundID);
		message.add(""+turnID);
		String msg = e.splitTxt(message);
		ClientHandler c = ((NetworkControllerTCPServer) nc.getControllerTCP()).getOnlyThreadPool().getClientConnected(playerPort);
		c.sendMessageTCP(msg);
	}

	@Override
	public void informPlayerAction(boolean herdPickUp, String cardPlay, boolean reverseDirection, int gameID,
			int roundID, int turnID) {
		System.out.println("HERD PICK UP : "+ herdPickUp+"  reverseDirection :" + reverseDirection );
		
		Encoder e2 = new Encoder("/");
		ArrayList<String> action = new ArrayList<>();
		ArrayList<String> message = new ArrayList<>();
		if(herdPickUp) {
			action.add("TR");
		}
		if (cardPlay != null) {
			action.add(cardPlay);
		}
		if (reverseDirection) {
			action.add("SJI");
		}
		
		String msg2 = e2.splitTxt(action);
		message.add("IAT");
		message.add(msg2);
		message.add(""+gameID);
		message.add(""+roundID);
		message.add(""+turnID);
		String msg = e.splitTxt(message);
		sendBroadcast(msg);
	}

	@Override
	public void endRound(String playersScoreManche, String playersScoreActuel, int gameID, int roundID) {
		ArrayList<String> message = new ArrayList<>();
		message.add("FM");
		message.add(playersScoreManche);
		message.add(playersScoreActuel);
		message.add(""+gameID);
		message.add(""+roundID);
		String msg = e.splitTxt(message);
		sendBroadcast(msg);
	}

	@Override
	public void endGame(String winner, int gameID) {
		ArrayList<String> message = new ArrayList<>();
		message.add("FP");
		message.add(winner);
		message.add(""+gameID);
		String msg = e.splitTxt(message);
		sendBroadcast(msg);
	}

	@Override
	public void endGameSession(int gameID) {
		ArrayList<String> message = new ArrayList<>();
		message.add("TSJ");
		message.add(""+gameID);
		String msg = e.splitTxt(message);
		sendBroadcast(msg);
		((NetworkControllerTCPServer) getNc().getControllerTCP()).getOnlyThreadPool().clearConnectedConnection();
		downNetworkGame();
	}

	@Override
	public void restartGame(int gameID, int newGameID) {
		ArrayList<String> message = new ArrayList<>();
		message.add("RNSJ");
		message.add(""+gameID);
		message.add(""+newGameID);
		String msg = e.splitTxt(message);
		sendBroadcast(msg);
	}
	
	@Override
	public void announceGame(int port, String name, int maxPlayer, int maxRealPlayer, int maxVirtualPlayer,
			int realPlayerNb, int virtualPlayerNb, String status) {
		ArrayList<String> message = new ArrayList<>();
		message.add("AP");
		message.add(new IpMachine().getIpMachine());
		message.add(""+port);
		message.add(name);
		message.add(""+maxPlayer);
		message.add(""+maxRealPlayer);
		message.add(""+maxVirtualPlayer);
		message.add(""+realPlayerNb);
		message.add(""+virtualPlayerNb);
		message.add(status);
		String msg = e.splitTxt(message);
		nc.sendMulticast(msg);
	}
	
	@Override
	public void announceDeconnexion(int gameID, int roundID, int turnID) {
		ArrayList<String> message = new ArrayList<>();
		message.add("DJ");
		message.add(""+gameID);
		message.add(""+roundID);
		message.add(""+turnID);
		String msg = e.splitTxt(message);
		sendBroadcast(msg);
	}

	@Override
	public void initializeTurn(String firstPlayer, int deckSize, int gameID, int roundID, int turnID) {
		ArrayList<String> message = new ArrayList<>();
		message.add("IT");
		message.add(firstPlayer);
		message.add(""+deckSize);
		message.add(""+gameID);
		message.add(""+roundID);
		message.add(""+turnID);
		String msg = e.splitTxt(message);
		sendBroadcast(msg);
	}

	@Override
	public int downNetworkGame() {
		
		NetworkControllerTCPServer nctcp = (NetworkControllerTCPServer) getNc().getControllerTCP();
		System.out.println("NTCP : "+nctcp);
		nctcp.stopListenTCP();
		System.out.println("NTCP : "+nctcp);
		nctcp.shutdownNetworkServer();
		System.out.println("NTCP : "+nctcp);
		return 0;
	}

	public void lostConnextion() {
		igca.lostConnextionStopGame();
	}
	


}
