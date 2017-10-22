package com.deco2800.hcg.managers;

import com.deco2800.hcg.contexts.PlayContext;
import com.deco2800.hcg.entities.worldmap.MapNode;
import com.deco2800.hcg.entities.worldmap.WorldMap;
import com.deco2800.hcg.entities.worldmap.WorldStack;
import com.deco2800.hcg.worldmapui.LevelStore;
import com.deco2800.hcg.worldmapui.WorldStackGenerator;
import com.deco2800.hcg.worlds.World;

/**
 * Manager encapsulating the world stack generator.
 */
public class WorldManager extends Manager {
	
	GameManager gameManager = GameManager.get();
	private ContextManager contextManager = (ContextManager) gameManager.getManager(ContextManager.class);
	private PlayerManager playerManager = (PlayerManager) gameManager.getManager(PlayerManager.class);
	private PlayerInputManager playerInputManager =
			(PlayerInputManager) gameManager.getManager(PlayerInputManager.class);
	
	/**
	 * The WorldStackGenerator instance.
	 */
	WorldStackGenerator worldStackGenerator = new WorldStackGenerator(new LevelStore().getLevels());

	/**
	 * Sets the generator seed for the map generator used.
	 * @param seed
	 *     The seed to set to.
	 */
	public void setGeneratorSeed(int seed) {
		worldStackGenerator.setGeneratorSeed(seed);
	}
	
	/**
	 * Generates and sets the game's world stack.
	 */
	public void generateAndSetWorldStack() {
        WorldStack worldStack = worldStackGenerator.generateWorldStack();
        gameManager.setWorldStack(worldStack);
	}
	
	/**
	 * Sets the specified world map.
	 * @param index
	 *     The new world map index.
	 */
	public void setWorldMap(int index) {
		WorldMap map = gameManager.getWorldStack().getWorldStack().get(index);
		gameManager.setWorldMap(map);
	}
	
	/**
	 * Selects the specified node.
	 * @param index
	 *     The new node index.
	 */
	public void selectNode(int index) {
		MapNode node = gameManager.getWorldMap().getContainedNodes().get(index);
		gameManager.setOccupiedNode(node);
		
		/*
		 * Simply loading in the world file caused bugs with movement
		 * due to the same world being loaded multiple times. This seems
		 * to fix that problem.
		 */

		gameManager.setOccupiedNode(node);

		// delete stopwatches
        ((StopwatchManager) gameManager.getManager(StopwatchManager.class)).deleteObservers();
        
        if(node.getNodeType() == 0) {
        	gameManager.setWorld(World.SAFEZONE);
        } else {
        	// create new world
    		World newWorld = new World(node.getNodeLinkedLevel().getWorld().getLoadedFile());
    		
            // add the new weather effects
            ((WeatherManager) gameManager.getManager(WeatherManager.class)).
              setWeather(newWorld.getWeatherType());

            newWorld.generatePuddles();
    		gameManager.setWorld(newWorld);
        }
		playerManager.spawnPlayers();
		playerInputManager.resetInputTick();
		contextManager.pushContext(new PlayContext());
	}
	
	/**
	 * Ends the current level
	 */
	public void completeLevel() {
        if(gameManager.getCurrentNode().getNodeType() != 3) {
            gameManager.getCurrentNode().changeNodeType(2);
            if (gameManager.getMapContext() != null) {
                gameManager.getMapContext().updateMapDisplay(gameManager.getWorldMap());
            }
            contextManager.popContext();
        } else {
            gameManager.getCurrentNode().changeNodeType(2);
            if (gameManager.getMapContext() != null) {
                gameManager.getMapContext().updateMapDisplay(gameManager.getWorldMap());
            }
            gameManager.getMapContext().addEndOfContext();
            contextManager.popContext();
        }
        // clear old observers (mushroom turret for example)
        World world = gameManager.getWorld();
        StopwatchManager manager = (StopwatchManager) GameManager.get().getManager(StopwatchManager.class);
        manager.deleteObservers();

        // stop the old weather effects
        ((WeatherManager) GameManager.get().getManager(WeatherManager.class)).stopAllEffect();
	}
	
}
