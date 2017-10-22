package com.deco2800.hcg.managers;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import java.util.*;

import com.deco2800.hcg.actors.ParticleEffectActor;
import com.deco2800.hcg.entities.AbstractEntity;

/**
 * A class to manage the game's particle effects.
 *
 * @author Bodhi Howe - @sinquios
 */

public class ParticleEffectManager extends Manager {

    ParticleEffectActor particleEffectActor;

    /**
     * Constructor for Particle Effect Manager
     */
    public ParticleEffectManager() {
        particleEffectActor = new ParticleEffectActor();
    }

    public ParticleEffectActor getActor() {
        return particleEffectActor;
    }
    
    public void addEffect(AbstractEntity entity, ParticleEffect effect) {
        particleEffectActor.add(entity, effect);
    }
}
