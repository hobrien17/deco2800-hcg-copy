package com.deco2800.hcg.entities.garden_entities.seeds;

import com.deco2800.hcg.entities.garden_entities.plants.AbstractGardenPlant;

public class FireSeed extends AbstractSeed {

    @Override
    public String getName() {
        return "Fire Seed";
    }

    @Override
    public void setTexture(String texture) {
        this.setTexture("Fire Seed");

    }

    @Override
    public AbstractGardenPlant plant(int xPos, int yPos) {
        // TODO Auto-generated method stub
        return null;
    }

}
