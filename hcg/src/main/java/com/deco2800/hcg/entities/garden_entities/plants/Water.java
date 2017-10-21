package com.deco2800.hcg.entities.garden_entities.plants;

import java.util.HashMap;

import com.deco2800.hcg.items.lootable.LootWrapper;

/**
 * Represents the Water plant which drops random loot
 *
 * @author Reilly Lundin
 */
public class Water extends AbstractGardenPlant {

	/**
	 * Creates a new Water plant in the given pot
	 * @param master the pot to associate the plant to
	 */
	public Water(Pot master) {

		super(master, "lily", 1200); //~10 real-life minutes
	}

	@Override
	public String getThisTexture() {
		switch (this.getStage()) {
		case SPROUT:
			return "lily_01";
		case SMALL:
			return "lily_02";
		case LARGE:
			return "lily_03";
		}
		return null;
	}

	@Override
	void setupLoot() {
		lootRarity = new HashMap<>();

		lootRarity.put(new LootWrapper("water_seed", 5, 10), 0.7);
        lootRarity.put(new LootWrapper("ice_seed", 5, 10), 0.2);
        lootRarity.put(new LootWrapper("explosive_seed", 5, 10), 0.1);

        checkLootRarity();
	}
}
