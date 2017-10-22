package com.deco2800.hcg.entities.bullets;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Harmable;
import com.deco2800.hcg.util.Effect;


/**
 * Ice Bullet
 * Deals no damage but freezes the target on the spot for x seconds
 *
 * ZA WARUDO
 * 
 * @author Yuki Nakazawa
 *
 */
public class IceBullet extends Bullet {

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
	public IceBullet(float posX, float posY, float posZ, float xd, float yd,
				  AbstractEntity user, int hitCount, float speed) {
		super(posX, posY, posZ, xd, yd, posZ,
				user, hitCount, speed);
		this.setTexture("battle_seed_blue");
		this.bulletType = BulletType.ICE;
	}
	
	/**
	 * Creates a new iceBullet moving towards the specified location
	 * 
	 * @param posX
	 * 			the starting x position
	 * @param posY
	 * 			the starting y position
	 * @param posZ
	 * 			the starting z position
	 * @param newX
	 * 			the target x position
	 * @param newY
	 * 			the target y position
	 * @param newZ
	 * 			the target z position
	 * @param user
	 * 			the entity who shot this bullet
	 */
	public IceBullet(float posX, float posY, float posZ, float newX, float newY, float newZ,
					 AbstractEntity user, float speed) {
		super(posX, posY, posZ, newX, newY, newZ, user, 1, speed);
		this.setTexture("battle_seed_blue");
		this.bulletType = BulletType.ICE;
	}
	
	/**
	 * Creates a new icebullet moving towards a specified location, with given x y and z length
	 * 
	 * @param posX
	 * 			the starting x position
	 * @param posY
	 * 			the starting y position
	 * @param posZ
	 * 			the starting z position
	 * @param newX
	 * 			the target x position
	 * @param newY
	 * 			the target y position
	 * @param newZ
	 * 			the target z position
	 * @param xLength
	 * 			the x size of the object
	 * @param yLength
	 * 			the y size of the object
	 * @param zLength
	 * 			the z size of the object
	 * @param user
	 * 			the entity who shot this bullet
	 * @param hitCount
	 * 			the number of entities this object can hit before being destroyed
	 */
	public IceBullet(float posX, float posY, float posZ, float newX, float newY, float newZ, float xLength,
			float yLength, float zLength, AbstractEntity user, int hitCount, float speed) {
		super(posX, posY, posZ, newX, newY, newZ, xLength, yLength, zLength, user, hitCount, speed);
		this.setTexture("battle_seed_blue");
		this.bulletType = BulletType.ICE;
	}
	
	@Override
	protected void applyEffect(Harmable target) {
		target.giveEffect(new Effect("Ice", 1, 0, 0, 0, 100, 0, user));
	}
}
