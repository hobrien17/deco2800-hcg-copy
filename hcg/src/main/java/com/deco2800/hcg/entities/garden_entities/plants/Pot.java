package com.deco2800.hcg.entities.garden_entities.plants;

import com.deco2800.hcg.entities.AbstractEntity;

/**
 * An entity that contains a plant
 *
 * @author Henry O'Brien
 */
public class Pot extends AbstractEntity  {
	
	AbstractGardenPlant plant;

	/**
	 * Creates a pot at the given position
	 * @param posX the x position
	 * @param posY the y position
	 * @param posZ the z position
	 */
	public Pot(float posX, float posY, float posZ) {
		super(posX, posY, posZ, 0.7f, 0.7f, 1, 1f, 1f, false);
		plant = null;
		setThisTexture();
	}
	
	/**
	 * Adds a plant to the pot, if empty
	 * 
	 * @param the plant to be added
	 * @return true if the plant was added, false if it could not be added
	 */
	public boolean addPlant(AbstractGardenPlant plant) {
		if(this.plant == null) {
			this.plant = plant;
			setThisTexture();
			return true;
		}
		return false;
		
	}
	
	/**
	 * Returns the plant currently in the pot
	 * 
	 * @return the plant in the pot, null if empty
	 */
	public AbstractGardenPlant getPlant() {
		return plant;
	}
	
	/**
	 * Removes the current plant from the pot
	 * 
	 */
	public void removePlant() {
		plant = null;
		setThisTexture();
	}
	
	/**
	 * Sets the texture of the pot based on the plant inside
	 */
	public void setThisTexture() {
		if(plant == null) {
			this.setTexture("pot");
		} else {
			this.setTexture(plant.getThisTexture());
		}
	}
}
