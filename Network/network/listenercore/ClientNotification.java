package network.listenercore;

public interface ClientNotification {

	void inverseSensJeu(String inverserSensJeux, String uidPartie, String uidTour, String uidManche);

	void jouerCarte(String carteJouer, String uidPartie, String uidTour, String uidManche);

	void requeteJoinGame(String name, String type, String idConnection, int port);
	
	void rentreTroupeau(String ram, String idp, String idm, String idt);

	void rechercherPartie(String typePartie, int taillePartie);

}
