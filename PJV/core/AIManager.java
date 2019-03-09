package core;

import java.util.List;

import card.AICard;
import card.Card;
import network.NetworkManager;
import player.AIPlayer;
import player.Player;

public class AIManager {
	
	// --- Start Singleton
	private static AIManager aiManager;
	
	private AIManager() {

	}
	
	public static AIManager getInstance() {
		if (aiManager == null) {
			aiManager = new AIManager();
		}
		return aiManager;
	}
	// --- End Singleton
	
	// --- Start Attributes
	private boolean log = false;
	private boolean verbose = false;
	
	private AIPlayer aiPlayer;
	private Game game;
	
	private int aiPosition;
	// --- End Attributes
	// --- Start Methods
	
	public void start(String nom, boolean log, boolean verbose) {
		aiPlayer = new AIPlayer(nom);
		this.log = log;
		this.verbose = verbose;
	}
	
	public void endTurn(AICard c) {
		aiPlayer.resetTurn(c);
	}

	// --- Start SendNetwork	
	public void takeHerd(String bool) {
		NetworkManager.getInstance().takeHerd(bool);
	}
	
	public void playCard(Card c) {
		NetworkManager.getInstance().playCard(c);
	}
	
	public void switchSens(String inv) {
		NetworkManager.getInstance().switchSens(inv);
	}
	// --- End SendNetwork
	// --- Start FromNetwork
	public void initializeGame(List<Player> players, int gameId) {
		game = new Game(players, gameId);
		game.setStatus("COMPLETE");
		aiPosition = getAIPositionInList(players);
		aiPlayer.initialize();

	}
	
	// --- Start Initialize
	public void initializeRound(boolean b, int roundID) {
		game.initializeRound(b, roundID);
	}

	public void initializeTurn(int numPlayerStart, int deckSize, int turnId) {
		game.initializeTurn(numPlayerStart, deckSize, turnId);
	}
	// --- End Initialize
		
	public void startGame(List<Card> cards) {
		aiPlayer.distributeHand(cards);
	}
	
	public void endRound(List<String> roundScore, List<String> totalScore) {
		getAIPlayer().getHand().clean();
	}

	public void distributeCard(Card card) {
		aiPlayer.distributeCard(card);
	}
	
	public void turnActionTakeHerdReverse(boolean herdPickUp, Card cardPlay, boolean reverseDirection) {
		game.turnPlayed(herdPickUp, cardPlay, reverseDirection);
	}

	public void turnActionCard(Card c) {
		game.turnPlayed(c);		
	}

	public void turnActionTakeHerdCard(boolean herdPickUp, Card c) {
		game.turnPlayedTR(herdPickUp, c);
	}

	public void turnActionReverseRotation(boolean rot, Card c) {
		game.turnPlayedRot(rot, c);
	}
	
	public void turnActionTakeHerdNoCard(boolean pickHerd) {
		game.turnPlayed(pickHerd);
	}
	
	public void setGameStatus(String status) {
		game.setStatus(status);
	}
	
	public int getAIPositionInList(List<Player> players) {
		for (int i = 0; i < players.size(); i++) {
			System.out.println(players.get(i).getName() + " ::::: " + aiPlayer.getName());
			if (players.get(i).getName().equals(aiPlayer.getName())) {
				return i;
			}
		}
		return -1;
	}
	
	public int getAIPosition() {
		return aiPosition;
	}

	public AIPlayer getAIPlayer() {
		return aiPlayer;
	}
	
	public Herd getCurrentHerd() {
		return game.getCurrentHerd();
	}
	
	public boolean isVerbose() {
		return verbose;
	}
	
	public boolean isLog() {
		return log;
	}
	// --- End FromNetwork
	// --- End Methods

	public void replay(String errorCode) {
		if(errorCode.equals("JC")) {
			System.out.println("ERREUR SUR LA CARTE JOUER NON PERMISE");
			game.takeHerd("OUI");
			game.getCurrentRound().replay();
		}
		
	}

	public int getSizeDeck() {
		return game.getDeckSize();
	}
}
