package com.deco2800.hcg.entities.garden_entities.plants;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.deco2800.hcg.entities.garden_entities.seeds.SunflowerSeed;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.ItemManager;

/**
 * Represents a basic plant which drops basic loot
 *
 * @author Henry O'Brien
 */
public class Sunflower extends AbstractGardenPlant {

    public Sunflower(Pot master) {
        super(master);
        this.advanceStage();
        this.advanceStage();
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
    public void onTick(long gameTickCount) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setupLoot() {
        lootRarity = new HashMap<>();

        lootRarity.put("sunflower_seed", 1.0);

        double sum = 0.0;
        for (Double rarity : lootRarity.values()) {
            if (rarity < 0.0 || rarity > 1.0) {
                LOGGER.error("Rarity should be between 0 and 1");
            }
            sum += rarity;
        }
        if (sum != 1.0) {
            LOGGER.warn("Total rarity should be 1");
        }
    }

    @Override
    public Item[] loot() {
        Item[] arr = new Item[1];
        arr[0] = ItemManager.getNew(randItem());

        return arr;
    }

}
