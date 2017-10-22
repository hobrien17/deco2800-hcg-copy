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

		super(master, "lily", 840); //~7 real-life minutes
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
	public void setupLoot() {
		lootRarity = new HashMap<>();

		lootRarity.put(new LootWrapper("water_seed", 10, 15), 0.4);
        lootRarity.put(new LootWrapper("ice_seed", 5, 10), 0.2);
        lootRarity.put(new LootWrapper("snag", 1, 2), 0.2);
        lootRarity.put(new LootWrapper("sausage", 1, 2), 0.1);
        lootRarity.put(new LootWrapper("bug_spray", 1, 1), 0.1);

        checkLootRarity();
	}
}
