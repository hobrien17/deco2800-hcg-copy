package com.deco2800.hcg.turrets;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.bullets.Bullet;
import com.deco2800.hcg.entities.enemyentities.Enemy;
import com.deco2800.hcg.entities.enemyentities.Squirrel;
import com.deco2800.hcg.entities.turrets.SunflowerTurret;
import com.deco2800.hcg.types.Weathers;

public class SunflowerTurretTest extends TurretBaseTest {
	
	private void setupWeather(Weathers weather) {
		world.setWeather(weather);
		turret = new SunflowerTurret(corpse);
	}
	
	private void setupNoWeather() {
		turret = new SunflowerTurret(corpse);
	}
	
	/*
	 * Tests that the turret shoots a bullet when an enemy is close
	 */
	@Test
	public void testShoot() {
		setupNoWeather();
		turret.update(sw, 0);
		for(AbstractEntity entity : world.getEntities()) {
			if(entity instanceof Bullet) {
				return;
			}
		}
		fail("A bullet should have been spawned");
	}
	
	/*
	 * Tests that the turret does not shoot when no enemies are in range
	 */
	@Test
	public void testNoShoot() {
		setupNoWeather();
		world.removeEntity(enemy);
		turret.update(sw, 0);
		for(AbstractEntity entity : world.getEntities()) {
			if(entity instanceof Bullet) {
				fail("No bullets should have been spawned");
			}
		}
	}
	
	/*
	 * Tests the turret's limited ammo
	 */
	@Test
	public void testAmmo() {
		setupNoWeather();
		for (int i = 0; i <= 10; i++) {
			assertTrue("The world should still contain the corpse", world.containsEntity(corpse));
			turret.update(sw, 0);
		}
		assertFalse("The turret should have been destroyed", world.containsEntity(corpse));
	}
	
	/*
	 * Test that the turret has less ammo in wind
	 */
	@Test
	public void testWindyWeather() {
		setupWeather(Weathers.WIND);
		for (int i = 0; i <= 5; i++) {
			assertTrue("The world should still contain the corpse", world.containsEntity(corpse));
			turret.update(sw, 0);
		}
		assertFalse("The turret should have been destroyed", world.containsEntity(corpse));
	}
	
	/*
	 * Test that the turret has more ammo in a storm
	 */
	@Test
	public void testStormyWeather() {
		setupWeather(Weathers.STORM);
		for (int i = 0; i <= 60; i++) {
			assertTrue("The world should still contain the corpse", world.containsEntity(corpse));
			turret.update(sw, 0);
		}
		assertFalse("The turret should have been destroyed", world.containsEntity(corpse));
	}
	
	/*
	 * Test that the turret will randomly shoot in a storm
	 */
	@Test
	public void testStormyWeather2() {
		setupWeather(Weathers.STORM);
		world.removeEntity(enemy);
		for (int i = 0; i <= 60; i++) {
			assertTrue("The world should still contain the corpse", world.containsEntity(corpse));
			turret.update(sw, 0);
		}
		assertFalse("The turret should have been destroyed", world.containsEntity(corpse));
	}
}
