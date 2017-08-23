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

	@Override
	public boolean addToStack(int number) {
    	//TODO: Added to fix implementation change. Alter this to whatever you want.
    	return false;
	}
    
    /**
     * Creates a new plant at the given x and y co-ordinates
     * 
     * @return a new plant
     * 
     * @deprecated This method should be replaced due to the addition of pots
     */
    public abstract AbstractGardenPlant plant(int xPos, int yPos);
}
