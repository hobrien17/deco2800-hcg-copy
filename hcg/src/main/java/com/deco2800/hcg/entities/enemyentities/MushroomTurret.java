package com.deco2800.hcg.entities.enemyentities;

import com.deco2800.hcg.entities.bullets.Bullet;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.StopwatchManager;
import com.deco2800.hcg.weapons.WeaponBuilder;
import com.deco2800.hcg.weapons.WeaponType;


import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class MushroomTurret extends Enemy implements Observer {

    int seconds;
    int range;
    StopwatchManager manager;

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
        this.setTexture("mushroom");
        this.level = 1;
        seconds = 0;
        range = 15 * this.level;
        manager = (StopwatchManager) GameManager.get().getManager(StopwatchManager.class);
        manager.addObserver(this);
        // weapon not working
        this.enemyWeapon = new WeaponBuilder()
                .setWeaponType(WeaponType.MACHINEGUN)
                .setUser(this)
                .setCooldown(50)
                .setTexture("battle_seed")
                .build();
    }

    @Override
    public void setupLoot() {
        lootRarity = new HashMap<>();

        lootRarity.put("fire_seed", 1.0);

        checkLootRarity();
    }

    /**
     * Removes the observer from the stopwatch manager.
     */
    public void removeObserver() {
        manager.deleteObserver(this);
    }

    /**
     * Creates bullets and shoots them in 8 different directions.
     */
    public void turretShoot() {
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
        Bullet bullet5 = new Bullet(this.getPosX(), this.getPosY(), this.getPosZ(),
                this.getPosX() + range, this.getPosY() + range, this.getPosZ(), this, 1);
        GameManager.get().getWorld().addEntity(bullet5);
        Bullet bullet6 = new Bullet(this.getPosX(), this.getPosY(), this.getPosZ(),
                Math.max(0,this.getPosX() - range), this.getPosY() + range , this.getPosZ(), this, 1);
        GameManager.get().getWorld().addEntity(bullet6);
        Bullet bullet7 = new Bullet(this.getPosX(), this.getPosY(), this.getPosZ(),
                this.getPosX() + range, Math.max(0,this.getPosY() - range), this.getPosZ(), this, 1);
        GameManager.get().getWorld().addEntity(bullet7);
        Bullet bullet8 = new Bullet(this.getPosX(), this.getPosY(), this.getPosZ(),
                Math.max(0,this.getPosX() - range), Math.max(0,this.getPosY() - range), this.getPosZ(), this, 1);
        GameManager.get().getWorld().addEntity(bullet8);
    }

    /**
     * Updates the turret sprites and shoots every 5 seconds.
     *
     * @param o
     * 			the Observable object calling the update method (should be an instance of StopwatchManager)
     * @param arg
     * 			the argument passed by the Observable object (should be the stopwatch's current time)
     */
    @Override
    public void update(Observable o, Object arg) {
        switch (seconds%6){
            case 0: // set turret phase 1
                this.setTexture("mushroom");
                break;
            case 1: // set turret phase 2
                break;
            case 2: // set turret phase 3
                break;
            case 3: // set turret phase 4
                this.setTexture("tower");
                break;
            case 4:
                this.turretShoot();
                break;
            default:
                break;

        }
        seconds++;
    }


}

