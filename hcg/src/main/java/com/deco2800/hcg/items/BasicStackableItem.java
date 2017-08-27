package com.deco2800.hcg.items;

public class BasicStackableItem implements Item {
    private String itemName;
    private int itemWeight;
    private String itemTexture;
    private int itemMaxStackSize;
    private int itemCurrentStackSize;

    /**
     * Creates a new BasicStackableItem
     * @param name the name of the item
     * @param weight the weight of the item
     * @param maxStackSize the maximum stack size of the item
     */
    BasicStackableItem(String name, int weight, int maxStackSize) {
        itemName = name;
        itemWeight = weight;
        itemMaxStackSize = maxStackSize;
        itemCurrentStackSize = 0;
    }
    /** Function for returning the name of an item **/
    public String getName() {
        return itemName;
    }

    /** Function for returning the max stack size of this item */
    public int getMaxStackSize() {
        return itemMaxStackSize;
    }

    /**
     * Retrieves the current size of this stack of items.
     * @return The stack size of this item.
     */
    public int getStackSize() {
        return this.itemCurrentStackSize;
    }
    
    /** Function for returning whether an item is wearable/equipable by a user
     * eg armour, character customization items */
    public boolean isWearable() {
        return false;
    }

    /** Function for returning the total weight of this item. For stackable items this is equal to itemWeight * stackSize **/
    public int getWeight() {
        int totalWeight = itemWeight * itemCurrentStackSize;
        return totalWeight;
    }

    /** Function for adding a number of an item to the stack of the current item.
     * @param number: the number of items to add to this items stack
     * @return true or false depending on whether the item was added or not
     */
    public boolean addToStack(int number) {
        if (itemCurrentStackSize + number > itemMaxStackSize) {
            return false;
        }
        //We can safely add the required number of items to the stack
        itemCurrentStackSize += number;
        return true;
    }

    /** Function for setting the icon of an item
     * follows a similar method to how entities does textures**/
    public void setTexture(String icon) {
        itemTexture = icon;
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
