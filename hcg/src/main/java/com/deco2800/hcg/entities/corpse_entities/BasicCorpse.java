package com.deco2800.hcg.entities.corpse_entities;

import com.deco2800.hcg.entities.garden_entities.seeds.Seed;
import com.deco2800.hcg.entities.turrets.AbstractTurret;

public class BasicCorpse extends Corpse {


    public BasicCorpse(float posX, float posY, float posZ, String texture) {
        super(posX, posY, posZ,texture);
        this.setThisTexture();
    }
    
    public void setThisTexture() {
    	if(turret == null) {
    		this.setTexture("tree");
    	} else {
    		this.setTexture(turret.getThisTexture());
    	}

    }

    @Override
    public boolean plantInside(Seed seed) {
        if(this.turret == null) {
            turret = seed.getNewTurret(this);
            this.setThisTexture();
            return true;
        }
        // Check if the enemy corpse does not contain special item
        return false;

    }

}
