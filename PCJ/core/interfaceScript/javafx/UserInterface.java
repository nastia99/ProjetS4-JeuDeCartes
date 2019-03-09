package core.interfaceScript.javafx;

import java.io.IOException;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class UserInterface extends Application {
    
    // --- Start Methods  
    // --- Start Override
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			List<String> args = getParameters().getRaw();
			if (args.isEmpty()) {
				throw new IOException("Path manquant");
			}
			else {
				Font.loadFont(getClass().getResourceAsStream("/core/assets/font/GoodDogCool.ttf"), 14);
				FXMLLoader fxml = new FXMLLoader(getClass().getClassLoader().getResource(args.get(0)));
				Scene scene = new Scene(fxml.load(), 1920, 1080);
				primaryStage.setTitle("MOW");
				primaryStage.setScene(scene);
				primaryStage.setFullScreen(true);
				primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
				primaryStage.show();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	// --- End Override
	// --- End Methods
}
