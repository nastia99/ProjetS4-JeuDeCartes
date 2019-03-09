package player;

import java.util.List;

import card.Card;
import core.Hand;
import core.HandFullException;

public class Player {

	// --- Start Construct
	public Player(int port, String name, String type) {
		this.port = port;
		this.name = name;
		this.type = type;
		hand = new Hand<>();
		nbFly = 0;
		points = 0;
	}
	// --- End Construct


	// --- Start Attributes
	// --- Start Identification Attributes
	private int port;
	private String name;
	private String type;
	// --- End Identification Attributes

	// --- Start Round Attributes
	private Hand<Card> hand;
	private int nbFly;
	// --- End Round Attributes

	// --- Start Game Attributes
	private int points;
	// --- End Game Attributes
	// --- End Attributes


	// --- Start Methods
	// --- Start Getter
	// --- Start Identification Getter
	public int getPort() {
		return port;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}
	// --- End Identification Getter

	// --- Start Fly Getter
	public int getNbFly() {
		return nbFly;
	}
	// --- End Fly Getter

	// --- Start Game Getter
	public int getPoints() { // donne le nombre de points des joueurs
		return points;
	}
	// --- End Game Getter
	// --- End Getter

	// --- Start Setter
	public void setType(String type) {
		this.type = type; // TODO Remove
	}
	// --- End Setter

	// --- Start Hand Methods
	public List<Card> getHand() {
		return hand.getCards();
	}

	public boolean isInHand(Card card) { // regarde la liste de carte du joueur si il y a la carte
		return hand.isInList(card);
	}

	public void addCard(Card card) throws HandFullException { // ajoute une carte
		hand.addCard(card);
	}

	public void playCard(Card card) { // joue une carte
		hand.playCard(card);
	}
	// --- End Hand Methods

	// --- Start Fly Control Methods
	public void addFly(int nbFly) { // ajoute des mouche pour le joueur
		this.nbFly += nbFly;
	}
	// --- End Fly Control Methods

	// --- Start Point Control Methods
	public void convertFlyToPoint() {
		points += nbFly;
		nbFly = 0;
	}
	// --- End Point Control Methods
	// --- End Methods



}
