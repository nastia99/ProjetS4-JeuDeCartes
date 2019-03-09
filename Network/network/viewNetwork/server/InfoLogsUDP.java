package network.viewnetwork.server;

public class InfoLogsUDP {
	
	private String ipM;
	private String pM;
	private String contenu;
	
	
	public InfoLogsUDP(String contenu, String ipM, String pM) {
		this.ipM = ipM;
		this.pM =pM;
		this.contenu = contenu;
	}
	
	public String getIpM() {
		return ipM;
	}
	public void setIpM(String ipM) {
		this.ipM = ipM;
	}
	public String getpM() {
		return pM;
	}
	public void setpM(String pM) {
		this.pM = pM;
	}
	public String getContenu() {
		return contenu;
	}
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
	

}
