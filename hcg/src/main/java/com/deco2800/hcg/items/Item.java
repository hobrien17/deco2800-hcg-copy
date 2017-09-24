package com.deco2800.hcg.items;

import java.util.ArrayList;

/**
 * Item interface provides a high level guide for others to implement custom items.
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
 * @authors coppers-j, willcs, raymogg
 **/
public interface Item {
    /**Each item has four required fields: name, isStackable (a boolean value), itemWeight and itemIcon **/
    boolean isStackable();

    /**
     * Retrieves an items display name
     * @return Name of item as String
     */
    String getName();

    /**
     * Returns the current stack size of this item.
     *
     * @return the current stack size of this item. Always returns 1 for
     *         non-stackable items.
     */
    int getStackSize();

    /**
     * Retrieves the stack limit of item type
     * @return Maximum stack size of item
     */
    int getMaxStackSize();

    /**
     * Checks if the item is armour or character customisation item
     * @return Whether or not item is wearable
     */
    boolean isWearable();

    /**
     * Checks if item is a weapon/ potion etc. and can be held in hot bar
     * @return Whether or not item can be equipped in hot bar
     */
    boolean isEquippable();
    
    /**
     * Retrieves the total weight of current stack of items
     * @return Item weight multiplied by current stack size
     */
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
    
    /**
     * Function for getting the icon of an item
     * @return A string identifying this item's texture
     */
    String getTexture();

    /**
     * Function for adding a number of an item to the stack of the current item.
     * Note that for non stackable items, this always returns false;
     * @param number the number of items to add to this items stack
     * @return true or false depending on whether the item was added or not
     * @throws IllegalArgumentException if number is too large or less than 1
     */
    boolean addToStack(int number);

    /**
     * Set this item's current stack size to the given number.
     *
     * @param number the new stack size of this item.
     */
    void setStackSize(int number) throws IllegalArgumentException;

    /**
     * Determine whether or not this Item and the given Item are functionally the
     * same item. Disregard stack size.
     *
     * @param item The item to compare this item to.
     * @return whether or not this item and the given item are functionally the
     *         same.
     */
    boolean sameItem(Item item) throws IllegalArgumentException;

    /**
     * Determine whether or not this Item and the given Item are equivalent items.
     *
     * @param item The item to compare this item to.
     * @return whether or not this item and the given item are equivalent.
     */
    boolean equals(Item item) throws IllegalArgumentException;
    
    /**
     * Gets this item's rarity.
     * 
     * @return The rarity of this item.
     */
    ItemRarity getRarity();

    /**
     * Make a deep copy of this item.
     * 
     * @return the copy of the item.
     */
    Item copy();
    
    /**
     * Retrieve the list of strings to be displayed in this item's tooltip.
     * 
     * @return The information to display in this item's tooltip. Null if there is none.
     */
    ArrayList<String> getInformation();
}
