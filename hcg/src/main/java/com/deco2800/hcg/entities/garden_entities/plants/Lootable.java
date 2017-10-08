package com.deco2800.hcg.entities.garden_entities.plants;

import java.util.List;
import java.util.Map;

import com.deco2800.hcg.items.Item;

/**
 * An interface to allow objects to drop loot
 *
 * @author Henry O'Brien
 */
public interface Lootable {
	
	/**
     * Returns the rarity of all possible loot dropped by this object
     *
     * @return A mapping of items to rarity (between 0 and 1)
     */
    Map<String, Double> getRarity();

    /**
     * Returns all possible loot dropped by this object
     *
     * @return an array of Strings representing items
     */
    List<String> getLootStrings();

    /**
     * Returns a list of new loot items where 0 <= length(\result) <=
     * length(this.getLoot()) Loot may vary based on rarity and other factors
     * Possible to return an empty array
     *
     * Currently only supports lists of 1 item
     *
     * @return A list of items
     */
    List<Item> getLoot();
    
    /**
     * Generates a random item based on the loot rarity
     *
     * @return A random item string in the plant's loot map
     */
    String randItem();
    
    /**
     * Adds loot item(s) to the surrounding area
     */
    void loot();
}
