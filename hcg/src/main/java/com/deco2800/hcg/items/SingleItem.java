package com.deco2800.hcg.items;

import com.deco2800.hcg.items.single.TestUniqueItem;

public abstract class SingleItem extends GenericItem {
    /**
     * Returns the current stack size of this item.
     *
     * @return the current stack size of this item. Always returns 1 for
     * non-stackable items.
     */
    @Override
    public int getStackSize() {
        return 1;
    }

    /**
     * Retrieves the total weight of current stack of items
     *
     * @return Item weight multiplied by current stack size
     */
    @Override
    public int getWeight() {
        return this.itemWeight;
    }

    /**
     * Retrieves the stack limit of item type
     *
     * @return Maximum stack size of item
     */
    @Override
    public int getMaxStackSize() {
        return 1;
    }

    /**
     * Checks if max stack size is 1
     *
     * @return Whether or not item will stack in inventory
     */
    @Override
    public boolean isStackable() {
        return false;
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
    public void setStackSize(int number) throws IllegalArgumentException{
        if (number < 1) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Determine whether or not this Item and the given Item are functionally the
     * same item. Disregard stack size.
     *
     * @param item : the item to compare this item to.
     * @return whether or not this item and the given item are functionally the
     * same.
     */
    @Override
    public boolean sameItem(Item item) throws IllegalArgumentException {
        return (item instanceof SingleItem && item.getName().equals(this.getName()));
    }
    
    @Override
    public boolean equals(Item item) {
        return this.sameItem(item) && this.getStackSize() == item.getStackSize();
    }
}
