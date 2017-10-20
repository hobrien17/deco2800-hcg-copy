package com.deco2800.hcg.trading;

import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.entities.garden_entities.seeds.Seed;
import com.deco2800.hcg.items.WeaponItem;
import com.deco2800.hcg.weapons.Weapon;
import com.deco2800.hcg.weapons.WeaponBuilder;
import com.deco2800.hcg.weapons.WeaponType;

/**
 * General Shop class that implements all the methods of the shop
 *
 * @author William Cornish / Team 1
 */
public class GardenShop extends Shop{
    public GardenShop() {

        Weapon starfall = new WeaponBuilder().setWeaponType(WeaponType.STARGUN).setUser(player).setRadius(0.7).build();
        Item starfallItem = new WeaponItem(starfall, "starfall", 0);
        addStock(starfallItem);

        Item testSeedExplosive = new Seed(Seed.Type.EXPLOSIVE);
        testSeedExplosive.addToStack(4);
        addStock(testSeedExplosive);
        Item testSeedFire = new Seed(Seed.Type.FIRE);
        testSeedFire.addToStack(4);
        addStock(testSeedFire);
        Item testSeedGrass = new Seed(Seed.Type.GRASS);
        testSeedGrass.addToStack(4);
        addStock(testSeedGrass);
        Item testSeedIce = new Seed(Seed.Type.ICE);
        testSeedIce.addToStack(4);
        addStock(testSeedIce);
        Item testSeedWater = new Seed(Seed.Type.WATER);
        testSeedWater.addToStack(4);
        addStock(testSeedWater);
    }
}
