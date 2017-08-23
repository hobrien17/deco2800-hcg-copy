package com.deco2800.hcg.entities.garden_entities.seeds;

import com.deco2800.hcg.entities.garden_entities.plants.AbstractGardenPlant;

public class SunflowerSeed extends AbstractSeed {

	@Override
	public String getName() {
		return "Sunflower Seed";
	}

	@Override
	public void setTexture(String texture) {
		this.setTexture("Gardening Seed");
	}

	@Override
	public AbstractGardenPlant plant(int xPos, int yPos) {
		// TODO Auto-generated method stub
		return null;
	}

}
