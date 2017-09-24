package com.deco2800.hcg.worldMap;

import com.deco2800.hcg.entities.worldmap.Level;
import com.deco2800.hcg.entities.worldmap.MapNode;
import com.deco2800.hcg.worlds.World;
import org.junit.Before;
import org.junit.Test;
import com.deco2800.hcg.entities.worldmap.WorldMap;
import com.deco2800.hcg.entities.worldmap.WorldStack;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class WorldStackTest {
    WorldMap worldMap;
    List<MapNode> nodeList;
    @Before
    public void setup() {
        Level newLevel = new Level(new World(), 0, 0, 0);
        MapNode node = new MapNode(0, 0, 0, newLevel, false);
        nodeList = new ArrayList<>();
        nodeList.add(node);
        worldMap = new WorldMap(0, 5, 3, nodeList);
        worldMap.addSeed(92);
        worldMap.setPosition(1);
    }

    @Test
    public void testBasicMethods() {
        WorldStack ws = new WorldStack();
        ws.addWorldToStack(worldMap);
        ws.incrementNumberOfWorlds(); // should put this method inside addWorldToStack
        assertEquals(ws.getWorldStack().size(), ws.getNumberOfWorlds());
        ws.addWorldToStack(worldMap);
        ws.incrementNumberOfWorlds(); // should put this method inside addWorldToStack
        ws.addWorldToStack(worldMap);
        ws.incrementNumberOfWorlds(); // should put this method inside addWorldToStack
        assertEquals(3, ws.getNumberOfWorlds());

    }
}
