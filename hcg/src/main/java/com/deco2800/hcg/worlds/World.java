package com.deco2800.hcg.worlds;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.Selectable;
import com.deco2800.hcg.entities.npc_entities.QuestNPC;
import com.deco2800.hcg.entities.npc_entities.ShopNPC;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AbstractWorld is the Game AbstractWorld
 *
 * It provides storage for the WorldEntities and other universal world level
 * items.
 */
public class World {
  
    static final Logger LOGGER = LoggerFactory.getLogger(World.class);
    
    private String loadedFile;

    private List<AbstractEntity> entities = new ArrayList<AbstractEntity>();
    protected TiledMap map;

    private int width;
    private int length;

    /**
     * Empty abstract world, for testing
     */
    public World() {
      // Purposefully empty for test code
    }
    
    /**
     * Creates a world from a given file. 
     * 
     */
    public World(String file) {
      
      // attempt to load the given file
      try{
          this.map = new TmxMapLoader()
              .load(file);
              loadedFile = file;
      } catch (Exception e) {
    	  System.out.println(e);
    	  LOGGER.error(e.toString());
    	  return;
      }
      
      /*
       * Grab the width and length values from the map file to use as the world size
       */
      this.setWidth(this.getMap().getProperties().get("width", Integer.class));
      this.setLength(this.getMap().getProperties().get("height", Integer.class));

      // get player
      Player player = ((PlayerManager) GameManager.get().getManager(PlayerManager.class)).getPlayer();
      
      // change player position based on the properties below
      player.setPosX(Float.parseFloat((String) this.getMap().getProperties().get("PlayerX")));
      player.setPosY(Float.parseFloat((String) this.getMap().getProperties().get("PlayerY")));
                      
      // loop over all object layers
      for (MapLayer layer : getObjectLayers()){
                
          Iterator<MapObject> objects = layer.getObjects().iterator();

          int i = 0; // for enemy's because they need unique id's i guess
          
          // store layer name
          String layerName = ((String) layer.getProperties().get("name")).toUpperCase();
          
          // make sure the layer has an associating entity type, otherwise we don't want to loop over the objects
          Boolean found = false;
          
          // loop over all entities, make sure the entity exists
          for(WorldEntities type : WorldEntities.values()){
            if (type.toString().equals(layerName)){
              found = true;
            }
          }
          
          while (objects.hasNext() && (found || "NPC".equals(layerName))) {
                        
            MapObject obj = objects.next();
                    
            // get x and y
            float x = (float) obj.getProperties().get("y"); // no clue why these are switched, help
            float y = (float) obj.getProperties().get("x");
            
            x/=32; // divide by the width / height, I guess this might screw up bigger tiles
            y/=32;
            
            y--; // this fixes it for some reason
            
            // spawn in the NPC's from the NPC layer
            if ("NPC".equals(layerName)) {
                            
              // create NPC
              try {
                
                this.addEntity(NPCs.valueOf(((String) obj.getProperties().get("Type")).toUpperCase()).spawn(x, y,
                    (String) obj.getProperties().get("fName"),
                    (String) obj.getProperties().get("sName"),
                    (String) obj.getProperties().get("texture")));
                
              }
              finally {/* it didn't work */}
              
            }
            else {
              
              // otherwise, our entity is definately in our enum! so call in the spawn method
              this.addEntity(WorldEntities.valueOf(layerName).spawn(x, y, i+1));

            }
                                    
            i++; // add to ensure uniqueness of the id, may be bad if there's multiple enemy types
            
          }
          
          // Remove this layer! After this method we will only have tile layers, which is good
          map.getLayers().remove(layer);

      }

    }
    
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
     * Adds entity to the world.
     * @param entity Entity to be added
     */
    public void addEntity(AbstractEntity entity) {
        entities.add(entity);
    }
    
    /**
     * Checks if world contains entity.
     * @param entity Entity to be checked
     */
    public boolean containsEntity(AbstractEntity entity) {
        return entities.contains(entity);
    }

    /**
     * Removes entity from the world.
     * @param entity Entity to be removed
     */
    public void removeEntity(AbstractEntity entity) {
        entities.remove(entity);
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
    
    /**
     * Returns the string of the loaded file used to create the world.
     * @return loadedFile
     */
    public String getLoadedFile() {
    	return loadedFile;
    }
    
    /**
     * Deselects all entities.
     */
    public void deSelectAll() {
        for (AbstractEntity r : this.getEntities()) {
            if (r instanceof Selectable) {
                ((Selectable) r).deselect();
            }
        }
}
}
