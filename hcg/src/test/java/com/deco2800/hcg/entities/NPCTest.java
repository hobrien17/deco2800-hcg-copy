package com.deco2800.hcg.entities;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import com.deco2800.hcg.entities.npc_entities.QuestNPC;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.worlds.World;
import org.junit.Before;
import org.junit.Test;

public class NPCTest {
    private GameManager gameManager;
    private PlayerManager playerManager;
    private World AbstractWorld;
    private TiledMapTileLayer layer;
    private TiledMap tiledMap;
    private MapProperties mapProperties;

    @Before
    public void testSetup(){
        // cannot mock game manager
        gameManager = GameManager.get();
        playerManager = (PlayerManager) GameManager.get().getManager(PlayerManager.class);

        // create all our mock classes
        AbstractWorld = mock(World.class);
        layer = mock(TiledMapTileLayer.class);

        // add to non-mocked gamemanager
        gameManager.setWorld(AbstractWorld);

        // have a map for our properties to go back to the player class
        tiledMap = mock(TiledMap.class);
        when(AbstractWorld.getMap()).thenReturn(tiledMap);

        // set working properties for the Camera in the player to work.
        mapProperties = mock(MapProperties.class);
        when(tiledMap.getProperties()).thenReturn(mapProperties);
        when(mapProperties.get("tilewidth")).thenReturn(32);
        when(mapProperties.get("tileheight")).thenReturn(32);

        when(layer.getProperties()).thenReturn(mapProperties);

    }

    @Test
    public void testNPCShortWander() {
        //Create Player
        Player player = new Player(0, 0, 0);

        //Register Player with game managers, do need player manager for NPC functionality
        gameManager.getWorld().addEntity(player);
        playerManager.setPlayer(player);

        //Current sizes for short Wander grid
        float shortWanderGridX = 15.0f;
        float shortWanderGridY = 15.0f;

        //Create NPC
        //QuestNPC testNPC = new QuestNPC(20,20,"Jane","Jensen","character_1","",  "character_1");
        QuestNPC testNPC = new QuestNPC(20.0f, 20.0f, "Jane", "Jensen","character_1", "", "");
        gameManager.getWorld().addEntity(testNPC);

        //Check for movement outside of 5x5 grid

        assertTrue("NPC moved in X dirn when it shouldn't have", testNPC.getPosX() == 20);
        assertTrue("NPC moved in Y dirn when it shouldn't have", testNPC.getPosY() == 20);

        //Do 10,000 tick steps of movement
        for (int i = 1; i < 1000000; i++) {
            testNPC.onTick(1);
            assertTrue("NPC did not remain within Short Wander X bounds", testNPC.getPosX() > (20 - shortWanderGridX/2) && testNPC.getPosX() < (20 + shortWanderGridX/2));
            assertTrue("NPC did not remain within Short Wander Y bounds", testNPC.getPosY() > (20 - shortWanderGridY/2) && testNPC.getPosY() < (20 + shortWanderGridY/2));
        }
    }

}