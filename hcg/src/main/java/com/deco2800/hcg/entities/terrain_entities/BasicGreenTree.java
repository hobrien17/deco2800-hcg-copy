package com.deco2800.hcg.entities.terrain_entities;

public class BasicGreenTree extends TerrainEntity {


    public BasicGreenTree(float posX, float posY, float posZ) {
        super(posX, posY, posZ, 1.0f, 1.0f, 1.0f);
        //this.setCentered();
        this.setTexture("tree_01");
    }
}
