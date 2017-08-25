package com.deco2800.hcg.entities;

import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.worlds.AbstractWorld;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

class TestWorld extends AbstractWorld {

}

class TestEntity extends AbstractEntity {

	public TestEntity(float posX, float posY, float posZ, float xLength, float yLength, float zLength, float xRenderLength, float yRenderLength, boolean centered) {
		super(posX, posY, posZ, xLength, yLength, zLength, xRenderLength, yRenderLength, centered);
	}

	public TestEntity(float posX, float posY, float posZ, float xLength, float yLength, float zLength) {
		super(posX, posY, posZ, xLength, yLength, zLength);
	}
}

public class AbstractEntityTest {
	@Test
	public void WorldEntityConstructorTest() {
		AbstractEntity e = new TestEntity(0, 0, 0, 1, 1, 1);
		assertEquals(e.getPosX(), 0, 0.1);
		assertEquals(e.getPosY(), 0, 0.1);
		assertEquals(e.getPosZ(), 0, 0.1);
		assertEquals(e.getXLength(), 1, 0.1);

		AbstractEntity e2 = new TestEntity(10, 20, 30, 1, 1, 1);
		assertEquals(e2.getPosX(), 10, 0.1);
		assertEquals(e2.getPosY(), 20, 0.1);
		assertEquals(e2.getPosZ(), 30, 0.1);
		assertEquals(e2.getXLength(), 1, 0.1);

		AbstractEntity e3 = new TestEntity(10, 20, 30, 45, 1, 1);
		assertEquals(e3.getPosX(), 10, 0.1);
		assertEquals(e3.getPosY(), 20, 0.1);
		assertEquals(e3.getPosZ(), 30, 0.1);
		assertEquals(e3.getXLength(), 45, 0.1);
	}

	@Test
	public void TextureTest() {
		AbstractEntity e = new TestEntity(0, 0, 0, 0, 1, 1);

		e.setTexture("blah");
		assertEquals(e.getTexture(), "blah");
	}

	@Test
	public void OrderingTest() {
		GameManager.get().setWorld(new TestWorld());
		AbstractEntity e1 = new TestEntity(0, 100, 0, 0, 1, 1);
		AbstractEntity e2 = new TestEntity(100, 0, 0, 0, 1, 1);

		List<AbstractEntity> list = new ArrayList<AbstractEntity>();
		list.add(e1);
		list.add(e2);

		Collections.sort(list);
		assertEquals(list.get(1), e1);
		assertEquals(list.get(0), e2);

		list.removeAll(list);
		list.add(e2);
		list.add(e1);

		Collections.sort(list);
		assertEquals(list.get(1), e1);
		assertEquals(list.get(0), e2);
	}

	@Test
	public void CollisionTest() {
		GameManager.get().setWorld(new TestWorld());

		AbstractEntity e1 = new TestEntity(1, 1, 0, 0.1f, 0.1f, 1, 1, 1, true);
		AbstractEntity e2 = new TestEntity(1, 1, 0, 0.1f, 0.1f, 1, 1, 1, false);
		assertFalse(e1.collidesWith(e2));

		AbstractEntity e3 = new TestEntity(1, 1, 0, 0.1f, 0.1f, 1, 1, 1, true);
		AbstractEntity e4 = new TestEntity(1, 1, 0, 1, 1, 1);
		Assert.assertTrue(e3.collidesWith(e4));


		AbstractEntity e5 = new TestEntity(1.25f, 1.25f, 0, 0.4f, 0.4f, 1, 1, 1, false);
		AbstractEntity e6 = new TestEntity(1.55f, 1.55f, 0, 0.4f, 0.4f, 1, 1, 1, false);
		assertTrue(e5.collidesWith(e6));

		AbstractEntity e7 = new TestEntity(1.25f, 1.25f, 0, 0.4f, 0.4f, 1, 1, 1, false);
		AbstractEntity e8 = new TestEntity(1.66f, 1.66f, 0, 0.4f, 0.4f, 1, 1, 1, false);
		assertFalse(e7.collidesWith(e8));

		AbstractEntity e9 = new TestEntity(1, 1, 0, 0.1f, 0.1f, 1, 1, 1, true);
		AbstractEntity e10 = new TestEntity(1.1f, 1.1f, 0, 0.1f, 0.1f, 1, 1, 1, true);
		assertTrue(e9.collidesWith(e10));

		AbstractEntity e11 = new TestEntity(1, 1, 0, 0.1f, 0.1f, 1, 1, 1, true);
		AbstractEntity e12 = new TestEntity(1.2f, 1.2f, 0, 0.1f, 0.1f, 1, 1, 1, true);
		assertFalse(e11.collidesWith(e12));
	}
}
