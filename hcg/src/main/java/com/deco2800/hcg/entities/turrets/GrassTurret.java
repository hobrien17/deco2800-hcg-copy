package com.deco2800.hcg.entities.turrets;

import java.util.List;
import java.util.Observable;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.corpse_entities.BasicCorpse;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.entities.enemyentities.Enemy;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.util.WorldUtil;

/**
 * Grass Turret
 * Has no current effect (currently destroys itself after a few seconds)
 * To be implemented later
 * 
 * @author Henry O'Brien
 *
 */
public class GrassTurret extends AbstractTurret {

	private int seconds;
	private final static int GROW = 5;
	
	/**
	 * Creates a new grass turret inside the given corpse
	 * 
	 * @param master
	 * 			the corpse to plant the turret inside
	 */
	public GrassTurret(Corpse master) {
		super(master, "Grass");
		seconds = 0;
	}

	/**
	 * Updates the turret, destroying itself if the turret's life time has elapsed
	 * 
	 * @param o
	 * 			the Observable object calling the update method (should be an instance of StopwatchManager)
	 * @param arg
	 * 			the argument passed by the Observable object (should be the stopwatch's current time)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(++seconds == GROW) {
			GameManager.get().getWorld().removeEntity(master);
		}
	}

	@Override
	public String getThisTexture() {
		return "grass_corpse";
	}

}
