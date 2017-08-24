package com.deco2800.hcg.worldMap;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.deco2800.hcg.entities.worldmap.Level;
import com.deco2800.hcg.entities.worldmap.MapNode;
import com.deco2800.hcg.entities.worldmap.WorldMap;
import com.deco2800.hcg.worlds.DemoWorld;

public class WorldMapTest {

    WorldMap worldMap;

    @Before
    public void setup() {
    	DemoWorld world = new DemoWorld();
    	Level newLevel = new Level(world, 0, 0, 0);
    	MapNode node = new MapNode(0, 0, "", 0, newLevel, false);
    	List<MapNode> nodeList = new ArrayList<>();
    	nodeList.add(node);
        worldMap = new WorldMap(0, "sand", 1, 5, 3, nodeList);
    }

    @Test
    public void testAccessors() {
        assertEquals(0, worldMap.getWorldType());
        assertEquals("sand", worldMap.getWorldTexture());
        assertEquals(1, worldMap.getWorldPosition());
        assertEquals("", worldMap.getWorldSeed());
        assertEquals(5, worldMap.getWorldRows());
        assertEquals(3, worldMap.getWorldColumns());
    }

}
