package com.deco2800.hcg.items;

/** Basic class for a simple non stackable item, non wearable item**/
public class BasicNonstackableItem implements Item {

    private String itemName;
    private int itemWeight;
    private String itemTexture;

    /**
     * Creates a new NonStackableItem with the given name and weight
     * @param name the name of the item
     * @param weight the weight of the item
     */
    public BasicNonstackableItem(String name, int weight) {
        itemName = name;
        itemWeight = weight;
    }
    /**
     * Function for returning the name of an item
     */
    public String getName() {
        return itemName;
    }

    /**
     * Function for returning the max stack size of this item
     */
    public int getMaxStackSize() {
        return 1;
    }

    /**
     * Function for returning whether an item is wearable/equipable by a user
     * eg armour, character customization items
     */
    public boolean isWearable() {
        return false;
    }

    /**
     * Function for returning the total weight of this item.
     * For stackable items this is equal to itemWeight * stackSize
     */
    public int getWeight() {
        return itemWeight;
    }

    /**
     * Function for setting the icon of an item
     * follows a similar method to how entities does textures**/
    public void setTexture(String icon) {
        itemTexture = icon;
    }

    /**
     * Adds the given number of items to this stack
     * @param number: the number of items to add to this items stack
     * @return <unknown>
     */
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
}
