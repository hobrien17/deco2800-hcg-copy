package com.deco2800.hcg.items;

public abstract class SingleItem implements Item{
    protected int baseValue;
    protected int itemWeight;
    protected String itemName;
    protected String texture;
    protected String uniqueData = ""; // not yet implemented
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
     * Retrieves the total weight of current stack of items
     *
     * @return Item weight multiplied by current stack size
     */
    @Override
    public int getWeight() {
        return this.itemWeight;
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
     * @param item : he item to compare this item to.
     * @return whether or not this item and the given item are functionally the
     * same.
     */
    @Override
    public boolean sameItem(Item item) throws IllegalArgumentException {
        // for stackable items we compare if strings are the same
        return this.itemName.equals(item.getName());
    }

    public void setUniqueData(String data) {
        this.uniqueData = data;
    }

    @Override
    public boolean equals(Item item) {
        return this == item;
    }
}
