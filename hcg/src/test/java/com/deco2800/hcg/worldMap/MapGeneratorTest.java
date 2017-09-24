package com.deco2800.hcg.worldMap;

import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertFalse;

import com.deco2800.hcg.worldmapui.LevelStore;
import com.deco2800.hcg.worlds.World;
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
    World world;
    MapGenerator mg;

    /* What kind of level?
	 * 0 for safe zone level
	 * 1 for standard level
	 * 2 for boss level
	 */
//    List<Level> levels = new ArrayList<Level>();
//    int numSaveZones = 10; // <- can change;
//    int numStandardZones = 15; // <- can change;
//    int numBossZones = 2; // <- can change;
    @Before
    public void setup() {
//        world = new World();
//        // adding numSaveZones*2 safe zone levels
//        for (int i = 0; i < numSaveZones; i++){
//            levels.add(new Level(world, 0,0,0));
//            levels.add(new Level(world, 1,0,0));
//        }
//        // adding numStandardZones*2 safe zone levels
//        for (int i = 0; i < numStandardZones; i++){
//            levels.add(new Level(world, 0,0,1));
//            levels.add(new Level(world, 1,0,1));
//        }
//        // adding numBossZones*2 safe zone levels
//        for (int i = 0; i < numBossZones; i++){
//            levels.add(new Level(world, 0,0,2));
//            levels.add(new Level(world, 1,0,2));
//        }
        LevelStore levels = new LevelStore();
        mg = new MapGenerator(levels.getLevels());

    }

    @Test
    public void testGetSeed(){
        /// testing if we can get the same worldmap using the seeds
        mg.setGeneratorSeed(100);
        WorldMap world = mg.generateWorldMap();
        assertEquals(100, world.getWorldSeed());

    }
    @Test
    public void testSameRandom() {
        WorldMap world = mg.generateWorldMap();
        WorldMap tmpWorld = mg.generateWorldMap();

    }
    @Test
    public void testProcedureRandom() {
        WorldMap world = mg.generateWorldMap();
        int seed = world.getWorldSeed();
        mg.setGeneratorSeed(seed);
        WorldMap tmpWorld = mg.generateWorldMap();
        System.out.println(world.getContainedNodes().size() + " " + tmpWorld.getContainedNodes().size());
        assertEquals(world.getContainedNodes().size(), tmpWorld.getContainedNodes().size());
        assertEquals(world.getWorldColumns(), tmpWorld.getWorldColumns());
        assertEquals(world.getWorldRows(), tmpWorld.getWorldRows());

        for (int i = 0; i < world.getContainedNodes().size(); i++) {
            assertEquals(true, world.getContainedNodes().get(i).isSameNode(tmpWorld.getContainedNodes().get(i)));
        }

    }


}
