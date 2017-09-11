package com.deco2800.hcg.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
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
        float shortWanderGridX = 5.0f;
        float shortWanderGridY = 5.0f;

        //Create NPC
        NPC testNPC = new NPC(10,10,0,0.5f,0.5f,1.0f, false,"Jane","Jensen", NPC.Type.Quest, "character_1") {};
        gameManager.getWorld().addEntity(testNPC);

        //Check for movement outside of 5x5 grid

        assertTrue("NPC moved in X dirn when it shouldn't have", testNPC.getPosX() == 10);
        assertTrue("NPC moved in Y dirn when it shouldn't have", testNPC.getPosY() == 10);

        //Do 10,000 tick steps of movement
        for (int i = 1; i < 10000; i++) {
            testNPC.onTick(1);
            assertTrue("NPC did not remain within Short Wander X bounds", testNPC.getPosX() > (10 - shortWanderGridX/2) && testNPC.getPosX() < (10 + shortWanderGridX/2));
            assertTrue("NPC did not remain within Short Wander Y bounds", testNPC.getPosY() > (10 - shortWanderGridY/2) && testNPC.getPosY() < (10 + shortWanderGridY/2));
        }
    }
}
