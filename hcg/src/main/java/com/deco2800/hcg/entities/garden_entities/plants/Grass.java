package com.deco2800.hcg.entities.garden_entities.plants;

import java.util.HashMap;

import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.ItemManager;

/**
 * Represents the Grass plant which drops random loot
 *
 * @author Reilly Lundin
 */
public class Grass extends AbstractGardenPlant {

    public Grass(Pot master) {
        super(master, 600);
    }

    @Override
    public String getThisTexture() {
        switch (this.getStage()) {
            case SPROUT:
                return "grass_01";
            case SMALL:
                return "grass_02";
            case LARGE:
                return "grass_03";
        }
        return null;

    }

    @Override
    public void setupLoot() {
        lootRarity = new HashMap<>();

        lootRarity.put("grass_seed", 1.0);

        checkLootRarity();
    }

    @Override
    public Item[] loot() {
        Item[] arr = new Item[1];
        arr[0] = ItemManager.getNew(randItem());

        return arr;
    }

}
