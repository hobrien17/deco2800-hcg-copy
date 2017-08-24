package com.deco2800.hcg.items;

/**
 * Item interface provides a high level guide for others to implement custom
 * items. The following methods outline the most basic methods an item requires
 * See the basicItem java class for an example implementation of this interface.
 * Not that simple items are not stackable. For items that are required to be
 * stacked, use the StackableItem interface.
 *
 * Required Fields for an item are Item Name: The ingame name of the item
 *
 * Item Weight: The weight of a single instance of this item - will be used for
 * inventory management. For stackable items, the total weight is numberOfItems
 * * itemWeight
 *
 * Max stack size: A int indicating the maximum number of these items that can
 * be held in a single stack. If 1, the item is considered to be a non stackable
 * item.
 *
 * isWearable: A simple boolean return value representing whether the item can
 * be equipped/worn by a user. These items dont necessarily need to provide
 * damange protection to the character and could simply be a customization
 * item.
 **/
public interface Item {
    /**Each item has four required fields: name, isStackable (a boolean value), itemWeight and itemIcon **/

    /**
     * Function for returning the name of an item
     **/
    String getName();

    /**
     * Function for getting the max stack size. 1 indicates a non stackable
     * item
     */
    int maxStackSize();

    /**
     * Function for returning whether an item is wearable/equipable by a user eg
     * armour, character customization items
     */
    boolean isWearable();

    /**
     * Function for returning the total weight of this item. For stackable items
     * this is equal to itemWeight * stackSize
     **/
    int getWeight();

    /**
     * Function for setting the icon of an item Implemented similar to the
     * AbstractEntitry texture. Be sure to register texture with TextureRegister
     * before assigning the texture to a item
     */
    void setTexture(String texture);

    /**
     * Function for adding a number of an item to the stack of the current item.
     * Note that for non stackable items, this always returns false;
     *
     * @param number: the number of items to add to this items stack
     * @return true or false depending on whether the item was added or not
     */
    boolean addToStack(int number);

}
