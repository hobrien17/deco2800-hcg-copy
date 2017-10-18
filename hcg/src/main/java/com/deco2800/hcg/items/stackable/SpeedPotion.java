package com.deco2800.hcg.items.stackable;

import com.deco2800.hcg.entities.Character;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.ItemRarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class SpeedPotion extends ConsumableItem {
    int staminaAmount;

    private static final Logger LOGGER = LoggerFactory.getLogger(HealthPotion.class);

    public SpeedPotion() {
        this.baseValue = 15;
        this.itemWeight = 3;
        this.itemName = "Speed Potion";
        this.texture = "purple_potion";
        this.currentStackSize = 1;
        this.maxStackSize = 5;
    }
    @Override
    public void consume(Character character) {
        ((Player)character).setSpeed(0.6f);
        LOGGER.info("Stamina Updated!");
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
        return item instanceof SpeedPotion ;
    }

    @Override
    public Item copy() {
        SpeedPotion newPotion = new SpeedPotion();
        newPotion.setStackSize(this.getStackSize());
        return newPotion;
    }

    @Override
    public ArrayList<String> getInformation() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Gives you boosted speed\nuntil you sprint or are slowed down");
        return list;
    }

    @Override
    public ItemRarity getRarity() {
        return ItemRarity.UNCOMMON;
    }
}

