package com.deco2800.hcg.trading;


import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.items.BasicSeed;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.SingleItem;
import com.deco2800.hcg.items.StackableItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Generic shop interfece that has basic shop commands necessary for any shop such as the ability to buy, sell
 * and hold stock.
 */
public abstract class Shop {
    int modifier = 0;
    Player player;
    BasicSeed seed = new BasicSeed();
    ArrayList<Item> shopStock = new ArrayList<Item>();

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
        if (shopStock.contains(item) && (item instanceof StackableItem)) {
            return shopStock.get(shopStock.indexOf(item)).getStackSize();
        } else if (item instanceof SingleItem) {
            int number = 0;
            for (Item stock: shopStock) {
                if (stock.sameItem(item)) {
                    number++;
                }
            }
            return number;
        } else {
            return 0;
        }
    }

    /**Add a new stock item to the shop taking the current stack size of the item as the number the shop should have in
     * stock
     *
     * @param item
     *          An instance of the stock class, e.g. a weapon or food item
     *
     */
    public void addStock(Item item) {
        if (!shopStock.contains(item) || (item instanceof SingleItem)) {
            shopStock.add(item);
        } else {
            shopStock.get(shopStock.indexOf(item)).addToStack(item.getStackSize());
        }
    }

    /**Add many new stock items to the shop, useful during initialisation of the shop.
     *
     * @param items
     *          Array of items to be added
     *
     */
    public void addStock(Item[] items) {
        for (int i = 0; i < items.length; i++) {
            addStock(items[i]);
        }
    }

    /**Method to get access to the items a shop currently has in stock for displaying to the user what options they
     * have to buy
     *
     * @return the items the shop currently has in stock
     */
    public ArrayList<Item> getStock() {
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
        if (!shopStock.contains(item) || (shopStock.get(shopStock.indexOf(item)).getStackSize() == 0)) {
            return 1;
        } else if (!player.getInventory().canInsert(item)) {
            return 2;
        } else if (item instanceof StackableItem) {
            int temp = item.getStackSize();
            item.setStackSize(1);
            player.getInventory().addItem(item);
            item.setStackSize(temp);
        } else if (item instanceof SingleItem) {
            player.getInventory().addItem(item);
        }
        if (!player.getInventory().containsSingleItem(seed) || (!player.getInventory().removeItem(seed, item
                .getBaseValue()+modifier))) {
            return 3;
        }

        if (item instanceof SingleItem) {
            shopStock.remove(item);
        } else if (shopStock.get(shopStock.indexOf(item)).getStackSize() == 1) {
            shopStock.remove(item);
        } else {
            shopStock.get(shopStock.indexOf(item)).addToStack(-1);
        }
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
        if (shopStock.contains(item)) {
            shopStock.get(shopStock.indexOf(item)).addToStack(item.getStackSize());
        } else {
            shopStock.add(item);
        }
        return 0;
    }
}
