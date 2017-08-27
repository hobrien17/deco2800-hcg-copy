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

public class MapNodeTest {

    WorldMap worldMap;
    BlankTestWorld world;
    Level tmpLevel;
    MapNode tmpNode;
    @Before
    public void setup() {
        world = new BlankTestWorld();
        tmpLevel = new Level(world, 0, 0, 0);
    }

    @Test
    public void testingBasicAtrtibutes() {

        tmpNode = new MapNode(0, 9, "", 0, tmpLevel, false);
        assertEquals(0, tmpNode.getNodeColumn());
        assertEquals(9, tmpNode.getNodeRow());
        assertEquals(0, tmpNode.getPreviousNodes().size());
        assertEquals(0, tmpNode.getProceedingNodes().size());
        assertEquals(tmpLevel, tmpNode.getNodeLinkedLevel());
    }

    @Test
    public void testingAddingPreviousNodes() {
        tmpNode = new MapNode(0, 9, "", 0, tmpLevel, false);
        for (int i = 0; i < 100; i++){
            tmpNode.addPreviousNode(new MapNode(0, 9, "", 0, tmpLevel, false));
        }
        assertEquals(100, tmpNode.getPreviousNodes().size());
        assertEquals(0, tmpNode.getProceedingNodes().size());
    }

    @Test
    public void testingAddingProceedingNodes() {
        tmpNode = new MapNode(0, 9, "", 0, tmpLevel, false);
        for (int i = 0; i < 100; i++){
            tmpNode.addProceedingNode(new MapNode(0, 9, "", 0, tmpLevel, false));
        }
        assertEquals(0, tmpNode.getPreviousNodes().size());
        assertEquals(100, tmpNode.getProceedingNodes().size());
    }

}
