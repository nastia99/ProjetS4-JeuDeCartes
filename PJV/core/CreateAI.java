package core;

import network.NetworkManager;

public class CreateAI {
	
	private String name;
	private boolean verbose;
	private boolean log;
	private String partyType;
	private int nbPlayer;
	private NetworkManager nm;
	private AIManager aiM;
	
	public CreateAI(String name, String partyType, String nbPlayer, boolean verbose, boolean log) {
		this.name = name;
		this.verbose = verbose;
		this.log = log;
		this.partyType = partyType;
		this.nbPlayer = Integer.parseInt(nbPlayer);
		nm = NetworkManager.getInstance();
		aiM = AIManager.getInstance();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isVerbose() {
		return verbose;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}

	public NetworkManager getNm() {
		return nm;
	}

	public void setNm(NetworkManager nm) {
		this.nm = nm;
	}

	public boolean isLog() {
		return log;
	}

	public AIManager getAiM() {
		return aiM;
	}

	public String getPartyType() {
		return partyType;
	}

	public int getNbPlayer() {
		return nbPlayer;
	}
	
	

	
	
}
