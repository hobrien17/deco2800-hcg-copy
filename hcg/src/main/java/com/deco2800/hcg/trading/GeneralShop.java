package com.deco2800.hcg.trading;

import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.inventory.Inventory;
import com.deco2800.hcg.items.Item;

import java.util.HashMap;
import java.util.Map;

public class GeneralShop implements Shop{
    int modifier = 0;
    Player player;
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
    public void buyStock(Item item) {
        shopStock.put(item, new Integer(shopStock.get(item) - 1));
        player.addItemToInventory(item);
    }

    @Override
    public void buyStock(Item item, int number) {
        shopStock.put(item, new Integer(shopStock.get(item) - number));
        for (int i = 0; i < number; i++) {
            player.addItemToInventory(item);
        }
    }

    @Override
    public void sellStock(Item item) {
        if (shopStock.containsKey(item)) {
            shopStock.put(item, new Integer(shopStock.get(item) + 1));
        } else {
            shopStock.put(item, new Integer(1));
        }
        player.getInventory().removeItem(item);
    }
}
