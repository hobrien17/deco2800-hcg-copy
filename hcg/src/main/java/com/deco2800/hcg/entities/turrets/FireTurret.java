package com.deco2800.hcg.entities.turrets;

import java.util.Observable;

import com.deco2800.hcg.entities.Bullet;
import com.deco2800.hcg.entities.Fireball;
import com.deco2800.hcg.managers.GameManager;

/**
 * Fire turret Fires four massive fireballs in different directions
 * 
 * @author Henry
 *
 */
public class FireTurret extends AbstractTurret {

	private int seconds;
	private final static int RANGE = 15;

	public FireTurret() {
		super("Fire");
		this.setTexture("tree");
		seconds = 0;
	}

	@Override
	public void update(Observable o, Object arg) {
		float[][] pos = { { this.getPosX() + RANGE, this.getPosY() }, 
				{ this.getPosX(), this.getPosY() + RANGE },
				{ Math.max(0, this.getPosX() - RANGE), this.getPosY() }, 
				{ this.getPosX(), Math.max(0, this.getPosY() - RANGE) } };
		if (seconds == 5) {
			GameManager.get().getWorld().removeEntity(this);
		} else if(seconds > 0 && seconds < 5) {
			Fireball fire = new Fireball(this.getPosX(), this.getPosY(), this.getPosZ(), pos[seconds-1][0], 
					pos[seconds-1][1], this.getPosZ(), this);
			GameManager.get().getWorld().addEntity(fire);
		}
		seconds++;
	}

}
