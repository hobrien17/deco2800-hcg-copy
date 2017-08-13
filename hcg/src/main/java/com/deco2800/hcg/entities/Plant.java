package com.deco2800.hcg.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 * Tower that can do things.
 * @author leggy
 *
 */
public class Plant extends AbstractEntity implements Tickable {



	/**
	 * Constructor for the base
	 *
	 * @param posX
	 *            The x-coordinate.
	 * @param posY
	 *            The y-coordinate.
	 * @param posZ
	 *            The z-coordinate.
	 */
	public Plant(float posX, float posY, float posZ) {
		super(posX, posY, posZ, 0.5f, 0.5f, 1, 1, 1, false);
		this.setTexture("plant");
	}



	/**
	 * On Tick handler
	 * @param gameTickCount Current game tick
	 */
	@Override
	public void onTick(long gameTickCount) {


	}
}
