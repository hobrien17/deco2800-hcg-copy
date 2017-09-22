package com.deco2800.hcg.entities.enemy_entities;

import com.deco2800.hcg.entities.Tickable;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.ItemManager;
import com.deco2800.hcg.weapons.WeaponBuilder;
import com.deco2800.hcg.weapons.WeaponType;

import java.util.HashMap;

public class Hedgehog extends Enemy implements Tickable {

    int walkingRange;
    int chargingRange;
    boolean chargedAtPlayer;

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
        this.setTexture("tree");
        this.level = 1;
        walkingRange = 30 * this.level;
        chargingRange = 15 * this.level;
        chargedAtPlayer = false;
        newPos.setX(posX);
        newPos.setY(posY);
        newPos.setZ(posZ);
        this.enemyWeapon = new WeaponBuilder()
                .setWeaponType(WeaponType.MACHINEGUN)
                .setUser(this)
                .setCooldown(50)
                .setTexture("battle_seed")
                .build();

    }

    /**
     * On Tick handler
     * @param gameTickCount Current game tick
     */
    @Override
    public void onTick(long gameTickCount) {
        float distance = this.distance(playerManager.getPlayer());
        if (chargedAtPlayer == true && distance > walkingRange){
            chargedAtPlayer = false;
        }
        if (chargedAtPlayer == false && distance < walkingRange && distance > chargingRange){
            // move_slowly to player
            setSpeed((this.level * 0.01f));
            newPos = this.getToPlayerPos();
        } else if (chargedAtPlayer == false && distance < chargingRange) {
            // charge at player
            setSpeed(this.level * 0.05f);
            newPos = getToPlayerPos();
        } else {
            // move randomly
            setSpeed(this.level * 0.03f);
            newPos = this.getRandomPos();
        }
        this.detectCollision();//Detect collision.
        if (this.collidedPlayer == true) {
            chargedAtPlayer = true;
        }
        this.moveAction();//Move enemy to the position in Box3D.
        myEffects.apply();

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
