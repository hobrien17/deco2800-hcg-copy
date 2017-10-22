package com.deco2800.hcg.entities.garden_entities.plants;

import java.util.HashMap;

import com.deco2800.hcg.items.lootable.LootWrapper;

/**
 * Represents the Ice plant which drops random loot
 *
 * @author Reilly Lundin
 */
public class Ice extends AbstractGardenPlant {

	/**
	 * Creates a new Ice plant in the given pot
	 * 
	 * @param master
	 *            the pot to associate the plant with
	 */
	public Ice(Pot master) {
		super(master, "ice", 960); //8 minutes
	}

	@Override
	public String getThisTexture() {
		switch (this.getStage()) {
		case SPROUT:
			return "ice_01"; // sprites currently not implemented
		case SMALL:
			return "ice_02";
		case LARGE:
			return "ice_03";
		default:
			return null;
		}
	}

	@Override
	public void setupLoot() {
		lootRarity = new HashMap<>();

		lootRarity.put(new LootWrapper("ice_seed", 15, 20), 0.5);
        lootRarity.put(new LootWrapper("water_seed", 5, 15), 0.3);
        lootRarity.put(new LootWrapper("key", 1, 1), 0.1);
        lootRarity.put(new LootWrapper("hoe", 1, 1), 0.1);

		checkLootRarity();
	}

}
