package com.deco2800.hcg.entities.worldmap;

import java.util.ArrayList;
import java.util.List;

// Somewhat incomplete class for now.
// Will add more as game concept becomes more clear.
public class WorldMap {
	private int worldType; // <- possibility of map biomes?
	private List<MapNode> containedNodes;
	private String worldBackgroundTexture;
	private int worldPosition; // <- world position in the collection of worlds
	private String worldSeed;
	private int worldRowNumber;
	private int worldColumnNumber;
	
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
	public int getWorldType() {
		return worldType;
	}
	
	public List<MapNode> getContainedNodes() {
		return new ArrayList<>(containedNodes);
	}
	
	public String getWorldTexture() {
		return worldBackgroundTexture;
	}
	
	public int getWorldPosition() {
		return worldPosition;
	}
	
	public String getWorldSeed() {
		return worldSeed;
	}
	
	public int getWorldRows() {
		return worldRowNumber;
	}
	
	public int getWorldColumns() {
		return worldColumnNumber;
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
	
	public void changeWorldTexture(String newTexture) {
		worldBackgroundTexture = newTexture;
	}
	
	public void addSeed(String seed) {
		worldSeed = seed;
	}
}
