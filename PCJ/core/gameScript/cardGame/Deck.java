package core.gameScript.cardGame;

		import java.util.*;

import core.gameScript.Exception.WrongNetworkCodeException;

public class Deck {
	// --- Start Attribute
	private List<Card> cards;
	private CardFactory cardFactory;
	// --- End Attribute


	// --- Start Constructor
	public Deck() {
		cards = new ArrayList<>();
		cardFactory=new CardFactory();
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
	 * @throws WrongNetworkCodeException 
	 */
	public boolean isInDeck(String card) throws WrongNetworkCodeException {
		Card c=Card.convertStringToCard(card);
		return cards.contains(c);
	}

	public int nbCardInDeck(){
		return cards.size();
	}

	public void resetDeck() {
		List<Card> toRemove= cards;
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
						cards.add(cardFactory.createCard("ZeroFlieClassical",j));
					}
					break;
				case 1:
					for (int j = 2; j <=14; j++) {
						cards.add(cardFactory.createCard("OneFlieClassical",j));
					}
					break;
				case 2:
					for (int j = 3; j <=13; j++) {
						cards.add(cardFactory.createCard("TwoFlieClassical",j));
					}
					break;
				case 3:
					for (int j = 7; j < 10; j++) {
						cards.add(cardFactory.createCard("ThreeFlieClassical",j));
					}
					break;
			}
		cards.add(cardFactory.createCard("Border",0));
		cards.add(cardFactory.createCard("Border",16));
		cards.add(cardFactory.createCard("Acrobat",7));
		cards.add(cardFactory.createCard("Acrobat",9));
		cards.add(cardFactory.createCard("NoValue",1));
		cards.add(cardFactory.createCard("NoValue",2));

	}
	// --- End Methods
}
