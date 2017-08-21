package com.deco2800.hcg.entities.worldmap;

import java.util.ArrayList;
import java.util.List;

// Somewhat incomplete class for now.
// Will add more as game concept becomes more clear.
public class WorldMap {
	private int mapType; // <- possibility of map biomes?
	private List<MapNode> containedNodes;
	private String mapTexture;
	private int mapPosition; // <- map position in the collection of worlds
	private String mapSeed;
	
	public WorldMap(int type, String texture, int position, String seed) {
		mapType = type;
		mapTexture = texture;
		mapPosition = position;
		mapSeed = seed;
		containedNodes = new ArrayList<>();
	}
	
	// ACCESSOR METHODS
	public int getMapType() {
		return mapType;
	}
	
	public List<MapNode> getContainedNodes() {
		return new ArrayList<>(containedNodes);
	}
	
	public String getMapTexture() {
		return mapTexture;
	}
	
	public int getMapPosition() {
		return mapPosition;
	}
	
	public String getMapSeed() {
		return mapSeed;
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
		mapTexture = newTexture;
	}
}
