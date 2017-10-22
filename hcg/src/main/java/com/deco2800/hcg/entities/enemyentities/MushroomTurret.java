package com.deco2800.hcg.entities.enemyentities;

import com.badlogic.gdx.math.Vector3;
import com.deco2800.hcg.items.lootable.LootWrapper;
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
    public MushroomTurret(float posX, float posY, float posZ, int id) {
        super(posX, posY, posZ, 1f, 1f, 1, false, 1000, 5, id, EnemyType.MUSHROOMTURRET);
        this.boss = false;
        this.setTexture("mushroom0");
        this.level = 1;
        seconds = 0;
        range = 15 * this.level;
        manager = (StopwatchManager) GameManager.get().getManager(StopwatchManager.class);
        manager.addObserver(this);
        healthMax = 20;
        healthCur = healthMax;
        this.enemyWeapon = new WeaponBuilder().setWeaponType(WeaponType.MULTIGUN).setUser(this)
                .setArc((float) Math.PI * 7 / 4).setPellets(8).setCooldown(30).setTexture("blank").build();
        enemyWeapon.updatePosition(new Vector3(this.getPosX(), this.getPosY(), this.getPosZ()));
    }

    @Override
    public void setupLoot() {
        lootRarity = new HashMap<>();

        lootRarity.put(new LootWrapper("fire_seed", 1.0f), 1.0);

        checkLootRarity();
    }

    /**
     * Removes the observer from the stopwatch manager.
     */
    public void removeObserver() {
        manager.deleteObserver(this);
    }

    /**
     * Removes the enemy Weapon.
     */
    public void removeWeapon() {
        GameManager.get().getWorld().removeEntity(this.enemyWeapon);
    }

    /**
     * Creates 8 bullets and shoots them in different directions.
     */
    public void turretShoot() {
        Vector3 position = new Vector3(this.getPosX() + 1, this.getPosY() + 1, 0);
        enemyWeapon.updateAim(position);
        enemyWeapon.openFire();
    }
    
    protected void stopShooting() {
        enemyWeapon.ceaseFire();
    }

    /**
     * Updates the turret sprites and shoots bullets every 8 seconds.
     *
     * @param o
     * 			the Observable object calling the update method (should be an instance of StopwatchManager)
     * @param arg
     * 			the argument passed by the Observable object (should be the stopwatch's current time)
     */
    @Override
    public void update(Observable o, Object arg) {
        if(!GameManager.get().getWorld().containsEntity(enemyWeapon)) {
            GameManager.get().getWorld().addEntity(enemyWeapon);            
        }
        switch (seconds%6){
            case 0: // set turret phase 1
                this.stopShooting();
                this.setTexture("mushroom1");
                break;
            case 1: // set turret phase 2
                this.setTexture("mushroom2");
                break;
            case 2: // set turret phase 3
                this.setTexture("mushroom3");
                break;
            case 3: // set turret phase 4
                this.setTexture("mushroom4");
                break;
            case 4: // set turret phase 5
                this.setTexture("mushroom5");
                break;
            case 5:
                this.setTexture("mushroom0");
                this.turretShoot();
                break;
            default:
                break;

        }
        seconds++;
    }

}

