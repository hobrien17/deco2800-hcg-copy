package com.deco2800.hcg.entities.garden_entities.plants;

import java.util.HashMap;

import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.lootable.LootWrapper;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.ItemManager;

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
        super(master, "cactus", 20);
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

        lootRarity.put(new LootWrapper("explosive_seed", 2, 5), 0.7);
        lootRarity.put(new LootWrapper("fire_seed", 5, 10), 0.2);
        lootRarity.put(new LootWrapper("grass_seed", 5, 10), 0.1);
        
        checkLootRarity();
    }

}
