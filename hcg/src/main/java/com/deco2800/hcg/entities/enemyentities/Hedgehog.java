package com.deco2800.hcg.entities.enemyentities;

import com.deco2800.hcg.entities.Tickable;
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
     * @param id the ID of the Hedgehog Enemy
     */
    public Hedgehog(float posX, float posY, float posZ, int id) {
        super(posX, posY, posZ, 0.3f, 0.3f, 1, false, 1000, 5, id);
        this.setTexture("tree");
        this.level = 1;
        walkingRange = 30 * this.level;
        chargingRange = 10 * this.level;
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

    @Override
    public void setupLoot() {
        lootRarity = new HashMap<>();

        lootRarity.put("explosive_seed", 1.0);

        checkLootRarity();
    }

    /**
     * Sets the charged at player status to the status given.
     * @param status
     * 			the status of charged at player, true if charged at player, false otherwise.
     *
     */
    public void setChargeStatus(boolean status) { this.chargedAtPlayer = status; }

    /**
     * Gets the charged at player status.
     * @return: status of chargedAtPlayer
     *
     */
    public boolean getChargeStatus() { return this.chargedAtPlayer; }

    /**
     * Changes the hedgehog's speed and status depending on the situation.
     *
     */
    public void setHedgehogStatus() {
        float distance = this.distance(playerManager.getPlayer());
        if (chargedAtPlayer && distance > chargingRange){
            this.setChargeStatus(false);
        }
        if (!chargedAtPlayer && distance < walkingRange && distance > chargingRange) {
            // move slowly to player
            setSpeed(this.level * 0.01f);
            this.setStatus(2);
            this.lastPlayerX = playerManager.getPlayer().getPosX();
            this.lastPlayerY = playerManager.getPlayer().getPosY();
        } else if (!chargedAtPlayer && distance < chargingRange) {
            // charge at player
            setSpeed(this.level * 0.05f);
            this.setStatus(2);
            this.lastPlayerX = playerManager.getPlayer().getPosX();
            this.lastPlayerY = playerManager.getPlayer().getPosY();
        } else {
            // move randomly
            setSpeed(this.level * 0.03f);
            this.setStatus(3);
        }
    }
    /**
     * On Tick handler
     * @param gameTickCount Current game tick
     */
    @Override
    public void onTick(long gameTickCount) {
        this.setHedgehogStatus();
        this.setNewPos();//Put new position into Box3D.
        this.detectCollision();//Detect collision.
        if (this.collidedPlayer) {
            this.setChargeStatus(true);
        }
        this.moveAction();//Move enemy to the position in Box3D.
        myEffects.apply();

    }

}
