package com.deco2800.hcg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.TimeUtils;
import com.deco2800.hcg.contexts.MainMenuContext;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.Tickable;
import com.deco2800.hcg.entities.worldmap.Level;
import com.deco2800.hcg.entities.worldmap.WorldMap;
import com.deco2800.hcg.entities.garden_entities.plants.Planter;
import com.deco2800.hcg.handlers.MouseHandler;
import com.deco2800.hcg.managers.*;
import com.deco2800.hcg.renderers.Renderable;
import com.deco2800.hcg.worldmapui.MapGenerator;
import com.deco2800.hcg.worlds.BlankTestWorld;

import java.util.ArrayList;

/**
 * Handles the creation of the world and rendering.
 */
public class Hardcor3Gard3ning extends Game {

    private GameManager gameManager;
    private ContextManager contextManager;
    private SoundManager soundManager;
    private PlayerManager playerManager;
    private TextureManager textureManager;
    private TimeManager timeManager;
	private InputManager inputManager;
	private PlantManager plantManager;
	private ItemManager itemManager;
	private StopwatchManager stopwatchManager;
	private NetworkManager networkManager;
    private MouseHandler mouseHandler;
    private long gameTickCount = 0;
    private long gameTickPeriod = 20;  // Tickrate = 50Hz
    private long nextGameTick = TimeUtils.millis() + gameTickPeriod;

    /**
     * Creates the required objects for the game to start. Called when the game first starts
     */
    @Override
    public void create() {

        // Create game manager
        gameManager = GameManager.get();

		/* Create a context manager, and set is as the screen target */
        contextManager = (ContextManager) gameManager.getManager(ContextManager.class);
        this.setScreen(contextManager);
        // Create a texture manager
        textureManager = (TextureManager) gameManager.getManager(TextureManager.class);

		/* Create a sound manager for the whole game */
        soundManager = (SoundManager) gameManager.getManager(SoundManager.class);

		/* Create a time manager. */
        timeManager = (TimeManager) gameManager.getManager(TimeManager.class);

        /* Create an input manager. */
        inputManager = (InputManager) gameManager.getManager(InputManager.class);
        inputManager.addKeyUpListener(new Planter());
        
        /* Create a player manager. */
        playerManager = (PlayerManager) gameManager.getManager(PlayerManager.class);
        Player player = new Player(5, 10, 0);
        player.initialiseNewPlayer(5, 5, 5, 5, 5, 20);
        playerManager.setPlayer(player);
        
        ArrayList<Level> levelList = new ArrayList<Level>();
        // Creates some test levels
        Level testLevel = new Level(new BlankTestWorld(), 0, 1, 1);
        Level testLevel2 = new Level(new BlankTestWorld(), 0, 1, 0);
        Level testLevel3 = new Level(new BlankTestWorld(), 0, 1, 1);
        Level testLevel4 = new Level(new BlankTestWorld(), 0, 1, 2);

        // Eventually this will contain all the playable game levels
        levelList.add(testLevel);
        levelList.add(testLevel2);
        levelList.add(testLevel3);
        levelList.add(testLevel4);
        
        /* Create a plant manager. */
        plantManager = (PlantManager) gameManager.getManager(PlantManager.class);
        
        /* Create an item manager */
        itemManager = (ItemManager) gameManager.getManager(ItemManager.class); 
        
        /* Setup stopwatch manager */
        stopwatchManager = (StopwatchManager) gameManager.getManager(StopwatchManager.class);
        stopwatchManager.startTimer(1);
        
        /* Create a network manager */
        networkManager = (NetworkManager) gameManager.getManager(NetworkManager.class);

        // Procedurally generate the world map and store it.
        MapGenerator mapGenerator = new MapGenerator(levelList);
        WorldMap worldMap = mapGenerator.generateWorldMap();
        gameManager.setWorldMap(worldMap);
        
        contextManager.pushContext(new MainMenuContext());

    }

    /**
     * Renderer thread Must update all displayed elements using a Renderer
     */
    @Override
    public void render() {
		if (networkManager.isInitialised()) {
			networkManager.processReceivedMessages();
		}
        fireTicks();
        clearScreen();
        super.render(); // Will render current context
        updateTitle();
    }

    /**
     * Disposes of assets etc when the rendering system is stopped.
     */
    @Override
    public void dispose() {
        // Don't need this at the moment
        contextManager.dispose();
    }

    /**
     * Clears the entire display as we are using lazy rendering
     */
    public void clearScreen() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    /**
     * Fires waiting game ticks
     */
    private void fireTicks() {
        while (TimeUtils.millis() >= nextGameTick) {
            if (contextManager.ticksRunning()) {

                // Tick managers
                GameManager.get().onTick(gameTickCount);

                // Tick entities
                for (Renderable e : GameManager.get().getWorld().getEntities()) {
                    if (e instanceof Tickable) {
                        ((Tickable) e).onTick(gameTickCount);
                    }
                }

                // Increment tick count
                gameTickCount += 1;
            }

            // Schedule next tick
            nextGameTick += gameTickPeriod;
        }
    }

    /**
     * Display FPS in window title
     */
    private void updateTitle() {
        Gdx.graphics.setTitle(
                "DECO2800 " + this.getClass().getCanonicalName() + " - FPS: " + Gdx.graphics
                        .getFramesPerSecond());
    }

}
