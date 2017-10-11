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
		super(master, "ice", 45);
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

		lootRarity.put(new LootWrapper("ice_seed", 2, 5), 0.7);
        lootRarity.put(new LootWrapper("water_seed", 5, 10), 0.2);
        lootRarity.put(new LootWrapper("sunflower_seed", 5, 15), 0.1);

		checkLootRarity();
	}

}
