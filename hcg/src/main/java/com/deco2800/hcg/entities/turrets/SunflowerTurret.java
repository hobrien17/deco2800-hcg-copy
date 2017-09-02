package com.deco2800.hcg.entities.turrets;

import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Bullet;
import com.deco2800.hcg.entities.Enemy;
import com.deco2800.hcg.entities.garden_entities.plants.Pot;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.StopwatchManager;
import com.deco2800.hcg.util.WorldUtil;

public class SunflowerTurret extends AbstractTurret implements Observer {
	
    static final Logger LOGGER = LoggerFactory.getLogger(GameManager.class);
    
    private int range;

	public SunflowerTurret() {
		super("Sunflower");
		this.setTexture("tree");
		StopwatchManager manager = (StopwatchManager)GameManager.get().getManager(StopwatchManager.class);
        manager.addObserver(this);
		range = 5;
	}

	@Override
	public void update(Observable o, Object arg) {
		Optional<AbstractEntity> closest = WorldUtil.closestEntityToPosition(this.getPosX(), this.getPosY(), range, 
				Enemy.class);
		if (closest.isPresent()) {
			Enemy enemy = (Enemy)closest.get();
			Bullet bullet = new Bullet(this.getPosX(), this.getPosY(), this.getPosZ(), enemy.getPosX(), 
					enemy.getPosY(), enemy.getPosZ(), this);
			GameManager.get().getWorld().addEntity(bullet);
		}
		
	}
	
	

}
