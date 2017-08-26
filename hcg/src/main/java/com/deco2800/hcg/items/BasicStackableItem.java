package com.deco2800.hcg.items;

import com.deco2800.hcg.util.MathHelper;

public class BasicStackableItem implements Item {
    private String itemName;
    private int itemWeight;
    private String itemTexture;
    private int itemMaxStackSize;
    private int itemCurrentStackSize;

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
    @Override
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
        this.itemCurrentStackSize = MathHelper.clamp(number, 1, this.getStackSize());
    }
    
    @Override
    public boolean isStackable() {
        return true;
    }
}
