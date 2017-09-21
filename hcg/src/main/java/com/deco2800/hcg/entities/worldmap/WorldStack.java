package com.deco2800.hcg.entities.worldmap;

import java.util.ArrayList;

public class WorldStack {
	private ArrayList<WorldMap> worldStore;
	private int numberOfWorlds;
	
	public WorldStack() {
		worldStore = new ArrayList<>();
	}
	
	public ArrayList<WorldMap> getWorldStack() {
		return new ArrayList<>(worldStore);
	}
	
	public void addWorldToStack(WorldMap newWorldMap) {
		worldStore.add(newWorldMap);
	}
	
	public void incrementNumberOfWorlds() {
		numberOfWorlds++;
	}
	
	public int getNumberOfWorlds() {
		return numberOfWorlds;
	}
}