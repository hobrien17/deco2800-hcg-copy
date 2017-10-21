package com.deco2800.hcg.entities.garden_entities.plants;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.garden_entities.seeds.Seed;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlantManager;

/**
 * An entity that contains a plant
 *
 * @author Henry O'Brien
 */
public class Pot extends AbstractEntity  {
	
	protected AbstractGardenPlant plant;
	private boolean locked;

	/**
	 * Creates a pot at the given position
	 * @param posX the x position
	 * @param posY the y position
	 * @param posZ the z position
	 */
	public Pot(float posX, float posY, float posZ) {
		super(posX, posY, posZ, 0.7f, 0.7f, 1, 1f, 1f, false);
		plant = null;
		locked = true;
		setThisTexture();
	}
	
	/**
	 * Adds a plant to the pot, if empty
	 * 
	 * @param plant 
	 * 			the plant to be added
	 * @return true if the plant was added, false if it could not be added
	 */
	public boolean addPlant(AbstractGardenPlant plant) {
		if(this.plant == null && !locked) {
			this.plant = plant;
			setThisTexture();
			return true;
		}
		return false;
	}
	
	/**
	 * Grows a plant out of the given seed and adds it to the pot
	 * 
	 * @param seed
	 * 			the seed to plant in the pot
	 * @return true of the seed was successfully added, false if it could not be added
	 */
	public boolean plantInside(Seed seed) {
		if(this.plant == null && !locked) {
			this.plant = seed.getNewPlant(this);
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
		((PlantManager)GameManager.get().getManager(PlantManager.class)).removePlants(plant);
		plant = null;
		setThisTexture();
	}
	
	/**
	 * Sets the texture of the pot based on the plant inside
	 */
	public void setThisTexture() {
		if(plant == null) {
			if(locked) {
				this.setTexture("pot_locked");
			} else {
				this.setTexture("pot");
			}
		} else {
			this.setTexture(plant.getThisTexture());
		}
	}
	
	/**
	 * Unlocks the pot, allowing it to be planted in
	 */
	public void unlock() {
		locked = false;
		setThisTexture();
	}
	
	/**
	 * Determines whether the pot is empty or not
	 * 
	 * @return true if no plant is planted inside, otherwise false
	 */
	public boolean isEmpty() {
		return plant == null;
	}
	
	/**
	 * Determines whether the pot is locked or not
	 * 
	 * @return true if the pot is locked, otherwise false
	 */
	public boolean isLocked() {
		return locked;
	}
}
