package com.deco2800.hcg.worldMap;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;

import com.deco2800.hcg.worlds.AbstractWorld;
import com.deco2800.hcg.worlds.EmptyWorld;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.deco2800.hcg.entities.worldmap.Level;
import com.deco2800.hcg.entities.worldmap.MapNode;
import com.deco2800.hcg.entities.worldmap.WorldMap;
import com.deco2800.hcg.worlds.DemoWorld;

public class MapNodeTest {

    WorldMap worldMap;
    EmptyWorld world;
    Level tmpLevel;
    MapNode tmpNode;
    @Before
    public void setup() {
        world = new EmptyWorld();
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
