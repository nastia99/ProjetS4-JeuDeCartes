package network.analyse.utilitaire;

import java.util.ArrayList;

public class Encoder {
	
	String separateur;
	
	public Encoder() {
		separateur = "-";
	}
	
	public Encoder(String string) {
		separateur = string;
	}

	public String splitTxt(ArrayList<String> txt) {
		StringBuilder builder = new StringBuilder();
		builder.append(txt.get(0));

		txt.remove(0);
		while(!txt.isEmpty()) {
			builder.append(separateur);
			builder.append(txt.get(0));
			txt.remove(0);
		}
		return builder.toString();
	}

}
