package core.gameScript;

import java.util.ArrayList;
import java.util.List;

import core.gameScript.Exception.HandFullException;
import core.gameScript.Exception.WrongNetworkCodeException;
import core.gameScript.cardGame.Card;
import core.gameScript.game.Game;
import core.gameScript.game.Player;

public class GameManager {

	// --- Start Singleton
	private static GameManager instance;

	private GameManager() {
		awake();
	}

	public static GameManager getInstance() {
		if (instance == null) {
			instance = new GameManager();
		}
		return instance;
	}
	// --- End Singleton


    // --- Start Attributes
	private Game game;
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

    }
    // --- End Construct Method

	// --- Start Player Control Methods
    public int addPlayer(int playerPort, String playerName, String playerType) {
        return game.addPlayer(playerPort, playerName, playerType);
    }

	public void deletePlayer(String namePlayer) {
		//game.deletePlayer(namePlayer); // ?
	}

	public boolean canAcceptRealPlayer() {
    	return game.canAcceptRealPlayer();
	}

	public boolean canAcceptVirtualPlayer() {
    	return game.canAcceptVirtualPlayer();
	}

	public List<String> getPlayersNames() {
		return game.getPlayersNames();
	}

	public int getPlayerPort() {
        return game.getCurrentPlayerPort();
    }

    public int getPlayerPort(int playerPosition) {
        return game.getPlayerPort(playerPosition);
    }

    public int getCurrentPlayer() {
        return game.getCurrentPlayer();
    }

	public String getWinner() {
        return game.getWinner();
    }

	public List<Integer> getPlayerPointRanking() {
		return game.getPlayerPointRanking();
	}

	public List<String> getPlayerNameRanking() {
		return game.getPlayerNameRanking();
	}
    // --- End Player Control Methods

	// --- Start Hand Control Methods
    public void givePlayerHand(int playerPosition, List<String> playerHand) throws WrongNetworkCodeException {
        List<Card> playerCards;

        playerCards = new ArrayList<>();
        for (String card : playerHand) {
            playerCards.add(Card.convertStringToCard(card));
        }
        game.givePlayerHand(playerPosition, playerCards);
    }

	public boolean isInPlayerHand(String networkCode) throws WrongNetworkCodeException {
    	return game.isInHandPlayer(Card.convertStringToCard(networkCode));
	}

	public void addCard(String networkCode) throws HandFullException, WrongNetworkCodeException {
    	game.addCard(Card.convertStringToCard(networkCode));
	}

	public int playCard(String networkCode) throws WrongNetworkCodeException {
    	return game.playCard(Card.convertStringToCard(networkCode));
	}
	// --- End Hand Control Methods

	// --- Start Game Control Methods
	public int getPort(){
		return game.getPort();
	}

	public int getGameID() {
    	return game.getGameID();
	}

	public String getName(){
		return game.getName();
	}

	public int getNbPlayer() {
		System.out.println(game);
    	return game.getNbPlayer();
	}

	public int getMaxVirtualPlayer(){
		return game.getMaxVirtualPlayer();
	}

	public int getMaxRealPlayer(){
		return game.getMaxRealPlayer();
	}

	public int getRealPlayer(){
		return game.getRealPlayerNb();
	}

	public int getVirtualPlayer(){
		return game.getVirtualPlayerNb();
	}

	public String getStatus() {
    	if (game == null) {
    		return null;
		}
    	return game.getStatusName();
	}

	public void createGame() {
    	game = new Game();
	}

	public void startGame() {
    	game.startGame();
	}

	public boolean gameContinue() {
    	return game.gameContinue();
	}

    public List<String> getPlayersGameScore() {
        return game.getPlayersGameScore();
    }

    public void endGame() {
    	game.endGame();
	}

	public void clearGame() {
// TODO Remove probably
    	game = new Game();
	}

	public void restartGame() {
		int port;
		String name;
		List<Player> players;
		int maxRealPlayer;
		int maxVirtualPlayer;
		int realPlayerNb;
		int virtualPlayerNb;

		port = game.getPort();
		name = game.getName();
		players = game.getPlayers();
		maxRealPlayer = game.getMaxRealPlayer();
		maxVirtualPlayer = game.getMaxVirtualPlayer();
		realPlayerNb = game.getRealPlayerNb();
		virtualPlayerNb = game.getVirtualPlayerNb();
		DealManager.getInstance().reset();
		for(int i=0; i<players.size(); i++ ) {
			players.get(i).resetFly();
			players.get(i).resetPoint();
		}
		game = new Game(port, name, players, maxRealPlayer, maxVirtualPlayer, realPlayerNb, virtualPlayerNb);
	}
	// --- End Game Control Methods

	// --- Start Round Control Methods
	public void newRound() {
    	game.newRound();
	}
	
	public int getRoundID() {
        return game.getRoundID();
    }

	public int reverseDirection() {
        return game.getRoundID();
    }

    public int getFirstPlayerRound() {
        return game.getFirstPlayerRound();
    }

	public boolean getGameDirection() {
        return game.getRoundDirection();
    }

    public List<String> getPlayersRoundScore() {
        return game.getPlayersRoundScore();
    }

    public void changeDirection() {
        game.changeDirection();
    }

    public void endRound() {
        game.endRound();
    }

    public void nextRound() {
        game.nextRound();
    }
	// --- End Round Control Methods

    // --- Start Turn Control Methods
    public int getTurnID() {
        return game.getCurrentTurnID();
    }

    public boolean turnHerdPickUp() {
        return game.turnHerdPickUp();
    }

    public String turnCardPlay() {
        Card card;
        card = game.turnCardPlay();
        if(card == null) {
        	return null;
        }
        return card.getNetworkCode();
    }

    public void nextTurn() {
    	EventManager.getInstance().resetCurrentPlayer(getCurrentPlayer());
        game.nextTurn();
    }

    public boolean turnDirectionChange() {
        return game.turnDirectionChange();
    }
    // --- End Turn Control Methods

    // --- Start Herd Control Method
    public void pickUpHerd() {
        game.pickUpHerd();
    }

    public boolean herdIsEmpty() {
        return game.herdIsEmpty();
    }
    // --- End Herd Control Method
    // --- End Methods

	public int incrementMaxRealPlayer() {
    	return game.incrementMaxRealPlayer();
	}

	public int decrementMaxRealPlayer() {
    	return game.decrementMaxRealPlayer();
	}

	public int incrementMaxVirtualPlayer() {
		return game.incrementMaxVirtualPlayer();
	}

	public int decrementMaxVirtualPlayer() {
		return game.decrementMaxVirtualPlayer();
	}

	public void finalizeRoundPickUpHerdHand() {
		game.finalizeRoundPickUpHerdHand();	
	}

	public void newRoundP() {
		
	}

	public void newNextRound() {
		game.newRound();
	}

	public void clearPlayersGameLobby() {
		game.clearPlayers();
		
	}

	

}