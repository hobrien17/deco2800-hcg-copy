package com.deco2800.hcg.managers;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import java.util.*;
import static java.util.Arrays.*;
import com.deco2800.hcg.actors.ParticleEffectActor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deco2800.hcg.types.Weathers;

public class WeatherManager extends Manager {

	// list of effects to be implemented
	// none
	// rain
	// snow
	// drought
	// storm (rain, clouds, lightning)
	// sandstorm

	// ParticleEffect rain;
	// ParticleEffect snow;
	// ParticleEffect wind;
	// ParticleEffect sandstorm;

	// Eventually move this to a public enum
	private final int NONE = 0;
	private final int RAIN = 1;
	private final int SNOW = 2;

	private Weathers weathers;
	ParticleEffect weather;
	ParticleEffectActor weatherActor;
	SpriteBatch batch;

	// onEffects: a list of effects that is currently on in game.
	ArrayList<Integer> onEffects;

	/**
	 * Constructor for weather manager
	 */
	public WeatherManager() {

		//boolean snow = false;

		onEffects = new ArrayList<Integer>();
		weather = new ParticleEffect();
		weather.start();

		addWeather(RAIN);
		//addWeather(SNOW);

		weatherActor = new ParticleEffectActor(weather);

	}

	/**
	 * sets up each weather effect given, including loading image files
	 * 
	 * @param weatherEffect
	 *            declared weather type
	 * @param fileName
	 *            filename of particle file image in "resources/particles/"
	 * 
	 * @require weatherEffect has been declared and NOT instantiated && filename
	 *          is in the path"resources/particles/"
	 * 
	 * @ensure allEffects contains weatherEffect
	 */
	private void setUp(String fileName) {
		// CHECK FOR WHETHER THIS WEATHER IS ALREADY ON; LATER WHEN MULTIPLE 
		// CONDITIONS ALLOWED (NOT NECESSARY YET)


		weather.load(
				Gdx.files.internal("resources/particles/" + fileName),
				Gdx.files.internal("resources/particles/"));

		ParticleEmitter newEmitter = weather.getEmitters().get(weather.getEmitters().size - 1);
		newEmitter.setPosition(Gdx.graphics.getWidth() / 2, 
				Gdx.graphics.getHeight() / 2);
		



		/* CURRENTLY HARDCODED SCALE; CHANGE TO JUST COVER SCREEN (NEEDS TO 
		UPDATE WITH RESIZE). */
		int scale = 9;

		float heightHighMax = newEmitter.getSpawnHeight().getHighMax();
		newEmitter.getSpawnHeight().setHighMax(heightHighMax * scale);

		float heightLowMax = newEmitter.getSpawnHeight().getLowMax();
		newEmitter.getSpawnHeight().setLowMax(heightLowMax * scale);

		float heightHighMin = newEmitter.getSpawnHeight().getHighMin();
		newEmitter.getSpawnHeight().setHighMin(heightHighMin * scale);

		float heightLowMin = newEmitter.getSpawnHeight().getLowMin();
		newEmitter.getSpawnHeight().setLowMin(heightLowMin * scale);

		float widthHighMax = newEmitter.getSpawnWidth().getHighMax();
		newEmitter.getSpawnWidth().setHighMax(widthHighMax * scale * 2);

		float widthLowMax = newEmitter.getSpawnWidth().getLowMax();
		newEmitter.getSpawnWidth().setLowMax(widthLowMax * scale * 2);

		float widthHighMin = newEmitter.getSpawnWidth().getHighMin();
		newEmitter.getSpawnWidth().setHighMin(widthHighMin * scale * 2);

		float widthLowMin = newEmitter.getSpawnWidth().getLowMin();
		newEmitter.getSpawnWidth().setLowMin(widthLowMin * scale * 2);
	}

	/**
	 * sets up each weather effect given, including loading image files
	 * 
	 * @param weatherEffect
	 *            declared weather type
	 * @param fileName
	 *            filename of particle file image in "resources/particles/"
	 * 
	 * @require weatherEffect has been declared and NOT instantiated && filename
	 *          is in the path"resources/particles/"
	 * 
	 * @ensure allEffects contains weatherEffect
	 */
	public void addWeather(int weatherType) {
		if (onEffects.contains(weatherType)) {
			return;
		}
		ParticleEmitter emitter = new ParticleEmitter();
		switch (weatherType) {
			case (NONE):
				// Turn off all weather conditions
				weather.dispose();
				break;
			case (RAIN):
				setUp("2dRain.p");
				break;
			case (SNOW):
				setUp("2dSnow.p");
				break;
			default:
				// Do nothing if weatherType is not an implemented weather type
				return;
		}
	}

	public void setWeather(int weatherType) {
		addWeather(NONE);
		addWeather(weatherType);
	}

	/**
	 * Turns off a particle effect, if getOnEffects() does not contain effect,
	 * nothing will happen
	 * 
	 * @ensure this.getOnEffects() does not contain effect
	 */
	public void stopEffect(int weatherType) {
		// THIS IS NOT WORKING AT THE MOMENT; DO NOT TEST (SOZ)

		int index = onEffects.indexOf(weatherType);
		if (index != -1) {
			onEffects.remove(index);
			//weather.getEmitters().get(index).dispose();
			//weather.getEmitters().remove(index);
		}
	}

	/**
	 * getter method for all current effects turned on.
	 * 
	 * Please note that this is a deep copy of the effects on list
	 * 
	 * @return effects: a list of integers representing the emitters currently 
	 * 					off and on in the game. E.g. index RAIN would be 1 if
	 * 					rain is currently on, 0 otherwise.
	 */
	public ArrayList<Integer> getOnEffects() {
		return onEffects;
	}



	//////////// THIS METHOD IS REDUNDANT; ONLY HERE TO SATISFY ALLEFFECTS TEST //////////
	/**
	 * getter method for all effects turned instantiated.
	 * 
	 * Please note that this is a deep copy of the effects on list
	 * 
	 * @return effects: all effects in the game
	 */
	public ArrayList<ParticleEffect> getAllEffects() {
		ArrayList<ParticleEffect> temp = new ArrayList<ParticleEffect>();
		temp.add(weather);
		return temp;
	}

	public ParticleEffectActor getActor() {
		return weatherActor;
	}

}
