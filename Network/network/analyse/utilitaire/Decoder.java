package network.analyse.utilitaire;

import java.util.ArrayList;

public class Decoder {
	
	public ArrayList<String> splitTxt(String txt) {
		System.out.println("NEW MESSAGE SERVEUR : "+txt );
		ArrayList<String> list = new ArrayList<>();
		for(String retval: txt.split("-")) {
			list.add(retval);
		}
		return list;
	}
	
}
