package com.deco2800.hcg.items;

public abstract class StackableItem extends GenericItem{
    protected int maxStackSize;
    protected int currentStackSize;

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
        return this.maxStackSize;
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
}
