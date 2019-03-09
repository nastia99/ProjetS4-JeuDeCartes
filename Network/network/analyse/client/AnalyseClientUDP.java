package network.analyse.client;

import java.util.ArrayList;
import java.util.logging.Logger;

import network.analyse.AnalyseUDP;
import network.analyse.utilitaire.Decoder;
import network.essaitcp.Game;
import network.listenercore.IGameServerAction;
import network.networkengine.NetworkController;
import network.networkengine.NetworkControllerClient;
import network.viewnetwork.ViewClient;

public class AnalyseClientUDP extends AnalyseUDP {
	
	private NetworkControllerClient ncc;
	private ViewClient v;
	private Decoder d = new Decoder();
	private static Logger logger = Logger.getLogger(AnalyseClientUDP.class.toString());
	private IGameServerAction igsa;

	public AnalyseClientUDP(NetworkController nc, ViewClient v, IGameServerAction igsa) {
		this.ncc = (NetworkControllerClient) nc;
		this.v =v;
		this.igsa = igsa;
	}
	
	@Override
	public void messageRecuUdp(String message) {
		System.out.print("MESSAGE ANALYSEUR UDP :"+message);
						ArrayList<String> valueData;
						valueData = d.splitTxt(message);
						if(valueData.get(0).equals("AP")) {
							setGame(valueData); ncc.downUDP(); notificationAnnoncePartie(valueData);
						}
						v.printMessageUDP(message);
					}

	private void notificationAnnoncePartie(ArrayList<String> valueData) {
		ncc.getFacade().setAdresseIPServer(valueData.get(1));
		igsa.serverAnnounceGame(valueData.get(1), valueData.get(2), valueData.get(3), valueData.get(4), 
				valueData.get(5), valueData.get(6), valueData.get(7), valueData.get(8),valueData.get(9));
	}

	public void setV(ViewClient v2) {
		this.v = v2;
	}

	public void setNc(NetworkControllerClient nc) {
		this.ncc =nc;
	}
	
	private void setGame(ArrayList<String> gl) {
		String addrIP = gl.get(1);
		int port = Integer.parseInt(gl.get(2));
		String name=gl.get(3);
		int nbPlayerMax =  Integer.parseInt(gl.get(4));
		int nbPlayerRMax = Integer.parseInt(gl.get(5));
		int nbPlayerVMax = Integer.parseInt(gl.get(6));
		ncc.getFacade().setGame(new Game(addrIP,port, name,  nbPlayerMax,nbPlayerRMax,nbPlayerVMax)) ;
		v.setGamelbl(ncc.getFacade().getGame().getAddrIP()+":"+ncc.getFacade().getGame().getPort()+" "+ncc.getFacade().getGame().getNbPlayerMax()+" JMax");
	}
}
