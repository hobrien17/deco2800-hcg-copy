package com.deco2800.hcg.entities;


/**
 * A generic player instance for the game
 */
public class Peon extends AbstractEntity implements Tickable {

	/**
	 * Constructor for the Peon
	 * @param posX
	 * @param posY
	 * @param posZ
	 */
	public Peon(float posX, float posY, float posZ) {
		super(posX, posY, posZ, 1, 1, 1);
		this.setTexture("player");
	}

	@Override
	public void onTick(int i) {

	}
}