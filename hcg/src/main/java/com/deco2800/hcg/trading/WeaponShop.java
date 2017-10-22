package com.deco2800.hcg.trading;

import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.WeaponItem;
import com.deco2800.hcg.weapons.Weapon;
import com.deco2800.hcg.weapons.WeaponBuilder;
import com.deco2800.hcg.weapons.WeaponType;

/**
 * Weapon shop that sell weapons
 *
 * @author Taari Meiners / Team 1
 */
public class WeaponShop extends Shop{
    public WeaponShop() {
        Weapon machinegunWeapon = new WeaponBuilder().setWeaponType(WeaponType.MACHINEGUN)
                .setRadius(0.7).build();
        Item machineGun = new WeaponItem(machinegunWeapon, "Machine Gun", 10);
        addStock(machineGun);
        Weapon multigunWeapon = new WeaponBuilder().setWeaponType(WeaponType.MULTIGUN)
                .setRadius(0.7).build();
        Item multiGun = new WeaponItem(multigunWeapon, "Multigun", 10);
        addStock(multiGun);
        Weapon shotgunWeapon = new WeaponBuilder().setWeaponType(WeaponType.SHOTGUN)
                .setRadius(0.7).build();
        Item shotgun = new WeaponItem(shotgunWeapon, "Shotgun", 10);
        addStock(shotgun);
        Weapon stargunWeapon = new WeaponBuilder().setWeaponType(WeaponType.STARGUN)
                .setRadius(0.7).build();
        Item stargun = new WeaponItem(stargunWeapon, "Stargun", 10);
        addStock(stargun);
    }
}
