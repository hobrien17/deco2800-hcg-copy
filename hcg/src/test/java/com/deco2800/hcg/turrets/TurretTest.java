package com.deco2800.hcg.turrets;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.bullets.Bullet;
import com.deco2800.hcg.entities.bullets.Fireball;
import com.deco2800.hcg.entities.corpse_entities.BasicCorpse;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.entities.enemy_entities.Enemy;
import com.deco2800.hcg.entities.enemy_entities.Squirrel;
import com.deco2800.hcg.entities.turrets.AbstractTurret;
import com.deco2800.hcg.entities.turrets.ExplosiveTurret;
import com.deco2800.hcg.entities.turrets.FireTurret;
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
	private final static int[] ENEMY_X = {7, 3, 5, 5};
	private final static int[] ENEMY_Y = {5, 5, 7, 3};
	
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
		
		enemy = new Squirrel(ENEMY_X[0], ENEMY_Y[0], 0, 0);
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
	
	@Test(timeout=1000)
	public void testSunflowerTurretShoot() {	
		setupSunflowerFullTest();
		
		turret.update(sw, 0);
		Bullet bullet = null;
		for(AbstractEntity entity : gm.getWorld().getEntities()) {
			if(entity instanceof Bullet) {
				bullet = (Bullet)entity;
				break;
			}
		}
		if(bullet == null) {
			fail();
		}
		
		while(bullet.getPosX() < ENEMY_X[0] || bullet.getPosY() < ENEMY_Y[0] ) {
			bullet.onTick(0);
		}
		
		checkTexture(SunflowerTurret.class);
	}
	
	@Test
	public void testSunflowerTurretNoShoot() {
		setupSunflowerTest();
		
		turret.update(sw, 0);
		for(AbstractEntity entity : gm.getWorld().getEntities()) {
			if(entity instanceof Bullet) {
				fail();
			}
		}
	}
	
	@Test(timeout=1000)
	public void testSunflowerTurretAmmo() {
		setupSunflowerFullTest();
		
		for(int i = 0; i < 11; i++) {
			assertTrue(gm.getWorld().containsEntity(corpse));
			turret.update(sw, 0);
		}
		assertFalse(gm.getWorld().containsEntity(corpse));
	}
	
	private void setupFireTest() {
		setupGM();
		
		sw = (StopwatchManager)gm.getManager(StopwatchManager.class);
		tm = (TimeManager)gm.getManager(TimeManager.class);
		corpse = new BasicCorpse(CORPSE_X, CORPSE_Y, 0);
		gm.getWorld().addEntity(corpse);
		turret = new FireTurret(corpse);
	}
	
	@Test
	public void testFireball() {
		setupFireTest();
		turret.update(sw, 0);
		turret.update(sw, 1);
		
		Fireball bullet = null;
		for(AbstractEntity entity : gm.getWorld().getEntities()) {
			if(entity instanceof Fireball) {
				bullet = (Fireball)entity;
				break;
			}
		}
		if(bullet == null) {
			fail();
		}
	}
	
	@Test
	public void testFireTurret() {
		setupFireTest();
		assertTrue(gm.getWorld().containsEntity(corpse));
		for(int i = 1; i <= 6; i++) {
			turret.update(sw, i);
		}
		assertFalse(gm.getWorld().containsEntity(corpse));
		
		checkTexture(FireTurret.class);

	}
	
	private void setupIceTest() {
		setupGM();
		
		sw = (StopwatchManager)gm.getManager(StopwatchManager.class);
		tm = (TimeManager)gm.getManager(TimeManager.class);
		corpse = new BasicCorpse(CORPSE_X, CORPSE_Y, 0);
		gm.getWorld().addEntity(corpse);
		turret = new IceTurret(corpse);
		
		enemy = new Squirrel(ENEMY_X[0], ENEMY_Y[0], 0, 0);
		gm.getWorld().addEntity(enemy);
	}
	
	@Test
	public void testIceTurret() {
		setupIceTest();
		enemy.setSpeed(10f);
		for(int i = 0; i < 5; i++) {
			turret.update(sw, i);
		}
		assertEquals(enemy.getSpeedX(), 0, 0);
		assertEquals(enemy.getSpeedY(), 0, 0);
		for(int i = 0; i < 10; i++) {
			turret.update(sw, i);
		}
		assertFalse(gm.getWorld().containsEntity(corpse));
		
		checkTexture(IceTurret.class);
	}
	
	private void setupExplosiveTest() {
		setupGM();
		
		sw = (StopwatchManager)gm.getManager(StopwatchManager.class);
		tm = (TimeManager)gm.getManager(TimeManager.class);
		corpse = new BasicCorpse(CORPSE_X, CORPSE_Y, 0);
		gm.getWorld().addEntity(corpse);
		turret = new ExplosiveTurret(corpse);
		
		enemy = new Squirrel(ENEMY_X[0], ENEMY_Y[0], 0, 0);
		gm.getWorld().addEntity(enemy);
	}
	
	@Test
	public void testExplosiveTurret() {
		setupExplosiveTest();
		
		for(int i = 0; i < 5; i++) {
			turret.update(sw, i);
		}
		assertFalse(gm.getWorld().containsEntity(enemy));
		
		checkTexture(ExplosiveTurret.class);
	}
	
	private void checkTexture(Class<? extends AbstractTurret> turretClass) {
		//System.out.println(corpse.getTexture());
		//assertEquals(corpse.getTexture(), textures.get(turretClass));
	}
	
	
	
	
}
