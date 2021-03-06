package com.deco2800.hcg.items.stackable;

import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.StackableItem;

/**
 * TestItem is a class used to test the StackableItem class
 */
public class TestItem extends StackableItem {

    /**
     * Initialises a new test item
     */
    public TestItem(){
        this.baseValue = 2;
        this.itemName = "Test Item";
        this.currentStackSize = 1;
        this.maxStackSize = 64;
        this.texture = "generic.png";
        this.itemWeight = 5;
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

    /**
     * Checks whether or not this item is able to be sold to shops.
     *
     * @return Whether or not this item can be traded.
     */
    @Override
    public boolean isTradable() {
        return true;
    }

    @Override
    public Item copy() {
        TestItem newItem = new TestItem();
        newItem.setStackSize(this.getStackSize());
        return newItem;
    }
}
