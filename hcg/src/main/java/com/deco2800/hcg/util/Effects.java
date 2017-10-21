package com.deco2800.hcg.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector3;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.corpse_entities.BasicCorpse;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.ParticleEffectManager;
import com.deco2800.hcg.entities.Character;

import java.util.*;

/**
 * An effect container class to store the active effects on an entity.
 *
 * Every entity that implements Harmable maintains an Effects object, to store a set of the
 * active effects on the entity.
 *
 * @author Alex Subaric (deadmeu)
 */
public class Effects {
    
    private int count;

    private Set<Effect> currentEffects; // a set of the current active effects

    private Character owner;       // a reference to the owner of this effects collection. All effects contained
                                        // here will be applied to the owner

    // TODO Store a copy of the original attributes of the owner (allows for temporary effects to take place)
    // Maybe initialise this to -1 or something? That way if there is more than one effect that modifies the
    // attribute, it won't overwrite the value.

    /**
     * Creates a new Effects container to store a set of active effects.
     *
     * @param owner A reference to the owner of this set. Must not be null.
     *
     * @throws NullPointerException if owner is null.
     */
    public Effects(Character owner) {
        // Check for valid arguments
        if (owner == null) {
            throw new NullPointerException("Reference to owner cannot be null.");
        }

        // Set the class properties to the supplied values and initialise the effects set.
        this.owner = owner;
        currentEffects = new HashSet<>();

        // Save a snapshot of the owner's initial attributes for safety.
        saveOriginalStats();
    }

    /**
     * Creates a new Effects container to store a set of active effects.
     *
     * @param owner A reference to the owner of this set. Must not be null.
     * @param effects A non-null collection of effects to be merged into this set upon creation.
     *
     * @throws NullPointerException is owner is null, or effects is null.
     */
    public Effects(Character owner, Collection<Effect> effects) {
        // Check for valid arguments
        if (owner == null) {
            throw new NullPointerException("Reference to owner cannot be null.");
        }
        if (effects == null) {
            throw new NullPointerException("Effects collection cannot be null.");
        }

        // Set the class properties to the supplied values and initialise the effects set.
        this.owner = owner;
        currentEffects = new HashSet<>(effects);

        // Save a snapshot of the owner's initial attributes for safety.
        saveOriginalStats();
    }

    /**
     * Saves a snapshot of the current owner's attributes to revert to at a later time.
     */
    private void saveOriginalStats() {
        // TODO implement when owner's attributes have been implemented
    }

    /**
     * Returns a copy of the set of effects.
     *
     * @return Returns a HashSet of type Effect with all the active effects.
     */
    public Set<Effect> getEffects() {
        return new HashSet<Effect>(currentEffects);
    }

    /**
     * Adds a new effect to the set of active effects.
     *
     * @param newEffect The effect to be added to the set. Must not be null.
     *
     * @return Returns true if the effect was applied, false otherwise.
     *
     * @throws NullPointerException if newEffect is null.
     */
    public boolean addEffect(Effect newEffect) {
        // TODO check to see if effect is already in there, if it is, reset the cooldown?
        // Check for valid arguments
		if (newEffect == null)
			throw new NullPointerException(
					"Effect to be added cannot be null.");

		// Do things depending on the level of the new effect, and whether it overrides a current effect.
        for (Effect effect : currentEffects) {
			// Only do things if the type of effects are the same
			if (!checkNames(effect.getName(), newEffect.getName())) {
				continue;
			}

			if (newEffect.getLevel() - effect.getLevel() > 0) {         // new effect is stronger
                removeEffect(effect);
				return addEffect(newEffect);
			} else if (newEffect.getLevel() - effect.getLevel() == 0) { // effects
																		// are
																		// the
																		// same
																		// level
				effect.resetUseCounter();
                // We only want to reset the cooldowns on effects that don't apply damage, otherwise lots of
                // extra damage would be added each time the effect is given to the entity.
				if (effect.getDamage() == 0)
					effect.resetCooldownTimer();
				return true;
			} else { // new effect is weaker
                return false;
            }
        }

        // new effect doesn't override any existing effect
        return currentEffects.add(newEffect);
    }

    /**
     * Checks whether two names are the same.
     *
     * @param name1 The first name to be checked, a string.
     * @param name2 The second name to be checked, a string.
     * @return Returns true if the two strings are equal, false otherwise.
     */
    private boolean checkNames(String name1, String name2) {
        return name1.toUpperCase().equals(name2.toUpperCase());
    }

    /**
     * Adds a collection of new effects to the set of active effects.
     *
     * @param effects The collection of effects to be added to the set. Must not be null.
     *
     * @return Returns true if the current effects set changed as a result of the call.
     *
     * @throws NullPointerException if effects is null.
     */
	public boolean addAllEffects(Collection<Effect> effects) {
        // TODO check to see if effect is already in there, if it is, reset the cooldown?
        boolean newChange = false;

        // Check for valid arguments
        if (effects == null)
          throw new NullPointerException("Effects collection to be added cannot be null.");

		for (Effect effect : effects) {
			if (addEffect(effect))
				newChange = true;
		}

        return newChange;
    }

    /**
     * Removes an effect from the set of active effects.
     *
     * @param effect The effect to be removed from the set. Must not be null.
     *
     * @return Returns true if this set contained the specified element
     *
     * @throws NullPointerException if effect is null.
     */
    public boolean removeEffect(Effect effect) {
        // TODO check to make sure the effects are actually in there
        // Check for valid arguments
        if (effect == null)
          throw new NullPointerException("Effect to be removed cannot be null.");

        return currentEffects.remove(effect); // HashSet will only remove an effect if it is present
    }

    /**
     * Removes all of the elements from the current effects set.
     */
    public void clear() {
        currentEffects.clear();
    }
    /**
     * Attempts to apply each effect in the set of active effects to the owner. An effect is only applied if
     * it abides to the following conditions:
     *      - The effect is contained within the set of current effects
     *      - The effect has a use counter greater than 0
     *      - The effect is not on a cooldown
     * When all of these conditions have been met, the effect is applied to the owner and the cooldown timer for the
     * effect is restarted.
     */
    public void apply() {
        for (Effect effect : currentEffects) {
            Character thisCharacter = owner;

            if (effect.getUseCount() == 0) {
                if (!effect.onCooldown()) {
                    thisCharacter.resetSpeed();
                    currentEffects.remove(effect);
                    continue;
                }
            } else {
                effect.decrementUses();
            }

			// Only activate while buff is active
			if (effect.onCooldown()) {
				return;
			}
			
			if(effect.getUseCount() % 10 == 0) {
                if(effect.getSpeedModifier() < 1) {
                    spawnParticles(thisCharacter, "frozen.p");
                }
                
                if(effect.getDamage() > 0 && effect.getDuration() > 1) {
                    spawnParticles(thisCharacter, "fire.p");
                }
			}
                
			effect.startCooldownTimer();
			// Handle damage
			thisCharacter.takeDamage(effect.getDamage());
			if (thisCharacter.getHealthCur() > 0) {
				thisCharacter.changeSpeed(effect.getSpeedModifier());
				return;
			}
			Double prob = Math.random();
			if (prob > 0.3) {
				Corpse corpse = new BasicCorpse(owner.getPosX(),
						owner.getPosY(), 0);
				GameManager.get().getWorld().addEntity(corpse);
			}
			GameManager.get().getWorld().removeEntity(owner);
			AbstractEntity creator = effect.getCreator();
			if (creator != null && creator instanceof Character) {
				((Character) creator).killAlert(owner);
			}
			// Handle slows
			thisCharacter.changeSpeed(effect.getSpeedModifier());

			// Handle damage reduction, fire rate reduction, etc.
		}
	}
    
    protected void spawnParticles(AbstractEntity entity, String particleFile) {
        ParticleEffect hitEffect = new ParticleEffect();
        hitEffect.load(Gdx.files.internal("resources/particles/" + particleFile),
        Gdx.files.internal("resources/particles/"));
        Vector3 position = GameManager.get().worldToScreen(new Vector3(entity.getPosX(), entity.getPosY(), 0));
        hitEffect.setPosition(position.x, position.y);
        hitEffect.start();
        ((ParticleEffectManager) GameManager.get().getManager(ParticleEffectManager.class)).addEffect(entity, hitEffect);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
          return true;
        if (o == null || getClass() != o.getClass())
          return false;

        Effects effects = (Effects) o;

        return (currentEffects != null ? currentEffects.equals(effects.currentEffects) : effects.currentEffects == null)
                && (owner != null ? owner.equals(effects.owner) : effects.owner == null);
    }

    @Override
    public int hashCode() {
        int result = currentEffects != null ? currentEffects.hashCode() : 0;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);

        return result;
    }
}
