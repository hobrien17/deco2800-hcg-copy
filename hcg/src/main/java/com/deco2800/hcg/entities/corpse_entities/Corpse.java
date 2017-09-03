package com.deco2800.hcg.entities.corpse_entities;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.garden_entities.seeds.Seed;

public abstract class Corpse extends AbstractEntity {

    Seed seed;

    /**
     * Creates an Enemy Corpse at the given position
     * @param posX the x position
     * @param posY the y position
     * @param posZ the z position
     */
    public Corpse(float posX, float posY, float posZ, String texture) {
        super(posX, posY, posZ, 0.7f, 0.7f, 1, 1f, 1f, false);
        seed = null;
        this.setTexture(texture);
    }

    /**
     * Returns the seed planted in the enemy corpse
     *
     * @return the seed in the corpse, null if empty or seed has grown has grown into a different entity
     */
    public Seed getSeed() {
        return seed;
    }
}
