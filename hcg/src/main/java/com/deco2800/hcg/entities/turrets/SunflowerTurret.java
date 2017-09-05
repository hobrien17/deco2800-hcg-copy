package com.deco2800.hcg.entities.turrets;

import java.util.Observable;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Bullet;
import com.deco2800.hcg.entities.Enemy;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.util.WorldUtil;

/**
 * Basic sunflower turret
 * Shoots standard seeds at any enemies in a limited range
 * 
 * @author Henry O'Brien
 *
 */
public class SunflowerTurret extends AbstractTurret {
	
    static final Logger LOGGER = LoggerFactory.getLogger(GameManager.class);
    
    private static final int RANGE = 5;

	public SunflowerTurret(Corpse master) {
		super(master, "Sunflower");
	}

	@Override
	public void update(Observable o, Object arg) {
		Optional<AbstractEntity> closest = WorldUtil.closestEntityToPosition(master.getPosX(), 
				master.getPosY(), RANGE, Enemy.class);
		if (closest.isPresent()) {
			Enemy enemy = (Enemy)closest.get();
			Bullet bullet = new Bullet(master.getPosX(), master.getPosY(), 
					master.getPosZ(), enemy.getPosX(), enemy.getPosY(), enemy.getPosZ(), master);
			GameManager.get().getWorld().addEntity(bullet);
		}
		
	}

	@Override
	public String getThisTexture() {
		// TODO Auto-generated method stub
		return "tree";
	}
	
	

}
