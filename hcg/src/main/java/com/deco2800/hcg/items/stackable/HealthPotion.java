package com.deco2800.hcg.items.stackable;

import java.util.ArrayList;

import com.deco2800.hcg.entities.Character;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.ItemRarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HealthPotion extends ConsumableItem {
    //Super simple example class of a health potion
    int healthAmount;

    private static final Logger LOGGER = LoggerFactory.getLogger(HealthPotion.class);

    public HealthPotion(int amount) {
        healthAmount = amount;
        this.baseValue = 10;
        this.itemWeight = 1;
        this.itemName = "Sausage";
        this.texture = "bunnings_snag";
        this.currentStackSize = 1;
        this.maxStackSize = 10;
    }

    @Override
    public void consume(Character character) {
        //TODO: Update character health
        ((Player)character).setHealthCur(character.getHealthCur() + 100);
        LOGGER.info("Health Updated!");
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
        return this.itemName;
    }

    @Override
    public boolean sameItem(Item item) {
        return item instanceof HealthPotion && this.healthAmount == ((HealthPotion) item).healthAmount;
    }

    @Override
    public Item copy() {
        HealthPotion newPotion = new HealthPotion(healthAmount);
        newPotion.setStackSize(this.getStackSize());
        return newPotion;
    }
    
    @Override
    public ArrayList<String> getInformation() {
        ArrayList<String> list = new ArrayList<>();
        list.add(String.format("+%s HP", this.healthAmount));
        return list;
    }
    
    @Override
    public ItemRarity getRarity() {
        return ItemRarity.UNCOMMON;
    }
}
