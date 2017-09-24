package com.deco2800.hcg.entities.garden_entities.plants;

import java.util.HashMap;

import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.ItemManager;

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
		}
		return null;

	}

	@Override
	public void setupLoot() {
		lootRarity = new HashMap<>();

		lootRarity.put("ice_seed", 1.0);

		checkLootRarity();
	}

	@Override
	public Item[] loot() {
		Item[] arr = new Item[1];
		arr[0] = ((ItemManager)GameManager.get().getManager(ItemManager.class)).getNew(this.randItem());

		return arr;
	}

}
