package com.deco2800.hcg;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.TimeUtils;
import com.deco2800.hcg.contexts.PlayContext;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.Tickable;
import com.deco2800.hcg.handlers.MouseHandler;
import com.deco2800.hcg.managers.*;
import com.deco2800.hcg.renderers.Renderable;
import com.deco2800.hcg.worlds.DemoWorld;
import com.deco2800.hcg.worlds.WorldMapWorld;
import com.deco2800.hcg.entities.Player;

/**
 * Handles the creation of the world and rendering.
 *
 */
public class Hardcor3Gard3ning extends Game {

	private GameManager gameManager;
	private SoundManager soundManager;
	private PlayerManager playerManager;
	private TextureManager textureManager;
	private TimeManager timeManager;
	private ContextManager contextManager;

	private MouseHandler mouseHandler;

	private long gameTickCount = 0;
	private long gameTickPeriod = 20;  // Tickrate = 50Hz
	private long nextGameTick = TimeUtils.millis() + gameTickPeriod;

	/**
	 * Creates the required objects for the game to start.
	 * Called when the game first starts
	 */
	@Override
	public void create() {

		// Create game manager
		gameManager = GameManager.get();

		// Create a texture manager
		//TODO move loading textures into texture manager
		textureManager = ((TextureManager) gameManager.getManager(TextureManager.class));
		textureManager.saveTexture("ground", "resources/maps/environment/ground.png");
		textureManager.saveTexture("squirrel", "resources/sprites/enemies/squirrel.png");
		textureManager.saveTexture("tower", "resources/sprites/misc/tower.png");
		textureManager.saveTexture("seed", "resources/sprites/seeds/Battle seed.png");
		textureManager.saveTexture("plant", "resources/sprites/plants/plant.png");
		textureManager.saveTexture("plant2", "resources/sprites/plants/plant2.png");
		textureManager.saveTexture("sunflower_01", "resources/sprites/plants/sunflower_01.png");
		textureManager.saveTexture("sunflower_02", "resources/sprites/plants/sunflower_02.png");
		textureManager.saveTexture("sunflower_03", "resources/sprites/plants/sunflower_03.png");
		

		/* currently, level portal dont have any texture. Using "tower" as temporary */
		textureManager.saveTexture("levelPortal", "resources/sprites/misc/tower.png");

		/**
		 *	Set up new stuff for this game
		 */

		/* Create an example world for the engine */
//		GameManager.get().setWorld(new DemoWorld());

        /* testing world map, commend this line below, uncommend line above to get back to the games */
		GameManager.get().setWorld(new DemoWorld());
		
		/* Create a sound manager for the whole game */
		soundManager = (SoundManager) gameManager.getManager(SoundManager.class);

		/* Create a time manager. */
		timeManager = (TimeManager) gameManager.getManager(TimeManager.class);

		/* Create a context manager, and set is as the screen target */
		contextManager = (ContextManager) gameManager.getManager(ContextManager.class);
		this.setScreen(contextManager);

		/* Create a player manager. */
		playerManager = (PlayerManager) gameManager.getManager(PlayerManager.class);


		//TODO everything below this line doesn't belong here

		/* Create an example world for the engine */
		gameManager.setWorld(new DemoWorld());

		contextManager.pushContext(new PlayContext());

		// Set up a player
		Player player = new Player(5, 10, 0);
		player.initialiseNewPlayer(5,5,5,5,5,20);
		playerManager.setPlayer(player);
		gameManager.getWorld().addEntity(playerManager.getPlayer());
	}

	/**
	 * Renderer thread
	 * Must update all displayed elements using a Renderer
	 */
	@Override
	public void render() {
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

	// Clear the entire display as we are using lazy rendering
	public void clearScreen() {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	// Fire waiting game ticks
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

	/* Display FPS in window title */
	private void updateTitle() {
		Gdx.graphics.setTitle("DECO2800 " + this.getClass().getCanonicalName() +  " - FPS: "+ Gdx.graphics.getFramesPerSecond());
	}
	
}
