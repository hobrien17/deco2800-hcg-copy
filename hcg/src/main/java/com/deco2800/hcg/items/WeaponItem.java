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
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null.");
        }
        this.weapon = weapon;
        this.itemName = name;
        this.itemWeight = weight;
        this.baseValue = 10;
        this.texture = "red_potion";
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

    /**
     * Sets a the WeaponItem's weapon reference to some new weapon.
     * @param newWeapon the new weapon to be set to the WeaponItem.
     */
    public void setWeapon(Weapon newWeapon) {
        if (newWeapon == null) {
            throw new IllegalArgumentException("New weapon cannot be null.");
        }
        weapon = newWeapon;
    }
    
    @Override
    public boolean sameItem(Item item) throws IllegalArgumentException {
        return item instanceof WeaponItem && 
               item.getName().equals(this.itemName) &&
               this.weapon.equals(((WeaponItem)item).getWeapon());
    }

    @Override
    public Item copy() {
        return new WeaponItem(weapon, itemName, itemWeight);
    }
}
