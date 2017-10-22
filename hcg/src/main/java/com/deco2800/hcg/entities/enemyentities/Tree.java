package com.deco2800.hcg.entities.enemyentities;

import com.deco2800.hcg.entities.Tickable;
import com.deco2800.hcg.items.lootable.LootWrapper;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.weapons.WeaponBuilder;
import com.deco2800.hcg.weapons.WeaponType;


import java.util.HashMap;

public class Tree extends Enemy implements Tickable {

    private boolean firstSpawn = false;
    private boolean secondSpawn = false;
    private boolean thirdSpawn = false;
    private boolean forthSpawn = false;

    /**
     * Constructor for the Crab class. Creates a new crab boss at the given
     * position.
     *
     * @param posX the x position
     * @param posY the y position
     * @param posZ the x position
     * @param id the ID of the Hedgehog Enemy
     */
    public Tree(float posX, float posY, float posZ, int id) {
        super(posX, posY, posZ, 1f, 1f, 1, false, 10000, 10, id, EnemyType.CRAB);

        this.boss = true;
        // set texture
        this.level = 1;
        newPos.setX(posX);
        newPos.setY(posY);
        newPos.setZ(posZ);
        this.enemyWeapon = new WeaponBuilder()
                .setWeaponType(WeaponType.MACHINEGUN)
                .setUser(this)
                .setCooldown(50)
                .setTexture("battle_seed")
                .build();
        this.setMovementSpeed((float) (this.movementSpeed*0.1));
        this.defaultSpeed = this.getMovementSpeed();

    }


    @Override
    public void setupLoot() {
        lootRarity = new HashMap<>();

        lootRarity.put(new LootWrapper("water_seed"), 1.0);

        checkLootRarity();
    }

    /**
     * On Tick handler
     * @param gameTickCount Current game tick
     */
    @Override
    public void onTick(long gameTickCount) {
        if (this.getHealthCur() <= this.getHealthMax()*0.5){
            this.setMovementSpeed(this.defaultSpeed *3);
        }
        this.tree();
        // Pos for ramdom ground weapon
        float ramdomX = Math.abs(random.nextFloat()) * GameManager.get().getWorld().getWidth();
        float ramdomY = Math.abs(random.nextFloat()) * GameManager.get().getWorld().getWidth();
        //need to set time counter
    }

    /**
     *  Logic for Tree
     *
     */
    private void tree(){
        this.setMovementSpeed(0);
        this.defaultSpeed = 0;

        if ((this.getHealthCur() < this.getHealthMax()) && (this.getHealthCur() > this.getHealthMax()*0.8)){
            //bottom
            this.setPosX((float) (GameManager.get().getWorld().getWidth() * 0.5));
            this.setPosY(0);
        } else if ((this.getHealthCur() < this.getHealthMax()*0.8) && (this.getHealthCur() > this.getHealthMax()*0.6)){
            //left
            this.setPosX(0);
            this.setPosY((float) (GameManager.get().getWorld().getLength() * 0.5));
            if (!firstSpawn){
                //New enemies spawn
                firstSpawn = true;
            }
        } else if ((this.getHealthCur() < this.getHealthMax()*0.6) && (this.getHealthCur() > this.getHealthMax()*0.4)){
            //right
            this.setPosX(GameManager.get().getWorld().getWidth());
            this.setPosY((float) (GameManager.get().getWorld().getLength() * 0.5));
            if (!secondSpawn){
                //New enemies spawn
                secondSpawn = true;
            }
        } else if ((this.getHealthCur() < this.getHealthMax()*0.4) && (this.getHealthCur() > this.getHealthMax()*0.2)){
            //top
            this.setPosX((float) (GameManager.get().getWorld().getWidth() * 0.5));
            this.setPosY(GameManager.get().getWorld().getLength());
            if (!thirdSpawn){
                //New enemies spawn
                thirdSpawn = true;
            }
        } else {
            //middle
            this.setPosX((float) (GameManager.get().getWorld().getWidth() * 0.5));
            this.setPosY((float) (GameManager.get().getWorld().getLength() * 0.5));
            if (!forthSpawn){
                //New enemies spawn
                forthSpawn = true;
            }
        }
    }
}
