package com.deco2800.hcg.entities.enemyentities;

import com.deco2800.hcg.entities.Tickable;
import com.deco2800.hcg.weapons.WeaponBuilder;
import com.deco2800.hcg.weapons.WeaponType;

public class Snail extends Enemy implements Tickable {
    /**
     * Constructor for the Hedgehog class. Creates a new hedgehog at the given
     * position.
     *
     * @param posX the x position
     * @param posY the y position
     * @param posZ the x position
     * @param id the ID of the Hedgehog Enemy
     */
    public Snail(float posX, float posY, float posZ, int id) {
        super(posX, posY, posZ, 0.3f, 0.3f, 1, false, 1000, 5, id);
        //this.setTexture("snail"); - TO DO: add the right texture
        this.level = 1;
        newPos.setX(posX);
        newPos.setY(posY);
        newPos.setZ(posZ);
        this.enemyWeapon = new WeaponBuilder()
                .setWeaponType(WeaponType.SHOTGUN)
                .setUser(this)
                .setCooldown(50)
                .setTexture("fire_seed")
                .build();

    }

    @Override
    public void onTick(long gameTickCount) {
        // TODO Auto-generated method stub
        
    }

    @Override
    void setupLoot() {
        // TODO Auto-generated method stub
        
    }

}
