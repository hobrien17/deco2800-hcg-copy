package com.deco2800.hcg.worldMap;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.deco2800.hcg.entities.worldmap.WorldMap;

public class WorldMapTest {

    WorldMap worldMap;

    @Before
    public void setup() {
        worldMap = new WorldMap(0, "sand", 1, 5, 3);
    }

    @Test
    public void testAccessors() {
        assertEquals(0, worldMap.getMapType());
        assertEquals("sand", worldMap.getMapTexture());
        assertEquals(1, worldMap.getMapPosition());
        assertEquals("", worldMap.getMapSeed());
        assertEquals(5, worldMap.getMapRows());
        assertEquals(3, worldMap.getMapColumns());
    }

}
