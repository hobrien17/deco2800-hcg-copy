package com.deco2800.hcg.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Random;

import static java.lang.Math.*;

public abstract class Enemy extends Character implements Harmable {
    
    // logger for this class
    private static final Logger LOGGER = LoggerFactory.getLogger(Enemy.class);


    public Enemy(float posX, float posY, float posZ, float xLength, float yLength, float zLength, boolean centered,
                   int health, int strength) {
        super(posX, posY, posZ, xLength, yLength, zLength, centered);
        // Current status of enemy. 1:New Born 2:Injured 3:Annoyed
        int status = 1;
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

    //public Enemy(Box3D position, float xRenderLength, float yRenderLength, boolean centered) {
    //    super(position, xRenderLength, yRenderLength, centered);
    //}
    
    /*
    private void setAttack(int attackDamage) {
        if (attackDamage < 0) {
            throw new IllegalArgumentException();
        } else {
            attack = attackDamage;
        }
    }

    public int getAttack() {return attack;}
    */

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

    // attack player
    public void causeDamage(Player player){
        
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
     * @author Jingwei WANG
     */
    public boolean detectPlayer(){
        float diffX = abs(this.getPosX() - Player.getPosX());
        float diffY = abs(this.getPosY() - Player.getPosY());
        double distance = sqrt(diffX * diffX + diffY * diffY);
        if(distance <= 10*this.getLevel()){
            //Annoyed by player.
            this.setStatus(3);
            return true;
        }else{
            return false;
        }
    }

    /**
     * Randomly go to next position which is inside the circle(Maximum distance depends on enemy level.)
     * based on current position.
     *
     * Go to the player's position if detected player during moving.
     *
     * @author Jingwei WANG
     */
    public void randomMove(){
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
        while(this.getPosX() != nextPosX && this.getPosY() != nextPosY){
            if(this.detectPlayer()){
                this.moveTo(Player.getPosX, Player.getPosY);
                break;
            }else{
                if(this.getPosX() < nextPosX){
                    speedX -= movementSpeed;
                }
                else if(this.getPosX() > nextPosX){
                    speedX += movementSpeed;
                }
                if(this.getPosY() < nextPosY){
                    speedY += movementSpeed;
                }
                else if(this.getPosY() > nextPosY){
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
     * @author Jingwei WANG
     */
    public void moveTo(float destPosX, float destPosY){
        while(this.getPosX() != destPosX && this.getPosY() != destPosY){
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
}
