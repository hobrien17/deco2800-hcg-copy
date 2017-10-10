package com.deco2800.hcg.entities.turrets;

import java.util.List;
import java.util.Observable;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.types.Weathers;
import com.deco2800.hcg.util.WorldUtil;

/**
 * A water turret which heals any nearby players
 * 
 * @author Henry O'Brien
 *
 */
public class WaterTurret extends AbstractTurret {
	
	private int seconds;
	private int die;
	private int regen;
	private static final int NORMAL_DIE = 30;
	private static final int REDUCED_DIE = 20;
	private static final int NORMAL_REGEN = 1;
	private static final int INCREASED_REGEN = 2;
	private static final int RANGE = 3;

	/**
	 * Creates a new water turret inside the given corpse
	 * 
	 * @param master
	 * 			the corpse to plant the turret inside
	 */
	public WaterTurret(Corpse master) {
		super(master, "Lily");
		seconds = 0;
		
		if(GameManager.get().getWorld().getWeatherType().equals(Weathers.RAIN)) {
			die = REDUCED_DIE;
			regen = INCREASED_REGEN;
		} else if(GameManager.get().getWorld().getWeatherType().equals(Weathers.DROUGHT)) {
			die = REDUCED_DIE;
			regen = NORMAL_REGEN;
		} else {
			die = NORMAL_DIE;
			regen = NORMAL_REGEN;
		}
	}

	/**
	 * Updates the turret, healing any players within range, or destroying itself if the turret's
	 * 	life time has elapsed
	 * 
	 * @param o
	 * 			the Observable object calling the update method (should be an instance of StopwatchManager)
	 * @param arg
	 * 			the argument passed by the Observable object (should be the stopwatch's current time)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(++seconds == die) {
			GameManager.get().getWorld().removeEntity(master);
			o.deleteObserver(this);
			return;
		}
		List<AbstractEntity> players = WorldUtil.allEntitiesToPosition(master.getPosX(), master.getPosY(), 
				RANGE, Player.class);
		for (AbstractEntity entity : players) {
			Player player = (Player)entity;
			player.takeDamage(regen * -1);
		}
		

	}

	@Override
	public String getThisTexture() {
		// TODO Auto-generated method stub
		return "water_corpse";
	}

}
