package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.AIManager;
import player.Player;


public class AIManagerTest {

	AIManager aiManager;
	
	@BeforeEach
	void setUp() throws Exception {
		aiManager = AIManager.getInstance();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testFindAI() {
		List<Player> players = new ArrayList<>();
		players.add(new Player(0, "Yann", "PJR"));
		players.add(new Player(3, "Bot", "PJV"));
		players.add(new Player(1, "Elise", "PJR"));
		aiManager.initializeGame(players, 2);
		assertEquals(aiManager.getAIPosition(), 2);
	}
}
