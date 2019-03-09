package core.interfaceScript.javafx.controller;


import core.interfaceScript.javafx.Sound;
import core.interfaceScript.javafx.UIManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;

import java.util.ResourceBundle;

public class ParameterController extends RotateReturnController  {
	
	// --- Start Attributes
	private UIManager uiManager;
		
	@FXML
	MenuBar param_langue;
	
	@FXML
	RadioMenuItem buttonEnLang;
	
	@FXML
	RadioMenuItem buttonFrLang;

    @FXML
    RadioMenuItem buttonDefaultTheme;

    @FXML
    RadioMenuItem buttonEasterTheme;

    @FXML
    RadioMenuItem buttonSummerTheme;

	@FXML
    RadioMenuItem buttonHalloweenTheme;

    @FXML
    RadioMenuItem buttonChristmasTheme;
	
	@FXML
	Button retour;

    @FXML
    private Pane parameterPane;

    @FXML
    private Slider musicSlider;
        
    private Sound son;
        
	// --- End Attributes

    // --- Start Constructor
    public ParameterController() {
        super();
        uiManager = UIManager.getInstance();
    }
    // --- End Constructor
    
	// --- Start Methods
    public void gameLocker(String str) {
    	if (str.equals("GameTable")) {
    		param_langue.setDisable(true);    		
    		param_langue.setDisable(true);
    	}
    	else if (str.equals("Menu")){
    		param_langue.setDisable(false);    		
    		param_langue.setDisable(false);
    	}
    }
    
	@FXML
	public void updateLang() {
		if (buttonEnLang.isSelected()) {
			uiManager.updateCurrentLang("EN");
		}
		else if (buttonFrLang.isSelected()) {
			uiManager.updateCurrentLang("FR");
		}
		else {
			uiManager.updateCurrentLang("FR");
		}
	}

	@FXML
    public void updateTheme(){
    	if(buttonDefaultTheme.isSelected()){
    		UIManager.getInstance().updateTheme("default", UIManager.getInstance().getDefaultBundle() );
        }
        else if(buttonEasterTheme.isSelected()){
        	UIManager.getInstance().updateTheme("easter", UIManager.getInstance().getEasterBundle());
        }
        else if(buttonSummerTheme.isSelected()){
        	UIManager.getInstance().updateTheme("summer", UIManager.getInstance().getSummerBundle());
        }
        else if(buttonHalloweenTheme.isSelected()){
        	UIManager.getInstance().updateTheme("halloween", UIManager.getInstance().getHalloweenBundle());
        }
        else {
            UIManager.getInstance().updateTheme("christmas", UIManager.getInstance().getChristmasBundle());
        }
    }

    public void updateTheme(String theme, ResourceBundle rb){
    	parameterPane.getStyleClass().clear();
    	parameterPane.getStyleClass().add(theme);
        rb.getString("stack");
    }
    
    @FXML
    public void updateSound() {    	
    	son = UIManager.getInstance().getSound();
    	if (son != null) {
    		son.updateSound(musicSlider.getValue(), uiManager.getSound());
    	}
    }
    

	// --- End Methods
}

