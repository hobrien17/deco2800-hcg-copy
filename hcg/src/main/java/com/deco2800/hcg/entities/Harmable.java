package com.deco2800.hcg.entities;

import com.deco2800.hcg.util.Effect;

import java.util.Collection;

/**
 * An interface to be used by entities that can be harmed in the game
 */
public interface Harmable {
    //TODO Whatever harmable things need to be able to do

    /**
     * Adds the given effect to an entity
     * @param effect the effect to add to the entity
     */
    void giveEffect(Effect effect);

    /**
     * Adds a collection of effects to an entity
     * @param effects the collection of effects to add
     */
    void giveEffect(Collection<Effect> effects);
}
