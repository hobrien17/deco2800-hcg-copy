package com.deco2800.hcg.items;

public class TestUniqueItem extends SingleItem {

    public TestUniqueItem() {
        this.baseValue = 100;
        this.itemWeight = 72;
        this.itemName = "Basic Sword";
        this.texture = "sword.png";
    }

    /**
     * Checks if the item is armour or character customisation item
     *
     * @return Whether or not item is wearable
     */
    @Override
    public boolean isWearable() {
        return false;
    }

    /**
     * Checks if item is a weapon/ potion etc. and can be held in hot bar
     *
     * @return Whether or not item can be equipped in hot bar
     */
    @Override
    public boolean isEquippable() {
        return true;
    }

    /**
     * Checks whether or not this item is able to be sold to shops.
     *
     * @return Whether or not this item can be traded.
     */
    @Override
    public boolean isTradable() {
        return true;
    }
}
