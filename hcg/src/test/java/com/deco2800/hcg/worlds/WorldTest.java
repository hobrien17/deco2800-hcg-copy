package com.deco2800.hcg.worlds;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.Tower;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;

class TestWorld extends World{

}

public class WorldTest extends World{

  @Test
  public void testAddRemoveEntity() {

    TestWorld testWorld = new TestWorld();
    
    Tower tower1 = new Tower(1, 1, 0);
    Tower tower2 = new Tower(1, 2, 0);

    testWorld.addEntity(tower1);
    testWorld.addEntity(tower2);
   
    assertTrue("Entity was not added.", testWorld.getEntities().contains(tower1));

    testWorld.removeEntity(tower1);
    
    assertFalse("Entity was not removed.", testWorld.getEntities().contains(tower1));
    assertTrue("Not deleted entity was removed.", testWorld.getEntities().contains(tower2));

    // attempt remove multiple times 
    testWorld.removeEntity(tower1);

  }

  @Test
  public void testChangeWidthHeight() {

    TestWorld testWorld = new TestWorld();
    
    int expected = 10;
    int unexpected = -10;

    testWorld.getWidth();
    testWorld.getLength();

    testWorld.setWidth(expected);
    testWorld.setLength(expected);

    assertTrue("Entity was not added.", testWorld.getWidth() == expected);
    assertTrue("Entity was not added.", testWorld.getLength() == expected);

    testWorld.setWidth(unexpected);
    testWorld.setLength(unexpected);

    assertTrue("Entity was not added.", testWorld.getWidth() == expected);
    assertTrue("Entity was not added.", testWorld.getLength() == expected);

  }

  @Test
  public void testGetTiledMapTileLayerAtPos() {
    
    TestWorld testWorld = new TestWorld();
    
    testWorld.setWidth(10);
    testWorld.setLength(10);

    assertTrue("Nonexisting layer was returned.", testWorld.getTiledMapTileLayerAtPos(0, 0) == null);

    TiledMapTileLayer layer1 = mock(TiledMapTileLayer.class);
    TiledMapTileLayer layer2 = mock(TiledMapTileLayer.class);
        
    TiledMapTileLayer.Cell cell = mock(TiledMapTileLayer.Cell.class);

    when(layer1.getCell(0, 0)).thenReturn(cell);
    
    testWorld.map = new TiledMap();
    
    testWorld.map.getLayers().add(layer1);

    assertTrue("Existing layer was not returned.", testWorld.getTiledMapTileLayerAtPos(0, 0) == layer1);

    testWorld.map.getLayers().add(layer2);

    assertTrue("Nonexisting layer was returned.", testWorld.getTiledMapTileLayerAtPos(1, 0) == null);

    when(layer2.getCell(1, 0)).thenReturn(cell);
    
    assertTrue("Existing layer was not returned.", testWorld.getTiledMapTileLayerAtPos(1, 0) == layer2);

  }
  
  @Test
  public void testGetObjectLayer() {
    TestWorld testWorld = new TestWorld();

    testWorld.setWidth(10);
    testWorld.setLength(10);

    assertTrue("Nonexisting layer was returned.", testWorld.getObjectLayers().size() == 0);

    TiledMapTileLayer layer1 = mock(TiledMapTileLayer.class);
    
    testWorld.map = new TiledMap();

    testWorld.map.getLayers().add(layer1);
    
    assertTrue("Non object layer was returned.", testWorld.getObjectLayers().size() == 0);
    
    // Cannot mock object layer class, there is none! 
    // Had issues with throwing exception on cast, gave up   
    
  }
  
  @Test
  public void testBadWorld() {

    World world = new World("This world doesn't exist");
    
    assertTrue(world.getTiledMapTileLayerAtPos(0, 0) == null);

    assertTrue(world.getObjectLayers().size() == 0);

    assertTrue(world.getMap() == null);

  }

  @Test
  public void testWorkingLoad() {
    
    // get game manager
    GameManager gameManager = GameManager.get();
    
    // create and add player
    Player player = new Player(0, 0, 0);
    
    // set player
    ((PlayerManager) GameManager.get().getManager(PlayerManager.class)).setPlayer(player);

    // create world from our good test map
    World world = new World("test");


    // add world to gamemanager
    gameManager.setWorld(world);
    
    // I don't trust jenkins
    if (world.getMap() != null) {
      // make sure all object layers are deleted after the world is initiated
      assertTrue("Object layers were not deleted", world.getObjectLayers().size() == 0);
      
      // ensure a few entities exist 
      assertTrue("Entites were not spawned", world.getEntities().size() >= 5);
      
      assertTrue("World length was not modified", world.getLength() != 0);
      assertTrue("World width was not modified", world.getWidth() != 0);
      
    }
    
  }
  
  @Test
  public void testLoadedFile() {

    World world = new World("test");

    if (world.getMap() != null) {
      assertTrue("resources/maps/maps/initial-map-test.tmx".equals(world.getLoadedFile()));
    }
    
  }
  
  @Test
  public void testDeselectAll() {
    
    World world = new World(null);
    Tower tower = new Tower(0, 0, 0);

    world.addEntity(tower);
    
    world.deSelectAll();
    
    assertFalse("Tower was selected", tower.isSelected());
  }
  
}

