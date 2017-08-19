package com.deco2800.hcg.entities.garden_entities.seeds;

import com.deco2800.hcg.entities.garden_entities.plants.AbstractGardenPlant;
import com.deco2800.hcg.items.Item;

/**
 * Represents a seed that can be planted
 * Grows into a specific plant type
 * Used as a shell for more specific types of seeds
 * 
 * @author Henry O'Brien
 *
 */
public abstract class AbstractSeed implements Item {
	
    public boolean isStackable() {
    	return true;
    }
    
    @Override
	public boolean isWearable() {
		return false;
	}
    
    @Override
	public int getWeight() {
		// TODO Auto-generated method stub
		return 0;
	}
    
    @Override
	public int maxStackSize() {
		// TODO Auto-generated method stub
		return 5;
	}
    
    /**
     * Gets the type of plant that grows when planted
     * 
     * @return the type of plant that grows out of this seed
     */
    public abstract AbstractGardenPlant plant();
}
