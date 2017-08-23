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
public abstract class AbstractGardenPlant implements Tickable, Lootable {
	
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
	private Pot master;
	
	Map<String, Double> lootRarity;
	
	public AbstractGardenPlant(Pot master) {
		this.stage = Stage.SMALL;
		this.master = master;
		master.setThisTexture();
		setupLoot();
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
		
		master.setThisTexture();
	}
	
	/**
	 * Sets the texture based on the plant's stage of growth
	 */
	public abstract String getThisTexture();
	
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
