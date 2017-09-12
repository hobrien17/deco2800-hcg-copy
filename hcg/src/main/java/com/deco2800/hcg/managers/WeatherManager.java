package com.deco2800.hcg.managers;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
//import com.deco2800.hcg.entities.Tickable;
import java.util.*;
import com.deco2800.hcg.actors.ParticleEffectActor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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

	ParticleEffect weather;
	ParticleEffectActor weatherActor;
	SpriteBatch batch;

	// onEffects: a list of effects that is currently on in game.
	ArrayList<ParticleEffect> onEffects;

	// allEffects: all effects instantiated and loaded in the game
	// NOTE: REALLY SHITTY TEST THAT CHECKS THERE ARE ALL EFFECTS HAVE 4 
	ArrayList<ParticleEffect> allEffects;

	/**
	 * Constructor for weather manager
	 */
	public WeatherManager() {

		boolean snow = false;

		onEffects = new ArrayList<ParticleEffect>();
		allEffects = new ArrayList<ParticleEffect>();

		if (snow) {
			weather = setUp(weather, "2dSnow.p");
		} else {
			weather = setUp(weather, "2dRain.p");
		}
		startEffect(weather);

		//batch = new SpriteBatch();
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
	private ParticleEffect setUp(ParticleEffect weatherEffect, String fileName) {
		// if (allEffects.contains(weatherEffect)) {
		// 	return;
		// }

		weatherEffect = new ParticleEffect();

		weatherEffect.load(
				Gdx.files.internal("resources/particles/" + fileName),
				Gdx.files.internal("resources/particles/"));
		weatherEffect.getEmitters().first().setPosition(
				Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		
		ParticleEmitter rain = weatherEffect.getEmitters().first();

		int scale = 9;

		float heightHighMax = rain.getSpawnHeight().getHighMax();
		rain.getSpawnHeight().setHighMax(heightHighMax * scale);

		float heightLowMax = rain.getSpawnHeight().getLowMax();
		rain.getSpawnHeight().setLowMax(heightLowMax * scale);

		float heightHighMin = rain.getSpawnHeight().getHighMin();
		rain.getSpawnHeight().setHighMin(heightHighMin * scale);

		float heightLowMin = rain.getSpawnHeight().getLowMin();
		rain.getSpawnHeight().setLowMin(heightLowMin * scale);

		float widthHighMax = rain.getSpawnWidth().getHighMax();
		rain.getSpawnWidth().setHighMax(widthHighMax * scale * 2);

		float widthLowMax = rain.getSpawnWidth().getLowMax();
		rain.getSpawnWidth().setLowMax(widthLowMax * scale * 2);

		float widthHighMin = rain.getSpawnWidth().getHighMin();
		rain.getSpawnWidth().setHighMin(widthHighMin * scale * 2);

		float widthLowMin = rain.getSpawnWidth().getLowMin();
		rain.getSpawnWidth().setLowMin(widthLowMin * scale * 2);

		allEffects.add(weatherEffect);
		return weatherEffect;
	}

	/**
	 * Turns on a particle effect,
	 * 
	 * If this.getOnEffects() contains effect, nothing will happen
	 * 
	 * @param effect
	 *            the effect that will to be started
	 * 
	 * @require effect != null
	 * 
	 * @ensure this.getOnEffects() contains effect
	 */
	public void startEffect(ParticleEffect effect) {
		onEffects.add(effect);
		effect.start();
	}

	/**
	 * Turns off a particle effect, if getOnEffects() does not contain effect,
	 * nothing will happen
	 * 
	 * @ensure this.getOnEffects() does not contain effect
	 */
	public void stopEffect(ParticleEffect effect) {
		if (onEffects.contains(effect)) {
			onEffects.remove(effect);
		}
	}

	/**
	 * getter method for all current effects turned on.
	 * 
	 * Please note that this is a deep copy of the effects on list
	 * 
	 * @return effects: all current effects turned on in the game
	 */
	public ArrayList<ParticleEffect> getOnEffects() {
		return new ArrayList<ParticleEffect>(onEffects);
	}

	/**
	 * getter method for all effects turned instantiated.
	 * 
	 * Please note that this is a deep copy of the effects on list
	 * 
	 * @return effects: all effects in the game
	 */
	public ArrayList<ParticleEffect> getAllEffects() {
		return new ArrayList<ParticleEffect>(allEffects);
	}

	public ParticleEffectActor getActor() {
		return weatherActor;
	}

	public enum Weather {
		NONE, RAIN, SNOW, DROUGHT, STORM, SANDSTORM
	}

}
