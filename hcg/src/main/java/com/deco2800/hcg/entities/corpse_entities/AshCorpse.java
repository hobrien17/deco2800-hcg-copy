package com.deco2800.hcg.entities.corpse_entities;

import com.deco2800.hcg.entities.garden_entities.seeds.Seed;

public class AshCorpse extends Corpse {

    public AshCorpse(float posX, float posY, float posZ, String texture) {
        super(posX, posY, posZ,texture);
    }

    /**
     * Adds a seed to the enemy corpse, if empty
     * Can only plant fire and explosive seeds in the Ash Corpse
     *
     * @param seed the seed to be added
     * @return true if the seed was added, false if it could not be added
     */
    public boolean addSeed(Seed seed) {
        if(this.seed == null && (seed.getType() == Seed.Type.FIRE || seed.getType() == Seed.Type.EXPLOSIVE)) {
            this.seed = seed;
            // Set new texture
            return true;
        }
        // Check if the enemy corpse does not contain special item
        return false;

    }
}
