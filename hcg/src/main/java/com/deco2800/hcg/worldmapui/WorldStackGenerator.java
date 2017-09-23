package com.deco2800.hcg.worldmapui;

import java.util.List;

import com.deco2800.hcg.entities.worldmap.Level;
import com.deco2800.hcg.entities.worldmap.WorldMap;
import com.deco2800.hcg.entities.worldmap.WorldStack;

public class WorldStackGenerator {
	private final int NUMBER_OF_WORLDS = 3;
	
	private MapGenerator mapGenerator;
	
	public WorldStackGenerator(List<Level> levelSet) {
		mapGenerator = new MapGenerator(levelSet);
	}
	
	public void setGeneratorSeed(int seed) {
		mapGenerator.setGeneratorSeed(seed);
	}
	
	public WorldStack generateWorldStack() {
		return buildStack(0, 0);
	}
	
	public WorldStack generateWorldStack(int rowNumber, int columnNumber) {
		return buildStack(rowNumber, columnNumber);
	}
	
	private WorldStack buildStack(int rowNumber, int columnNumber) {
		WorldStack worldStack = new WorldStack();
		for(Integer i = 0; i < NUMBER_OF_WORLDS; i++) {
			if(rowNumber == 0 && columnNumber == 0) {
				WorldMap worldMap = mapGenerator.generateWorldMap(i + 1);
				worldMap.setPosition(i);
				worldStack.addWorldToStack(worldMap);
			} else {
				WorldMap worldMap = mapGenerator.generateWorldMap(rowNumber, columnNumber, i + 1);
				worldMap.setPosition(i);
				worldStack.addWorldToStack(worldMap);
			}
			worldStack.incrementNumberOfWorlds();	
		}
		return worldStack;
	}
}
