package com.deco2800.hcg.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;

import java.util.List;
import java.util.Random;

/**
 * A generic player instance for the game
 */
public class Bullet extends AbstractEntity implements Tickable {

	private float speed = 2f;


	private float goalX;
	private float goalY;


	public Bullet(float posX, float posY, float posZ, float xd, float yd) {
		super(posX, posY, posZ, 0.6f, 0.6f, 1);
//		super(posX, posY, posZ, 0.5f, 0.5f, 1, 1, 1, false);
		this.setTexture("seed");

		float projX, projY;

		projX = xd/55f;
		projY = -(yd - 32f / 2f) / 32f + projX;
		projX -= projY - projX;

		this.goalX = projX;
		this.goalY = projY;

	}

	@Override
	public void onTick(int i) {
		if(Math.abs(Math.abs(this.getPosX()) - Math.abs(goalX)) < 1 && Math.abs(Math.abs(this.getPosY()) - Math.abs(goalY)) < 1) {
			GameManager.get().getWorld().removeEntity(this);
			GameManager.get().getWorld().addEntity(new Plant(this.goalX, this.goalY, 0));
		}

		float deltaX = getPosX() - goalX;
		float deltaY = getPosY() - goalY;

		float angle = (float)(Math.atan2(deltaY, deltaX)) + (float)(Math.PI);

		float changeX = (float)(speed * Math.cos(angle));
		float changeY = (float)(speed * Math.sin(angle));

		setPosX(getPosX() + changeX);
		setPosY(getPosY() + changeY);

	}
}