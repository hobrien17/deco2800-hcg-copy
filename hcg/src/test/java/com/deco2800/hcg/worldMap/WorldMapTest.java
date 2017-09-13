package com.deco2800.hcg.worldMap;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.deco2800.hcg.entities.worldmap.Level;
import com.deco2800.hcg.entities.worldmap.MapNode;
import com.deco2800.hcg.entities.worldmap.WorldMap;
import com.deco2800.hcg.worlds.World;

public class WorldMapTest {

    WorldMap worldMap;

    @Before
    public void setup() {
    	Level newLevel = new Level(new World(), 0, 0, 0);
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
