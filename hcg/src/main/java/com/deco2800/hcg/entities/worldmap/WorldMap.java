package com.deco2800.hcg.entities.worldmap;

import java.util.ArrayList;
import java.util.List;

// Somewhat incomplete class for now.
// Will add more as game concept becomes more clear.

/**
 * Holds information on the world map. At current, a WorldStack holds a collection of WorldMaps; each of which holds a
 * collection of nodes which each contain a level for the player to play.
 * WorldMap objects contain the world's type (for the possibility of biomes (jungle map, city map, desert map, etc.)),
 * the nodes which make up the map, the background texture of the map, the position of the map within the WorldStack,
 * the seed which can be used to generate the current map and the number of rows and columns which the map supports.
 */
public class WorldMap {
	private int worldType; // <- possibility of map biomes?
	private List<MapNode> containedNodes;
	private int worldPosition; // <- world position in the collection of worlds
	private int worldSeed;
	private int worldRowNumber;
	private int worldColumnNumber;
	private boolean unlocked;
	private boolean completed;
	
	/**
	 * Initialises the WorldMap object using the specified parameters.	
	 * @param type
	 *     The world's type (not 100% confirmed yet so for now it is not used)
	 * @param rows
	 *     The number of rows that the WorldMap supports
	 * @param columns
	 *     The number of columns that the WorldMap supports
	 * @param nodeList
	 *     The nodes which make up the WorldMap's playable game areas
	 */
	public WorldMap(int type, int rows, int columns, List<MapNode> nodeList) {
		worldType = type;
		worldRowNumber = rows;
		worldColumnNumber = columns;
		containedNodes = nodeList;
		completed = false;
		unlocked = false;
	}

	// ACCESSOR METHODS
	
	/**
	 * Gets the world's type.
	 * @return
	 *     Returns the world's current biome type
	 */
	public int getWorldType() {
		return worldType;
	}
	
	/**
	 * Gets the world's contained nodes.
	 * @return
	 *     Returns a list of the world's contained nodes
	 */
	public List<MapNode> getContainedNodes() {
		return new ArrayList<>(containedNodes);
	}
	
	/**
	 * Gets the world's position in the WorldStack.
	 * @return
	 *     Returns the world's current position in the WorldStack (0 being the top position)
	 */
	public int getWorldPosition() {
		return worldPosition;
	}
	
	/**
	 * Gets the world's seed.
	 * @return
	 *     Returns the world's generated seed value
	 */
	public int getWorldSeed() {
		return worldSeed;
	}
	
	/**
	 * Gets the world's number of rows.
	 * @return
	 *     Returns the number of rows supported by the WorldMap
	 */
	public int getWorldRows() {
		return worldRowNumber;
	}
	
	/**
	 * Gets the world's number of columns.
	 * @return
	 *     Returns the number of columns supported by the WorldMap
	 */
	public int getWorldColumns() {
		return worldColumnNumber;
	}
	
	// MANIPULATING METHODS
	
	/**
	 * Adds a node to the list of nodes contained within the world.
	 * @param node
	 *     The node to add to the list
	 */
	public void addContainedNode(MapNode node) {
		if(!(containedNodes.contains(node))) {
			containedNodes.add(node);
		}
	}
	
	/**
	 * Adds a collection of nodes to the list of nodes contained within the world.
	 * @param nodeList
	 *     The collections of nodes to add to the list
	 */
	public void addContainedNodeCollection(List<MapNode> nodeList) {
		for(MapNode node : nodeList) {
			if(!(containedNodes.contains(node))) {
				containedNodes.add(node);
			}
		}
	}
	
	/**
	 * Swaps out the nodes in this WorldMap to the ones specified in the parameter.
	 * @param nodeList
	 * 		The nodes to change to
	 */
	public void changeContainedNodes(List<MapNode> nodeList) {
		containedNodes = nodeList;
	}
	
	/**
	 * Adds a new seed to the world or changes the current one.
	 * @param seed
	 *     The seed to change to
	 */
	public void addSeed(int seed) {
		worldSeed = seed;
	}
	
	/**
	 * Toggles the completion status of the WorldMap.
	 */
	public void toggleCompleted() {
		completed = !completed;
	}
	
	/**
	 * Gets the completion status of the WorldMap.
	 * @return
	 *     Returns the completion status of the WorldMap.
	 */
	public boolean isCompleted() {
		return completed;
	}
	
	/**
	 * Sets the lock status of the WorldMap to be unlocked.
	 */
	public void setUnlocked() {
		unlocked = true;
	}
	
	/**
	 * Gets the lock status of the WorldMap.
	 * @return
	 *     Returns whether the WorldMap is unlocked.
	 */
	public boolean isUnlocked() {
		return unlocked;
	}
	
	/**
	 * Sets the position of the WorldMap in its WorldStack
	 * @param position
	 *     The position to set to.
	 */
	public void setPosition(int position) {
		worldPosition = position;
	}
}
