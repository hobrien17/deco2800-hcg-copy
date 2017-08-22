package com.deco2800.hcg.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deco2800.hcg.util.Box3D;

public abstract class Enemy extends Character implements Harmable {
    
    // logger for this class
    private static final Logger LOGGER = LoggerFactory.getLogger(Enemy.class);
    
    
    public Enemy(float posX, float posY, float posZ, float xLength, float yLength, float zLength, boolean centered,
                   int health, int strength) {
        super(posX, posY, posZ, xLength, yLength, zLength, centered);
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
}
