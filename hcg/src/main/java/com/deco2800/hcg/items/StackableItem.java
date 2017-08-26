package com.deco2800.hcg.items;

public abstract class StackableItem implements Item{
    protected int maxStackSize;
    protected int currentStackSize;
    protected int baseValue;
    protected int itemWeight;
    protected String itemName;
    protected String texture;
    /**
     * Retrieves an items display name
     * @return Name of item as String
     */
    @Override
    public String getName() {
        return this.itemName;
    }

    /**
     * Returns the current stack size of this item.
     *
     * @return the current stack size of this item. Always returns 1 for
     *         non-stackable items.
     */
    @Override
    public int getStackSize() {
        return this.currentStackSize;
    }

    /**
     * Retrieves the stack limit of item type
     * @return Maximum stack size of item
     */
    @Override
    public int getMaxStackSize() {
        return this.currentStackSize;
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
        return this.itemWeight * this.currentStackSize;
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
        // TODO: implement textures
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
        if ((this.currentStackSize + number) > this.maxStackSize) {
            return false;
        }
        this.currentStackSize = this.currentStackSize + number;
        return true;
    }

    /**
     * Set this item's current stack size to the given number.
     *
     * @param number : the new stack size of this item.
     */
    @Override
    public void setStackSize(int number) throws IllegalArgumentException{
        if (number < 1) {
            throw new IllegalArgumentException();
        }
        this.currentStackSize = number;
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
    public boolean sameItem(Item item) throws IllegalArgumentException {
        // for stackable items we compare if strings are the same
        return this.itemName.equals(item.getName());
    }

    /**
     * Determine whether or not this Item and the given Item are equivalent items.
     *
     * @param item : The item to compare this item to.
     * @return whether or not this item and the given item are equivalent.
     */
    @Override
    public boolean equals(Item item) throws IllegalArgumentException{
        return this==item;
    }
}
