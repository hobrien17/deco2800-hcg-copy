package com.deco2800.hcg.worldmapui;

import java.util.ArrayList;

import com.deco2800.hcg.entities.worldmap.Level;
import com.deco2800.hcg.worlds.World;

public class LevelStore {
	private ArrayList<Level> levelList;

	private static final String DEMO_WORLD = "resources/maps/maps/initial-map-test.tmx";
	public LevelStore() {
		/* See the wiki for the correct way to create and add levels!
		 * https://github.com/UQdeco2800/deco2800-2017-hardcor3gard3ning/wiki/Creating-Maps
		 */
		levelList = new ArrayList<>();
        // Test levels for now. Eventually this will contain all the playable game levels
        levelList.add(new Level(new World(DEMO_WORLD), 0, 1, 1));
        levelList.add(new Level(new World(DEMO_WORLD), 0, 1, 0));
        levelList.add(new Level(new World(DEMO_WORLD), 0, 1, 1));
        levelList.add(new Level(new World(DEMO_WORLD), 0, 1, 2));
		levelList.add(new Level(new World("resources/maps/maps/Safezone.tmx"), 0, 1, 0));
	}
	
	public ArrayList<Level> getLevels() {
		return new ArrayList<>(levelList);
	}

}
