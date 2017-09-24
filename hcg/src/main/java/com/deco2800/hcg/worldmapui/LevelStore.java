package com.deco2800.hcg.worldmapui;

import java.util.ArrayList;

import com.deco2800.hcg.entities.worldmap.Level;
import com.deco2800.hcg.worlds.World;

public class LevelStore {
	private ArrayList<Level> levelList;

	public LevelStore() {
		/* See the wiki for the correct way to create and add levels!
		 * https://github.com/UQdeco2800/deco2800-2017-hardcor3gard3ning/wiki/Creating-Maps
		 */
		levelList = new ArrayList<>();
		
		// Safe node level (there should only be one!)
		// Add safe node level here
		
		// Boss levels (there should only be three! (one for each biome)) Ensure they are in ascending biome order
		
        // All standard levels (do not need any order; just add to the bottom)
        levelList.add(new Level(new World("resources/maps/maps/level1.tmx"), 0, 1, 1));
        levelList.add(new Level(new World("resources/maps/maps/initial-map-test.tmx"), 0, 1, 1));
        levelList.add(new Level(new World("resources/maps/maps/snow.tmx"), 0, 1, 1));
	}
	
	public ArrayList<Level> getLevels() {
		return new ArrayList<>(levelList);
	}
}
