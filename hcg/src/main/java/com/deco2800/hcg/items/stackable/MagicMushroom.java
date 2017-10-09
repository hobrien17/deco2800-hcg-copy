package com.deco2800.hcg.items.stackable;

import com.badlogic.gdx.graphics.Color;
import com.deco2800.hcg.entities.Character;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.ItemRarity;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.ShaderManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.security.provider.SHA;

import java.util.ArrayList;

public class MagicMushroom extends ConsumableItem {
    //Super simple example class of a health potion

    private static final Logger LOGGER = LoggerFactory.getLogger(MagicMushroom.class);

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
        shaders.setCustom(0.3F, 0.3F, 0.4F, new Color(255, 153, 255, 50), 35);
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

