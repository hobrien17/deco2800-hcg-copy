package com.deco2800.hcg.trading;

import java.util.HashMap;
import java.util.Map;

public class GeneralShop implements Shop{
    int charisma = 0;
    Map<Stock, Integer> shopStock = new HashMap<>();

    @Override
    public void open(int charisma) {
        this.charisma = charisma;
    }

    @Override
    public int inStock(Stock stock){
        return shopStock.get(stock).intValue();
    }

    @Override
    public void addStock(Stock stock, int available) {
        shopStock.put(stock, new Integer(available));
    }

    @Override
    public void addStock(Stock[] stock, int[] available) {
        for (int i = 0; i < stock.length; i++) {
            shopStock.put(stock[i], new Integer(available[i]));
        }
    }

    @Override
    public Map<Stock, Integer> getStock() {
        return shopStock;
    }

    @Override
    public Stock buyStock(Stock stock) {
        shopStock.put(stock, new Integer(shopStock.get(stock) - 1));
        return stock;
    }

    @Override
    public Stock[] buyStock(Stock stock, int number) {
        return new Stock[0];
    }

    @Override
    public void sellStock(Stock stock) {
        shopStock.put(stock, new Integer(shopStock.get(stock) + 1));
    }
}
