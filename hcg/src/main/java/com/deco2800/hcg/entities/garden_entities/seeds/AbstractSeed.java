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

    /**
     * Creates an Enum type for the different seed types
     */
    public enum Seed{
        SUNFLOWER ("sunflower_seed", "sunflower"),
        CACTUS ("explosive_seed", "cactus"),
        GRASS ("grass_seed", "grass"),
        ICE ("ice_seed", "ice"),
        INFERNO ("fire_seed", "inferno"),
        WATER ("water_seed", "water");

        private String textureName;
        private String plantName;

        Seed(String textureName, String plantName){
            this.textureName = textureName;
            this.plantName = plantName;
        }
    }

    /**
     * Creates a new plant at the given x and y co-ordinates
     *
     * @return a new plant
     */
    public abstract AbstractGardenPlant plant(int xPos, int yPos);

    /**
     * Checks if the seed is stackable.
     * @return true if the seed is stackable, else false
     */
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
	public int getMaxStackSize() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public boolean addToStack(int number) {
    	//TODO: Added to fix implementation change. Alter this to whatever you want.
    	return false;
	}

    @Override
    public int getBaseValue() {
        // TODO Added to fix implementation change. Alter this to whatever you want.
        return 0;
    }

    @Override
    public boolean isTradable() {
        // TODO Added to fix implementation change. Alter this to whatever you want.
        return false;
    }
}
