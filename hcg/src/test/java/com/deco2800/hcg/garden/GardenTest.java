package com.deco2800.hcg.garden;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.garden_entities.plants.AbstractGardenPlant;
import com.deco2800.hcg.entities.garden_entities.plants.Cactus;
import com.deco2800.hcg.entities.garden_entities.plants.Explosive;
import com.deco2800.hcg.entities.garden_entities.plants.Grass;
import com.deco2800.hcg.entities.garden_entities.plants.Ice;
import com.deco2800.hcg.entities.garden_entities.plants.Inferno;
import com.deco2800.hcg.entities.garden_entities.plants.Planter;
import com.deco2800.hcg.entities.garden_entities.plants.Pot;
import com.deco2800.hcg.entities.garden_entities.plants.Sunflower;
import com.deco2800.hcg.entities.garden_entities.plants.Water;
import com.deco2800.hcg.entities.garden_entities.seeds.Seed;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.managers.TimeManager;

public class GardenTest {
	
	private static Map<Class<?>, String> sprites;
	private static Map<Seed.Type, String> seedNames;
	private static Map<Seed.Type, Class<? extends AbstractGardenPlant>> seedPlants;

	@BeforeClass
	public static void setup() {
		sprites = new HashMap<>();
		
		sprites.put(Sunflower.class, "sunflower");
		sprites.put(Cactus.class, "cactus");
		sprites.put(Grass.class, "grass");
		sprites.put(Water.class, "lily");
		sprites.put(Inferno.class, "inferno");
		sprites.put(Ice.class, "sunflower"); //need to change
		sprites.put(Explosive.class, "sunflower"); //need to change
	}

	@BeforeClass
	public static void setupSeed() {
		seedNames = new HashMap<>();
		seedPlants = new HashMap<>();

		seedNames.put(Seed.Type.EXPLOSIVE, "EXPLOSIVE");
		seedNames.put(Seed.Type.FIRE, "FIRE");
		seedNames.put(Seed.Type.GRASS, "GRASS");
		seedNames.put(Seed.Type.ICE, "ICE");
		seedNames.put(Seed.Type.SUNFLOWER, "SUNFLOWER");
		seedNames.put(Seed.Type.WATER, "WATER");
		
		seedPlants.put(Seed.Type.EXPLOSIVE, Cactus.class);
		seedPlants.put(Seed.Type.FIRE, Inferno.class);
		seedPlants.put(Seed.Type.GRASS, Grass.class);
		seedPlants.put(Seed.Type.ICE, Ice.class);
		seedPlants.put(Seed.Type.SUNFLOWER, Sunflower.class);
		seedPlants.put(Seed.Type.WATER, Water.class);
	}

	@Test
	public void testEmptyPot() {
		Pot p = new Pot(5, 5, 0);
				
		assertEquals(p.getPosX(), 5, 0.01);
		assertEquals(p.getPosY(), 5, 0.01);
		assertEquals(p.getPosZ(), 0, 0.01);
		
		assertEquals(p.getXLength(), 0.7, 0.01);
		assertEquals(p.getYLength(), 0.7, 0.01);
		assertEquals(p.getZLength(), 1, 0.01);
		assertEquals(p.getXRenderLength(), 1, 0.01);
		assertEquals(p.getYRenderLength(), 1, 0.01);
		
		assertEquals(p.getTexture(), "pot");
		
		assertEquals(p.getPlant(), null);
	}
	
	@Test
	public void testAddSunflower() {
		Pot p = new Pot(5, 5, 0);
		AbstractGardenPlant plant = new Sunflower(p);
		assertTrue(p.addPlant(plant));
		assertEquals(p.getPlant(), plant);
		assertEquals(plant.getGrowDelay(), 10);
	}
	
	@Test
	public void testAddTwice() {
		Pot p = new Pot(5, 5, 0);
		AbstractGardenPlant plant1 = new Sunflower(p);
		AbstractGardenPlant plant2 = new Cactus(p);
		assertTrue(p.addPlant(plant1));
		assertFalse(p.addPlant(plant2));
	}
	
	@Test
	public void testAllPlants() {
		Pot p = new Pot(5, 5, 0);
		AbstractGardenPlant[] plants = {new Sunflower(p), new Cactus(p), new Water(p), new Grass(p), new Inferno(p),
				new Ice(p), new Explosive(p)};
		for(AbstractGardenPlant plant : plants) {
			p.removePlant();
			p.addPlant(plant);
			testSprite(plant, p);
			testLoot(plant);
		}
	}
	
	@Test
	public void testAllSeeds() {
		Seed.Type[] types = {Seed.Type.EXPLOSIVE, Seed.Type.FIRE, Seed.Type.GRASS, Seed.Type.ICE, Seed.Type.SUNFLOWER,
				Seed.Type.WATER};
		for(Seed.Type type : types) {
			Seed seed = new Seed(type);
			testSeedDetails(seed);
			
		}
	}
	
	private void testLoot(AbstractGardenPlant plant) {
		assertNotEquals(plant.getLoot().length, 0);
		assertNotEquals(plant.loot().length, 0);
		
		assertTrue(plant.checkLootRarity());
	}
	
	private void testSprite(AbstractGardenPlant plant, Pot pot) {
		assertEquals(plant.getStage(), AbstractGardenPlant.Stage.SPROUT);
		assertEquals(pot.getTexture(), sprites.get(plant.getClass()) + "_01");
		
		plant.advanceStage();
		assertEquals(plant.getStage(), AbstractGardenPlant.Stage.SMALL);
		assertEquals(pot.getTexture(), sprites.get(plant.getClass()) + "_02");
		
		plant.advanceStage();
		assertEquals(plant.getStage(), AbstractGardenPlant.Stage.LARGE);
		assertEquals(pot.getTexture(), sprites.get(plant.getClass()) + "_03");
		
		plant.advanceStage();
		assertEquals(plant.getStage(), AbstractGardenPlant.Stage.LARGE);
		assertEquals(pot.getTexture(), sprites.get(plant.getClass()) + "_03");
	}

	private void testSeedDetails(Seed seed) {
		Pot p = new Pot(5, 5, 0);
		assertEquals(seed.getName(), seedNames.get(seed.getType()));
		try {
			assertTrue(seedPlants.get(seed.getType()).isInstance(seed.getNewPlant(p)));
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException |
				InvocationTargetException | NoSuchMethodException | SecurityException ex) {
			ex.printStackTrace();
			fail();
		}
	}
	
}
