package com.deco2800.hcg.trading;


import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.items.BasicSeed;
import com.deco2800.hcg.items.Item;

import java.util.HashMap;
import java.util.Map;

/** Generic shop interfece that has basic shop commands necessary for any shop such as the ability to buy, sell
 * and hold stock.
 */
public abstract class Shop {
    int modifier = 0;
    Player player;
    BasicSeed seed = new BasicSeed();
    Map<Item, Integer> shopStock = new HashMap<>();

    /**Open the shop so it can be interacted with.
     *
     * @param modifier
     *          modifier to apply to the prices of the items in the shop, percentage
     * @param player
     *          Reference to the player's and their inventory so that the shop can modify it directly
     *
     * @return nothing*/
    public void open(int modifier, Player player) {
        this.modifier = modifier;
        this.player = player;
    }

    /**Retrieve the number of a certain item this shop currently has in stock
     *
     * @param item
     *          the item to be checked
     * @return number in stock, 0 if none
     */
    public int inStock(Item item){
        if (shopStock.containsKey(item)) {
            return shopStock.get(item).intValue();
        } else {
            return 0;
        }
    }

    /**Add a new stock item to the shop with a corresponding number of this item the shop should have in stock
     *
     * @param item
     *          An instance of the stock class, e.g. a weapon or food item
     * @param available
     *          The number of this item that should be made available in the shop
     */
    public void addStock(Item item, int available) {
        if (shopStock.containsKey(item)) {
            shopStock.put(item, new Integer(shopStock.get(item) + available));
        } else {
            shopStock.put(item, new Integer(available));
        }
    }

    /**Add many new stock items to the shop, useful during initialisation of the shop. Care needs to be taken that the
     * indexes of both arrays match up, position 0 in the stock array will be assigned the availability number of
     * position 0 of the availability array.
     *
     * @param item
     *          Array of stock items to be added
     * @param available
     *          Array of numbers corresponding to the number of stock that should be made available
     */
    public void addStock(Item[] items, int[] available) {
        for (int i = 0; i < items.length; i++) {
            if (shopStock.containsKey(items[i])) {
                shopStock.put(items[i], new Integer(shopStock.get(items[i]) + available[i]));
            } else {
                shopStock.put(items[i], new Integer(available[i]));
            }
        }
    }

    /**Method to get access to the items a shop currently has in stock for displaying to the user what options they
     * have to buy
     *
     * @return the items the shop currently has in stock and the number of that item present
     */
    public Map<Item, Integer> getStock() {
        return shopStock;
    }


    /**Method to buy one of the specified stock item.
     *
     * @param item
     *          The stock to buy
     * @return 0 if bought successfully, 1 if no items available to buy, 2 if no space in player's inventory, 3 if not
     * enough currency in player's inventory
     */
    public int buyStock(Item item) {
        if (!shopStock.containsKey(item) || (shopStock.get(item) == 0)) {
            return 1;
        } else if (!player.addItemToInventory(item)) {
            return 2;
        } else if (!player.getInventory().containsItem(seed) || (!player.getInventory().removeItem(seed, item
                .getBaseValue()+modifier))) {
            return 3;
        }
        shopStock.put(item, new Integer(shopStock.get(item) - 1));
        return 0;
    }

    /**Method to buy a number of the specified stock item
     *
     * @param item
     *          The item to buy
     * @param number
     *          The number to buy
     * @return 0 if bought successfully, 1 if no items available to buy, 2 if no space in player's inventory, 3 if not
     * enough currency in player's inventory
     */
    public int buyStock(Item item, int number) {
        for (int i = 0; i < number; i++) {
            int exitStatus = buyStock(item);
            if (exitStatus != 0) {
                return exitStatus;
            }
        }
        return 0;
    }

    /**Method to sell an item of stock to the shop
     *
     * @param item
     *          Item that is to be sold
     * @return 0 if sold successfully, 1 if player cannot accept more currency
     */
    public int sellStock(Item item) {
        seed = new BasicSeed();
        seed.addToStack(item.getBaseValue()+modifier);
        player.getInventory().removeItem(item);
        if (!player.getInventory().addItem(seed)) {
            player.getInventory().addItem(item);
            return 1;
        }
        if (shopStock.containsKey(item)) {
            shopStock.put(item, new Integer(shopStock.get(item) + 1));
        } else {
            shopStock.put(item, new Integer(1));
        }
        return 0;
    }
}
