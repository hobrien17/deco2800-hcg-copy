package com.deco2800.hcg.managers;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.Gdx;
import java.util.*;


public class WeatherManager extends Manager implements TickableManager {

    // list of effects to be implemented
    // none
    // rain
    // snow
    // drought
    // storm (rain, clouds, lightning)
    // sandstorm


    ParticleEffect rain;
    ArrayList<ParticleEffect> effects;


    /**
     * Constructor
     */
    public WeatherManager() {
        effects = new ArrayList<ParticleEffect>();


        // maybe does not belong in constructor
        rain = new ParticleEffect();

        // set-up for rain
        rain.load(Gdx.files.internal("hcg/resources/particles/2dRain.p"),
                Gdx.files.internal("hcg/resources/particles/"));
        rain.getEmitters().first().setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
    }

    /**
     * Turns on a particle effect
     */
    public void startEffect(ParticleEffect effect) {
        effects.add(effect);
        effect.start();
    }

    /**
     * Turns off a particle effect
     */
    public void stopEffect(ParticleEffect effect) {
        effects.remove(effect);
    }

    /**
     * Handles incrementing time on tick event.
     *
     * @param gameTickCount
     *            of all game ticks so far.
     */
    public void onTick(long gameTickCount) {
        for (ParticleEffect ef: effects) {
            // this was the sample code for updating the particle effects, Ash to implement properly
            // rain.update(Gdx.graphics.getDeltaTime());
            // rain.draw();

            // reset animation if completed
            if (ef.isComplete()) {
                ef.reset();
            }
        }
    }




















}
