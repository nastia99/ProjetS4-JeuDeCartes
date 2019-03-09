package core.gameScript;

import core.gameScript.Exception.WrongNetworkCodeException;
import core.gameScript.cardGame.Card;
import core.gameScript.cardGame.Deck;
import java.util.*;

public class DealManager {

	// --- Start Singleton
	private static DealManager instance;

	private DealManager() {
		awake();
	}

	public static DealManager getInstance() {
		if (instance == null) {
			instance = new DealManager();
		}
		return instance;
	}
	// --- End Singleton


	// --- Start Attributes
	Deck deck;
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
		deck= new Deck();
	}
	// --- End Construct Method

	public String giveCard() {
		return deck.pickCard();
	}

	public List<String> giveAHand() {
		List<String> hand=new ArrayList<String>();

		for(int i=0;i<5;i++){
			hand.add(deck.pickCard());
		}
		return hand;
	}

	public boolean isEmpty(){
		return deck.isEmpty();
	}

	public boolean isInDeck(String card) throws WrongNetworkCodeException{
		return deck.isInDeck(card);
	}

	public int nbCardInDeck(){
		return deck.nbCardInDeck();
	}

	public boolean isSpecial(String networkCode) throws WrongNetworkCodeException{
		if(networkCode.equals(null)){
			return false;
		}
		Card c= Card.convertStringToCard(networkCode);
		return c.isSpecial();
	}


	public String getValue(String networkCode) throws WrongNetworkCodeException{
		Card c=Card.convertStringToCard(networkCode);
		return c.getValue();
	}

	public String getColor(String networkCode) throws WrongNetworkCodeException{
		Card c=Card.convertStringToCard(networkCode);
		return c.getColor();
	}


	public void reset(){
		deck.resetDeck();
	}
	// --- Start Overrides
	// --- End Overrides
	// --- End Methods
}