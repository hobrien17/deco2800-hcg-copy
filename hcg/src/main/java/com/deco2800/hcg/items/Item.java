package com.deco2800.hcg.items;

/** Item interface provides a high level guide for others to implement custom items.
 * The following methods outline the most basic methods an item requires
 * See the basicItem java class for an example implementation of this interface. Not that simple items are not stackable.
 * For items that are required to be stacked, use the StackableItem interface.
 *
 * Required Fields for an item are
 * Item Name: The ingame name of the item
 *
 * Item Weight: The weight of a single instance of this item - will be used for inventory management.
 *              For stackable items, the total weight is numberOfItems * itemWeight
 *
 * Max stack size: A int indicating the maximum number of these items that can be held in a single stack. If 1, the item
 *                  is considered to be a non stackable item.
 *
 * isWearable: A simple boolean return value representing whether the item can be equipped/worn by a user. These
 *              items dont necessarily need to provide damange protection to the character and could simply be a
 *              customization item.
 **/
public interface Item {
    /**Each item has four required fields: name, isStackable (a boolean value), itemWeight and itemIcon **/

    /** Function for returning the name of an item **/
    String getName();
    
    /**
     * Returns the current stack size of this item.
     * 
     * @return the current stack size of this item. Always returns 1 for
     *         non-stackable items.
     */
    int getStackSize();

    /**Function for getting the max stack size. 1 indicates a non stackable item */
    int getMaxStackSize();
    
    /** 
     * Return whether or not this item is stackable.
     * @return whether or not this item is stackable.
     */
    boolean isStackable();

    /** Function for returning whether an item is wearable/equipable by a user
     * eg armour, character customization items */
    boolean isWearable();

    /** Function for returning the total weight of this item. For stackable items this is equal to itemWeight * stackSize **/
    int getWeight();
    
    /**
     * Retrieves the base value of this particular item.
     * @return The base value of this item.
     */
    int getBaseValue();
    
    /**
     * Checks whether or not this item is able to be sold to shops.
     * @return Whether or not this item can be traded.
     */
    boolean isTradable();

    /** Function for setting the icon of an item
     * Implemented similar to the AbstractEntitry texture. Be sure to register texture with
     * TextureRegister before assigning the texture to a item*/
    void setTexture(String texture);
    
    /**
     * Function for adding a number of an item to the stack of the current item.
     * Note that for non stackable items, this always returns false.
     * 
     * @param number
     *            the number of items to add to this items stack
     * @return true or false depending on whether the item was added or not
     */
    boolean addToStack(int number);
    
    /**
     * Set this item's current stack size to the given number.
     * 
     * @param number
     *            the new stack size of this item.
     */
    void setStackSize(int number);
    
    /**
     * Determine whether or not this Item and the given Item are functionally the
     * same item. Disregard stack size.
     * 
     * @param item
     *            The item to compare this item to.
     * @return whether or not this item and the given item are functionally the
     *         same.
     */
    boolean sameItem(Item item);
    
    /**
     * Determine whether or not this Item and the given Item are equivalent items.
     * 
     * @param item
     *            The item to compare this item to.
     * @return whether or not this item and the given item are equivalent.
     */
    boolean equals(Item item);
}
