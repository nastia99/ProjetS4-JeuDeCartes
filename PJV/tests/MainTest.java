package tests;

import java.util.ArrayList;
import java.util.List;

import card.Card;
import core.AIManager;
import network.NetworkManager;
import player.AIPlayer;
import player.Player;

public class MainTest {

	public static void main(String[] args) {
		int gameID = 0;
		int roundID = 0;
		int turnID = 0;
		int deckSize = 48;
		NetworkManager nm = NetworkManager.getInstance();
		AIManager aiManager = AIManager.getInstance();

//		nm.searchGame("MIXTE", 3);
//		AIPlayer ai = aiManager.getAIPlayer();
		
//		nm.joinGame("GAME", "MIXTE", "35", 4502);
//		aiManager.start("BOT", true, false);
//		System.out.println("---------------------------");
//		List<Player> players = new ArrayList<>();
//		players.add(new Player(0, "Yann", "PJR"));
//		players.add(new Player(3, "BOT", "PJV"));
//		players.add(new Player(1, "Elise", "PJR"));
//		nm.serverInitializeGame("Yann,Bot,Elise", gameID);
//		nm.serverInitializeRound("DECROI", gameID, roundID);		
//		deckSize -= 15;
//		nm.serverInitializeTurn("0", deckSize, gameID, roundID, turnID);
//		turnID++;
//		deckSize--;
//		System.out.println("----->");
//		nm.serverInitializeTurn("0", deckSize, gameID, roundID, turnID);
//		turnID++;
//		deckSize--;
//		System.out.println("----->");
//		nm.serverInitializeTurn("0", deckSize, gameID, roundID, turnID);
//		nm.serverDistributeCard("V.7", gameID, roundID, turnID);
//		turnID++;
//		deckSize--;
//		System.out.println("----->");
//		nm.serverInitializeTurn("0", deckSize, gameID, roundID, turnID);
//		turnID++;
//		deckSize--;
//		System.out.println("----->");
//		nm.serverInitializeTurn("0", deckSize, gameID, roundID, turnID);
//		turnID++;
//		deckSize--;
//		System.out.println("----->");
//		nm.serverInitializeTurn("0", deckSize, gameID, roundID, turnID);
//		turnID++;
//		deckSize--;
//		nm.serverDistributeCard("V.7", gameID, roundID, turnID);
	}

}
