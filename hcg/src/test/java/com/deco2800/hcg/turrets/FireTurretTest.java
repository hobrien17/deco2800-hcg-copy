package com.deco2800.hcg.turrets;

import static org.junit.Assert.*;

import org.junit.Test;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.bullets.Fireball;
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
		for (int i = 1; i <= 5; i++) {
			turret.update(sw, i);
		}

		int counter = 0;
		for (AbstractEntity entity : world.getEntities()) {
			if (entity instanceof Fireball) {
				counter++;
			}
		}
		assertEquals("There should be exactly 4 fireballs", 4, counter);
	}
	
	@Test
	public void testDestroy() {
		setupNoWeather();
		
		for (int i = 1; i <= 6; i++) {
			assertTrue("The world should still contain the corpse", world.containsEntity(corpse));
			turret.update(sw, i);
		}
		assertFalse("The turret should have been destroyed", world.containsEntity(corpse));
	}
	
	@Test
	public void testFireballDistance() {
		setupNoWeather();
		
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
		
		fireball.setPosX(13.5f);
		fireball.setPosY(13.5f);
		fireball.onTick(0);
		
		assertFalse(world.containsEntity(fireball));
	}
	
	@Test
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
		
		fireball.setPosX(10.5f);
		fireball.setPosY(10.5f);
		fireball.onTick(0);
		
		assertFalse(world.containsEntity(fireball));
	}
	
	@Test
	public void testSandstorm() {
		setupWeather(Weathers.SANDSTORM);
		
		for (int i = 1; i <= 6; i++) {
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
	public void testSprite() {
		setupNoWeather();
		assertEquals("Sprite is incorrect", "fire_corpse", corpse.getTexture());
	}
}
