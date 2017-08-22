package com.deco2800.hcg.worldmapui;

import com.deco2800.hcg.entities.worldmap.WorldMap;
import com.deco2800.hcg.entities.worldmap.Level;

import java.util.ArrayList;
import java.util.List;

// INCOMPLETE CLASS! UPLOADED FOR GROUP EDITTING

// Adding temp constructor so that it can compile
public class MapGenerator {
	// generates a random world based on the provided set of game levels
	public WorldMap generateWorldMap(List<Level> levelSet) {
		return new WorldMap();
	}
	
	// generates a random world based on the provided world type (biome number) and the provieded set of game levels
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
		return new WorldMap();

	}
	
	//generates a world with the provided number of rows and columns for nodes and with the provided world type
	public WorldMap generateWorldMap(int rowNumber, int columnNumber, int worldType, List<Level> levelSet) {
		return new WorldMap();

	}
	
	private String generateMapSeed(WorldMap world) {
		return "";

	}
}
