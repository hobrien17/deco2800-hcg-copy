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
	private String worldBackgroundTexture;
	private int worldPosition; // <- world position in the collection of worlds
	private String worldSeed;
	private int worldRowNumber;
	private int worldColumnNumber;
	
	/**
	 * Initialises the WorldMap object using the specified parameters.	
	 * @param type
	 *     The world's type (not 100% confirmed yet so for now it is not used)
	 * @param texture
	 *     The background texture to display on the screen when the map is being displayed
	 * @param position
	 *     The position of the WorldMap in the WorldStack
	 * @param rows
	 *     The number of rows that the WorldMap supports
	 * @param columns
	 *     The number of columns that the WorldMap supports
	 * @param nodeList
	 *     The nodes which make up the WorldMap's playable game areas
	 */
	public WorldMap(int type, String texture, int position, int rows, int columns, List<MapNode> nodeList) {
		worldType = type;
		worldBackgroundTexture = texture;
		worldPosition = position;
		// blank initial seed
		worldSeed = "";
		worldRowNumber = rows;
		worldColumnNumber = columns;
		containedNodes = nodeList;
	}
	
	//TEMPORARY CONSTRUCTOR! REMOVE ONCE IMPLEMENTATION IS FINISHED!
	public WorldMap() {
		
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
	 * Gets the world's texture.
	 * @return
	 *     Returns the world's current texture
	 */
	public String getWorldTexture() {
		return worldBackgroundTexture;
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
	public String getWorldSeed() {
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
	 * Changes the background texture of the world display.
	 * @param newTexture
	 *     The new texture to change to
	 */
	public void changeWorldTexture(String newTexture) {
		worldBackgroundTexture = newTexture;
	}
	
	/**
	 * Adds a new seed to the world or changes the current one.
	 * @param seed
	 *     The seed to change to
	 */
	public void addSeed(String seed) {
		worldSeed = seed;
	}
}
