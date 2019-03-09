package network.viewnetwork.server;

public class InfoLogs {
	
	private String ipS;
	private String pSrc;
	private String ipD;
	private String pDest;
	private String contenu;
	
	public InfoLogs(String ipS, String pSrc, String ipD, String pDest, String contenu) {
		this.ipS = ipS;
		this.pSrc = pSrc;
		this.ipD = ipD;
		this.pDest = pDest;
		this.contenu = contenu;
	}
	
	public String getIpS() {
		return ipS;
	}
	public void setIpS(String ipS) {
		this.ipS = ipS;
	}
	public String getIpD() {
		return ipD;
	}
	public void setIpD(String ipD) {
		this.ipD = ipD;
	}
	public String getpSrc() {
		return pSrc;
	}
	public void setpSrc(String pSrc) {
		this.pSrc = pSrc;
	}
	public String getpDest() {
		return pDest;
	}
	public void setpDest(String pDest) {
		this.pDest = pDest;
	}
	public String getContenu() {
		return contenu;
	}
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}

}
