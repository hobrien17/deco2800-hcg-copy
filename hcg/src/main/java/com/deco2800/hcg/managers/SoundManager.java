package com.deco2800.hcg.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * SoundManager
 * Required to play sounds in the game engine.
 * @Author Tim Hadwen
 */
public class SoundManager extends Manager {

	private static final Logger LOGGER = LoggerFactory.getLogger(SoundManager.class);
	private Map<String, Sound> soundMap = new HashMap<String, Sound>();

	/**
	 * Constructor
	 * Loads all audio files to memory on startup.
	 */
	public SoundManager() {
		soundMap.put("ree", Gdx.audio.newSound(Gdx.files.internal("resources/sounds/ree1.wav")));
		soundMap.put("quack", Gdx.audio.newSound(Gdx.files.internal("resources/sounds/quack.wav")));
		soundMap.put("teleport", Gdx.audio.newSound(Gdx.files.internal("resources/sounds/teleport.wav")));

	}

	/**
	 * Play the sound mapped to a given string
	 */
	public void playSound(String soundString) {
		Sound sound = soundMap.get(soundString);
		if (sound != null) {
			LOGGER.info("Playing sound effect: " + soundString);
			sound.play(1f);
		} else {
			LOGGER.info("No reference to sound effect: " + soundString);
		}
	}

}
