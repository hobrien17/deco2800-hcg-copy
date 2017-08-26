package com.deco2800.hcg.util;

import java.util.*;

/**
 * An effects manager class to handle multiple effects
 *
 * An effect may be many different things caused by many different entities,
 * such as a weapon causing an enemy to be slowed or simply dealing damage.
 *
 * This is currently a skeleton of the effects class and the structure can
 * change in the future.
 */
public class Effects {

    private ArrayList<String> currentEffects;
    private String[] effectTypes = {"damage", "freeze", "slow", "stun"};

    /**
     * Creates a new Effects object
     */
    public Effects() {
        currentEffects = new ArrayList<String>();
    }

    /**
     * Getter method to get the current list of effects available for use
     */
    public String[] getAvailableEffects() {
        return effectTypes;
    }

    /**
     * Getter method to return the current effects applied on an entity
     */
    public ArrayList<String> returnCurrentEffects() {
        return currentEffects;
    }

    /**
     * Method to apply an effect to an entity
     */
    private void setEffect(String effectName) {
        currentEffects.add(effectName);
    }
}