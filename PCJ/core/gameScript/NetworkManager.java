
package core.gameScript;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import core.gameScript.Exception.WrongNetworkCodeException;
import network.facade.server.FacadeServer;
import network.listenercore.GameNotification;
import network.listenercore.IGameClientAction;
import network.listenercore.IGameInformation;


public class NetworkManager implements IGameInformation, IGameClientAction {
	
    // --- Start Singleton
    private static NetworkManager instance;

    private NetworkManager() {
		workers = Executors.newCachedThreadPool();
        awake();
    }

    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }
    // --- End Singleton


	// --- Start Attributes
	// --- Start Network Server
	private GameNotification gameNotification;
    // --- End Network Server

	// --- Start Thread
	private final ExecutorService workers;
	// --- End Thread

	// --- End Attributes


    // --- Start Methods
    // --- Start Getter & Setter
    // --- Start Getter
    // --- End Getter
    // --- Start Setter
    // --- End Setter
    // --- End Getter & Setter

    // --- Start Construct Method
    private void awake() {
    	NetworkManager networkManager;
    	networkManager = this;
    	workers.submit(  ()->{
    		gameNotification = new FacadeServer(networkManager, networkManager);
    		/*
    		gameNotification.startSearchGame(4505, this);
    		 //Pour ne pas afficher la fenetre network 
        	try {
    			Thread.sleep(1000);
    		} catch (InterruptedException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	//Start le server TCP de partie sur le port 4502 a la main 
        	gameNotification.annoncerPartie();
    		
        	*/
    	});
    	
    }
    // --- End Construct Method

	// --- Start Start Game Methods
	public void startNetworkNewGame(int gamePort) {
    	gameNotification.startNetworkNewGame(gamePort);
	}
	// --- End Start Game Methods

	// --- Start Announce Game Method
	public void announceGame(int port, String name, int maxPlayer, int maxRealPlayer, int maxVirtualPlayer, int realPlayerNb, int virtualPlayerNb, String status) {
    	gameNotification.announceGame(port, name, maxPlayer, maxRealPlayer, maxVirtualPlayer, realPlayerNb, virtualPlayerNb, status);
	}
	// --- End Announce Game Method

	// --- Start Player Methods
	public void acceptPlayer(String connectionID, int playerPort) {
    	gameNotification.acceptPlayer(connectionID, playerPort);
	}

	public void refusePlayer(String connectionID, int playerPort) {
    	gameNotification.refusePLayer(connectionID, playerPort);
	}
	// --- End Player Methods

	// --- Start Initialisation Methods
	public void initializeGame(List<String> playerList, int gameID) {
    	String players;

    	players = String.join(",", playerList);
    	gameNotification.initializeGame(players, gameID);
	}

	public void initializeRound(boolean gameDirection, int gameID, int roundID) {
    	String direction;

    	if (gameDirection) {
    		direction = "CROI";
		}
    	else {
    		direction = "DECROI";
		}
    	System.out.println("-------------INIT ROUND-------------");
    	System.out.println("----" + gameID +"--------"+roundID+"-----");
    	gameNotification.initializeRound(direction, gameID, roundID);
	}

	public void initializeTurn(int firstPlayer, int deckSize, int gameID, int roundID, int turnID) {
    	gameNotification.initializeTurn("" + firstPlayer, deckSize, gameID, roundID, turnID);
	}
	// --- End Initialisation Methods

	// --- Start Finalize Methods
	public void finalizeGame(String winner, int gameID) {
    	gameNotification.endGame(winner, gameID);
	}

	public int downNetworkGame() {
    	return gameNotification.downNetworkGame();
	}

	public void finalizeRound(List<String> playersRoundScore, List<String> playersGameScore, int gameID, int roundID) {
    	String roundScore;
    	String gameScore;

    	roundScore = String.join(",", playersRoundScore);
    	gameScore = String.join(",", playersGameScore);
    	gameNotification.endRound(roundScore, gameScore, gameID, roundID);
	}
	// --- End Finalize Methods

	// --- Start Inform Action Methods
	public void informPlayerAction(boolean herdPickUp, String cardPlay, boolean reverseDirection, int gameID, int roundID, int turnID) {
    	gameNotification.informPlayerAction(herdPickUp, cardPlay, reverseDirection, gameID, roundID, turnID);
	}

	public void indicateError(String errorCode, int gameID, int roundID, int turnID, int playerPort) {
    	gameNotification.indicateError(errorCode, gameID, roundID, turnID, playerPort);
	}

	public void distributeCard(String playerCard, int gameID, int roundID, int turnID, int playerPort) {
    	gameNotification.distributeCard(playerCard, gameID, roundID, turnID, playerPort);
	}
	// --- End Inform Action Methods

	// --- Start Hand Control Methods
	public void distributeHand(int playerPort, List<String> playerHand, int gameID, int roundID) {
    	String playerCards;

    	playerCards = String.join(",", playerHand);
    	gameNotification.distributeHand(playerCards, gameID, roundID, playerPort);
	}
	// --- End Hand Control Methods
    
    // --- Start Overrides
	// --- Start Game Client Action
	@Override
	public void playerSearchGame(String type, String tailleP) {
    	EventManager.getInstance().playerSearchGame(Integer.parseInt(tailleP) , type);
	}

	@Override
	public void joueurRequeteJoinGame(String name, String type, String idConnection, String port) {
		System.out.println("Requete join game");
		EventManager.getInstance().addNewPlayer(name, type, idConnection, Integer.parseInt(port));
	}

	@Override
	public void joueurRentreTroupeau(String ram, String idp, String idm, String idt, String port) {
    	if (ram.equals("OUI")) {
			EventManager.getInstance().pickUpHerd(Integer.parseInt(idp), Integer.parseInt(idm), Integer.parseInt(idt),Integer.parseInt(port));
		}
	}

	@Override
	public void joueurAJouerCarte(String carteJouer, String uidPartie, String uidTour, String uidManche, String port) {
		System.out.println("CARTE JOUE" + "-->" + carteJouer);
		EventManager.getInstance().playerPlayCard(carteJouer, Integer.parseInt(uidPartie), Integer.parseInt(uidTour), Integer.parseInt(uidManche),Integer.parseInt(port));
	}

	@Override
	public void joueurInverseSensJeu(String inverserSensJeux, String uidPartie, String uidTour, String uidManche, String port) {
		System.out.println("JOUEUR INVERSE SENS DU JEUX");
		boolean directionChange;
    	directionChange = inverserSensJeux.equals("OUI");
	
			EventManager.getInstance().changeDirection(directionChange, Integer.parseInt(uidPartie), Integer.parseInt(uidTour), Integer.parseInt(uidManche),Integer.parseInt(port));
		
	}
	// --- End Game Client Action
	// --- Start Game Information
	@Override
	public int getPort() {
		return EventManager.getInstance().getPort();
	}

	@Override
	public String getName() {
		return EventManager.getInstance().getName();
	}

	@Override
	public int getNbPlayerMax() {
		return EventManager.getInstance().getNbPlayer();
		
	}

	@Override
	public int getNbPlayerRMax() {
		return EventManager.getInstance().getMaxRealPlayer();
	}

	@Override
	public int getNbPlayerVMax() {
		return EventManager.getInstance().getMaxVirtualPlayer();
	}

	@Override
	public int getNbPlayerRConnect() {
		return EventManager.getInstance().getRealPlayer();
	}

	@Override
	public int getNbPlayerVConnect() {
		return EventManager.getInstance().getVirtualPlayer();
	}

	@Override
	public String getstatut() {
		return EventManager.getInstance().getStatus();
	}

	@Override
	public String getTypePartie() {
		int gameMaxRealPlayer;
		int gameMaxVirtualPlayer;

		gameMaxRealPlayer = EventManager.getInstance().getMaxRealPlayer();
		gameMaxVirtualPlayer = EventManager.getInstance().getMaxVirtualPlayer();
		if ((gameMaxRealPlayer > 0) && (gameMaxVirtualPlayer == 0)) {
			return "JRU";
		}
		else if ((gameMaxVirtualPlayer > 0) && (gameMaxRealPlayer == 0)) {
			return "JVU";
		}
		else if ((gameMaxRealPlayer != 0) && (gameMaxVirtualPlayer != 0)){
			return "MIXTE";
		}
		else if ((gameMaxRealPlayer != 0) && (gameMaxVirtualPlayer != 0) && (gameMaxRealPlayer > gameMaxVirtualPlayer)) {
			return "MIXSR";
		}
		else {
			return "MIXSV";
		}
	}
	

	public int shutdowNetworkTCP() {
		return gameNotification.shutdowNetworkTCP();
	}
	
	@Override
	public String getStatut() {
		return EventManager.getInstance().getStatus();
	}

	@Override
	public void refusePlayerAutomatic(String string, String string2, String string3, String port) {
		gameNotification.refusePLayer(string3, Integer.parseInt(port));
		
	}
	// --- End Game Information
    // --- End Overrides
    // --- End Methods

	public void shutdowNetwork() {
		gameNotification.shutdowNetwork();
		
	}

	public void endGameSession(int gameID) {
		gameNotification.endGameSession(gameID);
		
	}

	public void restartGame(int gameID, int newGameID) {
		gameNotification.restartGame(gameID, newGameID);
		
	}

	@Override
	public void lostConnextionStopGame() {
		 EventManager.getInstance().lostConnection();
	}

	public void announceDeco(int gameID, int roundID, int turnID) {
		gameNotification.announceDeconnexion(gameID, roundID, turnID);
	}

}