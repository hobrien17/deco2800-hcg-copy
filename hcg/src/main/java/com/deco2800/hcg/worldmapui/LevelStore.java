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
        // Test levels for now. Eventually this will contain all the playable game levels
        levelList.add(new Level(new World("resources/maps/maps/level1.tmx"), 0, 1, 1));
        levelList.add(new Level(new World("resources/maps/maps/initial-map-test.tmx"), 0, 1, 1));
        levelList.add(new Level(new World("resources/maps/maps/snow.tmx"), 0, 1, 1));
		levelList.add(new Level(new World("resources/maps/maps/mushroom_map_1.tmx"), 3, 1, 1));
		//levelList.add(new Level(new World("maps/maps/urban_wasteland_1.tmx"), 0, 1, 1));
		//levelList.add(new Level(new World("resources/maps/maps/level2.tmx"), 0, 1, 1));
	}
	
	public ArrayList<Level> getLevels() {
		return new ArrayList<>(levelList);
	}
}
