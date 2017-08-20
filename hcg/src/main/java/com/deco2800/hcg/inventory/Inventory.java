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
    public int getMaxSize();
    
    /**
     * Retrieve the amount of items currently stored inside this inventory.
     * 
     * @return The number of items currently stored.
     */
    public int getNumItems();
    
    /**
     * Retrieve the item stored at position <code>index</code> inside this
     * inventory.
     * 
     * @param index
     *            The position of the item to be retrieved.
     * @return The item stored at position <code>index</code>.
     */
    public Item getItem(int index);
    
    /**
     * Remove the item stored at position <code>index</code> inside this inventory.
     * 
     * @param index
     *            The position of the item to be removed.
     * @return The recently removed item.
     */
    public Item removeItem(int index);
    
    /**
     * Tests whether or not this inventory can currently accept <code>item</code>.
     * 
     * @param item
     *            The item to be tested.
     * @return Whether or not the item is able to be inserted into the inventory.
     */
    public boolean canInsert(Item item);
    
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
    public boolean insertItem(Item item, int index);
    
    /**
     * Inserts <code>item</code> into the first empty slot in the inventory or
     * appends it to the end if possible.
     * 
     * @param item
     *            The item to insert into the inventory.
     * @return Whether or not the item was able to be inserted into the inventory.
     */
    public boolean appendItem(Item item);
}
