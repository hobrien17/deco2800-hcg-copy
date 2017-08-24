package com.deco2800.hcg.trading;


import com.deco2800.hcg.inventory.Inventory;
import com.deco2800.hcg.items.Item;

import java.util.Map;

/** Generic shop interfece that has basic shop commands necessary for any shop such as the ability to buy, sell
 * and hold stock.
 */
public interface Shop {

    /**Open the shop so it can be interacted with.
     *
     * @param modifier
     *          modifier to apply to the prices of the items in the shop, percentage
     * @param inventory
     *          Reference to the player's inventory so that the shop can modify it directly
     *
     * @return nothing*/
    public void open(int modifier, Inventory inventory);

    /**Retrieve the number of a certain item this shop currently has in stock
     *
     * @param item
     *          the item to be checked
     * @return number in stock, 0 if none
     */
    public int inStock(Item item);

    /**Add a new stock item to the shop with a corresponding number of this item the shop should have in stock
     *
     * @param item
     *          An instance of the stock class, e.g. a weapon or food item
     * @param available
     *          The number of this item that should be made available in the shop
     */
    public void addStock(Item item, int available);

    /**Add many new stock items to the shop, useful during initialisation of the shop. Care needs to be taken that the
     * indexes of both arrays match up, position 0 in the stock array will be assigned the availability number of
     * position 0 of the availability array.
     *
     * @param item
     *          Array of stock items to be added
     * @param available
     *          Array of numbers corresponding to the number of stock that should be made available
     */
    public void addStock(Item[] item, int[] available);

    /**Method to get access to the items a shop currently has in stock for displaying to the user what options they
     * have to buy
     *
     * @return the items the shop currently has in stock and the number of that item present
     */
    public Map<Item, Integer> getStock();


    /**Method to buy one of the specified stock item.
     *
     * @param item
     *          The stock to buy
     * @return void, function should modify player's inventory directly
     */
    public void buyStock(Item item);

    /**Method to buy a number of the specified stock item
     *
     * @param item
     *          The item to buy
     * @param number
     *          The number to buy
     * @return void, function should modify player's inventory directly
     */
    public void buyStock(Item item, int number);

    /**Method to sell an item of stock to the shop
     *
     * @param item
     *          Item that is to be sold
     */
    public void sellStock(Item item);
}
