package com.deco2800.hcg.entities.garden_entities.plants;

import java.util.HashMap;

import com.deco2800.hcg.items.Item;
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
        super(master, 20);
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

        lootRarity.put("rock_seed", 1.0);

        checkLootRarity();
    }

    @Override
    public Item[] loot() {
        Item[] arr = new Item[1];
        arr[0] = ItemManager.getNew(randItem());

        return arr;
    }

}
