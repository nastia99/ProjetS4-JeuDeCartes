package core.gameScript;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import core.gameScript.Exception.HandFullException;
import core.gameScript.Exception.WrongNetworkCodeException;
import core.interfaceScript.javafx.UIManager;
import javafx.application.Platform;

public class EventManager {

	// --- Start Singleton
	private static EventManager instance;

	private EventManager() {
		awake();
	}

	public static EventManager getInstance() {
		if (instance == null) {
			instance = new EventManager();
		}
		return instance;
	}
	// --- End Singleton



	// --- Start Attributes
	// --- Start Managers
	private DealManager dealManager;
	private GameManager gameManager;
	private NetworkManager networkManager;
	private UIManager uiManager;
	private String stateEndGame;
	// --- End Managers

	// --- Start Thread
	private Thread endGameThread;
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
		dealManager = DealManager.getInstance();
		gameManager = GameManager.getInstance();
		uiManager = UIManager.getInstance();
		networkManager = NetworkManager.getInstance();
	}
	// --- End Construct Method

	// --- Start Announce Game Method
	public void announceGame() {
		System.out.println("ANNOUCE GAME");
		int port;
		String name;
		int maxPlayer;
		int maxRealPlayer;
		int maxVirtualPlayer;
		int realPlayerNb;
		int virtualPlayerNb;
		String status;

		port = gameManager.getPort();
		name = gameManager.getName();
		maxPlayer = gameManager.getNbPlayer();
		maxRealPlayer = gameManager.getMaxRealPlayer();
		maxVirtualPlayer = gameManager.getMaxVirtualPlayer();
		realPlayerNb = gameManager.getRealPlayer();
		virtualPlayerNb = gameManager.getVirtualPlayer();
		status = gameManager.getStatus();
		System.out.println("maxPlayer : " + maxPlayer);
		System.out.println("maxRealPlayer : " + maxRealPlayer);
		System.out.println("maxVirtualPlayer : " + maxVirtualPlayer);
		networkManager.announceGame(port, name, maxPlayer, maxRealPlayer, maxVirtualPlayer, realPlayerNb, virtualPlayerNb, status);
	}
	// --- End Announce Game Method

	// --- Start Game Creation Method
	public void createGame() {
		gameManager.createGame();
	}
	// --- End Game Creation Method

	// --- Start Game Modification Methods
	public int incrementMaxRealPlayer() {
		System.out.println("incrementMaxRealPlayer");
		return gameManager.incrementMaxRealPlayer();
	}

	public int decrementMaxRealPlayer() {
		System.out.println("decrementMaxRealPlayer");
		return gameManager.decrementMaxRealPlayer();
	}

	public int incrementMaxVirtualPlayer() {
		System.out.println("incrementMaxVirtualPlayer");
		return gameManager.incrementMaxVirtualPlayer();
	}

	public int decrementMaxVirtualPlayer() {
		System.out.println("decrementMaxVirtualPlayer");
		return gameManager.decrementMaxVirtualPlayer();
	}
	// --- End Game Modification Methods

	// --- Start Start Game Methods
	public void launchGame() {
		int gamePort;

		gamePort = gameManager.getPort();
		networkManager.startNetworkNewGame(gamePort);
		announceGame();
	}
	// --- End Start Game Method

	// --- Start Player Method
	public void playerSearchGame(int playerNb, String type) {
		int gamePlayerNb;
		int gameMaxRealPlayer;
		int gameMaxVirtualPlayer;

		gamePlayerNb = gameManager.getNbPlayer();
		gameMaxRealPlayer = gameManager.getMaxRealPlayer();
		gameMaxVirtualPlayer = gameManager.getMaxVirtualPlayer();

		System.out.println(gamePlayerNb +""+gameMaxRealPlayer+""+ gameMaxVirtualPlayer);
		System.out.println("gamePlayerNb : " + gamePlayerNb);
		System.out.println("gameMaxRealPlayer : " + gameMaxRealPlayer);
		System.out.println("gameMaxVirtualPlayer : " + gameMaxVirtualPlayer);

		if (gamePlayerNb <= playerNb) {
			switch (type) {
			case "JRU":
				if ((gameMaxRealPlayer > 0) && (gameMaxVirtualPlayer == 0)) {
					announceGame();
				}
				break;
			case "JVU":
				if ((gameMaxVirtualPlayer > 0) && (gameMaxRealPlayer == 0)) {
					announceGame();
				}
				break;
			case "MIXTE":
				if ((gameMaxRealPlayer != 0) && (gameMaxVirtualPlayer != 0)) {
					announceGame();
				}
				break;
			case "MIXSR":
				if ((gameMaxRealPlayer != 0) && (gameMaxVirtualPlayer != 0) && (gameMaxRealPlayer > gameMaxVirtualPlayer)) {
					announceGame();
				}
				break;
			case "MIXSV":
				if ((gameMaxRealPlayer != 0) && (gameMaxVirtualPlayer != 0) && (gameMaxVirtualPlayer > gameMaxRealPlayer)) {
					announceGame();
				}
				break;
			}
		}
	}

	public void addNewPlayer(String playerName, String playerType, String uidConnection, int playerPort) {
		System.out.println("==========Start ajout player===========");
		int playerPosition;

		if (!canAcceptPlayer(playerType)) {
			System.out.println("Can't accepte player type");
			networkManager.refusePlayer(uidConnection, playerPort);
		}
		else {
			System.out.println("Je passe dans cette partie la");
			if (!uiManager.acceptPlayer(playerName)) {
				System.out.println("Victime");
				networkManager.refusePlayer(uidConnection, playerPort);
			}
			else {
				System.out.println("ALED");
				playerPosition = gameManager.addPlayer(playerPort, playerName, playerType);
				Platform.runLater( () -> {
					uiManager.addPlayerInLobby(playerName, playerPosition);
				});
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				networkManager.acceptPlayer(uidConnection, playerPort);
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				announceGame();
			}
		}
	}

	private boolean canAcceptPlayer(String playerType) {
		if (playerType.equals("JR")) {
			return gameManager.canAcceptRealPlayer();
		}
		else if (playerType.equals("JV")) {
			return gameManager.canAcceptVirtualPlayer();
		}
		else {
			return false;
		}
	}
	// --- End Player Methods

	// --- Start Initialisation Methods
	public void startGame() {
		gameManager.startGame();
		initializeGame();
		wait(150);
		gameManager.newRound();
		initializeRound();
		wait(150);
		distributeHand();
		wait(150);
		initializeTurn();
	}

	// --- Start Restart Game
	public void restartGame() {
		this.setStateEndGame("RSG");
		endGameThread.interrupt();
		int oldGameId = gameManager.getGameID();
		gameManager.restartGame();
		int newGameId = gameManager.getGameID();
		networkManager.restartGame(oldGameId,newGameId);
		Platform.runLater( () -> {
			uiManager.setupFlies(gameManager.getPlayersGameScore());
			uiManager.cleanGame();
			uiManager.loadToScene(UIManager.GAMETABLE);
		});
		initializeGame();
		wait(150);
		gameManager.newRound();
		initializeRound();
		wait(150);
		distributeHand();
		wait(150);
		initializeTurn();
	}
	// --- End Restart Game


	public void startRound() {
		dealManager.reset();
		wait(150);
		gameManager.nextRound();
		wait(150);
		initializeRound();
		wait(150);
		distributeHand();
		wait(150);
		initializeTurn();
	}

	private void initializeGame() {
		List<String> playersName;
		int gameID;

		playersName = gameManager.getPlayersNames();
		gameID = gameManager.getGameID();
		networkManager.initializeGame(playersName, gameID);
	}

	private void initializeRound() {
		boolean gameDirection;
		int gameID;
		int roundID;

		gameDirection = gameManager.getGameDirection();
		gameID = gameManager.getGameID();
		roundID = gameManager.getRoundID();
		Platform.runLater( () -> {
			uiManager.setupRound(roundID);
		});
		networkManager.initializeRound(gameDirection, gameID, roundID);

	}

	private void initializeTurn() {
		int firstPlayer;
		int deckSize;
		int gameID;
		int roundID;
		int turnID;

		firstPlayer = gameManager.getFirstPlayerRound();
		deckSize = dealManager.nbCardInDeck();
		gameID = gameManager.getGameID();
		roundID = gameManager.getRoundID();
		turnID = gameManager.getTurnID();
		Platform.runLater( () -> {
			uiManager.setCurrentPlayer(gameManager.getCurrentPlayer());
		});
		networkManager.initializeTurn(firstPlayer, deckSize, gameID, roundID, turnID);

	}
	// --- End Initialisation Methods

	// --- Start Finalize Methods
	private void finalizeGame() {
		String winner;
		int gameID;
		stateEndGame = "null";
		winner = gameManager.getWinner();
		gameID = gameManager.getGameID();
		networkManager.finalizeGame(winner, gameID);
		endGameThread = new Thread( () -> {
			try {
				gameManager.endGame();
				Thread.sleep(60000);
			} catch (InterruptedException interrupt) {
				System.out.println("Interruption Fin Partie Attente Action");
				Thread.currentThread().interrupt();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!Thread.currentThread().isInterrupted()) {
				System.out.println("SHUTDOWN FIN ATTENTE 1 MIN FIN DE LA PARTIE ");
				returnMenu();
			}
			else {
				if(stateEndGame.equals("RSG")) {
					System.out.println("RESTART GAME");
				}
				else if(stateEndGame.equals("SHUT")) {
					System.out.println("SHUTDOWN FIN DE LA PARTIE ");
					networkManager.endGameSession(gameManager.getGameID());
					gameManager.clearGame();
					wait(150);
					networkManager.shutdowNetwork();
				}
			}
			endGameThread = null;
		});
		endGameThread.start();
		displayPlayerRanking();
	}

	private void finalizeRound() {
		System.out.println("FINALIZE ROUND");
		gameManager.endRound();
		informRoundEnd();
		wait(150);

		try {
			if (gameManager.gameContinue()) {
				startRound();
			}
			else {
				finalizeGame();
			}} catch (Exception e) {
				e.printStackTrace();
			}
	}
	// --- End Finalize Methods

	// --- Start Inform Action Methods
	private void informRoundEnd() {
		System.out.println("INFORM END ROUND");
		List<String> playersRoundScore;
		List<String> playersGameScore;
		int gameID;
		int roundID;

		playersRoundScore = gameManager.getPlayersRoundScore();
		playersGameScore = gameManager.getPlayersGameScore();
		gameID = gameManager.getGameID();
		roundID = gameManager.getRoundID();
		Platform.runLater( () -> {
			uiManager.setupFlies(playersGameScore);
		});
		networkManager.finalizeRound(playersRoundScore, playersGameScore, gameID, roundID);
	}

	private void informPlayerAction() {
		boolean herdPickUp;
		String cardPlay;
		boolean reverseDirection;
		int gameID;
		int roundID;
		int turnID;

		herdPickUp = gameManager.turnHerdPickUp();
		System.out.println("SERVFEUR PLAYER HAVE RAMASSE TROUPEAU : "+herdPickUp);
		cardPlay = gameManager.turnCardPlay();
		System.out.println("SERVEUR ACTION PLAY CARD : "  + cardPlay);
		reverseDirection = gameManager.turnDirectionChange();
		System.out.println("SERVEUR TURN DIRECTION : "  + reverseDirection);
		gameID = gameManager.getGameID();
		System.out.println("SERVEUR GAME ID : "  + gameID);
		roundID = gameManager.getRoundID();
		System.out.println("SERVEUR ROUND ID : "  + roundID);
		turnID = gameManager.getTurnID();
		System.out.println("SERVEUR TURN ID : "  + turnID);
		networkManager.informPlayerAction(herdPickUp, cardPlay, reverseDirection, gameID, roundID, turnID);
	}

	private void indicateError(String errorCode,int port) {
		int gameID;
		int roundID;
		int turnID;
		int playerPort;

		gameID = gameManager.getGameID();
		roundID = gameManager.getRoundID();
		turnID = gameManager.getTurnID();
		playerPort = gameManager.getPlayerPort();
		networkManager.indicateError(errorCode, gameID, roundID, turnID, port);
	}

	private void distributeCard(String playerCard) {
		int gameID;
		int roundID;
		int turnID;
		int playerPort;

		if(playerCard.equals("null")) {
			gameID = gameManager.getGameID();
			roundID = gameManager.getRoundID();
			turnID = gameManager.getTurnID();
			playerPort = gameManager.getPlayerPort();
			networkManager.distributeCard("PV", gameID, roundID, turnID, playerPort);
		}
		else {
			gameID = gameManager.getGameID();
			roundID = gameManager.getRoundID();
			turnID = gameManager.getTurnID();
			playerPort = gameManager.getPlayerPort();
			networkManager.distributeCard(playerCard, gameID, roundID, turnID, playerPort);
		}

	}
	// --- End Inform Action Methods

	// --- Start Hand Control Methods
	private void distributeHand() {
		int i;
		int nbPlayer;
		int gameID;
		int roundID;
		int playerPort;
		List<String> playerHand;

		nbPlayer = gameManager.getNbPlayer();
		for (i = 0 ; i < nbPlayer ; i++) {
			playerHand = dealManager.giveAHand();
			try {
				gameManager.givePlayerHand(i, playerHand);
			} catch (WrongNetworkCodeException e) {
				e.printStackTrace();
			}
			Platform.runLater( () -> {
				uiManager.manageDeck(dealManager.nbCardInDeck());
			});
			playerPort = gameManager.getPlayerPort(i);
			gameID = gameManager.getGameID();
			roundID = gameManager.getRoundID();
			networkManager.distributeHand(playerPort, playerHand, gameID, roundID);
			System.out.println("DISTRIBUTE HAND OF PLAYER :"+gameManager.getPlayersNames().get(i));
		}
	}

	public void playerPlayCard(String card, int gameID, int roundID, int turnID, int port) {
		System.out.println(" ------------------------------------------- ");
		int cardPosition;
		int currentPlayer;
		String cardColor;
		String cardValue;
		int deckSize;
		System.out.println( "GAME : "+gameID+" ROUND "+ roundID + " TURN "+  turnID  );
		if (checkID(gameID, roundID, turnID)) {
			System.out.println("Check id game turn round ok");
			wait(150);
			currentPlayer = gameManager.getCurrentPlayer();
			System.out.println("Get current player : " + currentPlayer);
			if(gameManager.getPlayerPort(currentPlayer) == port) {
				System.out.println("Good Players");
				try {
					if (gameManager.isInPlayerHand(card)) {
						System.out.println("Players have card in hand");

						cardPosition = GameManager.getInstance().playCard(card);
						if(cardPosition == -1) {
							indicateError("JC", port);
						}
						else if (cardPosition >= 0) {
							System.out.println("CARD POSITION : "+cardPosition);
							System.out.println("CURRENT PLAYER : "+ gameManager.getPlayersNames().get(currentPlayer));
							cardColor = dealManager.getColor(card);
							cardValue = dealManager.getValue(card);
							deckSize = dealManager.nbCardInDeck();

							System.out.println(gameManager.getPlayersNames().get(currentPlayer)+" HAVE PLAY "+cardColor+"."+cardValue);
							Platform.runLater( () -> {
								UIManager.getInstance().addCardToFlock(cardColor, cardValue, currentPlayer, cardPosition);
								UIManager.getInstance().manageDeck(deckSize);
							});
							if (!dealManager.isSpecial(card)) {
								giveCardAndFinishTurn(currentPlayer);
							}
						}
						else {
							System.out.println("INDICATE ERROR ---- PLAYER "+gameManager.getPlayersNames().get(currentPlayer)+"HAVE NOT THE CARD IN THIS HAND");
							indicateError("JC",port);
						}
					} 
				}catch (WrongNetworkCodeException e) {
					System.out.println("INDICATE ERROR ---- WRONG NETWORK CODE");
					indicateError("JC",port);
				}
			}
			else {
				System.out.println("INDICATE ERROR ----  WRONG PLAYER");
				indicateError("JC",port);
			}
		}
		else {
			System.out.println("INDICATE ERROR ----  WRONG GID RID TID");
			indicateError("JC",port);
		}
		System.out.println("DONE-----------------------------------------");
	}

	private void giveCardAndFinishTurn(int currentPlayer) {
		try {
			String cardGive = givePlayerCard();
			if(cardGive == null) {
				System.out.println(gameManager.getPlayersNames().get(currentPlayer) +" NOT GIVE :DEAL EMPTY");
			}
			System.out.println(gameManager.getPlayersNames().get(currentPlayer) +" GIVE "+cardGive);
		} catch (HandFullException e) {
			e.printStackTrace();
		}
		wait(150);
		informPlayerAction();
		wait(150);
		gameManager.nextTurn();
		initializeTurn();
	}

	public void resetCurrentPlayer(int currentPlayer) {
		Platform.runLater( () -> {
			uiManager.resetCurrentPlayer(currentPlayer);
		});		
	}

	private String givePlayerCard() throws HandFullException {
		String pickCard;
		if(!dealManager.isEmpty()) {
			pickCard = dealManager.giveCard();
			try {
				gameManager.addCard(pickCard);
			} catch (WrongNetworkCodeException e) {
				e.printStackTrace();
			} 
			Platform.runLater( () -> {
				uiManager.manageDeck(dealManager.nbCardInDeck());
			});

			distributeCard(pickCard);
			return pickCard;
		}
		else {
			distributeCard("null");
		}
		return null;
	}

	// --- End Hand Control Methods

	// --- Start Herd Control Method

	public void pickUpHerd(int gameID, int roundID, int turnID, int port) {
		if (checkID(gameID, roundID, turnID)) {
			System.out.println("PLAYER : "+gameManager.getPlayersNames().get(gameManager.getCurrentPlayer())+" RAMASSE TROUPEAU");
			if (dealManager.isEmpty()) {
				System.out.println("DEAL MANAGER EMPTY FINALIZE ROUND");
				if (gameManager.herdIsEmpty()) {
					indicateError("RT",port);
				}
				else {


					Platform.runLater( () -> {
						uiManager.clearCurrentflock();
					});

					gameManager.pickUpHerd();
					playersScore();	
					wait(150);
					informPlayerAction();
					wait(150);
					finalizeRound();
				}
			} else {

				if (gameManager.herdIsEmpty()) {
					System.out.println("INDICATE ERROR ----  HERD IS EMPTY");
					indicateError("RT",port);
				}
				else {
					Platform.runLater( () -> {
						uiManager.clearCurrentflock();
					});
					gameManager.pickUpHerd();
					playersScore();	
				}
			}
		}
		else {
			System.out.println("INDICATE ERROR ----  WRONG GID RID TID");
			indicateError("RT",port);
		}
	}

	public void playersScore() {
		System.out.println("PLAYER SCORE : ");
		int i = 0;
		while(i<gameManager.getPlayersNames().size()) {
			System.out.println("player : "+gameManager.getPlayersNames().get(i) +"have "+gameManager.getPlayersRoundScore().get(i));
			i++;
		}	
	}
	// --- End Herd Control Method

	// --- Start Change Direction

	public void changeDirection(boolean changeDirection, int gameID, int roundID, int turnID, int port) {
		String cardPlay;

		if (checkID(gameID, roundID, turnID)) {
			cardPlay = gameManager.turnCardPlay();
			try {
				if (dealManager.isSpecial(cardPlay)) {
					if (changeDirection) {
						System.out.println("CHANGEMENT DIRECTION BEFORE : "+gameManager.getGameDirection());
						gameManager.changeDirection();
						System.out.println("CHANGEMENT DIRECTION AFTER : "+gameManager.getGameDirection());
					}
					try {

						givePlayerCard();
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					informPlayerAction();
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					gameManager.nextTurn();
					initializeTurn();
				}
				else {
					indicateError("ISJ",port);
				}
			} catch (WrongNetworkCodeException e) {
				e.printStackTrace();
			}
		}
		else {
			indicateError("ISJ",port);
		}
	}
	// --- End Change Direction


	// --- Start End Game Screen
	private void displayPlayerRanking() {
		List<String> playerNameRanking;
		List<Integer> playerPointRanking;

		playerNameRanking = gameManager.getPlayerNameRanking();
		playerPointRanking = gameManager.getPlayerPointRanking();
		Platform.runLater( () -> {
			uiManager.loadScore(playerNameRanking, playerPointRanking);
		});
	}
	// --- End End Game Screen

	// --- Start Wait
	private void wait(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	// --- End Wait

	// --- Start ID Verification Method
	private boolean checkID(int gameID, int roundID, int turnID) {
		return (gameManager.getGameID() == gameID) && (gameManager.getRoundID() == roundID) && (gameManager.getTurnID() == turnID);
	}
	// --- End ID Verification Method

	// --- Start Game Information Methods
	public int getPort() {
		return gameManager.getPort();
	}

	public String getName() {
		return gameManager.getName();
	}

	public int getNbPlayer() {
		return gameManager.getNbPlayer();
	}

	public int getMaxRealPlayer() {
		return gameManager.getMaxRealPlayer();
	}

	public int getMaxVirtualPlayer() {
		return gameManager.getMaxVirtualPlayer();
	}

	public int getRealPlayer() {
		return gameManager.getRealPlayer();
	}

	public int getVirtualPlayer() {
		return gameManager.getVirtualPlayer();
	}

	public String getStatus() {
		return gameManager.getStatus();
	}

	public List<String> getPlayers() {
		return gameManager.getPlayersNames();
	}
	// --- End Game Information Methods

	// --- Start Exit PCJ Method
	public int shutDownPCJ(String string) {
		setStateEndGame(string);
		if(endGameThread != null) {
			endGameThread.interrupt();
		}
		else {
			gameManager.clearGame();
			wait(150);
			networkManager.shutdowNetwork();
		}
		wait(150);
		return 0;
	}
	// --- End Exit PCJ Method
	// --- End Methods

	public String getStateEndGame() {
		return stateEndGame;
	}

	public void setStateEndGame(String stateEndGame) {
		this.stateEndGame = stateEndGame;
	}

	public int shutdownTcp() {
		setStateEndGame("SHUT_TCP");
		if(endGameThread != null) {
			endGameThread.interrupt();
		}

		resetPlayers();
		//uiManager.clearLobby();
		return networkManager.shutdowNetworkTCP();
	}

	public void resetPlayers() {
		gameManager.clearPlayersGameLobby();

	}

	public void lostConnection() {
		int gameID = gameManager.getGameID();
		int roundID = gameManager.getRoundID();
		int turnID = gameManager.getTurnID();
		networkManager.announceDeco(gameID,roundID,turnID);
		gameManager.clearGame();
		setStateEndGame("CLIENT DECONNEXION WAIT 10 SECONDES");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		networkManager.endGameSession(gameManager.getGameID());
		wait(150);
		setStateEndGame("DOWN TCP");
		int i = uiManager.downTcp();
		wait(150);
		dealManager.reset();
		Platform.runLater( () -> {
			try {
			uiManager.loadToScene(UIManager.MENU);
			uiManager.cleanGame();
			uiManager.setupFlies(Arrays.asList("0", "0", "0", "0", "0"));
			uiManager.resetParameterGame();}catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public void returnMenu() {
		int gameID = gameManager.getGameID();
		int roundID = gameManager.getRoundID();
		int turnID = gameManager.getTurnID();
		gameManager.clearGame();
		setStateEndGame("RETOUR MENU ");
		networkManager.endGameSession(gameManager.getGameID());
		wait(150);
		setStateEndGame("DOWN TCP");
		int i = uiManager.downTcp();
		wait(150);
		dealManager.reset();
		Platform.runLater( () -> {
			try {
			uiManager.loadToScene(UIManager.MENU);
			uiManager.cleanGame();
			uiManager.setupFlies(Arrays.asList("0", "0", "0", "0", "0"));
			uiManager.resetParameterGame();}catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public void exitGameEnCours() {
		returnMenu();
	}
}
