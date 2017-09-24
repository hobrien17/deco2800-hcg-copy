package com.deco2800.hcg.entities.terrain_entities;

public class HouseWRoof extends TerrainEntity {

    /**
     * Creates a new House at the given position with the given size
     * parameters.
     *
     * @param posX    the x position
     * @param posY    the y position
     * @param posZ    the z position
     */
    public HouseWRoof(float posX, float posY, float posZ) {
        super(posX, posY, posZ, 5.0f, 5.0f, 1.0f);
        this.setTexture("houseWRoof");
    }
}
