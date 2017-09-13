package com.deco2800.hcg.entities.enemy_entities;

import com.deco2800.hcg.entities.Bullet;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.ItemManager;
import com.deco2800.hcg.managers.StopwatchManager;
import com.deco2800.hcg.weapons.WeaponBuilder;
import com.deco2800.hcg.weapons.WeaponType;


import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class MushroomTurret extends Enemy implements Observer {

    int seconds;
    int range;

    /**
     * Constructor for the MushroomTurret class. Creates a new turret at the given
     * position.
     *
     * @param posX the x position
     * @param posY the y position
     * @param posZ the x position
     * @param ID the ID of the MushroomTurret Enemy
     */
    public MushroomTurret(float posX, float posY, float posZ, int ID) {
        super(posX, posY, posZ, 0.3f, 0.3f, 1, false, 1000, 5, ID);
        //testing with tower sprite
        this.setTexture("tower");
        this.level = 1;
        seconds = 0;
        range = 15 * this.level;
        StopwatchManager manager = (StopwatchManager) GameManager.get().getManager(StopwatchManager.class);
        manager.addObserver(this);
        // weapon not working
        this.enemyWeapon = new WeaponBuilder()
                .setWeaponType(WeaponType.MACHINEGUN)
                .setUser(this)
                .setCooldown(50)
                .setTexture("battle_seed")
                .build();
    }

    public void update(Observable o, Object arg) {
        switch (seconds%5){
            case 0: // set turret phase 1 this.setTexture()
                break;
            case 1: // set turret phase 2 this.setTexture();
                break;
            case 2: // set turret phase 3 this.setTexture();
                break;
            case 3: // set turret phase 4 this.setTexture();
                break;
            case 4: // set turret phase 5 this.setTexture();
                Bullet bullet1 = new Bullet(this.getPosX(), this.getPosY(), this.getPosZ(),
                        this.getPosX() + range, this.getPosY(), this.getPosZ(), this, 1);
                GameManager.get().getWorld().addEntity(bullet1);
                Bullet bullet2 = new Bullet(this.getPosX(), this.getPosY(), this.getPosZ(),
                        Math.max(0,this.getPosX() - range), this.getPosY(), this.getPosZ(), this, 1);
                GameManager.get().getWorld().addEntity(bullet2);
                Bullet bullet3 = new Bullet(this.getPosX(), this.getPosY(), this.getPosZ(),
                        this.getPosX(), this.getPosY() + range, this.getPosZ(), this, 1);
                GameManager.get().getWorld().addEntity(bullet3);
                Bullet bullet4 = new Bullet(this.getPosX(), this.getPosY(), this.getPosZ(),
                        this.getPosX(), Math.max(0,this.getPosY() - range), this.getPosZ(), this, 1);
                GameManager.get().getWorld().addEntity(bullet4);
                break;

            // NEED TO IMPLEMENT WHAT TO DO WHEN BULLETS HIT PLAYER
        }
        seconds++;
    }

    @Override
    public void setupLoot() {
        lootRarity = new HashMap<>();

        lootRarity.put("fire_seed", 1.0);

        checkLootRarity();
    }

    @Override
    public Item[] loot() {
        Item[] arr = new Item[1];
        arr[0] = ((ItemManager)GameManager.get().getManager(ItemManager.class)).getNew(this.randItem());
        return arr;
    }
}

