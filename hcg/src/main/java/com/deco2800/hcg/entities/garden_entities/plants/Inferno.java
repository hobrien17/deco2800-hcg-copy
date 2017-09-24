package com.deco2800.hcg.entities.garden_entities.plants;

import java.util.HashMap;

import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.ItemManager;

/**
 * Represents the Inferno plant which drops random loot
 *
 * @author Reilly Lundin
 */
public class Inferno extends AbstractGardenPlant {

    /**
     * Creates a new inferno plant in the given pot
     * @param master the pot to associate the plant with
     */
    public Inferno(Pot master) {
        super(master, "inferno", 59);
    }

    @Override
    public String getThisTexture() {
        switch (this.getStage()) {
            case SPROUT:
                return "inferno_01";
            case SMALL:
                return "inferno_02";
            case LARGE:
                return "inferno_03";
        }
        return null;

    }

    @Override
    public void setupLoot() {
        lootRarity = new HashMap<>();

        lootRarity.put("fire_seed", 1.0);

        checkLootRarity();
    }

}
