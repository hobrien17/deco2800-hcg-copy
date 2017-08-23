package com.deco2800.hcg.worldmapui;

import com.deco2800.hcg.entities.worldmap.WorldMap;
import com.deco2800.hcg.entities.worldmap.Level;
import com.deco2800.hcg.entities.worldmap.MapNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// INCOMPLETE CLASS! UPLOADED FOR GROUP EDITTING

// Adding temp constructor so that it can compile
public class MapGenerator {
	private List<Level> levelsMaster;
	private List<Level> levelsOfType;
	private List<Level> levelsNotOfType;
	private List<Level> levelsUsed;
	
	public MapGenerator(List<Level> levelSet) {
		levelsMaster = levelSet;
		levelsOfType = new ArrayList<>();
		levelsNotOfType = new ArrayList<>();
		levelsUsed = new ArrayList<>();
	}
	
	// generates a random world based on the provided set of game levels
	public WorldMap generateWorldMap() {
		int rowCount = 10; // <- default number of rows (can be changed later)
		int columnCount = 10; // <- default number of columns (can be changed later)
		Random rand = new Random();
		int worldType = rand.nextInt(0); // <- currently only generates a 0. Change later for biomes
		List<MapNode> worldNodes = generateNodes(rowCount, columnCount, worldType);
		return new WorldMap();
	}
	
	// generates a random world based on the provided world type (biome number) and the provided set of game levels
	public WorldMap generateWorldMap(int worldType, List<Level> levelSet) {
		return new WorldMap();
	}
	
	// generates a world from the provided seed value. levelSet must contain all levels found in the seed
	public WorldMap generateWorldMap(String seed, List<Level> levelSet) {
		return new WorldMap();
	}
	
	/* generates a world with the provided number of rows and columns for nodes. Not all x, y positions are filled with
	 * nodes but this allows for longer/shorter games
	 */
	public WorldMap generateWorldMap(int rowNumber, int columnNumber, List<Level> levelSet) {
		Random rand = new Random();
		int worldType = rand.nextInt(0); // <- currently only generates a 0. Change later for biomes
		return new WorldMap();

	}
	
	//generates a world with the provided number of rows and columns for nodes and with the provided world type
	public WorldMap generateWorldMap(int rowNumber, int columnNumber, int worldType, List<Level> levelSet) {
		return new WorldMap();

	}
	
	private String generateMapSeed(WorldMap world) {
		return "";

	}
	
	private List<MapNode> generateNodes(int rowNumber, int columnNumber, int worldType) {
		Random rand = new Random();
		
		MapNode initialNode = new MapNode(0, rowNumber/2, "", 2, levelSet[rand.nextInt()]); //ENTER NODE TEXTURE!!!
		MapNode finalNode = new MapNode(columnNumber - 1, ); //ENTER NODE TEXTURE!!!
		
	}
	
	private void createLevelSets(int worldType) {
		for(Level i : levelsMaster) {
			if(levelsUsed.contains(i)) {
				;
			} else if(i.getWorldType() == worldType) {
				levelsOfType.add(i);
			} else {
				levelsNotOfType.add(i);
			}
		}
	}
	
	private Level getLevel() {
		Random rand = new Random();
		
		if(levelsOfType.isEmpty()) {
			int index = rand.nextInt(levelsNotOfType.size());
			Level levelReturned = levelsNotOfType.get(index);
			levelsNotOfType.remove(index);
			return levelReturned;
		} else {
			
		}
	}
}
