package com.deco2800.hcg.worldmapui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.deco2800.hcg.entities.worldmap.Level;
import com.deco2800.hcg.entities.worldmap.WorldMap;
import com.deco2800.hcg.entities.worldmap.WorldStack;

public class WorldStackGenerator {
	private final int NUMBER_OF_WORLDS = 5;
	
	private List<Integer> biomeList;
	private MapGenerator mapGenerator;
	
	public WorldStackGenerator(List<Level> levelSet) {
		biomeList = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5));
		mapGenerator = new MapGenerator(levelSet);
	}
	
	public WorldStack generateWorldStack() {
		return buildStack(0, 0);
	}
	
	public WorldStack generateWorldStack(int rowNumber, int columnNumber) {
		return buildStack(rowNumber, columnNumber);
	}
	
	private WorldStack buildStack(int rowNumber, int columnNumber) {
		WorldStack worldStack = new WorldStack();
		Random rand = new Random();
		for(Integer i = 0; i < NUMBER_OF_WORLDS; i++) {
			int index = rand.nextInt(biomeList.size());
			if(rowNumber == 0 && columnNumber == 0) {
				WorldMap worldMap = mapGenerator.generateWorldMap(biomeList.get(index));
				worldMap.setPosition(i);
				worldStack.addWorldToStack(worldMap);
			} else {
				WorldMap worldMap = mapGenerator.generateWorldMap(rowNumber, columnNumber, biomeList.get(index));
				worldMap.setPosition(i);
				worldStack.addWorldToStack(worldMap);
			}
			worldStack.incrementNumberOfWorlds();
			biomeList.remove(index);	
		}
		return worldStack;
	}
}
