package com.deco2800.hcg.garden;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import com.deco2800.hcg.entities.garden_entities.plants.AbstractGardenPlant;
import com.deco2800.hcg.entities.garden_entities.plants.Cactus;
import com.deco2800.hcg.entities.garden_entities.plants.Pot;
import com.deco2800.hcg.entities.garden_entities.plants.Sunflower;
import com.deco2800.hcg.entities.garden_entities.seeds.SunflowerSeed;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.TimeManager;

public class GardenTest {

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
	}
	
	@Test
	public void testAddSunflower() {
		Pot p = new Pot(5, 5, 0);
		AbstractGardenPlant plant = new Sunflower(p);
		assertTrue(p.addPlant(plant));
		assertEquals(p.getPlant(), plant);
		
		assertEquals(p.getPlant().getStage(), AbstractGardenPlant.Stage.SPROUT);
		assertEquals(p.getTexture(), "sunflower_01");
		
		p.getPlant().advanceStage();
		assertEquals(p.getPlant().getStage(), AbstractGardenPlant.Stage.SMALL);
		assertEquals(p.getTexture(), "sunflower_02");
		
		p.getPlant().advanceStage();
		assertEquals(p.getPlant().getStage(), AbstractGardenPlant.Stage.LARGE);
		assertEquals(p.getTexture(), "sunflower_03");
		
		p.getPlant().advanceStage();
		assertEquals(p.getPlant().getStage(), AbstractGardenPlant.Stage.LARGE);
		assertEquals(p.getTexture(), "sunflower_03");
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
	public void testTick() {
		TimeManager tm = (TimeManager)GameManager.get().getManager(TimeManager.class);
		tm.setTimeElapsed(0);
		
		Pot p = new Pot(5, 5, 0);
		AbstractGardenPlant plant = new Sunflower(p);
		p.addPlant(plant);
		
		assertEquals(plant.getStage(), AbstractGardenPlant.Stage.SPROUT);
		
		p.onTick(0);
		assertEquals(plant.getStage(), AbstractGardenPlant.Stage.SPROUT);
		
		tm.setTimeElapsed(plant.getGrowDelay());
		p.onTick(0);
		assertEquals(plant.getStage(), AbstractGardenPlant.Stage.SMALL);
		
		tm.setTimeElapsed(plant.getGrowDelay()*2);
		p.onTick(0);
		assertEquals(plant.getStage(), AbstractGardenPlant.Stage.LARGE);
	}
	
	@Test
	public void testLootSunflower() {
		Pot p = new Pot(5, 5, 0);
		AbstractGardenPlant plant = new Sunflower(p);
		p.addPlant(plant);
		
		assertNotEquals(plant.getLoot().length, 0);
		assertNotEquals(plant.loot().length, 0);
		
		Map<String, Double> rarity = plant.getRarity();
		double sum = 0.0;
		for(Double d : rarity.values()) {
			sum += d;
		}
		assertEquals(Double.compare(sum, 1.0), 0);
	}
	
}
