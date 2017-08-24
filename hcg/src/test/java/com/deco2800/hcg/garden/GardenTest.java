package com.deco2800.hcg.garden;

import static org.junit.Assert.*;

import org.junit.Test;

import com.deco2800.hcg.entities.garden_entities.plants.AbstractGardenPlant;
import com.deco2800.hcg.entities.garden_entities.plants.Sunflower;
import com.deco2800.hcg.entities.garden_entities.seeds.SunflowerSeed;
import com.deco2800.hcg.items.Item;

public class GardenTest {

	@Test
	public void testLoot() {
		/*AbstractGardenPlant flower = new Sunflower(0, 0, 0);
		
		String[] expectedLoot = {"sunflower_seed"};
		assertArrayEquals(flower.getLoot(), expectedLoot);
		
		Item[] loot = flower.loot();
		assertEquals(loot.length, 1);
		assertTrue(loot[0] instanceof SunflowerSeed);*/
	}
}
