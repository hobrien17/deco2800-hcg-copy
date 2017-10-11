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
        super(master, "inferno", 59);
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

        lootRarity.put(new LootWrapper("fire_seed", 5, 10), 0.7);
        lootRarity.put(new LootWrapper("explosive_seed", 2, 5), 0.2);
        lootRarity.put(new LootWrapper("water_seed", 5, 10), 0.1);

        checkLootRarity();
    }

}
