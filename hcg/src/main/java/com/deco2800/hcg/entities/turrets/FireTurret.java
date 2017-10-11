package com.deco2800.hcg.entities.turrets;

import java.util.List;
import java.util.Observable;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.bullets.Fireball;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.entities.enemyentities.Enemy;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.types.Weathers;
import com.deco2800.hcg.util.WorldUtil;

/**
 * Fire turret 
 * Fires four massive fireballs in different directions
 *
 * @author Henry O'Brien
 *
 */
public class FireTurret extends AbstractTurret {

	private int seconds;
	private int range;
	private static final int NORMAL_RANGE = 10;
	private static final int REDUCED_RANGE = 7;
	private static final int EXP_RANGE = 2;

	/**
	 * Creates a new fire turret in the given corpse
	 * 
	 * @param master
	 * 			the corpse to plant the turret inside
	 */
	public FireTurret(Corpse master) {
		super(master, "Inferno");
		seconds = 0;
		if(GameManager.get().getWorld().getWeatherType().equals(Weathers.RAIN)) {
			range = REDUCED_RANGE;
		} else {
			range = NORMAL_RANGE;
		}
	}

	/**
	 * Updates the turret, shooting the next fireball in the turret's sequence or destroying itself
	 * 	if the sequence is complete
	 * 
	 * @param o
	 * 			the Observable object calling the update method (should be an instance of StopwatchManager)
	 * @param arg
	 * 			the argument passed by the Observable object (should be the stopwatch's current time)
	 */
	@Override
	public void update(Observable o, Object arg) {
		
		float[][] pos = { { master.getPosX() + range, master.getPosY() + range },
				{ master.getPosX() + range, Math.max(0, master.getPosY() - range) },
				{ Math.max(0, master.getPosX() - range), master.getPosY() + range },
				{ Math.max(0, master.getPosX() - range), Math.max(0, master.getPosY() - range) } };
		if (seconds == 5) {
			if(GameManager.get().getWorld().getWeatherType().equals(Weathers.SANDSTORM)) {
				Explosion exp = new Explosion(master.getPosX()+1, master.getPosY(), 0, 0.25f);
				GameManager.get().getWorld().addEntity(exp);
				List<AbstractEntity> entities = WorldUtil.allEntitiesToPosition(master.getPosX(), 
						master.getPosY(), EXP_RANGE, Enemy.class);
				for(AbstractEntity entity : entities) {
					GameManager.get().getWorld().removeEntity(entity);
				}
			}
			GameManager.get().getWorld().removeEntity(master);
		} else if (seconds > 0 && seconds < 5) {
			Fireball fire = new Fireball(master.getPosX(), master.getPosY(), master.getPosZ(), pos[seconds - 1][0],
					pos[seconds - 1][1], master.getPosZ(), master);
			GameManager.get().getWorld().addEntity(fire);
		}
		seconds++;
	}

	@Override
	public String getThisTexture() {
		return "fire_corpse";
	}

}
