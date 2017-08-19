package com.deco2800.hcg.entities.garden_entities.seeds;

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
}
