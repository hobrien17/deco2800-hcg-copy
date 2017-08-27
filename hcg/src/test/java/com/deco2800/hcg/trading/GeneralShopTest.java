package com.deco2800.hcg.trading;


import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.items.BasicSeed;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.stackable.TestItem;
import com.deco2800.hcg.items.single.TestUniqueItem;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.worlds.DemoWorld;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Map;

public class GeneralShopTest {
	Item item1 = new TestUniqueItem("test1",2);
	Item item2 = new TestItem();
	BasicSeed seeds = new BasicSeed();
	Item arrayOfThings[] = {item1,item2};
	int arrayOfAmounts[] = {1,2};
    GameManager gameManager;
    DemoWorld demoWorld;
    TiledMapTileLayer layer;
    TiledMap tiledMap;
    MapProperties mapProperties;
    Shop shop;
    Player player;

	@Before
    //setup copied from player tests so that the player class being used functions correctly
    public void Setup() {
        // cannot mock game manager
        gameManager = GameManager.get();

        // create all our mock classes
        demoWorld = mock(DemoWorld.class);
        layer = mock(TiledMapTileLayer.class);

        // add to non-mocked gamemanager
        gameManager.setWorld(demoWorld);

        // have a map for our properties to go back to the player class
        tiledMap = mock(TiledMap.class);
        when(demoWorld.getMap()).thenReturn(tiledMap);

        // set working properties for the Camera in the player to work.
        mapProperties = mock(MapProperties.class);
        when(tiledMap.getProperties()).thenReturn(mapProperties);
        when(mapProperties.get("tilewidth")).thenReturn(32);
        when(mapProperties.get("tileheight")).thenReturn(32);

        when(layer.getProperties()).thenReturn(mapProperties);

        seeds.addToStack(50);
        shop = new GeneralShop();
        player = new Player(0,0,0);
    }
	
    @Test
    public void TestInitialiseShop() {
    	shop.open(0, player);
    	assertEquals(0,shop.inStock(item1));
    	shop.addStock(item1, 1); 	
    	assertEquals(1,shop.inStock(item1));
    	
    }
    @Test
    public void testAddingAndGettingStock(){
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
    	assertEquals(2,currentStock.get(item1));
    	assertEquals(4,currentStock.get(item2));
    	
    	
    }
    
    @Test
    public void testBuyingStock(){
        player.getInventory().addItem(seeds);
    	shop.open(0, player);
    	shop.addStock(arrayOfThings, arrayOfAmounts);
    	shop.buyStock(item1);
    	assertEquals(0, shop.inStock(item1));
    	player.getInventory().addItem(item1);
        assertThat(player.getInventory().containsItem(item1), is(equalTo(true)));
    	assertThat(player.getInventory().containsItem(item2), is(equalTo(false)));
    	
    }
    
    @Test
    public void testSellingStock(){
    	shop.open(0, player);
    	player.addItemToInventory(item1);
    	shop.sellStock(item1);
    	assertEquals(1,shop.inStock(item1));
        assertThat(player.getInventory().containsItem(item1), is(equalTo(false)));
    }

    @Test
	public void testSBasicSeedProperties() {
    	assertTrue(seeds.isTradable());
    	assertFalse(seeds.isEquippable());
	}

	@Test
    public void addStockExistingStock() {
        shop.addStock(item1,1);
        shop.addStock(item1, 1);
        assertThat(shop.inStock(item1), is(equalTo(2)));
    }

    @Test
    public void buyStockNotInStock() {
        assertThat(shop.buyStock(item1), is(equalTo(1)));
    }

    @Test
    public void buyStockNoRoomInInventory() {
        shop.open(0, player);
        Item item = new TestUniqueItem("heavy item", 100);
        player.getInventory().addItem(item);
        shop.addStock(item1, 1);
        assertThat(shop.buyStock(item1), is(equalTo(2)));
    }

    @Test
    public void buyStockNoCurrency() {
        shop.open(0, player);
        shop.addStock(item1, 1);
        assertThat(shop.buyStock(item1), is(equalTo(3)));
    }

    @Test
    public void buyManyStockNotInStock() {
        assertThat(shop.buyStock(item1,2), is(equalTo(1)));
    }

    @Test
    public void buyManyStockPass() {
        shop.open(0, player);
        Item item = new TestItem();
        shop.addStock(item, 3);
        player.getInventory().addItem(seeds);
        assertThat(shop.buyStock(item,2), is(equalTo(0)));
        assertThat(player.getInventory().containsItem(item), is(equalTo(true)));
    }

    @Test
    public void sellItemAlreadyOneInShop() {
        Item item = new TestItem();
        player.getInventory().addItem(seeds);
        player.getInventory().addItem(item);
        shop.addStock(item, 1);
        shop.open(0, player);
        assertThat(shop.sellStock(item), is(equalTo(0)));
        assertThat(shop.inStock(item), is(equalTo(2)));
    }
}
