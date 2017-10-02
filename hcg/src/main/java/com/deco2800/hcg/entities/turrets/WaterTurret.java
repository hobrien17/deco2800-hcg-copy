package com.deco2800.hcg.entities.turrets;

import java.util.List;
import java.util.Observable;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.util.WorldUtil;

/**
 * A water turret which heals any nearby players
 * 
 * @author Henry O'Brien
 *
 */
public class WaterTurret extends AbstractTurret {
	
	private int seconds;
	private static final int DIE = 30;
	private static final int RANGE = 3;

	/**
	 * Creates a new water turret inside the given corpse
	 * 
	 * @param master
	 * 			the corpse to plant the turret inside
	 */
	public WaterTurret(Corpse master) {
		super(master, "Lily");
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
		if(++seconds == DIE) {
			GameManager.get().getWorld().removeEntity(master);
			o.deleteObserver(this);
			return;
		}
		List<AbstractEntity> players = WorldUtil.allEntitiesToPosition(master.getPosX(), master.getPosY(), 
				RANGE, Player.class);
		for (AbstractEntity entity : players) {
			Player player = (Player)entity;
			player.takeDamage(-1);
		}
		

	}

	@Override
	public String getThisTexture() {
		// TODO Auto-generated method stub
		return "water_corpse";
	}

}
