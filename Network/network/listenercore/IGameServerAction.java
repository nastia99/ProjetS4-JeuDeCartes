package network.listenercore;

public interface IGameServerAction {
	
	public void announceDeconnexion(int gameID, int roundID, int turnID);
    void serverAcceptPlayer(String connectionID);
    void serverRefusePLayer(String connectionID);
    void serverInitializeGame(String playerList, int gameID);
    void serverInitializeRound(String gameDirection, int gameID, int roundID);
    void serverDistributeHand(String playerHand, int gameID, int roundID);
    void serverInitializeTurn(String string, int deckSize, int gameID, int roundID, int turnID);
    void serverDistributeCard(String playerCard, int gameID, int roundID, int turnID);
    void serverIndicateError(String errorCode, int gameID, int roundID, int turnID);
    void serverEndRound(String playersScoreManche, String playersScoreActuel, int gameID, int roundID);
    void serverEndGame(String winner, int gameID);
    void serverEndGameSession(int gameID);
    void serverRestartGame(int gameID, int newGameID);
	void serverAnnounceDeconnexion(int i, int j, int k);
	void serverAnnounceGame(String port, String ip, String name, String maxPlayer, String maxRealPlayer, String maxVirtualPlayer, String realPlayerNb, String virtualPlayerNb, String status);
	public void serverInformPlayerAction(String action, int gID, int rID, int tID);
}
