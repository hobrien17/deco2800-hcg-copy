package com.deco2800.hcg.entities.turrets;

import com.badlogic.gdx.graphics.Color;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Tickable;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.SoundManager;
import com.deco2800.hcg.shading.LightEmitter;

/**
 * Explosion class
 * Creates an explosion animation which grows before disappearing
 * 
 * @author Henry O'Brien
 *
 */
public class Explosion extends AbstractEntity implements Tickable, LightEmitter {
	
	private float change;
	private static final float MOD = 0.015f;
	
	/**
	 * Creates a new explosion at the given co-ordinates
	 * 
	 * @param posX
	 * 			the x position of the explosion
	 * @param posY
	 * 			the y position of the explosion
	 * @param posZ
	 * 			the z position of the explosion
	 * @param change
	 * 			the explosion's rate of change
	 */
	public Explosion(float posX, float posY, float posZ, float change) {
		super(posX, posY, posZ, 0, 0, 0, 0.01f, 0.01f, true);
		this.setTexture("explosion");
		this.change = change;
		((SoundManager)GameManager.get().getManager(SoundManager.class)).playSound("explosion");

	}
	
	/**
	 * Returns the rate at which the explosion grows at
	 * 
	 * @return the explosion's rate of change
	 */
	public float getRateOfChange() {
	    return this.change;
	}

	@Override
	public void onTick(long gameTickCount) {
		if(this.getXRenderLength() <= 0 || this.getYRenderLength() <= 0) {
			GameManager.get().getWorld().removeEntity(this);
		} else{
			growRender(change, change);
			this.setPosX(this.getPosX()-change);
			change -= MOD;
		}
	}

	@Override
	public Color getLightColour() {
		// TODO Auto-generated method stub
		return Color.ORANGE;
	}

	@Override
	public float getLightPower() {
		// TODO Auto-generated method stub
		return change * 20;
	}
}
