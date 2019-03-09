package network.analyse.client;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import network.analyse.AnalyseTCP;
import network.analyse.listener.TCPListener;
import network.analyse.listener.TCPPrintListener;
import network.analyse.utilitaire.Decoder;
import network.listenercore.IGameServerAction;
import network.tcp.controller.NetworkControllerTCPClient;
import network.viewnetwork.View;


public class AnalyseClientTCP extends AnalyseTCP implements TCPListener{

	private static final Logger LOGGER = Logger.getLogger( AnalyseClientTCP.class.getName() );
	private NetworkControllerTCPClient nctcp;
	private final ExecutorService workers = Executors.newCachedThreadPool();
	private Decoder d = new Decoder();
	private IGameServerAction igsa;

	public AnalyseClientTCP(NetworkControllerTCPClient networkControllerTCPClient, View v, IGameServerAction igsa) {
		this.nctcp = networkControllerTCPClient;
		ajouterListenerPrintTCP((TCPPrintListener) v);
		this.igsa = igsa;
	}

	@Override
	public void messageRecuTcp(String message, int portServer) {
		workers.submit(() -> {
			if(message.equals("ACK_GET_LATENCY")) {
				nctcp.getService().get("Read").setWaitLatency(true);
			}
			else {
				ArrayList<String> valueData;
				valueData = d.splitTxt(message);
				switch (valueData.get(0)) {
				case "AC": 	notificationAcceptServer(valueData);
				break;
				case "RC": 	notificationRefuseServer(valueData);
				break;
				case "IP": 	notificationInitialisationGame(valueData);
				break;
				case "IM": 	notificationInitialisationRound(valueData);
				break;
				case "IT": 	notificationInitialisationTurn(valueData);
				break;
				case "DM": 	notificationDistribuerMainPlayer(valueData);
				break;
				case "FC": 	notificationCartePiocherPlayer(valueData);
				break;
				case "IE": 	notificationErreur(valueData);
				break;
				case "IAT": notificationInformerActionTurn(valueData);
				break;
				case "FM": 	notificationFinManche(valueData);
				break;
				case "FP": 	notificationFinPartie(valueData);
				break;
				case "TSJ": notificationFinGameSession(valueData);	
				break;
				case "RNSJ": notificationRestartGame(valueData);	
				break;
				case "DJ": 	notificationAnnonceDeconnexion(valueData);
				break;
				default: 	
					break;
				}
			}
			tcppl.afficherMessage(message);
		});
	}

	private void notificationRefuseServer(ArrayList<String> valueData) {
		igsa.serverRefusePLayer(valueData.get(1));
	}

	private void notificationAcceptServer(ArrayList<String> valueData) {
		igsa.serverAcceptPlayer(valueData.get(1));
	}

	private void notificationAnnonceDeconnexion(ArrayList<String> valueData) {
		int i1 = Integer.parseInt(valueData.get(1));
		int i2 = Integer.parseInt(valueData.get(2));
		int i3 = Integer.parseInt(valueData.get(3));
		igsa.serverAnnounceDeconnexion(i1,i2,i3);
	}

	private void notificationRestartGame(ArrayList<String> valueData) {
		int i1 = Integer.parseInt(valueData.get(1));
		int i2 = Integer.parseInt(valueData.get(2));
		igsa.serverRestartGame(i1, i2);
	}

	private void notificationFinGameSession(ArrayList<String> valueData) {
		int i1 = Integer.parseInt(valueData.get(1));
		igsa.serverEndGameSession(i1);
	}

	private void notificationFinPartie(ArrayList<String> valueData) {
		int i2 = Integer.parseInt(valueData.get(2));
		igsa.serverEndGame(valueData.get(1), i2);
	}

	private void notificationFinManche(ArrayList<String> valueData) {
		int i2 = Integer.parseInt(valueData.get(3));
		int i3 = Integer.parseInt(valueData.get(4));
		igsa.serverEndRound(valueData.get(1), valueData.get(2),i2, i3);
	}

	private void notificationInformerActionTurn(ArrayList<String> valueData) {
		igsa.serverInformPlayerAction(valueData.get(1), Integer.parseInt(valueData.get(2)), Integer.parseInt(valueData.get(3)), Integer.parseInt(valueData.get(3)));
	}

	private void notificationErreur(ArrayList<String> valueData) {
		int i2 = Integer.parseInt(valueData.get(2));
		int i3 = Integer.parseInt(valueData.get(3));
		int i4 = Integer.parseInt(valueData.get(4));
		igsa.serverIndicateError(valueData.get(1), i2,i3,i4);

	}

	private void notificationCartePiocherPlayer(ArrayList<String> valueData) {
		int i2 = Integer.parseInt(valueData.get(2));
		int i3 = Integer.parseInt(valueData.get(3));
		int i4 = Integer.parseInt(valueData.get(4));
		igsa.serverDistributeCard(valueData.get(1),i2,i3,i4);

	}

	private void notificationDistribuerMainPlayer(ArrayList<String> valueData) {
		int i2 = Integer.parseInt(valueData.get(2));
		int i3 = Integer.parseInt(valueData.get(3));
		igsa.serverDistributeHand(valueData.get(1),i2,i3);
	}

	private void notificationInitialisationTurn(ArrayList<String> valueData) {
		int i2 = Integer.parseInt(valueData.get(2));
		int i3 = Integer.parseInt(valueData.get(3));
		int i4 = Integer.parseInt(valueData.get(4));
		int i5 = Integer.parseInt(valueData.get(5));
		igsa.serverInitializeTurn(valueData.get(1),i2, i3, i4, i5);
	}

	private void notificationInitialisationRound(ArrayList<String> valueData) {
		igsa.serverInitializeRound(valueData.get(1), Integer.parseInt(valueData.get(2)), Integer.parseInt(valueData.get(3)));
	}

	private void notificationInitialisationGame(ArrayList<String> valueData) {
		igsa.serverInitializeGame(valueData.get(1), Integer.parseInt(valueData.get(2)));
	}

	public void setListenerPrint(TCPPrintListener tcpl) {
		ajouterListenerPrintTCP(tcpl);
	}

}
