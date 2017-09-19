package com.deco2800.hcg.managers;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.Gdx;
import java.util.*;
import com.deco2800.hcg.actors.ParticleEffectActor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deco2800.hcg.types.Weathers;

/**
 * A class to manage the game's internal system of weather. Weather can be set using the methods in this class
 * Import com.deco2800.hcg.types.Weathers as well as this one to use the weather system.
 *
 * @author Team 7 (Organic Java)
 */

public class WeatherManager extends Manager {

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
		onEffects = new ArrayList<Weathers>();
		weather = new ParticleEffect();
		weather.start();

		// setWeather(Weathers.RAIN);
		// setWeather(Weathers.SNOW);
		// setWeather(Weathers.SANDSTORM);
		// setWeather(Weathers.WIND);
		// stopEffect();

		weatherActor = new ParticleEffectActor(weather);
	}

	/**
	 * Sets up visuals given a valid .p file for a weather effect.
	 * 
	 * @param fileName
	 *            filename of particle file image in "resources/particles/"
	 * 
	 * @require fileName refers to a .p file && weather has been declared && 
	 * 			filename is in the path"resources/particles/"
	 */
	private void setUp(String fileName) {
		weather.load(Gdx.files.internal("resources/particles/" + fileName),
				Gdx.files.internal("resources/particles/"));
		ParticleEmitter newEmitter = 
				weather.getEmitters().get(weather.getEmitters().size - 1);
		newEmitter.setPosition(Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() / 2);

		// Scale is currently hardcoded; TO DO
		int scale = 9;
		float heightHighMax, heightLowMax, heightHighMin, heightLowMin, 
				widthHighMax, widthLowMax, widthHighMin, widthLowMin;
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
		//weather.getEmitters
		switch (weatherType) {
			case NONE:
				// Turn off all weather conditions
				stopEffect();
				break;
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
		
		onEffects.add(weatherType);
	}

	/**
	 * Turns off weather effects.
	 */
	public void stopEffect() {
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
}
