package com.deco2800.hcg.entities.enemyentities;

import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.Tickable;
import com.deco2800.hcg.items.lootable.LootWrapper;
import com.deco2800.hcg.weapons.WeaponBuilder;
import com.deco2800.hcg.weapons.WeaponType;

import java.util.HashMap;

public class Hedgehog extends Enemy implements Tickable {

    int walkingRange;
    int chargingRange;
    boolean chargedAtPlayer;
    private int counter;
    private int delay;
    private int spriteCount;

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
        super(posX, posY, posZ, 0.3f, 0.3f, 1, false, 1000, 5, id, EnemyType.HEDGEHOG);
        this.boss = false;
        this.setTexture("hedgehogW1");
        this.level = 1;
        this.walkingRange = 30 * this.level;
        this.chargingRange = 10 * this.level;
        this.setChargeStatus(false);
        newPos.setX(posX);
        newPos.setY(posY);
        newPos.setZ(posZ);
        this.delay = 20;
        this.counter = 20;
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

        lootRarity.put(new LootWrapper("explosive_seed"), 1.0);

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
        this.setClosestPlayer(playerManager.getPlayer());
        if (chargedAtPlayer && distance > chargingRange){
            this.setChargeStatus(false);
        }
        if (!chargedAtPlayer && distance < walkingRange && distance > chargingRange) {
            // move slowly to player
            this.setSpeed(this.level * 0.01f);
            this.setStatus(2);
            this.updateStandingSprite();
            this.lastPlayerX = playerManager.getPlayer().getPosX();
            this.lastPlayerY = playerManager.getPlayer().getPosY();
        } else if (!chargedAtPlayer && distance < chargingRange) {
            // charge at player
            this.setSpeed(this.level * 0.05f);
            this.setStatus(2);
            this.updateBallSprite();
            this.lastPlayerX = playerManager.getPlayer().getPosX();
            this.lastPlayerY = playerManager.getPlayer().getPosY();
        } else {
            // move randomly
            this.setSpeed(this.level * 0.03f);
            this.setStatus(3);
            this.updateStandingSprite();
        }
    }
    
    /**
     * Changes the hedgehog's speed & status with respects to multiple players
     */
    public void setHedgehogStatusMultiplayer() {
    	float distance;
    	Player closestPlayer = this.getClosestPlayer();
    	
    	this.detectPlayers();
    	distance = this.distance(closestPlayer);
    	
    	if (chargedAtPlayer && distance > chargingRange) {
    		this.setChargeStatus(false);    	
    	} 
    	
    	if (!chargedAtPlayer && distance < walkingRange && distance > chargingRange) {
    		// move slowly to player
            setSpeed(this.level * 0.01f);
            this.setStatus(2);
            this.updateStandingSprite();
            this.lastPlayerX = closestPlayer.getPosX();
            this.lastPlayerY = closestPlayer.getPosY();
    	} else if (!chargedAtPlayer && distance < chargingRange) {
            // charge at player
            setSpeed(this.level * 0.05f);
            this.setStatus(2);
            this.updateBallSprite();
            this.lastPlayerX = closestPlayer.getPosX();
            this.lastPlayerY = closestPlayer.getPosY();
        } else {
            // move randomly
            setSpeed(this.level * 0.03f);
            this.setStatus(3);
            this.updateStandingSprite();
        }
    	
    }

    public void updateBallSprite() {
        if (spriteCount%4 == 0) {
            switch (this.direction) {
                case 1:
                    if (this.getTexture() == "hedgeballWE1") {
                        this.setTexture("hedgeballWE2");
                    } else {
                        this.setTexture("hedgeballWE1");
                    }
                    break;
                case 2:
                    if (this.getTexture() == "hedgeballNS1") {
                        this.setTexture("hedgeballNS2");
                    } else {
                        this.setTexture("hedgeballNS1");
                    }
                    break;
                case 3:
                    if (this.getTexture() == "hedgeballWE1") {
                        this.setTexture("hedgeballWE2");
                    } else {
                        this.setTexture("hedgeballWE1");
                    }
                    break;
                case 4:
                    if (this.getTexture() == "hedgeballNS1") {
                        this.setTexture("hedgeballNS2");
                    } else {
                        this.setTexture("hedgeballNS1");
                    }
                    break;
                default:
                    break;
            }
        }
        spriteCount++;
    }

    public void updateStandingSprite() {
        if (spriteCount%4 == 0) {
            switch (this.direction) {
                case 1:
                    if (this.getTexture() == "hedgehogE1") {
                        this.setTexture("hedgehogE2");
                    } else {
                        this.setTexture("hedgehogE1");
                    }
                case 2:
                    if (this.getTexture() == "hedgehogN1") {
                        this.setTexture("hedgehogN2");
                    } else {
                        this.setTexture("hedgehogN1");
                    }
                    break;
                case 3:
                    if (this.getTexture() == "hedgehogW1") {
                        this.setTexture("hedgehogW2");
                    } else {
                        this.setTexture("hedgehogW1");
                    }
                    break;
                case 4:
                    if (this.getTexture() == "hedgehogS1") {
                        this.setTexture("hedgehogS2");
                    } else {
                        this.setTexture("hedgehogS1");
                    }
                    break;
                default:
                    break;
            }
        }
        spriteCount++;
    }
    
    /**
     * On Tick handler
     * @param gameTickCount Current game tick
     */
    @Override
    public void onTick(long gameTickCount) {
    	this.setHedgehogStatus();
    	this.setNewPos();//Put new position into Box3D.
        this.setDirection();
    	this.detectCollision();//Detect collision.
        if (this.counter < this.delay) {
            this.counter++;
        } else if (this.collidedPlayer) {
            this.setChargeStatus(true);
            this.causeDamage(this.getTarget());
            this.counter = 0;
        }
    	this.moveAction();//Move enemy to the position in Box3D.
    	myEffects.apply();
    }

}
