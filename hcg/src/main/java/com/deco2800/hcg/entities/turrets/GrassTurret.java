package com.deco2800.hcg.entities.turrets;

import java.util.Observable;

import com.badlogic.gdx.graphics.Color;
import com.deco2800.hcg.entities.bullets.GrassTurretBullet;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.types.Weathers;

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
	private int angleChange;
	private static final int GROW = 5;
	private static final int DISTANCE = 5;
	private static final int NORMAL_ANGLE_CHANGE = 5;
	private static final int INCREASED_ANGLE_CHANGE = 20;
	
	/**
	 * Creates a new grass turret inside the given corpse
	 * 
	 * @param master
	 * 			the corpse to plant the turret inside
	 */
	public GrassTurret(Corpse master) {
		super(master, "Grass");
		if(GameManager.get().getWorld().getWeatherType().equals(Weathers.WIND)) {
			seconds = GROW - 1;
		} else {
			seconds = 0;
		}
		if(GameManager.get().getWorld().getWeatherType().equals(Weathers.STORM)) {
			angleChange = INCREASED_ANGLE_CHANGE;
		} else {
			angleChange = NORMAL_ANGLE_CHANGE;
		}
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
			for(int angle = 0; angle < 360; angle += angleChange) {
				double radAngle = angle*Math.PI/180;
				double newX = master.getPosX() + DISTANCE*Math.cos(radAngle);
				double newY = master.getPosY() + DISTANCE*Math.sin(radAngle);
				GrassTurretBullet bullet = new GrassTurretBullet(master.getPosX(), master.getPosY(), master.getPosZ(), 
						(float)newX, (float)newY, 0, master, 0.5f, 1000);
				GameManager.get().getWorld().addEntity(bullet);
			}
			GameManager.get().getWorld().removeEntity(master);
		}
	}

	@Override
	public String getThisTexture() {
		return "grass_corpse";
	}

	@Override
	public int getGlowStrength() {
		return 0;
	}

	@Override
	public Color getGlowColor() {
		return Color.GREEN;
	}

}
