package com.deco2800.hcg.managers;

import com.deco2800.hcg.contexts.PlayContext;
import com.deco2800.hcg.entities.worldmap.MapNode;
import com.deco2800.hcg.entities.worldmap.WorldStack;
import com.deco2800.hcg.worldmapui.LevelStore;
import com.deco2800.hcg.worldmapui.WorldStackGenerator;
import com.deco2800.hcg.worlds.World;

/**
 * Manager encapsulating the world stack generator.
 */
public class WorldManager extends Manager {
	
	GameManager gameManager = GameManager.get();
	SoundManager soundManager = (SoundManager) GameManager.get()
	.getManager(SoundManager.class);
	private PlayerManager playerManager = (PlayerManager) gameManager.getManager(PlayerManager.class);
	private ContextManager contextManager = (ContextManager) gameManager.getManager(ContextManager.class);
	
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
        
        // create new world
		World newWorld = new World(node.getNodeLinkedLevel().getWorld().getLoadedFile());
		
        // add the new weather effects
        ((WeatherManager) gameManager.getManager(WeatherManager.class)).
          setWeather(newWorld.getWeatherType());
        
        // play ambient music
        soundManager.stopSound("ambientMusic");
        soundManager.loopSound("ambientMusic");

        newWorld.generatePuddles();
		gameManager.setWorld(newWorld);
		playerManager.spawnPlayers();
		contextManager.pushContext(new PlayContext());
	}
	
}
