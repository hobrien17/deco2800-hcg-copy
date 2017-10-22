package com.deco2800.hcg.entities.terrain_entities;

public class WarningSign extends TerrainEntity {

    /**
     * Creates a new Swing at the given position with the given size
     * parameters.
     *
     * @param posX    the x position
     * @param posY    the y position
     * @param posZ    the z position
     */
    public WarningSign(float posX, float posY, float posZ) {
        super(posX, posY, posZ, 1.0f, 1.0f, 1.0f);
        this.setTexture("warningsign"); // warning sign of things to come
    }
}
