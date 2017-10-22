package com.deco2800.hcg.worldMap;

import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertFalse;

import com.badlogic.gdx.Gdx;
import com.deco2800.hcg.entities.worldmap.*;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.TextureManager;
import com.deco2800.hcg.worlds.World;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.List;


public class WorldStackMapEntityTest {

    WorldMap worldMap;
    World world;
    Level tmpLevel;
    MapNode fixNode;
    List<MapNode> nodeList;
    GameManager gameManager = GameManager.get();
    TextureManager textureManager = (TextureManager) gameManager.getManager(TextureManager.class);
    WorldStack ws;
    WorldStackMapEntity wsMapEntity;
    private int spriteWidth = 50;
    private int spriteHeight;
    @Before
    public void setup() {
        world = new World();
        tmpLevel = new Level(world, 0, 0, 0);
        fixNode = new MapNode(1, 2, 0, tmpLevel, false);
        nodeList = new ArrayList<>();
        nodeList.add(fixNode);
        worldMap = new WorldMap(1, 5, 3, nodeList);
        gameManager.setWorldMap(worldMap);

        ws = new WorldStack();
        ws.addWorldToStack(worldMap);
        ws.incrementNumberOfWorlds(); // should put this method inside addWorldToStack
        assertEquals(ws.getWorldStack().size(), ws.getNumberOfWorlds());
        ws.addWorldToStack(worldMap);
        ws.incrementNumberOfWorlds(); // should put this method inside addWorldToStack
        ws.addWorldToStack(worldMap);
        ws.incrementNumberOfWorlds(); // should put this method inside addWorldToStack
        gameManager.setWorldStack(ws);
        wsMapEntity = new WorldStackMapEntity(worldMap);
    }

    @Test
    public void testingBasicAtrtibutes() {
        assertEquals(worldMap, wsMapEntity.getWorldMap());
    }

    @Test
    public void testingRenderingTexture() {
        worldMap = new WorldMap(0, 5, 3, nodeList);
        wsMapEntity = new WorldStackMapEntity(worldMap);
        assertEquals(textureManager.getTexture("safe_node"), wsMapEntity.getWorldTexture());

        worldMap = new WorldMap(1, 5, 3, nodeList);
        wsMapEntity = new WorldStackMapEntity(worldMap);
        assertEquals(textureManager.getTexture("ws_urban"), wsMapEntity.getWorldTexture());

        worldMap = new WorldMap(2, 5, 3, nodeList);
        wsMapEntity = new WorldStackMapEntity(worldMap);
        assertEquals(textureManager.getTexture("ws_forest"), wsMapEntity.getWorldTexture());

        worldMap = new WorldMap(3, 5, 3, nodeList);
        wsMapEntity = new WorldStackMapEntity(worldMap);
        assertEquals(textureManager.getTexture("ws_fungi"), wsMapEntity.getWorldTexture());

        worldMap = new WorldMap(99, 5, 3, nodeList);
        wsMapEntity = new WorldStackMapEntity(worldMap);
        assertEquals(textureManager.getTexture("safe_node"), wsMapEntity.getWorldTexture());
    }


}
