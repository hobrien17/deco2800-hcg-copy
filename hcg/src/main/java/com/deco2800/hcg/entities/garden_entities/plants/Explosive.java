package com.deco2800.hcg.entities.garden_entities.plants;

import java.util.HashMap;

import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.ItemManager;

/**
 * Represents the Explosive plant which drops random loot
 *
 * @author Reilly Lundin
 */
public class Explosive extends AbstractGardenPlant {

    public Explosive(Pot master) {
        super(master, 100);
    }

    @Override
    public String getThisTexture() {
        switch (this.getStage()) {
            case SPROUT:
                return null;
            case SMALL:
                return null;
            case LARGE:
                return null;
        }
        return null;

    }

    @Override
    public void setupLoot() {
        lootRarity = new HashMap<>();

        lootRarity.put("explosive_seed", 1.0);

        double sum = 0.0;
        for (Double rarity : lootRarity.values()) {
            if (rarity < 0.0 || rarity > 1.0) {
                LOGGER.error("Rarity should be between 0 and 1");
            }
            sum += rarity;
        }
        if (Double.compare(sum, 1.0) != 0) {
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
