package core.interfaceScript.javafx;

import java.io.IOException;
import java.util.*;

import core.gameScript.EventManager;
import core.gameScript.game.Player;
import core.interfaceScript.javafx.controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;

public class UIManager {

	// --- Start Singleton
	private static UIManager instance;
	
	private UIManager() {

	}
	
	public static final UIManager getInstance() {
		if (instance == null) {
			instance = new UIManager();
		}
		return UIManager.instance;
	}
	// --- End Singleton
	
	// --- Start Attributes
    private HashMap<String, Pane> screenMap = new HashMap<>();
    private Scene scene;
    
    private Stack<String> previousFXML = new Stack<String>(); // Parent to reset previous state
    
	private double rotation = 360 / 4;

	private Sound sound;
	
	private Sound themeSound;
	
	// --- Start Controller
	// --- Start CurrentController
	private MenuController menuController;
	private IdleScreenController idleScreenController;
	private GameTableController gameTableController;
	private ParameterController parameterController;
	private PartieParameterController partieParameterController;
	private LobbyController lobbyController;
	private LeaveController leaveController;
	private OptionController optionController;
	private RulesController rulesController;
	private ScoreController scoreController;
	// --- End CurrentController
	private MenuController menuControllerFr;
	private IdleScreenController idleScreenControllerFr;
	private GameTableController gameTableControllerFr;
	private ParameterController parameterControllerFr;
	private PartieParameterController partieParameterControllerFr;
	private LobbyController lobbyControllerFr;
	private LeaveController leaveControllerFr;
	private OptionController optionControllerFr;
	private RulesController rulesControllerFr;
	private ScoreController scoreControllerFr;
	
	private MenuController menuControllerEn;
	private IdleScreenController idleScreenControllerEn;
	private GameTableController gameTableControllerEn;
	private ParameterController parameterControllerEn;
	private PartieParameterController partieParameterControllerEn;
	private LobbyController lobbyControllerEn;
	private LeaveController leaveControllerEn;
	private OptionController optionControllerEn;
	private RulesController rulesControllerEn;
	private ScoreController scoreControllerEn;
	
	
	// --- Start FXML
	// --- Start Language
	private static String languageFr = "FR";
	private static ResourceBundle frenchBundle = ResourceBundle.getBundle("core/assets/langue.fr");
	public static final String FR = "FR";
	private static String LanguageEn = "EN";
	private static ResourceBundle englishBundle = ResourceBundle.getBundle("core/assets/langue.en");
	public static final String EN = "EN";
	private static String currentLang = FR;
	// --- End Language
    // --- Start Themes
    private String currentTheme = defaultTheme;
    private static final String defaultTheme = "DEFAULT";
    private Boolean acceptPlayer=null;

    public ResourceBundle getDefaultBundle() {
        return defaultBundle;
    }

    public ResourceBundle getEasterBundle() {
        return easterBundle;
    }

    public ResourceBundle getSummerBundle() {
        return summerBundle;
    }

    public ResourceBundle getHalloweenBundle() {
        return halloweenBundle;
    }

    public ResourceBundle getChristmasBundle() {
        return christmasBundle;
    }
    private static ResourceBundle defaultBundle = ResourceBundle.getBundle("core/assets/themes/default");
    private static ResourceBundle easterBundle = ResourceBundle.getBundle("core/assets/themes/easter");
    private static ResourceBundle summerBundle = ResourceBundle.getBundle("core/assets/themes/summer");
    private static ResourceBundle halloweenBundle = ResourceBundle.getBundle("core/assets/themes/halloween");
    private static ResourceBundle christmasBundle = ResourceBundle.getBundle("core/assets/themes/christmas");

    // --- End Themes
	// --- Stat IHM
	private static final String PATH = "./core/interfaceScript/javafx/fxml/";
	public static final String IDLE = "Idle";
	public static final String MENU = "Menu";
	public static final String GAMETABLE = "GameTable";
	public static final String RULES = "Rules";
	public static final String PARAMETER = "Parameter";
	public static final String PARTIEPARAMETER = "PartieParameter";
	public static final String LOBBY = "Lobby";
	public static final String SCORE = "Score";
	public static final String LEAVE ="Leave";
	public static final String OPTION = "Option";
	private static final String EXTENSION = ".fxml";
	// --- End IHM
	// --- End FXML
	// --- End Controller
	
	// --- End Attributes
	
	/**
	 * Method containing the processing to be performed when creating the instance
	 * @throws IOException 
	 */
	public void awake() throws IOException {
		// Chargement bundle
		FXMLLoader idleFr = new FXMLLoader(getClass().getClassLoader().getResource(PATH + IDLE + EXTENSION), frenchBundle);
		screenMap.put(FR + IDLE, idleFr.load());
		idleScreenControllerFr = idleFr.getController();
		
		FXMLLoader idleEn = new FXMLLoader(getClass().getClassLoader().getResource(PATH + IDLE + EXTENSION), englishBundle);
		screenMap.put(EN + IDLE, idleEn.load());
		idleScreenControllerEn = idleEn.getController();

		FXMLLoader menuFr = new FXMLLoader(getClass().getClassLoader().getResource(PATH + MENU + EXTENSION), frenchBundle);
		screenMap.put(FR + MENU, menuFr.load());
		menuControllerFr = menuFr.getController();
		
		FXMLLoader menuEn = new FXMLLoader(getClass().getClassLoader().getResource(PATH + MENU + EXTENSION), englishBundle);
		screenMap.put(EN + MENU, menuEn.load());
		menuControllerEn = menuEn.getController();
		
		FXMLLoader gameFr = new FXMLLoader(getClass().getClassLoader().getResource(PATH + GAMETABLE + EXTENSION), frenchBundle);
		screenMap.put(FR + GAMETABLE, gameFr.load());
		gameTableControllerFr = gameFr.getController();
		
		FXMLLoader gameEn= new FXMLLoader(getClass().getClassLoader().getResource(PATH + GAMETABLE + EXTENSION), englishBundle);
		screenMap.put(EN + GAMETABLE, gameEn.load());
		gameTableControllerEn = gameEn.getController();
		
		FXMLLoader rulesFr = new FXMLLoader(getClass().getClassLoader().getResource(PATH + RULES + EXTENSION), frenchBundle);
		screenMap.put(FR + RULES, rulesFr.load());		
		rulesControllerFr = rulesFr.getController();
		
		FXMLLoader rulesEn = new FXMLLoader(getClass().getClassLoader().getResource(PATH + RULES + EXTENSION), englishBundle);
		screenMap.put(EN + RULES, rulesEn.load());
		rulesControllerEn = rulesEn.getController();
		
		FXMLLoader parameterFr = new FXMLLoader(getClass().getClassLoader().getResource(PATH + PARAMETER + EXTENSION), frenchBundle);
		screenMap.put(FR + PARAMETER, parameterFr.load());
		parameterControllerFr = parameterFr.getController();
		
		FXMLLoader parameterEn = new FXMLLoader(getClass().getClassLoader().getResource(PATH + PARAMETER + EXTENSION), englishBundle);
		screenMap.put(EN + PARAMETER, parameterEn.load());
		parameterControllerEn = parameterEn.getController();
		
		FXMLLoader partieParameterFr = new FXMLLoader(getClass().getClassLoader().getResource(PATH + PARTIEPARAMETER + EXTENSION), frenchBundle);
		screenMap.put(FR + PARTIEPARAMETER, partieParameterFr.load());
		partieParameterControllerFr = partieParameterFr.getController();
		
		FXMLLoader partieParameterEn = new FXMLLoader(getClass().getClassLoader().getResource(PATH + PARTIEPARAMETER + EXTENSION), englishBundle);
		screenMap.put(EN + PARTIEPARAMETER, partieParameterEn.load());
		partieParameterControllerEn = partieParameterEn.getController();
		
		FXMLLoader lobbyFr = new FXMLLoader(getClass().getClassLoader().getResource(PATH + LOBBY + EXTENSION), frenchBundle);
		screenMap.put(FR + LOBBY, lobbyFr.load());
		lobbyControllerFr = lobbyFr.getController();
		
		FXMLLoader lobbyEn = new FXMLLoader(getClass().getClassLoader().getResource(PATH + LOBBY + EXTENSION), englishBundle);
		screenMap.put(EN + LOBBY, lobbyEn.load());
		lobbyControllerEn = lobbyEn.getController();

        FXMLLoader scoreFr = new FXMLLoader(getClass().getClassLoader().getResource(PATH + SCORE + EXTENSION), frenchBundle);
        screenMap.put(FR + SCORE, scoreFr.load());
        scoreControllerFr = scoreFr.getController();

        FXMLLoader scoreEn = new FXMLLoader(getClass().getClassLoader().getResource(PATH + SCORE + EXTENSION), englishBundle);
        screenMap.put(EN + SCORE, scoreEn.load());
        scoreControllerEn = scoreEn.getController();
		
		FXMLLoader leaveFr = new FXMLLoader(getClass().getClassLoader().getResource(PATH + LEAVE + EXTENSION), frenchBundle);
		screenMap.put(FR + LEAVE, leaveFr.load());
		leaveControllerFr = leaveFr.getController();
		
		FXMLLoader leaveEn = new FXMLLoader(getClass().getClassLoader().getResource(PATH + LEAVE+ EXTENSION), englishBundle);
		screenMap.put(EN + LEAVE, leaveEn.load());
		leaveControllerEn = leaveEn.getController();
		
		FXMLLoader optionFr = new FXMLLoader(getClass().getClassLoader().getResource(PATH + OPTION+ EXTENSION), frenchBundle);
		screenMap.put(FR + OPTION, optionFr.load());
		optionControllerFr = optionFr.getController();
		
		FXMLLoader optionEn = new FXMLLoader(getClass().getClassLoader().getResource(PATH + OPTION+ EXTENSION), englishBundle);
		screenMap.put(EN + OPTION, optionEn.load());
		optionControllerEn = optionEn.getController();
		
		// --- Set current controller on fr at the beginning
		menuController = menuControllerFr;
		gameTableController = gameTableControllerFr;
		parameterControllerEn = parameterControllerFr;
		partieParameterController = partieParameterControllerFr;
		lobbyController = lobbyControllerFr;
		leaveController = leaveControllerFr;
		optionController = optionControllerFr;
		rulesController = rulesControllerFr;
		scoreController = scoreControllerFr;
		idleScreenController = idleScreenControllerFr;	
	}
	
	/**
	 * Method containing the processing to be performed after the awake of the instance
	 * ? Make factory to do that ?
	 * @throws InterruptedException 
	 */
	public void start() {
		Application.launch(UserInterface.class, PATH + IDLE + EXTENSION);
	}
	
    // --- Start FXML
    private void activate(String name) {
    	Pane pane = new Pane(screenMap.get(currentLang + name));
    	Parent p = scene.getRoot();
    	p = null; // Let GC do its work
    	scene.setRoot(pane);
    	previousFXML.clear();
    }
    
    private void add(String newFXML, String oldFXML) {
		previousFXML.push(oldFXML);
    	Pane pane = screenMap.get(currentLang + newFXML);
   		Group g = new Group();
   		g.getChildren().add(scene.getRoot());
   		pane.setOpacity(0);
   		g.getChildren().add(pane);
   		ComputeAnimation.playFadeTransition(pane, 0.75, 1);
   		scene.setRoot(g);
   		
   		// --- Pour locker les parametres ingame
   		parameterControllerFr.gameLocker(oldFXML);
   		parameterControllerEn.gameLocker(oldFXML);
    }
    
    public void removeToScene() {
    	Group g = new Group();
    	g.getChildren().add(scene.getRoot());
    	Pane p = screenMap.get(currentLang + previousFXML.pop());
    	p.setOpacity(0);
   		g.getChildren().add(p);
    	ComputeAnimation.playFadeTransition(p, 0.75, 1);
    	scene.setRoot(g);
    }

	public void loadToScene(String name) {
		activate(name);
	}
	
	public void addScene(String newFXML, String oldFXML) {
		add(newFXML, oldFXML);
	}
	
	public void updateCurrentLang(String lng) {
		currentLang = lng;		
	}

	public void updateTheme(String theme, ResourceBundle rb){
		currentTheme = theme;

		if (sound != null) {
			sound.stop();
		}
		sound= new Sound(rb.getString("sound"));
		sound.play();
		menuControllerEn.updateTheme(currentTheme, rb);
		gameTableControllerEn.updateTheme(currentTheme, rb);
		parameterControllerEn.updateTheme(currentTheme, rb);
		partieParameterControllerEn.updateTheme(currentTheme, rb);
		lobbyControllerEn.updateTheme(currentTheme, rb);
		leaveControllerEn.updateTheme(currentTheme, rb);
		rulesControllerEn.updateTheme(currentTheme, rb);
		optionControllerEn.updateTheme(currentTheme, rb);
		scoreControllerEn.updateTheme(currentTheme, rb);
		
		menuControllerFr.updateTheme(currentTheme, rb);
		gameTableControllerFr.updateTheme(currentTheme, rb);
		parameterControllerFr.updateTheme(currentTheme, rb);
		partieParameterControllerFr.updateTheme(currentTheme, rb);
		lobbyControllerFr.updateTheme(currentTheme, rb);
		leaveControllerFr.updateTheme(currentTheme, rb);
		rulesControllerFr.updateTheme(currentTheme, rb);
		optionControllerFr.updateTheme(currentTheme, rb);
		scoreControllerFr.updateTheme(currentTheme, rb);
		
		setSound(sound);
	}

    // --- End FXML 
	
	// ---------- Start Call from Controller ---------- //
	
	// --- Start LobbyController --- //
	public void setPlayerListGameTable(ArrayList<String> playerList) {
		gameTableController.setPlayerList(playerList);
		gameTableController.setupPlayer();
	}
	
	public boolean acceptPlayer(String name) {	
		return lobbyController.acceptPlayer(name);
	}
	// --- End LobbyController --- //
	
	// --- Start GameTableController --- //
	public void clearCurrentflock() {
		gameTableController.clearCurrentFlock();
	}
	
	public void addCardToFlock(String color,String number,int player,int whereToPut) {
		gameTableController.addCardToFlock(color, number, player, whereToPut);
	}
	// --- End GameTableController --- //
	
	// ---------- End Call from Controller ---------- //

	// --- Start Game
	public void putCard(Player p) {
		
	}
	
	public void launchGame() {
		EventManager.getInstance().launchGame();
	}
	
	public void addBot() {
		EventManager.getInstance().incrementMaxVirtualPlayer();
	}
	
	public void removeBot() {
		EventManager.getInstance().decrementMaxVirtualPlayer();
	}
	
	public void removePlayer() {
		EventManager.getInstance().decrementMaxRealPlayer();

	}
	
	public void addPlayer() {
		EventManager.getInstance().incrementMaxRealPlayer();
	}
	
	public void startGame() {
		EventManager.getInstance().startGame();
	}
	
	public void restartGame() {
		EventManager.getInstance().restartGame();
	}
	// --- End Game
	
	// --- Start Setter
	public void setScene(Scene s) {
		scene = s;
	}
	
	public void setSound(Sound sound) {
		themeSound = sound;
	}
	// --- End Setter	
	
	// --- Start Getter
	public double getRotation() {
		return rotation;
	}
	
	public Sound getSound() {
		return themeSound;
	}
	// --- End Getter

	
	// --- Start Speak	
	public void addPlayerInLobby(String name, int nbPlayer) {
		lobbyController.addPlayer(name, nbPlayer);
	}
	// --- End Speak

    // --- Start NbPlayer
    public int getNbPlayers(){
    	return EventManager.getInstance().getNbPlayer();
    }
    // --- End NbPlayer
	
    // --- Start Leave
    public int exit(String string) {
    	int i = EventManager.getInstance().shutDownPCJ(string);
    	return i;
    }    

	public void setAcceptPlayer(Boolean accept) {
		acceptPlayer = accept;
	}
	
	public Boolean getAcceptPlayer() {
		return acceptPlayer;
	}
    
    // --- End Leave

	// --- Start LoadScore

	public void loadScore(List<String> playerPseudos, List<Integer> playerScores){
		gameTableController.scoreView();
		scoreController.displayPlayerInfo(playerPseudos, playerScores);
	}

	public void addPlayer(String playerName, int playerPosition) {
		lobbyController.addPlayer(playerName, playerPosition);
	}

	public void manageDeck(int nbCardInDeck) {
		gameTableController.manageDeck(nbCardInDeck);
	}

	public void setupRound(int roundID) {
		gameTableController.setupRound(roundID);
	}

	public void setCurrentPlayer(int currentPlayer) {
		gameTableController.setCurrentPlayer(currentPlayer, true);
	}

	public void resetCurrentPlayer(int currentPlayer) {
		gameTableController.setCurrentPlayer(currentPlayer, false);		
	}

	public int downTcp() {
		lobbyController.removePlayer();
		return EventManager.getInstance().shutdownTcp();
	}

	public void clearPlayers() {
		EventManager.getInstance().resetPlayers();
		
	}

	public void restartMenu() {
		EventManager.getInstance().shutdownTcp();	
	}

	public void clearLobby() {
		lobbyController.removePlayer();
	}
	
	// --- End LoadScore

	public void setupFlies(List<String> playersGameScore) {
		List<Integer> playersScores = new ArrayList<>();
		for (String str : playersGameScore) {
			playersScores.add(Integer.parseInt(str));
		}
		gameTableController.setPlayerScore(playersScores);
		gameTableController.setupScore();
	}

	public void resetUiTable() {
		gameTableController.setupScore();
		
	}

	public void returnMenu() {
		EventManager.getInstance().returnMenu();
		
	}

	public void resetParameterGame() {
		partieParameterControllerFr.resetNbPlayer();
		
	}

	public void cleanGame() {
		gameTableController.cleanGame();
	}

	public void exitGameEnCours() {
		EventManager.getInstance().exitGameEnCours();
	}
}