package com.deco2800.hcg.actors;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.Gdx;

public class ParticleEffectActor extends Actor {
	ParticleEffect effect;
	SpriteBatch batch;

	/**
	 * Constructor for ParticleEffect Actor
	 * 
	 * @param effect:
	 *            Particle effect displayed on screen
	 */
	public ParticleEffectActor(ParticleEffect effect) {
		super();
		this.effect = effect;
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		effect.draw(batch);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		effect.update(delta);
	}

	/**
	 * Allow completion and ignores continuous setting until emitter is started
	 * again
	 * 
	 */
	public void allowCompletion() {
		effect.allowCompletion();
	}

	/**
	 * Draws and renders particle effect
	 * 
	 */
	public void render() {
		batch = new SpriteBatch();
		batch.begin();
		effect.update(Gdx.graphics.getDeltaTime());
		effect.draw(batch);

		// reset animation if completed
		if (effect.isComplete()) {
			effect.reset();
		}
		batch.end();
	}
}