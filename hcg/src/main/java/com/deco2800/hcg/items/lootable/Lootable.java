package com.deco2800.hcg.items.lootable;

import java.util.List;
import java.util.Map;

import com.deco2800.hcg.items.Item;

/**
 * An interface to allow objects to drop loot
 * 
 * See the plant classes for a typical implementation
 *
 * @author Henry O'Brien
 */
public interface Lootable {
	
	/**
     * Returns the rarity of all possible loot dropped by this object
     *
     * @return A mapping of lootwrappers to rarity (between 0 and 1)
     */
    Map<LootWrapper, Double> getRarity();

    /**
     * Returns a list of all loot
     *
     * @return A list of items
     */
    List<Item> getAllLoot();
    
    /**
     * Generates a random item based on the loot rarity
     *
     * @return A random item wrapper in the object's loot map
     */
    LootWrapper randItem();
    
    /**
     * Returns a list of randomly generated loot from the map
     * 
     * @return A list of loot wrappers
     */
    List<Item> getLoot();
    
    /**
     * Adds loot item(s) to the surrounding area
     */
    void loot();
}
