package network.essaitcp;

public class Essai {
	
	public static void main (String[] args) {
		
		EssaiClient ec = new EssaiClient();
		EssaiServeur es = new EssaiServeur();
		
		
		new Thread(es,"es").start();
		new Thread(ec,"ec").start();

	}
}