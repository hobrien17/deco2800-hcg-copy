package com.deco2800.hcg.turrets;

import static org.junit.Assert.*;

import org.junit.Test;

import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.turrets.WaterTurret;
import com.deco2800.hcg.types.Weathers;

public class WaterTurretTest extends TurretBaseTest {
	Player player;
	
	private void setupWeather(Weathers weather) {
		world.setWeather(weather);
		turret = new WaterTurret(corpse);
		player = new Player(5, 6, 0);
		world.addEntity(player);
	}

	private void setupNoWeather() {
		turret = new WaterTurret(corpse);
		player = new Player(5, 6, 0);
		world.addEntity(player);
	}
	
	@Test
	public void testHeal() {
		setupNoWeather();

		assertEquals("Health should start at max", player.getHealthMax(), player.getHealthCur());
		player.takeDamage(10);
		for (int i = 9; i >= 0; i--) {
			turret.update(sw, 10 - i);
			assertEquals("Health should currently be " + i + "below max", player.getHealthMax() - i,
					player.getHealthCur());
		}
	}
	
	@Test
	public void testHealOverMax() {
		setupNoWeather();

		assertEquals("Health should start at max", player.getHealthMax(), player.getHealthCur());
		player.takeDamage(5);
		for (int i = 9; i >= 0; i--) {
			turret.update(sw, 10 - i);
			int below = Math.max(i - 5, 0);
			assertEquals("Health should currently be " + below + "below max", player.getHealthMax() - below,
					player.getHealthCur());
		}
	}
	
	@Test
	public void testDestroy() {
		setupNoWeather();
		for(int i = 1; i <= 30; i++) {
			assertTrue("Corpse should be in world", world.containsEntity(corpse));
			turret.update(sw, i);
		}
		assertFalse("Turret should have been destroyed", world.containsEntity(corpse));
	}
	
	@Test
	public void testDrought() {
		setupWeather(Weathers.DROUGHT);
		for(int i = 1; i <= 20; i++) {
			assertTrue("Corpse should be in world", world.containsEntity(corpse));
			turret.update(sw, i);
		}
		assertFalse("Turret should have been destroyed", world.containsEntity(corpse));
	}
	
	@Test
	public void testRainyWeatherHeal() {
		setupWeather(Weathers.RAIN);

		assertEquals("Health should start at max", player.getHealthMax(), player.getHealthCur());
		player.takeDamage(20);
		for (int i = 9; i >= 0; i--) {
			turret.update(sw, 10 - i);
			assertEquals("Health should currently be " + 2*i + "below max", player.getHealthMax() - 2*i,
					player.getHealthCur());
		}
	}
	
	@Test
	public void testRainyWeatherDestroy() {
		setupWeather(Weathers.RAIN);
		for(int i = 1; i <= 20; i++) {
			assertTrue("Corpse should be in world", world.containsEntity(corpse));
			turret.update(sw, i);
		}
		assertFalse("Turret should have been destroyed", world.containsEntity(corpse));
	}
}
