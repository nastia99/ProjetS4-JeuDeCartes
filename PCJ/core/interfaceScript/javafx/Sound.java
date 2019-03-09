package core.interfaceScript.javafx;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sound {

	String file;
	Media sound;
	MediaPlayer player;

	public Sound(String file) {
		this.file = file;
		
		this.sound = new Media(ClassLoader.getSystemResource(file).toString());
		this.player = new MediaPlayer(sound);
	}

	public void play(){
			player.play();
			player.setCycleCount(MediaPlayer.INDEFINITE);
	}

	public void playOnce() {
		player.play();
	}
	public void stop(){
		player.stop();
	}
	
	public void updateSound(double volume, Sound son) {
		son.setVolume((volume/100)+0.05);
	}

	public void setVolume(double value){
		player.setVolume(value);
	}
	
	public void setFile(String file) {
		this.file = file;
	}

	public double getVolume(){
		return player.getVolume();
	}
	
	public String getfile() {
		return file;
	}
}

//Slide clamper

