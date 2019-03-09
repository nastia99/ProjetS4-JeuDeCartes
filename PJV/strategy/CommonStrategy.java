package strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import card.AICard;
import card.Acrobat;
import card.Card;
import card.CardColor;
import card.Classical;
import card.Deck;
import card.NoValue;
import core.AIManager;
import core.Game;
import core.Herd;
import core.Round;
import player.AIPlayer;

public class CommonStrategy {

	private AIPlayer ia;
	private Round round;

	public CommonStrategy(AIPlayer ia) {
		this.ia = ia;
	}
	
	public AICard chooseCardAleatoire(Herd h) {
		List<AICard> playablesCard = ia.getPlayableCards(h);
		int nbCartesJouables = playablesCard.size();// calculer nb carte en main jouable
		if (nbCartesJouables == 0) {
			return null;
		}
		
		Random r = new Random();
		int valeur = r.nextInt(nbCartesJouables); // choisir aléatoire entre 0 et nb
		
		AICard carteAJouer = playablesCard.get(valeur);
		return carteAJouer;// return la carte choisi aléatoirement
	}

	public Card playTheCard(Herd herd, Game game) {
		return chooseCardAleatoire(herd);
	}
}