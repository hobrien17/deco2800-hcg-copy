package com.deco2800.hcg.entities.garden_entities.plants;

import java.util.HashMap;

import com.deco2800.hcg.items.lootable.LootWrapper;

/**
 * Represents the Inferno plant which drops random loot
 *
 * @author Reilly Lundin
 */
public class Inferno extends AbstractGardenPlant {

    /**
     * Creates a new inferno plant in the given pot
     * @param master the pot to associate the plant with
     */
    public Inferno(Pot master) {
        super(master, "inferno", 1800); //~15 real-life minutes
    }

    @Override
    public String getThisTexture() {
        switch (this.getStage()) {
            case SPROUT:
                return "inferno_01";
            case SMALL:
                return "inferno_02";
            case LARGE:
                return "inferno_03";
            default:
                return null;
        }
    }

    @Override
    public void setupLoot() {
        lootRarity = new HashMap<>();

        lootRarity.put(new LootWrapper("fire_seed", 7, 9), 0.5);
        lootRarity.put(new LootWrapper("grass_seed", 6, 9), 0.2);
        lootRarity.put(new LootWrapper("shotgun", 1, 1), 0.1);
        lootRarity.put(new LootWrapper("multigun", 1, 1), 0.1);
        lootRarity.put(new LootWrapper("trowel", 1, 1), 0.1);

        checkLootRarity();
    }

}
