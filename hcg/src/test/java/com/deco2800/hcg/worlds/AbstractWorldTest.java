package com.deco2800.hcg.worlds;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mockito;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.deco2800.hcg.entities.Tower;
import com.deco2800.hcg.entities.terrain_entities.WallBlock;

class TestWorld extends World{

}

public class AbstractWorldTest extends World{

  @Test
  public void testAddRemoveEntity() {

    TestWorld testWorld = new TestWorld();
    
    Tower tower1 = new Tower(1, 1, 0);
    Tower tower2 = new Tower(1, 2, 0);

    testWorld.addEntity(tower1);
    testWorld.addEntity(tower2);
   
    assertTrue("Entity was not added.", testWorld.getEntities().contains(tower1) == true);

    testWorld.removeEntity(tower1);
    
    assertTrue("Entity was not removed.", testWorld.getEntities().contains(tower1) == false);
    assertTrue("Not deleted entity was removed.", testWorld.getEntities().contains(tower2) == true);

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
  
}

