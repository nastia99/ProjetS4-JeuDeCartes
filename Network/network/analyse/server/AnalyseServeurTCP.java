package network.analyse.server;

import java.util.ArrayList;
import java.util.logging.Logger;
import network.analyse.AnalyseTCP;
import network.analyse.listener.TCPListener;
import network.analyse.listener.TCPPrintListener;
import network.analyse.utilitaire.Decoder;
import network.listenercore.IGameClientAction;
import network.tcp.server.ClientHandler;
import network.tcp.server.ThreadPoolServerTCP;
import network.viewnetwork.server.NetworkServerView;

public class AnalyseServeurTCP  extends AnalyseTCP implements TCPListener{
	
	private ThreadPoolServerTCP server;
	private Decoder d = new Decoder();
	private IGameClientAction igca;
	private static final Logger LOGGER = Logger.getLogger( AnalyseServeurTCP.class.getName() );

	public AnalyseServeurTCP(ThreadPoolServerTCP threadPoolServerTCP,NetworkServerView v, IGameClientAction igca) {
		this.server = threadPoolServerTCP;
		this.igca = igca;
		ajouterListenerPrintTCP((TCPPrintListener) v);
	}

	@Override
	public void messageRecuTcp(String message,int portClient) {
		ClientHandler ch = server.getAllClient(portClient);
		if(message.equals("GET_LATENCY")) {
			ch.sendMessageTCP("ACK_GET_LATENCY");
		}
		else {
			tcppl.afficherMessage(message,ch.getSocket(),false);
			ArrayList<String> valueData;
			valueData = d.splitTxt(message);
			
			switch (valueData.get(0)) {
			case "CP": 	notificationRejoindrePartie(valueData,""+portClient);
				break;
			case "RT": 	notificationJoueurTroupeau(valueData,""+portClient);
				break;
			case "JC": 	notificationJoueurCarte(valueData,""+portClient);
				break;
			case "ISJ": notificationJoueurSens(valueData,""+portClient);
				break;
			default: 	
				break;
		}
		}
		
		ch.setCompteurListen(ch.getCompteurListen() + 1);
	}

	private void notificationRejoindrePartie(ArrayList<String> valueData, String port) {
		
		String st = igca.getStatut();
		
		System.out.println("STATUT "+st);
			if(st.equals("ATTENTE")) {	
				
				igca.joueurRequeteJoinGame(valueData.get(1), valueData.get(2), valueData.get(3),port);
			}
			else {
				igca.refusePlayerAutomatic(valueData.get(1), valueData.get(2), valueData.get(3),port);
			}
		
	
		
	}
	
	private void notificationJoueurTroupeau(ArrayList<String> valueData, String port) {
			igca.joueurRentreTroupeau(valueData.get(1),valueData.get(2),valueData.get(3),valueData.get(4),port);
	}
	
	private void notificationJoueurCarte(ArrayList<String> valueData, String port) {
		igca.joueurAJouerCarte(valueData.get(1),valueData.get(2),valueData.get(3),valueData.get(4), port); 
	}

	private void notificationJoueurSens(ArrayList<String> valueData, String port) {
		igca.joueurInverseSensJeu(valueData.get(1),valueData.get(2),valueData.get(3),valueData.get(4),port);
	}

}
