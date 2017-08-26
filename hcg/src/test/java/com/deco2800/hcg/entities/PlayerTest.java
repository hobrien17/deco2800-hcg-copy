package com.deco2800.hcg.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.InputManager;
import com.deco2800.hcg.worlds.DemoWorld;

import javax.naming.directory.InvalidAttributesException;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

// author - 
public class PlayerTest {

	
	@Test
	public void testInitialiseNewPlayer() {

		Player player = new Player(0, 0, 0);
		player.initialiseNewPlayer(0, 0, 0, 0, 0, 0);
		
		assertTrue("Player agility isn't set to value 0.", player.attributes.get("agility") == 0);

	}

	@Test(expected = InvalidAttributesException.class)
	public void testAttributesMap() throws InvalidAttributesException{
		Player player = new Player(0, 0, 0);
		player.initialiseNewPlayer(0, 0, 0, 0, 0, 0);
		player.setAttribute("agility",2);
		assertEquals("player agility should be 2 after setAttribute changed it",2, player.getAttribute("agility"));
		player.setAttribute("Throw an exception",1000);
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
	  
	  Player player = new Player(0, 0, 0);
	  
      InputManager input = (InputManager) GameManager.get()
          .getManager(InputManager.class);

	  input.keyDown(Input.Keys.S);
	  
	  assertTrue("Player X speed didn't change.", player.getSpeedX() != 0);
      assertTrue("Player Y speed didn't change.", player.getSpeedY() != 0);

      input.keyUp(Input.Keys.S);
      
      assertTrue("Player X speed wasn't reset.", player.getSpeedX() == 0);
      assertTrue("Player Y speed wasn't reset.", player.getSpeedY() == 0);

      input.keyDown(Input.Keys.A);
      
      assertTrue("Player X speed didn't change.", player.getSpeedX() != 0);
      assertTrue("Player Y speed didn't change.", player.getSpeedY() != 0);

      input.keyUp(Input.Keys.A);
      
      assertTrue("Player X speed wasn't reset.", player.getSpeedX() == 0);
      assertTrue("Player Y speed wasn't reset.", player.getSpeedY() == 0);
      
      input.keyDown(Input.Keys.D);
      
      assertTrue("Player X speed didn't change.", player.getSpeedX() != 0);
      assertTrue("Player Y speed didn't change.", player.getSpeedY() != 0);

      input.keyUp(Input.Keys.D);
      
      assertTrue("Player X speed wasn't reset.", player.getSpeedX() == 0);
      assertTrue("Player Y speed wasn't reset.", player.getSpeedY() == 0);

	}
	
	GameManager gameManager;
	DemoWorld demoWorld;
	TiledMapTileLayer layer;
	TiledMap tiledMap;
	MapProperties mapProperties;
	
	@Before
	public void testSetup(){
		// cannot mock game manager
		gameManager = GameManager.get();
		
		// create all our mock classes
		demoWorld = mock(DemoWorld.class);
		layer = mock(TiledMapTileLayer.class);

		// add to non-mocked gamemanager
		gameManager.setWorld(demoWorld);

		// have a map for our properties to go back to the player class
		tiledMap = mock(TiledMap.class);
		when(demoWorld.getMap()).thenReturn(tiledMap);

		// set working properties for the Camera in the player to work.
		mapProperties = mock(MapProperties.class);
		when(tiledMap.getProperties()).thenReturn(mapProperties);
		when(mapProperties.get("tilewidth")).thenReturn(32);
		when(mapProperties.get("tileheight")).thenReturn(32);

		when(layer.getProperties()).thenReturn(mapProperties);

	}
	
	// note - any change to player may break these tests - add more mocking
	@Test
	public void testBasicMovement() {
		
		Player player = new Player(0, 0, 0);
		
		// ensure player can move to the squares we're testing
		when(demoWorld.getTiledMapTileLayerAtPos(0, 0)).thenReturn(layer);
		when(demoWorld.getTiledMapTileLayerAtPos(1, 1)).thenReturn(layer);
		when(demoWorld.getTiledMapTileLayerAtPos(2, 2)).thenReturn(layer);

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
	
	// note - any change to player may break these tests - add more mocking
	@Test
	public void testSlipperyMovement() {
		
		Player player = new Player(0, 0, 0);
		
		// ensure player can move to the squares we're testing
		when(demoWorld.getTiledMapTileLayerAtPos(0, 0)).thenReturn(layer);
		when(demoWorld.getTiledMapTileLayerAtPos(1, 1)).thenReturn(layer);
		when(demoWorld.getTiledMapTileLayerAtPos(2, 2)).thenReturn(layer);
		
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
		when(demoWorld.getTiledMapTileLayerAtPos(0, 0)).thenReturn(layer);
		
		// add the custom properties of speed and name
		when(mapProperties.get("speed")).thenReturn("1");
		when(mapProperties.get("name")).thenReturn("Sample Text");
		
		// set damage
		when(mapProperties.get("damage")).thenReturn("1");
		when(mapProperties.get("damagetype")).thenReturn("1");

		// add player
		gameManager.getWorld().addEntity(player);

		int startHealth = 1000;
		
		player.setHealth(startHealth);
		
		assertTrue("Player health not set correctly", player.getHealth() == startHealth);

		// do one tick of movement
		player.onTick(0);	

		assertTrue("Player health wasn't decreased when it should have been", player.getHealth() == startHealth - 1);
		
		// reset damage
		when(mapProperties.get("damage")).thenReturn("2");

		// do one tick of movement
		player.onTick(1);	

		assertTrue("Player health wasn't decreased as much it should have been", player.getHealth() == startHealth - 3);

		when(mapProperties.get("damagetype")).thenReturn("0");

		// do one tick of movement
		player.onTick(1);	
		
		assertTrue("Player health was decreased when the tile was enemy only", player.getHealth() == startHealth - 3);

	}
	
	// note - any change to player may break these tests - add more mocking
    @Test
    public void testTowerCollision() {

      Player player = new Player(0, 0, 0);
      
      // ensure player can move to the squares we're testing
      when(demoWorld.getTiledMapTileLayerAtPos(0, 0)).thenReturn(layer);
      when(demoWorld.getTiledMapTileLayerAtPos(1, 1)).thenReturn(layer);

      // add the custom properties of speed and name
      when(mapProperties.get("speed")).thenReturn("1");
      when(mapProperties.get("name")).thenReturn("Sample Text");
      
      // add player
      gameManager.getWorld().addEntity(player);

      Tower tower = new Tower(1, 1, 0);
      
      List<AbstractEntity> entities = new ArrayList<AbstractEntity>();
      
      entities.add(tower);
      
      when(demoWorld.getEntities()).thenReturn(entities);
      
      // set positive speed
      player.setSpeedX(1.0f);
      player.setSpeedY(1.0f);

      player.onTick(0);
      
      assertTrue("Player moved to position of tower when it should have collided with Tower",
          player.getPosX() == 0);
      assertTrue("Player moved to position of tower when it should have collided with Tower",
          player.getPosY() == 0);

    }
	
}
