package card;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// TODO Talk difference version 
public class Deck {
	// --- Start Attribute
	protected List<Card> cards;
	// --- End Attribute


	// --- Start Constructor
	public Deck() {
		cards = new ArrayList<>();
		remplir();
	}
	// --- End Constructor


	// --- Start Methods
	public boolean isEmpty() {
		return cards.isEmpty();
	}
	/**
	 *
	 * @param card
	 */
	public boolean isInDeck(String card) {
		Card c = Card.convertStringToCard(card);
		return cards.contains(c);
	}

	public int nbCardInDeck(){
		return cards.size();
	}

	public void resetDeck() {
		List<Card> toRemove = cards;
		cards.removeAll(toRemove);
		remplir();
	}

	public String pickCard(){
		Random rand = new Random();
		String s;
		Card c= cards.get(rand.nextInt(cards.size()));
		cards.remove(c);
		s=c.getNetworkCode();
		return s;
	}

	private void remplir(){
		for(int i=0; i<4;i++)
			switch (i) {
				case 0:
					for (int j = 1; j <=15; j++) {
						cards.add(new Classical(CardColor.V,Integer.toString(j)));
					}
					break;
				case 1:
					for (int j = 2; j <=14; j++) {
						cards.add(new Classical(CardColor.J,Integer.toString(j)));
					}
					break;
				case 2:
					for (int j = 3; j <=13; j++) {
						cards.add(new Classical(CardColor.O,Integer.toString(j)));
					}
					break;
				case 3:
					for (int j = 7; j < 10; j++) {
						cards.add(new Classical(CardColor.R,Integer.toString(j)));
					}
					break;
			}
		cards.add(new Classical(CardColor.S, "0"));
		cards.add(new Classical(CardColor.S, "16"));
		cards.add(new Acrobat(CardColor.S, "7"));
		cards.add(new Acrobat(CardColor.S, "9"));
		cards.add(new NoValue(CardColor.S, "1"));
		cards.add(new NoValue(CardColor.S, "2"));

	}
	// --- End Methods
}
