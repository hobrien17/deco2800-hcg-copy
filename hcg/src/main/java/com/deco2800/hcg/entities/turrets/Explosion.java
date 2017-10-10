package com.deco2800.hcg.entities.turrets;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Tickable;
import com.deco2800.hcg.managers.GameManager;

/**
 * Explosion class
 * Creates an explosion animation which grows before disappearing
 * 
 * @author Henry O'Brien
 *
 */
public class Explosion extends AbstractEntity implements Tickable {
	
	private float change;
	private final static float MOD = 0.015f;
	
	/**
	 * Creates a new explosion at the given co-ordinates
	 * 
	 * @param posX
	 * 			the x position of the explosion
	 * @param posY
	 * 			the y position of the explosion
	 * @param posZ
	 * 			the z position of the explosion
	 */
	public Explosion(float posX, float posY, float posZ, float change) {
		super(posX, posY, posZ, 0, 0, 0, 0.01f, 0.01f, true);
		this.setTexture("explosion");
		this.change = change;
	}
	
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
}
