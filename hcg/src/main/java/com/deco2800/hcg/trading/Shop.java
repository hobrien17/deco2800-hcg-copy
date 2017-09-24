package com.deco2800.hcg.trading;


import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.garden_entities.seeds.Seed;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.SingleItem;
import com.deco2800.hcg.items.StackableItem;

import java.util.ArrayList;
import java.util.List;

/** Generic shop interfece that has basic shop commands necessary for any shop such as the ability to buy, sell
 * and hold stock.
 */
public abstract class Shop {
    int modifier = 0;
    Player player;
    Seed seed = new Seed(Seed.Type.SUNFLOWER);
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
        Item shopItem = contains(item);
        if ((shopItem != null) && (item instanceof StackableItem)) {
            return shopItem.getStackSize();
        } else {
            int number = 0;
            for (Item stock: shopStock) {
                if (stock.sameItem(item)) {
                    number++;
                }
            }
            return number;
        }
    }

    /**
     * Helper method to retrieve if the shop has this item in stock
     * @param item
     * @return item in the shop
     */
    private Item contains(Item item) {
        for (Item currentItem : shopStock) {
            if (currentItem.sameItem(item)) {
                return currentItem;
            }
        }
        return null;
    }

    /**Add a new stock item to the shop taking the current stack size of the item as the number the shop should have in
     * stock
     *
     * @param item
     *          An instance of the stock class, e.g. a weapon or food item
     *
     */
    public void addStock(Item item) {
        Item shopItem = contains(item);
        if ((shopItem == null) || (item instanceof SingleItem)) {
            shopStock.add(item);
        } else {
            shopItem.addToStack(item.getStackSize());
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
    public List<Item> getStock() {
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

    /**
     * Helper method to find an item in the shop stock. If there is a copy that does not have max stack size it returns
     * this one, else it returns any of the others.
     *
     * @param item
     * @return the item that was found
     */
    private Item containsItem(Item item) {
        int maxStack = item.getMaxStackSize();
        Item prelim = null;
        for (Item i: shopStock) {
            if (i.sameItem(item)) {
                if (prelim == null) {
                    prelim = i;
                } else if ((prelim.getStackSize() == maxStack) && (i.getStackSize() != maxStack)) {
                        prelim = i;
                }
            }
        }
        return prelim;
    }

    /**Method to sell an item of stock to the shop
     *
     * @param item
     *          Item that is to be sold
     * @return 0 if sold successfully, 1 if player cannot accept more currency,
     * 			2 if item is null, 3 if item not in inventory
     */
    public int sellStock(Item item) {
    	if(item == null){
    		return 2;
    	}
        seed = new Seed(Seed.Type.SUNFLOWER);
        seed.setStackSize(item.getBaseValue()+modifier);
        if ((item instanceof SingleItem) || (item.getStackSize() == 1)) {
            System.out.println("fddf");
            if (!player.getInventory().removeItem(item)) {

                return 3;
            }
        } else {
            item.addToStack(-1);
        }
        if (!player.getInventory().addItem(seed)) {
            player.getInventory().addItem(item.copy());
            return 1;
        }
        if (item instanceof StackableItem) {
            Item shopCopy = containsItem(item);
            if ((shopCopy != null) && shopCopy.addToStack(1)) {
                //it worked, do nothing
            } else {
                Item newItem = item.copy();
                newItem.setStackSize(1);
                shopStock.add(newItem);
            }
        }  else {
            shopStock.add(item.copy());
        }
        return 0;
    }
}
