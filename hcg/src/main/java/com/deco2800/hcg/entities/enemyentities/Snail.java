package com.deco2800.hcg.entities.enemyentities;

import com.deco2800.hcg.entities.Tickable;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.ItemManager;
import com.deco2800.hcg.weapons.WeaponBuilder;
import com.deco2800.hcg.weapons.WeaponType;

import java.util.HashMap;

public class Snail extends Enemy implements Tickable {
    /**
     * Constructor for the Hedgehog class. Creates a new hedgehog at the given
     * position.
     *
     * @param posX the x position
     * @param posY the y position
     * @param posZ the x position
     * @param id the ID of the Snail Enemy
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
        // status should always be 1
        this.setNewPos();//Put new position into Box3D.
        this.detectCollision();//Detect collision.
        this.moveAction();//Move enemy to the position in Box3D.
        // Apply any effects that exist on the entity
        myEffects.apply();
        
    }

    @Override
    public void setupLoot() {
        lootRarity = new HashMap<>();

        lootRarity.put("grass_seed", 1.0);

        checkLootRarity();
    }

    @Override
    public Item[] loot() {
        Item[] arr = new Item[1];
        arr[0] = ((ItemManager) GameManager.get().getManager(ItemManager.class)).getNew(this.randItem());
        return arr;
    }

}
