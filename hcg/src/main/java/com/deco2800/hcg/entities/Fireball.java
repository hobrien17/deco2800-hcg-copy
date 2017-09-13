package com.deco2800.hcg.entities;

import java.util.List;

import com.deco2800.hcg.entities.enemy_entities.Enemy;
import com.deco2800.hcg.entities.turrets.AbstractTurret;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.util.Box3D;
import com.deco2800.hcg.util.Effect;
import com.deco2800.hcg.entities.bullets.Bullet;

/**
 * Fireball class
 * Extends bullet
 * Used by the FireTurret
 * Currently is just a larger bullet, but this will probably change
 *
 * @author Henry O'Brien
 *
 */
public class Fireball extends Bullet {

	public Fireball(float posX, float posY, float posZ, float newX, float newY, float newZ,
					AbstractEntity user, int hitCount) {
		super(posX-2.5f, posY, posZ, newX, newY, newZ, 3f, 3f, 1f, user, hitCount);
		this.speed = 0.2f;
	}

	@Override
	protected void applyEffect(Enemy target) {
		target.giveEffect(new Effect("Shot", 1, 2, 0, 0, 1, 0));
	}

}

