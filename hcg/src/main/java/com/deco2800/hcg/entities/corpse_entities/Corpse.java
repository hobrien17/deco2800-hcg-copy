package com.deco2800.hcg.entities.corpse_entities;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.bullets.Bullet;
import com.deco2800.hcg.entities.bullets.FireBullet;
import com.deco2800.hcg.entities.bullets.GrassBullet;
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
    public Corpse(float posX, float posY, float posZ) {
        super(posX, posY, posZ, 0.7f, 0.7f, 1, 1f, 1f, false);
        turret = null;
        this.setThisTexture();
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
    
    /**
     * Adds a bullet to the enemy corpse, if empty
     *
     * @param seed the seed to be added
     * @return true if the seed was added, false if it could not be added
     */
    public boolean plantInside(Bullet bullet) {
    	if(bullet instanceof GrassBullet) {
    		return plantInside(new Seed(Seed.Type.GRASS));
    	} else if(bullet instanceof FireBullet) {
    		return plantInside(new Seed(Seed.Type.FIRE));
    	} else {
    		return plantInside(new Seed(Seed.Type.SUNFLOWER));
    	}
    }
}
