package com.deco2800.hcg.entities;

import com.deco2800.hcg.util.Box3D;

public abstract class Enemy extends AbstractEntity implements Harmable {
    private int health; 

    public Enemy(float posX, float posY, float posZ, float xLength, float yLength, float zLength, int health) {
        super(posX, posY, posZ, xLength, yLength, zLength);
        if (health > 0) {
            this.health = health;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public Enemy(float posX, float posY, float posZ, float xLength, float yLength, float zLength,
                          float xRenderLength, float yRenderLength, boolean centered) {
        super(posX, posY, posZ, xLength, yLength, zLength, xRenderLength, yRenderLength, centered);
    }

    public Enemy(Box3D position, float xRenderLength, float yRenderLength, boolean centered) {
        super(position, xRenderLength, yRenderLength, centered);
    }
    
    public int getHealth() {
        return this.health;
    }
    
    public void setHealth(int newHealth) {
        if (newHealth > 0) {
            this.health = newHealth;
        }
        else {
            throw new IllegalArgumentException();
        }
    }

}
