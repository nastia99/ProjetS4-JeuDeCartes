package network.udp;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MulticastPublisher implements Runnable, Multicast  {

	  private int mcastPort = 0;
	  private InetAddress mcastAddr = null;
	  private InetAddress localHost = null;
	  private DatagramSocket dgramSocket = null;
	  private boolean running = false;
	  private String message = null;
	  private static Logger logger = Logger.getLogger(MulticastPublisher.class.toString());
	  
	  public MulticastPublisher(int port, InetAddress addr, String msg) {
		  this.message = msg;
		  this.init(port, addr);
	  }
	  
	  private void init(int port, InetAddress addr) {
		  
		  running = true;
		  mcastPort = port;
		    mcastAddr = addr;
			
		    try {
		    	setLocalHost(InetAddress.getLocalHost());
		    } catch (UnknownHostException uhe) {
		    	logger.log(Level.WARNING, "Problems identifying local host", uhe);
		    	System.exit(1);
		    }
		    
		    try {
			      dgramSocket = new DatagramSocket();
			} catch (IOException ioe){
				logger.log(Level.WARNING, "problems creating the datagram socket", ioe);
			    System.exit(1);
			}
	  }
	  
	  
	public void run() {

	    DatagramPacket packet = null;
	  
	      try {
	  
	       /* 
	        ObjectOutputStream out = new ObjectOutputStream (bos);
	        out.writeObject(message);
	        out.flush();
	        out.close();
	        	   
	        packet = new DatagramPacket(bos.toByteArray(),bos.size(), mcastAddr,mcastPort);*/
	    	  
	    	/*  
	    	  new DataOutputStream(bos).write(message.getBytes());
	    	  byte[] contenu = bos.toByteArray();*/
	    	  ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    	  byte[] m = message.getBytes("UTF-8");
	    	  (new DataOutputStream(bos)).write(m);
	    	  bos.flush();
	    	  byte[] contenu = bos.toByteArray();
	    	  packet = new DatagramPacket(contenu,contenu.length, mcastAddr,mcastPort);
	   
	        dgramSocket.send(packet);
	        logger.log(Level.INFO, "sending multicast message");
	      } catch (IOException ioe) {
	    	logger.log(Level.SEVERE, "error sending multicast", ioe);
	        System.exit(1);
	      }
	      running = false;
	}


	private void setLocalHost(InetAddress localHost) {
		this.localHost = localHost;
	}

	@Override
	public void stopService() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getRunning() {
		return running;
	}

}

