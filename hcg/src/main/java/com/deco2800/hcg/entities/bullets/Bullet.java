package com.deco2800.hcg.entities.bullets;

import com.deco2800.hcg.entities.enemyentities.MushroomTurret;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.util.Box3D;
import com.deco2800.hcg.util.Effect;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Harmable;
import com.deco2800.hcg.entities.Tickable;
import com.deco2800.hcg.entities.terrain_entities.DestructableTree;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.entities.enemyentities.Enemy;
import com.deco2800.hcg.entities.Player;

import java.util.List;

/**
 * A generic player instance for the game
 */
public class Bullet extends AbstractEntity implements Tickable {

	protected float speed = 0.5f;

	protected float goalX;
	protected float goalY;

	protected float angle;
	protected float changeX;
	protected float changeY;

	private AbstractEntity user;
	private int hitCount;

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
	public Bullet(float posX, float posY, float posZ, float xd, float yd,
			AbstractEntity user, int hitCount) {
		this(posX, posY, posZ, xd, yd, posZ,
				user, hitCount);
	}

	/**
	 * Creates a new bullet moving towards the given co-ordinates
	 *
	 * @param posX
	 *            the x position of the bullet
	 * @param posY
	 *            the x position of the bullet
	 * @param posZ
	 *            the x position of the bullet
	 * @param newX
	 *            the x goal of the bullet
	 * @param newY
	 *            the y goal of the bullet
	 * @param newZ
	 *            the z goal of the bullet
	 * @param user
	 *            the entity using the bullet
	 * @param hitCount
	 *            the total number of enemies that can be hit
	 */
	public Bullet(float posX, float posY, float posZ, float newX, float newY,
			float newZ, AbstractEntity user, int hitCount) {
		this(posX, posY, posZ, newX, newY, newZ, 0.6f, 0.6f, 1, user, hitCount);
	}

	/**
	 * Creates a new bullet of specified dimensions moving towards the given
	 * co-ordinates
	 *
	 * @param posX
	 *            the x position of the bullet
	 * @param posY
	 *            the x position of the bullet
	 * @param posZ
	 *            the x position of the bullet
	 * @param newX
	 *            the x goal of the bullet
	 * @param newY
	 *            the y goal of the bullet
	 * @param newZ
	 *            the z goal of the bullet
	 * @param xLength
	 *            the size of the bullet in the x-direction
	 * @param yLength
	 *            the size of the bullet in the y-direction
	 * @param zLength
	 *            the size of the bullet in the z-direction
	 * @param user
	 *            the entity using the bullet
	 * @param hitCount
	 *            the total number of enemies that can be hit
	 */
	public Bullet(float posX, float posY, float posZ, float newX, float newY,
			float newZ, float xLength, float yLength, float zLength,
			AbstractEntity user, int hitCount) {
		super(posX, posY, posZ, xLength, yLength, zLength);
		this.setTexture("battle_seed");

		this.goalX = newX;
		this.goalY = newY;

		float deltaX = getPosX() - goalX;
		float deltaY = getPosY() - goalY;

		this.angle = (float) (Math.atan2(deltaY, deltaX)) + (float) (Math.PI);

		this.changeX = (float) (speed * Math.cos(angle));
		this.changeY = (float) (speed * Math.sin(angle));

		this.user = user;
		this.hitCount = hitCount;
	}

	/**
	 * Returns the projection of the bullet
	 *
	 * @param xd
	 *            the bullet's x direction
	 * @param yd
	 *            the bullet's y direction
	 * @return a pair of x and y co-ordinates
	 */
	protected static float[] getProj(float xd, float yd) {
		float[] proj = new float[2];

		proj[0] = xd / 55f;
		proj[1] = -(yd - 32f / 2f) / 32f + proj[0];
		proj[0] -= proj[1] - proj[0];
		
		return proj;
	}

	/**
	 * On Tick handler
	 *
	 * @param gameTickCount
	 *            Current game tick
	 */
	@Override
	public void onTick(long gameTickCount) {
		if (Math.abs(Math.abs(this.getPosX()) - Math.abs(goalX)) < 1
				&& Math.abs(Math.abs(this.getPosY()) - Math.abs(goalY)) < 1) {
			GameManager.get().getWorld().removeEntity(this);
		}
		setPosX(getPosX() + changeX);
		setPosY(getPosY() + changeY);

		entityHit();
	}

	/**
	 * Detects collision with entity and if enemy, apply effect of bullet. After
	 * applying effect, bullet is removed from the world.
	 */
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
					if (target instanceof  MushroomTurret) {
						MushroomTurret turret = (MushroomTurret) target;
						turret.remove_observer();
						GameManager.get().getWorld().removeEntity(turret);

					} else if (target.getHealthCur() <= 0) {
							applyEffect(target);
					}
					hitCount--;
				}
				// Collision with destructable tree
				if (entity instanceof DestructableTree && user instanceof Player && !(this instanceof GrassBullet)) {
					DestructableTree tree = (DestructableTree) entity;
					applyEffect(tree);
					hitCount--;
				}
				// Collision with player
				if (entity instanceof Player && user instanceof Enemy) {
					// add code to apply effect to player here
					Enemy enemyUser = (Enemy) user;
					enemyUser.causeDamage((Player) entity);
					hitCount--;
				}
				// COllision with corpse
				if (entity instanceof Corpse && user instanceof Player) {
					Corpse corpse = (Corpse) entity;
					corpse.plantInside(this);
					hitCount = 0;
				}
				if (hitCount == 0) {
					GameManager.get().getWorld().removeEntity(this);
					break;

				}
			}
		}
	}

	/**
	 * Performs the action to be performed when an enemy is hit by a bullet
	 *
	 * @param target
	 *            the hit enemy
	 */
	protected void applyEffect(Harmable target) {
		// Set target to be the enemy whose collision got detected and
		// give it an effect
		target.giveEffect(new Effect("Shot", 1, 1, 0, 0, 1, 0));
	}
}
