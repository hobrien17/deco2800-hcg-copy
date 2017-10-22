package com.deco2800.hcg.entities.terrain_entities;

public class Volcano extends TerrainEntity{
    /**
     * Creates a new Volcano at the given position with the given size
     * parameters.
     *
     * @param posX    the x position
     * @param posY    the y position
     * @param posZ    the z position
     */
    public Volcano(float posX, float posY, float posZ) {
        super(posX, posY, posZ, 3.0f, 3.0f, 3.0f);
        this.setTexture("volcano");
    }
}
