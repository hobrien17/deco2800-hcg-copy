package com.deco2800.hcg.entities;

import com.deco2800.hcg.util.Box3D;

/**
 * Represents a plant in the garden
 * Used as a shell for more specific types of plants
 * 
 * @author Henry O'Brien
 */
public abstract class AbstractGardenPlant extends AbstractEntity implements Clickable, Tickable, Selectable {
	
	/**
	 * Represents a stage of growth
	 * Can be either SPROUT, SMALL, or LARGE
	 * New plants should start as SPROUTs
	 * 
	 * More stages can be added if needed (remember to update relevant methods)
	 */
	public enum Stage {
		SPROUT, 
		SMALL, 
		LARGE
	}
	
	private Stage stage;

	public AbstractGardenPlant(Box3D position, float xRenderLength, float yRenderLength, boolean centered, 
			Stage stage) {
		super(position, xRenderLength, yRenderLength, centered);
		this.stage = stage;
	}
	
	public AbstractGardenPlant(float posX, float posY, float posZ, float xLength, float yLength, float zLength,
			  float xRenderLength, float yRenderLength, boolean centered, Stage stage) {
		super(posX, posY, posZ, xLength, yLength, zLength, xRenderLength, yRenderLength, centered);
		this.stage = stage;
	}
	
	public AbstractGardenPlant(float posX, float posY, float posZ, float xLength, float yLength, float zLength,
			  float xRenderLength, float yRenderLength, boolean centered) {
		this(posX, posY, posZ, xLength, yLength, zLength, xRenderLength, yRenderLength, centered, Stage.SMALL);
	}
	
	public AbstractGardenPlant(float posX, float posY, float posZ, float xLength, float yLength, float zLength) {
		this(posX, posY, posZ, xLength, yLength, zLength, xLength, yLength, false, Stage.SMALL);
	}
	
	/**
	 * Gets the plant's stage of growth
	 * 
	 * @return The current stage of growth (i.e. SPROUT, SMALL, or LARGE)
	 */
	public Stage getStage() {
		return stage;
	}
	
	/**
	 * Advances the plant's stage of growth, if possible
	 */
	public void advanceStage() {
		switch(stage) {
		case SPROUT:
			stage = Stage.SMALL;
			break;
		case SMALL:
			stage = Stage.LARGE;
			break;
		case LARGE:
			//Plant is at maximum growth, do nothing
			break;
		}
	}
	
	/**
	 * Sets the texture based on the plant's stage of growth
	 */
	public abstract void setThisTexture();
	
	/**
	 * Gets a list of all possible loot dropped by this tree
	 * Replace return type with loot when implemented
	 * 
	 * @return An array of all possible loot dropped by this plant
	 */
	public abstract Object[] getLoot();
	
	//Rest to be implemented later

}
