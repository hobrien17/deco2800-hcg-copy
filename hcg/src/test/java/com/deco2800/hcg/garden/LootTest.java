package com.deco2800.hcg.garden;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.deco2800.hcg.BaseTest;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.ItemEntity;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.garden_entities.plants.AbstractGardenPlant;
import com.deco2800.hcg.entities.garden_entities.plants.Pot;
import com.deco2800.hcg.entities.garden_entities.seeds.Seed;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.lootable.LootWrapper;
import com.deco2800.hcg.items.tools.BugSpray;
import com.deco2800.hcg.items.tools.Fertiliser;
import com.deco2800.hcg.items.tools.Hoe;
import com.deco2800.hcg.items.tools.Shovel;
import com.deco2800.hcg.items.tools.Tool;
import com.deco2800.hcg.items.tools.Trowel;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.worlds.World;

public class LootTest extends BaseTest {
	
	private LootWrapper sunLoot = new LootWrapper("sunflower_seed", 5, 10);
	private LootWrapper waterLoot = new LootWrapper("water_seed", 1, 5);
	private LootWrapper grassLoot = new LootWrapper("grass_seed", 1, 11);
	
	private class TestPlant extends AbstractGardenPlant {

		public TestPlant(Pot master) {
			super(master, "test", 100);
		}

		@Override
		public String getThisTexture() {
			return null;
		}

		@Override
		public void setupLoot() {
			lootRarity = new HashMap<>();

			lootRarity.put(sunLoot, 0.6);
	        lootRarity.put(waterLoot, 0.3);
	        lootRarity.put(grassLoot, 0.1);
		}
	}
	
	private class BrokenPlant extends AbstractGardenPlant {

		public BrokenPlant(Pot master) {
			super(master, "test", 10);
		}

		@Override
		public String getThisTexture() {
			return null;
		}

		@Override
		public void setupLoot() {
			lootRarity = new HashMap<>();

			lootRarity.put(new LootWrapper("sunflower_seed", 5, 10), 1.1);
		}
	}
	
	private class BrokenPlant2 extends AbstractGardenPlant {

		public BrokenPlant2(Pot master) {
			super(master, "test", 10);
		}

		@Override
		public String getThisTexture() {
			return null;
		}

		@Override
		public void setupLoot() {
			lootRarity = new HashMap<>();

			lootRarity.put(new LootWrapper("sunflower_seed", 5, 10), 0.5);
			lootRarity.put(new LootWrapper("water_seed", 1, 5), 0.4);
		}
	}
	
	private World world;
	private Player player;
	
	@Before
	public void setup() {
		world = new World();
		player = new Player(5, 4, 0);
		GameManager.get().setWorld(world);
		((PlayerManager)GameManager.get().getManager(PlayerManager.class)).setPlayer(player);
		world.addEntity(player);
	}
	
	@Test
	public void testBrokenPlants() {
		Pot pot = new Pot(5, 5, 0);
		Pot pot2 = new Pot(5, 6, 0);
		BrokenPlant plant = new BrokenPlant(pot);
		BrokenPlant2 plant2 = new BrokenPlant2(pot2);
		assertFalse(plant.checkLootRarity());
		assertFalse(plant2.checkLootRarity());
	}
	
	@Test
	public void testLoot() {
		Pot pot = new Pot(5, 5, 0);
		world.addEntity(pot);
		TestPlant plant = new TestPlant(pot);
		assertTrue(plant.checkLootRarity());
		
		Map<LootWrapper, Double> map = new HashMap<>();
		List<Item> list = new ArrayList<>();
		
		map.put(sunLoot, 0.6);
        map.put(waterLoot, 0.3);
        map.put(grassLoot, 0.1);
        
        Seed sunSeed = new Seed(Seed.Type.SUNFLOWER);
        Seed waterSeed = new Seed(Seed.Type.WATER);
        Seed grassSeed = new Seed(Seed.Type.GRASS);
        list.add(sunSeed);
        list.add(waterSeed);
        list.add(grassSeed);
        
        assertEquals(map, plant.getRarity());
        assertEquals(list, plant.getAllLoot());
        
        LootWrapper rand = plant.randItem();
        assertTrue(rand.equals(sunLoot) || rand.equals(waterLoot) || rand.equals(grassLoot));
        List<Item> randList = plant.getLoot();
        for(Item item : randList) {
        	if(((Seed)item).getType().equals(Seed.Type.SUNFLOWER)) {
        		assertTrue(item.getStackSize() >= 5 && item.getStackSize() <= 10);
        	}
        	else if(((Seed)item).getType().equals(Seed.Type.WATER)) {
        		assertTrue(item.getStackSize() >= 1 && item.getStackSize() <= 5);
        	}
        	else if(((Seed)item).getType().equals(Seed.Type.GRASS)) {
        		assertTrue(item.getStackSize() == 1);
        	}
        	else {
        		fail();
        	}
        }
        
        plant.loot();
        for(AbstractEntity entity : world.getEntities()) {
        	if(entity instanceof ItemEntity) {
        		Item item = ((ItemEntity)entity).getItem();
        		if(((Seed)item).getType().equals(Seed.Type.SUNFLOWER) || 
        				((Seed)item).getType().equals(Seed.Type.WATER) ||
        				((Seed)item).getType().equals(Seed.Type.GRASS)) {
        			fail();
        		}
        	}
        }
        plant.advanceStage();
        plant.advanceStage();
        plant.loot();
        for(AbstractEntity entity : world.getEntities()) {
        	if(entity instanceof ItemEntity) {
        		Item item = ((ItemEntity)entity).getItem();
        		if(((Seed)item).getType().equals(Seed.Type.SUNFLOWER) || 
        				((Seed)item).getType().equals(Seed.Type.WATER) ||
        				((Seed)item).getType().equals(Seed.Type.GRASS)) {
        			world.removeEntity(entity);
        			return;
        		}
        	}
        }
        fail();
	}
	
	@Test
	public void testTools() {
		Tool shovel = new Shovel();
		Tool fert = new Fertiliser();
		Tool spray = new BugSpray();
		Tool hoe = new Hoe();
		Tool trowel = new Trowel();
		
		Pot pot = new Pot(5, 5, 0);
		world.addEntity(pot);
		pot.unlock();
		TestPlant plant = new TestPlant(pot);
		pot.addPlant(plant);
		
		//Test shovel
		assertFalse(pot.isEmpty());
		shovel.use();
		assertTrue(pot.isEmpty());
		
		pot.addPlant(plant);
		//Test fertiliser
		assertEquals(100, plant.getGrowDelay());
		fert.use();
		assertEquals(75, plant.getGrowDelay());
		
		//Test bugspray
		Map<LootWrapper, Double> old = new HashMap<>(plant.getRarity());
		spray.use();
		assertTrue(old.get(sunLoot) > plant.getRarity().get(sunLoot));
		assertTrue(old.get(waterLoot) > plant.getRarity().get(waterLoot));
		assertTrue(old.get(grassLoot) < plant.getRarity().get(grassLoot));
		assertTrue(plant.checkLootRarity());
		
		//Test hoe
		plant.advanceStage();
		plant.advanceStage();
		assertEquals(1, plant.getNumLoot());
		hoe.use();
		assertEquals(2, plant.getNumLoot());
		assertTrue(pot.isEmpty());
		int count = 0;
		for(AbstractEntity entity : world.getEntities()) {
        	if(entity instanceof ItemEntity) {
        		Item item = ((ItemEntity)entity).getItem();
        		if(((Seed)item).getType().equals(Seed.Type.SUNFLOWER) || 
        				((Seed)item).getType().equals(Seed.Type.WATER) ||
        				((Seed)item).getType().equals(Seed.Type.GRASS)) {
        			world.removeEntity(entity);
        			count++;
        		}
        	}
        }
		assertEquals(2, count);
		
		//Test trowel
		pot.addPlant(plant);
		trowel.use();
		for(AbstractEntity entity : world.getEntities()) {
        	if(entity instanceof ItemEntity) {
        		Item item = ((ItemEntity)entity).getItem();
        		if(((Seed)item).getType().equals(Seed.Type.SUNFLOWER) || 
        				((Seed)item).getType().equals(Seed.Type.WATER) ||
        				((Seed)item).getType().equals(Seed.Type.GRASS)) {
        			world.removeEntity(entity);
        			return;
        		}
        	}
        }
		fail();
		
	}
}
