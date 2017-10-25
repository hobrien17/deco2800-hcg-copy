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

		// Safe Zone levels
		levelList.add(new Level(new World("resources/maps/maps/grass_safeZone_02.tmx"), 0, 1, 0));

		// Normal Levels
		/*levelList.add(new Level(new World("resources/maps/maps/grass_normal_01.tmx"), 2, 4, 1));
		levelList.add(new Level(new World("resources/maps/maps/grass_normal_02.tmx"), 2, 5, 1));
	    levelList.add(new Level(new World("resources/maps/maps/grass_normal_03.tmx"), 2, 5, 1));
	    levelList.add(new Level(new World("resources/maps/maps/grass_normal_04.tmx"), 2, 7, 1));
	    levelList.add(new Level(new World("resources/maps/maps/grass_normal_05.tmx"), 2, 6, 1));
		levelList.add(new Level(new World("resources/maps/maps/grass_normal_06.tmx"), 2, 4, 1));*/
		levelList.add(new Level(new World("resources/maps/maps/grass_normal_07.tmx"), 2, 6, 1));
	    levelList.add(new Level(new World("resources/maps/maps/mastapeice.tmx"), 3,7,1));
	    /*levelList.add(new Level(new World("resources/maps/maps/mushroom_normal_01.tmx"), 3, 8, 1));
		levelList.add(new Level(new World("resources/maps/maps/mushroom_normal_02.tmx"), 3, 8, 1));
		levelList.add(new Level(new World("resources/maps/maps/mushroom_normal_03.tmx"), 3, 9, 1));
		levelList.add(new Level(new World("resources/maps/maps/mushroom_normal_04.tmx"), 3, 8, 1));
		levelList.add(new Level(new World("resources/maps/maps/volcanic_normal_01.tmx"), 3, 8, 1));
		levelList.add(new Level(new World("resources/maps/maps/volcanic_normal_03_01.tmx"), 3, 8, 1));*/
		levelList.add(new Level(new World("resources/maps/maps/volcanic_normal_04.tmx"), 3, 8, 1));
		
		// Boss levels (there should only be three! (one for each main biome))
		// Ensure they are in ascending biome order
        levelList.add(new Level(new World("resources/maps/maps/grass_boss_01.tmx"), 2, 7, 2));
        levelList.add(new Level(new World("resources/maps/maps/volcanic_boss_01.tmx"), 3, 9, 2));

	}
	
	public ArrayList<Level> getLevels() {
		return new ArrayList<>(levelList);
	}
}
