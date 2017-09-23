package com.deco2800.hcg.turrets;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.bullets.Bullet;
import com.deco2800.hcg.entities.bullets.Fireball;
import com.deco2800.hcg.entities.bullets.GrassBullet;
import com.deco2800.hcg.entities.corpse_entities.BasicCorpse;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.entities.enemy_entities.Enemy;
import com.deco2800.hcg.entities.enemy_entities.Squirrel;
import com.deco2800.hcg.entities.garden_entities.seeds.Seed;
import com.deco2800.hcg.entities.turrets.AbstractTurret;
import com.deco2800.hcg.entities.turrets.ExplosiveTurret;
import com.deco2800.hcg.entities.turrets.FireTurret;
import com.deco2800.hcg.entities.turrets.GrassTurret;
import com.deco2800.hcg.entities.turrets.IceTurret;
import com.deco2800.hcg.entities.turrets.SunflowerTurret;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.StopwatchManager;
import com.deco2800.hcg.managers.TimeManager;
import com.deco2800.hcg.worlds.World;

public class TurretTest {
	
	private GameManager gm;
	
	private StopwatchManager sw;
	private TimeManager tm;
	private Corpse corpse;
	private AbstractTurret turret;
	private Enemy enemy;
	
	private final static int CORPSE_X = 5;
	private final static int CORPSE_Y = 5;
	private final static int ENEMY_X = 7;
	private final static int ENEMY_Y = 5;
	
	private static Map<Class<? extends AbstractTurret>, String> textures;
	
	private void setupGM() {
		gm = GameManager.get();
		gm.setWorld(new World());
		
		setupTextures();
	}
	
	private static void setupTextures() {
		textures = new HashMap<>();
		textures.put(SunflowerTurret.class, "sunflower_corpse");
		textures.put(ExplosiveTurret.class, "cactus_corpse_03");
		textures.put(IceTurret.class, "ice_corpse_03");
		textures.put(FireTurret.class, "fire_corpse");
	}
	
	private void setupSunflowerFullTest() {
		setupSunflowerTest();
		
		enemy = new Squirrel(ENEMY_X, ENEMY_Y, 0, 0); //add an enemy to test the sunflower shooting
		gm.getWorld().addEntity(enemy);
	}
	
	private void setupSunflowerTest() {
		setupGM();
		sw = (StopwatchManager)gm.getManager(StopwatchManager.class);
		tm = (TimeManager)gm.getManager(TimeManager.class);
		corpse = new BasicCorpse(CORPSE_X, CORPSE_Y, 0);
		gm.getWorld().addEntity(corpse);
		turret = new SunflowerTurret(corpse);
	}
	
	/*
	 * Tests that the sunflower turret fires a bullet when an enemy is nearby
	 */
	@Test
	public void testSunflowerTurretShoot() {	
		setupSunflowerFullTest();
		
		turret.update(sw, 0); //update the turret to spawn a bullet
		
		for(AbstractEntity entity : gm.getWorld().getEntities()) {
			if(entity instanceof Bullet) {
				return; //if there is a bullet in the game, return
			}
		}
		fail("A bullet should have been created"); 
		//if we get here a bullet does not exist, so we fail the test
	}
	
	/*
	 * Tests that the sunflower turret does nothing when no enemy is nearby
	 */
	@Test
	public void testSunflowerTurretNoShoot() {
		setupSunflowerTest();
		
		turret.update(sw, 0);
		for(AbstractEntity entity : gm.getWorld().getEntities()) {
			if(entity instanceof Bullet) {
				fail("A bullet should not have been shot from the turret"); 
				//there shouldn't be any bullets in the world
			}
		}
	}
	
	/*
	 * Tests that the sunflower turret destroys itself when out of ammo
	 */
	@Test
	public void testSunflowerTurretAmmo() {
		setupSunflowerFullTest();
		
		for(int i = 0; i < 11; i++) {
			assertTrue("The world should still contain the corpse", gm.getWorld().containsEntity(corpse));
			turret.update(sw, 0);
		}
		assertFalse("The turret should have been destroyed", gm.getWorld().containsEntity(corpse));
	}
	
	private void setupFireTest() {
		setupGM();
		
		sw = (StopwatchManager)gm.getManager(StopwatchManager.class);
		tm = (TimeManager)gm.getManager(TimeManager.class);
		corpse = new BasicCorpse(CORPSE_X, CORPSE_Y, 0);
		gm.getWorld().addEntity(corpse);
		turret = new FireTurret(corpse);
	}
	
	/*
	 * Tests that a fireball is spawned when the fire turret is planted
	 */
	@Test
	public void testFireball() {
		setupFireTest();
		turret.update(sw, 0); //update the turret twice to spawn a fireball
		turret.update(sw, 1);
		
		for(AbstractEntity entity : gm.getWorld().getEntities()) {
			if(entity instanceof Fireball) {
				return; //if a fireball exists return
			}
		}
		fail("A fireball should have been created"); //if we get here a fireball does not exist, so we fail the test
	}
	
	/*
	 * Tests that the fire turret destroys itself once all fireballs are fired
	 */
	@Test
	public void testFireTurret() {
		setupFireTest();
		
		for(int i = 0; i < 6; i++) {
			assertTrue("The world should still contain the corpse", gm.getWorld().containsEntity(corpse));
			turret.update(sw, i);
		}
		assertFalse("The turret should have been destroyed", gm.getWorld().containsEntity(corpse));
		
	}
	
	private void setupIceTest() {
		setupGM();
		
		sw = (StopwatchManager)gm.getManager(StopwatchManager.class);
		tm = (TimeManager)gm.getManager(TimeManager.class);
		corpse = new BasicCorpse(CORPSE_X, CORPSE_Y, 0);
		gm.getWorld().addEntity(corpse);
		turret = new IceTurret(corpse);
		
		enemy = new Squirrel(ENEMY_X, ENEMY_Y, 0, 0); //add an enemy to test speed change
		enemy.setSpeed(1f);
		gm.getWorld().addEntity(enemy);
	}
	
	/*
	 * Tests that the ice turret freezes nearby enemies, and destroys itself when done
	 */
	@Test
	public void testIceTurret() {
		setupIceTest();
		for(int i = 0; i < 5; i++) {
			turret.update(sw, i); //update until the turret detonates
		}
		assertEquals("The enemy should be frozen", enemy.getSpeedX(), 0, 0); //check that the enemy is frozen
		assertEquals("The enemy should be frozen", enemy.getSpeedY(), 0, 0);
		for(int i = 0; i < 10; i++) {
			assertTrue("The corpse should still be in the world", gm.getWorld().containsEntity(corpse));
			turret.update(sw, i); //update until the turret destroys itself
		}
		assertFalse("The turret should have been destroyed", gm.getWorld().containsEntity(corpse)); 
		//check that the turret has been destroyed
	}
	
	private void setupExplosiveTest() {
		setupGM();
		
		sw = (StopwatchManager)gm.getManager(StopwatchManager.class);
		tm = (TimeManager)gm.getManager(TimeManager.class);
		corpse = new BasicCorpse(CORPSE_X, CORPSE_Y, 0);
		gm.getWorld().addEntity(corpse);
		turret = new ExplosiveTurret(corpse);
		
		enemy = new Squirrel(ENEMY_X, ENEMY_Y, 0, 0); //add an enemy to test enemy destruction
		gm.getWorld().addEntity(enemy);
	}
	
	/*
	 * Tests that the explosive turret destroys itself and any nearby enemies
	 */
	@Test
	public void testExplosiveTurret() {
		setupExplosiveTest();
		
		for(int i = 0; i < 5; i++) {
			assertTrue("The world should still contain the enemy", gm.getWorld().containsEntity(enemy));
			assertTrue("The world should still contain the corpse", gm.getWorld().containsEntity(corpse));
			turret.update(sw, i);
		}
		assertFalse("The enemy should have been destroyed", gm.getWorld().containsEntity(enemy)); 
		//check that the enemy has been destroyed
		assertFalse("The turret should have been destroyed", gm.getWorld().containsEntity(corpse)); 
		//check that the turret has been destroyed
	}
	
	private void setupGrassTest() {
		setupGM();
		
		sw = (StopwatchManager)gm.getManager(StopwatchManager.class);
		tm = (TimeManager)gm.getManager(TimeManager.class);
		corpse = new BasicCorpse(CORPSE_X, CORPSE_Y, 0);
		gm.getWorld().addEntity(corpse);
		turret = new GrassTurret(corpse);
	}
	
	/*
	 * Tests that the grass turret spawns exactly 72 bullets
	 */
	@Test
	public void testGrassTurret() {
		setupGrassTest();
		
		for(int i = 0; i < 5; i++) {
			turret.update(sw, i);
		}
		int counter = 0;
		for(AbstractEntity entity : gm.getWorld().getEntities()) {
			if(entity instanceof GrassBullet) {
				counter++; //add one grass bullet to the counter
			}
		}
		assertEquals("Exactly 72 grass bullets should have spawned", counter, 72);
		//we should have 72 grass bullets in the world
	}
	
	@Test
	public void testTextures() {
		setupGM();
		sw = (StopwatchManager)gm.getManager(StopwatchManager.class);
		
		corpse = new BasicCorpse(CORPSE_X, CORPSE_Y, 0);
		assertEquals("An empty corpse should have the empty corpse sprite", corpse.getTexture(), "corpse");
		corpse.plantInside(new Seed(Seed.Type.SUNFLOWER));
		assertEquals("The corpse should have the sunflower sprite", corpse.getTexture(), "sunflower_corpse");
		
		corpse = new BasicCorpse(CORPSE_X, CORPSE_Y, 0);
		corpse.plantInside(new Seed(Seed.Type.WATER));
		assertEquals("The corpse should have the water sprite", corpse.getTexture(), "water_corpse");
		
		corpse = new BasicCorpse(CORPSE_X, CORPSE_Y, 0);
		corpse.plantInside(new Seed(Seed.Type.GRASS));
		assertEquals("The corpse should have the grass sprite", corpse.getTexture(), "grass_corpse");
		
		corpse = new BasicCorpse(CORPSE_X, CORPSE_Y, 0);
		corpse.plantInside(new Seed(Seed.Type.FIRE));
		assertEquals("The corpse should have the fire sprite", corpse.getTexture(), "fire_corpse");
		
		corpse = new BasicCorpse(CORPSE_X, CORPSE_Y, 0);
		corpse.plantInside(new Seed(Seed.Type.EXPLOSIVE));
		for(int i = 0; i < 3; i++) {
			assertEquals("The corpse should have the first cactus sprite", corpse.getTexture(), "cactus_corpse_01");
			corpse.getTurret().update(sw, i);
		}
		assertEquals("The corpse should have the second cactus sprite", corpse.getTexture(), "cactus_corpse_02");
		corpse.getTurret().update(sw, 3);
		assertEquals("The corpse should have the third cactus sprite", corpse.getTexture(), "cactus_corpse_03");
		
		corpse = new BasicCorpse(CORPSE_X, CORPSE_Y, 0);
		corpse.plantInside(new Seed(Seed.Type.ICE));
		for(int i = 0; i < 3; i++) {
			assertEquals("The corpse should have the first ice sprite", corpse.getTexture(), "ice_corpse_01");
			corpse.getTurret().update(sw, i);
		}
		assertEquals("The corpse should have the second ice sprite", corpse.getTexture(), "ice_corpse_02");
		corpse.getTurret().update(sw, 3);
		assertEquals("The corpse should have the third ice sprite", corpse.getTexture(), "ice_corpse_03");
	}
	
	
}
