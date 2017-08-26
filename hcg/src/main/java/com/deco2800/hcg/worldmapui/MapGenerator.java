package com.deco2800.hcg.worldmapui;

import com.deco2800.hcg.entities.worldmap.WorldMap;
import com.deco2800.hcg.entities.worldmap.Level;
import com.deco2800.hcg.entities.worldmap.MapNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to generate a new WorldMap instance based on a set of parameters.
 * The MapGenerator class can be used similar to a factory in that multiple calls to the generateWorldMap method will
 * produce different maps each time. There are a number of different parameters that can be changed both within the
 * MapGenerator class and with different uses of the generateWorldMap method to produce different types of worlds.
 */
public class MapGenerator {
	static final Logger LOGGER = LoggerFactory.getLogger(MapGenerator.class);
	
	private final int DEFAULT_COLUMN_COUNT = 10; // <- default number of columns in a world (can be changed later)
	private final int DEFAULT_ROW_COUNT = 10; // <- default number of rows in a world (can be changed later)
	private final int MAX_COLUMNS_BEFORE_SAFENODE = 5; // <- change as needed
	private final int MIN_NODES_PER_COLUMN = 2; // <- be very careful with this value. Should never exceed rowNumber/2
	private final int SAFE_NODE_PROBABILITY = 5; // <- probability that a generated node will be a safe node in %
	
	private List<Level> levelsMaster;
	private List<Level> levelsOfType;
	private List<Level> levelsNotOfType;
	private List<Level> levelsBoss;
	private List<Level> levelsNonBoss;
	
	/**
	 * Initialises the MapGenerator class.
	 * @param levelSet
	 *     The list of levels that the MapGenerator can choose from. Should generally contain a large number (the full
	 *     set) of levels and a number of boss levels. If the list does not contain enough levels for the MapGenerator
	 *     to use, the MapGenerator will start to reuse levels.
	 */
	public MapGenerator(List<Level> levelSet) {
		levelsMaster = levelSet;
		levelsBoss = new ArrayList<>();
		levelsOfType = new ArrayList<>();
		levelsNotOfType = new ArrayList<>();
	}
	
	/**
	 * Generates a random world based on the provided set of game levels. Uses default values for row and column count
	 * and for the world type.
	 * @return
	 *     Returns the generated world map
	 */
	public WorldMap generateWorldMap() {
		int rowCount = DEFAULT_ROW_COUNT;
		int columnCount = DEFAULT_COLUMN_COUNT; 
		int worldType = 0; // <- no worldType means any random levels
		return generateNewMap(rowCount, columnCount, worldType); 
	}
	
	/**
	 * Generates a random world based on the provided set of game levels. Uses default values for row and column count
	 * and a random value for the world type.
	 * @param worldType
	 *     The type of world to generate. A worldType of 0 will produce a map with no biome/theme and will choose random
	 *     levels from the provided level set. Different worldType numbers will result in different world themes (jungle,
	 *     desert, city, etc.). Current biomes to choose from:
	 *         0 - no biome
	 *         *** NO OTHER BIOMES YET ***
	 * @return
	 *     Returns the generated world map
	 */
	public WorldMap generateWorldMap(int worldType) {
		int rowCount = DEFAULT_ROW_COUNT;
		int columnCount = DEFAULT_COLUMN_COUNT;
		return generateNewMap(rowCount, columnCount, worldType);
	}

	/**
	 * Generates a world from the provided seed value. levelSet must contain all levels found in the seed's world.
	 * @param seed
	 *     The seed to generate the world from
	 * @return
	 *     Returns the generated world map
	 */
	public WorldMap generateWorldMap(String seed) {
		return generateMapFromSeed(seed);
	}
	
	/**
	 * Generates a random world based on the provided set of game levels. Uses the provided row and column numbers and
	 * a random value for the world type.
	 * @param rowNumber
	 *     The number of rows that the map will have for node positions
	 * @param columnNumber
	 *     The number of columns that the map will have for node positions
	 * @return
	 *     Returns the generated world map
	 */
	public WorldMap generateWorldMap(int rowNumber, int columnNumber) {
		int worldType = 0; // <- no worldType means any random levels
		return generateNewMap(rowNumber, columnNumber, worldType);
	}
	
	/**
	 * Generates a random world based on the provided set of game levels. Uses the provided row and column numbers and
	 * for the world type.
	 * @param worldType
	 *     The type of world to generate. A worldType of 0 will produce a map with no biome/theme and will choose random
	 *     levels from the provided level set. Different worldType numbers will result in different world themes (jungle,
	 *     desert, city, etc.). Current biomes to choose from:
	 *         0 - no biome
	 *         *** NO OTHER BIOMES YET ***
	 * @param rowNumber
	 *     The number of rows that the map will have for node positions
	 * @param columnNumber
	 *     The number of columns that the map will have for node positions
	 * @return
	 *     Returns the generated world map
	 */
	public WorldMap generateWorldMap(int rowNumber, int columnNumber, int worldType) {
		return generateNewMap(rowNumber, columnNumber, worldType);
	}
	
	/**
	 * Used to randomly generate a map based on the specified parameters.
	 * @param rowCount
	 *     The number of rows to generate in the map
	 * @param columnCount
	 *     The number of columns to generate in the map
	 * @param worldType
	 *     The world type to use for the map
	 * @return
	 *     Returns the generated world map based on the specified parameters
	 */
	private WorldMap generateNewMap(int rowCount, int columnCount, int worldType) {
		resetLevelSets(worldType);
		List<MapNode> worldNodes = generateNodes(rowCount, columnCount, worldType);
		/* Optional to view the produced node map */
		for(MapNode node : worldNodes) {
			LOGGER.info(node.toString());
		}
		/* If a multi-world/WorldStack is introduced, the world position of 0 will need to be changed to a function 
		 * which returns the world's position in the stack/collection of worlds. 
		 */
		WorldMap generatedWorld = new WorldMap(worldType, "", 0, rowCount, columnCount, worldNodes);
		generatedWorld.addSeed(generateMapSeed(generatedWorld));
		return generatedWorld;
	}
	
	/**
	 * Generates a new world map from the specified seed string.
	 * @param seed
	 *     The seed to generate from
	 * @return
	 *     Returns the generated world map
	 */
	private WorldMap generateMapFromSeed(String seed) {
		return new WorldMap(); //still need to complete!
	}
	
	/**
	 * Generates a new map seed from the world map provided.
	 * @param world
	 *     The world map object to generate the seed from
	 * @return
	 *     The seed string of the provieded world
	 */
	private String generateMapSeed(WorldMap world) {
		return "";
	}
	
	/**
	 * Used to generate nodes for the map based on the number of rows and columns on the board. Attempts to make all
	 * of the selected node levels match the specified world type but will use other levels if this is not possible.
	 * @param rowNumber
	 *     The number of rows to generate nodes for
	 * @param columnNumber
	 *     The number of columns to generate nodes for
	 * @param worldType
	 *     The world type to choose levels for
	 * @return
	 *     Returns a list of nodes for the world map to use
	 */
	private List<MapNode> generateNodes(int rowNumber, int columnNumber, int worldType) {
		Random rand = new Random();
		
		MapNode initialNode = new MapNode(0, rowNumber/2, "", 1, getLevel(worldType), true); //ENTER NODE TEXTURE!!!
		MapNode finalNode = new MapNode(columnNumber - 1, rowNumber/2, "", 3, getBossLevel(), false); //AS ABOVE!!!
		
		int columnsSinceSafeNode = 0;
		List<MapNode> nodeList = new ArrayList<>();
		nodeList.add(initialNode);
		
		for(int i = 1; i < columnNumber - 1; i++) {
			boolean safeNodeInColumn = false;
			int numberOfNodes = rand.nextInt((int) Math.ceil(rowNumber/2)) + MIN_NODES_PER_COLUMN;
			List<Integer> currentOccupiedRows = new ArrayList<>();
			for(int j = 0; j < numberOfNodes; j++) {
				int nodeRow = 0;
				boolean correctRow = false;
				while(!correctRow) {
					nodeRow = rand.nextInt(rowNumber);
					if(!(currentOccupiedRows.contains(nodeRow))) {
						correctRow = true;
					}
				}
				int probability = rand.nextInt(101) + 1; //random integer between 1 and 100;
				int nodeType;
				if(probability < SAFE_NODE_PROBABILITY || columnsSinceSafeNode >= MAX_COLUMNS_BEFORE_SAFENODE) {
					nodeType = 0; //node will be a safenode
					safeNodeInColumn = true;
					columnsSinceSafeNode = 0;
				} else {
					nodeType = 1;
				}
				MapNode basicNode = new MapNode(i, nodeRow, "", nodeType, getLevel(worldType), false);
				currentOccupiedRows.add(nodeRow);
				nodeList.add(basicNode);
				if(!safeNodeInColumn) {
					columnsSinceSafeNode++;
				}
			}
		}
		nodeList.add(finalNode); //add the boss node to the end of the list
		nodeList = createMapTree(nodeList, columnNumber); //create link mapping of nodes for use in world map
		return nodeList;
	}
	
	/**
	 * Used to create a map tree from the nodes provided. The provided node list must have at least one node in every
	 * column.
	 * @param nodeList
	 *     The list of nodes to link
	 * @param columnNumber
	 *     The number of columns in the world map
	 * @return
	 *     Returns a list of connected map nodes for use in the world map
	 */
	private List<MapNode> createMapTree(List<MapNode> nodeList, int columnNumber) {
		Stack<MapNode> nodeStack = new Stack<>();
		List<List<MapNode>> columnStore = new ArrayList<>();
		for(int i = 0; i < columnNumber; i++) {
			columnStore.add(new ArrayList<>());
		}
		for(MapNode node : nodeList) {
			nodeStack.push(node);
			columnStore.get(node.getNodeColumn()).add(node); //add all nodes to column store based on their column #
		}
		while(!nodeStack.isEmpty()) {
			MapNode treeNode = nodeStack.pop();
			if(treeNode.getNodeType() == 3) { //BOSS NODE!
				for(MapNode node : columnStore.get(treeNode.getNodeColumn() - 1)) {
					/* Make all of the last column's nodes progress to the boss node. Can change later if needed for
					 * gameplay reasons. Pretty simple to change. 
					 */
					node.addProceedingNode(treeNode);
					treeNode.addPreviousNode(node);
				}
			} else if(treeNode.getNodeColumn() == 0) {
				; //do nothing (matches already made)
			} else {
				int minDistance = 99999; //just a large number
				MapNode selectedNode = columnStore.get(treeNode.getNodeColumn() - 1).get(0); // placeholder node
				for(MapNode node : columnStore.get(treeNode.getNodeColumn() - 1)) {
					//determine distance between rows
					int distance = Math.abs(treeNode.getNodeRow() - node.getNodeRow());
					if(distance < minDistance) {
						minDistance = distance;
						selectedNode = node;
					}
				}
				selectedNode.addProceedingNode(treeNode);
				treeNode.addPreviousNode(selectedNode);				
			}
		}
		return nodeList;
	}
	
	/**
	 * Generates lists of levels based on their specifications.
	 * Levels which are of the requested level type are added to the levelsOfType list, levels which are not of the
	 * requested type are added to the levelsNotOfType list and boss levels are added to the levelsBoss list. If no
	 * worldType is selected (worldType is 0), then all of the non-boss levels are added to the levelsNonBoss list.
	 * @param worldType
	 *     The requested world type
	 */
	private void resetLevelSets(int worldType) {
		levelsOfType.clear();
		levelsNotOfType.clear();
		levelsBoss.clear();
		levelsNonBoss.clear();
		for(Level i : levelsMaster) {
			if(i.getLevelType() == 2) {
				levelsBoss.add(i);
			} else if(worldType == 0) {
				levelsNonBoss.add(i);
			} else if(i.getWorldType() == worldType) {
				levelsOfType.add(i);
			} else {
				levelsNotOfType.add(i);
			}
		}
	}
		
	/**
	 * Gets a boss level.
	 * @return
	 *     Returns a random boss level from the levelsBoss list
	 */
	private Level getBossLevel() {
		if(!(levelsBoss.isEmpty())) {
			Random rand = new Random();
			int index = rand.nextInt(levelsBoss.size());
			return levelsBoss.get(index);
		} else {
			return getLevel(0); //return a random level
		}
	}
	
	/**
	 * Gets a standard level.
	 * @param worldType
	 *     The world type to choose levels from
	 * @return
	 *     Returns a random standard level. If possible, a level of the requested world type is provided. Once these run
	 *     out, a level not of the requested type is provided. Once both of these lists are empty, a random level from
	 *     the non boss set will be chosen.
	 */
	private Level getLevel(int worldType) {
		Random rand = new Random();
		if(worldType == 0) {
			int index = rand.nextInt(levelsNonBoss.size());
			Level levelReturned = levelsNonBoss.get(index);
			levelsNonBoss.remove(index);
			return levelReturned;
		} else if(!(levelsOfType.isEmpty())) {
			int index = rand.nextInt(levelsOfType.size());
			Level levelReturned = levelsOfType.get(index);
			levelsOfType.remove(index);
			return levelReturned;
		} else if (!(levelsNotOfType.isEmpty())){
			int index = rand.nextInt(levelsNotOfType.size());
			Level levelReturned = levelsNotOfType.get(index);
			levelsNotOfType.remove(index);
			return levelReturned;
		} else {
			resetLevelSets(0);
			return getLevel(0);
		}
	}
}