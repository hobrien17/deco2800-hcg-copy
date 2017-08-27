package com.deco2800.hcg.entities.garden_entities.plants;

import java.util.HashMap;
import java.util.Observable;

import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.ItemManager;

/**
 * Represents a basic plant which drops basic loot
 *
 * @author Henry O'Brien
 */
public class Sunflower extends AbstractGardenPlant {

    /**
     * Creates a new Sunflower plant in the given pot
     * @param master the pot to associate the plant with
     */
    public Sunflower(Pot master) {

        super(master, 10);
    }

    @Override
    public String getThisTexture() {
        switch (this.getStage()) {
            case SPROUT:
                return "sunflower_01";
            case SMALL:
                return "sunflower_02";
            case LARGE:
                return "sunflower_03";
            default:
                return null;
        }

    }

    @Override
    public void setupLoot() {
        lootRarity = new HashMap<>();

        lootRarity.put("sunflower_seed", 1.0);

        checkLootRarity();
    }

    @Override
    public Item[] loot() {
        Item[] arr = new Item[1];
        arr[0] = ((ItemManager)GameManager.get().getManager(ItemManager.class)).getNew(this.randItem());

        return arr;
    }

}
