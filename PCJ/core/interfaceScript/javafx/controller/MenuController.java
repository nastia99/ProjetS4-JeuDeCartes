package core.interfaceScript.javafx.controller;

import core.gameScript.EventManager;
import core.interfaceScript.javafx.Sound;
import core.interfaceScript.javafx.UIManager;
import core.interfaceScript.javafx.skin.CloudSkin;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import java.util.ResourceBundle;

public class MenuController {

	// --- Start Attributes
	@FXML
	private Button playButton;
	
	@FXML
	private Button rulesButton;

	@FXML
	private Pane menuPane;
	// --- End Attributes

	// --- Start Methods
	@FXML
	private void initialize() {
		CloudSkin csPlay = new CloudSkin(playButton);
		playButton.setSkin(csPlay);
		CloudSkin csRule = new CloudSkin(rulesButton);
		rulesButton.setSkin(csRule);
	}

	public void rulesView() {
		UIManager.getInstance().addScene(UIManager.RULES, UIManager.MENU);
	}

	public void paramView() {
		Sound son = new Sound("core/assets/sounds/Yee.wav");
		son.playOnce();
		UIManager.getInstance().addScene(UIManager.PARAMETER, UIManager.MENU);
	}

	public void startGame() {
		Sound son = new Sound("core/assets/sounds/mow.wav");
		son.playOnce();
		EventManager.getInstance().createGame();
		UIManager.getInstance().addScene(UIManager.PARTIEPARAMETER, UIManager.MENU);
	}

	public void updateTheme(String theme, ResourceBundle rb){
	    menuPane.getStyleClass().clear();
	    menuPane.getStyleClass().add(theme);
	    menuPane.applyCss();
    }
	
	public void leaveView() {
		UIManager.getInstance().addScene(UIManager.LEAVE, UIManager.MENU);
	}
	
	
	
	// --- End Methods

}