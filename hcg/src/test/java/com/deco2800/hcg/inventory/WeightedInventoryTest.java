package com.deco2800.hcg.inventory;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class WeightedInventoryTest {

	@Test
	public void TestWeightLimit() {
		int weightLimit = 100;
		WeightedInventory inventory = new WeightedInventory(weightLimit);
		
		assertTrue("Empty inventory's empty space is not its weight limit",
				inventory.getFreeSpace() == weightLimit);
		assertTrue("Empty inventory's max size is not its weight limit",
				inventory.getMaxSize() == weightLimit);
		
		// TODO test more once more item stuff is happening
	}
}
