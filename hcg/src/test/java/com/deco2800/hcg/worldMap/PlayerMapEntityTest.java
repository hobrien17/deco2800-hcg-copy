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
import com.deco2800.hcg.entities.worldmap.PlayerMapEntity;

import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.List;


public class PlayerMapEntityTest {

    WorldMap worldMap;
    World world;
    Level tmpLevel;
    MapNode tmpNode;
    MapNode fixNode;
    MapNodeEntity fixNodeEntity;
    List<MapNode> nodeList;
    GameManager gameManager = GameManager.get();
    TextureManager textureManager = (TextureManager) gameManager.getManager(TextureManager.class);
    PlayerMapEntity player;

    private int spriteWidth = 30;
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
        player = new PlayerMapEntity();
    }

    @Test
    public void testingBasicAtrtibutes() {
        assertEquals(textureManager.getTexture("player_map"), player.getPlayerTexture());
        assertEquals(0, player.getXPos(), 0.001);
        assertEquals(0, player.getYPos(), 0.001);
        assertEquals(spriteWidth, player.getWidth(), 0.001);
        spriteHeight = player.getPlayerTexture().getHeight() / (player.getPlayerTexture().getWidth() / spriteWidth);
        assertEquals(spriteHeight, player.getHeight(), 0.001);
    }

    @Test
    public void testingUpdatePosition() {
//        player.updatePosByNodeEntity(fixNodeEntity);
//        float renderX = fixNodeEntity.getXPos() + fixNodeEntity.getWidth() / 2 - spriteWidth / 2;
//        System.out.println(Math.round(renderX) +" "+ renderX + " " + fixNodeEntity.getXPos());
//        assertEquals(Math.round(renderX), fixNodeEntity.getXPos(), 0.001);
//        float renderY = fixNodeEntity.getYPos() + fixNodeEntity.getHeight();
    }



}
