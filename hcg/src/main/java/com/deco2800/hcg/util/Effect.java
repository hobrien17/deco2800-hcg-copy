package com.deco2800.hcg.util;

public class Effect {

    private String name;
    private int damage;
    private int duration;
    private int cooldown;       // effect cooldown (in ms)
    private int delay;
    private long cooldownTimer;
    private double slowAmount;

    /**
     * Creates a new Effect with the given properties.
     *
     * @param name The name of the effect.
     * @param damage The damage the effect causes each tick.
     * @param duration The number of times the effect will be applied (the lifetime).
     * @param slowAmount The amount the player is slowed. Expressed as a percentage between 0 and 1. 0 is no slow
     *                   effect, 1 completely prevents movement.
     * @param cooldown The time (in seconds) before the effect may be applied again, after it has just been applied.
     * @param delay The time (in seconds) before the effect may be used for the first time.
     */
    public Effect(String name, int damage, double slowAmount, int cooldown, int duration, int delay) {
        // TODO add checks for invalid values
        this.name = name;
        this.damage = damage;
        this.slowAmount = slowAmount;
        this.cooldown = cooldown;
        this.duration = duration;
        this.delay = delay;

        resetCooldownTimer();
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
     * Returns the amount of damage caused by the effect when it is applied.
     *
     * @return Returns a positive integer denoting damage.
     */
    public int getDamage() {
        return damage;
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
    public String getName() { return name; }

    /**
     * Returns the cooldown time of the effect.
     *
     * @return Returns an integer denoting the cooldown time.
     */
    public int getCooldown() { return cooldown; }

    /**
     * Returns the delay time of the effect.
     *
     * @return Returns an integer denoting the delay time.
     */
    public int getDelay() { return delay; }

    /**
     * Returns the amount of slow caused by the effect when it is applied.
     *
     * @return Returns a double denoting the magnitude of the slow. 0 is no change, 1 reduces speed to 0.
     */
    public double getSlowAmount() {
        return slowAmount;
    }

    /**
     * Decrements the duration of the effect by one.
     */
    public void decrementDuration() {
        duration--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Effect effect = (Effect) o;

        if (damage != effect.damage) return false;
        if (duration != effect.duration) return false;
        if (Double.compare(effect.slowAmount, slowAmount) != 0) return false;
        if (cooldown != effect.cooldown) return false;
        if (cooldownTimer != effect.cooldownTimer) return false;
        return name != null ? name.equals(effect.name) : effect.name == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        result = 31 * result + damage;
        result = 31 * result + duration;
        temp = Double.doubleToLongBits(slowAmount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + cooldown;
        result = 31 * result + (int) (cooldownTimer ^ (cooldownTimer >>> 32));
        return result;
    }
}
