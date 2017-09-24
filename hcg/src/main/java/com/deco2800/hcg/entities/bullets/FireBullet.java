package com.deco2800.hcg.entities.bullets;

import java.util.List;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Harmable;
import com.deco2800.hcg.entities.enemyentities.Enemy;
import com.deco2800.hcg.util.Effect;
import com.deco2800.hcg.util.WorldUtil;

/**
 * Fire bullet
 * Kills the enemy it hits, and sets nearby enemies on fire
 * Since effects are not fully implemented, this bullet doesn't work at the moment
 * 
 * @author Henry O'Brien
 *
 */
public class FireBullet extends Bullet {
	
	/**
	 * Creates a new firebullet moving towards the specified location
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
	public FireBullet(float posX, float posY, float posZ, float newX, float newY, float newZ, AbstractEntity user) {
		super(posX, posY, posZ, newX, newY, newZ, user, 1);
		this.setTexture("battle_seed_red");
	}
	
	/**
	 * Creates a new firebullet moving towards a specified location, with given x y and z length
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
	public FireBullet(float posX, float posY, float posZ, float newX, float newY, float newZ, float xLength, 
			float yLength, float zLength, AbstractEntity user, int hitCount) {
		super(posX, posY, posZ, newX, newY, newZ, xLength, yLength, zLength, user, hitCount);
		this.setTexture("battle_seed_red");
	}
	
	@Override
	protected void applyEffect(Harmable target) {
		target.giveEffect(new Effect("Shot", 1, 2, 0, 0, 1, 0));
		AbstractEntity entity = (AbstractEntity)target;
		List<AbstractEntity> closest = WorldUtil.allEntitiesToPosition(entity.getPosX(), entity.getPosY(), 1, Enemy.class);
		for(AbstractEntity close : closest) {
			((Enemy)close).giveEffect(new Effect("Fire", 1, 0, 0, 0, 5, 0));
		}
	}
}
