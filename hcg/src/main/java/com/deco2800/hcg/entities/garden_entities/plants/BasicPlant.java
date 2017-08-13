package com.deco2800.hcg.entities.garden_entities.plants;

/**
 * Represents a basic plant which drops basic loot
 * 
 * @author Henry O'Brien
 *
 */
public class BasicPlant extends AbstractGardenPlant {

	public BasicPlant(float posX, float posY, float posZ, float xLength, float yLength, float zLength) {
		super(posX, posY, posZ, xLength, yLength, zLength);
	}

	@Override
	public void setThisTexture() {
		// Need to implement

	}

	@Override
	public Object[] getLoot() {
		// Need to implement
		return null;
	}

	@Override
	public Object[] harvest() {
		// Need to implement
		return null;
	}

}
