package com.deco2800.hcg.items;

/** Basic class for a simple non stackable item, non wearable item**/
public class BasicNonstackableItem implements Item {

    private String itemName;
    private int itemWeight;
    private String itemTexture;

    BasicNonstackableItem(String name, int weight) {
        itemName = name;
        itemWeight = weight;
    }
    /** Function for returning the name of an item **/
    @Override
    public String getName() {
        return itemName;
    }

    /** Function for returning the max stack size of this item */
    @Override
    public int getMaxStackSize() {
        return 1;
    }

    /** Function for returning whether an item is wearable/equipable by a user
     * eg armour, character customization items */
    @Override
    public boolean isWearable() {
        return false;
    }

    /**
     * Function for returning the total weight of this item. For stackable items
     * this is equal to itemWeight * stackSize
     **/
    @Override
    public int getWeight() {
        return itemWeight;
    }

    /** Function for setting the icon of an item
     * follows a similar method to how entities does textures**/
    @Override
    public void setTexture(String icon) {
        itemTexture = icon;
    }

    @Override
    public boolean addToStack(int number) {
        return false;
    }
    
    @Override
    public int getBaseValue() {
        return 1; 
    }
    
    @Override
    public boolean isTradable() {
        return true;
    }
    
    @Override
    public int getStackSize() {
        return 1;
    }

    @Override
    public boolean sameItem(Item item) {
        return item.getClass().equals(this.getClass());
    }
    
    @Override
    public boolean equals(Item item) {
        return item.getStackSize() == this.getStackSize() && this.sameItem(item);
    }
    
    @Override
    public void setStackSize(int number) {
        return;
    }
    
    @Override
    public boolean isStackable() {
        return false;
    }
    
    @Override
    public boolean isEquippable() {
        return false;
    }
}
