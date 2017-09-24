package com.deco2800.hcg.shading;

import com.badlogic.gdx.graphics.Color;

/**
 * Interface to be implemented by any entity than can emit light.
 */
public interface LightEmitter {
    /**
     * Grab the current light colour of this entity.
     * 
     * @return This entity's current light colour.
     */
    Color getLightColour();
    
    /**
     * Grab the current light power of this entity. If it shouldn't emit light right
     * now, return 0.
     * 
     * @return This entity's current light power.
     */
    float getLightPower();
}
