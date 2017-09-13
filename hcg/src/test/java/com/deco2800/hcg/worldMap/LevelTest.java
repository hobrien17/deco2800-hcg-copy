package com.deco2800.hcg.worldMap;

import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertFalse;

import com.deco2800.hcg.worlds.World;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.deco2800.hcg.entities.worldmap.Level;
import com.deco2800.hcg.entities.worldmap.MapNode;
import com.deco2800.hcg.entities.worldmap.WorldMap;

public class LevelTest {

    WorldMap worldMap;
    World world;
    Level tmpLevel;
    @Before
    public void setup() {
        world = new World();

    }

    @Test
    public void testingBasicAtrtibutes() {

        tmpLevel = new Level(world, 1, 5, 3);
        assertEquals(3, tmpLevel.getLevelType());
        assertEquals(1, tmpLevel.getWorldType());
        assertEquals(5, tmpLevel.getDifficulty());
        assertEquals(world, tmpLevel.getWorld());
    }

    @Test
    public void testingBasicMethods() {
        tmpLevel = new Level(world, 0, 2, 1);
        tmpLevel.changeDifficulty(4);
        assertEquals(4, tmpLevel.getDifficulty());
        tmpLevel.changeLevelType(3);
        assertEquals(3,tmpLevel.getLevelType());
        tmpLevel.changeWorldType(4);
        assertEquals(4,tmpLevel.getWorldType());

        World newWorld = new World();
        tmpLevel.setWorld(newWorld);
        assertEquals(newWorld, tmpLevel.getWorld());
    }



}
