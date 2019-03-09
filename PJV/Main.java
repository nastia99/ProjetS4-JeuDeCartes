import java.util.logging.Level;
import java.util.logging.Logger;

import core.CreateAI;

public class Main {

	private static Logger logger = Logger.getLogger(Main.class.toString());
	
	public static void main(String[] args) {
		System.out.println("HELLO");
		String str = "";
		for (int i = 0; i < args.length; i++) {
			str += args[i]+"\n";
		}
		CreateAI aiCreate = null;
		if (args.length > 3) {
			if (args[3].equals("--log") || args[3].equals("-l")) {
				logger.log(Level.INFO, "Arguments : " + str);
				aiCreate = new CreateAI(args[0], args[1], args[2], false, true);
			}
			else if (args[3].equals("--verbose") || args[3].equals("-v")) {
				aiCreate = new CreateAI(args[0], args[1], args[2], true, false);
			}
			else if (args[1].equals("--help") || args[1].equals("-h")) {
				logger.log(Level.INFO, "Syntaxe ---> java -jar iaG4 <nomJoueur> <typePartie> <nbJoueurTotal> [(--log || -l) || (--verbose || -v)]");
			}
		}
		else if (args.length == 3) {
			aiCreate = new CreateAI(args[0], args[1], args[2], false, false);
		}
		
		if (aiCreate != null) {
			if (aiCreate.getAiM() != null) {
				aiCreate.getAiM().start(aiCreate.getName(), aiCreate.isLog(), aiCreate.isVerbose());
			}
			if (aiCreate.getNm() != null) {
				aiCreate.getNm().searchGame(aiCreate.getPartyType(), aiCreate.getNbPlayer());
			}
		}
		else {
			logger.log(Level.SEVERE, "Syntaxe ---> java -jar iaG4 <nomJoueur> <typePartie> <nbJoueurTotal> [(--log || -l) || (--verbose || -v)]");
		}
	}
}