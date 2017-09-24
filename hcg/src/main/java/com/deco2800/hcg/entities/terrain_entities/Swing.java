package com.deco2800.hcg.entities.terrain_entities;

public class Swing extends TerrainEntity {

    /**
     * Creates a new Swing at the given position with the given size
     * parameters.
     *
     * @param posX    the x position
     * @param posY    the y position
     * @param posZ    the z position
     */
    public Swing(float posX, float posY, float posZ) {
        super(posX, posY, posZ, 3.0f, 3.0f, 1.0f);
        this.setTexture("swing");
    }
}
