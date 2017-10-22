package com.deco2800.hcg.items.stackable;

import com.badlogic.gdx.graphics.Color;
import com.deco2800.hcg.entities.Character;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.ItemRarity;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.ShaderManager;
import java.util.ArrayList;

public class SmallMushroom extends ConsumableItem  {

    public SmallMushroom() {
        this.baseValue = 100;
        this.itemWeight = 5;
        this.itemName = "Small Mushroom";
        this.texture = "small_mushroom";
        this.currentStackSize = 1;
        this.maxStackSize = 5;
    }
    @Override
    public void consume(Character character) {
        // Update shader to run the magic mushroom shader for 4 in game hours. Also fully heals you
        ShaderManager shaders = (ShaderManager) GameManager.get().getManager(ShaderManager.class);
        shaders.setCustom(0.0F, 0.2F, 0.2F, new Color(150, 153, 120, 50), 250);
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
        return item instanceof SmallMushroom;
    }

    @Override
    public Item copy() {
        SmallMushroom newShroom = new SmallMushroom();
        newShroom.setStackSize(this.getStackSize());
        return newShroom;
    }

    @Override
    public ArrayList<String> getInformation() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Gives you +25HP");
        return list;
    }

    @Override
    public ItemRarity getRarity() {
        return ItemRarity.COMMON;
    }
}
