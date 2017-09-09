package com.deco2800.hcg.entities;

import com.deco2800.hcg.entities.enemy_entities.Enemy;
import com.deco2800.hcg.util.Effect;

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

	public Fireball(float posX, float posY, float posZ, float newX, float newY, float newZ, AbstractEntity user) {
		super(getPosChange(posX, posY, newX, newY)[0], getPosChange(posX, posY, newX, newY)[1], posZ, newX, newY, newZ, 
				getTextureVals(posX, posY, newX, newY)[0], getTextureVals(posX, posY, newX, newY)[1], 1f, user, 0.2f);
		if(newX > posX && newY > posY) {
			this.setTexture("fireball_right");
		} else if(newX > posX && newY < posY) {
			this.setTexture("fireball_up");
		} else if(newX < posX && newY < posY) {
			this.setTexture("fireball_left");
		} else if(newX < posX && newY > posY) {
			this.setTexture("fireball_down");
		}
	}
	
	/**
	 * Aligns the position of the fireball so that it appears to be fired out of the user
	 * 
	 * @param posX
	 * 			the normal x position
	 * @param posY
	 * 			the normal y position
	 * @param newX
	 * 			the new x position
	 * @param newY
	 * 			the new y position
	 * @return A pair of adjusted x and y co-ordinates
	 */
	private static float[] getPosChange(float posX, float posY, float newX, float newY) {
		float[] ret = new float[2];
		if(newX < posX && newY < posY) {
			ret[0] = posX - 2f;
			ret[1] = posY - 2f;
		} else if(newX < posX && newY > posY) {
			ret[0] = posX - 2f;
			ret[1] = posY + 2f;
		} else {
			ret[0] = posX;
			ret[1] = posY;
		}
		return ret;
	}
	
	/**
	 * Modifies the texture size so that all fireballs are evenly sized
	 * 
	 * @param posX
	 * 			the original x position
	 * @param posY
	 * 			the original y position
	 * @param newX
	 * 			the new x position
	 * @param newY
	 * 			the new y position
	 * @return The length and width of the fireball texture
	 */
	private static float[] getTextureVals(float posX, float posY, float newX, float newY) {
		float[] ret = new float[2];
		if((newX > posX && newY > posY) || (newX < posX && newY < posY)) {
			ret[0] = 3f;
			ret[1] = 3f;
		} else {
			ret[0] = 1f;
			ret[1] = 1f;
		}
		return ret;
	}
	
	@Override
	protected void removeEnemy(Enemy target) {
		target.giveEffect(new Effect("Shot", 1, 2, 0, 0, 1, 0));
	}

}
