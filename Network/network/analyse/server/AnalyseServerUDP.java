package network.analyse.server;


import java.util.ArrayList;
import java.util.logging.Logger;
import network.analyse.AnalyseUDP;
import network.analyse.utilitaire.Decoder;
import network.listenercore.IGameClientAction;
import network.listenercore.IGameInformation;
import network.networkengine.NetworkControllerServer;

public class AnalyseServerUDP extends AnalyseUDP {
	
	private static Logger logger = Logger.getLogger(AnalyseServerUDP.class.toString());
	private NetworkControllerServer ncs; 
	private Decoder d = new Decoder();
	private IGameClientAction igca;
	private IGameInformation igi;
	
	public AnalyseServerUDP(NetworkControllerServer networkControllerServer, IGameClientAction igca, IGameInformation igi) {
		this.ncs = networkControllerServer;
		this.igca = igca;
		this.igi = igi;
	}

	@Override
	public void messageRecuUdp(String message) {
		ArrayList<String> valueData;
		valueData = d.splitTxt(message);
		this.udppl.printUDP(message,""+ncs.getControllerUDP().getAddr(),""+ncs.getControllerUDP().getPort());
		/*
		if(valueData.get(0).equals("RP") ) {
			notificationRecherchePartie(valueData);
		}*/
		
		System.out.println(message);
		if(igi.getstatut() == null) {
			
		}
		else {
			if(valueData.get(0).equals("RP") && igi.getstatut().equals("ATTENTE")) {
				System.out.println("STATUT :" +igi.getstatut());
				notificationRecherchePartie(valueData);
			}
			else if(valueData.get(0).equals("RP") && !igi.getstatut().equals("ATTENTE")) {
				System.out.println("STATUT :" +igi.getstatut());
				annoncePartieAutomatic(valueData);
			}
		}
		
	}


	private void annoncePartieAutomatic(ArrayList<String> valueData) {
		if(igi.getTypePartie().equals(valueData.get(1)) && igi.getNbPlayerMax() == Integer.parseInt(valueData.get(2))) {
			ncs.getFacade().announceGame(igi.getPort(), igi.getName(), igi.getNbPlayerMax(),igi.getNbPlayerRMax() ,igi.getNbPlayerVMax(), 
			igi.getNbPlayerRConnect(), igi.getNbPlayerVConnect(), igi.getstatut());
		}
	}

	private void notificationRecherchePartie(ArrayList<String> valueData) {
			igca.playerSearchGame(valueData.get(1), valueData.get(2));
	}

	public void setNc(NetworkControllerServer nc) {
		this.ncs = nc;
	}

}
