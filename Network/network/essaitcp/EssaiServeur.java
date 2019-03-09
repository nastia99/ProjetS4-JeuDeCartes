package network.essaitcp;

import network.facade.server.FacadeServer;
import network.listenercore.IGameClientAction;
import network.listenercore.IGameInformation;

public class EssaiServeur implements Runnable {
	
	private static IGameClientAction igca = null;
	private static IGameInformation igi = null;
	
	public static void main (String[] args) throws Exception {
		
		FacadeServer network = new FacadeServer(igca,igi);
		Thread.sleep(1000);
		network.startNetworkNewGame(4502);
		Thread.sleep(10000);
	
		
		
  }	 
	
	@Override
	public void run() {
			try {
				main(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}	

}
