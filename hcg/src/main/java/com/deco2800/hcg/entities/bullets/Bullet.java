package com.deco2800.hcg.entities.bullets;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Harmable;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.Tickable;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.entities.enemyentities.Enemy;
import com.deco2800.hcg.entities.enemyentities.MushroomTurret;
import com.deco2800.hcg.entities.terrain_entities.DestructableTree;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.ParticleEffectManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.managers.SoundManager;
import com.deco2800.hcg.shading.LightEmitter;
import com.deco2800.hcg.util.Box3D;
import com.deco2800.hcg.util.Effect;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector3;


/**
 * A generic player instance for the game
 */
public class Bullet extends AbstractEntity implements Tickable, LightEmitter {

	protected float speed = 0.5f;

	protected float goalX;
	protected float goalY;

	protected float angle;
	protected float changeX;
	protected float changeY;
	protected float deltaX;
	protected float deltaY;

	protected AbstractEntity user;
	protected int hitCount;
	protected BulletType bulletType;

	protected int distanceTravelled;

	private SoundManager soundManager;
	private GameManager gameManager = GameManager.get();
	private PlayerManager playerManager = (PlayerManager) gameManager.getManager(PlayerManager.class);

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
		this.soundManager = (SoundManager) GameManager.get().getManager(SoundManager.class);
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
		this.soundManager = (SoundManager) GameManager.get().getManager(SoundManager.class);
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
		this.bulletType = BulletType.BASIC;

		this.goalX = newX;
		this.goalY = newY;

		float deltaX = getPosX() - (goalX - this.getXLength()/2);
		float deltaY = getPosY() - (goalY - this.getYLength()/2);

		this.angle = (float) (Math.atan2(deltaY, deltaX)) + (float) (Math.PI);

		this.changeX = (float) (speed * Math.cos(angle));
		this.changeY = (float) (speed * Math.sin(angle));

		this.user = user;
		this.hitCount = hitCount;

		this.soundManager = (SoundManager) GameManager.get().getManager(SoundManager.class);
	}

	/**
	 * Return BulletType
	 *
	 * @return bulletType
	 *             Type of Bullet Enum
	 */
	public BulletType getBulletType() {
		return this.bulletType;
	}

	/**
	 * On Tick handler
	 *
	 * @param gameTickCount
	 *            Current game tick
	 */
	@Override
	public void onTick(long gameTickCount) {
		distanceTravelled += 1;
		if (distanceTravelled >= 20 && distanceTravelled % 20 == 0) {
			specialAbility();
		}
		entityHit();
		if (Math.abs(Math.abs(this.getPosX() + this.getXLength()/2)
				- Math.abs(goalX)) < 0.5
				&& Math.abs(Math.abs(this.getPosY() + this.getYLength()/2)
				- Math.abs(goalY)) < 0.5) {
		}
		setPosX(getPosX() + changeX);
		setPosY(getPosY() + changeY);
		if (distanceTravelled >= 100) {
			GameManager.get().getWorld().removeEntity(this);
		}
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
			if (!this.collidesWith(entity)) {
				continue;
			}
			// Collision with enemy
			if (entity instanceof Enemy
					&& (user instanceof Player || user instanceof Corpse)) {
				Enemy target = (Enemy) entity;
				if (target instanceof MushroomTurret) {
					MushroomTurret turret = (MushroomTurret) target;
					turret.removeObserver();
					turret.removeWeapon();
					if (user instanceof Player) {
						((Player) user).killLogAdd(target.getEnemyType());
					}
					GameManager.get().getWorld().removeEntity(turret);

				} else if (target.getHealthCur() <= 0) {
					// Temporary increase of xp for all enemies killed
					playerManager.getPlayer().gainXp(50);
					applyEffect(target);
				} else {
					// Temporary increase of xp for all enemies killed
					playerManager.getPlayer().gainXp(50);
					applyEffect(target);
				}
                spawnParticles(entity, "hitPuff.p");
				hitCount--;
			}

			// Collision with destructable tree
			if (entity instanceof DestructableTree && user instanceof Player
					&& !(this instanceof GrassBullet)) {
				DestructableTree tree = (DestructableTree) entity;
				spawnParticles(entity, "hitPuff.p");
				applyEffect(tree);
				hitCount--;
			}

			// Collision with player
			if (entity instanceof Player && user instanceof Enemy) {
				// add code to apply effect to player here
				Enemy enemyUser = (Enemy) user;
				spawnParticles(entity, "hitPuff.p");
				enemyUser.causeDamage((Player) entity);
				hitCount--;
			}

			if (hitCount == 0) {
				GameManager.get().getWorld().removeEntity(this);
				break;
			}
		}
	}

	/**
	 * Method to overwrite in the grassBullet and trackingBullet class.
	 * Called in the onTick method.
	 */
	protected void specialAbility() {
		return;
	}

	/**
	 * Updates the angle (direction) that the bullet travels too.
	 *
	 * @param changeAngle: The value to change the angle by
	 */
	public void updateAngle(int changeAngle) {
		this.angle += changeAngle;
		this.changeX = (float) (speed * Math.cos(angle));
		this.changeY = (float) (speed * Math.sin(angle));
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
		target.giveEffect(new Effect("Shot", 1, 5000, 1, 0, 1, 0, user));
	}

	protected void playCollisionSound(Bullet bulletType) {
	    return;
	}

    @Override
    public Color getLightColour() {
        return Color.ORANGE;
    }

    @Override
    public float getLightPower() {
        return 3;
    }
	
	protected void spawnParticles(AbstractEntity entity, String particleFile) {
	    ParticleEffect hitEffect = new ParticleEffect();
        hitEffect.load(Gdx.files.internal("resources/particles/" + particleFile),
        Gdx.files.internal("resources/particles/"));
        Vector3 position = GameManager.get().worldToScreen(new Vector3(entity.getPosX(), entity.getPosY(), 0));
        hitEffect.setPosition(position.x, position.y);
        hitEffect.start();
        ((ParticleEffectManager) GameManager.get().getManager(ParticleEffectManager.class)).addEffect(entity, hitEffect);
	}
}