package com.deco2800.hcg.worldMap;

import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertFalse;

import com.deco2800.hcg.entities.worldmap.MapNodeEntity;
import com.deco2800.hcg.worlds.World;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.deco2800.hcg.entities.worldmap.Level;
import com.deco2800.hcg.entities.worldmap.MapNode;
import com.deco2800.hcg.entities.worldmap.WorldMap;

import java.util.ArrayList;
import java.util.List;


public class MapNodeEntityTest {

//    WorldMap worldMap;
//    World world;
//    Level tmpLevel;
//    MapNode tmpNode;
//    MapNode fixNode;
//    MapNodeEntity fixNodeEntity;
//    @Before
//    public void setup() {
//        world = new World();
//        tmpLevel = new Level(world, 0, 0, 0);
//        fixNode = new MapNode(1, 0, 0, tmpLevel, false);
////        fixNodeEntity = new MapNodeEntity(fixNode);
//    }
//
//    @Test
//    public void testingBasicAtrtibutes() {
//
//        tmpNode = new MapNode(0, 9, 0, tmpLevel, false);
//        MapNodeEntity tmpNodeEntity = new MapNodeEntity(tmpNode);
//
//        assertEquals(0, tmpNodeEntity.getNode().getNodeColumn());
//        assertEquals(9, tmpNodeEntity.getNode().getNodeRow());
//        assertEquals(0, tmpNodeEntity.getNode().getPreviousNodes().size());
//        assertEquals(0, tmpNodeEntity.getNode().getProceedingNodes().size());
//        assertEquals(tmpLevel, tmpNodeEntity.getNode().getNodeLinkedLevel());
//        assertEquals(false, tmpNodeEntity.getNode().isSelected());
//
//
//    }
//
//    @Test
//    public void testingAddingPreviousNodes() {
//        tmpNode = new MapNode(0, 9, 0, tmpLevel, false);
//        MapNodeEntity tmpNodeEntity = new MapNodeEntity(tmpNode);
//        for (int i = 0; i < 100; i++){
//            tmpNodeEntity.getNode().addPreviousNode(new MapNode(0, 9, 0, tmpLevel, false));
//        }
//
//        assertEquals(100, tmpNodeEntity.getNode().getPreviousNodes().size());
//        assertEquals(0, tmpNodeEntity.getNode().getProceedingNodes().size());
//
//
//
//        List<MapNode> nodeList = new ArrayList<MapNode>();
//        for (int i = 0; i < 100; i++){
//            nodeList.add(new MapNode(0, 9, 0, tmpLevel, false));
//        }
//        tmpNodeEntity.getNode().addPreviousNodeCollection(nodeList);
//        assertEquals(200, tmpNodeEntity.getNode().getPreviousNodes().size());
//
//
//
//        tmpNodeEntity.getNode().addPreviousNode(fixNode);
//        tmpNodeEntity.getNode().addPreviousNode(fixNode);
////        System.out.println("ahihi " + tmpNode.getPreviousNodes().size());
//        assertEquals(201, tmpNodeEntity.getNode().getPreviousNodes().size());
//
//    }
//
//    @Test
//    public void testingAddingProceedingNodes() {
//        tmpNode = new MapNode(0, 9, 0, tmpLevel, false);
//        MapNodeEntity tmpNodeEntity = new MapNodeEntity(tmpNode);
//
//        for (int i = 0; i < 100; i++){
//            tmpNodeEntity.getNode().addProceedingNode(new MapNode(0, 9, 0, tmpLevel, false));
//        }
//        assertEquals(0, tmpNodeEntity.getNode().getPreviousNodes().size());
//        assertEquals(100, tmpNodeEntity.getNode().getProceedingNodes().size());
//
//        List<MapNode> nodeList = new ArrayList<MapNode>();
//        for (int i = 0; i < 100; i++){
//            nodeList.add(new MapNode(0, 9, 0, tmpLevel, false));
//        }
//        tmpNodeEntity.getNode().addProceedingNodeCollection(nodeList);
//        assertEquals(200, tmpNodeEntity.getNode().getProceedingNodes().size());
//
//        tmpNodeEntity.getNode().addProceedingNode(fixNode);
//        tmpNodeEntity.getNode().addProceedingNode(fixNode);
////        System.out.println("ahihi " + tmpNode.getPreviousNodes().size());
//        assertEquals(201, tmpNodeEntity.getNode().getProceedingNodes().size());
//    }
//
//
//
//    @Test
//    public void testBasicMethods() {
//
//        tmpNode = new MapNode(0, 9, 1, tmpLevel, false);
//        MapNodeEntity tmpNodeEntity = new MapNodeEntity(tmpNode);
//
//        tmpNodeEntity.getNode().selectNode();
//        assertEquals(true, tmpNodeEntity.getNode().isSelected());
//
//        tmpNodeEntity.getNode().unselectNode();
//        assertEquals(false, tmpNodeEntity.getNode().isSelected());
//
//        tmpNodeEntity.getNode().discoverNode();
//        assertEquals(true, tmpNodeEntity.getNode().isDiscovered());
//
//        tmpNodeEntity.getNode().hideNode();
//        assertEquals(false, tmpNodeEntity.getNode().isDiscovered());
//
//        tmpNodeEntity.getNode().setNodeID(123);
//        assertEquals(123, tmpNodeEntity.getNode().getNodeID());
//
//        Level newLevel = new Level(world, 0, 0, 0);
//        tmpNodeEntity.getNode().changeLinkedLevel(newLevel);
//        assertEquals(newLevel, tmpNodeEntity.getNode().getNodeLinkedLevel());
//
//
//    }

//    @Test
//    public void testXandYPos() {
//        tmpNode = new MapNode(0, 9, 1, tmpLevel, false);
//        MapNodeEntity tmpNodeEntity = new MapNodeEntity(tmpNode);
//
//        tmpNodeEntity.getNode().setXPos(100);
//        assertEquals(100, tmpNodeEntity.getNode().getXPos());
//        tmpNodeEntity.getNode().setYPos(-1);
//        assertEquals(-1, tmpNodeEntity.getNode().getYPos());
//    }
}
