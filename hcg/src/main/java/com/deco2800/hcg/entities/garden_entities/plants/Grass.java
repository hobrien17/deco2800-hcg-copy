package com.deco2800.hcg.entities.garden_entities.plants;

import java.util.HashMap;

import com.deco2800.hcg.items.lootable.LootWrapper;

/**
 * Represents the Grass plant which drops random loot
 *
 * @author Reilly Lundin
 */
public class Grass extends AbstractGardenPlant {

    /**
     * Creates a new Grass plant in the given pot
     * @param master the pot to associate the plant with
     */
    public Grass(Pot master) {
        super(master, "grass", 15);
    }

    @Override
    public String getThisTexture() {
        switch (this.getStage()) {
            case SPROUT:
                return "grass_01";
            case SMALL:
                return "grass_02";
            case LARGE:
                return "grass_03";
            default:
                return null;
        }
    }

    @Override
    public void setupLoot() {
        lootRarity = new HashMap<>();

        lootRarity.put(new LootWrapper("grass_seed", 5, 10), 0.7);
        lootRarity.put(new LootWrapper("sunflower_seed", 5, 15), 0.2);
        lootRarity.put(new LootWrapper("explosive_seed", 2, 5), 0.1);

        checkLootRarity();
    }

}
