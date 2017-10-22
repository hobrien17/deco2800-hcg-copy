package com.deco2800.hcg.trading;

import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.entities.garden_entities.seeds.Seed;
import com.deco2800.hcg.items.stackable.Key;
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

        Item fertiliser = new Fertiliser();
        fertiliser.setStackSize(20);
        addStock(fertiliser);
        Item bugSpray = new BugSpray();
        bugSpray.setStackSize(10);
        addStock(bugSpray);
        Item trowel = new Trowel();
        trowel.setStackSize(10);
        addStock(trowel);
        Item hoe = new Hoe();
        hoe.setStackSize(10);
        addStock(hoe);
        Item key = new Key();
        key.setStackSize(22);
        addStock(key);

        Item seedExplosive = new Seed(Seed.Type.EXPLOSIVE);
        seedExplosive.addToStack(4);
        addStock(seedExplosive);
        Item seedFire = new Seed(Seed.Type.FIRE);
        seedFire.addToStack(4);
        addStock(seedFire);
        Item seedIce = new Seed(Seed.Type.ICE);
        seedIce.addToStack(9);
        addStock(seedIce);
        Item seedWater = new Seed(Seed.Type.WATER);
        seedWater.addToStack(9);
        addStock(seedWater);
        Item seedGrass = new Seed(Seed.Type.GRASS);
        seedGrass.addToStack(9);
        addStock(seedGrass);
    }
}
