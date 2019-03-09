package player;

import java.util.ArrayList;
import java.util.List;

import card.AICard;
import card.Card;
import core.AIManager;
import core.Hand;
import core.HandFullException;
import core.Herd;
import core.Round;

public class AIPlayer {
	
	public AIPlayer(String name) {
		pseudo = name;
	}	
	
	// --- Start Attributes
	private String pseudo;
	private static final int ACCEPTABLE_HERD = 5;
	// --- Start Cards
	private AIManager aiManager;
	private Hand<AICard> hand;
	private List<AICard> playableCards;
	// --- End Cards 

	// --- End Attributes
	
	// --- Start Methods
	public void initialize() {
		aiManager = AIManager.getInstance();
		hand = new Hand<AICard>();
	}
	// --- Start Turn, Round, Game
	public void distributeHand(List<Card> cards) {
		for (Card c : cards) {
			AICard aiC = new AICard(c.getColor(), c.getNumber());
			try {
				hand.addCard(aiC);
			} catch (HandFullException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void startTurn() {
		
	}
	
	public void resetTurn(AICard card) {
		for (AICard c : hand.getCards()) {
			c.setAIValue(0);
		}
		playableCards.clear();
		try {
			hand.addCard(card);
		} catch (HandFullException e) {
			e.printStackTrace();
		}
	}
	// --- End Turn, Round, Game
	
	public AICard playBestCard() {
		AICard bestCard = new AICard(Integer.MIN_VALUE, null, null);
		for (AICard c : playableCards) {
			if (bestCard.getValue() < c.getValue()) {
				bestCard = c;
			}
		}
		return bestCard;
	}
	
	public void distributeCard(Card c) {
		try {
			hand.addCard(new AICard(c));
		} catch (HandFullException e) {
			e.printStackTrace();
		}
	}
	// --- End Methods
	// --- Start Getters
	public Hand<AICard> getHand() {
		return hand;
	}
	
	public List<AICard> getPlayableCards(Herd h){
		List<AICard> playables = new ArrayList<>();
		for (Card c : hand.getCards()) {
			if (h.isPlayable(c)) {
				playables.add(new AICard(c));
			}
		}
		return playables;
	}
	
	public String getName() {
		return pseudo;
	}
	
	public void clean() {
		hand.clean();
	}
	// --- End Getters

}