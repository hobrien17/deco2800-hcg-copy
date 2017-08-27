package com.deco2800.hcg.items;

import com.deco2800.hcg.weapons.Weapon;

/**
 * The WeaponItem class represents inventory items that have weapons
 * as their basis. (e.g the same weapon basis can be used to make
 * multiple different items)
 */
public class WeaponItem extends SingleItem {
    private Weapon weapon;

    /**
     * Constructor for the WeaponItem class. It creates a new WeaponItem
     * with the given weapon, name and weight
     * @param weapon the weapon to make an item of
     * @param name the name of the item
     * @param weight the weight of the item
     */
    public WeaponItem(Weapon weapon, String name, int weight) {
        this.weapon = weapon;
        this.itemName = name;
        this.itemWeight = weight;
    }
    
    @Override
    public boolean isWearable() {
        return false;
    }
    
    @Override
    public boolean isEquippable() {
        return true;
    }
    
    @Override
    public boolean isTradable() {
        return true;
    }
    
    /**
     * Returns the weapon that this item contains.
     * @return The weapon that this item contains.
     */
    public Weapon getWeapon() {
        return this.weapon;
    }
    
    @Override
    public boolean sameItem(Item item) throws IllegalArgumentException {
        return (item instanceof WeaponItem && 
                item.getName() == this.itemName && 
                this.weapon.equals(((WeaponItem)item).getWeapon()));
    }
}
