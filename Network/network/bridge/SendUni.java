package network.bridge;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class SendUni {
	
	private static Logger logger = Logger.getLogger(SendUni.class.toString());
	private int portEnvoi;
	private  int portReception;
	protected final ExecutorService workers = Executors.newCachedThreadPool();
	final static String INET_ADDR = "224.7.7.7";
	final static int PORT = 7777;


	public SendUni(int port,int port2) {
		this.portEnvoi = port;
		this.portReception = port2;
	
			startUnicastR();
	}

	private void startUnicastR() {
		
		workers.submit(new Runnable() {
			
			static final int LONG_TAMPON = 1024;
		  

			  public void run(){
				  try {
				  byte tampon[] = new byte[LONG_TAMPON];

		            DatagramPacket paquet =
		                    new DatagramPacket(tampon, 0, LONG_TAMPON);

		            DatagramSocket socket = new DatagramSocket(portReception);

		            while (true) {
		                socket.receive(paquet);
		            
		                String chaine = new String(paquet.getData(),
		                        paquet.getOffset(), paquet.getLength() );

		                System.out.println("Message: " + chaine);
		                System.out.println("Recu de " + paquet.getSocketAddress());
		                System.out.println();
		                
		            }
		        } catch( Exception e) {
		            System.err.println("Houston we have a problem");
		            e.printStackTrace();
		            System.exit(1);
		        }
			  }});
	}
	
	public void sendUnicast(String chaine) {
		final String DESTINATION = "127.0.0.1";
	    final int DELAI = 2000;
		 try {
	            byte tampon[] = chaine.getBytes();
	            
	            InetAddress adresse = InetAddress.getByName(DESTINATION);         

	            DatagramPacket paquet =
	                    new DatagramPacket(tampon, 0, tampon.length, adresse, portEnvoi);
	         
	            DatagramSocket socket = new DatagramSocket();
	         
	            System.out.println("Send Unicast message: " + chaine);
	            socket.send(paquet);
	            
	         
	        } catch (Exception e) {
	            System.err.println("Houston we have a problem");
	            e.printStackTrace();
	            System.exit(1);
	        }
		
	}
	

}
