package com.deco2800.hcg.entities.terrain_entities;

public class Rock extends TerrainEntity {

    /**
     * Creates a new WallBlock at the given position with the given size
     * parameters.
     *
     * @param posX    the x position
     * @param posY    the y position
     * @param posZ    the z position
     */
    public Rock(float posX, float posY, float posZ) {
        super(posX, posY, posZ, 0.5f, 0.5f, 0.5f);
        this.setTexture("rock");
    }
}
