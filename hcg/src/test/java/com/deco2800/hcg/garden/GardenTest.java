package com.deco2800.hcg.garden;

import static org.junit.Assert.*;

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
import com.deco2800.hcg.entities.garden_entities.seeds.AbstractSeed;
import com.deco2800.hcg.entities.garden_entities.seeds.ExplosiveSeed;
import com.deco2800.hcg.entities.garden_entities.seeds.FireSeed;
import com.deco2800.hcg.entities.garden_entities.seeds.GrassSeed;
import com.deco2800.hcg.entities.garden_entities.seeds.IceSeed;
import com.deco2800.hcg.entities.garden_entities.seeds.SunflowerSeed;
import com.deco2800.hcg.entities.garden_entities.seeds.WaterSeed;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.managers.TimeManager;

public class GardenTest {
	
	private static Map<Class<?>, String> sprites;
	private static Map<Class<?>, String> spritesSeed;

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
		spritesSeed = new HashMap<>();

		spritesSeed.put(ExplosiveSeed.class, "explosive_seed");
		spritesSeed.put(FireSeed.class, "fire_seed");
		spritesSeed.put(GrassSeed.class, "grass_seed");
		spritesSeed.put(IceSeed.class, "ice_seed");
		spritesSeed.put(SunflowerSeed.class, "sunflower_seed");
		spritesSeed.put(WaterSeed.class, "water_seed");
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

	private void testSpriteSeed(AbstractSeed seed) {
		assertEquals(spritesSeed.get(seed.getClass()));
	}
	
}
