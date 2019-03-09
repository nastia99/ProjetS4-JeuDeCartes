package core.interfaceScript.javafx.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.util.ResourceBundle;

import core.interfaceScript.javafx.UIManager;

public class LeaveController extends RotateReturnController{

	// --- Start
	
	@FXML
	private Button exitButton;
	
	@FXML
	private Button retourButton;

	@FXML
	private Pane leavePane;
	
	private UIManager uiManager = UIManager.getInstance();
	
    @FXML
    public void exit() {
    	int i = uiManager.exit("SHUT");
    	if(i==0) {
    		Platform.exit();
    	}
	}

	public void updateTheme(String theme, ResourceBundle rb){
		leavePane.getStyleClass().clear();
		leavePane.getStyleClass().add(theme);
	}
}


