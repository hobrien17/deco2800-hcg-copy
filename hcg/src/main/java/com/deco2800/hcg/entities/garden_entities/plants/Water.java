package com.deco2800.hcg.entities.garden_entities.plants;

import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.ItemManager;

/**
 * Represents the Water plant which drops random loot
 *
 * @author Reilly Lundin
 */
public class Water extends AbstractGardenPlant {

	public Water(Pot master) {
		super(master, 1000);
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
	public Item[] loot() {
		Item[] arr = new Item[1];
		arr[0] = ItemManager.getNew(randItem());
		
		return arr;
	}

	@Override
	void setupLoot() {
		// TODO Auto-generated method stub
		
	}
}
