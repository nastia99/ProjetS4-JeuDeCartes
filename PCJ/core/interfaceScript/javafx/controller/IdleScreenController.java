package core.interfaceScript.javafx.controller;

import core.interfaceScript.javafx.skin.CloudSkin;
import javafx.animation.PathTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import core.interfaceScript.javafx.ComputeAnimation;
import core.interfaceScript.javafx.Sound;
import core.interfaceScript.javafx.UIManager;
import javafx.scene.shape.Ellipse;

public class IdleScreenController {

    // --- Start Attributes
	@FXML
	Button startButton;

	@FXML
	Label start1;
	
	@FXML
	Label start2;
	
	@FXML
	ImageView cowFly;

	@FXML
	private Pane idlePane;

	private Sound son;
	
	private Sound soundEffect;
	
	private String dftSound = "core/assets/sounds/fond.wav";
	// --- End Attributes

    // --- Start Methods
	public void initialize() {
		Ellipse ellipse = new Ellipse();
		ellipse.setCenterX(980);
		ellipse.setCenterY(490);
		ellipse.setRadiusX(610);
		ellipse.setRadiusY(350);
		ComputeAnimation.playTransitionAroundPoint(ellipse, cowFly, 10, 360, PathTransition.INDEFINITE);
		CloudSkin cloudSkin = new CloudSkin(startButton);
		startButton.setSkin(cloudSkin);
		son = new Sound (dftSound);
		son.setVolume(0.1);
		son.play();
	}

	public void startGame() {
		soundEffect = new Sound("core/assets/sounds/mow.wav");
		soundEffect.playOnce();
		UIManager.getInstance().setScene(cowFly.getScene());
		UIManager.getInstance().loadToScene(UIManager.MENU);
	}
	
	public Sound getSound() {
		return son;
	}
	
	public Sound getSoundEffect() {
		return soundEffect;
	}
	// --- End Methods
}