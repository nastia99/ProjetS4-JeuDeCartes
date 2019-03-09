package network.bridge;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BridgeMulticast {
	
	private static Logger logger = Logger.getLogger(BridgeMulticast.class.toString());
	private int portEnvoi;
	private  int portReception;
	protected final ExecutorService workers = Executors.newCachedThreadPool();
	final static String INET_ADDR = "224.7.7.7";
	final static int PORT = 7777;


	public BridgeMulticast(int port,int port2) {
		this.portEnvoi = port;
		this.portReception = port2;
	
			startUnicastR();
			startUDPM();
	}

	private void startUDPM() {
		workers.submit(new Runnable() {

			  private InetAddress localHost;

			public void run(){ 
				  InetAddress address;
				
				 
				  try {

				      localHost = InetAddress.getLocalHost();

				    } catch (UnknownHostException uhe) {

				      System.out.println("Problems identifying local host");

				      uhe.printStackTrace();  System.exit(1);

				    }
				  
				  try (MulticastSocket clientSocket = new MulticastSocket(PORT)){
					  address = InetAddress.getByName(INET_ADDR);
					  clientSocket.joinGroup(address);
			
				              while (true) {
	
				            	  byte[] buf = new byte[1024];
				    	    	  DatagramPacket packet = new DatagramPacket(buf,buf.length);
				    	    	  logger.log(Level.INFO,"En Attente d'un packet");
				    	    	  
				    	    	  clientSocket.receive(packet);
				    	    	  ByteArrayInputStream bis = new ByteArrayInputStream(buf);
				    	    	  DataInputStream ds = new DataInputStream(bis);
				    	    	  String messageS = ds.readUTF();
				    	    	  
				                  if (!(packet.getAddress().equals(localHost))) {
				                	  String log = "Received multicast packet: "+ messageS + " from: " + packet.getAddress();
					    	    	  logger.log(Level.INFO,log);
					                  sendUnicast(messageS);
				                    } 
				                  ds.close();
				                  bis.close();
				                
				              }
				              
				          } catch (IOException ex) {
				        	
				              ex.printStackTrace();
			
				          }
			
				      }
	  
		});
		
	}

	private void startUnicastR() {
	
		workers.submit(new Runnable() {
			
			static final int LONG_TAMPON = 1024;
			DatagramSocket socket = null;

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
		                sendMulticast(chaine);
		            }
		            
		        } catch( Exception e) {
		        	this.socket.close();
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
	
	public void sendMulticast(String message) {
				          InetAddress addr;
				          DatagramSocket serverSocket = null;
						try {
							serverSocket = new DatagramSocket();
						} catch (SocketException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						 DatagramPacket packet = null;
						  
					      try {
					  
					    	  addr = InetAddress.getByName(INET_ADDR);
					    	  
					    	  ByteArrayOutputStream bos = new ByteArrayOutputStream();
					    	  (new DataOutputStream(bos)).writeUTF(message);
					    	  byte[] contenu = bos.toByteArray();
					    	  packet = new DatagramPacket(contenu,contenu.length, addr,PORT);
					   
					        serverSocket.send(packet);
					        logger.log(Level.INFO, "sending multicast message");
					      } catch (IOException ioe) {
					    	logger.log(Level.SEVERE, "error sending multicast", ioe);
					        System.exit(1);
					      }
	}
	
	

	
}
