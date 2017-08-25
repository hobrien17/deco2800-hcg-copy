package com.deco2800.hcg.trading;

import com.deco2800.hcg.entities.Player;
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
        return shopStock.get(item).intValue();
    }

    @Override
    public void addStock(Item item, int available) {
        shopStock.put(item, new Integer(available));
    }

    @Override
    public void addStock(Item[] items, int[] available) {
        for (int i = 0; i < items.length; i++) {
            shopStock.put(items[i], new Integer(available[i]));
        }
    }

    @Override
    public Map<Item, Integer> getStock() {
        return shopStock;
    }

    @Override
    public void buyStock(Item item) {
        shopStock.put(item, new Integer(shopStock.get(item) - 1));
        //code to modify player's inventory
    }

    @Override
    public void buyStock(Item item, int number) {
        shopStock.put(item, new Integer(shopStock.get(item) - number));
        //code to modify player's inventory
    }

    @Override
    public void sellStock(Item item) {
        shopStock.put(item, new Integer(shopStock.get(item) + 1));
        //code to modify player's inventory
    }
}
