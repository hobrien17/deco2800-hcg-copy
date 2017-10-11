package com.deco2800.hcg.managers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deco2800.hcg.entities.garden_entities.seeds.*;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.stackable.HealthPotion;

/**
 * An class that creates new items based on the inputed parameters
 *
 * @author Henry O'Brien
 */
public class ItemManager extends Manager {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameManager.class);

    /**
     * Returns a new item, defined by the given name
     *
     * @param name The name of the item to return
     * @return A new item
     */
    public Item getNew(String name) {
        if (name == "sunflower_seed") {
            return new Seed(Seed.Type.SUNFLOWER);
        } else if (name == "explosive_seed") {
        	 return new Seed(Seed.Type.EXPLOSIVE);
        } else if (name == "fire_seed") {
        	 return new Seed(Seed.Type.FIRE);
        } else if (name == "grass_seed") {
        	 return new Seed(Seed.Type.GRASS);
        } else if (name == "ice_seed") {
        	 return new Seed(Seed.Type.ICE);
        } else if (name == "water_seed") {
        	 return new Seed(Seed.Type.WATER);
        } else if (name == "health_potion") {
        	return new HealthPotion(10);
        }

        LOGGER.warn("Unable to find given class, returning null");
        return null;
    }
}
