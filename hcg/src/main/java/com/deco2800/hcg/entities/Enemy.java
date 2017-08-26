package com.deco2800.hcg.entities;

import com.badlogic.gdx.math.Vector3;
import com.deco2800.hcg.entities.garden_entities.plants.Lootable;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Random;

import static java.lang.Math.*;

public abstract class Enemy extends Character implements Lootable {
    
    // logger for this class
    private static final Logger LOGGER = LoggerFactory.getLogger(Enemy.class);
    protected PlayerManager playerManager;
    // Current status of enemy. 1 : New Born, 2 : Injured 3 : Annoyed
    int status;
    int ID;
    Map<String, Double> lootRarity;
    
    public Enemy(float posX, float posY, float posZ, float xLength, float yLength, float zLength, boolean centered,
                   int health, int strength, int ID) {
        super(posX, posY, posZ, xLength, yLength, zLength, centered);
        this.playerManager = (PlayerManager) GameManager.get().getManager(PlayerManager.class);
        status = 1;
        if (ID > 0) {
            this.ID = ID;
        } else {
            throw new IllegalArgumentException();
        }
        if (health > 0) {
            this.health = health;
        } else {
            throw new IllegalArgumentException();
        }
        if (strength > 0) {
            this.strength = strength;
        } else {
            throw new IllegalArgumentException();
        }
        this.setSpeedX(0);
        this.setSpeedY(0);


    }

    /**
     * Gets the enemy ID
     *
     * @return the integer ID of the enemy
     */
    public int getID() { return ID; }

    /**
     * Take the damage inflicted by the other entities
     * 
     * @param damage: the amount of the damage
     * @exception: throw IllegalArgumentException if damage is a negative integer
     */
    public void takeDamage(int damage) {
        if (damage < 0){
            throw new IllegalArgumentException();
        }
        else if (damage > health){
            health = 0;
        }
        else {
            health -= damage;
        }
    }

    /**
     * Change the health level of the enemy i.e can increase or decrease the health level (depending on whether the amount is positive or negative)
     * 
     * @param amount: the amount that the health level will change by
     */
    public void changeHealth(int amount) {
        health = Math.max(health + amount, 0);
    }

    /**
     * Attack the player
     * 
     */
    public void causeDamage(Player player) {
        //we have to use this because at the moment the Player class has no takeDamage method yet. We are advised that they will implement it soon
        player.setHealth(player.getHealth() - 10);
    }

    @Override
    public Map<String, Double> getRarity() {
        return lootRarity;
    }

    /**
     * Gets a list of all possible loot dropped by this plant
     *
     * @return An array of all possible loot
     */
    public String[] getLoot() {
        return lootRarity.keySet().toArray(new String[lootRarity.size()]);
    }

    /**
     * Sets up the loot rarity map for a plant
     */
    abstract void setupLoot();
    //Use this in special enemy classes to set up drops

    /**
     * Generates a random item based on the loot rarity
     *
     * @return A random item string in the plant's loot map
     */
    public String randItem() {
        Double prob = Math.random();
        Double total = 0.0;
        for (Map.Entry<String, Double> entry : lootRarity.entrySet()) {
            total += entry.getValue();
            if (total > prob) {
                return entry.getKey();
            }
        }
        LOGGER.warn("No item has been selected, returning null");
        return null;
    }

    /**
     * Checks that the loot rarity is valid
     *
     * @return true if the loot rarity is valid, otherwise false
     */
    public boolean checkLootRarity() {
        double sum = 0.0;
        for (Double rarity : lootRarity.values()) {
            if (rarity < 0.0 || rarity > 1.0) {
                LOGGER.error("Rarity should be between 0 and 1");
                return false;
            }
            sum += rarity;
        }
        if (Double.compare(sum, 1.0) != 0) {
            LOGGER.warn("Total rarity should be 1");
            return false;
        }
        return true;
    }

    /**
     * Set new status of enemy.
     * @param newStatus
     */
    public void setStatus(int newStatus){
        this.status = newStatus;
    }

    /**
     * To detect player's position. If player is near enemy, return 1.
     * @return: 0. Undetected
     *          1. Detected player
     *
     */
    public boolean detectPlayer(){
        float diffX = abs(this.getPosX() - playerManager.getPlayer().getPosX());
        float diffY = abs(this.getPosY() - playerManager.getPlayer().getPosY());
        double distance = sqrt(diffX * diffX + diffY * diffY);
        if(distance <= 10*this.getLevel()){
            //Annoyed by player.
            this.setStatus(3);
            return true;
        }else{
            this.setStatus(1);
            return false;
        }
    }    

    /**
     * Randomly go to next position which is inside the circle(Maximum distance depends on enemy level.)
     * based on current position.
     *
     * Go to the player's position if detected player during moving.
     *
     */
    public void randomMove() {
        float radius;
        float distance;
        float nextPosX;
        float nextPosY;
        float nextPosZ;
        //Get direction of next position. Randomly be chosen between 0 and 360.
        radius = Math.abs(new Random().nextFloat()) * 400 % 360;
        //Get distance to next position which is no more than maximum.
        distance = Math.abs(new Random().nextFloat()) * 10 * this.getLevel();
        nextPosX = (float) (this.getPosX() + distance * cos(radius));
        nextPosY = (float) (this.getPosY() + distance * cos(radius));
        while (this.getPosX() != nextPosX && this.getPosY() != nextPosY) {
            if (this.detectPlayer()) {
                this.moveToPlayer();
                break;
            } else {
                if (this.getPosX() < nextPosX) {
                    speedX -= movementSpeed;
                } else if (this.getPosX() > nextPosX) {
                    speedX += movementSpeed;
                }
                if (this.getPosY() < nextPosY) {
                    speedY += movementSpeed;
                } else if (this.getPosY() > nextPosY) {
                    speedY -= movementSpeed;
                }
            }
        }
    }

    /**
     * Go to the pointed position.
     * @param destPosX: the X of next position
     * @param destPosY: the Y of next position
     *
     */
    public void moveTo(float destPosX, float destPosY){
        while(this.getPosX() != destPosX && this.getPosY() != destPosY ){
            if(this.getPosX() < destPosX){
                speedX -= movementSpeed;
            }
            else if(this.getPosX() > destPosX){
                speedX += movementSpeed;
            }
            if(this.getPosY() < destPosY){
                speedY += movementSpeed;
            }
            else if(this.getPosY() > destPosY){
                speedY -= movementSpeed;
            }
        }
    }

    /**
     * Move enemy to player.
     *
     */
    public void moveToPlayer(){
        while(this.getPosX() != playerManager.getPlayer().getPosX() &&
                this.getPosY() != playerManager.getPlayer().getPosY() &&
                this.detectPlayer()){
            if(this.getPosX() < playerManager.getPlayer().getPosX()){
                speedX -= movementSpeed;
            }
            else if(this.getPosX() > playerManager.getPlayer().getPosX()){
                speedX += movementSpeed;
            }
            if(this.getPosY() < playerManager.getPlayer().getPosY()){
                speedY += movementSpeed;
            }
            else if(this.getPosY() > playerManager.getPlayer().getPosY()){
                speedY -= movementSpeed;
            }
        }
    }
    
    public void shoot() {
        Vector3 worldCoords = GameManager.get().getCamera()
                .unproject(new Vector3(playerManager.getPlayer().getPosX(), playerManager.getPlayer().getPosY(), 0));
        Bullet bullet = new Bullet(this.getPosX(), this.getPosY(), this.getPosZ(), worldCoords.x, worldCoords.y);
        GameManager.get().getWorld().addEntity(bullet);
    }
}
