package network.tcp;



import java.io.IOException;
import java.io.PrintStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import network.analyse.AnalyseTCP;
import network.analyse.observer.ObserverTCP;



public class HandleMessage extends ObserverTCP {
	
	private final HashMap<Integer,String> bufferInput = new HashMap<>();
	private final HashMap<Integer,String> bufferOutput = new HashMap<>();
	private final Map<Integer,String> synBufferInput = Collections.synchronizedMap(bufferInput);
	private final Map<Integer,String> synBufferOutput = Collections.synchronizedMap(bufferOutput);
	private final ExecutorService workers = Executors.newCachedThreadPool();
	private boolean runningSend = true;
	private int compteurSend = 0; 
	private Sender client; 
	private PrintStream out;
	private final Object lock = new Object();
	private static final Logger LOGGER = Logger.getLogger(HandleMessage.class.getName());
	
	
	public HandleMessage(Sender clientHandler) {
		this.client = clientHandler;
	}
	
	public void setListener(AnalyseTCP atcp) {
		this.ajouterListenerTCP(atcp);
	}

	private int getCompteurSend() {
		return compteurSend;
	}

	private Sender getClient() {
		return this.client;
	}

	private Map<Integer,String> getSynBufferInput(){
		return this.synBufferInput;
	}
	
	private HashMap<Integer,String> getBufferOutput(){
		return this.bufferOutput;
	}
	
	private Map<Integer,String> getSynBufferOutput(){
		return this.synBufferOutput;
	}
	
	public void arretBuffer() {
		this.runningSend = false;
	
		
		workers.shutdown();
		workers.shutdownNow();
		
		if(out != null) {
			out.close();
		}
	}

	public void addBuffer(String message) {
		workers.submit(() -> {
					synchronized(getSynBufferOutput()) {
						getSynBufferOutput().put(getCompteurSend(), message);
						}
					
					synchronized(lock) {
						lock.notifyAll();

					}});
	}
	
	public void addBufferReceiver(String message) {
		workers.submit(() -> {
					synchronized(getSynBufferInput()) {
						tcpl.messageRecuTcp(message,client.getSocket().getPort());
						}
					});
	}
	
	public void send() {
		Map<Integer, String> map = Collections.synchronizedMap(getBufferOutput());
		Set<Entry<Integer, String>> set = map.entrySet();

		synchronized(map) {
			Iterator<Entry<Integer, String>> itr = set.iterator();
			try {
				out = new PrintStream(getClient().getSocket().getOutputStream());
			} catch (IOException e1) {
				LOGGER.log(Level.INFO, "Socket close : "+e1.toString());
			}

			while(itr.hasNext()) {
				Entry<Integer, String> me = itr.next();
				LOGGER.log(Level.INFO, "Message "+getClient().getSocket().getLocalPort()+" envoi  "+me.getValue()+" a "+getClient().getSocket().getPort());
				out.println(me.getValue());
				out.flush();
				int e = me.getKey();
				getBufferOutput().remove(e);
				compteurSend++;
			}
		}
	}

	public void sendBuffer() {
		workers.submit(() -> {
			while(runningSend) {
				synchronized(lock) {
					while(getBufferOutput().size()<=0 && !Thread.currentThread().isInterrupted()) {
						try {
							lock.wait();
						} catch (InterruptedException e) {
							LOGGER.log(Level.INFO, "Demmande Interruption Thread pendant attente d'envoi de message : Arret des Buffers et workers");
							runningSend = false;
							Thread.currentThread().interrupt();
						}
					}
				}
				if(!Thread.currentThread().isInterrupted()) {
					send();
				}
			}
			if (out != null) {
				out.close();
			}
		});
	}
}
