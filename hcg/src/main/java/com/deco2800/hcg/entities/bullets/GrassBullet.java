package com.deco2800.hcg.entities.bullets;

import com.deco2800.hcg.entities.terrain_entities.DestructableTree;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.util.WorldUtil;
import java.util.Optional;
import com.deco2800.hcg.entities.AbstractEntity;

/**
 * Grass bullet class
 * This bullet acts like a normal bullet, but if it doesn't hit any enemies it will spawn a DestructableTree
 *    in its spot
 *
 * @author Henry O'Brien
 *
 */
public class GrassBullet extends Bullet {

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

