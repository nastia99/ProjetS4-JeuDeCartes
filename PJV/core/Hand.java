package core;

import java.util.ArrayList;
import java.util.List;

import card.Card;

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
	// --- Start Getter
	public List<T> getCards() {
		return cards;
	}
	// --- End Getter

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
			//cards.remove(cards);
			//cards.add(card);
		}
		cards.add(card);
	}
	
	public void removeCard(T card) {
		cards.remove(card);
	}
	
	public void clean() {
		cards.clear();
	}
	// --- End Hand Modification
	
	// --- Start Overrides
	@Override
	public String toString() {
		String str ="";
		for(Card c : cards) {
			str += c.toString() + ", ";
		}
		return str;
	}
	// --- End Overrides
	// --- End Methods


}