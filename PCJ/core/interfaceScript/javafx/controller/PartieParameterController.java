package core.interfaceScript.javafx.controller;

import core.gameScript.EventManager;
import core.interfaceScript.javafx.UIManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.ResourceBundle;

public class PartieParameterController extends RotateReturnController{

	int MINPLAYER = 3;
	int MAXPLAYER = 5;
	
	// --- Start Attributes

	// --- Start FXML
	@FXML
	Text nbPlayer;
	
	@FXML
	Text nbBot;

    @FXML
    private Pane partieParameterPane;
    
    @FXML
    private Button startButton;
	// --- End FXML

    int nbP;
    int nbB;

	// --- End Attributes
	// --- Start Methods

    @FXML
    public void initialize() {
        nbP = Integer.parseInt(nbPlayer.getText());
        nbB = Integer.parseInt(nbBot.getText());
    }

	@FXML
	void addPlayer() {
		if(nbP+nbB < MAXPLAYER && nbP >= 0){
            nbP++;
            nbPlayer.setText(String.valueOf(nbP));
            if (nbP+nbB >=3) {
        		startButton.setDisable(false);
        	}
        	else
        		startButton.setDisable(true);
            UIManager.getInstance().addPlayer();
        }

	}
	
	@FXML
	void removePlayer() {
        if(nbP+nbB <= MAXPLAYER && nbP > 0){
            nbP--;
            nbPlayer.setText(String.valueOf(nbP));
    		        }
        if (nbP+nbB >=3) {
    		startButton.setDisable(false);
    	}
    	else
    		startButton.setDisable(true);
        UIManager.getInstance().removePlayer();

	}
	
    @FXML
    void addBot() {
        if(nbP+nbB < MAXPLAYER && nbB >= 0){
            nbB++;
            nbBot.setText(String.valueOf(nbB));
        	UIManager.getInstance().addBot();
        	if (nbP+nbB >=3) {
        		startButton.setDisable(false);
        	}
        	else
        		startButton.setDisable(true);
        }
    }
    
    @FXML
    void removeBot() { 
        if(nbP+nbB <= MAXPLAYER && nbB > 0){
            nbB--;
            nbBot.setText(String.valueOf(nbB));
            if (nbP+nbB >=3) {
        		startButton.setDisable(false);
        	}
        	else
        		startButton.setDisable(true);
    		UIManager.getInstance().removeBot();
        }
    }

    @FXML
    void play() {
    	if(nbP+nbB>=MINPLAYER) {    		
    		UIManager.getInstance().launchGame();
    		startButton.setDisable(true);
    		UIManager.getInstance().addScene(UIManager.LOBBY, UIManager.PARTIEPARAMETER);
    	}
    }
    
    public void updateTheme(String theme, ResourceBundle rb){
        partieParameterPane.getStyleClass().clear();
        partieParameterPane.getStyleClass().add(theme);
    }
    // --- End Methods

	public void resetNbPlayer() {
		 nbB=0;
		 nbP=0;
         nbBot.setText("0");
         nbPlayer.setText("0");
		
	}
}
