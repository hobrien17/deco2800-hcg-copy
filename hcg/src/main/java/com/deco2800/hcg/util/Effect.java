package com.deco2800.hcg.util;

import com.deco2800.hcg.entities.AbstractEntity;

/**
 * An effect class to store all the properties of an effect. Effects may be added to an Effects container which
 * each harmable entity has.
 *
 * @author Alex Subaric (deadmeu)
 */
public class Effect {

    // TODO look at replacing the cooldown timer implementation to something that incorporates the stopwatch manager.
    private String name;        // name of the effect
    private int level;          // level of the effect
    private int damage;         // damage the effect deals
    private int duration;       // lifetime of the effect (in # of applications)
    private int useCounter;     // counter to keep track of number of applications.
    private int cooldown;       // effect cooldown (in ms)
    private int delay;          // delay until effect is ready for use (in ms)
    private long cooldownTimer; // timer used to keep track of whether effect is on ready to be used
    private float speedModifier;  // amount (in percentage) of slow to apply. 0 is none, 1 is 100% slow effect
    private AbstractEntity creator; // the character that gave the effect to this owner.

    /**
     * Creates a new Effect with the given properties.
     *
     * @param name The name of the effect, a non-null string. Cannot be empty.
     * @param level The level of the effect, an integer greater than 0.
     * @param damage The damage caused each application, an integer greater than or equal to 1.
     * @param speedModifier The new speed of the player relative to the current movement speed.
     * @param cooldown The time (in ms) before the effect may be applied again, after it has just been applied. An
     *                 integer greater than or equal to 0.
     * @param duration The number of times the effect will be applied (the lifetime). An integer greater than or equal
     *                 to 1.
     * @param delay The time (in ms) before the effect may be used for the first time. An integer greater than or equal
     *              to 1.
     *
     * @throws NullPointerException if name is null.
     * @throws IllegalArgumentException if an argument is not valid.
     */
    public Effect(String name, int level, int damage, float speedModifier, int cooldown, int duration, int delay, AbstractEntity creator) {
        // Check for valid arguments
        if (name == null) {
            throw new NullPointerException("Effect name cannot be null.");
        }

        if (name.isEmpty()) {
            throw new IllegalArgumentException("Effect name cannot be empty.");
        }

        if (!(level >= 1)) {
            throw new IllegalArgumentException("Level must be a positive integer and at least 1.");
        }

        if (!(speedModifier >= 0)) {
            throw new IllegalArgumentException("Slow amount must be >= 0.");
        }
        if (!(cooldown >= 0)) {
            throw new IllegalArgumentException("Cooldown must be a positive integer.");
        }
        if (!(duration >= 0)) {
            throw new IllegalArgumentException("Duration must be a positive integer.");
        }
        if (!(delay >= 0)) {
            throw new IllegalArgumentException("Delay must be a positive integer.");
        }

        // Set the class properties to the supplied values
        this.name = name;
        this.level = level;
        this.damage = damage;
        this.speedModifier = speedModifier;
        this.cooldown = cooldown;
        this.duration = duration;
        this.delay = delay;

        // Set the cooldown timer based on the supplied delay value
        this.cooldownTimer = System.currentTimeMillis() - (cooldown - delay);
        this.creator = creator;

        resetUseCounter();
    }

    /**
     * Returns whether the effect is on cooldown.
     *
     * @return Returns true if the effect is on cooldown, false otherwise.
     */
    public boolean onCooldown() {
        return System.currentTimeMillis() - cooldownTimer < cooldown;
    }

    /**
     * Resets the use counter back to the defined duration.
     */
    public void resetUseCounter() {
        useCounter = duration;
    }

    /**
     * Resets the effect's active cooldown timer.
     */
    public void resetCooldownTimer() {
        cooldownTimer = 0;
    }

    /**
     * Starts the effect's active cooldown timer.
     */
    public void startCooldownTimer() {
        cooldownTimer = System.currentTimeMillis();
    }

    /**
     * Returns the level of the effect.
     *
     * @return Returns a positive integer (minimum of 1) denoting the level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Returns the amount of damage caused by the effect when it is applied.
     *
     * @return Returns a positive integer denoting damage.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Returns the number of applications left until the effect is killed.
     *
     * @return Returns a positive integer denoting number of uses left.
     */
    public int getUseCount() {
        return useCounter;
    }

    /**
     * Returns the number of times the effect may be applied (duration).
     *
     * @return Returns a positive integer denoting the duration.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Returns the name of the effect.
     *
     * @return Returns a string denoting the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the cooldown time of the effect.
     *
     * @return Returns an integer denoting the cooldown time (in ms).
     */
    public int getCooldown() {
        return cooldown;
    }

    /**
     * Returns the delay time of the effect.
     *
     * @return Returns an integer denoting the delay time (in ms).
     */
    public int getDelay() {
        return delay;
    }

    /**
     * Returns the speed modifier.
     *
     * @return Returns a float denoting the new relative speed.
     */
    public float getSpeedModifier() {
        return speedModifier;
    }

    /**
     * Returns the creator.
     *
     * @return Returns a Character denoting the effect creator.
     */
    public AbstractEntity getCreator() {
        return creator;
    }

    /**
     * Decrements the use counter of the effect by one.
     */
    public void decrementUses() {
        useCounter--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
          return true;
        if (o == null || getClass() != o.getClass())
          return false;

        Effect effect = (Effect) o;

		if (level != effect.level 
				|| damage != effect.damage
				|| duration != effect.duration 
				|| cooldown != effect.cooldown
				|| delay != effect.delay) {
			return false;
		}

		return Float.compare(effect.speedModifier, speedModifier) == 0
                && (name != null ? name.equals(effect.name) : effect.name == null);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;

        result = name != null ? name.hashCode() : 0;
        result = 31 * result + level;
        result = 31 * result + damage;
        result = 31 * result + duration;
        result = 31 * result + cooldown;
        result = 31 * result + delay;
        temp = Float.floatToIntBits(speedModifier);
        result = 31 * result + (int) (temp ^ (temp >>> 32));

        return result;
    }
}
