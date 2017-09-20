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

import java.util.ArrayList;
import java.util.List;

public class MapNodeTest {

    WorldMap worldMap;
    World world;
    Level tmpLevel;
    MapNode tmpNode;
    @Before
    public void setup() {
        world = new World();
        tmpLevel = new Level(world, 0, 0, 0);
    }

    @Test
    public void testingBasicAtrtibutes() {

        tmpNode = new MapNode(0, 9, 0, tmpLevel, false);
        assertEquals(0, tmpNode.getNodeColumn());
        assertEquals(9, tmpNode.getNodeRow());
        assertEquals(0, tmpNode.getPreviousNodes().size());
        assertEquals(0, tmpNode.getProceedingNodes().size());
        assertEquals(tmpLevel, tmpNode.getNodeLinkedLevel());
        assertEquals(false, tmpNode.isSelected());

    }

    @Test
    public void testingAddingPreviousNodes() {
        tmpNode = new MapNode(0, 9, 0, tmpLevel, false);
        for (int i = 0; i < 100; i++){
            tmpNode.addPreviousNode(new MapNode(0, 9, 0, tmpLevel, false));
        }

        assertEquals(100, tmpNode.getPreviousNodes().size());
        assertEquals(0, tmpNode.getProceedingNodes().size());

        List<MapNode> nodeList = new ArrayList<MapNode>();
        for (int i = 0; i < 100; i++){
            nodeList.add(new MapNode(0, 9, 0, tmpLevel, false));
        }
        tmpNode.addPreviousNodeCollection(nodeList);
        assertEquals(200, tmpNode.getPreviousNodes().size());


    }

    @Test
    public void testingAddingProceedingNodes() {
        tmpNode = new MapNode(0, 9, 0, tmpLevel, false);
        for (int i = 0; i < 100; i++){
            tmpNode.addProceedingNode(new MapNode(0, 9, 0, tmpLevel, false));
        }
        assertEquals(0, tmpNode.getPreviousNodes().size());
        assertEquals(100, tmpNode.getProceedingNodes().size());

        List<MapNode> nodeList = new ArrayList<MapNode>();
        for (int i = 0; i < 100; i++){
            nodeList.add(new MapNode(0, 9, 0, tmpLevel, false));
        }
        tmpNode.addProceedingNodeCollection(nodeList);
        assertEquals(200, tmpNode.getProceedingNodes().size());
    }
    
    @Test
    public void testIDIncrement() {
    	tmpNode = new MapNode(0, 9, 1, tmpLevel, false);
    	int initialID = tmpNode.getNodeID();
    	MapNode tmpNode1 = new MapNode(0, 9, 1, tmpLevel, false);
    	MapNode tmpNode2 = new MapNode(0, 9, 1, tmpLevel, false);
    	MapNode tmpNode3 = new MapNode(0, 9, 1, tmpLevel, false);
    	MapNode tmpNode4 = new MapNode(0, 9, 1, tmpLevel, false);
    	
    	assertEquals(initialID, tmpNode.getNodeID());
    	assertEquals(++initialID, tmpNode1.getNodeID());
    	assertEquals(++initialID, tmpNode2.getNodeID());
    	assertEquals(++initialID, tmpNode3.getNodeID());
    	assertEquals(++initialID, tmpNode4.getNodeID());
    }

    @Test
    public void testBasicMethods() {
        tmpNode = new MapNode(0, 9, 1, tmpLevel, false);

        tmpNode.selectNode();
        assertEquals(true, tmpNode.isSelected());

        tmpNode.unselectNode();
        assertEquals(false, tmpNode.isSelected());

        tmpNode.discoverNode();
        assertEquals(true, tmpNode.isDiscovered());

        tmpNode.hideNode();
        assertEquals(false, tmpNode.isDiscovered());

        tmpNode.setNodeID(123);
        assertEquals(123, tmpNode.getNodeID());

        Level newLevel = new Level(world, 0, 0, 0);
        tmpNode.changeLinkedLevel(newLevel);
        assertEquals(newLevel, tmpNode.getNodeLinkedLevel());


    }
}
