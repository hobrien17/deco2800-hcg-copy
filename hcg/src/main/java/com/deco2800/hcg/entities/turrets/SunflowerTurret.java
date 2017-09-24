package com.deco2800.hcg.entities.turrets;

import java.util.Observable;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.bullets.Bullet;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.entities.enemyentities.Enemy;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.util.WorldUtil;

/**
 * Basic sunflower turret
 * Shoots seeds at enemies in a limited range, and destroys itself when out of ammo
 * 
 */
public class SunflowerTurret extends AbstractTurret {

	static final Logger LOGGER = LoggerFactory.getLogger(GameManager.class);

	private static final int RANGE = 5;
	protected int ammo;

	/**
	 * Creates a new sunflower turret inside the given corpse
	 * 
	 * @param master
	 * 			the corpse to plant the turret inside
	 */
	public SunflowerTurret(Corpse master) {
		super(master, "Sunflower");
		ammo = 10;
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

