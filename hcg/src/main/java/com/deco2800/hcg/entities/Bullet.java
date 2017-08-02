package com.deco2800.hcg.entities;

import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.moos.entities.AbstractEntity;
import com.deco2800.moos.entities.Tickable;
import com.deco2800.moos.managers.GameManager;
import com.deco2800.moos.managers.SoundManager;
import com.deco2800.moos.util.Box3D;

import java.util.List;
import java.util.Random;

/**
 * A generic player instance for the game
 */
public class Bullet extends AbstractEntity implements Tickable {

	private float speed = 1f;

	private PlayerManager playerManager;
	private SoundManager soundManager;


	private float goalX;
	private float goalY;



	public Bullet(float posX, float posY, float posZ, float xd, float xy) {
		super(posX, posY, posZ, 0.6f, 0.6f, 1);
//		super(posX, posY, posZ, 0.5f, 0.5f, 1, 1, 1, false);
		this.setTexture("seed");

		this.goalX = xd;
		this.goalY = xy;
	}

	@Override
	public void onTick(int i) {
		if(this.getPosX() == goalX && this.getPosY() == goalY) {
			GameManager.get().getWorld().removeEntity(this);
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