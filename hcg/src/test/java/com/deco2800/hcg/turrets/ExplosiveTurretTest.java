package com.deco2800.hcg.turrets;

import static org.junit.Assert.*;

import org.junit.Test;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.enemyentities.Enemy;
import com.deco2800.hcg.entities.enemyentities.Squirrel;
import com.deco2800.hcg.entities.turrets.Explosion;
import com.deco2800.hcg.entities.turrets.ExplosiveTurret;
import com.deco2800.hcg.entities.turrets.FireTurret;
import com.deco2800.hcg.types.Weathers;

public class ExplosiveTurretTest extends TurretBaseTest {
	Enemy farEnemy;
	
	private void setupWeather(Weathers weather) {
		world.setWeather(weather);
		turret = new ExplosiveTurret(corpse);
		farEnemy = new Squirrel(12, 5, 0, 1);
		world.addEntity(farEnemy);
	}

	private void setupNoWeather() {
		turret = new ExplosiveTurret(corpse);
		farEnemy = new Squirrel(12, 5, 0, 1);
		world.addEntity(farEnemy);
	}
	
	@Test
	public void testExplode() {
		setupNoWeather();
		for (int i = 1; i <= 5; i++) {
			assertTrue("The enemies should still be in the world", world.containsEntity(enemy));
			assertTrue("The enemies should still be in the world", world.containsEntity(farEnemy));
			turret.update(sw, i);
		}
		
		assertFalse("The enemy should have been destroyed", world.containsEntity(enemy));
		assertTrue("The far enemy should still be in the world", world.containsEntity(farEnemy));
	}
	
	@Test
	public void testDestroy() {
		setupNoWeather();
		for (int i = 1; i <= 5; i++) {
			assertTrue("The corpse should still be in the world", world.containsEntity(corpse));
			turret.update(sw, i);
		}
		
		assertFalse("The corpse should have been destroyed", world.containsEntity(corpse));
		for(AbstractEntity entity : world.getEntities()) {
			if(entity instanceof Explosion) {
				return;
			}
		}
		fail("An explosion should have been created");
	}
	
	@Test(timeout=1000)
	public void testExplosion() {
		setupNoWeather();
		for (int i = 1; i <= 5; i++) {
			turret.update(sw, i);
		}
		
		Explosion exp = null;
		for (AbstractEntity entity : world.getEntities()) {
			if (entity instanceof Explosion) {
				exp = (Explosion) entity;
				break;
			}
		}
		if (exp == null) {
			fail("There should be an explosion in the world");
			return;
		}
		assertEquals("The explosion should have the right sprite", "explosion", exp.getTexture());
		assertEquals("The explosion should be at the correct x position", 6, exp.getPosX(), 0);
		assertEquals("The explosion should be at the correct y position", 5, exp.getPosY(), 0);
		assertEquals("The explosion should have the correct x-length", 0.01f, exp.getXRenderLength(), 0);
		assertEquals("The explosion should have the correct y-length", 0.01f, exp.getYRenderLength(), 0);
		exp.onTick(0);
		assertEquals("The explosion should be at the correct x position", 6 - 0.4f, exp.getPosX(), 0);
		assertEquals("The explosion should be at the correct y position", 5, exp.getPosY(), 0);
		assertEquals("The explosion should have the correct new x-length", 0.01f + 0.4f, exp.getXRenderLength(), 0);
		assertEquals("The explosion should have the correct new y-length", 0.01f + 0.4f, exp.getYRenderLength(), 0);

		while (world.containsEntity(exp)) {
			exp.onTick(0);
			// this loop will continue until the explosion is destroyed
			// if the explosion is never destroyed, the test will timeout (after 1 second)
		}
	}
	
	/*
	 * Test that the explosion will be removed on boundary cases of x and y render length
	 */
	@Test
	public void testExplosionBoundaryCases() {
		for (int j = 0; j <= 1; j++) {
			setupNoWeather();
			
			for (int i = 1; i <= 5; i++) {
				turret.update(sw, i);
			}

			Explosion exp = null;
			for (AbstractEntity entity : world.getEntities()) {
				if (entity instanceof Explosion) {
					exp = (Explosion) entity;
					break;
				}
			}
			if (exp == null) {
				fail("There should be an explosion in the world");
				return;
			}
			
			if(j == 0) {
				exp.growRender(-0.01f, 0);
			} else if(j == 1) {
				exp.growRender(0, -0.01f);
			}
			exp.onTick(0);
			assertFalse(world.containsEntity(exp));
			
			resetTest();
		}

	}
	
	@Test
	public void testSnowyWeather() {
		setupWeather(Weathers.SNOW);
		
		for (int i = 1; i <= 10; i++) {
			assertTrue("The corpse should still be in the world", world.containsEntity(corpse));
			turret.update(sw, i);
		}
		
		assertFalse("The corpse should have been destroyed", world.containsEntity(corpse));
		for(AbstractEntity entity : world.getEntities()) {
			if(entity instanceof Explosion) {
				return;
			}
		}
		fail("An explosion should have been created");
	}
	
	@Test
	public void testDrought() {
		setupWeather(Weathers.DROUGHT);
		
		for (int i = 1; i <= 5; i++) {
			assertTrue("The enemies should still be in the world", world.containsEntity(enemy));
			assertTrue("The enemies should still be in the world", world.containsEntity(farEnemy));
			turret.update(sw, i);
		}
		
		assertFalse("The enemy should have been destroyed", world.containsEntity(enemy));
		assertFalse("The far enemy should have been destroyed", world.containsEntity(farEnemy));
	}
	
	@Test
	public void testDroughtExplosion() {
		setupWeather(Weathers.DROUGHT);
		
		for (int i = 1; i <= 5; i++) {
			turret.update(sw, i);
		}
		
		Explosion exp = null;
		for (AbstractEntity entity : world.getEntities()) {
			if (entity instanceof Explosion) {
				exp = (Explosion) entity;
				break;
			}
		}
		if (exp == null) {
			fail("There should be an explosion in the world");
			return;
		}
		exp.onTick(0);
		assertEquals("The explosion should be at the correct x position", 6 - 0.5f, exp.getPosX(), 0);
		assertEquals("The explosion should be at the correct y position", 5, exp.getPosY(), 0);
		assertEquals("The explosion should have the correct new x-length", 0.01f + 0.5f, exp.getXRenderLength(), 0);
		assertEquals("The explosion should have the correct new y-length", 0.01f + 0.5f, exp.getYRenderLength(), 0);

		while (world.containsEntity(exp)) {
			exp.onTick(0);
			// this loop will continue until the explosion is destroyed
			// if the explosion is never destroyed, the test will timeout (after 1 second)
		}
	}
}
