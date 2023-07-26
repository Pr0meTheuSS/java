/*
 * Project: TowerDefence
 * Created Date: Wednesday, April 26th 2023, 10:12:50 pm
 * Author: Olimpiev Y. Y.
 * -----
 * Last Modified:  yr.olimpiev@gmail.com
 * Modified By: Olimpiev Y. Y.
 * -----
 * Copyright (c) 2023 NSU
 * 
 * -----
 */
package Controllers;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

class SoundController {
	public SoundController() {
		sounds.put("peace", new Media("file:///home/prometheus/home/prometheus/peace.wav"));
		sounds.put("war", new Media("file:///home/prometheus/home/prometheus/war.wav"));
	}

	public void playMusic(String key) {
		if (sounds.containsKey(key)) {
			if (soundPlayer != null) {
				soundPlayer.stop();
			}
			soundPlayer = new MediaPlayer(sounds.get(key));
			soundPlayer.play();	
		} else {
			System.err.println("Incorrect key in sound controller");
		}
	}

	public void stopMusic() {
		if (soundPlayer != null) {
			soundPlayer.stop();
		}
	}

	private MediaPlayer soundPlayer = null;
	private Map<String, Media> sounds = new HashMap<>();
}
