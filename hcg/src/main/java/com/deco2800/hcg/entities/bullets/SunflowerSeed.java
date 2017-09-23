package com.deco2800.hcg.entities.bullets;

import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.util.Box3D;
import com.deco2800.hcg.util.Effect;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.entities.enemyentities.Enemy;
import com.deco2800.hcg.entities.Player;

import java.util.List;

/**
 * An extension of the bullet class which will check for collision with
 * a corpse and spawn a sunflower turret
 */
public class SunflowerSeed extends Bullet {

	private int hitCount;
	private AbstractEntity user;

	public SunflowerSeed(float posX, float posY, float posZ, float xd, float yd,
				  AbstractEntity user, int hitCount) {
		super(posX, posY, posZ, getProj(xd, yd)[0], getProj(xd, yd)[1], posZ,
				user, hitCount);
		this.user = user;
		this.hitCount = hitCount;
	}

	@Override
	protected void entityHit() {
		Box3D pos = getBox3D();
		pos.setX(getPosX());
		pos.setY(getPosY());
		List<AbstractEntity> entities = GameManager.get().getWorld()
				.getEntities();
		for (AbstractEntity entity : entities) {
			if (this.collidesWith(entity)) {
				// Collision with enemy
				if (entity instanceof Enemy
						&& (user instanceof Player || user instanceof Corpse)) {
					Enemy target = (Enemy) entity;
					applyEffect(target);
					hitCount--;
				}
				// Collision with corpse
				if (entity instanceof Corpse && user instanceof Player) {
					// TODO add sunflower turret here
					hitCount = 0;
				}
				if (hitCount == 0) {
					GameManager.get().getWorld().removeEntity(this);
					break;
				}
			}
		}

	}

	@Override
	protected void applyEffect(Enemy target) {
		target.giveEffect(new Effect("Shot", 1, 1, 0, 0, 1, 0));
	}
}

