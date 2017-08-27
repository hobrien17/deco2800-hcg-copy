package com.deco2800.hcg.util;

public class Effect {

    private String  name;
    private int     damage;
    private int     duration;
    private double  slowAmount;
    private int     cooldown;       // effect cooldown (in ms)
    private long    cooldownTimer;

    public Effect(String name, int damage, int duration, double slowAmount, int cooldown) {
        // TODO add checks for invalid values
        this.name = name;
        this.name = name;
        this.damage = damage;
        this.duration = duration;
        this.slowAmount = slowAmount;
        this.cooldown = cooldown;

        resetCooldownTimer();
    }

    public boolean onCooldown() {
        return System.currentTimeMillis() - cooldownTimer < cooldown;
    }

    public void resetCooldownTimer() {
        cooldownTimer = 0;
    }

    public void startCooldownTimer() {
        cooldownTimer = System.currentTimeMillis();
    }

    public int getDamage() {
        return damage;
    }

    public int getDuration() {
        return duration;
    }

    public void decrementDuration() {
        duration--;
    }

    public double getSlowAmount() {
        return slowAmount;
    }




}
