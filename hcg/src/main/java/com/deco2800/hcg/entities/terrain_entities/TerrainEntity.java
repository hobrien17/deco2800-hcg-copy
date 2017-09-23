package com.deco2800.hcg.entities.terrain_entities;

import com.deco2800.hcg.entities.AbstractEntity;

/**
 * @author Ken
 */
class TerrainEntity extends AbstractEntity {

    /**
     * Creates a new TerrainEntity at the given position with the given size
     * parameters.
     *
     * This class should have extra attrs and methods added as required, destructibility etc
     *
     * @param posX    the x position
     * @param posY    the y position
     * @param posZ    the z position
     * @param xLength the length of the entity in terms of the x-axis
     * @param yLength the length of the entity in terms of the y-axis
     * @param zLength the height of the entity
     */
    TerrainEntity(float posX, float posY, float posZ, float xLength, float yLength, float zLength) {

        super(posX, posY, posZ, xLength, yLength, zLength);
    }
}
