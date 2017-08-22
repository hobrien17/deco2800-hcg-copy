package com.deco2800.hcg.entities.worldmap;

import java.util.ArrayList;
import java.util.List;

// Somewhat incomplete class for now.
// Will add more as game concept becomes more clear.
public class WorldMap {
	private int mapType; // <- possibility of map biomes?
	private List<MapNode> containedNodes;
	private String mapBackgroundTexture;
	private int mapPosition; // <- map position in the collection of worlds
	private String mapSeed;
	private int mapRowNumber;
	private int mapColumnNumber;
	
	public WorldMap(int type, String texture, int position, int rows, int columns) {
		mapType = type;
		mapBackgroundTexture = texture;
		mapPosition = position;
		// blank initial seed
		mapSeed = "";
		mapRowNumber = rows;
		mapColumnNumber = columns;
		containedNodes = new ArrayList<>();
	}

	// temp constuctor only. Add so that worldmapui/MapGenerator class cancompile
	public WorldMap(){

	}


	// ACCESSOR METHODS
	public int getMapType() {
		return mapType;
	}
	
	public List<MapNode> getContainedNodes() {
		return new ArrayList<>(containedNodes);
	}
	
	public String getMapTexture() {
		return mapBackgroundTexture;
	}
	
	public int getMapPosition() {
		return mapPosition;
	}
	
	public String getMapSeed() {
		return mapSeed;
	}
	
	public int getMapRows() {
		return mapRowNumber;
	}
	
	public int getMapColumns() {
		return mapColumnNumber;
	}
	
	// MANIPULATING METHODS
	public void addContainedNode(MapNode node) {
		if(!(containedNodes.contains(node))) {
			containedNodes.add(node);
		}
	}
	
	public void addContainedNodeCollection(List<MapNode> nodeList) {
		for(MapNode node : nodeList) {
			if(!(containedNodes.contains(node))) {
				containedNodes.add(node);
			}
		}
	}
	
	public void changeMapTexture(String newTexture) {
		mapBackgroundTexture = newTexture;
	}
	
	public void addSeed(String seed) {
		mapSeed = seed;
	}
}
