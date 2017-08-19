package com.deco2800.hcg.entities.garden_entities.plants;

/**
 * Represents a basic plant which drops basic loot
 * 
 * @author Henry O'Brien
 *
 */
public class BasicPlant extends AbstractGardenPlant {

	public BasicPlant(float posX, float posY, float posZ) {
		super(posX, posY, posZ, 0.5f, 0.5f, 1, 1, 1, false, Stage.SPROUT);
	}

	@Override
	public void setThisTexture() {
		switch (this.getStage()) {
		case SPROUT:
			this.setTexture("plant");
			break;
		case SMALL:
			this.setTexture("plant2");
			break;
		case LARGE:
			this.setTexture("tree");
			break;
		}

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

	@Override
	public void onTick(long gameTickCount) {
		// TODO Auto-generated method stub
		
	}

}
