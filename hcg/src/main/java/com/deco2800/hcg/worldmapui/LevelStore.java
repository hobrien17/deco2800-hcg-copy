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
        levelList.add(new Level(new World("resources/maps/maps/initial-map-test.tmx"), 0, 1, 1));
        levelList.add(new Level(new World("resources/maps/maps/initial-map-test.tmx"), 0, 1, 0));
        levelList.add(new Level(new World("resources/maps/maps/initial-map-test.tmx"), 0, 1, 1));
        levelList.add(new Level(new World("resources/maps/maps/initial-map-test.tmx"), 0, 1, 2));
	}
	
	public ArrayList<Level> getLevels() {
		return new ArrayList<>(levelList);
	}
}