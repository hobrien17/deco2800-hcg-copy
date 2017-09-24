package com.deco2800.hcg.garden;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.deco2800.hcg.BaseTest;
import com.deco2800.hcg.contexts.playContextClasses.PlantWindow;
import com.deco2800.hcg.entities.garden_entities.plants.AbstractGardenPlant;
import com.deco2800.hcg.entities.garden_entities.plants.Cactus;
import com.deco2800.hcg.entities.garden_entities.plants.Grass;
import com.deco2800.hcg.entities.garden_entities.plants.Ice;
import com.deco2800.hcg.entities.garden_entities.plants.Inferno;
import com.deco2800.hcg.entities.garden_entities.plants.Pot;
import com.deco2800.hcg.entities.garden_entities.plants.Sunflower;
import com.deco2800.hcg.entities.garden_entities.plants.Water;
import com.deco2800.hcg.entities.garden_entities.seeds.Seed;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.ItemManager;
import com.deco2800.hcg.managers.PlantManager;
import com.deco2800.hcg.managers.StopwatchManager;

public class GardenTest extends BaseTest {

	private static Map<Class<?>, String> sprites;
	private static Map<Class<?>, String> names;
	private static Map<Seed.Type, String> seedNames;
	private static Map<Seed.Type, Class<? extends AbstractGardenPlant>> seedPlants;

	private class TestPlantBase extends AbstractGardenPlant {

		public TestPlantBase(Pot master) {
			super(master, "bad", 10);
		}

		@Override
		public Item[] loot() {
			Item[] arr = new Item[1];
			arr[0] = ((ItemManager) GameManager.get().getManager(ItemManager.class)).getNew(this.randItem());

			return arr;
		}

		@Override
		public String getThisTexture() {
			return "sunflower_01";
		}

		@Override
		protected void setupLoot() {
			lootRarity = new HashMap<>();
			lootRarity.put("sunflower_seed", 0.1);
			lootRarity.put("water_seed", 0.3);
			lootRarity.put("grass_seed", 0.6);
		}

	}

	/**
	 * Bad Plant 1 
	 * Loot rarity sums up to more than 1
	 * 
	 * @author Henry
	 *
	 */
	private class BadPlant1 extends TestPlantBase {

		public BadPlant1(Pot master) {
			super(master);
		}

		@Override
		protected void setupLoot() {
			lootRarity = new HashMap<>();
			lootRarity.put("sunflower_seed", 0.2);
			lootRarity.put("water_seed", 0.3);
			lootRarity.put("grass_seed", 0.6);
		}
	}

	/**
	 * Bad plant 2 
	 * Has piece of loot with rarity > 1
	 * 
	 * @author Henry
	 *
	 */
	private class BadPlant2 extends TestPlantBase {

		public BadPlant2(Pot master) {
			super(master);
		}

		@Override
		protected void setupLoot() {
			lootRarity = new HashMap<>();
			lootRarity.put("sunflower_seed", 1.2);
		}
	}

	/**
	 * Bad plant 3
	 * Has negative rarity
	 * 
	 * @author Henry
	 *
	 */
	private class BadPlant3 extends TestPlantBase {

		public BadPlant3(Pot master) {
			super(master);
		}

		@Override
		protected void setupLoot() {
			lootRarity = new HashMap<>();
			lootRarity.put("sunflower_seed", -1.0);
		}
	}

	/**
	 * Bad plant 4 
	 * Has no loot
	 * 
	 * @author Henry
	 *
	 */
	private class BadPlant4 extends TestPlantBase {

		public BadPlant4(Pot master) {
			super(master);
		}

		@Override
		protected void setupLoot() {
			lootRarity = new HashMap<>();
		}

		public String lootTest() {
			return randItem();
		}
	}

	@BeforeClass
	public static void setup() {
		sprites = new HashMap<>();
		names = new HashMap<>();

		sprites.put(Sunflower.class, "sunflower");
		sprites.put(Cactus.class, "cactus");
		sprites.put(Grass.class, "grass");
		sprites.put(Water.class, "lily");
		sprites.put(Inferno.class, "inferno");
		sprites.put(Ice.class, "ice"); // need to change

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

		seedNames.put(Seed.Type.EXPLOSIVE, "explosive");
		seedNames.put(Seed.Type.FIRE, "fire");
		seedNames.put(Seed.Type.GRASS, "grass");
		seedNames.put(Seed.Type.ICE, "ice");
		seedNames.put(Seed.Type.SUNFLOWER, "sunflower");
		seedNames.put(Seed.Type.WATER, "water");

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
		assertEquals(plant.getPot(), p);
	}

	@Test
	public void testBadPlant() {
		Pot p = new Pot(5, 5, 0);
		p.unlock();

		BadPlant1 plant1 = new BadPlant1(p);
		assertFalse("Loot should be invalid", plant1.checkLootRarity());

		BadPlant2 plant2 = new BadPlant2(p);
		assertFalse("Loot should be invalid", plant2.checkLootRarity());
		
		BadPlant3 plant3 = new BadPlant3(p);
		assertFalse("Loot should be invalid", plant3.checkLootRarity());

		BadPlant4 plant4 = new BadPlant4(p);
		Item[] empty = new Item[0];
		assertFalse("Loot should be invalid", plant4.checkLootRarity());
		assertEquals("randItem should be returning null", null, plant4.lootTest());
		assertArrayEquals("Should be returning empty loot", empty, plant4.getLoot());
	}

	@Test
	public void testGoodPlant() {
		Pot p = new Pot(5, 5, 0);
		p.unlock();
		Map<String, Double> rarityTest = new HashMap<>();
		rarityTest.put("sunflower_seed", 0.1);
		rarityTest.put("water_seed", 0.3);
		rarityTest.put("grass_seed", 0.6);

		AbstractGardenPlant plant = new TestPlantBase(p);

		assertEquals("Plant's loot rarity should be the same as rarityTest", rarityTest, plant.getRarity());
		
		Map<String, Double> rarityTest2 = new HashMap<>();
		rarityTest2.put("sunflower_seed", 0.3);
		rarityTest2.put("water_seed", 0.2);
		rarityTest2.put("grass_seed", 0.5);
		
		plant.increaseRarity(0.1, 0.3);
		assertEquals("Plant's loot rarity should be the same as rarityTest2 after rarity increase", 
				rarityTest2, plant.getRarity());
	}
	
	@Test
	public void testGoodPlantGrow() {
		Pot p = new Pot(5, 5, 0);
		p.unlock();
		AbstractGardenPlant plant = new TestPlantBase(p);
		StopwatchManager sw = (StopwatchManager)GameManager.get().getManager(StopwatchManager.class);
		PlantManager pm = (PlantManager)GameManager.get().getManager(PlantManager.class);
		Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));
    	PlantWindow window = new PlantWindow(skin);
    	pm.setPlantWindow(window, skin);
		assertEquals("Plant should initially be sprout", plant.getStage(), AbstractGardenPlant.Stage.SPROUT);
		
		for(float i = 1.0f; i < 11.0f; i += 1.0) {
			plant.update(sw, i);
		}
		assertEquals("Plant should now be small", plant.getStage(), AbstractGardenPlant.Stage.SMALL);
		for(float i = 11.0f; i < 21.0f; i += 1.0) {
			plant.update(sw, i);
		}
		assertEquals("Plant should now be large", plant.getStage(), AbstractGardenPlant.Stage.LARGE);
	}
	
	@Test
	public void testGoodPlantGrowMod() {
		Pot p = new Pot(5, 5, 0);
		p.unlock();
		AbstractGardenPlant plant = new TestPlantBase(p);
		StopwatchManager sw = (StopwatchManager)GameManager.get().getManager(StopwatchManager.class);
		PlantManager pm = (PlantManager)GameManager.get().getManager(PlantManager.class);
		Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));
    	PlantWindow window = new PlantWindow(skin);
    	pm.setPlantWindow(window, skin);
    	plant.changeDelay(0.8f);
		assertEquals("Plant should initially be sprout", plant.getStage(), AbstractGardenPlant.Stage.SPROUT);
		
		for(float i = 1.0f; i < 9.0f; i += 1.0) {
			plant.update(sw, i);
		}
		assertEquals("Plant should now be small", plant.getStage(), AbstractGardenPlant.Stage.SMALL);
		for(float i = 9.0f; i < 17.0f; i += 1.0) {
			plant.update(sw, i);
		}
		assertEquals("Plant should now be large", plant.getStage(), AbstractGardenPlant.Stage.LARGE);
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
		AbstractGardenPlant[] plants = { new Sunflower(p), new Cactus(p), new Water(p), new Grass(p), new Inferno(p),
				new Ice(p) };
		for (AbstractGardenPlant plant : plants) {
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
		Seed.Type[] types = { Seed.Type.EXPLOSIVE, Seed.Type.FIRE, Seed.Type.GRASS, Seed.Type.ICE, Seed.Type.SUNFLOWER,
				Seed.Type.WATER };
		for (Seed.Type type : types) {
			Seed seed = new Seed(type);
			testSeedDetails(seed);

		}
	}

	/*
	 * Tests that the plant returns valid loot
	 */
	private void testLoot(AbstractGardenPlant plant) {
		assertNotEquals(plant.getLoot().length, 0);
		assertNotEquals(plant.loot().length, 0);

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
