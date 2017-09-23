package com.deco2800.hcg.actors;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;

public class ParticleEffectActor extends Actor {
	Map<ParticleEffect, Boolean> effects;
	SpriteBatch batch;

	/**
	 * Constructor for ParticleEffect Actor
	 * 
	 * @param effect:
	 *            Particle effect displayed on screen
	 */
	public ParticleEffectActor() {
		super();
		effects = new HashMap<>();
	}
	
	public void add(ParticleEffect effect, boolean repeat) {
		effects.put(effect, repeat);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		for(ParticleEffect effect : effects.keySet()) {
			effect.draw(batch);
		}
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		for(ParticleEffect effect : effects.keySet()) {
			effect.update(delta);
		}
	}

	/**
	 * Allow completion and ignores continuous setting until emitter is started
	 * again
	 * 
	 */
	public void allowCompletion() {
		for(ParticleEffect effect : effects.keySet()) {
			effect.allowCompletion();
		}
	}

	/**
	 * Draws and renders particle effect
	 * 
	 */
	public void render() {
		batch = new SpriteBatch();
		batch.begin();
		for(Map.Entry<ParticleEffect, Boolean> entry : effects.entrySet()) {
			entry.getKey().update(Gdx.graphics.getDeltaTime());
			entry.getKey().draw(batch);
			// reset animation if completed
			if (entry.getKey().isComplete() && entry.getValue()) {
				entry.getKey().reset();
			} else if(entry.getKey().isComplete() && !entry.getValue()) {
				entry.getKey().dispose();
			}
		}
		
		batch.end();
	}
}