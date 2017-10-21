package com.deco2800.hcg.entities.garden_entities.plants;

import java.util.HashMap;

import com.deco2800.hcg.items.lootable.LootWrapper;

/**
 * Represents a basic plant which drops basic loot
 *
 * @author Henry O'Brien
 */
public class Sunflower extends AbstractGardenPlant {

    /**
     * Creates a new Sunflower plant in the given pot
     * @param master the pot to associate the plant with
     */
    public Sunflower(Pot master) {
        super(master, "sunflower", 600); //~5 real-life minutes
    }

    @Override
    public String getThisTexture() {
        switch (this.getStage()) {
            case SPROUT:
                return "sunflower_01";
            case SMALL:
                return "sunflower_02";
            case LARGE:
                return "sunflower_03";
        }
        return null; //this should never occur
    }

    @Override
    public void setupLoot() {
        lootRarity = new HashMap<>();

        lootRarity.put(new LootWrapper("sunflower_seed", 2, 4), 0.7);
        lootRarity.put(new LootWrapper("water_seed", 2, 4), 0.2);
        lootRarity.put(new LootWrapper("ice_seed", 2, 4), 0.1);

        checkLootRarity();
    }

    

}
