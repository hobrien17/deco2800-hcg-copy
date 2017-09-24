package com.deco2800.hcg.entities.garden_entities.plants;

import java.util.HashMap;

import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.ItemManager;

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

		super(master, "lily", 30);
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
	protected void setupLoot() {
		lootRarity = new HashMap<>();

        lootRarity.put("water_seed", 1.0);

        checkLootRarity();
	}
}
