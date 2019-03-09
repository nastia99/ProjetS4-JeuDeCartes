package core.gameScript.game;

import core.gameScript.Exception.HandFullException;
import core.gameScript.cardGame.Card;

import java.util.ArrayList;
import java.util.List;

public class Hand<T extends Card> {

	// --- Start Constructor
	public Hand() { // liste de carte dans la main du joueur
		cards = new ArrayList<>(5);
	}
	// --- End Constructor


	// --- Start Attributes
	private List<T> cards; // liste des cartes pour un joueur
	// --- End Attributes


	// --- Start Methods
	// --- Start Getter & Setter
	// --- Start Getter
	public List<T> getCards() {
		return cards;
	}
	// --- End Getter

	// --- Start Setter
	public void setCards(List<T> playerHand) {
		cards = playerHand;
	}
	// --- End Setter
	// --- End Getter & Setter

	// --- Start Hand Control
	public boolean isInList(T card) { // regarde si la carte est dans une liste de carte pour un joueur
		return cards.contains(card);
	}
	// --- End Hand Control

	// --- Start Hand Modification
	public void playCard(T card) { // on enleve la carte de la lite de carte pour un joueur
		cards.remove(card);
	}

	public void addCard(T card) throws HandFullException { // on ajoute d'une carte dans une liste de carte pour un joueur
		if (cards.size() >= 5) {
			throw new HandFullException();
		}
		cards.add(card);
	}
	// --- End Hand Modification

	// --- Start Hand Information
	public int nbFliesInHand() {
		int nbFliesInHand;

		nbFliesInHand = 0;
		for(Card card : cards) {
			nbFliesInHand += card.getFly();
		}
		return nbFliesInHand;
	}
	// --- End Hand Information
	// --- End Methods

}