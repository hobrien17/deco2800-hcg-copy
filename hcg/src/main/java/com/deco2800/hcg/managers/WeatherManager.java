package com.deco2800.hcg.managers;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.Gdx;
import java.util.*;
import com.deco2800.hcg.actors.ParticleEffectActor;
import com.deco2800.hcg.types.Weathers;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.SoundManager;

/**
 * A class to manage the game's internal system of weather. Weather can be set
 * using the methods in this class Import com.deco2800.hcg.types.Weathers as
 * well as this one to use the weather system.
 *
 * @author Team 7 (Organic Java)
 */

public class WeatherManager extends Manager {

	SoundManager soundManager;

	// list of effects to be implemented
	// none
	// rain
	// snow
	// drought
	// storm (rain, clouds, lightning)
	// sandstorm

	ParticleEffect weather;
	ParticleEffectActor weatherActor;

	// onEffects: a list of effects that are currently on in the game.
	ArrayList<Weathers> onEffects;

	/**
	 * Constructor for weather manager
	 */
	public WeatherManager() {
		soundManager = (SoundManager) GameManager.get()
				.getManager(SoundManager.class);

		onEffects = new ArrayList<Weathers>();
		weather = new ParticleEffect();
		weather.start();

		// setWeather(Weathers.RAIN);
		// setWeather(Weathers.SNOW);
		// setWeather(Weathers.SANDSTORM);
		// setWeather(Weathers.WIND);
		// stopEffect();

		weatherActor = new ParticleEffectActor();
		weatherActor.add(weather, true);
	}

	/**
	 * Sets up visuals given a valid .p file for a weather effect.
	 * 
	 * @param fileName
	 *            filename of particle file image in "resources/particles/"
	 * 
	 * @require fileName refers to a .p file && weather has been declared &&
	 *          filename is in the path"resources/particles/"
	 */
	private void setUp(String fileName) {
		weather.load(Gdx.files.internal("resources/particles/" + fileName),
				Gdx.files.internal("resources/particles/"));
		for (int emitter = 0; emitter < weather.getEmitters().size; emitter++) {

			ParticleEmitter newEmitter = weather.getEmitters()
					.get(emitter);
			newEmitter.setPosition(Gdx.graphics.getWidth() / 2,
					Gdx.graphics.getHeight() / 2);

			// Scale is currently hardcoded; TO DO
			int scale = 9;
			float heightHighMax;
			float heightLowMax;
			float heightHighMin;
			float heightLowMin;
			float widthHighMax;
			float widthLowMax;
			float widthHighMin;
			float widthLowMin;
			
			heightHighMax = newEmitter.getSpawnHeight().getHighMax();
			newEmitter.getSpawnHeight().setHighMax(heightHighMax * scale);

			heightLowMax = newEmitter.getSpawnHeight().getLowMax();
			newEmitter.getSpawnHeight().setLowMax(heightLowMax * scale);

			heightHighMin = newEmitter.getSpawnHeight().getHighMin();
			newEmitter.getSpawnHeight().setHighMin(heightHighMin * scale);

			heightLowMin = newEmitter.getSpawnHeight().getLowMin();
			newEmitter.getSpawnHeight().setLowMin(heightLowMin * scale);

			widthHighMax = newEmitter.getSpawnWidth().getHighMax();
			newEmitter.getSpawnWidth().setHighMax(widthHighMax * scale * 2);

			widthLowMax = newEmitter.getSpawnWidth().getLowMax();
			newEmitter.getSpawnWidth().setLowMax(widthLowMax * scale * 2);

			widthHighMin = newEmitter.getSpawnWidth().getHighMin();
			newEmitter.getSpawnWidth().setHighMin(widthHighMin * scale * 2);

			widthLowMin = newEmitter.getSpawnWidth().getLowMin();
			newEmitter.getSpawnWidth().setLowMin(widthLowMin * scale * 2);
		}
	}

	/**
	 * Changes weather to given weather effect. If the provided error type is
	 * not valid, nothing happens.
	 * 
	 * @param weatherType:
	 *            int representation of desired weather type
	 */
	public void setWeather(Weathers weatherType) {
		if (onEffects.contains(weatherType)) {
			return;
		}
		ParticleEmitter emitter = new ParticleEmitter();
		// weather.getEmitters
		if (weatherType == Weathers.NONE){
			stopAllEffect();
			return;
		}
		switch (weatherType) {
		case RAIN:
			setUp("2dRain.p");
			break;
		case SNOW:
			setUp("2dSnow.p");
			break;
		case SANDSTORM:
			setUp("2dSandstorm.p");
			break;
		case WIND:
			setUp("2dWind.p");
			break;
		}
		
		soundManager.loopSound(enumToSoundFile(weatherType));
		onEffects.add(weatherType);
	}

	/**
	 * Turns off all weather effects.
	 */
	public void stopAllEffect() {
		for (Weathers weatherType : onEffects) {
			soundManager.stopSound(enumToSoundFile(weatherType));
		}

		weather.dispose();
		onEffects.clear();
	}

	/**
	 * Turns off specific weather effect if turned on, else do nothing
	 * 
	 * @param Weathers:
	 *            weather type you would like to turn off
	 * 
	 * @ensure weatherManager.getOnEffects() does not contain weathers
	 */
	public void stopEffect(Weathers weatherType) {
		if (!onEffects.contains(weatherType)) {
			return;
		}

		soundManager.stopSound(enumToSoundFile(weatherType));
		onEffects.remove(weatherType);
		weather.dispose();
	}

	/**
	 * getter method for all current effects turned on.
	 * 
	 * @return effects: a list of integers representing the emitters currently
	 *         off and on in the game. E.g. index RAIN would be 1 if rain is
	 *         currently on, 0 otherwise.
	 */
	public ArrayList<Weathers> getOnEffects() {
		return onEffects;
	}

	/**
	 * Getter method for the particle effect actor that handles the front end
	 * weather gui.
	 * 
	 * @return particle effect actor that handles the front end weather gui
	 */
	public ParticleEffectActor getActor() {
		return weatherActor;
	}

	/**
	 * Helper method to get the sound file name relating to the weatherType
	 * 
	 * @param weatherType:
	 *            type of weather
	 * @return string format of the sound file corresponding to the weatherType
	 * 
	 * @require weatherType != None
	 * 
	 */
	private String enumToSoundFile(Weathers weatherType) {
		switch (weatherType) {
		case RAIN:
			return "weatherRain";
		case SNOW:
			return "weatherSnow";
		case SANDSTORM:
			return "weatherSandStorm";
		case WIND:
			return "weatherWind";
		case DROUGHT:
			return "weatherDrought";
		case STORM:
			return "weatherStorm";
		default:
			return "";
		}
	}
}
