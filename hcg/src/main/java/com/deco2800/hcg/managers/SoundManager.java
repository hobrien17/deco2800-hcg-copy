package com.deco2800.hcg.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.GdxRuntimeException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * SoundManager Required to play sounds in the game engine.
 *
 * @Author Tim Hadwen
 */
public class SoundManager extends Manager {

	private static final Logger LOGGER = LoggerFactory.getLogger(SoundManager.class);
	private Map<String, Sound> soundMap = new HashMap<String, Sound>();
	
	private ArrayList<String> onWeatherSounds = new ArrayList<String>();

	ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	ScheduledFuture<?> randomLoop;

	ArrayList<Sound> weatherSounds = new ArrayList<Sound>();

	// String Constants
	private static final String NO_REF = "No reference to sound effect: ";

	/**
	 * Constructor Loads all audio files to memory on startup.
	 */
	public SoundManager() {

		try {
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
			soundMap.put("grass", Gdx.audio.newSound(Gdx.files.internal("resources/sounds/grass.wav")));
			soundMap.put("ground", Gdx.audio.newSound(Gdx.files.internal("resources/sounds/ground.wav")));
			soundMap.put("hit", Gdx.audio.newSound(Gdx.files.internal("resources/sounds/hit.wav")));

			// Gun sounds
			soundMap.put("gun-rifle-shoot",
					Gdx.audio.newSound(Gdx.files.internal("resources/sounds/gun-rifle-shoot.wav")));
			soundMap.put("gun-shotgun-shoot",
					Gdx.audio.newSound(Gdx.files.internal("resources/sounds/gun-shotgun-shoot.wav")));
			soundMap.put("gun-stargun-shoot",
					Gdx.audio.newSound(Gdx.files.internal("resources/sounds/gun-stargun-shoot.wav")));
			soundMap.put("gun-grenadelauncher-shoot",
					Gdx.audio.newSound(Gdx.files.internal("resources/sounds/gun-grenadelauncher-shoot.wav")));
			soundMap.put("bullet-grenade-explode",
					Gdx.audio.newSound(Gdx.files.internal("resources/sounds/bullet-grenade-explode.wav")));

			// For weather Effects
			soundMap.put("weatherRain", Gdx.audio.newSound(
					Gdx.files.internal("resources/sounds/environmental/rain-ambient-hardsurface-shortloop.wav")));
			soundMap.put("weatherSandStorm", Gdx.audio
					.newSound(Gdx.files.internal("resources/sounds/environmental/darude-sandstorm-shortloop.wav")));
			soundMap.put("weatherSnow", Gdx.audio.newSound(
					Gdx.files.internal("resources/sounds/environmental/wind-ambient-semirandom-longloop-spooky.wav")));
			soundMap.put("weatherWind", Gdx.audio.newSound(
					Gdx.files.internal("resources/sounds/environmental/wind-ambient-semirandom-longloop-spooky.wav")));
			soundMap.put("weatherDrought", Gdx.audio.newSound(
					Gdx.files.internal("resources/sounds/environmental/wind-ambient-semirandom-longloop-spooky.wav")));
			soundMap.put("weatherStorm", Gdx.audio.newSound(
					Gdx.files.internal("resources/sounds/environmental/rain-ambient-hardsurface-shortloop.wav")));
			soundMap.put("weatherStormSting", Gdx.audio.newSound(Gdx.files.internal("resources/sounds/ree2.wav")));

			
			// For gardening (careful with your variable name pls, two grass exist)
			soundMap.put("plantingPot", Gdx.audio.newSound(Gdx.files.internal("resources/sounds/garden/planting_in_soil_pot.wav")));
			soundMap.put("bugSpray", Gdx.audio.newSound(Gdx.files.internal("resources/sounds/garden/bugspray.wav")));
			soundMap.put("explosion", Gdx.audio.newSound(Gdx.files.internal("resources/sounds/garden/explosion.wav")));
			soundMap.put("fertiliser",
					Gdx.audio.newSound(Gdx.files.internal("resources/sounds/garden/fertiliser.wav")));
			soundMap.put("fireball", Gdx.audio.newSound(Gdx.files.internal("resources/sounds/garden/fireball.wav")));
			soundMap.put("grass1", Gdx.audio.newSound(Gdx.files.internal("resources/sounds/garden/grass.wav")));
			soundMap.put("iceTurret", Gdx.audio.newSound(Gdx.files.internal("resources/sounds/garden/ice_tree.wav")));
			soundMap.put("plantingCorpse",
					Gdx.audio.newSound(Gdx.files.internal("resources/sounds/garden/plant_in_enemies.wav")));
			soundMap.put("plantingLily",
					Gdx.audio.newSound(Gdx.files.internal("resources/sounds/garden/plant_waterlilly_in_pot.wav")));
			soundMap.put("shovelTrowel",
					Gdx.audio.newSound(Gdx.files.internal("resources/sounds/garden/shovel_or_trowel.wav")));
			soundMap.put("key", Gdx.audio.newSound(Gdx.files.internal("resources/sounds/garden/key.wav")));
			
			// Shop sounds
			soundMap.put("loot1", 
					Gdx.audio.newSound(Gdx.files.internal("resources/sounds/loot.wav")));
			soundMap.put("loot2", 
					Gdx.audio.newSound(Gdx.files.internal("resources/sounds/loot2.wav")));


			// Loot
            soundMap.put("loot1", Gdx.audio.newSound(Gdx.files.internal("resources/sounds/loot.wav")));
            soundMap.put("loot2", Gdx.audio.newSound(Gdx.files.internal("resources/sounds/loot2.wav")));
		} catch (GdxRuntimeException e) {
			throw new ResourceLoadException(e);
		}

	}

	/**
	 * Play the sound mapped to a given string
	 */
	public void playSound(String soundString) {
		Sound sound = soundMap.get(soundString);
		if (sound != null) {
			LOGGER.info("Playing sound effect: " + soundString);
			sound.play(1f);
			if (soundString.contains("weather")){
				onWeatherSounds.add(soundString);
			}
		} else {
			LOGGER.info(NO_REF + soundString);
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
			if (soundString.contains("weather")){
				onWeatherSounds.remove(soundString);
			}
		} else {
			LOGGER.info(NO_REF + soundString);
		}
	}
	
	/**
	 * Stops playing all sounds in the soundMap, for player death and eixt level.
	 */
	public void stopAll() {
		for (Entry<String, Sound> entry : soundMap.entrySet()){
			Sound sound = entry.getValue();
			sound.stop();
			
			if (entry.getKey().contains("weather")){
				onWeatherSounds.remove(entry.getKey());
			}
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
			LOGGER.info(NO_REF + soundString);
		}
	}

	/**
	 * stops all playing weather sounds
	 */
	public void stopWeatherSounds() {

		for (Sound playing : weatherSounds) {
			playing.stop();
		}

		weatherSounds.clear();
	}

	/**
	 * special loop sound player for weather effects
	 */
	public void ambientLoopSound(String soundString) {

		for (Sound playing : weatherSounds) {
			playing.stop();
		}

		weatherSounds.clear();

		Sound sound = soundMap.get(soundString);
		Sound sting = soundMap.get("weatherStormSting"); // change this to unique sounds

		weatherSounds.add(sound);
		weatherSounds.add(sting);

		if (sound != null) {
			LOGGER.info("Playing sound effect, looping : " + soundString);
			sound.loop(1f);

			Runnable stingSound = new Runnable() {
				public void run() {
					randomPlaySound(sting);
				}
			};

			try {
				randomLoop.cancel(true);
			} catch (NullPointerException e) {
				LOGGER.info("No sting playing yet",e);
			}
			randomLoop = executor.scheduleAtFixedRate(stingSound, 0, 4, TimeUnit.SECONDS);

		} else {
			LOGGER.info(NO_REF + soundString);
		}
	}

	/*
	 * Helper method that provides a chance to play a sound at a random interval
	 * 
	 */
	private void randomPlaySound(Sound sound) {
		double random = Math.random();
		if (random > 0.65) {
			sound.play((float) random); // varies intensity
		}
	}

	/** pauses weather sounds which can be useful when going to menu
	 * 
	 */
	public void pauseWeatherSounds(){
		for (String weatherSound : onWeatherSounds) {
			stopSound(weatherSound);
		}
	}
	
	/** pauses weather sounds which can be useful when going to menu
	 * 
	 */
	public void unpauseWeatherSounds(){
		for (String weatherSound : onWeatherSounds) {
			playSound(weatherSound);
		}
	}
}