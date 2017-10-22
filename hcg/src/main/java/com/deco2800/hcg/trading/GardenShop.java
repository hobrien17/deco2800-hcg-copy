package com.deco2800.hcg.trading;

import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.entities.garden_entities.seeds.Seed;
import com.deco2800.hcg.items.tools.BugSpray;
import com.deco2800.hcg.items.tools.Fertiliser;
import com.deco2800.hcg.items.tools.Hoe;
import com.deco2800.hcg.items.tools.Trowel;

/**
 * General Shop class that implements all the methods of the shop
 *
 * @author William Cornish / Team 1
 */
public class GardenShop extends Shop{
    public GardenShop() {

        Item hoe = new Hoe();
        addStock(hoe);
        Item fertiliser = new Fertiliser();
        fertiliser.setStackSize(10);
        addStock(fertiliser);
        Item bugSpray = new BugSpray();
        bugSpray.setStackSize(5);
        addStock(bugSpray);
        Item trowel = new Trowel();
        addStock(trowel);

        Item seedExplosive = new Seed(Seed.Type.EXPLOSIVE);
        seedExplosive.addToStack(4);
        addStock(seedExplosive);
        Item seedFire = new Seed(Seed.Type.FIRE);
        seedFire.addToStack(4);
        addStock(seedFire);
        Item seedGrass = new Seed(Seed.Type.GRASS);
        seedGrass.addToStack(4);
        addStock(seedGrass);
        Item seedIce = new Seed(Seed.Type.ICE);
        seedIce.addToStack(4);
        addStock(seedIce);
        Item seedWater = new Seed(Seed.Type.WATER);
        seedWater.addToStack(4);
        addStock(seedWater);
    }
}
