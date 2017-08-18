package com.deco2800.hcg.entities;

import com.deco2800.hcg.util.Box3D;

public abstract class Enemy extends AbstractEntity implements Harmable {

    public Enemy(float posX, float posY, float posZ, float xLength, float yLength, float zLength) {
        super(posX, posY, posZ, xLength, yLength, zLength);
    }

    public Enemy(float posX, float posY, float posZ, float xLength, float yLength, float zLength,
                          float xRenderLength, float yRenderLength, boolean centered) {
        super(posX, posY, posZ, xLength, yLength, zLength, xRenderLength, yRenderLength, centered);
    }

    public Enemy(Box3D position, float xRenderLength, float yRenderLength, boolean centered) {
        super(position, xRenderLength, yRenderLength, centered);
    }

}
