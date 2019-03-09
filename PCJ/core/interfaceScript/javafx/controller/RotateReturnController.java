package core.interfaceScript.javafx.controller;

import core.interfaceScript.javafx.ComputeAnimation;
import core.interfaceScript.javafx.UIManager;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

public class RotateReturnController {
	
	// --- Start Attributes
	// --- Start Rotation
	@FXML
	GridPane globalGrid;

	private boolean rotation = false;
	private double rotateTime = 2;
	private double startRotation;
	// --- End Rotation

	protected UIManager uiManager = UIManager.getInstance();
	// --- End Attributes

	// --- Start Methods
	public void rotateLeft90() {
    	if (!rotation) {
        	ComputeAnimation.playRotationTransition(globalGrid, rotateTime, uiManager.getRotation(), 1);
        	startRotation = System.currentTimeMillis() * Math.pow(10,  -3);
        	rotation = true;
    	}
    	else {
    		if (System.currentTimeMillis() * Math.pow(10,  -3) - startRotation > rotateTime) {
    			rotation = false;
    		}
    	}
    }

	public void rotateRight90() {
    	if (!rotation) {
        	ComputeAnimation.playRotationTransition(globalGrid, rotateTime, - uiManager.getRotation(), 1);
        	startRotation = System.currentTimeMillis() * Math.pow(10,  -3);
        	rotation = true;
    	}
    	else {
    		if (System.currentTimeMillis() * Math.pow(10,  -3) - startRotation > rotateTime) {
    			rotation = false;
    		}
    	}  
    }
    
	public void retour() {
		uiManager.removeToScene();
	}
	// --- End Methods
}
