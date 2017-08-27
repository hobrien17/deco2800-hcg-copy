package com.deco2800.hcg.util;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.managers.GameManager;

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

    // We use a hashset because we don't want duplicate elements
    private HashSet<Effect> currentEffects;

    // The owner of this effects collection. All effects contained here will be
    // applied to the owner.
    private AbstractEntity owner;

    //private int originalSlow;

    public Effects(AbstractEntity owner) {
        this.owner = owner;
        currentEffects = new HashSet<>();

        saveOriginalStats();
    }

    public Effects(AbstractEntity owner, Collection<Effect> effects) {
        this.owner = owner;
        currentEffects = new HashSet<>(effects);

        saveOriginalStats();
    }

    private void saveOriginalStats() {
        // NOT IMPLEMENTED IN HARMABLE YET
//        originalSlow = owner.getSpeed();
//        originalHealth = owner.getHealth();
//        originalAttackSpeed = owner.getAttackSpeed();
    }

    /**
     * Getter method to get the current list of effects available for use
     */
    public HashSet<Effect> getEffects() {
        return new HashSet<Effect>(currentEffects);
    }

    public void addEffect(Effect effect) {
        // TODO check to see if effect is already in there, if it is, reset the cooldown?
        currentEffects.add(effect);
    }

    public void addAllEffects(Collection<Effect> effects) {
        // TODO check to see if effect is already in there, if it is, reset the cooldown?
        currentEffects.addAll(effects);
    }

    public void removeEffect(Effect effect) {
        // TODO check to make sure the effects are actually in there
        currentEffects.remove(effect);
    }

    public void apply() {
        for (Effect e : currentEffects) {
            if (e.getDuration() == 0) {
                currentEffects.remove(e);
                continue;
            } else {
                e.decrementDuration();
            }

            if (!e.onCooldown()) {
                e.startCooldownTimer();

                // TODO
                // Handle damage  -  NOT IMPLEMENTED IN HARMABLE YET
                //owner.takeDamage(e.getDamage());
                GameManager.get().getWorld().removeEntity(owner);

                // Handle slows  -  NOT IMPLEMENTED IN HARMABLE YET
                //owner.setSpeed(owner.getSpeed * e.getSlowAmount());

                // Handle damage reduction, fire rate reduction, etc.
            }
        }

    }
}