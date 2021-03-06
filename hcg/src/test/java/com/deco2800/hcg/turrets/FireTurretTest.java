package com.deco2800.hcg.turrets;

import static org.junit.Assert.*;

import org.junit.Test;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.bullets.Fireball;
import com.deco2800.hcg.entities.enemyentities.Enemy;
import com.deco2800.hcg.entities.enemyentities.Squirrel;
import com.deco2800.hcg.entities.turrets.Explosion;
import com.deco2800.hcg.entities.turrets.FireTurret;
import com.deco2800.hcg.types.Weathers;

public class FireTurretTest extends TurretBaseTest {

	private void setupWeather(Weathers weather) {
		world.setWeather(weather);
		turret = new FireTurret(corpse);
	}

	private void setupNoWeather() {
		turret = new FireTurret(corpse);
	}
	
	@Test
	public void testShoot() {
		setupNoWeather();
		for (int i = 1; i <= 13; i++) {
			turret.update(sw, i);
		}

		int counter = 0;
		for (AbstractEntity entity : world.getEntities()) {
			if (entity instanceof Fireball) {
				counter++;
			}
		}
		assertEquals("There should be exactly 12 fireballs", 12, counter);
	}
	
	@Test
	public void testDestroy() {
		setupNoWeather();
		
		for (int i = 1; i <= 14; i++) {
			assertTrue("The world should still contain the corpse", world.containsEntity(corpse));
			turret.update(sw, i);
		}
		assertFalse("The turret should have been destroyed", world.containsEntity(corpse));
	}
	
	@Test(timeout=1000)
	public void testRainyWeather() {
		setupWeather(Weathers.RAIN);
		
		turret.update(sw, 0);
		turret.update(sw, 1);
		
		Fireball fireball = null;
		for (AbstractEntity entity : world.getEntities()) {
			if (entity instanceof Fireball) {
				fireball = (Fireball)entity;
			}
		}
		if(fireball == null) {
			fail("No fireball created");
		}
		
		while(world.containsEntity(fireball)) {
			fireball.onTick(0);
		}
		
		assertFalse(world.containsEntity(fireball));
	}
	
	@Test
	public void testSandstorm() {
		setupWeather(Weathers.SANDSTORM);
		
		for (int i = 1; i <= 14; i++) {
			turret.update(sw, i);
		}

		for (AbstractEntity entity : world.getEntities()) {
			if (entity instanceof Explosion) {
				return;
			}
		}
		fail("There should be an explosion during a sandstorm");
	}
	
	@Test
	public void testSandstormExplosion() {
		setupWeather(Weathers.SANDSTORM);
		Enemy closeEnemy = new Squirrel(6, 5, 0, 1);
		world.addEntity(closeEnemy);
		
		for (int i = 1; i <= 14; i++) {
			assertTrue("Close enemy should still be in world", world.containsEntity(closeEnemy));
			turret.update(sw, i);
		}
		assertFalse("Close enemy should be destroyed", world.containsEntity(closeEnemy));
	}
}
