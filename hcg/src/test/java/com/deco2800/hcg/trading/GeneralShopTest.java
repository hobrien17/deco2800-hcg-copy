package com.deco2800.hcg.trading;


import com.deco2800.hcg.items.BasicNonstackableItem;
import com.deco2800.hcg.items.Item;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertTrue;

public class GeneralShopTest {
    @Test
    public void TestInitialiseShop() {
        Item item = new BasicNonstackableItem("Test Item", 2);
        Stock stock = new Stock(item, 2);
        Shop shop = new GeneralShop();
        shop.addStock(stock,2);
        Map<Stock, Integer> expectedStock = new HashMap<>();
        expectedStock.put(stock, new Integer(2));
        Assert.assertEquals(expectedStock, shop.getStock());
    }
}
