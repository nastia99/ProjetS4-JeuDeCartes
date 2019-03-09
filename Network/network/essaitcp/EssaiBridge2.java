package network.essaitcp;

import network.bridge.BridgeMulticast;
import network.bridge.SendUni;

public class EssaiBridge2 implements Runnable {


	public static void main (String[] args) throws Exception {
		
		SendUni su = new SendUni(8510,4710);
		
		while(true) {
			Thread.sleep(2000);
			su.sendUnicast("TEST");
		}
		
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
