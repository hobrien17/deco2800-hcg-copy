package com.deco2800.hcg.inventory;

import com.deco2800.hcg.items.Item;

/**
 * Generic Inventory Interface for managing and accessing items within an
 * inventory. Should be implemented by any object that stores items with the
 * intention of them being accessed from the outside.
 */
public interface Inventory extends Iterable<Item> {
    
    /**
     * Retrieve the amount of items currently able to be stored inside this
     * inventory.
     * 
     * @return The number of items currently able to be stored.
     */
    int getMaxSize();
    
    /**
     * Retrieve the amount of items currently stored inside this inventory.
     * 
     * @return The number of items currently stored.
     */
    int getNumItems();
    
    /**
     * Retrieve the item stored at position <code>index</code> inside this
     * inventory.
     * 
     * @param index
     *            The position of the item to be retrieved.
     * @return The item stored at position <code>index</code>.
     * @throws IndexOutOfBoundsException
     *            if the provided index is not a valid position in the inventory.
     */
    Item getItem(int index);
    
    /**
     * Remove the item stored at position <code>index</code> inside this inventory.
     * 
     * @param index
     *            The position of the item to be removed.
     * @return The recently removed item.
     * @throws IndexOutOfBoundsException
     *            if the provided index is not a valid position in the inventory.
     */
    Item removeItem(int index);
    
    /**
     * Remove the given item from the inventory, if it exists.
     * 
     * @param item
     *            The item to remove from the inventory.
     * @return Whether or not the item was found and removed.
     */
    boolean removeItem(Item item);

    /**
     * Remove a given number of the given item
     *
     * @param item
     *          The item to remove
     * @param number
     *          The number to remove
     * @return
     */
    boolean removeItem(Item item, int number);
    
    /**
     * Tests whether or not this inventory can currently accept <code>item</code>.
     * 
     * @param item
     *            The item to be tested.
     * @return Whether or not the item is able to be inserted into the inventory.
     */
    boolean canInsert(Item item);
    
    /**
     * Tests whether or not the slot at position <code>index</code> can currently
     * accept <code>item</code>.
     * 
     * Differs from {@link #allowItemInSlot(Item, int)} in that this method should check to see
     * if there's space for the item, whereas {@code allowItemInSlot} checks to see
     * if there's a restriction on that slot preventing the item from being placed
     * there.
     * 
     * @param item
     *            The item to be tested.
     * @param index
     *            The position of the slot to try and insert into.
     * @return Whether or not the item is able to be inserted into the slot.

     */
    boolean canFitItemInSlot(Item item, int index);
    
    /**
     * Tests whether or not the slot at position <code>index</code> can contain
     * <code>item</code> at all.
     * 
     * Differs from {@link #canFitItemInSlot(Item, int)} in that this method should check to see
     * if there's a restriction on this slot preventing the item from being placed
     * there, whereas {@code canInsert} checks to see if there's space for
     * the item.
     * 
     * @param item
     *            The item to be tested.
     * @param index
     *            The position of the slot to to check.
     * @return Whether or not the slot is compatible with the item
     * @throws IndexOutOfBoundsException
     * 
     */
    boolean allowItemInSlot(Item item, int index);
    
    /**
     * Inserts <code>item</code> into position <code>index</code> in the inventory
     * if possible.
     * 
     * @param item
     *            The item to insert into the inventory.
     * @param index
     *            The position to insert the item into.
     * @return Whether or not the item was able to be inserted into the inventory.
     */
    boolean insertItem(Item item, int index);
    
    /**
     * Inserts <code>item</code> into the inventory into the first free spaces that
     * the item can go.
     * 
     * @param item
     *            The item to insert into the inventory.
     * @return Whether or not the item was able to be inserted into the inventory.
     */
    boolean addItem(Item item);
    
    /**
     * Tests whether or not this inventory currently contains the given item.
     * 
     * @param item
     *            The item to search the inventory for.
     * @return Whether or not the item was found in the inventory.
     */
    boolean containsItem(Item item);

    /**
     * Tests whether the inventory contains any of this item regardless of stack size
     *
     * @param item
     *          The item to search for
     * @return Whether or not it was found in the inventory
     */
    boolean containsSingleItem(Item item);

    /**
     * Returns the total number of items with the supplied name that are contained in the inventory
     *
     */
    int numberOf(String itemName);
}
