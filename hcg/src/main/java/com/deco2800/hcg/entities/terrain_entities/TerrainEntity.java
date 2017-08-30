package com.deco2800.hcg.entities.terrain_entities;

import com.deco2800.hcg.entities.AbstractEntity;

public class TerrainEntity extends AbstractEntity {

    /**
     * Creates a new AbstractEntity at the given position with the given size
     * parameters.
     *
     * @param posX    the x position
     * @param posY    the y position
     * @param posZ    the z position
     * @param xLength the length of the entity in terms of the x-axis
     * @param yLength the length of the entity in terms of the y-axis
     * @param zLength the height of the entity
     */
    public TerrainEntity(float posX, float posY, float posZ, float xLength, float yLength, float zLength) {

        super(posX, posY, posZ, xLength, yLength, zLength);
//        this.setTexture(texture);
    }
}
