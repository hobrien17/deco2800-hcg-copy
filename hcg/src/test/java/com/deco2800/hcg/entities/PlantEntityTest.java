package com.deco2800.hcg.entities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlantEntityTest {
	private Plant plant;

	@Before
	public void setUp() {
		plant = new Plant(0,0,0);
	}

	@Test
	public void onTickTest() {
		try {
			plant.onTick(1);
		} catch (UnsupportedOperationException e) {
			Assert.assertTrue(true);
			return;
		}

		Assert.fail("on tick is not throwing error");	}

}
