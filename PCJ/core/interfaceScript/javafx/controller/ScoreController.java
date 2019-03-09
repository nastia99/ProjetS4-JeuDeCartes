package core.interfaceScript.javafx.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import core.interfaceScript.javafx.UIManager;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class ScoreController extends RotateReturnController {

	@FXML
	private Pane scorePane;

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
	private Pane pane_Player11;

	@FXML
	private Pane pane_Player21;

	@FXML
	private Pane pane_Player31;

	@FXML
	private Pane pane_Player41;

	@FXML
	private Pane pane_Player51;

	@FXML
	private ImageView img1;

	@FXML
	private ImageView img2;

	@FXML
	private ImageView img3;

	@FXML
	private ImageView img4;

	@FXML
	private ImageView img5;

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
	public void leave(){
		UIManager.getInstance().addScene(UIManager.LEAVE, UIManager.SCORE);
	}

	@FXML
	public void menu(){
		UIManager.getInstance().returnMenu();
	}

	@FXML
	public void restart(){
		UIManager.getInstance().restartGame();
	}

	public void displayPlayerInfo(List<String> playerPseudos, List<Integer> playerScores) {

		HashMap<String, Integer> players = new HashMap<>();
		for (int i = 0; i < playerPseudos.size(); i++) {
			players.put(playerPseudos.get(i), playerScores.get(i));
		}
		List<Entry<String, Integer>> sortPlayer = new ArrayList(players.entrySet());
		sortPlayer.sort(Entry.comparingByValue());

		j1.setText(sortPlayer.get(0).getKey());
		j2.setText(sortPlayer.get(1).getKey());
		j3.setText(sortPlayer.get(2).getKey());
		s1.setText("" + sortPlayer.get(0).getValue());
		s2.setText("" + sortPlayer.get(1).getValue());
		s3.setText("" + sortPlayer.get(2).getValue());
		
		pane_Player1.setOpacity(1);
		pane_Player2.setOpacity(1);
		pane_Player3.setOpacity(1);

		pane_Player11.setOpacity(1);
		pane_Player21.setOpacity(1);
		pane_Player31.setOpacity(1);

		img1.setOpacity(1);
		img2.setOpacity(1);
		img3.setOpacity(1);

		if (playerPseudos.size() ==4) {
			j4.setText(sortPlayer.get(3).getKey());
			pane_Player4.setOpacity(1);
			pane_Player41.setOpacity(1);
			img4.setOpacity(1);
			s4.setText("" + sortPlayer.get(3).getValue());
		}

		if (playerPseudos.size()==5) {
			j4.setText(sortPlayer.get(3).getKey());
			pane_Player4.setOpacity(1);
			pane_Player41.setOpacity(1);
			img4.setOpacity(1);
			s4.setText("" + sortPlayer.get(3).getValue());
			
			j5.setText(sortPlayer.get(4).getKey());
			pane_Player5.setOpacity(1);
			pane_Player51.setOpacity(1);
			img5.setOpacity(1);
			s5.setText("" + sortPlayer.get(4).getValue());
		}		
	}

	public void updateTheme(String theme, ResourceBundle rb){
		scorePane.getStyleClass().clear();
		scorePane.getStyleClass().add(theme);
	}
}
