package test;

import static org.junit.Assert.assertEquals;


import java.util.ArrayList;
import org.easymock.EasyMock;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import network.facade.client.FacadeClient;
import network.facade.server.FacadeServer;
import network.listenercore.IGameClientAction;
import network.listenercore.IGameInformation;
import network.listenercore.IGameServerAction;
import network.tcp.controller.NetworkControllerTCPServer;
import network.tcp.server.ClientHandler;
import network.utilitaire.IpMachine;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestFacade {
	
	private static FacadeServer fs;
	private static FacadeClient fc;
	
	//Serveur
	private static IGameClientAction actionPlayer;
	//Client
	private static IGameServerAction actionServer;
	private static FacadeClient fc2;
	private static IGameServerAction actionServer2;
	private static FacadeClient fc3;
	private static IGameServerAction actionServer3;
	private static IGameInformation serverInformation;
	
	private int playerPort;
	private int playerPort2;
	
	@BeforeClass
	public static void setUp() {
		actionPlayer = EasyMock.createMock(IGameClientAction.class);
		serverInformation = EasyMock.createMock(IGameInformation.class);
		actionServer = EasyMock.createMock(IGameServerAction.class);
		actionServer2 = EasyMock.createMock(IGameServerAction.class);
		actionServer3 = EasyMock.createMock(IGameServerAction.class);
		
		fs = new FacadeServer(actionPlayer,serverInformation);
		fc = new FacadeClient(actionServer);
		fc2 = new FacadeClient(actionServer2);
		fc3 = new FacadeClient(actionServer3);
	}
	
	public void tempo() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
				//Ignore
		}
	}
	
	public void resetMock() {
		EasyMock.reset(actionServer);
		EasyMock.reset(actionServer2);
	}
//	
	public void resetMockPlayer() {	
		EasyMock.reset(actionPlayer);
	}
	
	public int getPortPlayer() {
		return Integer.parseInt(fc.getNc().getControllerTCP().getPortLocal());
	}
	
	public int getPortPlayer2() {
		return Integer.parseInt(fc2.getNc().getControllerTCP().getPortLocal());
	}
	
	@Test
	public void testAFacadeInit() {
		//ETAT START
		assertEquals(true,fs.getNc().getRunningUDP());
		assertEquals(false,fs.getNc().getRunningTCP());
		assertEquals(false,fc.getNc().getRunningTCP());
		assertEquals(false,fc.getNc().getRunningUDP());
		
		//SHUTDOWN AND START UDP--
		fs.getNc().downUDP();
		assertEquals(false,fs.getNc().getRunningUDP());
		fs.getNc().startUDP();
		assertEquals(true,fs.getNc().getRunningUDP());
		fc.getNc().startUDP();
		assertEquals(true,fs.getNc().getRunningUDP());
		fc.getNc().downUDP();
		assertEquals(false,fc.getNc().getRunningUDP());
		
		//SHUTDOWN AND START UDP
		fs.startNetworkNewGame(4502);
//		assertEquals(true,fs.getNc().getRunningTCP());
		assertEquals("4502",fs.getNc().getControllerTCP().getPortLocal());
		fs.downNetworkGame();
		assertEquals(false,fs.getNc().getRunningTCP());
		fs.startNetworkNewGame(4503);
		assertEquals(true,fs.getNc().getRunningTCP());
		assertEquals("4503",fs.getNc().getControllerTCP().getPortLocal());
	}
	
	@Test
	public void testBUDP() {
		//Test appel a la methode du igca quand reception recherche partie
		actionPlayer.playerSearchGame("TypePartie", "4");
		fc.rechercherPartie("TypePartie", 4);
		tempo();
		assertEquals("RP-TypePartie-4",fs.getNsv().getLastMsgUDP().getContenu());
		assertEquals(true,fc.getNc().getRunningUDP());
		
		//Annonce Partie
		IpMachine i = new IpMachine();
		String ip = i.getIpMachine();
		actionServer.serverAnnounceGame(ip ,"4503", "Partie", "5", "3", "2", "0","0", "wait");
		EasyMock.replay(actionServer);
		fs.announceGame(4503, "Partie", 5, 3, 2, 0, 0, "wait");
		tempo();
		EasyMock.verify(actionServer);
		assertEquals(ip,fc.getAdresseIPServer());
		assertEquals(false,fc.getNc().getRunningUDP());
		
		resetMock();
		resetMockPlayer();
		fc2.rechercherPartie("TypePartie", 4);
		tempo();
		assertEquals(true,fc2.getNc().getRunningUDP());
		
		//Annonce Partie
		IpMachine i2 = new IpMachine();
		String ip2 = i2.getIpMachine();
		actionServer2.serverAnnounceGame(ip2 ,"4503", "Partie", "5", "3", "2", "0","0", "wait");
		EasyMock.replay(actionServer2);
		fs.announceGame(4503, "Partie", 5, 3, 2, 0, 0, "wait");
		tempo();
		EasyMock.verify(actionServer2);
		assertEquals(ip2,fc2.getAdresseIPServer());
		assertEquals(false,fc2.getNc().getRunningUDP());
	}
	
	@Test
	public void testCTCP() {
		//ACCEPT PLAYER
		//TODO Voir reelement si je ne peut pas test l'appel a la methode joueurRequeteJoinGame car port client aleatoire
		/*
		
		assertEquals("ATTENTE",actionPlayer.getStatut());*/
		fc.requeteJoinGame("Jack", "PJV", "4521354", 4503);
		tempo();
		assertEquals("CP-Jack-PJV-4521354",fs.getNsv().getLastMsgTCP().getContenu());
		
		
		playerPort = getPortPlayer();
		assertEquals(0,fs.getNc().getNbClientConnected());
		assertEquals(1,fs.getNc().getNbClientWaiting());
		fs.acceptPlayer("4521354", playerPort);
		assertEquals(1,fs.getNc().getNbClientConnected());
		assertEquals(0,fs.getNc().getNbClientWaiting());
		assertEquals(playerPort,((NetworkControllerTCPServer) fs.getNc().getControllerTCP()).getOnlyThreadPool().getClientConnected(playerPort).getPortClient());
		
		//REFUSE PLAYER
		fc2.requeteJoinGame("Jacki", "PJV", "4521353", 4503);
		tempo();
		assertEquals("CP-Jacki-PJV-4521353",fs.getNsv().getLastMsgTCP().getContenu());
		playerPort2 = getPortPlayer2();
		assertEquals(1,fs.getNc().getNbClientConnected());
		assertEquals(1,fs.getNc().getNbClientWaiting());
		fs.refusePLayer("4521353", playerPort2);
		assertEquals(1,fs.getNc().getNbClientConnected());
		assertEquals(0,fs.getNc().getNbClientWaiting());
		tempo();
		assertEquals(false,fc2.getNc().getRunningTCP());
		
		//INIT 2 PLAYER
		fc2.requeteJoinGame("Jacki", "PJV", "4521353", 4503);
		tempo();
		playerPort2 = getPortPlayer2();
		assertEquals(1,fs.getNc().getNbClientConnected());
		assertEquals(1,fs.getNc().getNbClientWaiting());
		fs.acceptPlayer("4521353", playerPort2);
		assertEquals(2,fs.getNc().getNbClientConnected());
		assertEquals(0,fs.getNc().getNbClientWaiting());
		tempo();
		
		//VERIF BON PORT CLIENT
		ArrayList<ClientHandler> lc = ((NetworkControllerTCPServer) fs.getNc().getControllerTCP()).getOnlyThreadPool().getClientsConnectGame();
		assertEquals(2, lc.size());
		assertEquals(playerPort, lc.get(0).getPortClient());
		assertEquals(playerPort2, lc.get(1).getPortClient());
		
		fc3.requeteJoinLocalHost("Jacky", "PJV", "4521353", 4503);
		tempo();
		assertEquals(2,fs.getNc().getNbClientConnected());
		assertEquals(1,fs.getNc().getNbClientWaiting());
		((NetworkControllerTCPServer) fs.getNc().getControllerTCP()).getOnlyThreadPool().clearWaitConnection();
		assertEquals(2,fs.getNc().getNbClientConnected());
		assertEquals(0,fs.getNc().getNbClientWaiting());
		
		fc3.requeteJoinLocalHost("Jacky", "PJV", "4521353", 4503);
		tempo();
		assertEquals(2,fs.getNc().getNbClientConnected());
		assertEquals(1,fs.getNc().getNbClientWaiting());
		fc3.getNc().deconnectTCPClient();
		tempo();
		assertEquals(2,fs.getNc().getNbClientConnected());
		assertEquals(0,fs.getNc().getNbClientWaiting());
	}
	
	@Test
	public void testDPartieInit() {
		resetMock();
		actionServer.serverInitializeGame("Jack,Jacki", 123456798);
		actionServer2.serverInitializeGame("Jack,Jacki", 123456798);
		EasyMock.replay(actionServer);
		EasyMock.replay(actionServer2);
		fs.initializeGame("Jack,Jacki", 123456798);
		tempo();
		EasyMock.verify(actionServer);
		EasyMock.verify(actionServer2);
		tempo();
		/* TODO TEST PARTIE START REFUSE JOUEUR AUTO
		fc3.requeteJoinGame("Jacky", "PJV", "4521353", 4503);
		tempo();
		assertEquals(2,fs.getNc().getNbClientConnected());
		assertEquals(0,fs.getNc().getNbClientWaiting());
		*/
	}
	
	@Test
	public void testEDistributeHand() {
		playerPort = getPortPlayer();
		playerPort2 = getPortPlayer2();
		resetMock();
		actionServer.serverDistributeHand("Roi.14", 123456, 123456);
		actionServer2.serverDistributeHand("Dame.3", 123456, 123456);
		EasyMock.replay(actionServer);
		EasyMock.replay(actionServer2);
		tempo();
		fs.distributeHand("Roi.14", 123456, 123456, playerPort);
		fs.distributeHand("Dame.3", 123456, 123456, playerPort2);
		tempo();
		EasyMock.verify(actionServer);
		EasyMock.verify(actionServer2);
		tempo();
	}
	
	@Test
	public void testFMancheInit() {
		resetMock();
		actionServer.serverInitializeRound("Sens", 123456798, 159753456);
		actionServer2.serverInitializeRound("Sens", 123456798, 159753456);
		EasyMock.replay(actionServer);
		EasyMock.replay(actionServer2);
		fs.initializeRound("Sens", 123456798, 159753456);
		tempo();
		EasyMock.verify(actionServer);
		EasyMock.verify(actionServer2);
		tempo();
	}
	
	@Test
	public void testGTurnInit() {
		resetMock();
		actionServer.serverInitializeTurn("Jack", 40, 123456, 123456, 123456);
		actionServer2.serverInitializeTurn("Jack", 40, 123456, 123456, 123456);
		EasyMock.replay(actionServer);
		EasyMock.replay(actionServer2);
		fs.initializeTurn("Jack", 40, 123456, 123456, 123456);
		tempo();
		EasyMock.verify(actionServer);
		EasyMock.verify(actionServer2);
		tempo();
	}
	

	@Test
	public void testHDistributeCard() {
		playerPort = getPortPlayer();
		playerPort2 = getPortPlayer2();
		resetMock();
		actionServer.serverDistributeCard("Roi.14", 123, 123, 123);
		actionServer2.serverDistributeCard("Dame.5", 123, 123, 123);
		EasyMock.replay(actionServer);
		EasyMock.replay(actionServer2);
		fs.distributeCard("Roi.14", 123, 123,123, playerPort);
		fs.distributeCard("Dame.5", 123, 123, 123, playerPort2);
		tempo();
		EasyMock.verify(actionServer);
		EasyMock.verify(actionServer2);
	}
	
	@Test
	public void testIError() {
		playerPort = getPortPlayer();
		playerPort2 = getPortPlayer2();
		resetMock();
		actionServer.serverIndicateError("Erreur", 123, 123, 123);
		actionServer2.serverIndicateError("Erreur2", 123, 123, 123);
		EasyMock.replay(actionServer);
		EasyMock.replay(actionServer2);
		fs.indicateError("Erreur", 123, 123, 123, playerPort);
		fs.indicateError("Erreur2", 123, 123, 123, playerPort2);
		tempo();
		EasyMock.verify(actionServer);
		EasyMock.verify(actionServer2);
		tempo();
	}
	
	@Test
	public void testJActionPlayer() {
		resetMock();
		actionServer.serverInformPlayerAction("TR/Roi.14/SJI", 123, 123, 123);
		actionServer2.serverInformPlayerAction("TR/Roi.14/SJI", 123, 123, 123);
		EasyMock.replay(actionServer);
		EasyMock.replay(actionServer2);
		fs.informPlayerAction(true, "Roi.14", true, 123, 123, 123);
		tempo();
		EasyMock.verify(actionServer);
		EasyMock.verify(actionServer2);
		tempo();
		
		resetMock();
		actionServer.serverInformPlayerAction("TR/Roi.14", 123, 123, 123);
		actionServer2.serverInformPlayerAction("TR/Roi.14", 123, 123, 123);
		EasyMock.replay(actionServer);
		EasyMock.replay(actionServer2);
		fs.informPlayerAction(true, "Roi.14", false, 123, 123, 123);
		tempo();
		EasyMock.verify(actionServer);
		EasyMock.verify(actionServer2);
		tempo();
		
		resetMock();
		actionServer.serverInformPlayerAction("Roi.14/SJI",  123, 123, 123);
		actionServer2.serverInformPlayerAction("Roi.14/SJI",  123, 123, 123);
		EasyMock.replay(actionServer);
		EasyMock.replay(actionServer2);
		fs.informPlayerAction(false, "Roi.14", true, 123, 123, 123);
		tempo();
		EasyMock.verify(actionServer);
		EasyMock.verify(actionServer2);
		tempo();
		
		resetMock();
		actionServer.serverInformPlayerAction("Roi.14", 123, 123, 123);
		actionServer2.serverInformPlayerAction("Roi.14", 123, 123, 123);
		EasyMock.replay(actionServer);
		EasyMock.replay(actionServer2);
		fs.informPlayerAction(false, "Roi.14", false, 123, 123, 123);
		tempo();
		EasyMock.verify(actionServer);
		EasyMock.verify(actionServer2);
		tempo();
	}
	
	@Test
	public void testKEndRound() {
		 resetMock();
		actionServer.serverEndRound("120","100,120", 123, 123);
		actionServer2.serverEndRound("120","100,120", 123, 123);
		EasyMock.replay(actionServer);
		EasyMock.replay(actionServer2);
		fs.endRound("120","100,120", 123, 123);
		tempo();
		EasyMock.verify(actionServer);
		EasyMock.verify(actionServer2);
		tempo();
	}
	
	@Test
	public void testLEndGame() {
		 resetMock();
		actionServer.serverEndGame("Jack",123);
		actionServer2.serverEndGame("Jack",123);
		EasyMock.replay(actionServer);
		EasyMock.replay(actionServer2);
		fs.endGame("Jack",123);
		tempo();
		EasyMock.verify(actionServer);
		EasyMock.verify(actionServer2);
		tempo();
	}
	
	@Test
	public void testMRentreTroupeau() {
		playerPort = getPortPlayer();
		playerPort2 = getPortPlayer2();
		resetMockPlayer();
		actionPlayer.joueurRentreTroupeau("true", "123", "idm", "idt",""+playerPort);
		EasyMock.replay(actionPlayer);
		fc.rentreTroupeau("true", "123", "idm", "idt");
		tempo();
		EasyMock.verify(actionServer);
		EasyMock.verify(actionServer2);
		tempo();
	}
	
	
	@Test
	public void testNPlayCard() {
		playerPort = getPortPlayer();
		playerPort2 = getPortPlayer2();
		resetMockPlayer();
		actionPlayer.joueurAJouerCarte("Valet.7", "132","132", "132",""+playerPort2);
		EasyMock.replay(actionPlayer);
		fc2.jouerCarte("Valet.7", "132","132", "132");
		tempo();
		EasyMock.verify(actionServer);
		EasyMock.verify(actionServer2);
		tempo();
	}
	
	@Test
	public void testOEndGame() {
		playerPort = getPortPlayer();
		playerPort2 = getPortPlayer2();
		resetMockPlayer();
		actionPlayer.joueurInverseSensJeu("true", "123", "123", "123",""+playerPort2);
		EasyMock.replay(actionPlayer);
		fc2.inverseSensJeu("true", "123", "123", "123");
		tempo();
		EasyMock.verify(actionServer);
		EasyMock.verify(actionServer2);
		tempo();
	}
	
	@Test
	public void testPRestartGame() {
		resetMock();
		actionServer.serverRestartGame(123, 124);
		actionServer2.serverRestartGame(123, 124);
		EasyMock.replay(actionServer);
		EasyMock.replay(actionServer2);
		fs.restartGame(123, 124);
		tempo();
		EasyMock.verify(actionServer);
		EasyMock.verify(actionServer2);
		tempo();
	}
	
	@Test
	public void testQDecoPlayer() {
		resetMock();
		actionServer.serverAnnounceDeconnexion(123, 123, 123);
		actionServer2.serverAnnounceDeconnexion(123, 123, 123);
		EasyMock.replay(actionServer);
		EasyMock.replay(actionServer2);
		fs.announceDeconnexion(123, 123, 123);
		tempo();
		EasyMock.verify(actionServer);
		EasyMock.verify(actionServer2);
		tempo();
	}
	
	
	@Test
	public void testZEngGameSession() {
		resetMock();
		actionServer.serverEndGameSession(132);
		actionServer2.serverEndGameSession(132);
		EasyMock.replay(actionServer);
		EasyMock.replay(actionServer2);
		fs.endGameSession(132);
		tempo();
		EasyMock.verify(actionServer);
		EasyMock.verify(actionServer2);
		tempo();
		assertEquals(false,fs.getNc().getRunningTCP());	
	}
}
