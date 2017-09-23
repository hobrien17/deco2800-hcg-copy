package com.deco2800.hcg.entities.turrets;

import java.util.List;
import java.util.Observable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.deco2800.hcg.actors.ParticleEffectActor;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.enemy_entities.Enemy;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.WeatherManager;
import com.deco2800.hcg.types.Weathers;
import com.deco2800.hcg.util.WorldUtil;

/**
 * Ice turret
 * Freezes enemies in a short range
 * Slows down enemies in a longer range
 * 
 * @author Henry O'Brien
 *
 */
public class IceTurret extends AbstractTurret {

	private int seconds;
	private List<AbstractEntity> near;
	private List<AbstractEntity> far;
	private static final int BLOW = 5;
	private static final int RESET = BLOW + 10;
	private static final int CLOSE_RANGE = 5;
	private static final int FAR_RANGE = 30;
	
	/**
	 * Creates a new ice turret inside the given corpse
	 * 
	 * @param master
	 * 			the corpse to plant the turret inside
	 */
	public IceTurret(Corpse master) {
		super(master, "Ice");
		seconds = 0;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(++seconds == BLOW) {
			near = WorldUtil.allEntitiesToPosition(master.getPosX(), 
					master.getPosY(), CLOSE_RANGE, Enemy.class);
			far = WorldUtil.allEntitiesToPosition(master.getPosX(), 
					master.getPosY(), FAR_RANGE, Enemy.class);
			for(AbstractEntity entity : near) {
				Enemy enemy = (Enemy)entity;
				enemy.setSpeed(0);
			}
			for(AbstractEntity entity : far) {
				if(!near.contains(entity)) {
					Enemy enemy = (Enemy)entity;
					enemy.changeSpeed(0.5f);
				}
			}
		}
		if(seconds == RESET) {
			for(AbstractEntity entity : near) {
				Enemy enemy = (Enemy)entity;
				enemy.resetSpeed();
			}
			for(AbstractEntity entity : far) {
				Enemy enemy = (Enemy)entity;
				enemy.resetSpeed();
			}
			GameManager.get().getWorld().removeEntity(master);
		}
		if(seconds == BLOW - 1) {
			master.setTexture("ice_corpse_03");
		} else if(seconds == BLOW - 2) {
			master.setTexture("ice_corpse_02");
		}
	}

	@Override
	public String getThisTexture() {
		return "ice_corpse_01";
	}

}
