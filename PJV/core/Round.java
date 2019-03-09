package core;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import card.Card;
import card.CardColor;
import player.Player;
import strategy.CommonStrategy;

public class Round {
		// --- Start Constructor	
		public Round(List<Player> players, boolean sens, int startPlayerNb, Game game) {
			this.players = players;
			initialePlayer = startPlayerNb;
			currentPlayer = initialePlayer;
			direction = sens;
			this.game = game;
		}
		// --- End Constructor

		// --- Start Attributes
		private static Logger logger = Logger.getLogger(Round.class.toString());
		
		private Game game;
		private int currentHerd;
		// --- Start Player Management Attributes
		private List<Player> players;
		private int initialePlayer;
		private int currentPlayer;
		private boolean direction;
		// --- End Player Management Attributes
		// --- End Attributes
		
		// --- Start Methods
		public void addTurn(int turnId) {
			currentHerd = turnId;
			if (AIManager.getInstance().isLog() || AIManager.getInstance().isVerbose()) {
				logger.log(Level.INFO, "CHECK TOUR : IA joue en " + game.getAIPosition() + " -- joueur actuel " + currentPlayer);
			}

			if (game.getAIPosition() == currentPlayer) {
				CommonStrategy strat = new CommonStrategy(AIManager.getInstance().getAIPlayer());
				Card c = strat.playTheCard(game.getCurrentHerd(), game);
				System.out.println(c + "\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
				if (c != null) {
					if (AIManager.getInstance().isLog()) {
						logger.log(Level.INFO, "Carte proposé : " + c.toString());
					}
					game.aiPlayerPlayed(c);
					AIManager.getInstance().getAIPlayer().getHand().getCards().remove(c);
					
					try { Thread.sleep(1000); } catch (Exception ex) { ex.printStackTrace(); }
					if (c.getColor().equals(CardColor.S)) {
						Random r = new Random();
						if (r.nextInt(10) < 5) {
							game.switchSens("OUI");
						}
						else {
							game.switchSens("NON");
						}
					}
//					game.takeHerd("NON");
				}
				else {
					if (AIManager.getInstance().isLog()) {
						logger.log(Level.INFO, "Demande de ramassé le troupeau");
					}
					game.takeHerd("OUI");
					try { Thread.sleep(1000); } catch (Exception ex) { ex.printStackTrace(); }
					if (game.getDeckSize() > 0) {
						if (AIManager.getInstance().isVerbose()) {
							logger.log(Level.INFO,"S'il reste encore des cartes pour jouer un tour \n Il en reste " + game.getDeckSize());
						}
						replay();
					}
				}
			}
			else {
				game.playerIs(currentPlayer);
			}
		}
		
		public void replay() {
			if (AIManager.getInstance().isVerbose()) {
				logger.log(Level.INFO,"----------------- REPLAYING ------------------");
			}
			CommonStrategy strat = new CommonStrategy(AIManager.getInstance().getAIPlayer());
			Card c = strat.playTheCard(game.getCurrentHerd(), game);
			if (AIManager.getInstance().isVerbose()) {
				logger.log(Level.INFO, "Carte proposé : " + c.toString());
			}
			game.aiPlayerPlayed(c);
			AIManager.getInstance().getAIPlayer().getHand().getCards().remove(c);
		}
		
		public void endTurn() {
			nextPlayer();
		}
		
		public void changeRotation() {
			direction = !direction;
		}
		
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
			
			if (AIManager.getInstance().isVerbose()) {
				logger.log(Level.INFO,"Prochain joueur : "+ players.get(currentPlayer).getName());
			}
		}
		
		public int getCurrentPlayer() {
			return currentPlayer;
		}
//		// --- End Methods
//

		public void setRotation(boolean b) {
			direction = b;
			
		}

		public void setPlayerStartRound(int currentPlayerId) {
			initialePlayer = currentPlayerId;
			currentPlayer = initialePlayer;
		}

}