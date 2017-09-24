package com.deco2800.hcg.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.deco2800.hcg.entities.worldmap.Level;
import com.deco2800.hcg.entities.worldmap.MapNode;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.InputManager;
import com.deco2800.hcg.worlds.World;

import javax.naming.directory.InvalidAttributesException;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

// author - 
public class PlayerTest {

	
	@Test
	public void testInitialiseNewPlayer() {

		Player player = new Player(0, 0, 0);
		player.initialiseNewPlayer(0, 0, 0, 0, 0, 0, 0, 0, "Name");
		
		assertTrue("Player agility isn't set to value 0.", player.attributes.get("agility") == 0);

	}

	@Test
	public void testAttributesMap() {
		Player player = new Player(0, 0, 0);

		player.initialiseNewPlayer(0, 0, 0, 0, 0, 0, 0, 0,"Name");

		player.setAttribute("agility",2);
		assertEquals("player agility should be 2 after setAttribute changed it",2, player.getAttribute("agility"));
		player.setAttribute("DontAddThis",1000);
		assertEquals("attribute should not be added", player.getAttribute("DontAddThis"), -1);
	}

	@Test
	public void testXp() {

		Player player = new Player(0, 0, 0);
		player.setXp(0);

		assertTrue("Player xp isn't set to 0.", player.getXp() == 0);

		player.gainXp(10);

		assertTrue("Player xp wasn't gained.", player.getXp() == 10);
	}

	@Test
	public void testToString() {
		
		assertTrue("Player string isn't 'The player'.", (new Player(0, 0, 0)).toString() == "The player");

	}
	
	@Test
	public void testSetSpeedAndPosition() {
		
		Player player = new Player(0, 0, 0);
			
		assertTrue("Player X not correctly initialised", player.getPosX() == 0);
		assertTrue("Player Y not correctly initialised", player.getPosY() == 0);

		player.setSpeedX(1.0f);
		player.setSpeedY(1.0f);
				
		player.setPosX(1.0f);
		player.setPosY(1.0f);

		assertTrue("Player X didn't change", player.getPosX() != 0);
		assertTrue("Player Y didn't change", player.getPosY() != 0);
		
		assertTrue("Player X speed not set correctly", player.getSpeedX() != 0);
		assertTrue("Player Y speed not set correctly", player.getSpeedY() != 0);

	}
		
	@Test
	public void testPlayerInput() {
		// TODO This must be performed by a PlayerInputManager test as inputs are now queued
	}
	
	@Test
	public void testMultiPlayerInput() {
		// TODO This must be performed by a PlayerInputManager test as inputs are now queued
	}
	
	GameManager gameManager;
	World AbstractWorld;
	TiledMapTileLayer layer;
	TiledMap tiledMap;
	MapProperties mapProperties;
	
	@Before
	public void testSetup(){
		// cannot mock game manager
		gameManager = GameManager.get();
		
		
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
		
		// add node to gamemanager
		Level testLevel = new Level(new World(), 0, 0, 0);
		MapNode testNode0 = new MapNode(0, 9, 1, testLevel, false);
		gameManager.setOccupiedNode(testNode0);

	}
	
	// note - any change to player may break these tests - add more mocking
	@Test
	public void testBasicMovement() {
		
		Player player = new Player(0, 0, 0);
		
		// ensure player can move to the squares we're testing
		when(AbstractWorld.getTiledMapTileLayerAtPos(0, 0)).thenReturn(layer);
		when(AbstractWorld.getTiledMapTileLayerAtPos(1, 1)).thenReturn(layer);
		when(AbstractWorld.getTiledMapTileLayerAtPos(2, 2)).thenReturn(layer);

		// add the custom properties of speed and name
		when(mapProperties.get("speed")).thenReturn("1");
		when(mapProperties.get("name")).thenReturn("Sample Text");

		// add player
		gameManager.getWorld().addEntity(player);
		
		// do one step of movement
		player.onTick(0);
		
		// ensure player did not move when no user input was given
		
		assertTrue("Player moved in X dirn when it shouldn't have", player.getPosX() == 0);
		assertTrue("Player moved in Y dirn when it shouldn't have", player.getPosY() == 0);
		
		player.setSpeedX(1.0f);
		player.setSpeedY(1.0f);

		player.onTick(1);	

		assertTrue("Player didn't move in X dirn when it should have", player.getPosX() == 1);
		assertTrue("Player didn't move in Y dirn when it should have", player.getPosY() == 1);
		
		player.setPosX(0);
		player.setPosY(0);
		
		// double our speed, see if it changes
		when(mapProperties.get("speed")).thenReturn("2");

		player.onTick(2);	

		assertTrue("Player didn't move in X dirn as far as it should have", player.getPosX() == 2);
		assertTrue("Player didn't move in Y dirn as far as it should have", player.getPosY() == 2);

	}
	
	@Test
	public void testSprint() {
	    
	    Player player = new Player(0, 0, 0);

	    player.initialiseNewPlayer(5, 5, 5, 5, 5, 20, 20, 20, "Name");
	    
	    assertTrue("Player's maximum stamina was not initialised correctly",
	            player.getStaminaMax() == 250);
        assertTrue("Player's current stamina was not initialised correctly",
                player.getStaminaCur() == 250);
	}
	
	// note - any change to player may break these tests - add more mocking
	@Test
	public void testSlipperyMovement() {
		
		Player player = new Player(0, 0, 0);
		
		// ensure player can move to the squares we're testing
		when(AbstractWorld.getTiledMapTileLayerAtPos(0, 0)).thenReturn(layer);
		when(AbstractWorld.getTiledMapTileLayerAtPos(1, 1)).thenReturn(layer);
		when(AbstractWorld.getTiledMapTileLayerAtPos(2, 2)).thenReturn(layer);
		
		// add the custom properties of speed and name
		when(mapProperties.get("speed")).thenReturn("1");
		when(mapProperties.get("name")).thenReturn("Sample Text");
		
		// set slippery
		when(mapProperties.get("slippery")).thenReturn("1.0");

		// add player
		gameManager.getWorld().addEntity(player);
						
		player.setSpeedX(1.0f);
		player.setSpeedY(1.0f);

		// do one tick of movement
		player.onTick(1);	

		// should have moved at least a little, but shouldn't have moved as much as if it were not slippery
		assertTrue("Player didn't move in X dirn as much as it should have", player.getPosX() > 0 && player.getPosX() < 1);
		assertTrue("Player didn't move in Y dirn as much as it should have", player.getPosY() > 0 && player.getPosY() < 1); 
		
		float playerX = player.getPosX();
		float playerY = player.getPosY();

		player.setSpeedX(0.0f);
		player.setSpeedY(0.0f);
		
		// do one tick of movement
		player.onTick(1);

		// should have moved at least a little further than it was before
		assertTrue("Player didn't move in X dirn as much as it should have", player.getPosX() > playerX);
		assertTrue("Player didn't move in Y dirn as much as it should have", player.getPosY() > playerY); 
		
	}

	// note - any change to player may break these tests - add more mocking
	@Test
	public void testDamageTiles() {

		Player player = new Player(0, 0, 0);
		
		// ensure player can move to the squares we're testing
		when(AbstractWorld.getTiledMapTileLayerAtPos(0, 0)).thenReturn(layer);
		
		// add the custom properties of speed and name
		when(mapProperties.get("speed")).thenReturn("1");
		when(mapProperties.get("name")).thenReturn("Sample Text");
		
		// set damage
		when(mapProperties.get("damage")).thenReturn("1");
		when(mapProperties.get("damagetype")).thenReturn("1");

		// add player
		gameManager.getWorld().addEntity(player);

		int startHealth = 1000;
		
		player.setHealthMax(startHealth);
		player.setHealthCur(startHealth);
		
		assertTrue("Player max health not set correctly", player.getHealthMax() == startHealth);
		assertTrue("Player current health not set correctly", player.getHealthCur() == startHealth);

		// do one tick of movement
		player.onTick(0);

		//TODO temporary comment out until a proper heal layer exists in layerProperties.
//		assertTrue("Player health wasn't decreased when it should have been", player.getHealthCur() == startHealth - 1);
		
		// reset damage
		when(mapProperties.get("damage")).thenReturn("2");

		// do one tick of movement
		player.onTick(1);

		//TODO temporary comment out until a proper heal layer exists in layerProperties.
//		assertTrue("Player health wasn't decreased as much it should have been", player.getHealthCur() == startHealth - 3);

		when(mapProperties.get("damagetype")).thenReturn("0");

		// do one tick of movement
		player.onTick(1);

		//TODO temporary comment out until a proper heal layer exists in layerProperties.
//		assertTrue("Player health was decreased when the tile was enemy only", player.getHealthCur() == startHealth - 3);

	}
	
	// note - any change to player may break these tests - add more mocking
    @Test
    public void testTowerCollision() {

      Player player = new Player(0, 0, 0);
      
      // ensure player can move to the squares we're testing
      when(AbstractWorld.getTiledMapTileLayerAtPos(0, 0)).thenReturn(layer);
      when(AbstractWorld.getTiledMapTileLayerAtPos(1, 1)).thenReturn(layer);

      // add the custom properties of speed and name
      when(mapProperties.get("speed")).thenReturn("1");
      when(mapProperties.get("name")).thenReturn("Sample Text");
      
      // add player
      gameManager.getWorld().addEntity(player);

      Tower tower = new Tower(1, 1, 0);
      
      List<AbstractEntity> entities = new ArrayList<AbstractEntity>();
      
      entities.add(tower);
      
      when(AbstractWorld.getEntities()).thenReturn(entities);
      
      // set positive speed
      player.setSpeedX(1.0f);
      player.setSpeedY(1.0f);

      player.onTick(0);
      
      assertTrue("Player moved to position of tower when it should have collided with Tower",
          player.getPosX() == 0);
      assertTrue("Player moved to position of tower when it should have collided with Tower",
          player.getPosY() == 0);

    }

	@Test
	public void playerKillLogTests() {
		Player player = new Player(0, 0, 0);

		int exampleID1 = 0;
		int exampleID2 = 1;
		int exampleID3 = 2;

		assertEquals(0,player.killLogGet(exampleID1));
		player.killLogAdd(exampleID1);
		assertEquals(1,player.killLogGet(exampleID1));
		assertEquals(1,player.killLogGetTotal(exampleID1));
		assertEquals(true,player.killLogContains(exampleID1));
		for (int i=0; i<10; i++) {
			player.killLogAdd(exampleID2);
		}
		assertEquals(10,player.killLogGet(exampleID2));
		assertEquals(1,player.killLogGet(exampleID1));
		assertEquals(10,player.killLogGetTotal(exampleID2));
		assertEquals(1,player.killLogGetTotal(exampleID1));
		assertEquals(false,player.killLogContains(exampleID3));
		assertEquals(0,player.killLogGet(exampleID3));
		assertEquals(0,player.killLogGetTotal(exampleID3));

		//The above all assumes only one world, extra world tests below
		int exampleNode1 = 0;
		int exampleNode2 = 1;
		int exampleNode3 = 2;

		assertEquals(0,player.killLogGet(exampleID1,exampleNode1));
		player.killLogAdd(exampleID1,exampleNode1);
		assertEquals(1,player.killLogGet(exampleID1,exampleNode1));
		assertEquals(2,player.killLogGetTotal(exampleID1));
		assertEquals(true,player.killLogContains(exampleID1,exampleNode1));
		for (int i=0; i<10; i++) {
			player.killLogAdd(exampleID2,exampleNode2);
		}
		assertEquals(10,player.killLogGet(exampleID2,exampleNode2));
		assertEquals(1,player.killLogGet(exampleID1,exampleNode1));
		assertEquals(20,player.killLogGetTotal(exampleID2));
		assertEquals(2,player.killLogGetTotal(exampleID1));
		assertEquals(false,player.killLogContains(exampleID3,exampleNode1));
		player.killLogAdd(exampleID3,exampleNode3);
		assertEquals(false,player.killLogContains(exampleID3,exampleNode1));
		assertEquals(true,player.killLogContains(exampleID3,exampleNode3));
		assertEquals(0,player.killLogGet(exampleID3,exampleNode1));
		assertEquals(1,player.killLogGet(exampleID3,exampleNode3));
		assertEquals(1,player.killLogGetTotal(exampleID3));
	}
}
