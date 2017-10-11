package com.deco2800.hcg.managers;

import com.deco2800.hcg.entities.worldmap.WorldStack;
import com.deco2800.hcg.worldmapui.LevelStore;
import com.deco2800.hcg.worldmapui.WorldStackGenerator;

/**
 * Manager encapsulating the world stack generator.
 */
public class WorldManager extends Manager {
	
	/**
	 * The WorldStackGenerator instance.
	 */
	WorldStackGenerator worldStackGenerator = new WorldStackGenerator(new LevelStore().getLevels());

	/**
	 * Sets the generator seed for the map generator used.
	 * @param seed
	 *     The seed to set to.
	 */
	public void setGeneratorSeed(int seed) {
		worldStackGenerator.setGeneratorSeed(seed);
	}
	
	/**
	 * Generates and sets the game's world stack.
	 */
	public void generateAndSetWorldStack() {
        WorldStack worldStack = worldStackGenerator.generateWorldStack();
        GameManager.get().setWorldStack(worldStack);
	}
	
}
