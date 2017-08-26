package com.deco2800.hcg.items;

public abstract class StackableItem implements Item{

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
     * Any sublcass will be stackable
     * @return true as item will stack in inventory
     */
    @Override
    public boolean isStackable() {
        return true;
    }

    /**
     * Retrieves the total weight of current stack of items
     *
     * @return Item weight multiplied by current stack size
     */
    @Override
    public int getWeight() {
        return this.itemWeight * this.;
    }

    /**
     * Retrieves the base value of this particular item.
     *
     * @return The base value of this item.
     */
    @Override
    public int getBaseValue() {
        return 0;
    }

    /**
     * Checks whether or not this item is able to be sold to shops.
     *
     * @return Whether or not this item can be traded.
     */
    @Override
    public boolean isTradable() {
        return false;
    }

    /**
     * Function for setting the icon of an item
     * Implemented similar to the AbstractEntitry texture. Be sure to register texture with
     * TextureRegister before assigning the texture to a item
     *
     * @param texture : filename of texture
     * @throws IllegalArgumentException if texture is an invalid file name
     */
    @Override
    public void setTexture(String texture) throws IllegalArgumentException {

    }

    /**
     * Function for adding a number of an item to the stack of the current item.
     * Note that for non stackable items, this always returns false;
     *
     * @param number : the number of items to add to this items stack
     * @return true or false depending on whether the item was added or not
     * @throws IllegalArgumentException if number is too large or less than 1
     */
    @Override
    public boolean addToStack(int number) {
        return false;
    }

    /**
     * Set this item's current stack size to the given number.
     *
     * @param number : the new stack size of this item.
     */
    @Override
    public void setStackSize(int number) {

    }

    /**
     * Determine whether or not this Item and the given Item are functionally the
     * same item. Disregard stack size.
     *
     * @param item : he item to compare this item to.
     * @return whether or not this item and the given item are functionally the
     * same.
     */
    @Override
    public boolean sameItem(Item item) {
        return false;
    }

    /**
     * Determine whether or not this Item and the given Item are equivalent items.
     *
     * @param item : The item to compare this item to.
     * @return whether or not this item and the given item are equivalent.
     */
    @Override
    public boolean equals(Item item) {
        return false;
    }
}
