package core.gameScript.game;

import java.util.*;

import core.gameScript.Exception.HandFullException;
import core.gameScript.cardGame.Card;

public class Game {

    // --- Start Constructor
    // --- Start New Game Constructor
    public Game() {
        generatePort();
        status = Status.ATTENTE;
        generateGameID();
        name = "GameG4B";
        players = new ArrayList<>();
        maxRealPlayer = 0;
        maxVirtualPlayer = 0;
        realPlayerNb = 0;
        virtualPlayerNb = 0;
        rounds = new ArrayList<>();
        currentRound = 0;
    }
    // --- End New Game Constructor

    // --- Start Restart Game Constructor
    public Game(int port, String name, List<Player> players, int maxRealPlayer, int maxVirtualPlayer, int realPlayerNb, int virtualPlayerNb) {
        this.port = port;
        status = Status.ATTENTE;
        generateGameID();
        this.name = name;
        this.players = players;
        this.maxRealPlayer = maxRealPlayer;
        this.maxVirtualPlayer = maxVirtualPlayer;
        this.realPlayerNb = realPlayerNb;
        this.virtualPlayerNb = virtualPlayerNb;
        rounds = new ArrayList<>();
        currentRound = 0;
    }
    // --- End Restart Game Constructor
    // --- End Constructor


    // --- Start Attributes
    // --- Start Identification Attributes
    private int port;
    private Status status;
    private int gameID;
    private String name;
    // --- End Identification Attributes

    // --- Start End Game Constant
    private static final int END_GAME_POINTS = 100;
    // --- End End Game Constant

    // --- Start Max Player Constant
    private static final int MAX_GAME_PLAYER = 5;
    // --- End Max Player Constant

    // --- Start Player Attributes
    private List<Player> players;
    private int maxRealPlayer;
    private int maxVirtualPlayer;
    private int realPlayerNb;
    private int virtualPlayerNb;
    // --- End Player Attributes

    // --- Start Round Attributes
    private List<Round> rounds;
    private int currentRound;
    // --- End Round Attributes
    // --- End Attributes


    // --- Start Methods
    // --- Start Getter & Setter
    // --- Start Getter
    public int getPort() {
        return port;
    }

    public Status getStatus() {
        return status;
    }

    public int getGameID() {
        return gameID;
    }

    public String getName() {
        return name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getMaxRealPlayer() {
        return maxRealPlayer;
    }

    public int getMaxVirtualPlayer() {
        return maxVirtualPlayer;
    }

    public int getRealPlayerNb() {
        return realPlayerNb;
    }

    public int getVirtualPlayerNb() {
        return virtualPlayerNb;
    }

    public int getCurrentRound() {
        return currentRound;
    }
    // --- End Getter

    // --- Start Setter
    public void setnbMaxJR(int n) {
        // TODO Remove
        //System.err.println("MAX PLAYER R "+n);
        this.maxRealPlayer = n;
    }

    public void setnbMaxJV(int n) {
        // TODO Remove
        //System.err.println("MAX PLAYER V "+n);
        this.maxVirtualPlayer = n;
    }
    // --- End Setter
    // --- End Getter & Setter

    // --- Start ID Generation
    private int generateRandomNb(int min, int max) {
        Random random = new Random();

        return random.nextInt((max - min) + 1) + min;
    }

    private void generatePort() {
        port = generateRandomNb(1024, 65535);
    }

    private void generateGameID() {
        gameID = generateRandomNb(0, Integer.MAX_VALUE -1);
    }
    // --- End ID Generation

    // --- Start Game Control Methods
    public void startGame() {
        status = Status.COMPLETE;
    }

    public boolean gameContinue() { // regarde si ce n'est pas la fin de la partie
        for (Player player : players) {
            if (player.getPoints() > END_GAME_POINTS) {
                return false;
            }
        }
        return true;
    }

    public void endGame() {
        status = Status.TERMINEE;
    }
    // --- End Game Control Methods

    // --- Start Round Control Methods
    public int getRoundID() {
        return rounds.get(currentRound).getRoundID();
    }

    public boolean getRoundDirection() {
        return rounds.get(currentRound).getDirection();
    }

    public int getFirstPlayerRound() {
        return rounds.get(currentRound).getFirstPlayer();
    }

    public void changeDirection() {
        rounds.get(currentRound).changeDirection();
    }

    public void nextPlayer() {
        rounds.get(currentRound).nextPlayer();
    }

    public void newRound() {
        Round round;
        round = new Round(players);
        rounds.add(round);
    }
    


    public List<String> getPlayersRoundScore() {
        return rounds.get(currentRound).getPlayersRoundScore();
    }

    public void endRound() {
        rounds.get(currentRound).endRound();
    }

    public void nextRound() { // cr�� une nouvelle manche et l'ajoute dans la liste des manches de la partie
        Round round;
        int newRoundID;
        int previousRoundPlayer;
        boolean previousDirection;

        previousRoundPlayer = rounds.get(currentRound).getCurrentPlayer();
        previousDirection = getRoundDirection();
        currentRound++;
        newRoundID = this.getCurrentRound() + 1;
        round = new Round(newRoundID, players, previousRoundPlayer, previousDirection);
        rounds.add(round);
       
    }
    // --- End Round Control Methods

    // --- Start Turn Control Methods
    public int getCurrentTurnID() {
        return rounds.get(currentRound).getTurnID();
    }

    public boolean turnHerdPickUp() {
        return rounds.get(currentRound).turnHerdPickUp();
    }

    public Card turnCardPlay() {
        return rounds.get(currentRound).turnCardPlay();
    }

    public boolean turnDirectionChange() {
        return rounds.get(currentRound).turnDirectionChange();
    }
    
	public void nextTurn() {
		rounds.get(currentRound).nextTurn();
	}
    // --- End Turn Control Methods

    // --- Start Player Control Methods
    public int incrementMaxRealPlayer() {
        if ((maxRealPlayer + maxVirtualPlayer) >= MAX_GAME_PLAYER) {
            return -1;
        }
        maxRealPlayer++;
        return maxRealPlayer;
    }

    public int decrementMaxRealPlayer() {
        if (maxRealPlayer <= 0) {
            return -1;
        }
        maxRealPlayer--;
        return maxRealPlayer;
    }

    public int incrementMaxVirtualPlayer() {
        if ((maxRealPlayer + maxVirtualPlayer) >= MAX_GAME_PLAYER) {
            return -1;
        }
        maxVirtualPlayer++;
        return maxVirtualPlayer;
    }

    public int decrementMaxVirtualPlayer() {
        if (maxVirtualPlayer <= 0) {
            return -1;
        }
        maxVirtualPlayer--;
        return maxVirtualPlayer;
    }

    public boolean canAcceptRealPlayer() {
        return realPlayerNb < maxRealPlayer;
    }

    public boolean canAcceptVirtualPlayer() {
        return virtualPlayerNb < maxVirtualPlayer;
    }

    public int addPlayer(int port, String name, String type) { // ajoute un joueur associ� a une game
        Player player;

        if (players.size() <= (maxRealPlayer + maxVirtualPlayer)) {
            player = new Player(port, name, type);
            if (type.equals("JR")) {
                if (realPlayerNb < maxRealPlayer) {
                    players.add(player);
                    realPlayerNb++;
                    updateStatus();
                    return players.indexOf(player);
                }
            }
            else if(type.equals("JV")){
                if (virtualPlayerNb < maxVirtualPlayer) {
                    players.add(player);
                    virtualPlayerNb++;
                    updateStatus();
                    return players.indexOf(player);
                }
            }
        }
        return -1;
    }

    public int getNbPlayer() {
        return maxRealPlayer + maxVirtualPlayer;
    }

    public List<String> getPlayersNames() {
        List<String> ps = new ArrayList<>();
        for (Player p : players) {
            ps.add(p.getName());
        }
        return ps;
    }

    public int getCurrentPlayerPort() {
        return rounds.get(currentRound).getCurrentPlayerPort();
    }

    public int getPlayerPort(int playerPosition) {
        return rounds.get(currentRound).getPlayerPort(playerPosition);
    }

    public int getCurrentPlayer() {
        return rounds.get(currentRound).getCurrentPlayer();
    }

    public List<String> getPlayersGameScore() {
        List<String> playersGameScore;

        playersGameScore = new ArrayList<>();
        for (Player player : players) {
            playersGameScore.add("" + player.getPoints());
        }
        return playersGameScore;
    }

    public String getWinner() {
        String winner;
        int winnerPoint;

        winner = players.get(0).getName();
        winnerPoint = players.get(0).getPoints();
        for(Player player : players) {
            if (player.getPoints() < winnerPoint) {
                winner = player.getName();
                winnerPoint = player.getPoints();
            }
        }
        return winner;
    }

    public List<Integer> getPlayerPointRanking() {
        List<Integer> playerPointRanking;

        playerPointRanking = new ArrayList<>();
        for (Player player : players) {
            playerPointRanking.add(player.getPoints());
        }
        Collections.sort(playerPointRanking);
        return playerPointRanking;
    }

    public List<String> getPlayerNameRanking() {
        List<String> playerNameRanking;
        List<Integer> playerPointRanking;

        playerNameRanking = new ArrayList<>();
        playerPointRanking = getPlayerPointRanking();
        for (int playerPoint : playerPointRanking) {
            for (Player player : players) {
                if (playerPoint == player.getPoints()) {
                    playerNameRanking.add(player.getName());
                    break;
                }
            }
        }
        return playerNameRanking;
    }
    // --- End Player Control Methods

    // --- Start Status Methods
    public String getStatusName() {
        return status.getText();
    }
    private void updateStatus() {
        if ((realPlayerNb >= maxRealPlayer) && (virtualPlayerNb >= maxVirtualPlayer)) {
            status = Status.COMPLETE;
        }
        else {
            status = Status.ATTENTE;
        }
    }
    // --- End Status Methods

    // --- Start Hand Control Methods
    public void givePlayerHand(int playerPosition, List<Card> playerHand) {
        rounds.get(currentRound).givePlayerHand(playerPosition, playerHand);
    }

    public boolean isInHandPlayer(Card card) {
        return rounds.get(currentRound).isInCurrentPlayerHand(card);
    }

    public void addCard(Card card) throws HandFullException {
        rounds.get(currentRound).currentPlayerAddCard(card);
    }

    public int playCard(Card card) {
        return rounds.get(currentRound).currentPlayerPlayCard(card);
    }
    // --- End Hand Control Methods

    // --- Start Herd Control Method
    public void pickUpHerd() {
        rounds.get(currentRound).currentPlayerPickUpHerd();
    }

    public boolean herdIsEmpty() {
        return rounds.get(currentRound).herdIsEmpty();
    }
    // --- End Herd Control Methods    
    // --- End Methods

	public void finalizeRoundPickUpHerdHand() {
		rounds.get(currentRound).finalizeRoundPickUpHerdHand();;
		
	}

	public void clearPlayers() {
		 generatePort();
	     status = Status.ATTENTE;
	     generateGameID();
	     name = "GameG4B";
	     players = new ArrayList<>();
	     realPlayerNb = 0;
	     virtualPlayerNb = 0;
	     rounds = new ArrayList<>();
	     currentRound = 0;
	}

}