package com.deco2800.hcg.entities.garden_entities.plants;

import java.util.HashMap;

import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.ItemManager;

/**
 * Represents the Ice plant which drops random loot
 *
 * @author Reilly Lundin
 */
public class Ice extends AbstractGardenPlant {

    public Ice(Pot master) {
        super(master, 100);
    }

    @Override
    public String getThisTexture() {
        switch (this.getStage()) {
            case SPROUT:
                return null;
            case SMALL:
                return null;
            case LARGE:
                return null;
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
        arr[0] = ItemManager.getNew(randItem());

        return arr;
    }

}
