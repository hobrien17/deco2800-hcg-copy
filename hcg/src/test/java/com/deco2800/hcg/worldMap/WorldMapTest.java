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
    List<MapNode> nodeList;
    @Before
    public void setup() {
    	Level newLevel = new Level(new World(), 0, 0, 0);
    	MapNode node = new MapNode(0, 0, 0, newLevel, false);
    	nodeList = new ArrayList<>();
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

    @Test
    public void testGetContainedNodes(){
        assertEquals(1, worldMap.getContainedNodes().size());
    }

    @Test
    public void testGetContainedNodesDeepCopy(){
//        System.out.println(nodeList == worldMap.getContainedNodes());
        assertEquals(false, worldMap.getContainedNodes() == nodeList);
    }

    @Test
    public void testAddContainedNode(){
        assertEquals(1, worldMap.getContainedNodes().size());
        Level newLevel = new Level(new World(), 0, 0, 0);
        MapNode newNode = new MapNode(0, 0, 0, newLevel, false);
        worldMap.addContainedNode(newNode);
        assertEquals(2, worldMap.getContainedNodes().size());

        // adding existing Node. should not be added to the contained node
        worldMap.addContainedNode(newNode);
        assertEquals(2, worldMap.getContainedNodes().size());

        newNode = new MapNode(0, 0, 0, newLevel, false);
        worldMap.addContainedNode(newNode);
        assertEquals(3, worldMap.getContainedNodes().size());

    }

    @Test
    public void testAddContainedNodeCollection() {
        List<MapNode> nodeList = new ArrayList<MapNode>();
        Level newLevel = new Level(new World(), 0, 0, 0);
        int numTest = 4;
        for (int i = 0; i < 4; i++) {
            nodeList.add(new MapNode(0, 0, 0, newLevel, false));
        }
        worldMap.addContainedNodeCollection(nodeList);
        assertEquals(1 + numTest, worldMap.getContainedNodes().size());

    }

    @Test
    public void testChangeWorldTexture() {
        worldMap.changeWorldTexture("yolo");
        assertEquals("yolo", worldMap.getWorldTexture());
        worldMap.changeWorldTexture("");
        assertEquals("", worldMap.getWorldTexture());

    }




}
