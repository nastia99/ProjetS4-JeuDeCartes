package network.listenercore;

public interface GameNotification {
	
    void announceGame(int port, String name, int maxPlayer, int maxRealPlayer, int maxVirtualPlayer, int realPlayerNb, int virtualPlayerNb, String status);
    void acceptPlayer(String connectionID, int playerPort);
    void refusePLayer(String connectionID, int playerPort);
    void initializeGame(String playerList, int gameID);
    void initializeRound(String gameDirection, int gameID, int roundID);
    void distributeHand(String playerHand, int gameID, int roundID, int playerPort);
    void initializeTurn(String firstPlayer, int deckSize, int gameID, int roundID, int turnID);
    void distributeCard(String playerCard, int gameID, int roundID, int turnID, int playerPort);
    void indicateError(String errorCode, int gameID, int roundID, int turnID, int playerPort);
    void informPlayerAction(boolean herdPickUp, String cardPlay, boolean reverseDirection, int gameID, int roundID, int turnID);
    void endRound(String playersScoreManche, String playersScoreActuel, int gameID, int roundID);
    void endGame(String winner, int gameID);
    void endGameSession(int gameID);
    void restartGame(int gameID, int newGameID);
    void startNetworkNewGame(int port);
    int downNetworkGame();
	void announceDeconnexion(int gameID, int roundID, int turnID);
	void shutdowNetwork();
	int shutdowNetworkTCP();
}

