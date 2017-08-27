package com.deco2800.hcg.items.single;

import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.SingleItem;

/**
 * This is a test class, intended to be able to have variable properties to
 * avoid having to create a bunch of different item classes to test with.
 */
public class TestUniqueItem extends SingleItem {
    private String uniqueData;
    
    public TestUniqueItem(String name, int weight) {
        this.baseValue = 100;
        this.itemWeight = weight;
        this.itemName = name;
        this.texture = "sword.png";
    }
    
    /**
     * Retrieves an items display name
     *
     * @return Name of item as String
     */
    @Override
    public String getName() {
        return String.format("%s (%s)", this.itemName, this.uniqueData);
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

    public void setUniqueData(String data) {
        this.uniqueData = data;
    }

    public String getUniqueData() {
        return this.uniqueData;
    }

    /**
     *
     * @param item : the item to compare this item to.
     * @return true iff the unique data and the name of the items are the same
     */
    @Override
    public boolean sameItem(Item item) {
        if(item instanceof TestUniqueItem) {
            if(this.uniqueData == null) {
                return (((TestUniqueItem) item).getUniqueData() == null && super.sameItem(item));
            }

            return (this.uniqueData.equals(((TestUniqueItem) item).getUniqueData())
                    && super.sameItem(item));
        }
        
        return false;
    }
}
