package com.deco2800.hcg.managers;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.Gdx;
import java.util.*;

import com.deco2800.hcg.actors.ParticleEffectActor;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.types.Weathers;

/**
 * A class to manage the game's particle effects.
 *
 * @author Bodhi Howe - @sinquios
 */

public class ParticleEffectManager extends Manager {

    ParticleEffectActor particleEffectActor;

    ArrayList<ParticleEffect> currentEffects;

    /**
     * Constructor for Particle Effect Manager
     */
    public ParticleEffectManager() {
        currentEffects = new ArrayList<ParticleEffect>();
        particleEffectActor = new ParticleEffectActor();
    }

    public void stopAllEffects() {
        currentEffects.clear();
    }

    public void stopEffect(ParticleEffect effect) {
        if (currentEffects.contains(effect)) {
            currentEffects.remove(effect);
        }
    }

    public ArrayList<ParticleEffect> getCurrentEffects() {
        return currentEffects;
    }

    public ParticleEffectActor getActor() {
        return particleEffectActor;
    }
    
    public void addEffect(ParticleEffect effect, AbstractEntity entity) {
        particleEffectActor.add(effect, entity);
    }
}
