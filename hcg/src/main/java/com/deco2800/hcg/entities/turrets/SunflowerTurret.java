package com.deco2800.hcg.entities.turrets;

import java.util.Observable;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.bullets.Bullet;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.entities.enemyentities.Enemy;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.types.Weathers;
import com.deco2800.hcg.util.WorldUtil;

/**
 * Basic sunflower turret
 * Shoots seeds at enemies in a limited range, and destroys itself when out of ammo
 * 
 */
public class SunflowerTurret extends AbstractTurret {

	static final Logger LOGGER = LoggerFactory.getLogger(GameManager.class);

	private int ammo;
	Random rand;
	private static final int RANGE = 5;
	private static final int NORMAL_AMMO = 10;
	private static final int REDUCED_AMMO = 5;
	private static final int INCREASED_AMMO = 60;

	/**
	 * Creates a new sunflower turret inside the given corpse
	 * 
	 * @param master
	 * 			the corpse to plant the turret inside
	 */
	public SunflowerTurret(Corpse master) {
		super(master, "Sunflower");
		rand = new Random();
		if(GameManager.get().getWorld().getWeatherType().equals(Weathers.WIND)) {
			System.out.println("wind");
			ammo = REDUCED_AMMO;
		} else if(GameManager.get().getWorld().getWeatherType().equals(Weathers.STORM)) {
			ammo = INCREASED_AMMO;
		} else {
			ammo = NORMAL_AMMO;
		}
	}

	/**
	 * Updates the turret, shooting at any nearby enemies and destroying itself if out of ammo
	 * 
	 * @param o
	 * 			the Observable object calling the update method (should be an instance of StopwatchManager)
	 * @param arg
	 * 			the argument passed by the Observable object (should be the stopwatch's current time)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (ammo > 0) {
			Optional<AbstractEntity> closest = WorldUtil.closestEntityToPosition(master.getPosX(), master.getPosY(),
					RANGE, Enemy.class);
			if (closest.isPresent()) {
				Enemy enemy = (Enemy) closest.get();
				Bullet bullet = new Bullet(master.getPosX(), master.getPosY(), master.getPosZ(), enemy.getPosX(),
						enemy.getPosY(), enemy.getPosZ(), master, 1);
				GameManager.get().getWorld().addEntity(bullet);
				ammo--;
			} else if(GameManager.get().getWorld().getWeatherType().equals(Weathers.STORM)) {
				double angle = rand.nextInt(360)*Math.PI/180;
				float goalX = master.getPosX() + (float)(RANGE*Math.cos(angle));
				float goalY = master.getPosY() + (float)(RANGE*Math.sin(angle));
				Bullet bullet = new Bullet(master.getPosX(), master.getPosY(), master.getPosZ(),
						goalX, goalY, 0, master, 1);
				GameManager.get().getWorld().addEntity(bullet);
				ammo--;
			}
		} else {
			GameManager.get().getWorld().removeEntity(master);
		}
	}

	@Override
	public String getThisTexture() {
		// TODO Auto-generated method stub
		return "sunflower_corpse";
	}

}

