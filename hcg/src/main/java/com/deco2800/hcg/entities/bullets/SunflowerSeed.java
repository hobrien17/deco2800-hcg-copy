package com.deco2800.hcg.entities.bullets;

import com.deco2800.hcg.util.Effect;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Harmable;

/**
 * An extension of the bullet class which will check for collision with
 * a corpse and spawn a sunflower turret
 */
public class SunflowerSeed extends Bullet {

	public SunflowerSeed(float posX, float posY, float posZ, float xd, float yd,
				  AbstractEntity user, int hitCount) {
		super(posX, posY, posZ, xd, yd, posZ,
				user, hitCount);
		this.bulletType = BulletType.SUNFLOWER;
	}

	@Override
	protected void applyEffect(Harmable target) {
		target.giveEffect(new Effect("Shot", 1, 1, 0, 0, 1, 0));
	}
}

