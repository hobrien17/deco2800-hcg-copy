package com.deco2800.hcg.actors;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.Gdx;

public class ParticleEffectActor extends Actor {
	ParticleEffect effect;
	SpriteBatch batch;

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

	// public void start() {
	// 	effect.start();
	// }

	public void allowCompletion() {
		effect.allowCompletion();
	}

	public void render() {
		// this was the sample code for updating the particle effects, Ash
		// to implement properly
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