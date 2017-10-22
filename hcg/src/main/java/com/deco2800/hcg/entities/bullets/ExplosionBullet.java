package com.deco2800.hcg.entities.bullets;

import java.util.ArrayList;
import java.util.List;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Harmable;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.enemyentities.Enemy;
import com.deco2800.hcg.entities.turrets.Explosion;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.util.Effect;
import com.deco2800.hcg.util.WorldUtil;

/**
 * Explosion bullet
 * Deals a set amount of damage to all enemies in a specific radius
 * 
 * @author Yuki Nakazawa
 *
 */
public class ExplosionBullet extends Bullet {

    private Explosion explosion;
    
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
	public ExplosionBullet(float posX, float posY, float posZ, float xd, float yd,
					 AbstractEntity user, int hitCount, float speed) {
		super(posX, posY, posZ, xd, yd, posZ,
				user, hitCount, speed);
		this.setTexture("battle_seed_grey");
		this.bulletType = BulletType.EXPLOSION;
	}
	
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
	public ExplosionBullet(float posX, float posY, float posZ, float newX, float newY, float newZ,
						   AbstractEntity user, float speed) {
		super(posX, posY, posZ, newX, newY, newZ, user, 1, speed);
		this.setTexture("battle_seed_grey");
	    this.bulletType = BulletType.EXPLOSION;
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
	public ExplosionBullet(float posX, float posY, float posZ, float newX, float newY, float newZ, float xLength,
			float yLength, float zLength, AbstractEntity user, int hitCount, float speed) {
		super(posX, posY, posZ, newX, newY, newZ, xLength, yLength, zLength, user, hitCount, speed);
		this.setTexture("battle_seed_grey");
		this.bulletType = BulletType.EXPLOSION;
	}
	
	@Override
	protected void applyEffect(Harmable target) {
        explosion = new Explosion(this.getPosX(), this.getPosY(), this.getPosZ(), 0.3f);
        GameManager.get().getWorld().addEntity(explosion);
		AbstractEntity entity = (AbstractEntity)target;
		List<AbstractEntity> closest = WorldUtil.allEntitiesToPosition(entity.getPosX(), entity.getPosY(), 2.5f, Enemy.class);
		target.giveEffect(new Effect("Explosion", 1, 1000, 1, 0, 1, 0, user));
		ArrayList<Player> players = ((PlayerManager) (GameManager.get().getManager(PlayerManager.class))).getPlayers();
		for(AbstractEntity close : closest) {
		    if(user instanceof Player && close instanceof Player && players.contains(close)) {
		    } else if(user instanceof Enemy && close instanceof Player) {
		        ((Player)close).giveEffect(new Effect("Explosion", 1, 1000, 1, 0, 1, 0, user));
		    } else {
		        ((Enemy)close).giveEffect(new Effect("Explosion", 1, 1000, 1, 0, 1, 0, user));
		    }
		}
	}
}
