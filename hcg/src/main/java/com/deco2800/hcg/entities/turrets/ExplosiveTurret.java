package com.deco2800.hcg.entities.turrets;

import java.util.List;
import java.util.Observable;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.entities.enemyentities.Enemy;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.types.Weathers;
import com.deco2800.hcg.util.WorldUtil;

/**
 * Explosive Turret
 * After a few seconds, destroys itself and all enemies in a limited range
 * 
 * @author Henry O'Brien
 *
 */
public class ExplosiveTurret extends AbstractTurret {

	private int seconds;
	private int range;
	private int blow;
	private float expSize;
	private static final int NORMAL_BLOW = 5;
	private static final int INCREASED_BLOW = 10;
	private static final int NORMAL_RANGE = 5;
	private static final int INCREASED_RANGE = 10;
	private static final float NORMAL_SIZE = 0.4f;
	private static final float INCREASED_SIZE = 0.5f;
		
	/**
	 * Creates a new explosive turret in the given corpse
	 * 
	 * @param master
	 * 			the corpse to plant this turret inside
	 */
	public ExplosiveTurret(Corpse master) {
		super(master, "Cactus");
		seconds = 0;
		
		if(GameManager.get().getWorld().getWeatherType().equals(Weathers.DROUGHT)) {
			range = INCREASED_RANGE;
			expSize = INCREASED_SIZE;
			blow = NORMAL_BLOW;
		} else if(GameManager.get().getWorld().getWeatherType().equals(Weathers.SNOW)) {
			range = NORMAL_RANGE;
			expSize = NORMAL_SIZE;
			blow = INCREASED_BLOW;
		} else {
			range = NORMAL_RANGE;
			expSize = NORMAL_SIZE;
			blow = NORMAL_BLOW;
		}
	}
	
	/**
	 * Updates the turret, destroying itself and all enemies around it if the blow time has elapsed
	 * 
	 * @param o
	 * 			the Observable object calling the update method (should be an instance of StopwatchManager)
	 * @param arg
	 * 			the argument passed by the Observable object (should be the stopwatch's current time)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(++seconds == blow) {
			Explosion exp = new Explosion(master.getPosX()+1, master.getPosY(), 0, expSize);
			GameManager.get().getWorld().addEntity(exp);
			List<AbstractEntity> entities = WorldUtil.allEntitiesToPosition(master.getPosX(), 
					master.getPosY(), range, Enemy.class);
			for(AbstractEntity entity : entities) {
				GameManager.get().getWorld().removeEntity(entity);
			}
			GameManager.get().getWorld().removeEntity(master);
		} else if(seconds == blow - 1) {
			master.setTexture("cactus_corpse_03");
		} else if(seconds == blow - 2) {
			master.setTexture("cactus_corpse_02");
		}
	}
	
	@Override
	public String getThisTexture() {
		return "cactus_corpse_01";
	}

}
