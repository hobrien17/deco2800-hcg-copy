package com.deco2800.hcg.worldMap;

import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertFalse;

import com.deco2800.hcg.worlds.BlankTestWorld;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.deco2800.hcg.entities.worldmap.Level;
import com.deco2800.hcg.entities.worldmap.MapNode;
import com.deco2800.hcg.entities.worldmap.WorldMap;
import com.deco2800.hcg.worldmapui.MapGenerator;
import java.util.*;
public class MapGeneratorTest {
    WorldMap worldMap;
    BlankTestWorld world;
    MapGenerator mg;

    /* What kind of level?
	 * 0 for safe zone level
	 * 1 for standard level
	 * 2 for boss level
	 */
    List<Level> levels = new ArrayList<Level>();
    int numSaveZones = 10; // <- can change;
    int numStandardZones = 15; // <- can change;
    int numBossZones = 2; // <- can change;
    @Before
    public void setup() {
        world = new BlankTestWorld();
        // adding numSaveZones*2 safe zone levels
        for (int i = 0; i < numSaveZones; i++){
            levels.add(new Level(world, 0,0,0));
            levels.add(new Level(world, 1,0,0));
        }
        // adding numStandardZones*2 safe zone levels
        for (int i = 0; i < numStandardZones; i++){
            levels.add(new Level(world, 0,0,1));
            levels.add(new Level(world, 1,0,1));
        }
        // adding numBossZones*2 safe zone levels
        for (int i = 0; i < numBossZones; i++){
            levels.add(new Level(world, 0,0,2));
            levels.add(new Level(world, 1,0,2));
        }
    }

    @Test
    public void testProcedureRandom(){
        /// testing if we can get the same worldmap using the seeds
        mg = new MapGenerator(levels);
        WorldMap world = mg.generateWorldMap();
        // this test in the future should change to assertEquals
        assertNotEquals(world, mg.generateWorldMap(world.getWorldSeed()));
    }


}
