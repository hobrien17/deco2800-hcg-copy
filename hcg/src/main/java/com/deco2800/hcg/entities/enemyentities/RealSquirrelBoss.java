package com.deco2800.hcg.entities.enemyentities;

import com.deco2800.hcg.entities.Tickable;
import com.deco2800.hcg.items.lootable.LootWrapper;
import com.deco2800.hcg.weapons.WeaponBuilder;
import com.deco2800.hcg.weapons.WeaponType;
import java.util.HashMap;

public class RealSquirrelBoss extends Enemy implements Tickable {

    private String[] sprites = {"squirrelE", "squirrelN", "squirrelW", "squirrelS"};

    /**
     * Constructor for the Crab class. Creates a new crab boss at the given
     * position.
     *
     * @param posX the x position
     * @param posY the y position
     * @param posZ the x position
     * @param id   the ID of the Hedgehog Enemy
     */
    public RealSquirrelBoss(float posX, float posY, float posZ, int id) {
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
        this.setMovementSpeed((float) (playerManager.getPlayer().getMovementSpeed() * 0.5));
        this.defaultSpeed = this.getMovementSpeed();
    }

    @Override
    public void setupLoot() {
        lootRarity = new HashMap<>();

        lootRarity.put(new LootWrapper("water_seed", 1.0f), 1.0);

        checkLootRarity();
    }

    /**
     * On Tick handler
     * @param gameTickCount Current game tick
     */
    @Override
    public void onTick(long gameTickCount) {
        squirrel();
    }

    /**
     *  Logic for Squirrel
     *
     */
    void squirrel(){

        if (this.getNumberPlayers() > 1) {
            float closestDistance = findClosestPlayer();
            if (closestDistance <= 10 * this.level){
                newPos.setX(2 * this.getPosX() - this.closestPlayer.getPosX());
                newPos.setY(2 * this.getPosY() - this.closestPlayer.getPosY());
                if ((this.getHealthCur() < this.getHealthMax()) && (this.getHealthCur() > this.getHealthMax()*0.85)){
                    this.setMovementSpeed((float) (this.defaultSpeed * 1.2));
                } else if ((this.getHealthCur() < this.getHealthMax()*0.85) && (this.getHealthCur() > this.getHealthMax()*0.5)){
                    this.setMovementSpeed((float) (this.defaultSpeed * 1.4));
                } else if ((this.getHealthCur() < this.getHealthMax()*0.5) && (this.getHealthCur() > this.getHealthMax()*0.25)){
                    this.setMovementSpeed((float) (this.defaultSpeed * 1.6));
                } else {
                    this.setMovementSpeed((float) (this.defaultSpeed * 1.8));
                }
            } else {
                newPos = this.getRandomPos();
            }

        } else {
            this.closestPlayer = playerManager.getPlayer();
            float distance = this.distance(playerManager.getPlayer());
            if (distance <= 10 * this.level){
                newPos.setX(2 * this.getPosX() - this.closestPlayer.getPosX());
                newPos.setY(2 * this.getPosY() - this.closestPlayer.getPosY());
            } else {
                newPos = this.getRandomPos();
            }
        }
        this.setDirection();
        this.updateSprite(sprites);
        this.detectCollision();
        this.moveAction();

    }
}
