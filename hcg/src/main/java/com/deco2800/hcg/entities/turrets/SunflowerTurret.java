package com.deco2800.hcg.entities.turrets;

import java.util.Observable;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.bullets.Bullet;
import com.deco2800.hcg.entities.enemy_entities.Enemy;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.util.WorldUtil;

/**
<<<<<<< HEAD
 * Basic sunflower turret Shoots standard seeds at any enemies in a limited
 * range
 * 
=======
 * Basic sunflower turret
 * Shoots standard seeds at any enemies in a limited range
 *
>>>>>>> origin/master
 * @author Henry O'Brien
 *
 */
public class SunflowerTurret extends AbstractTurret {

	static final Logger LOGGER = LoggerFactory.getLogger(GameManager.class);

	private static final int RANGE = 5;
	protected int ammo;

	public SunflowerTurret(Corpse master) {
		super(master, "Sunflower");
		ammo = 10;
	}

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
		return "tree";
	}

}

