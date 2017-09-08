package com.deco2800.hcg.buffs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An AbstractBuff provides a basis for buffs that can be applied
 * to the player or other entities within the game.
 *
 * author: danie
 */
public class Buff {

    // The name of the buff
    private String name;
    // The description of the buff's effects
    private String description;
    // The duration of the buff
    private int duration;
    // The set of effects the buff has
    private HashMap<String, Float> effects;
    // The line separator for the current system
    private static final String LINE_SEPARATOR =
            System.getProperty("line.separator");


    /**
     * Creates a new Buff with the given name, description and duration.
     * @param name the name of the buff
     * @param description the description of the buff
     * @param duration the duration of the buff
     */
    public Buff(String name, String description, int duration) {
        this.name = name;
        this.description = description;
        this.duration = duration;

        effects = new HashMap<>();
    }

    /**
     * Returns the buff's name
     * @return the name of the buff
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the buff's description
     * @return the description of the buff
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the buff duration.
     * @return the duration of the buff
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Returns a list of effects caused by the buff
     * @return a list of strings that describe the effects caused by the buff
     */
    public List<String> getEffects() {
        List<String> returnList = new ArrayList<String>();
        for (Map.Entry<String, Float> entry : effects.entrySet()) {
            returnList.add(entry.getKey());
        }

        return returnList;
    }

    /**
     * Returns the value corresponding to the given effect. If the buff does
     * not have the effect, returns -1.
     *
     * @param effectName the effect whose value needs to be retrieved
     * @return the value associated with a given effect, or -1 if the effect
     *          does not exist.
     */
    public float getEffect(String effectName) {
        if (effects.containsKey(effectName)) {
            return effects.get(effectName);
        }
        return (float) -1;
    }

    /**
     * Adds an effect to the buff
     * @param name the name of the effect to add
     * @param value the corresponding value of the effect.
     */
    public void addEffect(String name, Float value) {
        effects.put(name, value);
    }

    /**
     * Reduces the duration of the buff by the given amount.
     * If amount is a negative number, this method makes no changes.
     *
     * @param amount the amount the duration should be decreased by
     */
    public void decrementDuration(int amount) {
        if (amount < 0) {
            return;
        } else if (amount > duration) {
            duration = 0;
            return;
        }
        duration -= amount;
    }

    @Override
    public String toString() {
        return "Buff: " + getName() + LINE_SEPARATOR +
                "Duration: " + getDuration() + LINE_SEPARATOR +
                "Description: " + getDescription() + LINE_SEPARATOR;
    }
}
