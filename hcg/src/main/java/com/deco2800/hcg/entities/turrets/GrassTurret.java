package com.deco2800.hcg.entities.turrets;

import java.util.Observable;
import com.deco2800.hcg.entities.bullets.GrassBullet;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.managers.GameManager;

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
	private static final int GROW = 5;
	private static final int DISTANCE = 5;
	
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
			for(int angle = 0; angle < 360; angle += 5) {
				double newX = master.getPosX() + DISTANCE*Math.cos(angle);
				double newY = master.getPosY() + DISTANCE*Math.sin(angle);
				GrassBullet bullet = new GrassBullet(master.getPosX(), master.getPosY(), master.getPosZ(), 
						(float)newX, (float)newY, 0, master);
				GameManager.get().getWorld().addEntity(bullet);
			}
			GameManager.get().getWorld().removeEntity(master);
		}
	}

	@Override
	public String getThisTexture() {
		return "grass_corpse";
	}

}
