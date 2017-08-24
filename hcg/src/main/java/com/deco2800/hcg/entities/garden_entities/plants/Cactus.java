package com.deco2800.hcg.entities.garden_entities.plants;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.ItemManager;

/**
 * Represents the Cactus plant which drops random loot
 *
 * @author Reilly Lundin
 */
public class Cactus extends AbstractGardenPlant {

    public Cactus(Pot master) {
        super(master, 750);
    }
    
    @Override
	public String getThisTexture() {
		switch (this.getStage()) {
		case SPROUT:
			return "cactus_01";
		case SMALL:
			return "cactus_02";
		case LARGE:
			return "cactus_03";
		}
		return null;
    }

    @Override
    public void setupLoot() {
        lootRarity = new HashMap<>();

        lootRarity.put("rock_seed", 1.0);

        double sum = 0.0;
        for (Double rarity : lootRarity.values()) {
            if (rarity < 0.0 || rarity > 1.0) {
                LOGGER.error("Rarity should be between 0 and 1");
            }
            sum += rarity;
        }
        if (sum != 1.0) {
            LOGGER.warn("Total rarity should be 1");
        }
    }

    @Override
    public Item[] loot() {
        Item[] arr = new Item[1];
        arr[0] = ItemManager.getNew(randItem());

        return arr;
    }

}
