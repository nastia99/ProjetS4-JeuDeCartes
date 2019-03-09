package core.interfaceScript.javafx.controller;

import javafx.fxml.FXML;

import javafx.scene.layout.Pane;

import java.util.ResourceBundle;

public class RulesController extends RotateReturnController {

    // --- Start Attributes

    @FXML
    private Pane rulesPane;
    
    @FXML
    private Pane rules1;
    
    @FXML
    private Pane rules2;
    
    @FXML
    private Pane rules3;
    
    @FXML
    private Pane rules4;
    
    // --- End Attributes

    // --- Start Methods

    public void updateTheme(String theme, ResourceBundle rb){
        rulesPane.getStyleClass().clear();
        rulesPane.getStyleClass().add(theme);
    }
    
    @FXML
    public void regles1() {
    	rules1.setOpacity(0);
	    rules3.setOpacity(0);
    	rules2.setOpacity(1);
    	rules2.toFront();
    }
    
    @FXML
    public void showOldRules1() {
    	rules2.setOpacity(0);
    	rules3.setOpacity(0);
    	rules1.setOpacity(1);
    	rules1.toFront();
    }
    
    @FXML
    public void showRules2() {
    	rules2.setOpacity(0);
    	rules1.setOpacity(0);
    	rules3.setOpacity(1);
    	rules3.toFront();
    }
    
    @FXML
    public void showOldRules2() {
    	rules1.setOpacity(0);
    	rules3.setOpacity(0);
    	rules2.setOpacity(1);
    	rules2.toFront();
    }
    
    @FXML
    public void showRules3() {
    	rules3.setOpacity(0);
    	rules4.setOpacity(1);
    	rules4.toFront();
    }
    
    @FXML
    public void showOldRules3() {
    	rules3.setOpacity(1);
    	rules4.setOpacity(0);
    	rules3.toFront();
    }
   
     
    
    
      
    // --- End Methods

    
}
