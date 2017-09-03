package com.deco2800.hcg.entities.turrets;

import java.util.Observable;

import com.deco2800.hcg.entities.Bullet;
import com.deco2800.hcg.entities.Fireball;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
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

	public FireTurret(Corpse master) {
		super(master, "Fire");
		seconds = 0;
	}

	@Override
	public void update(Observable o, Object arg) {
		float[][] pos = { { this.getCorpse().getPosX() + RANGE, this.getCorpse().getPosY() }, 
				{ this.getCorpse().getPosX(), this.getCorpse().getPosY() + RANGE },
				{ Math.max(0, this.getCorpse().getPosX() - RANGE), this.getCorpse().getPosY() }, 
				{ this.getCorpse().getPosX(), Math.max(0, this.getCorpse().getPosY() - RANGE) } };
		if (seconds == 5) {
			GameManager.get().getWorld().removeEntity(this.getCorpse());
		} else if(seconds > 0 && seconds < 5) {
			Fireball fire = new Fireball(this.getCorpse().getPosX(), this.getCorpse().getPosY(), 
					this.getCorpse().getPosZ(), pos[seconds-1][0], pos[seconds-1][1], this.getCorpse().getPosZ(), 
					this.getCorpse());
			GameManager.get().getWorld().addEntity(fire);
		}
		seconds++;
	}

	@Override
	public String getThisTexture() {
		return "tree";
	}

}
