package com.deco2800.hcg.entities.garden_entities.seeds;

import com.deco2800.hcg.entities.garden_entities.plants.AbstractGardenPlant;
import com.deco2800.hcg.items.Item;

public class GrassSeed extends AbstractSeed {

    @Override
    public String getName() {
        return "Grass Seed";
    }

    /**
     * Returns the current stack size of this item.
     *
     * @return the current stack size of this item. Always returns 1 for
     * non-stackable items.
     */
    @Override
    public int getStackSize() {
        return 0;
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

    @Override
    public void setTexture(String texture) {
        this.setTexture("Grass Seed");

    }

    /**
     * Set this item's current stack size to the given number.
     *
     * @param number the new stack size of this item.
     */
    @Override
    public void setStackSize(int number) throws IllegalArgumentException {

    }

    /**
     * Determine whether or not this Item and the given Item are functionally the
     * same item. Disregard stack size.
     *
     * @param item The item to compare this item to.
     * @return whether or not this item and the given item are functionally the
     * same.
     */
    @Override
    public boolean sameItem(Item item) throws IllegalArgumentException {
        return false;
    }

    /**
     * Determine whether or not this Item and the given Item are equivalent items.
     *
     * @param item The item to compare this item to.
     * @return whether or not this item and the given item are equivalent.
     */
    @Override
    public boolean equals(Item item) throws IllegalArgumentException {
        return false;
    }

    @Override
    public AbstractGardenPlant plant(int xPos, int yPos) {
        // TODO Auto-generated method stub
        return null;
    }

}
