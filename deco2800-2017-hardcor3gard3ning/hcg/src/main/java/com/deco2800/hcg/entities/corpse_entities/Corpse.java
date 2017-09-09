package com.deco2800.hcg.entities.corpse_entities;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.garden_entities.seeds.Seed;
import com.deco2800.hcg.entities.turrets.AbstractTurret;

public abstract class Corpse extends AbstractEntity {

    AbstractTurret turret;

    /**
     * Creates an Enemy Corpse at the given position
     * @param posX the x position
     * @param posY the y position
     * @param posZ the z position
     */
    public Corpse(float posX, float posY, float posZ, String texture) {
        super(posX, posY, posZ, 0.7f, 0.7f, 1, 1f, 1f, false);
        turret = null;
        this.setTexture(texture);
    }

    /**
     * Returns the seed planted in the enemy corpse
     *
     * @return the seed in the corpse, null if empty or seed has grown has grown into a different entity
     */
    public AbstractTurret getTurret() {
        return turret;
    }
    
    /**
     * Sets the texture of this corpse based on what's planted inside
     * 
     */
    public abstract void setThisTexture();
    
    /**
     * Adds a seed to the enemy corpse, if empty
     *
     * @param seed the seed to be added
     * @return true if the seed was added, false if it could not be added
     */
    public abstract boolean plantInside(Seed seed);
}
