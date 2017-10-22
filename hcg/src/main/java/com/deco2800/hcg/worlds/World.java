package com.deco2800.hcg.worlds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Selectable;
import com.deco2800.hcg.entities.garden_entities.plants.Pot;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.TextureManager;
import com.deco2800.hcg.renderers.Renderable;
import com.deco2800.hcg.types.Weathers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.deco2800.hcg.util.Array2D;
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
	private Weathers weather;

	private List<AbstractEntity> entities = new ArrayList<AbstractEntity>();
	public List<Renderable> renderables = new ArrayList<Renderable>();
	protected TiledMap map;

	private int width;
	private int length;

	private float startingPlayerX;
	private float startingPlayerY;

	//Store Collisions
	protected Array2D<List<AbstractEntity>> collisionMap;
	
	public static final World SAFEZONE = new World("resources/maps/maps/grass_safeZone_02.tmx");
	
	/**
	 * Empty abstract world, for testing
	 */
	public World() {
		// Purposefully empty for test code

		//Create Test Collision Map - with test dimensions

		this.setWidth(100);
		this.setLength(100);

		this.collisionMap = new Array2D<> (this.getWidth() + 1, this.getLength() + 1);
		for (int x = 0; x < this.getWidth() + 1; x++) {
			for (int y = 0; y < this.getLength() + 1; y++) {
				this.collisionMap.set(x, y, new ArrayList<>());
			}
		}
	}

	/**
	 * Creates a world from a given file.
	 *
	 */
	public World(String file) {
		if (file == null) {
			LOGGER.error("Empty filename, not test conditions");
			return;
		}

		// attempt to load the given file
		try {
			if ("test".equals(file)){ // for WorldTest
				file = "resources/maps/maps/test.tmx";
			}
			this.map = new TmxMapLoader().load(file);
			loadedFile = file;

		} catch (Exception e) {
			LOGGER.error(String.valueOf(e));
			return;
		}

		/*
		 * Grab the width and length values from the map file to use as the
		 * world size
		 */

		this.setWidth(
				this.getMap().getProperties().get("width", Integer.class));
		this.setLength(
				this.getMap().getProperties().get("height", Integer.class));


		//Create Collision Map
		//Added Extra Y-length to allow for inconsistencies between the CollisionMap and Tiled.
		this.collisionMap = new Array2D<> (this.getWidth(), this.getLength()*2);
		for (int x = 0; x < this.getWidth(); x++) {
			for (int y = 0; y < this.getLength()*2; y++) {
				this.collisionMap.set(x, y, new ArrayList<>());
			}
		}

		// check for weather
		if (this.map.getProperties().get("weather") != null) {
			// make string of weather for enum
			this.weather = Weathers.valueOf(((String) this.map.getProperties().get("weather")).toUpperCase());

		} else {
			this.weather = Weathers.NONE;
		}

		// load starting player X and Y
		startingPlayerX = Float.parseFloat((String) this.getMap().getProperties().get("PlayerX"));
		startingPlayerY = Float.parseFloat((String) this.getMap().getProperties().get("PlayerY"));

		// loop over all object layers
		for (MapLayer layer : getObjectLayers()) {

			Iterator<MapObject> objects = layer.getObjects().iterator();

			int i = 0; // for enemy's because they need unique id's i guess

			// store layer name
			String layerName = ((String) layer.getProperties().get("name"))
					.toUpperCase();

			// make sure the layer has an associating entity type, otherwise we
			// don't want to loop over the objects
			Boolean found = false;

			// loop over all entities, make sure the entity exists
			for (WorldEntities type : WorldEntities.values()) {
				if (type.toString().equals(layerName)) {
					found = true;
				}
			}

			while (objects.hasNext() && (found || "NPC".equals(layerName))) {

				MapObject obj = objects.next();

				// get x and y
				float x = (float) obj.getProperties().get("y"); // no clue why
				// these are
				// switched,
				// help
				float y = (float) obj.getProperties().get("x");

				x /= 32; // divide by the width / height, I guess this might
				// screw up bigger tiles
				y /= 32;

				y--; // this fixes it for some reason

				// spawn in the NPC's from the NPC layer
				if ("NPC".equals(layerName)) {

					// create NPC
					try {

						this.addEntity(NPCs.valueOf(
								((String) obj.getProperties().get("Type")).toUpperCase()).spawn(x, y,(String) obj.getProperties()
										.get("fName"),
								(String) obj.getProperties()
										.get("sName"),
								(String) obj.getProperties()
										.get("texture"), (String) obj.getProperties()
										.get("conversation"), (String) obj.getProperties()
										.get("faceImage")));

					} finally {
						/* it didn't work */}

				} else {

					// otherwise, our entity is definately in our enum! so call
					// in the spawn method
					this.addEntity(WorldEntities.valueOf(layerName).spawn(x, y, i + 1));

				}

				i++; // add to ensure uniqueness of the id, may be bad if
				// there's multiple enemy types

			}

			// Remove this layer! After this method we will only have tile
			// layers, which is good
			map.getLayers().remove(layer);

		}

        // add new tile for poison trail
        MapProperties mapProperties = new MapProperties();
        mapProperties.put("name", "newSludge");
        mapProperties.put("damagetype", "1");
        mapProperties.put("damage", "1");
        mapProperties.put("speed", "1.0");

        this.addTiledMapTileLayer("newSludge", mapProperties);

        this.generatePuddles();

	}


	/**
	 * Makes an int array of coordinates (left, right, bottom top) which would be used for updating the collision map
	 * from a provided entity.
	 *
	 * @param entity  the entity to get the collision coordinates for
	 * @return int array of the coordinates. would be in order of left, right, bottom top.
	 */
	public int[] makeCollisionCoords(AbstractEntity entity) {
		int[] result = new int[4];
		result[0] = (int)entity.getPosX();
		result[1] = (int)Math.ceil(entity.getPosX() + entity.getXLength());
		result[2] = (int)entity.getPosY();
		result[3] = (int)Math.ceil(entity.getPosY() + entity.getYLength());
		return result;
	}

	/**
	 * Gets the entity at an x y position.
	 *
	 * @param x a tile x coordinate
	 * @param y a tile y coordinate
	 * @return a list of entities found at the given tile.
	 */
	public List<AbstractEntity> getEntities(int x, int y) {
		try {
			return this.collisionMap.get(x, y);
		} catch (IndexOutOfBoundsException e) {
			LOGGER.error("Invalid Tile Coordinate", e);
			throw new IndexOutOfBoundsException("Invalid tile coordinate.");
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
	 * Returns all Object Layers present in the map. Implementation assumes that
	 * the only types of layers are Tile Layers and Object Layers(!!!).
	 *
	 * @return A list of all Object Layers in the map
	 */
	public List<MapLayer> getObjectLayers() {

		// create object layers list
		List<MapLayer> objectLayers = new ArrayList<MapLayer>();

		// ensure no errors with empty map for testing later
		if (map != null) {

			// iterate through all map layers
			Iterator<MapLayer> itr = map.getLayers().iterator();

			// Magic, loop through each layer and if the cast to
			// TiledMapTileLayer fails then it
			// is an object layer. So add that to the list of object layers
			while (itr.hasNext()) {

				MapLayer layer = itr.next();

				// Attempt to cast to tiled map layer. if it can't, the it's an
				// objectlayer by assumption
				if (!(layer instanceof TiledMapTileLayer)) {
					objectLayers.add(layer);
				}
			}
		}
		return objectLayers;
	}

	/**
	 * Returns the highest TiledMapTileLayer that contains the cell at the given X and Y
	 * position. See documentation on TiledMapTileLayer.
	 *
	 * @param posX
	 *            X position
	 * @param posY
	 *            Y position
	 * @return The highest TiledMapTileLayer that contains a cell at the given position. Null
	 *         if no such TiledMapTileLayer exists.
	 */
	public TiledMapTileLayer getTiledMapTileLayerAtPos(int posX, int posY) {
		// check for no map

		TiledMapTileLayer highestLayer = null;

		if (map != null) {
			// loop through all layers
			// we want the highest up layer that satisfies it

			Iterator<MapLayer> itr = map.getLayers().iterator();

			while (itr.hasNext()) {

				TiledMapTileLayer layer = (TiledMapTileLayer) itr.next();

				if (layer.getCell(posX, posY) != null) {
					highestLayer = (TiledMapTileLayer) layer;

				}

			}
		}

		if (highestLayer != null) {
			return highestLayer;
		}

		return null;

	}

	/**
	 * Returns a layer with the given property name and property
	 * @param propertyName name of the property
	 * @param property property
	 * @return MapLayer that has the given property
	 */
	public MapLayer getMapLayerWithProperty(String propertyName, String property) {

		for (MapLayer m : map.getLayers()) {
			if (property.equals(m.getProperties().get(propertyName))) {
				return m;
			}
		}
		return null;

	}

	/**
	 * Adds a TiledMapTileLayer with a given name and the given properties.
	 * Note .getName() will not work after this method, you must go through
	 * getProperties().get("name")
	 * @param name name of the layer
	 * @param properties properties of the layer
	 */
	public void addTiledMapTileLayer(String name, MapProperties properties) {

		TiledMapTileLayer layer = new TiledMapTileLayer(this.getWidth(), this.getLength(), 55, 32);
		layer.getProperties().putAll(properties);
		map.getLayers().add(layer);

	}

	/**
	 *  Adds / changes a tile with a given texture at a given position in a given layer.
	 *
	 * @param posX X position of tile to change
	 * @param posY Y position of tile to change
	 * @param texture texture to change tile to
	 * @param newLayer the destination layer of the tile
	 * @return true if success, false if failure
	 */
	public boolean newTileAtPos(int posX, int posY, Texture texture, TiledMapTileLayer newLayer) {
		if (newLayer != null) {

			// make new texture region
			TextureRegion textureRegion = new TextureRegion(texture);
			StaticTiledMapTile tile = new StaticTiledMapTile(textureRegion);

			Cell cell = new Cell();
			cell.setTile(tile);

			newLayer.setCell(posY, posX, cell);

		}
		return false;
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
	 *
	 * @param entity
	 *            Entity to be added
	 */
	public void addEntity(AbstractEntity entity) {
		entities.add(entity);

		//Add to the collision map
		int[] collisionCoords = makeCollisionCoords(entity);

		for (int x = collisionCoords[0]; x < collisionCoords[1]; x++) {
			for (int y = collisionCoords[2]; y < collisionCoords[3]; y++) {
				if (x >= 0 && x < this.getWidth() && y >= 0 && y < this.getLength()) {
					collisionMap.get(x, y).add(entity);
				}
			}
		}
	}

	/**
	 * Checks if world contains entity.
	 *
	 * @param entity
	 *            Entity to be checked
	 */
	public boolean containsEntity(AbstractEntity entity) {
		return entities.contains(entity);
	}

	/**
	 * Removes entity from the world.
	 *
	 * @param entity
	 *            Entity to be removed
	 */
	public void removeEntity(AbstractEntity entity) {
		entities.remove(entity);

		//Remove from collision map
		int[] collisionCoords = makeCollisionCoords(entity);

		int xVal = collisionCoords[0];
		int yVal = collisionCoords[2];
		
		if (xVal > 0 && xVal < this.getWidth() && yVal > 0 && yVal < this.getLength()) {
			for (int x = collisionCoords[0]; x < collisionCoords[1]; x++) {
				for (int y = collisionCoords[2]; y < collisionCoords[3]; y++) {
					try {
						collisionMap.get(x, y).remove(entity);
					} catch(IndexOutOfBoundsException ex) {
						LOGGER.warn("Can't remove entity",ex);
					}
				}
			}
		}
	}

	/**
	 * Gets the collision map of the world.
	 * yes this uses a lot of memory.
	 *
	 * @return the map of collisions of the world.
	 */
	public Array2D<List<AbstractEntity>> getCollisionMap() {
		return collisionMap;
	}

	/**
	 * Changes world width to a specified new width, provided new width is > 0
	 *
	 * @param width
	 *            New width
	 */
	public void setWidth(int width) {
		if (width > 0) {
			this.width = width;
		}
	}

	/**
	 * Changes world length to a specified new width, provided new height is > 0
	 *
	 * @param length
	 *            New length
	 */
	public void setLength(int length) {
		if (length > 0) {
			this.length = length;
		}
	}

	/**
	 * Returns world width.
	 *
	 * @return width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns world length.
	 *
	 * @return length
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Returns the string of the loaded file used to create the world.
	 *
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

	/**
	 * Returns the type of weather specified in the Tiled map.
	 * @return The weather type of this map
	 */
	public Weathers getWeatherType() {
		return weather;
	}
	
	/**
	 * Sets the weather of the world to newWeather
	 * 
	 * @param newWeather
	 * 			the world's new weather type
	 */
	public void setWeather(Weathers newWeather) {
		weather = newWeather;
	}

	/**
	 * Returns the starting X for the player
	 * @return the starting X for the player
	 */
	public float getStartingPlayerX() {
		return startingPlayerX;

	}

	/**
	 * Returns the starting Y for the player
	 * @return the starting Y for the player
	 */
	public float getStartingPlayerY() {
		return startingPlayerY;
	}
	
	/**
	 * If this is the safezone, ensure the plants re-observe the stopwatch
	 */
	public void loadPlantObservers() {
		if(!(this.equals(SAFEZONE))) {
			return;
		}
		for(AbstractEntity entity : this.getEntities()) {
			if(entity instanceof Pot && !((Pot)entity).isEmpty()) {
				((Pot)entity).getPlant().setObserver();
			}
		}
	}

	/**
	 * Makes a few puddles at the world. Will add rainpuddle if weather is rain, 
	 * icepuddle if weather is snow and none otherwise.
	 */
  public void generatePuddles() {
    
    MapProperties mapProperties = new MapProperties();
    
    // get texture manager
    TextureManager textureManager = (TextureManager) 
        GameManager.get().getManager(TextureManager.class);
    
    String layerName;
    String texture;
    
    switch(this.weather) {
      case RAIN:
        // lets make a rain puddle!
        layerName = "puddle";
        texture = "rainpuddle";
        break;
        
      case SNOW:
        // lets make an ice puddle!
        layerName = "icepuddle";
        texture = "icepuddle";
        break;
        
      default:
        return;
    }
    
    mapProperties.put("name", layerName);
    mapProperties.put("speed", "1");
    
    this.addTiledMapTileLayer(layerName, mapProperties);
    
    Random rand = new Random();
    
    int successes = -3 - rand.nextInt(2);
    
    // checked tiles list. there will only be at most our number of tiles created * 10^2 of these
    List<String> tilesTaken = new ArrayList<String>((0 - successes) * 10 * 10);
    
    // pick a random square on the map, check its surroundings are not null
    // we will time out after 200 attempts
    for (int i = 0; i < 200; i++) {
      
      // pick a random pair of numbers  
      int posX = 5 + rand.nextInt(this.getWidth() - 10);
      int posY = 5 + rand.nextInt(this.getLength() - 10);

      Boolean success = true;
      List<String> tilesSuccess = new ArrayList<String>(10 * 10);
      
      // check that we have chosen a valid tile (i.e. enough space around it
      for (int x = posX - 5; x < posX + 5 && (success); x++) {
        for (int y = posY - 5; y < posY + 5; y++) {
          if (this.getTiledMapTileLayerAtPos(y, x) == null) {
            success = false;
            break;
            
          } else if (tilesTaken.contains(String.format("%d,%d", y, x))){
            success = false;
            break;
            
          } else {
            // make sure we aren't on an exit tile! this would be bad. same as water tiles
            String name = (String) this.getTiledMapTileLayerAtPos(y, x).getProperties().get("name");
            
            if (name != null && ("exit".equals(name) || "water-deep".equals(name) || 
                  "water-shallow".equals(name))) {
              
              success = false;
              break;
              
            }
            // make sure we aren't on an damage tile either
            if (this.getTiledMapTileLayerAtPos(y, x).getProperties().get("damage") != null) {
              success = false;
              break;
            }
            // make sure we aren't on top of a slippery tile too
            if (this.getTiledMapTileLayerAtPos(y, x).getProperties().get("slippery") != null) {
              success = false;
              break;
            }

            tilesSuccess.add(String.format("%d,%d", y, x));
          }

        }
      }
      
      if (success) {
        // add tile
        this.newTileAtPos((int) posX, (int) posY, textureManager.getTexture(texture),
           (TiledMapTileLayer) this.getMapLayerWithProperty("name", layerName));
        
        // add to successful blocks created. If we've made enough then break
        successes++;
        if (successes > 0) {
          break;
        }

        // add all the tiles used in this puddle creation to the tilesTaken list
        for (String str : tilesSuccess) {
          tilesTaken.add(str);
        }
        
      }
      
    }    

  }
}