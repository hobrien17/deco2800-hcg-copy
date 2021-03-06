package com.deco2800.hcg.items.stackable;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.deco2800.hcg.entities.Character;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.ItemRarity;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.ShaderManager;

public class MagicMushroom extends ConsumableItem {
    //Super simple example class of a health potion

    public MagicMushroom() {
        this.baseValue = 100;
        this.itemWeight = 5;
        this.itemName = "Magic Mushroom";
        this.texture = "magic_mushroom";
        this.currentStackSize = 1;
        this.maxStackSize = 5;
    }
    @Override
    public void consume(Character character) {
        // Update shader to run the magic mushroom shader for 4 in game hours. Also fully heals you
        ShaderManager shaders = (ShaderManager) GameManager.get().getManager(ShaderManager.class);
        shaders.setCustom(0.0F, 0.6F, 0.6F, new Color(1F, 0F, 1F, 1F), 35);
        ((Player)character).setHealthCur(character.getHealthMax());
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
        return item instanceof MagicMushroom;
    }

    @Override
    public Item copy() {
        MagicMushroom newShroom = new MagicMushroom();
        newShroom.setStackSize(this.getStackSize());
        return newShroom;
    }

    @Override
    public ArrayList<String> getInformation() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Fully heals you, with a catch...");
        return list;
    }

    @Override
    public ItemRarity getRarity() {
        return ItemRarity.RARE;
    }
}

