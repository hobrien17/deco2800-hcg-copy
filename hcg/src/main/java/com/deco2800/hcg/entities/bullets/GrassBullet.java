package com.deco2800.hcg.entities.bullets;

import com.deco2800.hcg.entities.terrain_entities.DestructableTree;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.util.WorldUtil;
import java.util.Optional;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Harmable;
import com.deco2800.hcg.util.Effect;

/**
 * Grass bullet class
 * This bullet acts like a normal bullet, but if it doesn't hit any enemies it will spawn a DestructableTree
 *    in its spot
 *
 * @author Henry O'Brien
 *
 */
public class GrassBullet extends Bullet {

	private float posX;
	private float posY;
	private float posZ;
	private float xd;
	private float yd;
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
	public GrassBullet(float posX, float posY, float posZ, float xd, float yd,
					  AbstractEntity user, int hitCount) {
		super(posX, posY, posZ, xd, yd, posZ,
				user, hitCount);
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		this.xd = xd;
		this.yd = yd;
		this.user = user;
		this.setTexture("battle_seed_green");
		this.bulletType = BulletType.GRASS;
	}

	/**
	 * Creates a new grass bullet moving towards the given co-ordinates
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
	public GrassBullet(float posX, float posY, float posZ, float newX, float newY, float newZ, AbstractEntity user) {
		super(posX, posY, posZ, newX, newY, newZ, user, 1);
		this.setTexture("battle_seed_green");
		this.bulletType = BulletType.GRASS;
	}

	@Override
	protected void specialAbility() {
		Bullet bulletLeft = new Bullet(this.getPosX(), this.getPosY(), this.getPosZ(), this.xd, this.yd, this.user, 1);
		Bullet bulletRight = new Bullet(this.getPosX(), this.getPosY(), this.getPosZ(), this.xd, this.yd, this.user, 1);
		bulletLeft.updateAngle(-80);
		bulletRight.updateAngle(80);
		GameManager.get().getWorld().addEntity(bulletLeft);
		GameManager.get().getWorld().addEntity(bulletRight);
	}

	protected void applyEffect(Harmable target) {
		// Set target to be the enemy whose collision got detected and
		// give it an effect
		target.giveEffect(new Effect("Shot", 1, 5000, 1, 0, 1, 0, user));
	}
}

