package com.deco2800.hcg.worldmapui;

import java.util.List;

import com.deco2800.hcg.entities.worldmap.Level;
import com.deco2800.hcg.entities.worldmap.WorldMap;
import com.deco2800.hcg.entities.worldmap.WorldStack;

/**
 * Generator class for the WorldStack for the player to play through.
 * @author Ivo
 */
public class WorldStackGenerator {
	private static final int NUMBER_OF_WORLDS = 3;
	
	private MapGenerator mapGenerator;
	
	public WorldStackGenerator(List<Level> levelSet) {
		mapGenerator = new MapGenerator(levelSet);
	}
	
	/**
	 * Sets the generator seed for the map generator used.
	 * @param seed
	 *     The seed to set to.
	 */
	public void setGeneratorSeed(int seed) {
		mapGenerator.setGeneratorSeed(seed);
	}
	
	/**
	 * Generates a new WorldStack from no input parameters.
	 * @return
	 *     Returns a WorldStack with the default number of rows and columns.
	 */
	public WorldStack generateWorldStack() {
		return buildStack(0, 0);
	}
	
	/**
	 * Generates a new WorldStack with WorldMaps which have the specified number of rows and columns.
	 * @param rowNumber
	 *     The row number to generate with.
	 * @param columnNumber
	 *     The column number to generate with.
	 * @return
	 *     Returns a WorldStack with WorldMaps which have the specified number of rows and columns.
	 */
	public WorldStack generateWorldStack(int rowNumber, int columnNumber) {
		return buildStack(rowNumber, columnNumber);
	}
	
	/**
	 * Builds the requested WorldStack object.
	 * @param rowNumber
	 *     The number of rows to use in the WorldMaps
	 * @param columnNumber
	 *     The number of columns to use in the WorldMaps
	 * @return
	 *     The WorldStack object requested
	 */
	private WorldStack buildStack(int rowNumber, int columnNumber) {
		WorldStack worldStack = new WorldStack();
		for(Integer i = 0; i < NUMBER_OF_WORLDS; i++) {
			if(rowNumber == 0 && columnNumber == 0) {
				WorldMap worldMap = mapGenerator.generateWorldMap(i + 1);
				worldMap.setPosition(i);
				worldStack.addWorldToStack(worldMap);
			} else {
				WorldMap worldMap = mapGenerator.generateWorldMap(rowNumber, columnNumber, i + 1);
				worldMap.setPosition(i);
				worldStack.addWorldToStack(worldMap);
			}
			worldStack.incrementNumberOfWorlds();	
		}
		return worldStack;
	}
}
