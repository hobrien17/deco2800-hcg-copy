package com.deco2800.hcg.entities;

import com.deco2800.hcg.util.Box3D;

public abstract class Enemy extends Character implements Harmable {
    private int attack;

    public Enemy(float posX, float posY, float posZ, float xLength, float yLength, float zLength, boolean centered,
                   int health, int attack) {
        super(posX, posY, posZ, xLength, yLength, zLength, centered);
        if (health > 0) {
            this.health = health;
        } else {
            throw new IllegalArgumentException();
        }
        if (attack > 0) {
            this.attack = attack;
        } else {
            throw new IllegalArgumentException();
        }

    }

    //public Enemy(float posX, float posY, float posZ, float xLength, float yLength, float zLength,
    //                      float xRenderLength, float yRenderLength, boolean centered) {
    //   super(posX, posY, posZ, xLength, yLength, zLength, xRenderLength, yRenderLength, centered);
    //}

    //public Enemy(Box3D position, float xRenderLength, float yRenderLength, boolean centered) {
    //    super(position, xRenderLength, yRenderLength, centered);
    //}
    

    private void setAttack(int attackDamage) {
        if (attackDamage < 0) {
            throw new IllegalArgumentException();
        } else {
            attack = attackDamage;
        }
    }

    public int getAttack() {return attack;}

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

    public void gainHealth(int extraHealth) {
        if (extraHealth < 0){
            throw new IllegalArgumentException();
        }
        else {
            health += extraHealth;
        }
    }

    // attack player
    public void causeHarm(){

    }
}
