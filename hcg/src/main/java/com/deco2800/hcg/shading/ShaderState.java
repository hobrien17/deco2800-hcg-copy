package com.deco2800.hcg.shading;

import com.badlogic.gdx.graphics.Color;
import com.deco2800.hcg.managers.TimeManager;

/**
 * Container class to pass world / time / weather information down to the draw
 * call and into the render pipeline.
 * 
 * @author WillCS
 */
public class ShaderState {
    public static final int FILTER_DEFAULT = 0;
    
    /**
     * For standard day/night lighting these should both be (1, 1, 1, 1). /* Fiddle
     * with them for more nuanced day/night lighting /* We'll lerp between these as
     * the sun rises and sets.
     */
    public Color sunColour;
    public Color moonColour;
    
    /** How day it is. Determines how much to lerp between sun and moon colour. */
    private float dayAmount;
    
    /** Bitmask of all the colour filters that should be applied. */
    public int filters;
    
    /**
     * Create a new ShaderState with the given sun and moon colours.
     * 
     * @param sunColour
     *            The colour of sunlight.
     * @param moonColour
     *            The colour of moonlight.
     */
    public ShaderState(Color sunColour, Color moonColour) {
        this.sunColour = sunColour;
        this.moonColour = moonColour;
    }
    
    /**
     * Sets the current dayAmount from the time in TimeManager. Currently works off
     * a static 5am to 7pm day, may be modified eventually for seasons.
     * 
     * @param time
     *            The TimeManager containing the current time.
     */
    public void setTime(TimeManager time) {
        float distFromMidday = Math.abs(12.0F - (60.0F * time.getHours() + time.getMinutes()) / 60.0F);
        if(distFromMidday < 6) {
            dayAmount = 1;
        } else if(distFromMidday > 8) {
            dayAmount = 0;
        } else {
            dayAmount = (8.0F - distFromMidday) / 2.0F;
        }
    }
    
    /**
     * Get the current global ambient light colour based on the time of day.
     * 
     * @return The current ambient light value.
     */
    public Color getGlobalLightColour() {
        return moonColour.lerp(sunColour, dayAmount);
    }
}
