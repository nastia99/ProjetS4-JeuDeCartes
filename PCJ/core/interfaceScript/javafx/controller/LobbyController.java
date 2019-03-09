package core.interfaceScript.javafx.controller;

import core.interfaceScript.javafx.ComputeAnimation;
import core.interfaceScript.javafx.UIManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class LobbyController extends RotateReturnController{



	// --- Start Attributes

	private UIManager uiManager;
	private ArrayList<String> playerList = new ArrayList<>();

	@FXML
	private Pane lobbyPane;

	// start player part //
	@FXML
	private Text joined_player;
	@FXML
	private Pane paneForAddPlayer;
	@FXML
	private Button yesButton;
	@FXML
	private Button noButton;
	// end player part //

	@FXML
	private Button startButton;

	@FXML
	private Text player_1;
	@FXML
	private Text player_2;
	@FXML
	private Text player_3;
	@FXML
	private Text player_4;
	@FXML
	private Text player_5;

	@FXML
	private Pane pane_player_1;
	@FXML
	private Pane pane_player_2;
	@FXML
	private Pane pane_player_3;
	@FXML
	private Pane pane_player_4;
	@FXML
	private Pane pane_player_5;

	private int verifPlay = 0;

	// --- End Attributes

	// --- Start Methods
	@FXML
	private void initialize() {
		uiManager = UIManager.getInstance();
	}

	@FXML
	void play() {
		UIManager.getInstance().startGame();	
		uiManager.setPlayerListGameTable(playerList);
		// --- Reset
		verifPlay = 0;
		startButton.setDisable(true);
		// --- Reset
		uiManager.loadToScene(UIManager.GAMETABLE);
	}   

	@Override
	public void retour() {
		int i = uiManager.downTcp();
		if (i == 0) {
			uiManager.removeToScene();
			uiManager.clearPlayers();
			removePlayer();
		}
	}

	public void removePlayer() {
		//recoit un int qui est le numero du joueur et son nom en string
		playerList.removeAll(playerList);
		pane_player_1.setOpacity(0);
		pane_player_2.setOpacity(0);
		pane_player_3.setOpacity(0);
		pane_player_4.setOpacity(0);
		pane_player_5.setOpacity(0);
		player_1.setText("");
		player_2.setText("");
		player_3.setText("");
		player_4.setText("");
		player_5.setText("");
	}

	public void addPlayer(String name, int nbPlayer) {
		//recoit un int qui est le numero du joueur et son nom en string
		playerList.add(name);
		Node pane = null;
		switch (nbPlayer) {
		case 0:
			player_1.setText(name);
			pane = pane_player_1;
			break;
		case 1:
			player_2.setText(name);
			pane = pane_player_2;
			break;
		case 2:
			player_3.setText(name);
			pane = pane_player_3;
			break;
		case 3:
			player_4.setText(name);
			pane = pane_player_4;
			break;
		case 4:
			player_5.setText(name);
			pane = pane_player_5;
			break;
		default:
			break;
		}
		if(pane != null) {
			ComputeAnimation.playFadeTransition(pane, 1, 1);
		}
	}

	public void updateTheme(String theme, ResourceBundle rb){
		lobbyPane.getStyleClass().clear();
		lobbyPane.getStyleClass().add(theme);
	}

	//true if ajouter player
	public boolean acceptPlayer(String name) {
		//return showPopupPlayerAdd(name);
		verifPlay++;
		if (verifPlay == uiManager.getNbPlayers()) {
			startButton.setDisable(false);
		}
		return true ;
	}

	//    private boolean showPopupPlayerAdd(String name) {
	//   	 boolean acceptTest=false;
	//   	 Alert alert = new Alert(AlertType.CONFIRMATION);
	//   	 alert.setTitle("Player");
	//   	 alert.setHeaderText(name+" try to connect, add him ?");
	//   	 ButtonType yes = new ButtonType("Yes");
	//   	 ButtonType no = new ButtonType("No");
	//   	 
	//   	 // Remove default ButtonTypes
	//   	 
	//   	 alert.getButtonTypes().clear();
	//   	 alert.getButtonTypes().addAll(yes,no);
	//   	 // option != null.
	//   	 Optional<ButtonType> option = alert.showAndWait();
	//   	 
	//   	 if (option.get() == null) {
	//   		 acceptTest=false;
	//   		 return false;
	//   	 } 
	//   	 else if (option.get() == yes) {
	//   		 acceptTest=true;
	//   		 return true;
	//   	 } 
	//   	 else if (option.get() == no) {
	//   		 acceptTest=false;
	//   		 return false;
	//   		 }
	//		return acceptTest;
	//    }

	// --- End Methods
}

