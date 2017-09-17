package com.deco2800.hcg.entities.enemy_entities;

import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.ItemManager;
import com.deco2800.hcg.managers.StopwatchManager;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class Hedgehog extends Enemy implements Observer {

    int seconds;
    int range;

    /**
     * Constructor for the Hedgehog class. Creates a new hedgehog at the given
     * position.
     *
     * @param posX the x position
     * @param posY the y position
     * @param posZ the x position
     * @param ID the ID of the Hedgehog Enemy
     */
    public Hedgehog(float posX, float posY, float posZ, int ID) {
        super(posX, posY, posZ, 0.3f, 0.3f, 1, false, 1000, 5, ID);
        //this.setTexture();
        this.level = 1;
        seconds = 0;
        range = 20 * this.level;
        StopwatchManager manager = (StopwatchManager) GameManager.get().getManager(StopwatchManager.class);
        manager.addObserver(this);

    }

    @Override
    public void update(Observable o, Object arg) {
        float distance = this.distance(playerManager.getPlayer());
        if (seconds == 5 && distance < range){
            // roll at player
            seconds = 0;
        } else {
            newPos = this.getRandomPos();
            this.detectCollision();//Detect collision.
            this.moveAction();//Move enemy to the position in Box3D.
            
            if (seconds == 5){
                seconds = 0;
            } else {
                seconds++;
            }
        }
    }

    @Override
    public void setupLoot() {
        lootRarity = new HashMap<>();

        lootRarity.put("explosive_seed", 1.0);

        checkLootRarity();
    }

    @Override
    public Item[] loot() {
        Item[] arr = new Item[1];
        arr[0] = ((ItemManager)GameManager.get().getManager(ItemManager.class)).getNew(this.randItem());
        return arr;
    }

}
