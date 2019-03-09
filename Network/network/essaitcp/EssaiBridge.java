package network.essaitcp;

import network.bridge.BridgeMulticast;

public class EssaiBridge implements Runnable {

	public static void main (String[] args) throws Exception {
		
		BridgeMulticast bm = new BridgeMulticast(4710,8510);
	
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
