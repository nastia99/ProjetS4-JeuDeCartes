package network.listenercore;

public interface IGameInformation {
	
	int getPort();
	String getName();
	int getNbPlayerMax();
	int getNbPlayerRMax();
	int getNbPlayerVMax();
	int getNbPlayerRConnect();
	int getNbPlayerVConnect();
	String getstatut();
	String getTypePartie();
	
}
