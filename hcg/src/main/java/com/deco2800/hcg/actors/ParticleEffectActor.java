package com.deco2800.hcg.actors;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.*;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;

import com.deco2800.hcg.managers.GameManager;

public class ParticleEffectActor extends Actor {
	Map<ParticleEffect, Boolean> effects;
	SpriteBatch batch;

	/**
	 * Constructor for ParticleEffect Actor
	 */
	public ParticleEffectActor() {
		super();
		effects = new HashMap<>();
	}
	
	/**
	 * Adds the given particle effect to this actor to be rendered.
	 * @param effect: The ParticleEffect to add to this actor.
	 * @param repeat: Weather or not to repeatedly render this effect.
	 */
	public void add(ParticleEffect effect, boolean repeat) {
		effects.put(effect, repeat);
	}

	/**
	 * Override the draw method for Actor to draw all effects.
	 * @param batch: The Batch of sprites in the effect to draw.
	 * @param parentAlpha: As for Actor class.
	 */
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.setProjectionMatrix(GameManager.get().getCamera().combined);
		for(ParticleEffect effect : effects.keySet()) {
			effect.draw(batch);
		}
	}

	/**
	 * Override the act method for Actor to update all effects.
	 * @param delta: As for Actor class.
	 */
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
	 */
	public void allowCompletion() {
		for(ParticleEffect effect : effects.keySet()) {
			effect.allowCompletion();
		}
	}

	/**
	 * Renders all particle effects
	 */
	public void render() {
		batch = new SpriteBatch();
		batch.setProjectionMatrix(GameManager.get().getCamera().combined);
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