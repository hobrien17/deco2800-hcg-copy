package com.deco2800.hcg.entities.bullets;

import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.util.WorldUtil;
import java.util.Optional;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Harmable;
import com.deco2800.hcg.entities.enemyentities.Enemy;
import com.deco2800.hcg.util.Effect;

/**
 * Homing bullet class
 * This bullet will lock on to the closest enemy, 20 ticks after it is fired, and change its trajectory
 * to fly towards its target.
 *
 * @author Yuki Nakazawa
 *
 */
public class HomingBullet extends Bullet {

	private AbstractEntity user;

	/**
	 * Creates a new Bullet at the given position with the given direction.
	 *
	 * @param posX
	 *            the x position of the bullet
	 * @param posY
	 *            the y position of the bullet
	 * @param posZ
	 *            the z position of the bullet
	 * @param xd
	 *            the y direction for the bullet
	 * @param yd
	 *            the x direction for the bullet
	 * @param user
	 *            the entity using the bullet
	 * @param hitCount
	 *            the total number of enemies that can be hit
	 */
	public HomingBullet(float posX, float posY, float posZ, float xd, float yd,
					  AbstractEntity user, int hitCount) {
		super(posX, posY, posZ, xd, yd, posZ,
				user, hitCount);
		this.user = user;
		this.setTexture("battle_seed");
		this.bulletType = BulletType.HOMING;
	}

	/**
	 * Creates a new homing bullet moving towards the given co-ordinates
	 * 
	 * @param posX
	 * 			the bullet's starting x position
	 * @param posY
	 * 			the bullet's starting y position
	 * @param posZ
	 * 			the bullet's starting z position
	 * @param newX
	 * 			the bullet's target x position
	 * @param newY
	 * 			the bullet's target y position
	 * @param newZ
	 * 			the bullet's target z position
	 * @param user
	 * 			the entity who shot the bullet
	 */
	public HomingBullet(float posX, float posY, float posZ, float newX, float newY, float newZ, AbstractEntity user) {
		super(posX, posY, posZ, newX, newY, newZ, user, 1);
		this.setTexture("battle_seed");
		this.bulletType = BulletType.HOMING;
	}

	/**
	 * Locates the closest enemy within the range of 10 and creates a new bullet aimed at
	 * that target. The original bullet is removed.
	 */
	@Override
	protected void specialAbility() {
		Optional<AbstractEntity> closest = WorldUtil.closestEntityToPosition(this.getPosX(),
				this.getPosY(), 10, Enemy.class);
		if (closest.isPresent()) {
			Enemy enemy = (Enemy) closest.get();
			Bullet bullet = new Bullet(this.getPosX(), this.getPosY(), this.getPosZ(),
					enemy.getPosX(), enemy.getPosY(), enemy.getPosZ(), user, 1);
			GameManager.get().getWorld().addEntity(bullet);
			GameManager.get().getWorld().removeEntity(this);
		}
	}

	protected void applyEffect(Harmable target) {
		// Set target to be the enemy whose collision got detected and
		// give it an effect
		target.giveEffect(new Effect("Shot", 1, 5000, 1, 0, 1, 0, user));
	}
}

