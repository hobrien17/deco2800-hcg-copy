package com.deco2800.hcg.managers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.deco2800.hcg.contexts.WorldMapContext;
import com.deco2800.hcg.entities.worldmap.MapNode;
import com.deco2800.hcg.entities.worldmap.WorldMap;
import com.deco2800.hcg.worlds.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Game manager manages all the components of the game. Throughout we call
 * GameManager GM Created by timhadwen on 30/7/17.
 */
public class GameManager implements TickableManager {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(GameManager.class);

    private static GameManager instance = null;

    private List<Manager> managers = new ArrayList<>();

    private World gameWorld;

    private OrthographicCamera camera;

    private WorldMap worldMap;
    
    private MapNode occupiedNode;
    
    private WorldMapContext mapContext;

    /**
     * Returns an instance of the GM
     *
     * @return GameManager
     */
    public static GameManager get() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    /**
     * Private constructor to inforce use of get()
     */
    private GameManager() {

    }

    /**
     * Adds a manager component to the GM
     */
    public void addManager(Manager manager) {
        managers.add(manager);
    }

    /**
     * Retrives a manager from the list. If the manager does not exist one will
     * be created, added to the list and returned
     *
     * @param type The class type (ie SoundManager.class)
     * @return A Manager component of the requested type
     */
    public Manager getManager(Class<?> type) {
        /* Check if the manager exists */
        for (Manager m : managers) {
            if (m.getClass() == type) {
                return m;
            }
        }

		/* Otherwise create one */
        try {
            Constructor<?> ctor = type.getConstructor();
            this.addManager((Manager) ctor.newInstance());
        } catch (Exception e) {
            // Gotta catch 'em all
            LOGGER.error(e.toString());
            // e.printStackTrace();
        }

		/* And then return it */
        for (Manager m : managers) {
            if (m.getClass() == type) {
                return m;
            }
        }
        LOGGER.warn("GameManager.get returned null! It shouldn't have!");
        return null;
    }

    /**
     * Sets the current game world
     */
    public void setWorld(World world) {
        this.gameWorld = world;
    }

    /**
     * Gets the current game world
     */
    public World getWorld() {
        return gameWorld;
    }

    /**
     * @deprecated
     */
    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    /**
     * @deprecated
     */
    public OrthographicCamera getCamera() {
        return camera;
    }

    /**
     * On tick method for ticking managers with the TickableManager interface
     *
     * @param gameTickCount Current game tick
     */
    @Override
    public void onTick(long gameTickCount) {
        for (Manager m : managers) {
            if (m instanceof TickableManager) {
                ((TickableManager) m).onTick(gameTickCount);
            }
        }
    }

    /**
     * Gets the worldMap
     * @return the stored WorldMap structure
     */
    public WorldMap getWorldMap() {
    	return worldMap;
    }

    /**
     * Sets the worldMap
     * @param worldMap the generated map to store
     */
    public void setWorldMap(WorldMap worldMap) {
    	this.worldMap = worldMap;
    }
    
    public MapNode getCurrentNode() {
    	return occupiedNode;
    }
    
    public void setOccupiedNode(MapNode node) {
    	occupiedNode = node;
    }
    
    public void setMapContext(WorldMapContext context) {
    	mapContext = context;
    }
    
    public WorldMapContext getMapContext() {
    	return mapContext;
    }
}