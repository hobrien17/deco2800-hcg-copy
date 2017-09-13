package com.deco2800.hcg;

import com.deco2800.hcg.entities.Tree;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.util.WorldUtil;

import com.deco2800.hcg.worlds.World;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WorldUtilTest {
	@Test
	public void TestDistanceFunctions() {
		GameManager.get().setWorld(new TestWorld());
		Tree t1 = new Tree(1, 1, 1);
		Tree t2 = new Tree(2, 2, 1);
		GameManager.get().getWorld().addEntity(t1);
		GameManager.get().getWorld().addEntity(t2);

		WorldUtil.closestEntityToPosition(0, 0, 2);
		assertEquals(t1, WorldUtil.closestEntityToPosition(0, 0, 2).get());
	}
	
	private class TestWorld extends World {
		
	}
}
