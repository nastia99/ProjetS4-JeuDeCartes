package core.interfaceScript.javafx.controller;

import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import core.gameScript.GameManager;
import core.interfaceScript.javafx.ComputeAnimation;
import core.interfaceScript.javafx.UIManager;

public class GameTableController {

	// --- Start Attributes
	private ImageView playCard;
	private UIManager uiManager = UIManager.getInstance();
	private String backCardName = "defaultCard" ; // change this to change global back theme 
	private int deckSizeSave = 48;
	private int scale = 8;
	private List<String> playerList = new ArrayList<>();
	private List<Integer> playerScores = new ArrayList<>();

	public void setPlayerList(List<String> playerList) {
		this.playerList = playerList;
	}
	
	public void setPlayerScore(List<Integer> playerScore) {
		this.playerScores = playerScore;
	}

	@FXML
	private Pane pane_Player1;
	@FXML
	private Pane pane_Player2;
	@FXML
	private Pane pane_Player3;
	@FXML
	private Pane pane_Player4;
	@FXML
	private Pane pane_Player5;

	@FXML
	private Pane pane_PlayerScore1;
	@FXML
	private Pane pane_PlayerScore2;
	@FXML
	private Pane pane_PlayerScore3;
	@FXML
	private Pane pane_PlayerScore4;
	@FXML
	private Pane pane_PlayerScore5;

	@FXML
	private Text j1;
	@FXML
	private Text j2;
	@FXML
	private Text j3;
	@FXML
	private Text j4;
	@FXML
	private Text j5;

	@FXML
	private Text s1;
	@FXML
	private Text s2;
	@FXML
	private Text s3;
	@FXML
	private Text s4;
	@FXML
	private Text s5;

	@FXML
	private Text round;
	@FXML
	private Pane deck;
	@FXML
	private Pane gameTablePane;
	@FXML
	private HBox flock;	
	@FXML
	private Text round1;
	
	@FXML
	private ImageView sens;
	
	private String ROUND;
	private String SCORE;

	// --- End Attributes

	// --- Start Methods
	@FXML
	private void initialize() throws Exception {
		flock.setSpacing(1);
		flock.setTranslateX(60);
		flock.setMaxHeight(236);
		
		ROUND = round.getText();
		SCORE = s1.getText();
	}

	public void cleanGame() {
		setupDeck();
		setupFlock();
	}

	public void setupPlayer() {
		int size = playerList.size();
		j1.setText(playerList.get(0));
		j2.setText(playerList.get(1));
		j3.setText(playerList.get(2));
		pane_Player1.setOpacity(1);
		pane_Player2.setOpacity(1);
		pane_Player3.setOpacity(1);
		pane_PlayerScore1.setOpacity(1);
		pane_PlayerScore2.setOpacity(1);
		pane_PlayerScore3.setOpacity(1);
		
		if (size == 4) {
			j4.setText(playerList.get(3));
			pane_Player4.setOpacity(1);
			pane_PlayerScore4.setOpacity(1);
		}
		if (size==5) {
			j4.setText(playerList.get(3));
			j5.setText(playerList.get(4));
			pane_Player4.setOpacity(1);
			pane_Player5.setOpacity(1);
			pane_PlayerScore4.setOpacity(1);
			pane_PlayerScore5.setOpacity(1);
		}
		
		for (int i = 0; i < playerList.size(); i++) {
			playerScores.add(0);
		}
		setupScore();
	}

	public void setupScore() {
		int size = playerList.size();
		s1.setText(SCORE + " : " + playerScores.get(0).toString());
		s2.setText(SCORE + " : " + playerScores.get(1).toString());
		s3.setText(SCORE + " : " + playerScores.get(2).toString());
		if (size == 4) {
			s4.setText(SCORE + " : " + playerScores.get(3).toString());
		}
		if (size == 5) {
			s4.setText(SCORE + " : " + playerScores.get(3).toString());
			s5.setText(SCORE + " : " + playerScores.get(4).toString());
		}		
	}

	private void setupDeck() {
		Pane deckCard0 = createBackCard(backCardName);
		Pane deckCard1 = createBackCard(backCardName);
		Pane deckCard2 = createBackCard(backCardName);
		Pane deckCard3 = createBackCard(backCardName);
		deck.getChildren().clear();
		double i = 0 ;
		deck.getChildren().add(deckCard0);
		deck.getChildren().add(deckCard1);
		deck.getChildren().add(deckCard2);
		deck.getChildren().add(deckCard3);
		for (Node deckCardPane : deck.getChildren()) {
			deckCardPane.setTranslateX(i+8);
			deckCardPane.setTranslateY(i);
			i+=5;
		}
	}
	
	public void setupFlock() {
		if (!flock.getChildren().isEmpty()) {
			flock.getChildren().clear();
		}
	}

	public void manageDeck(int deckSize) {
		if (deckSize<=15 && deck.getChildren().size()==4) {
			deck.getChildren().remove(deck.getChildren().size()-1);
		}
		if (deckSize<=10 && deck.getChildren().size()==3) {
			deck.getChildren().remove(deck.getChildren().size()-1);
		}
		if (deckSize<=5 && deck.getChildren().size()==2) {
			deck.getChildren().remove(deck.getChildren().size()-1);
		}
		if (deckSize==0) {	
			deck.getChildren().clear();
		}
		deckSizeSave = deckSize;
	}

	private Pane createBackCard(String backToLoad) {
		Pane cardPane = new Pane();
		String pathBack="/core/assets/cards/back/"+backToLoad.trim()+".png";
		Image imgBack = new Image(getClass().getResourceAsStream(pathBack));
		ImageView cardBack = new ImageView(imgBack);

		cardPane.setMaxHeight(imgBack.getHeight()/scale);
		cardPane.setMaxWidth(imgBack.getWidth()/scale);
		cardBack.setPreserveRatio(true);
		cardBack.setFitHeight(imgBack.getHeight()/scale);
		cardBack.setFitWidth(imgBack.getWidth()/scale);

		cardPane.getChildren().add(cardBack);
		return cardPane;
	}


	/**
	 * 
	 * @param color
	 * @param number
	 * @return a pane with all cards layers inside 
	 */
	private Pane createCarte(String color,String number) {  
		Pane cardPane = new Pane();

		//imageview Color
		String pathColor="/core/assets/cards/color/"+color.trim()+".png";	
		Image imgColor = new Image(getClass().getResourceAsStream(pathColor));
		ImageView cardColor = new ImageView(imgColor);

		//immageview number
		String pathNumber="/core/assets/cards/number/"+number.trim()+".png";	
		Image imgNumber = new Image(getClass().getResourceAsStream(pathNumber));
		ImageView cardNumber = new ImageView(imgNumber);

		cardPane.setMaxHeight(imgColor.getHeight()/scale);
		cardPane.setMaxWidth(imgColor.getWidth()/scale);

		//add color

		cardColor.setPreserveRatio(true);
		cardColor.setFitHeight(imgColor.getHeight()/scale);
		cardColor.setFitWidth(imgColor.getWidth()/scale);

		cardPane.getChildren().add(cardColor);

		//add number
		cardNumber.setPreserveRatio(true);
		cardNumber.setFitHeight(imgNumber.getHeight()/scale);
		cardNumber.setFitWidth(imgNumber.getWidth()/scale);

		cardPane.getChildren().add(cardNumber);
		return cardPane;
	}

	public void addCardToFlock(String color,String number,int player,int whereToPut) {
		Pane card = createCarte(color, number);
		Pane backPaneCard = createBackCard(this.backCardName);
		Pane fullCard = new Pane();
		fullCard.getChildren().addAll(card, backPaneCard);
		addCard(fullCard,whereToPut);					
		fromPlayerToFlock(player, fullCard, card, backPaneCard);
		updateDirection();
	}

	private void addCard(Pane card, int whereToPlace) {
		if (flock.getChildren().size()>2) {
			flock.setSpacing(-10);
		}
		if (flock.getChildren().size()>4) {
			flock.setSpacing(-20);
		}
		if (flock.getChildren().size()>6) {
			flock.setTranslateX(-20);
			flock.setSpacing(-40);
		}
		if (flock.getChildren().size()>8) {
			flock.setTranslateX(-20);
			flock.setSpacing(-60);
		}
		if (flock.getChildren().size()>10) {
			flock.setTranslateX(-20);
			flock.setSpacing(-80);
		}
		if (flock.getChildren().size()>12) {
			flock.setSpacing(-90);
		}
		if (flock.getChildren().size()<19) {
			flock.getChildren().add(whereToPlace, new Pane(card));
		}		
	}

	public void clearCurrentFlock() {
		flock.getChildren().clear();
	}

	private void fromPlayerToFlock(int player,Pane fullCard, Pane frontCard, Pane backPaneCard) {
		Pane playerPane = new Pane(); 
		switch (player) {
		case 0:
			playerPane.setLayoutX(pane_Player1.getLayoutX());
			playerPane.setLayoutY(pane_Player1.getLayoutY());
			break;
		case 1:
			playerPane.setLayoutX(pane_Player2.getLayoutX());
			playerPane.setLayoutY(pane_Player2.getLayoutY());
			break;
		case 2:
			playerPane.setLayoutX(pane_Player3.getLayoutX());
			playerPane.setLayoutY(pane_Player3.getLayoutY());
			break;
		case 3:
			playerPane.setLayoutX(pane_Player4.getLayoutX());
			playerPane.setLayoutY(pane_Player4.getLayoutY());
			break;
		case 4:
			playerPane.setLayoutX(pane_Player5.getLayoutX());
			playerPane.setLayoutY(pane_Player5.getLayoutY());
			break;
		default:
			playerPane.setLayoutX(960);
			playerPane.setLayoutY(108);
			break;
		}
		Line l = new Line();
		l.setStartX(playerPane.getLayoutX());
		l.setStartY(playerPane.getLayoutY());
		l.setEndX(flock.getLayoutX());
		l.setEndY(flock.getLayoutY());

		PerspectiveCamera cam = new PerspectiveCamera();
		flock.getScene().setCamera(cam);

		Animation a = ComputeAnimation.playPathTransition(l, fullCard, 1, 1);
		ComputeAnimation.playFlipAfterAnimation(backPaneCard, frontCard, 1, 1, true, a);
	}

	public void updateTheme(String theme, ResourceBundle rb){
		gameTablePane.getStyleClass().clear();
		gameTablePane.getStyleClass().add(theme);
		this.backCardName = theme+"Card".trim();
		setupDeck();
		manageDeck(deckSizeSave);
	}

	public void paramView() {
		UIManager.getInstance().addScene(UIManager.OPTION, UIManager.GAMETABLE);
	}

	public void scoreView() {
		UIManager.getInstance().loadToScene(UIManager.SCORE);
	}

	public void setRound(int id) {
		round.setText(ROUND + " : " + id);
		round1.setText(ROUND + " : " + id);
	}

	public void setupRound(int roundID) {
		setupDeck();
		setRound(roundID);
	}

	public void setCurrentPlayer(int currentPlayer, boolean turn) {
		String style = "-fx-background-color:";
		if (turn) {
			style += "rgb(234,120,234)";
		}
		else {
			style += "rgb(234,255,234)";
		}
		switch(currentPlayer) {
		case 0:
			pane_Player1.setStyle(style);
			break;
		case 1:
			pane_Player2.setStyle(style);
			break;
		case 2:
			pane_Player3.setStyle(style);
			break;
		case 3:
			pane_Player4.setStyle(style);
			break;
		case 4:
			pane_Player5.setStyle(style);
			break;
		default:
			break;
		}
	}

	public void updateDirection() {
		if (GameManager.getInstance().getGameDirection()==false) {
			if (sens.getRotate()==180) {
				ComputeAnimation.playRotationTransition(sens, 1, -180, 1);
			}
		}
		else {
			if (sens.getRotate()==0) {
				ComputeAnimation.playRotationTransition(sens, 1, 180, 1);
			}
		}
	}
	// --- End Methods


}

