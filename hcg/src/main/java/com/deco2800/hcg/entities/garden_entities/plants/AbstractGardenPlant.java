package com.deco2800.hcg.entities.garden_entities.plants;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.managers.GameManager;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.deco2800.hcg.entities.*;
import com.deco2800.hcg.util.Box3D;

/**
 * Represents a plant in the garden
 * Used as a shell for more specific types of plants
 * 
 * @author Henry O'Brien
 */
public abstract class AbstractGardenPlant extends AbstractEntity implements Clickable, Tickable, Selectable, Lootable {
	
	static final Logger LOGGER = LoggerFactory.getLogger(GameManager.class);
	
	/**
	 * Represents a stage of growth
	 * Can be either SPROUT, SMALL, or LARGE
	 * New plants should start as SPROUTs
	 * 
	 * More stages can be added if needed (remember to update relevant methods)
	 */
	enum Stage {
		SPROUT, 
		SMALL, 
		LARGE
	}
	
	private Stage stage;
	
	Map<String, Double> lootRarity;

	public AbstractGardenPlant(Box3D position, float xRenderLength, float yRenderLength, boolean centered, 
			Stage stage) {
		super(position, xRenderLength, yRenderLength, centered);
		this.stage = stage;
		setThisTexture();
		setupLoot();
	}
	
	public AbstractGardenPlant(float posX, float posY, float posZ, float xLength, float yLength, float zLength,
			  float xRenderLength, float yRenderLength, boolean centered, Stage stage) {
		super(posX, posY, posZ, xLength, yLength, zLength, xRenderLength, yRenderLength, centered);
		this.stage = stage;
		setThisTexture();
		setupLoot();
	}
	
	public AbstractGardenPlant(float posX, float posY, float posZ, float xLength, float yLength, float zLength,
			  float xRenderLength, float yRenderLength, boolean centered) {
		this(posX, posY, posZ, xLength, yLength, zLength, xRenderLength, yRenderLength, centered, Stage.SMALL);
	}
	
	public AbstractGardenPlant(float posX, float posY, float posZ, float xLength, float yLength, float zLength) {
		this(posX, posY, posZ, xLength, yLength, zLength, xLength, yLength, false, Stage.SMALL);
	}
	
	@Override
	public void onClick() {
		advanceStage();
	}

	@Override
	public boolean isSelected() {
		// Need to implement
		return false;
	}

	@Override
	public void deselect() {
		// Need to implement

	}

	@Override
	public Button getButton() {
		// Need to implement
		return null;
	}

	@Override
	public void buttonWasPressed() {
		// Need to implement

	}
	
	@Override
	public Map<String, Double> getRarity() {
		// TODO Auto-generated method stub
		return lootRarity;
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
		
		setThisTexture();
	}
	
	/**
	 * Sets the texture based on the plant's stage of growth
	 */
	public abstract void setThisTexture();
	
	/**
	 * Sets up the loot rarity map for a plant
	 */
	abstract void setupLoot();
	
	/**
	 * Generates a random item based on the loot rarity
	 * 
	 * @return A random item string in the plant's loot map
	 */
	abstract String randItem();
	
	//Rest to be implemented later

}
