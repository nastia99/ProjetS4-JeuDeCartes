package network.essaitcp;

import network.facade.server.FacadeServer;
import network.utilitaire.IpMachine;

public class Game {

	private boolean gamePhysical;
	private String addrIP;
	private int port;
	private String name;
	private int nbPlayerMax;
	private int nbPlayerRMax;
	private int nbPlayerVMax;
	private int nbPlayerRConnect;
	private int nbPlayerVConnect;
	private String statut;
	
	private FacadeServer fs;
	
	private IpMachine addr = new IpMachine();
	
	public Game(int port, String name, int nbPlayerMax,  int nbPlayerRMax, int nbPlayerVMax, FacadeServer fs, boolean physical) {
		this.addrIP = addr.getIpMachine();
		this.port = port;
		this.name= name;
		this.nbPlayerMax = nbPlayerMax;
		this.nbPlayerRMax = nbPlayerRMax;
		this.nbPlayerVMax = nbPlayerVMax;
		this.setFs(fs);
		this.setGamePhysical(physical);
		fs.startNetworkNewGame(this.port);
	}
	
	public Game(String addri, int port, String name, int nbPlayerMax,  int nbPlayerRMax, int nbPlayerVMax) {
		this.addrIP = addri;
		this.port = port;
		this.name= name;
		this.nbPlayerMax = nbPlayerMax;
		this.nbPlayerRMax = nbPlayerRMax;
		this.nbPlayerVMax = nbPlayerVMax;
	}
	
	public String getAddrIP() {
		return addrIP;
	}
	public void setAddrIP(String addrIP) {
		this.addrIP = addrIP;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNbPlayerMax() {
		return nbPlayerMax;
	}
	public void setNbPlayerMax(int nbPlayerMax) {
		this.nbPlayerMax = nbPlayerMax;
	}
	public int getNbPlayerRMax() {
		return nbPlayerRMax;
	}
	public void setNbPlayerRMax(int nbPlayerRMax) {
		this.nbPlayerRMax = nbPlayerRMax;
	}
	public int getNbPlayerVMax() {
		return nbPlayerVMax;
	}
	public void setNbPlayerVMax(int nbPlayerVMax) {
		this.nbPlayerVMax = nbPlayerVMax;
	}
	public int getNbPlayerRConnect() {
		return nbPlayerRConnect;
	}
	public void setNbPlayerRConnect(int nbPlayerRConnect) {
		this.nbPlayerRConnect = nbPlayerRConnect;
	}
	public int getNbPlayerVConnect() {
		return nbPlayerVConnect;
	}
	public void setNbPlayerVConnect(int nbPlayerVConnect) {
		this.nbPlayerVConnect = nbPlayerVConnect;
	}
	public String getStatut() {
		return statut;
	}
	public void setStatut(String statut) {
		this.statut = statut;
	}

	public boolean isGamePhysical() {
		return gamePhysical;
	}

	private void setGamePhysical(boolean gamePhysical) {
		this.gamePhysical = gamePhysical;
	}

	public FacadeServer getFs() {
		return fs;
	}

	private void setFs(FacadeServer fs) {
		this.fs = fs;
	}
}
