package com.deco2800.hcg.managers;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
//import com.deco2800.hcg.entities.Tickable;
import java.util.*;

public class WeatherManager extends Manager implements TickableManager {

	// list of effects to be implemented
	// none
	// rain
	// snow
	// drought
	// storm (rain, clouds, lightning)
	// sandstorm

	// rain: the particleEffect (display) for rain in the game
	ParticleEffect rain;

	// onEffects: a list of effects that is currently on in game.
	ArrayList<ParticleEffect> onEffects;

	// allEffects: all effects instantiated and loaded in the game
	ArrayList<ParticleEffect> allEffects;

	/**
	 * Constructor for weather manager
	 */
	public WeatherManager() {
		onEffects = new ArrayList<ParticleEffect>();
		allEffects = new ArrayList<ParticleEffect>();

		setUp(rain, "2dRain.p");
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
	private void setUp(ParticleEffect weatherEffect, String fileName) {
		if (allEffects.contains(weatherEffect)) {
			return;
		}

		weatherEffect = new ParticleEffect();

		// set-up for rain
		weatherEffect.load(
				Gdx.files.internal("resources/particles/" + fileName),
				Gdx.files.internal("resources/particles/"));
		weatherEffect.getEmitters().first().setPosition(
				Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		
		allEffects.add(weatherEffect);
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
	 * Handles incrementing time on tick event.
	 *
	 * @param gameTickCount
	 *            of all game ticks so far.
	 */
	public void onTick(long gameTickCount) {
		for (ParticleEffect ef : onEffects) {
			// this was the sample code for updating the particle effects, Ash
			// to implement properly
			// rain.update(Gdx.graphics.getDeltaTime());
			// rain.draw();

			// reset animation if completed
			if (ef.isComplete()) {
				ef.reset();
			}
		}
	}

}
