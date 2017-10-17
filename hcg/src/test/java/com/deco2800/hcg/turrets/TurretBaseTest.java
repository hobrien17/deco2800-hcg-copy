package com.deco2800.hcg.turrets;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import com.deco2800.hcg.BaseTest;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.bullets.Bullet;
import com.deco2800.hcg.entities.corpse_entities.BasicCorpse;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.entities.enemyentities.Enemy;
import com.deco2800.hcg.entities.enemyentities.Squirrel;
import com.deco2800.hcg.entities.turrets.AbstractTurret;
import com.deco2800.hcg.entities.turrets.Explosion;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.StopwatchManager;
import com.deco2800.hcg.types.Weathers;
import com.deco2800.hcg.worlds.World;

public class TurretBaseTest extends BaseTest {
	
	static StopwatchManager sw;
	static GameManager gm;
	static World world;
	
	AbstractTurret turret;
	Corpse corpse;
	Enemy enemy;
	
	@BeforeClass
	public static void setupWorld() {
		gm = GameManager.get();
		world = new World();
		gm.setWorld(world);
		world.setWeather(Weathers.NONE);
		sw = (StopwatchManager)gm.getManager(StopwatchManager.class);
	}
	
	@Before
	public void setupTest() {
		corpse = new BasicCorpse(5, 5, 0);
		world.addEntity(corpse);
		
		enemy = new Squirrel(7, 5, 0, 0);
		world.addEntity(enemy);
	}
	
	@After
	public void resetTest() {
		for(AbstractEntity entity : world.getEntities()) {
			if(entity instanceof Enemy) {
				world.removeEntity(entity);
			}
			if(entity instanceof Bullet) {
				world.removeEntity(entity);
			}
			if(entity instanceof Corpse) {
				world.removeEntity(entity);
			}
			if(entity instanceof Explosion) {
				world.removeEntity(entity);
			}
			if(entity instanceof Player) {
				world.removeEntity(entity);
			}
		}
		world.setWeather(Weathers.NONE);
	}
}
