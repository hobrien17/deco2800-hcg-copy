package com.deco2800.hcg.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * SoundManager Required to play sounds in the game engine.
 *
 * @Author Tim Hadwen
 */
public class SoundManager extends Manager {

	private static final Logger LOGGER = LoggerFactory.getLogger(SoundManager.class);
	private Map<String, Sound> soundMap = new HashMap<String, Sound>();

	/**
	 * Constructor Loads all audio files to memory on startup.
	 */
	public SoundManager() {
		soundMap.put("ree", Gdx.audio.newSound(Gdx.files.internal("resources/sounds/ree1.wav")));
		soundMap.put("quack", Gdx.audio.newSound(Gdx.files.internal("resources/sounds/quack.wav")));
		// walking sound effect
		soundMap.put("water-shallow", Gdx.audio.newSound(Gdx.files.internal("resources/sounds/water-shallow.wav")));
		soundMap.put("water-deep", Gdx.audio.newSound(Gdx.files.internal("resources/sounds/water-deep.wav")));
		soundMap.put("sand", Gdx.audio.newSound(Gdx.files.internal("resources/sounds/sand.wav")));
		soundMap.put("ice", Gdx.audio.newSound(Gdx.files.internal("resources/sounds/ice.wav")));
		soundMap.put("lava", Gdx.audio.newSound(Gdx.files.internal("resources/sounds/lava.wav")));
		soundMap.put("sludge", Gdx.audio.newSound(Gdx.files.internal("resources/sounds/sludge.wav")));
		soundMap.put("spikes", Gdx.audio.newSound(Gdx.files.internal("resources/sounds/spikes.wav")));
		soundMap.put("ground", Gdx.audio.newSound(Gdx.files.internal("resources/sounds/ground.wav")));
		soundMap.put("hit", Gdx.audio.newSound(Gdx.files.internal("resources/sounds/hit.wav")));
		soundMap.put("gun-rifle-shoot", Gdx.audio.newSound(Gdx.files.internal("resources/sounds/gun-rifle-shoot.wav")));
		soundMap.put("gun-shotgun-shoot", Gdx.audio.newSound(Gdx.files.internal("resources/sounds/gun-shotgun-shoot.wav")));
		soundMap.put("gun-stargun-shoot", Gdx.audio.newSound(Gdx.files.internal("resources/sounds/gun-stargun-shoot.wav")));
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

	/**
	 * Stops playing all instances of this sound.
	 */
	public void stopSound(String soundString) {
		Sound sound = soundMap.get(soundString);
		if (sound != null) {
			LOGGER.info("Stop sound effect: " + soundString);
			sound.stop();
		} else {
			LOGGER.info("No reference to sound effect: " + soundString);
		}
	}

	/**
	 * continue playing sound is a loop
	 */
	public void loopSound(String soundString) {
		Sound sound = soundMap.get(soundString);
		if (sound != null) {
			LOGGER.info("Playing sound effect, looping : " + soundString);
			sound.loop(1f);
		} else {
			LOGGER.info("No reference to sound effect: " + soundString);
		}
	}

}
