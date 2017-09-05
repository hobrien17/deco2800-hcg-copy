package com.deco2800.hcg.worlds;

import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.deco2800.hcg.entities.AbstractEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * AbstractWorld is the Game AbstractWorld
 *
 * It provides storage for the WorldEntities and other universal world level
 * items.
 */
public abstract class AbstractWorld {

    private List<AbstractEntity> entities = new ArrayList<AbstractEntity>();
    private List<ParticleEffect> effects = new ArrayList<ParticleEffect>();
    protected TiledMap map;

    private int width;
    private int length;

    /**
     * Returns a list of entities in this world
     *
     * @return All Entities in the world
     */
    public List<AbstractEntity> getEntities() {
        return new ArrayList<AbstractEntity>(this.entities);
    }
    
    /**
     * Returns all Object Layers present in the map. 
     * Implementation assumes that the only types of layers are Tile Layers
     * and Object Layers(!!!).
     * 
     * @return A list of all Object Layers in the map
     */
    public List<MapLayer> getObjectLayers() {
      
      // create object layers list
      List<MapLayer> objectLayers = new ArrayList<MapLayer>();

      // ensure no errors with empty map for testing later
      if (map != null){
        
        // iterate through all map layers
        Iterator<MapLayer> itr = map.getLayers().iterator();
  
        // Magic, loop through each layer and if the cast to TiledMapTileLayer fails then it
        // is an object layer. So add that to the list of object layers
        while (itr.hasNext()) {
          
          MapLayer layer = itr.next();  
          
          // Attempt to cast to tiled map layer. if it can't, the it's an objectlayer by assumption
          if (!(layer instanceof TiledMapTileLayer)){
            objectLayers.add(layer);
          }
                      
        }
        
      }
      return objectLayers;
      
    }

    /**
     * Returns the TiledMapTileLayer that contains the cell at the given X and Y
     * position. See documentation on TiledMapTileLayer.
     *
     * @param posX X position
     * @param posY Y position
     * @return A TiledMapTileLayer that contains the players current cell. Null
     * if no such TiledMapTileLayer exists.
     */
    public TiledMapTileLayer getTiledMapTileLayerAtPos(int posX, int posY) {
      // check for no map
      if (map != null) {
          // loop through all layers
          Iterator<MapLayer> itr = map.getLayers().iterator(); //GameManager.get().getWorld().getMap()
  
          while (itr.hasNext()) {
  
              TiledMapTileLayer layer = (TiledMapTileLayer) itr.next();
  
              if (layer.getCell(posX, posY) != null) {
                  return (TiledMapTileLayer) layer;
              }
  
          }
      }
      
        return null;

    }

    /**
     * Returns the current map for this world
     *
     * @return Map object for this world
     */
    public TiledMap getMap() {
        return this.map;
    }

    /**
     * Adds entitiy to the world.
     * @param entity Entity to be added
     */
    public void addEntity(AbstractEntity entity) {
        entities.add(entity);
    }

    /**
     * Adds entitiy from the world.
     * @param entity Entity to be removed
     */
    public void removeEntity(AbstractEntity entity) {
        entities.remove(entity);
    }
    
    /**
     * Adds effect to the world.
     * @param entity Entity to be added
     */
    public void addEffect(ParticleEffect effect) {
        effects.add(effect);
    }

    /**
     * Adds effect from the world.
     * @param entity Entity to be removed
     */
    public void removeEffect(ParticleEffect effect) {
        effects.remove(effect);
    }

    /**
     * Changes world width to a specified new width, provided new width is > 0
     * @param width New width
     */
    public void setWidth(int width) {
      if (width > 0) {
        this.width = width;
      }
    }
    
    /**
     * Changes world length to a specified new width, provided new height is > 0
     * @param width New length
     */
    public void setLength(int length) {
      if (length > 0) {
        this.length = length;
      }
    }

    /**
     * Returns world width.
     * @return width
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Returns world length.
     * @return length
     */
    public int getLength() {
        return length;
    }
}
