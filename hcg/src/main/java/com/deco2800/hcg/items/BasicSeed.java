package com.deco2800.hcg.items;

/**
 * Basic Seed class that is used as the main currency of the game
 *
 * @author Taari Meiners / Team 1
 */
public class BasicSeed extends StackableItem {

    /**
     * Create new instance of BasicSeed
     */
    public BasicSeed() {
        this.baseValue = 1;
        this.itemName = "Basic Seed";
        this.currentStackSize = 1;
        this.maxStackSize = 256;
        this.texture = "seed";
        this.itemWeight = 0;
    }

    @Override
    public boolean isEquippable() {
        return false;
    }

    @Override
    public boolean isTradable() {
        return true;
    }
}
