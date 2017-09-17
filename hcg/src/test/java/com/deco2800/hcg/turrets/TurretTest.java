package com.deco2800.hcg.turrets;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.bullets.Bullet;
import com.deco2800.hcg.entities.corpse_entities.BasicCorpse;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.entities.enemy_entities.Enemy;
import com.deco2800.hcg.entities.enemy_entities.Squirrel;
import com.deco2800.hcg.entities.turrets.AbstractTurret;
import com.deco2800.hcg.entities.turrets.SunflowerTurret;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.StopwatchManager;
import com.deco2800.hcg.managers.TimeManager;
import com.deco2800.hcg.worlds.World;

public class TurretTest {
	
	private static GameManager gm;
	
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
	
	@BeforeClass
	public static void setup() {
		gm = GameManager.get();
		gm.setWorld(new World());
		
		setupTextures();
	}
	
	private static void setupTextures() {
		textures = new HashMap<>();
		textures.put(SunflowerTurret.class, "tree");
	}
	
	@Before
	public void setupTest() {
		sw = (StopwatchManager)gm.getManager(StopwatchManager.class);
		tm = (TimeManager)gm.getManager(TimeManager.class);
		corpse = new BasicCorpse(CORPSE_X, CORPSE_Y, 0, "tree");
		gm.getWorld().addEntity(corpse);
		enemy = new Squirrel(ENEMY_X, ENEMY_Y, 0, 0);
		turret = new SunflowerTurret(corpse);
		gm.getWorld().addEntity(enemy);
	}
	
	@Test(timeout=1000)
	public void testSunflowerTurretShoot() {		
		
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
		
		while(bullet.getPosX() < ENEMY_X || bullet.getPosY() < ENEMY_Y ) {
			bullet.onTick(0);
		}
		assertTrue(bullet.getPosX() == ENEMY_X && bullet.getPosY() == ENEMY_Y);
	}
	
	@Test(timeout=1000)
	public void testSunflowerTurretAmmo() {
		for(int i = 0; i < 11; i++) {
			assertTrue(gm.getWorld().containsEntity(corpse));
			turret.update(sw, 0);
		}
		assertFalse(gm.getWorld().containsEntity(corpse));
	}
	
	@Test
	public void checkTextures() {
		assertEquals(corpse.getTexture(), textures.get(turret.getClass()));
	}
	
	
}
