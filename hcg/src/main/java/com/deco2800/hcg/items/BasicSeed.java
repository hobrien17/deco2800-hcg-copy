package com.deco2800.hcg.items;

public class BasicSeed extends StackableItem {

    public BasicSeed() {
        this.baseValue = 1;
        this.itemName = "Basic Seed";
        this.currentStackSize = 1;
        this.maxStackSize = 256;
        this.texture = "generic.png";
        this.itemWeight = 0;
    }

    @Override
    public boolean isEquippable() {
        return false;
    }

    @Override
    public boolean isTradable() {
        return true;
    }
}
