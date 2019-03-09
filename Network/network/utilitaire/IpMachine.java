package network.utilitaire;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IpMachine {
	
	 private String nomHote ;
     private String adresseIPLocale ;
 	private static Logger logger = Logger.getLogger(IpMachine.class.toString());
     
     public String getIpMachine(){
    	 
    	 try{
    	     	InetAddress inetadr = InetAddress.getLocalHost();
    	        adresseIPLocale = inetadr.getHostAddress();

    	     } catch (UnknownHostException e) {
    	    	 logger.log(Level.WARNING, "UnknownHost",e);
    	 
    	     }
    	 return adresseIPLocale;
     }
     
     public String getHostName(){
    	 
    	 try{
    	     InetAddress inetadr = InetAddress.getLocalHost();
    	     
    	        nomHote = inetadr.getHostName();

    	     } catch (UnknownHostException e2) {
    	    	 logger.log(Level.WARNING, "UnknownHost",e2);
    	     }
    	 return  nomHote;
     }
}

