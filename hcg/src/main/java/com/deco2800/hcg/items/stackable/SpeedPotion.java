package com.deco2800.hcg.items.stackable;

import com.deco2800.hcg.entities.Character;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.ItemRarity;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.StopwatchManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class SpeedPotion extends ConsumableItem implements Observer {
    int staminaAmount;
    private static final Logger LOGGER = LoggerFactory.getLogger(HealthPotion.class);
    int startTime;
    int endTime;
    Character usingCharacter;
    StopwatchManager manager;

    public SpeedPotion() {
        this.baseValue = 15;
        this.itemWeight = 3;
        this.itemName = "Speed Potion";
        this.texture = "purple_potion";
        this.currentStackSize = 1;
        this.maxStackSize = 5;
        startTime = 0;
        endTime = 0;
    }
    @Override
    public void consume(Character character) {
        //((Player)character).setSpeed(0.6f);
        usingCharacter = character;
        ((Player)character).setStaminaCur(character.getStaminaMax());
        StopwatchManager manager = (StopwatchManager) GameManager.get().getManager(StopwatchManager.class);
        manager.addObserver(this);
        LOGGER.info("Stamina Updated!");
        startTime = (int)manager.getStopwatchTime();
        endTime = startTime + 100;
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
        list.add("Unlimited stamina for 20s");
        return list;
    }

    @Override
    public ItemRarity getRarity() {
        return ItemRarity.UNCOMMON;
    }

    @Override
    public void update(Observable o, Object arg) {
        int time = (int) (float) arg;
        endTime--;
        if (time - startTime > endTime) {
            //Time is done
            //Persons stamina is no longer going to be set to max
        } else {
            //Potion is not finished - set stamina to max
            usingCharacter.setStaminaCur(usingCharacter.getStaminaMax());
        }

    }

}

