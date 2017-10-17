package com.deco2800.hcg.turrets;

import static org.junit.Assert.*;

import org.junit.Test;

import com.badlogic.gdx.graphics.Color;
import com.deco2800.hcg.entities.enemyentities.Enemy;
import com.deco2800.hcg.entities.enemyentities.Squirrel;
import com.deco2800.hcg.entities.turrets.IceTurret;
import com.deco2800.hcg.types.Weathers;

public class IceTurretTest extends TurretBaseTest {
	private Enemy farEnemy;
	
	private void setupWeather(Weathers weather) {
		world.setWeather(weather);
		turret = new IceTurret(corpse);
		farEnemy = new Squirrel(20, 5, 0, 1);
		farEnemy.setSpeed(1f);
		enemy.setSpeed(1f);
		world.addEntity(farEnemy);
	}

	private void setupNoWeather() {
		turret = new IceTurret(corpse);
		farEnemy = new Squirrel(20, 5, 0, 1);
		farEnemy.setSpeed(1f);
		enemy.setSpeed(1f);
		world.addEntity(farEnemy);
	}
	
	@Test
	public void testFreeze() {
		setupNoWeather();
		
		for (int i = 1; i <= 5; i++) {
			turret.update(sw, i); // update until the turret detonates
		}
		assertEquals("The enemy should be frozen", 0f, enemy.getMovementSpeed(), 0);
		assertEquals("The second enemy should be slower than normal", 0.5f, farEnemy.getMovementSpeed(), 0);
	}
	
	@Test
	public void testFreezeReset() {
		setupNoWeather();
		
		for (int i = 1; i <= 15; i++) {
			turret.update(sw, i); // update until the turret is destroyed
		}
		assertFalse("The enemy should not be frozen", 0f == enemy.getMovementSpeed());
	}
	
	@Test
	public void testDestroy() {
		setupNoWeather();
		
		for (int i = 1; i <= 15; i++) {
			assertTrue("The world should contain the corpse", world.containsEntity(corpse));
			turret.update(sw, i); // update until the turret is destroyed
		}
		assertFalse("The world should not contain the corpse", world.containsEntity(corpse));
	}
	
	@Test
	public void testColour() {
		setupNoWeather();
		
		for (int i = 1; i <= 5; i++) {
			turret.update(sw, i); // update until the turret detonates
		}
		assertEquals("The enemies should be blue", Color.ROYAL, enemy.getTint());
		assertEquals("The far away enemies should be cyan", Color.CYAN, farEnemy.getTint());
		
		for(int i = 1; i <= 10; i++) {
			turret.update(sw, i); //update until the turret is destroyed
		}
		assertEquals("The enemies should be normal coloured when un-frozen", null, enemy.getTint());
		assertEquals("The enemies should be normal coloured when un-frozen", null, enemy.getTint());
	}
	
	@Test
	public void testSnowyWeather() {
		setupWeather(Weathers.SNOW);
		
		for (int i = 1; i <= 5; i++) {
			turret.update(sw, i); // update until the turret detonates
		}
		assertEquals("The enemy should be frozen", 0f, enemy.getMovementSpeed(), 0);
		assertEquals("The second enemy should be frozen when snowing", 0f, farEnemy.getMovementSpeed(), 0);
	}
	
	@Test
	public void testSandstorm() {
		setupWeather(Weathers.SANDSTORM);
		
		for (int i = 1; i <= 5; i++) {
			turret.update(sw, i); // update until the turret detonates
		}
		assertEquals("The enemy should be slower than normal", 0.5f, enemy.getMovementSpeed(), 0);
		assertEquals("The second enemy should be slower than normal", 0.5f, farEnemy.getMovementSpeed(), 0);
	}
}
