package core.interfaceScript.javafx.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import java.util.ResourceBundle;

import core.interfaceScript.javafx.UIManager;

public class OptionController extends RotateReturnController {

    // --- Start Attributes

    @FXML
    private Pane optionPane;

    private UIManager uiManager = UIManager.getInstance();
    
    // --- End Attributes

    // --- Start Methods

    public void updateTheme(String theme, ResourceBundle rb){
        optionPane.getStyleClass().clear();
        optionPane.getStyleClass().add(theme);
    }

    public void leaveView() {
    	uiManager.exitGameEnCours();
    }
    
    public void paramView() {
    	uiManager.addScene(UIManager.PARAMETER, UIManager.GAMETABLE);
    }
    
    public void rulesView(){
    	uiManager.addScene(UIManager.RULES, UIManager.GAMETABLE);
    }
    // --- End Methods

}
