package com.deco2800.hcg.entities.terrain_entities;

public class Sign extends TerrainEntity {

    /**
     * Creates a new Sign at the given position with the given size
     * parameters.
     *
     * @param posX    the x position
     * @param posY    the y position
     * @param posZ    the z position
     * @param ID      the ID of the sign
     */
    public Sign(float posX, float posY, float posZ, int ID) {
        super(posX, posY, posZ, 1.0f, 1.0f, 1.0f);
        switch(ID) {
            default:
                break;
        }
    }
}