package com.deco2800.hcg.garden;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.deco2800.hcg.entities.garden_entities.plants.AbstractGardenPlant;
import com.deco2800.hcg.entities.garden_entities.plants.Cactus;
import com.deco2800.hcg.entities.garden_entities.plants.Grass;
import com.deco2800.hcg.entities.garden_entities.plants.Ice;
import com.deco2800.hcg.entities.garden_entities.plants.Inferno;
import com.deco2800.hcg.entities.garden_entities.plants.Pot;
import com.deco2800.hcg.entities.garden_entities.plants.Sunflower;
import com.deco2800.hcg.entities.garden_entities.plants.Water;
import com.deco2800.hcg.entities.garden_entities.seeds.Seed;

public class GardenTest {
	
	private static Map<Class<?>, String> sprites;
	private static Map<Class<?>, String> names;
	private static Map<Seed.Type, String> seedNames;
	private static Map<Seed.Type, Class<? extends AbstractGardenPlant>> seedPlants;

	@BeforeClass
	public static void setup() {
		sprites = new HashMap<>();
		names = new HashMap<>();
		
		sprites.put(Sunflower.class, "sunflower");
		sprites.put(Cactus.class, "cactus");
		sprites.put(Grass.class, "grass");
		sprites.put(Water.class, "lily");
		sprites.put(Inferno.class, "inferno");
		sprites.put(Ice.class, "ice"); //need to change
		
		names.put(Sunflower.class, "sunflower");
		names.put(Cactus.class, "cactus");
		names.put(Grass.class, "grass");
		names.put(Water.class, "lily");
		names.put(Inferno.class, "inferno");
		names.put(Ice.class, "ice");
	}

	@BeforeClass
	public static void setupSeed() {
		seedNames = new HashMap<>();
		seedPlants = new HashMap<>();

		seedNames.put(Seed.Type.EXPLOSIVE, "Explosive Seed");
		seedNames.put(Seed.Type.FIRE, "Fire Seed");
		seedNames.put(Seed.Type.GRASS, "Grass Seed");
		seedNames.put(Seed.Type.ICE, "Ice Seed");
		seedNames.put(Seed.Type.SUNFLOWER, "Sunflower Seed");
		seedNames.put(Seed.Type.WATER, "Water Seed");
		
		seedPlants.put(Seed.Type.EXPLOSIVE, Cactus.class);
		seedPlants.put(Seed.Type.FIRE, Inferno.class);
		seedPlants.put(Seed.Type.GRASS, Grass.class);
		seedPlants.put(Seed.Type.ICE, Ice.class);
		seedPlants.put(Seed.Type.SUNFLOWER, Sunflower.class);
		seedPlants.put(Seed.Type.WATER, Water.class);
	}

	/*
	 * Tests the details of an empty pot
	 */
	@Test
	public void testEmptyPot() {
		Pot p = new Pot(5, 5, 0);
		p.unlock();
				
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
	
	/*
	 * Tests adding to a pot
	 */
	@Test
	public void testAddSunflower() {
		Pot p = new Pot(5, 5, 0);
		p.unlock();
		AbstractGardenPlant plant = new Sunflower(p);
		assertTrue(p.addPlant(plant));
		assertEquals(p.getPlant(), plant);
		assertEquals(plant.getGrowDelay(), 10);
	}
	
	/*
	 * Tests that a pot can only contain one plant
	 */
	@Test
	public void testAddTwice() {
		Pot p = new Pot(5, 5, 0);
		p.unlock();
		AbstractGardenPlant plant1 = new Sunflower(p);
		AbstractGardenPlant plant2 = new Cactus(p);
		assertTrue(p.addPlant(plant1));
		assertFalse(p.addPlant(plant2));
	}
	
	/*
	 * Tests that plants cannot be added to a locked pot
	 */
	@Test
	public void testLockedPot() {
		Pot p = new Pot(5, 5, 0);
		assertFalse(p.addPlant(new Sunflower(p)));
	}
	
	/*
	 * Tests the details of all in-game plants
	 */
	@Test
	public void testAllPlants() {
		Pot p = new Pot(5, 5, 0);
		p.unlock();
		AbstractGardenPlant[] plants = {new Sunflower(p), new Cactus(p), new Water(p), new Grass(p), new Inferno(p),
				new Ice(p)};
		for(AbstractGardenPlant plant : plants) {
			p.removePlant();
			p.addPlant(plant);
			testSprite(plant, p);
			testName(plant);
			testLoot(plant);
		}
	}
	
	/*
	 * Tests the details of all in-game seeds
	 */
	@Test
	public void testAllSeeds() {
		Seed.Type[] types = {Seed.Type.EXPLOSIVE, Seed.Type.FIRE, Seed.Type.GRASS, Seed.Type.ICE, Seed.Type.SUNFLOWER,
				Seed.Type.WATER};
		for(Seed.Type type : types) {
			Seed seed = new Seed(type);
			testSeedDetails(seed);
			
		}
	}
	
	/*
	 * Tests that the plant returns valid loot
	 */
	private void testLoot(AbstractGardenPlant plant) {
		assertNotEquals(plant.getAllLoot().size(), 0);
		assertNotEquals(plant.getLoot().size(), 0);
		
		assertTrue(plant.checkLootRarity());
	}
	
	/*
	 * Tests that the name of the plant is correct
	 */
	private void testName(AbstractGardenPlant plant) {
		assertEquals(plant.getName(), names.get(plant.getClass()));
	}
	
	/*
	 * Tests that the plant's sprite is correct, depending on its stage
	 */
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

	/*
	 * Tests that a seed's details are correct
	 */
	private void testSeedDetails(Seed seed) {
		Pot p = new Pot(5, 5, 0);
		p.unlock();
		assertEquals(seed.getName(), seedNames.get(seed.getType()));
		assertTrue(seedPlants.get(seed.getType()).isInstance(seed.getNewPlant(p)));
	}
	
}
