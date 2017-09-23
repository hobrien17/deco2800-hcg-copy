package com.deco2800.hcg.entities.bullets;

import com.deco2800.hcg.entities.enemy_entities.Enemy;
import com.deco2800.hcg.entities.terrain_entities.DestructableTree;
import com.deco2800.hcg.entities.terrain_entities.Tree;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.util.Effect;
import com.deco2800.hcg.util.WorldUtil;

import java.util.Optional;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.bullets.Bullet;

/**
 * Fireball class
 * Used by the FireTurret
 * Is currently a large bullet that destroys everything in its path
 * Has the capability to be changed to set enemies on fire instead of destroying them
 *
 * @author Henry O'Brien
 *
 */
public class GrassBullet extends Bullet {

	/**
	 * Creates a new fireball moving towards the given co-ordinates
	 * 
	 * @param posX
	 * 			the fireball's starting x position
	 * @param posY
	 * 			the fireball's starting y position
	 * @param posZ
	 * 			the fireball's starting z position
	 * @param newX
	 * 			the fireball's target x position
	 * @param newY
	 * 			the fireball's target y position
	 * @param newZ
	 * 			the fireball's target z position
	 * @param user
	 * 			the entity who shot the fireball
	 */
	public GrassBullet(float posX, float posY, float posZ, float newX, float newY, float newZ, AbstractEntity user) {
		super(posX, posY, posZ, newX, newY, newZ, user, 1);	
	}

	@Override
	public void onTick(long gameTickCount) {
		if (Math.abs(Math.abs(this.getPosX()) - Math.abs(goalX)) < 1
				&& Math.abs(Math.abs(this.getPosY()) - Math.abs(goalY)) < 1) {
			Optional<AbstractEntity> close = WorldUtil.closestEntityToPosition(goalX, goalY, 0.5f);
			if(!close.isPresent()) {
				DestructableTree tree = new DestructableTree(goalX, goalY, 0);
				GameManager.get().getWorld().addEntity(tree);
			}
			GameManager.get().getWorld().removeEntity(this);
		}
		setPosX(getPosX() + changeX);
		setPosY(getPosY() + changeY);

		entityHit();
	}

}

