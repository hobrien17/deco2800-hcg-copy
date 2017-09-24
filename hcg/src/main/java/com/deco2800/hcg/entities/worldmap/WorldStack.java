package com.deco2800.hcg.entities.worldmap;

import java.util.ArrayList;

/**
 * WorldStack holds the information pertaining to the collection of worlds that the player will play through.
 * It contains the list of world maps that the player can access and the number of worlds in this list.
 * 
 * @author Ivo
 */
public class WorldStack {
	private ArrayList<WorldMap> worldStore;
	private int numberOfWorlds;

	public WorldStack() {
		worldStore = new ArrayList<>();
	}
	
	/**
	 * Get the list of WorldMaps stored in the stack.
	 * @return
	 *     Returns the WorldMaps stored in the stack.
	 */
	public ArrayList<WorldMap> getWorldStack() {
		return new ArrayList<>(worldStore);
	}
	
	/**
	 * Adds the provided WorldMap to the stack.
	 * @param newWorldMap
	 *     The WorldMap to add.
	 */
	public void addWorldToStack(WorldMap newWorldMap) {
		worldStore.add(newWorldMap);
	}
	
	/**
	 * Adds one to the number of worlds stored in the stack.
	 */
	public void incrementNumberOfWorlds() {
		numberOfWorlds++;
	}
	
	/**
	 * Gets the number of worlds contained in the stack.
	 * @return
	 *     Returns the number of worlds in the stack.
	 */
	public int getNumberOfWorlds() {
		return numberOfWorlds;
	}
}