package network.udp;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.analyse.observer.ObserverUDP;

public class MulticastReceiver extends ObserverUDP implements Runnable, Multicast {
		
	  private int mcastPort = 0;
	  private InetAddress mcastAddr = null;
	  private InetAddress localHost = null;
	  private DatagramSocket dgramSocket = null;
	  private boolean running;
	  private MulticastSocket mSocket = null;
	  private Object lock = new Object();
	  private static Logger logger = Logger.getLogger(MulticastReceiver.class.toString());


	  public MulticastReceiver(int port, InetAddress addr) {
		  this.init(port, addr);
	}
	  
	  public Object getLock() {
		  return this.lock;
	  }
	  
	  private void init(int port, InetAddress addr) {

		  mcastPort = port;
		  mcastAddr = addr;
			
		  try {
		    	setLocalHost(InetAddress.getLocalHost());
		  } catch (UnknownHostException uhe) {
			  logger.log(Level.WARNING,"Problems identifying local host", uhe);
		    	System.exit(1);
		  }
		    
		  try {
			    setDgramSocket(new DatagramSocket());
			} catch (IOException ioe){
				 logger.log(Level.WARNING,"problems creating the datagram socket.",ioe);
			      System.exit(1);
			}		  
	  }

	@Override
	public void run() {

	    mSocket = null;

	    try {
	    	logger.log(Level.INFO,"Initialisation Reception multicast");
	    	mSocket = new MulticastSocket(mcastPort);
	    	mSocket.joinGroup(mcastAddr);
	    } catch(IOException ioe) {
	    	logger.log(Level.WARNING,"Probleme pour ouvrir le port du multicast",ioe);    
	    	System.exit(1);
	    }

	    DatagramPacket packet;
	    logger.log(Level.INFO,"Multicast Configurer");    

	    running = true;
	    
	    while (running) {
	    	
	    	
	      try {
	    	  byte[] buf = new byte[1024];
	    	  packet = new DatagramPacket(buf,buf.length);
	    	  logger.log(Level.INFO,"En Attente d'un packet");
	    	  
	    	  mSocket.receive(packet);
	    	  
	    	//  String messageS = new String(packet.getData(),0,packet.getLength()).trim();
	    	  byte[] b = packet.getData();
	    	  String messageS = new String(b,"UTF-8").trim();
	    	 // String messageS = (new DataInputStream(new ByteArrayInputStream(buf))).readUTF();
	    	  
	    	  this.udpl.messageRecuUdp(messageS);
	    	  String log = "Received multicast packet: "+ messageS + " from: " + packet.getAddress();
	    	  logger.log(Level.INFO,log);
/*
	    	  ByteArrayInputStream bistream = new ByteArrayInputStream(packet.getData());
	    	  DataInputStream dis = new DataInputStream(bistream);
	    	  String msg = dis.readLine().trim();
	    	  */
	    	  //dis.readUTF(); TODO
	    	 
	    	//  ObjectInputStream ois = new ObjectInputStream(bistream);
	    	//  String msg = (String) ois.readObject();
	   
	    	 
	    	
	       // ois.close();
	    	/*  dis.close();
	        bistream.close();*/
	      } catch(IOException ioe) {
	    	  logger.log(Level.WARNING,"Trouble reading multicast message",ioe); 

	      } 
	    }
	  }
	
	public void stopService() {
		this.running = false;
		try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			logger.log(Level.SEVERE, "Interruption attente stop service multicast reception", e1);
			Thread.currentThread().interrupt();
		}
		logger.log(Level.INFO, "Service listener multicast UDP down ....");
		 try {
				mSocket.leaveGroup(mcastAddr);
			} catch (IOException e) {
				logger.log(Level.INFO, "IOException", e);
			}
	}
	
	
	@Override
	public boolean getRunning() {
		return this.running;
	}

	private void setDgramSocket(DatagramSocket dgramSocket) {
		this.dgramSocket = dgramSocket;
	}

	public InetAddress getLocalHost() {
		return localHost;
	}

	private void setLocalHost(InetAddress localHost) {
		this.localHost = localHost;
	}
}
