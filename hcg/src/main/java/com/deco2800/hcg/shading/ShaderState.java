package com.deco2800.hcg.shading;

import com.badlogic.gdx.graphics.Color;
import com.deco2800.hcg.managers.TimeManager;
import com.deco2800.hcg.util.MathHelper;

/**
 * Container class to pass world / time / weather information down to the draw
 * call and into the render pipeline.
 * 
 * @author WillCS
 */
public class ShaderState {
    public static final int FILTER_DEFAULT = 0;
    
    private boolean heatEnabled;
    private boolean bloomEnabled;
    
    /**
     * For standard day/night lighting these should be (1, 1, 1, 1) & (0.3, 0.3, 0.8, 1) 
     * respectively. Fiddle with them for more nuanced day/night lighting
     * We'll lerp between these as the sun rises and sets.
     */
    private Color sunColour;
    private Color moonColour;
    
    private float dayHeat;
    private float nightHeat;
    
    private float dayBloom;
    private float nightBloom;
    
    /** How day it is. Determines how much to lerp between sun and moon colour. */
    private float dayAmount;
    
    /** Bitmask of all the colour filters that should be applied. */
    public int filters;
    
    /** How overcast it is, will be set depending on weather */
    private float overcast;

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
        
        this.dayBloom = 1.0F;
        this.nightBloom = 0.0F;
        
        this.dayHeat = 0.2F;
        this.nightHeat = 0.0F;
    }
    
    /**
     * Sets the current dayAmount from the time in TimeManager.
     * Uses a nice sine wave.
     * 
     * @param time
     *            The TimeManager containing the current time.
     */
    public void setTime(TimeManager time) {
        float normalTime = ((3600.0F * time.getHours() + 60.0F * time.getMinutes() 
                    + time.getSeconds()) / 3600.0F) / 24.0F;
        dayAmount = 0.5F * (float) Math.cos(MathHelper.TWO_PI * normalTime + Math.PI) + 0.5F;
    }
    
    /**
     * Get the current global ambient light colour based on the time of day.
     * 
     * @return The current ambient light value.
     */
    public Color getGlobalLightColour() {
        return moonColour.lerp(sunColour, dayAmount);
    }
    
    /**
     * Get the amount of bloom that should be applied, based on the time of day.
     * 
     * @return The current bloom value.
     */
    public float getBloom() {
        if(bloomEnabled) {
            float scaleValue = dayAmount * dayAmount;
            return scaleValue * dayBloom + (1 - scaleValue) * nightBloom;
        }
        return 0F;
    }
    
    /**
     * Get the amount of heat distortion that should be applied, based on the time of day.
     * 
     * @return The current heat value.
     */
    public float getHeat() {
        if(heatEnabled) {
            float scaleValue = dayAmount * dayAmount;
            return scaleValue * dayHeat + (1 - scaleValue) * nightHeat;
        }
        return 0F;
    }

    /**
     * Get how overcast the shader is.
     *
     * @return current contrast
     */
    public float getContrast() {
        return this.overcast;
    }
    
    /**
     * Sets whether or not heat distortion should be applied.
     * 
     * @param heat
     *            Whether or not to use heat distortion.
     */
    public void setHeat(boolean heat) {
        this.heatEnabled = heat;
    }
    
    /**
     * Sets whether or not bloom should be applied.
     * 
     * @param bloom
     *            Whether or not to use heat bloom.
     */
    public void setBloom(boolean bloom) {
        this.bloomEnabled = true;
    }
    
    /**
     * Sets the contrast to resemble how gloomy/overcast the wetaher is
     *
     * @param contrast
     *          value from 0.0 to 1.0 as a percentage of overcast
     * @throws IllegalArgumentException
     */
    public void setContrast(float contrast) {
        if(contrast < 0.0 || contrast >1.0) {
            throw new IllegalArgumentException();
        }
        this.overcast = contrast;
    }

    /** Sets the day night heat values for shader state
     *
     */
    public void setValues(float contrast, float waves) {
        setContrast(contrast);
        this.dayHeat = waves;
        this.nightHeat = waves;

    }
}
