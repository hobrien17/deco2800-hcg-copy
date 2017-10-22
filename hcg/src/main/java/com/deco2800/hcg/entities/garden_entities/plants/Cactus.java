package com.deco2800.hcg.entities.garden_entities.plants;

import java.util.HashMap;

import com.deco2800.hcg.items.lootable.LootWrapper;

/**
 * Represents the Cactus plant which drops random loot
 *
 * @author Reilly Lundin
 */
public class Cactus extends AbstractGardenPlant {

    /**
     * Creates a new Cactus plant in the given pot
     * @param master the pot to associate the plant with
     */
    public Cactus(Pot master) {
        super(master, "cactus", 1200); //~10 real-life minutes
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
			default:
				return null;
		}
    }

    @Override
    public void setupLoot() {
        lootRarity = new HashMap<>();

        lootRarity.put(new LootWrapper("explosive_seed", 15, 20), 0.45);
        lootRarity.put(new LootWrapper("sunflower_seed", 20, 30), 0.1);
        lootRarity.put(new LootWrapper("grass_seed", 5, 15), 0.1);
        lootRarity.put(new LootWrapper("fire_seed", 5, 15), 0.1);
        lootRarity.put(new LootWrapper("water_seed", 5, 15), 0.1);
        lootRarity.put(new LootWrapper("ice_seed", 5, 15), 0.1);
        lootRarity.put(new LootWrapper("starfall", 1, 1), 0.05);
        
        checkLootRarity();
    }

}
