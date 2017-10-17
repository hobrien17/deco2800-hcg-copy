package com.deco2800.hcg.turrets;

import static org.junit.Assert.*;

import org.junit.Test;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.bullets.GrassBullet;
import com.deco2800.hcg.entities.turrets.FireTurret;
import com.deco2800.hcg.entities.turrets.GrassTurret;
import com.deco2800.hcg.types.Weathers;

public class GrassTurretTest extends TurretBaseTest {
	
	private void setupWeather(Weathers weather) {
		world.setWeather(weather);
		turret = new GrassTurret(corpse);
	}

	private void setupNoWeather() {
		turret = new GrassTurret(corpse);
	}
	
	@Test
	public void testShoot() {
		setupNoWeather();
		
		for(int i = 1; i <= 5; i++) {
			turret.update(sw, i);
		}
		
		int counter = 0;
		for (AbstractEntity entity : gm.getWorld().getEntities()) {
			if (entity instanceof GrassBullet) {
				counter++; // add one grass bullet to the counter
			}
		}
		assertEquals("Exactly 72 grass bullets should have spawned", counter, 72);
	}
	
	@Test
	public void testDestroy() {
		setupNoWeather();
		
		for(int i = 1; i <= 5; i++) {
			assertTrue("The corpse should still be in the world", world.containsEntity(corpse));
			turret.update(sw, i);
		}
		
		assertFalse("The turret should have been destroyed", world.containsEntity(corpse));
	}
	
	@Test
	public void testWindyWeather() {
		setupWeather(Weathers.WIND);
		
		turret.update(sw, 1);
		assertFalse("The turret should have been destroyed", world.containsEntity(corpse));
	}
	
	@Test
	public void testStormyWeather() {
		setupWeather(Weathers.STORM);
		
		for(int i = 1; i <= 5; i++) {
			turret.update(sw, i);
		}
		
		int counter = 0;
		for (AbstractEntity entity : gm.getWorld().getEntities()) {
			if (entity instanceof GrassBullet) {
				counter++; // add one grass bullet to the counter
			}
		}
		assertEquals("Exactly 18 grass bullets should have spawned in a storm", counter, 18);
	}
}
