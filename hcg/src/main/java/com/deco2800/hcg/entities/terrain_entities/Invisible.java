package com.deco2800.hcg.entities.terrain_entities;

public class Invisible extends TerrainEntity {

    /**
     * Creates a new WallBlock at the given position with the given size
     * parameters.
     *
     * @param posX    the x position
     * @param posY    the y position
     * @param posZ    the z position
     */
    public Invisible(float posX, float posY, float posZ) {
        super(posX, posY, posZ, 1.0f, 1.0f, 1.0f);
        this.setTexture("invisible");
        
    }
}
