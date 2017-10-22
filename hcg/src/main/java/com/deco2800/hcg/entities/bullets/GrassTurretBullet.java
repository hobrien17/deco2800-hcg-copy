package com.deco2800.hcg.entities.bullets;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.terrain_entities.DestructableTree;
import com.deco2800.hcg.managers.GameManager;

public class GrassTurretBullet extends Bullet {
	public GrassTurretBullet(float posX, float posY, float posZ, float goalX, float goalY, float goalZ,
			  AbstractEntity user, float speed) {
		super(posX, posY, posZ, goalX, goalY, goalZ,
			user, 1, speed);
		this.setTexture("battle_seed_green");
		this.bulletType = BulletType.GRASS;
	}
	
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
			DestructableTree tree = new DestructableTree(goalX, goalY, 0);
			GameManager.get().getWorld().addEntity(tree);
			GameManager.get().getWorld().removeEntity(this);
		}
		setPosX(getPosX() + changeX);
		setPosY(getPosY() + changeY);
	}
}
