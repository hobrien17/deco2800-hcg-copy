package com.deco2800.hcg.trading;

import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.inventory.Inventory;
import com.deco2800.hcg.items.BasicSeed;
import com.deco2800.hcg.items.Item;

import java.util.HashMap;
import java.util.Map;

public class GeneralShop implements Shop{
    int modifier = 0;
    Player player;
    BasicSeed seed = new BasicSeed();
    Map<Item, Integer> shopStock = new HashMap<>();

    @Override
    public void open(int modifier, Player player) {
        this.modifier = modifier;
        this.player = player;
    }

    @Override
    public int inStock(Item item){
        try {
            return shopStock.get(item).intValue();
        } catch (NullPointerException e) {
            return 0;
        }
    }

    @Override
    public void addStock(Item item, int available) {
        if (shopStock.containsKey(item)) {
            shopStock.put(item, new Integer(shopStock.get(item) + available));
        } else {
            shopStock.put(item, new Integer(available));
        }
    }

    @Override
    public void addStock(Item[] items, int[] available) {
        for (int i = 0; i < items.length; i++) {
            if (shopStock.containsKey(items[i])) {
                shopStock.put(items[i], new Integer(shopStock.get(items[i]) + available[i]));
            } else {
                shopStock.put(items[i], new Integer(available[i]));
            }
        }
    }

    @Override
    public Map<Item, Integer> getStock() {
        return shopStock;
    }

    @Override
    public int buyStock(Item item) {
        if (shopStock.get(item) == 0) {
            return 1;
        } else if (!player.addItemToInventory(item)) {
            return 2;
        } else if (!player.getInventory().containsItem(seed) && (!player.getInventory().removeItem(seed, item
                .getBaseValue()+modifier))) {
            return 3;
        }
        shopStock.put(item, new Integer(shopStock.get(item) - 1));
        return 0;
    }

    @Override
    public int buyStock(Item item, int number) {
        for (int i = 0; i < number; i++) {
            int exitStatus = buyStock(item);
            if (exitStatus != 0) {
               return exitStatus;
            }
        }
        return 0;
    }

    @Override
    public int sellStock(Item item) {
        seed = new BasicSeed();
        seed.addToStack(item.getBaseValue()+modifier);
        player.getInventory().removeItem(item);
        if (!player.getInventory().addItem(seed)) {
            player.getInventory().addItem(item);
            System.out.println("fds");
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
