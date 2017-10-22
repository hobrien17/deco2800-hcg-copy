package com.deco2800.hcg.worldMap;

import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertFalse;

import com.deco2800.hcg.entities.worldmap.MapNodeEntity;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.TextureManager;
import com.deco2800.hcg.worlds.World;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.deco2800.hcg.entities.worldmap.Level;
import com.deco2800.hcg.entities.worldmap.MapNode;
import com.deco2800.hcg.entities.worldmap.WorldMap;

import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.List;


public class MapNodeEntityTest {

    WorldMap worldMap;
    World world;
    Level tmpLevel;
    MapNode tmpNode;
    MapNode fixNode;
    MapNodeEntity fixNodeEntity;
    List<MapNode> nodeList;
    GameManager gameManager = GameManager.get();
    TextureManager textureManager = (TextureManager) gameManager.getManager(TextureManager.class);

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
        fixNodeEntity = new MapNodeEntity(fixNode, worldMap);
    }

    @Test
    public void testingBasicAtrtibutes() {
        assertEquals(1, fixNodeEntity.getNode().getNodeColumn());
        assertEquals(2, fixNodeEntity.getNode().getNodeRow());
        assertEquals(true, fixNodeEntity.getNode().isSameNode(fixNode));

    }

    @Test
    public void testingTexture() {
        // worldmap type = 1, node type = 0, 1, 2, 3, 99
        worldMap = new WorldMap(1, 5, 3, nodeList);
        fixNodeEntity = new MapNodeEntity(new MapNode(1, 2, 0, tmpLevel, false), worldMap);
        assertEquals(textureManager.getTexture("safe_node"), fixNodeEntity.getNodeTexture());

        fixNodeEntity = new MapNodeEntity(new MapNode(1, 2, 1, tmpLevel, false), worldMap);
        assertEquals(textureManager.getTexture("discovered_node"), fixNodeEntity.getNodeTexture());

        fixNodeEntity = new MapNodeEntity(new MapNode(1, 2, 2, tmpLevel, false), worldMap);
        assertEquals(textureManager.getTexture("completed_node"), fixNodeEntity.getNodeTexture());

        fixNodeEntity = new MapNodeEntity(new MapNode(1, 2, 3, tmpLevel, false), worldMap);
        assertEquals(textureManager.getTexture("fungi_node"), fixNodeEntity.getNodeTexture());

        fixNodeEntity = new MapNodeEntity(new MapNode(1, 2, 99, tmpLevel, false), worldMap);
        assertEquals(textureManager.getTexture("discovered_node"), fixNodeEntity.getNodeTexture());

        // worldmap type = 2, node type = 0, 1, 2, 3, 99
        worldMap = new WorldMap(2, 5, 3, nodeList);
        fixNodeEntity = new MapNodeEntity(new MapNode(1, 2, 0, tmpLevel, false), worldMap);
        assertEquals(textureManager.getTexture("forest_safe_node"), fixNodeEntity.getNodeTexture());

        fixNodeEntity = new MapNodeEntity(new MapNode(1, 2, 1, tmpLevel, false), worldMap);
        assertEquals(textureManager.getTexture("forest_discovered_node"), fixNodeEntity.getNodeTexture());

        fixNodeEntity = new MapNodeEntity(new MapNode(1, 2, 2, tmpLevel, false), worldMap);
        assertEquals(textureManager.getTexture("forest_completed_node"), fixNodeEntity.getNodeTexture());

        fixNodeEntity = new MapNodeEntity(new MapNode(1, 2, 3, tmpLevel, false), worldMap);
        assertEquals(textureManager.getTexture("forest_boss_node"), fixNodeEntity.getNodeTexture());

        fixNodeEntity = new MapNodeEntity(new MapNode(1, 2, 99, tmpLevel, false), worldMap);
        assertEquals(textureManager.getTexture("discovered_node"), fixNodeEntity.getNodeTexture());

        // worldmap type = 99, node type = 0, 1, 2, 3, 99
        worldMap = new WorldMap(99, 5, 3, nodeList);
        fixNodeEntity = new MapNodeEntity(new MapNode(1, 2, 0, tmpLevel, false), worldMap);
        assertEquals(textureManager.getTexture("waste_safe_node"), fixNodeEntity.getNodeTexture());

        fixNodeEntity = new MapNodeEntity(new MapNode(1, 2, 1, tmpLevel, false), worldMap);
        assertEquals(textureManager.getTexture("waste_discovered_node"), fixNodeEntity.getNodeTexture());

        fixNodeEntity = new MapNodeEntity(new MapNode(1, 2, 2, tmpLevel, false), worldMap);
        assertEquals(textureManager.getTexture("waste_completed_node"), fixNodeEntity.getNodeTexture());

        fixNodeEntity = new MapNodeEntity(new MapNode(1, 2, 3, tmpLevel, false), worldMap);
        assertEquals(textureManager.getTexture("waste_boss_node"), fixNodeEntity.getNodeTexture());

        fixNodeEntity = new MapNodeEntity(new MapNode(1, 2, 99, tmpLevel, false), worldMap);
        assertEquals(textureManager.getTexture("discovered_node"), fixNodeEntity.getNodeTexture());

    }
    @Test
    public void testingRender() {
        assertEquals(spriteWidth, fixNodeEntity.getWidth(), 0.001);
        spriteHeight = fixNodeEntity.getNodeTexture().getHeight() / (fixNodeEntity.getNodeTexture().getWidth() / spriteWidth);
        assertEquals(spriteHeight, fixNodeEntity.getHeight(), 0.001);

        assertEquals(fixNodeEntity.getNode().getXPos(), fixNodeEntity.getXPos() + fixNodeEntity.getWidth() / 2, 0.001);
        assertEquals(fixNodeEntity.getNode().getYPos(), fixNodeEntity.getYPos() + fixNodeEntity.getHeight() / 2, 0.001);


    }


}
