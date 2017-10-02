package com.deco2800.hcg.entities.terrain_entities;

public class PicketFenceNS extends TerrainEntity {

    /**
     * Creates a new PicketFence at the given position with the given size
     * parameters.
     *
     * @param posX    the x position
     * @param posY    the y position
     * @param posZ    the z position
     */
    public PicketFenceNS(float posX, float posY, float posZ) {
        super(posX, posY, posZ, 1.0f, 1.0f, 1.0f);
        this.setTexture("picketFenceNS");
    }
}
