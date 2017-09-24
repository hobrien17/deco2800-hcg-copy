package com.deco2800.hcg.items;

import java.util.ArrayList;

/**
 * A generic item forms the basis of the Item class.
 */
public abstract class GenericItem implements Item {
    protected int baseValue;
    protected int itemWeight;
    protected String itemName;
    protected String texture;

    /**
     * Retrieves an items display name
     *
     * @return Name of item as String
     */
    @Override
    public String getName() {
        return this.itemName;
    }

    /**
     * Retrieves the base value of this particular item.
     *
     * @return The base value of this item.
     */
    @Override
    public int getBaseValue() {
        return this.baseValue;
    }
    
    @Override
    public String getTexture() {
        return this.texture;
    }
    
    @Override
    public boolean equals(Item item) {
        return this == item;
    }
    
    @Override
    public ItemRarity getRarity() {
        return ItemRarity.COMMON;
    }
    
    @Override
    public ArrayList<String> getInformation() {
        return null;
    }
}
