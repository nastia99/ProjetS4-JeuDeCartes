package network.facade.client;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.analyse.client.AnalyseClientUDP;
import network.analyse.utilitaire.Encoder;
import network.essaitcp.Game;
import network.fabrique.FactoryController;
import network.facade.Facade;
import network.listenercore.ClientNotification;
import network.listenercore.IGameServerAction;
import network.networkengine.NetworkControllerClient;
import network.tcp.controller.NetworkControllerTCPClient;
import network.viewnetwork.ViewClient;


public class FacadeClient implements ClientNotification, Facade  {
	
	private FactoryController fse = new FactoryController();
	private NetworkControllerClient nc;
	private Object lock = new Object();
	private Game g = null;
	private ViewClient v;
	private AnalyseClientUDP acusp;
	private Encoder e = new Encoder();
	private IGameServerAction igsa;
	private static final Logger LOGGER = Logger.getLogger( FacadeClient.class.getName() );
	private String adresseIPServer = null;
	
	public FacadeClient(IGameServerAction igsa){
		setAcusp(new AnalyseClientUDP(getNc(), v, igsa));
		setNc((NetworkControllerClient) fse.getController("Client", this));
		v = getNc().getView();
		getAcusp().setNc(getNc());
		getAcusp().setV(v);
		this.igsa = igsa;
	}
	
	@Override
	public void rechercherPartie(String typePartie, int taillePartie) {
			getNc().startUDP();
			try {
				Thread.sleep(1000);
				String message = "RP-"+typePartie+"-"+taillePartie;
				getNc().getControllerUDP().sendMessage(message); 
			} catch (InterruptedException e2) {
				LOGGER.log(Level.SEVERE, "Interruption lors Recherche Partie", e2);
				Thread.currentThread().interrupt();
			}
			
	}
	
	public void arretClient() {
		getNc().deconnectTCPClient();
		getNc().downUDP();
	}
	
	public void stopRechercherPartie() {
		getNc().downUDP();
	}
	
	public Object getLock() {
		return lock;
	}

	public void setLock(Object lock) {
		this.lock = lock;
	}
	
	public ViewClient getVue() {
		return this.v;
	}
	

	public void requeteJoinLocalHost(String name, String type, String idConnection, int port) {
		getNc().connectTCPClient("localhost",port,igsa);	
		try {
			Thread.sleep(2000);
			((NetworkControllerTCPClient) getNc().getControllerTCP()).sendMessageTCP("CP-"+name+"-"+type+"-"+idConnection);
		} catch (InterruptedException e1) {
			LOGGER.log(Level.SEVERE, "Interruption lors Requete Join Game", e1);
			 Thread.currentThread().interrupt();
		}
	}
	
	@Override
	public void requeteJoinGame(String name, String type, String idConnection, int port) {
		getNc().connectTCPClient(this.getAdresseIPServer(),port,igsa);	
		try {
			Thread.sleep(2000);
			((NetworkControllerTCPClient) getNc().getControllerTCP()).sendMessageTCP("CP-"+name+"-"+type+"-"+idConnection);
		} catch (InterruptedException e1) {
			LOGGER.log(Level.SEVERE, "Interruption lors Requete Join Game", e1);
			 Thread.currentThread().interrupt();
		}
	}
	
	@Override
	public void rentreTroupeau(String ram, String idp, String idm, String idt) {
		ArrayList<String> list = new ArrayList<>();
		list.add("RT");
		list.add(ram);
		list.add(idp);
		list.add(idm);
		list.add(idt);
		String msg = e.splitTxt(list);
		getNc().sendTCP(msg);
	}
	@Override
	public void jouerCarte(String carteJouer, String uidPartie, String uidTour, String uidManche) {
		ArrayList<String> list = new ArrayList<>();
		list.add("JC");
		list.add(carteJouer);
		list.add(uidPartie);
		list.add(uidTour);
		list.add(uidManche);
		String msg = e.splitTxt(list);
		getNc().sendTCP(msg);
	}
	@Override
	public void inverseSensJeu(String inverserSensJeux, String uidPartie, String uidTour, String uidManche) {
		ArrayList<String> list = new ArrayList<>();
		list.add("ISJ");
		list.add(inverserSensJeux);
		list.add(uidPartie);
		list.add(uidTour);
		list.add(uidManche);
		String msg = e.splitTxt(list);
		getNc().sendTCP(msg);
	}


	public AnalyseClientUDP getAcusp() {
		return acusp;
	}

	public void setAcusp(AnalyseClientUDP acusp) {
		this.acusp = acusp;
	}

	public void setGame(Game game) {
		this.g = game;
	}

	public Game getGame() {
	return this.g;	
	}

	public NetworkControllerClient getNc() {
		return nc;
	}

	private void setNc(NetworkControllerClient nc) {
		this.nc = nc;
	}

	public String getAdresseIPServer() {
		return adresseIPServer;
	}

	public void setAdresseIPServer(String adresseIPServer) {
		this.adresseIPServer = adresseIPServer;
	}
}
