package com.deco2800.hcg.managers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.math.Vector3;
import com.deco2800.hcg.actors.ParticleEffectActor;
import com.deco2800.hcg.util.Box3D;

public class ParticleManager extends Manager {
	List<ParticleEffect> effects;
	ParticleEffectActor actor;
	
	public enum Effect {
		HEALTH ("health.p");
		
		private String file;
		
		Effect(String file) {
			this.file = file;
		}
		
		String getFile() {
			return file;
		}
	}
	
	public ParticleManager() {
		effects = new ArrayList<>();
		
		actor = new ParticleEffectActor();
	}
	
	public void start(Effect effectName, Box3D pos) {
		
        		
		String fileName = effectName.getFile();
		ParticleEffect effect = new ParticleEffect();
		effects.add(effect);
		effect.load(Gdx.files.internal("resources/particles/" + fileName),
				Gdx.files.internal("resources/particles/"));
		ParticleEmitter newEmitter = effect.getEmitters()
				.get(effect.getEmitters().size - 1);
		effect.setPosition(pos.getX(), pos.getY());
		//newEmitter.setPosition(pos.getX(), pos.getY());
		
		actor.add(effect, false);
	}
	
	public ParticleEffectActor getActor() {
		return actor;
	}
	
	
}
