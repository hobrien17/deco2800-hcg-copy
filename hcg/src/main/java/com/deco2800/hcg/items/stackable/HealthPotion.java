package com.deco2800.hcg.items.stackable;

import com.deco2800.hcg.entities.Character;
import com.deco2800.hcg.items.Item;

public class HealthPotion extends ConsumableItem {
    //Super simple example class of a health potion
    int healthAmount;

    public HealthPotion(int amount) {
        healthAmount = amount;
        this.baseValue = 10;
        this.itemWeight = 1;
        this.itemName = "Health Potion";
        this.texture = "red_potion";
        this.currentStackSize = 1;
        this.maxStackSize = 10;
    }
    @Override
    public void consume(Character character) {
        //TODO: Update character health
        //character.updateHealth(healthAmount)
        System.out.println("Health Updated!");

    }

    @Override
    public boolean isEquippable() {
        return false;
    }

    @Override
    public boolean isTradable() {
        return false;
    }

    @Override
    public String getName() {
        return String.format("Name: %s\n Type: %s\nDetails: %s\n", this.itemName, "+"+this.healthAmount+"HP",
                "Gives you a boost of 100HP!");
    }

    @Override
    public boolean sameItem(Item item) {
        return item instanceof HealthPotion && this.itemName == ((HealthPotion) item).itemName;
    }


}
