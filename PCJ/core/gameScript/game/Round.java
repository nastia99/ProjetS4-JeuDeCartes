package core.gameScript.game;

import core.gameScript.Exception.HandFullException;
import core.gameScript.cardGame.Card;

import java.util.ArrayList;
import java.util.List;

public class Round {

	// --- Start Constructor
	public Round(List<Player> players) {
		roundID = 1;
		this.players = players;
		firstPlayer = 0;
		currentPlayer = firstPlayer;
		direction = true;
		cardsBoard = new ArrayList<>();
		turn = new Turn();
	}

	public Round(int roundID, List<Player> players, int startPlayerNb, boolean direction) {
		this.roundID = roundID;
		this.players = players;
		firstPlayer = startPlayerNb;
		currentPlayer = firstPlayer;
		this.direction = direction;
		cardsBoard = new ArrayList<>();
		turn = new Turn();
		resetPlayerFly();
	}
	// --- End Constructor


	// --- Start Attributes
	// --- Start Identification Attributes
	private int roundID;
	// --- End Identification Attributes

	// --- Start Player Management Attributes
	private List<Player> players;
	private int firstPlayer;
	private int currentPlayer;
	private boolean direction;
	// --- End Player Management Attributes

	// --- Start Board Attribute
	private List<Card> cardsBoard;
	// --- End Board Attribute

	// --- Start Turn Identification
	private Turn turn;
	// --- End Turn Identification
	// --- End Attributes


	// --- Start Methods
	// --- Start Getter
	public int getRoundID() {
		return roundID;
	}

	public Turn getTurn() {
		return turn;
	}

	public int getFirstPlayer() {
		return firstPlayer;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public boolean getDirection() {
		return direction;
	}
	// --- End Getter

	// --- Start Round Control Method
	public void endRound() {
		for (Player player : players) {
			player.addFly(player.getNbFliesInHand());
			player.convertFlyToPoint();
		}
	}
	// --- End Round Control Method

	// --- Start Player Control Method
	public int getCurrentPlayerPort() {
		return players.get(currentPlayer).getPort();
	}

	public int getPlayerPort(int playerPosition) {
		return players.get(playerPosition).getPort();
	}

	public List<String> getPlayersRoundScore() {
		List<String> playersRoundScore;

		playersRoundScore = new ArrayList<>();
		for (Player player : players) {
			playersRoundScore.add("" + player.getNbFly());
		}
		return playersRoundScore;
	}
	private void resetPlayerFly() {
		for (Player player : players) {
			player.resetFly();
		}
	}
	// --- End Player Control Method

	// --- Start Hand Control Methods
	public void givePlayerHand(int playerPosition, List<Card> playerHand) {
		players.get(playerPosition).givePlayerHand(playerHand);
	}

	public boolean isInCurrentPlayerHand(Card card) { // on verifie si le joueur a une carte card
		return players.get(currentPlayer).isInHand(card);
	}

	public int currentPlayerPlayCard(Card card) { // enleve une carte card au joueur
		int cardPosition;
					
		cardPosition = card.place(cardsBoard);
		if(cardPosition == -1) {
			return -1;
		}
		else {
			turn.playCard(card);
			players.get(currentPlayer).playCard(card);
			return cardPosition;
		}	
	}

	public void currentPlayerAddCard(Card card) throws HandFullException { // ajoute une carte card au joueur
		players.get(currentPlayer).addCard(card);
	}
	// --- End Hand Control Methods

	// --- Start Herd Methods
	public int getHerdFlies() {
		int nbFly;

		nbFly = 0;
		for (Card card : cardsBoard) {
			nbFly += card.getFly();
		}
		return nbFly;
	}
	
	public int getFlyHandFinalizeRound(int nbPlayer) {
		int nbFly;
		nbFly = 0;

		List<Card> l = this.players.get(nbPlayer).getHand();
		for (Card card : l) {
			nbFly += card.getFly();
		}
		
		
		return nbFly;
	}

	public void currentPlayerPickUpHerd() { // ajoute mouche au compteur de mouche du joueur
		int nbFly;

		nbFly = getHerdFlies();
		players.get(currentPlayer).addFly(nbFly);
		turn.pickUpHerd();
		cardsBoard.clear();
	}
	
	public void finalizeRoundPickUpHerdHand() {
		int nbFly;
		int i =0;
		
		while(i<players.size()) {
			nbFly = getFlyHandFinalizeRound(i);
			players.get(i).addFly(nbFly);
			List<Card> lc =players.get(i).getHand();
			players.get(i).getHand().removeAll(lc);
			i++;
		}
	}

	public boolean herdIsEmpty() {
		return cardsBoard.isEmpty();
	}
	// --- End Herd Methods

	// --- Start Turn Control Methods
	public int getTurnID() {
		return turn.getTurnID();
	}

	public boolean turnHerdPickUp() {
		return turn.getHerdPickUp();
	}

	public Card turnCardPlay() {
		return turn.getCardPlay();
	}

	public boolean turnDirectionChange() {
		return turn.getDirectionChange();
	}
	// --- End Turn Control Methods

	// --- Start Scheduling Methods
	public void nextPlayer() {
		if (direction) {
			if (currentPlayer < (players.size() - 1)) {
				currentPlayer++;
			}
			else {
				currentPlayer = 0;
			}
		}
		else {
			if (currentPlayer > 0) {
				currentPlayer--;
			}
			else {
				currentPlayer = (players.size() - 1);
			}
		}
		System.out.println("NEXT PLAYER :" +players.get(currentPlayer).getName());
	}

	public void nextTurn() {
		turn.nextTurn();
		nextPlayer();
	}

	public void changeDirection() {
		direction = !direction;
		turn.setDirectionChangeTrue();
	}
	// --- End Scheduling Methods

	// --- Start Playing Method
	public boolean isPlaceable(Card card) {
		return card.isPlaceable(cardsBoard);
	}
	// --- End Playing Method
	// --- End Methods

}