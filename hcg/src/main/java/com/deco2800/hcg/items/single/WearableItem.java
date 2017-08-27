package com.deco2800.hcg.items.single;

import com.deco2800.hcg.items.SingleItem;

/**
 * The WearableItem class represents objects that can be worn
 * by the player (i.e. armor, accessories etc.)
 */
public abstract class WearableItem extends SingleItem{

    public enum WearableType {HEAD, CHEST, LEGS, FEET}
    protected WearableType type;

    /**
     * gets the type of wearable item
     * @return enumerated
     */
    public WearableType getType() {
        return this.type;
    }

    /**
     * Checks if the item is armour or character customisation item
     *
     * @return Whether or not item is wearable
     */
    @Override
    public boolean isWearable() {
        return true;
    }

    /**
     * Checks if item is a weapon/ potion etc. and can be held in hot bar
     *
     * @return Whether or not item can be equipped in hot bar
     */
    @Override
    public boolean isEquippable() {
        return false;
    }
}
