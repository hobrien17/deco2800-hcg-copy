package com.deco2800.hcg.managers;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.Gdx;
import java.util.*;

import com.deco2800.hcg.actors.ParticleEffectActor;
import com.deco2800.hcg.types.Weathers;

/**
 * A class to manage the game's internal system of weather. Weather can be set
 * using the methods in this class Import com.deco2800.hcg.types.Weathers as
 * well as this one to use the weather system.
 *
 * @author Team 7 (Organic Java)
 */

public class WeatherManager extends Manager {

	SoundManager soundManager;
	ShaderManager shaderManager;
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
		shaderManager = (ShaderManager) GameManager.get().getManager(ShaderManager.class);

		onEffects = new ArrayList<Weathers>();
		weather = new ParticleEffect();
		weather.start();
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
		this.resize();
	}

	/**
	 * Resize the current weather effect to fit the current screen.
	 */
	public void resize() {
		for (int emitter = 0; emitter < weather.getEmitters().size; emitter++) {
			ParticleEmitter newEmitter = weather.getEmitters()
					.get(emitter);
			newEmitter.setPosition(0, 0);

			newEmitter.getSpawnHeight().setHighMax(2*Gdx.graphics.getHeight());
			newEmitter.getSpawnHeight().setHighMin(2*Gdx.graphics.getHeight());
			newEmitter.getSpawnHeight().setLowMax(-Gdx.graphics.getHeight());
			newEmitter.getSpawnHeight().setLowMin(-Gdx.graphics.getHeight());

			newEmitter.getSpawnWidth().setHighMax(2*Gdx.graphics.getWidth());
			newEmitter.getSpawnWidth().setHighMin(2*Gdx.graphics.getWidth());
			newEmitter.getSpawnWidth().setLowMax(-Gdx.graphics.getWidth());
			newEmitter.getSpawnWidth().setLowMin(-Gdx.graphics.getWidth());

			newEmitter.setMaxParticleCount(2*newEmitter.getMaxParticleCount());
			newEmitter.setMinParticleCount(2*newEmitter.getMinParticleCount());
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
		//Color baseColor = new Color(0.3f, 0.3f,0.5f, 1);
		switch (weatherType) {
		case RAIN:
			setUp("2dRain.p");
			shaderManager.setOvercast(0.3f);
			break;
		case SNOW:
			setUp("2dSnow.p");
			shaderManager.setOvercast(0.2f);
			break;
		case SANDSTORM:
			setUp("2dSandstorm.p");
			shaderManager.setOvercast(0f);
			break;
		case WIND:
			setUp("2dWind.p");
			shaderManager.setOvercast(0f);
			break;
		case DROUGHT:
			setUp("2dDrought.p");
			//shaderManager.setCustom(0f, 0.15f, 0f, baseColor, 1000);
			break;
		case STORM:
			setUp("2dStorm.p");
			shaderManager.setOvercast(0.9f);
			break;
		}
		
		soundManager.ambientLoopSound(enumToSoundFile(weatherType));
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
	 * @param weatherType:
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
