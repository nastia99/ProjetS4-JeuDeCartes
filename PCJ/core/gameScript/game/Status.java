package core.gameScript.game;

public enum Status {
	ATTENTE ("ATTENTE"),
	ANNULEE ("ANNULEE"),
	COMPLETE ("COMPLETE"),
	TERMINEE ("TERMINEE");
	
	private String text;
	
	private Status(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
}
