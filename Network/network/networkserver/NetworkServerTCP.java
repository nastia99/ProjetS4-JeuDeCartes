package network.networkserver;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import network.listenercore.IGameClientAction;
import network.tcp.controller.NetworkControllerTCPServer;
import network.tcp.server.ThreadPoolServerTCP;
import network.utilitaire.IpMachine;
import network.viewnetwork.server.NetworkServerView;


public class NetworkServerTCP implements NetworkServer{
	

	private ThreadPoolServerTCP tp = null;
	private ExecutorService workers = Executors.newCachedThreadPool();
	private NetworkControllerTCPServer nctcps;
	private String nameService = "TCP";
	private static Logger logger = Logger.getLogger(NetworkServerTCP.class.toString());
	
	public NetworkServerTCP(NetworkControllerTCPServer networkControllerTCPServer) {
		this.nctcps = networkControllerTCPServer;
	}

	public boolean isRunning() {
        return tp != null;
	}

	public NetworkServerView getViewServer() {
		return nctcps.getViewServer();
	}
	
	public void createThreadTCP(int port, IGameClientAction igca) {
		ThreadPoolServerTCP t = new ThreadPoolServerTCP(port, this,igca);
		if(tp == null) {
			this.tp = t;
			workers = Executors.newCachedThreadPool();
			workers.submit(tp);
		}
		else {
			logger.log(Level.WARNING, "One game is already start");

		}
	}
	
	public void stopNetworkServer() {
		workers.shutdown();
	}


	public void stopThreadTCP() {
		if(tp == null) {
			logger.log(Level.WARNING,"Already threadPool stop");
		}
		else {
			tp.shutdown();
			tp = null;
		}
	}



	public ThreadPoolServerTCP getOnlyPool() {
		return this.tp;
	}

	@Override
	public String getEtat() {
		if(tp != null)
		{
			return "Listen";
		}
		else return "Down";
	}

	@Override
	public String getNameService() {
		return this.nameService;
	}

	@Override
	public String getAdressIp() {
		return new IpMachine().getIpMachine();
	}

	@Override
	public String getPortListen() {
		if(tp != null) {
			return ""+tp.getPort();
		} else return "";
	}

	public void notifyLostConnection() {
		nctcps.notifyLostConnection();
		
	}

}
