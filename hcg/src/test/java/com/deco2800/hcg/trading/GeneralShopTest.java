package com.deco2800.hcg.trading;


import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.items.BasicNonstackableItem;
import com.deco2800.hcg.items.Item;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class GeneralShopTest {
	Item item1 = new Stock("thing", true, 1, 1);
	Item item2 = new Stock("thing", true, 1, 2);
	Item item3 = new Stock("thing", true, 1, 3);
	Item item4 = new Stock("thing", true, 1, 4);
	Item arrayOfThings[] = {item1,item2,item3,item4};
	int arrayOfAmounts[] = {1,2,3,4};
	
	
    @Test
    public void TestInitialiseShop() {
    	Shop shop = new GeneralShop();
    	Player player = new Player(0,0,0);
    	shop.open(0, player);
    	assertEquals(0,shop.inStock(item1));
    	shop.addStock(item1, 1); 	
    	assertEquals(1,shop.inStock(item1));
    	
    }
    @Test
    public void testAddingAndGettingStock(){
    	Shop shop = new GeneralShop();
    	Player player = new Player(0,0,0);
    	shop.open(0, player);
    	shop.addStock(item1, 1);
    	shop.addStock(item2, 2);
    	assertEquals(1,shop.inStock(item1));
    	assertEquals(2,shop.inStock(item2));
    	Map currentStock = shop.getStock();
    	assertEquals(1,currentStock.get(item1));
    	assertEquals(2,currentStock.get(item2));
    	shop.addStock(arrayOfThings, arrayOfAmounts);
    	currentStock = shop.getStock();
    	assertEquals(2,shop.inStock(item1));
    	assertEquals(4,shop.inStock(item2));
    	assertEquals(3,shop.inStock(item3));
    	assertEquals(4,shop.inStock(item4));
    	assertEquals(2,currentStock.get(item1));
    	assertEquals(4,currentStock.get(item2));
    	assertEquals(3,currentStock.get(item3));
    	assertEquals(4,currentStock.get(item4));
    	
    	
    }
    
    @Test
    public void testBuyingStock(){
    	Shop shop = new GeneralShop();
    	Player player = new Player(0,0,0);
    	shop.open(0, player);
    	shop.addStock(arrayOfThings, arrayOfAmounts);
    	shop.buyStock(item2);
    	shop.buyStock(item4, 3);
    	assertEquals(1, shop.inStock(item2));
    	assertEquals(1, shop.inStock(item4));
    	assertTrue(player.getInventory().containsItem(item2));
    	assertTrue(player.getInventory().containsItem(item4));
    	assertFalse(player.getInventory().containsItem(item1));
    	
    }
    
    @Test
    public void testSellingStock(){
    	Shop shop = new GeneralShop();
    	Player player = new Player(0,0,0);
    	shop.open(0, player);
    	player.addItemToInventory(item1);
    	shop.sellStock(item1);
    	assertEquals(1,shop.inStock(item1));
    	assertFalse(player.getInventory().containsItem(item1));
    }
}
