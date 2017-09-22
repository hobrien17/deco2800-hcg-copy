package com.deco2800.hcg.entities.turrets;

import java.util.Observable;
import com.deco2800.hcg.entities.bullets.Fireball;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.managers.GameManager;

/**
 * Fire turret 
 * Fires four massive fireballs in different directions
 *
 * @author Henry O'Brien
 *
 */
public class FireTurret extends AbstractTurret {

	private int seconds;
	private static final int RANGE = 10;

	/**
	 * Creates a new fire turret in the given corpse
	 * 
	 * @param master
	 * 			the corpse to plant the turret inside
	 */
	public FireTurret(Corpse master) {
		super(master, "Fire");
		seconds = 0;

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
		
		float[][] pos = { { master.getPosX() + RANGE, master.getPosY() + RANGE },
				{ master.getPosX() + RANGE, Math.max(0, master.getPosY() - RANGE) },
				{ Math.max(0, master.getPosX() - RANGE), master.getPosY() + RANGE },
				{ Math.max(0, master.getPosX() - RANGE), Math.max(0, master.getPosY() - RANGE) } };
		if (seconds == 5) {
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
