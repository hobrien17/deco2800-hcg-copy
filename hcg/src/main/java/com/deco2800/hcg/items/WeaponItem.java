package com.deco2800.hcg.items;

import com.deco2800.hcg.weapons.Weapon;

public class WeaponItem extends SingleItem {
    private Weapon weapon;
    
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
