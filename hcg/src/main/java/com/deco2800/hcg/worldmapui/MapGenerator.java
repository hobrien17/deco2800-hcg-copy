package com.deco2800.hcg.worldmapui;

import com.deco2800.hcg.entities.worldmap.WorldMap;
import com.deco2800.hcg.entities.worldmap.Level;
import com.deco2800.hcg.entities.worldmap.MapNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

// INCOMPLETE CLASS! UPLOADED FOR GROUP EDITTING

// Adding temp constructor so that it can compile
public class MapGenerator {
	private int DEFAULT_COLUMN_COUNT = 10; // <- default number of columns in a world (can be changed later)
	private int DEFAULT_ROW_COUNT = 10; // <- default number of rows in a world (can be changed later)
	
	private List<Level> levelsMaster;
	private List<Level> levelsOfType;
	private List<Level> levelsNotOfType;
	private List<Level> levelsBoss;
	
	public MapGenerator(List<Level> levelSet) {
		levelsMaster = levelSet;
		levelsBoss = new ArrayList<>();
		levelsOfType = new ArrayList<>();
		levelsNotOfType = new ArrayList<>();
	}
	
	// generates a random world based on the provided set of game levels
	public WorldMap generateWorldMap() {
		int rowCount = DEFAULT_ROW_COUNT;
		int columnCount = DEFAULT_COLUMN_COUNT; 
		Random rand = new Random();
		int worldType = rand.nextInt(1); // <- currently only generates a 0. Change later for biomes
		return generateNewMap(rowCount, columnCount, worldType); 
	}
	
	// generates a random world based on the provided world type (biome number) and the provided set of game levels
	public WorldMap generateWorldMap(int worldType) {
		int rowCount = DEFAULT_ROW_COUNT;
		int columnCount = DEFAULT_COLUMN_COUNT;
		return generateNewMap(rowCount, columnCount, worldType);
	}
	
	// generates a world from the provided seed value. levelSet must contain all levels found in the seed
	public WorldMap generateWorldMap(String seed) {
		return generateMapFromSeed(seed);
	}
	
	/* generates a world with the provided number of rows and columns for nodes. Not all x, y positions are filled with
	 * nodes but this allows for longer/shorter games
	 */
	public WorldMap generateWorldMap(int rowNumber, int columnNumber) {
		Random rand = new Random();
		int worldType = rand.nextInt(1); // <- currently only generates a 0. Change later for biomes
		return generateNewMap(rowNumber, columnNumber, worldType);
	}
	
	//generates a world with the provided number of rows and columns for nodes and with the provided world type
	public WorldMap generateWorldMap(int rowNumber, int columnNumber, int worldType) {
		return generateNewMap(rowNumber, columnNumber, worldType);
	}
	
	private WorldMap generateNewMap(int rowCount, int columnCount, int worldType) {
		resetLevelSets(worldType);
		List<MapNode> worldNodes = generateNodes(rowCount, columnCount, worldType);
		/* If a multi-world is introduced, the world position of 0 will need to be changed to a function which returns
		 * the world's position in the stack/collection of worlds. 
		 */
		WorldMap generatedWorld = new WorldMap(worldType, "", 0, rowCount, columnCount, worldNodes);
		generatedWorld.addSeed(generateMapSeed(generatedWorld));
		return generatedWorld;
	}
	
	private WorldMap generateMapFromSeed(String seed) {
		return new WorldMap(); //still need to complete!
	}
	private String generateMapSeed(WorldMap world) {
		return "";

	}
	
	private List<MapNode> generateNodes(int rowNumber, int columnNumber, int worldType) {
		final int MAX_COLUMNS_BEFORE_SAFENODE = 5; // <- change as needed
		Random rand = new Random();
		
		MapNode initialNode = new MapNode(0, rowNumber/2, "", 1, getLevel(), true); //ENTER NODE TEXTURE!!!
		MapNode finalNode = new MapNode(columnNumber - 1, rowNumber/2, "", 3, getBossLevel(), false); //ENTER NODE TEXTURE!!!
		
		int columnsSinceSafeNode = 0;
		List<MapNode> nodeList = new ArrayList<>();
		nodeList.add(initialNode);
		
		for(int i = 1; i < columnNumber - 1; i++) {
			boolean safeNodeInColumn = false;
			int numberOfNodes = rand.nextInt((int) Math.ceil(rowNumber/1.7)) + 1;
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
				if(probability < 5 || columnsSinceSafeNode >= MAX_COLUMNS_BEFORE_SAFENODE) { //adjust for safenode count
					nodeType = 0; //node will be a safenode
					safeNodeInColumn = true;
					columnsSinceSafeNode = 0;
				} else {
					nodeType = 1;  // in the future, will add diffrent node types in here
				}
				MapNode basicNode = new MapNode(i, nodeRow, "", nodeType, getLevel(), false);
				currentOccupiedRows.add(nodeRow);
				nodeList.add(basicNode);
				if(!safeNodeInColumn) {
					columnsSinceSafeNode++;
				}
			}
		}
		nodeList.add(finalNode);
		nodeList = createMapTree(nodeList, columnNumber);
		return nodeList;
	}
	
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
	
	private void resetLevelSets(int worldType) {
		levelsOfType.clear();
		levelsNotOfType.clear();
		levelsBoss.clear();
		for(Level i : levelsMaster) {
			if(i.getLevelType() == 2) {
				levelsBoss.add(i);
			} else if(i.getWorldType() == worldType) {
				levelsOfType.add(i);
			} else {
				levelsNotOfType.add(i);
			}
		}
	}
	
	private Level getBossLevel() {
		if(!(levelsBoss.isEmpty())) {
			Random rand = new Random();
			int index = rand.nextInt(levelsBoss.size());
			return levelsBoss.get(index);
		} else {
			return getLevel();
		}
	}
	
	private Level getLevel() {
		Random rand = new Random();
		
		if(!(levelsOfType.isEmpty())) {
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
			int index = rand.nextInt(levelsMaster.size());
			return levelsMaster.get(index);
		}
	}
}
