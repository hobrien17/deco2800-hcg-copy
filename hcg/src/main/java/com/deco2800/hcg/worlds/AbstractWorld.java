package com.deco2800.hcg.worlds;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.managers.GameManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * AbstractWorld is the Game AbstractWorld
 *
 * It provides storage for the WorldEntities and other universal world level items.
 */
public abstract class AbstractWorld {

    private List<AbstractEntity> entities = new ArrayList<AbstractEntity>();
    protected TiledMap map;

    private int width;
    private int length;

    /**
     * Returns a list of entities in this world
     * @return All Entities in the world
     */
    public List<AbstractEntity> getEntities() {
        return new ArrayList<AbstractEntity>(this.entities);
    }

    /**
     * Returns the TiledMapTileLayer that contains the cell at the 
     * given X and Y position. See documentation on TiledMapTileLayer.
     * 
     * @param posX X position
     * @param posY Y position
     * @return A TiledMapTileLayer that contains the players current cell. 
     * Null if no such TiledMapTileLayer exists.
     */
    public TiledMapTileLayer getTiledMapTileLayerAtPos(int posX, int posY) {
    	// loop through all layers
		Iterator<MapLayer> itr = GameManager.get().getWorld().
				getMap().getLayers().iterator();
		
		while(itr.hasNext()) {
			
			TiledMapTileLayer layer = (TiledMapTileLayer)itr.next();
			
			if (layer.getCell(posX, posY) != null) {
				return (TiledMapTileLayer)layer;
			}
		
		}
			
		return null;
    	
    }
    
    /**
     * Returns the current map for this world
     * @return Map object for this world
     */
    public TiledMap getMap() {
        return this.map;
    }


    public void addEntity(AbstractEntity entity) {
        entities.add(entity);
    }

    public void removeEntity(AbstractEntity entity) {
        entities.remove(entity);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }
}
