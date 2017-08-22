package com.deco2800.hcg;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.deco2800.hcg.entities.Selectable;
import com.deco2800.hcg.entities.Tickable;
import com.deco2800.hcg.handlers.MouseHandler;
import com.deco2800.hcg.managers.*;
import com.deco2800.hcg.renderers.Render3D;
import com.deco2800.hcg.renderers.Renderable;
import com.deco2800.hcg.renderers.Renderer;
import com.deco2800.hcg.worlds.DemoWorld;
import com.deco2800.hcg.worlds.GardenDemo;
import com.deco2800.hcg.entities.Player;

/**
 * Handles the creation of the world and rendering.
 *
 */
public class Hardcor3Gard3ning extends ApplicationAdapter implements ApplicationListener {

	
	/**
	 * Set the renderer.
	 * 3D is for Isometric worlds
	 * 2D is for Side Scrolling worlds
	 * Check the documentation for each renderer to see how it handles WorldEntity coordinates
	 */
	private Renderer renderer = new Render3D();

	/**
	 * Create a camera for panning and zooming.
	 * Camera must be updated every render cycle.
	 */

	private SoundManager soundManager;
	private MouseHandler mouseHandler;
	private PlayerManager playerManager;
	private TextureManager textureManager;
	private TimeManager timeManager;

	private Stage stage;
	private Window window;

	private boolean ticking = true;
	private long gameTickCount = 0;
	private long gameTickPeriod = 20;  // Tickrate = 50Hz
	private long nextGameTick = TimeUtils.millis() + gameTickPeriod;

	private Label clockLabel;
	/**
	 * Creates the required objects for the game to start.
	 * Called when the game first starts
	 */
	@Override
	public void create () {

		textureManager = ((TextureManager) GameManager.get().getManager(TextureManager.class));
		textureManager.saveTexture("ground", "resources/maps/environment/ground.png");
		textureManager.saveTexture("squirrel", "resources/sprites/enemies/squirrel.png");
		textureManager.saveTexture("tower", "resources/sprites/misc/tower.png");
		textureManager.saveTexture("seed", "resources/sprites/seeds/Battle seed.png");
		textureManager.saveTexture("plant", "resources/sprites/plants/plant.png");
		textureManager.saveTexture("plant2", "resources/sprites/plants/plant2.png");
		
		/**
		 *	Set up new stuff for this game
		 */
		/* Create an example world for the engine */
		GameManager.get().setWorld(new DemoWorld());
		
		/* Create a sound manager for the whole game */
		soundManager = (SoundManager) GameManager.get().getManager(SoundManager.class);

		
		/* Create a player manager. */
		playerManager = (PlayerManager)GameManager.get().getManager(PlayerManager.class);

		Player player = new Player(5, 10, 0);
		player.initialiseNewPlayer(5,5,5,5,5,20);
		playerManager.setPlayer(player);
		GameManager.get().getWorld().addEntity(playerManager.getPlayer());

		/* Create a time manager. */
		timeManager = (TimeManager) GameManager.get().getManager(TimeManager.class);

		/**
		 * Setup the game itself
		 */
		/* Setup the camera and move it to the center of the world */
		GameManager.get().setCamera(new OrthographicCamera(1920, 1080));
		GameManager.get().getCamera().translate(GameManager.get().getWorld().getWidth()*32, 0);

		/**
		 * Setup GUI
		 */
		stage = new Stage(new ScreenViewport());
		Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));
		window = new Window("Menu", skin);

		/* Add a quit button to the menu */
		Button button = new TextButton("Quit", skin);

		/* Add another button to the menu */
		Button anotherButton = new TextButton("Play Duck Sound", skin);

		/* Add a label for the clock; change to be prettier later. */
		clockLabel = new Label(timeManager.getDateTime(), skin);
		timeManager.setLabel(clockLabel);
		
		/* Add a programatic listener to the quit button */
		button.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				System.exit(0);
			}
		});

		/* Add a handler to play a sound */
		anotherButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				soundManager.playSound("quack");
			}
		});



		/* Add all buttons to the menu */
		window.add(button);
		window.add(anotherButton);



		window.add(clockLabel);



		window.pack();
		window.setMovable(false); // So it doesn't fly around the screen
		window.setPosition(0, stage.getHeight()); // Place it in the top left of the screen

		/* Add the window to the stage */
		stage.addActor(window);


		/**
		 * Setup inputs for the buttons and the game itself
		 */
		/* Setup an Input Multiplexer so that input can be handled by both the UI and the game */
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(stage); // Add the UI as a processor

		InputManager input = (InputManager) GameManager.get().getManager(InputManager.class);
		inputMultiplexer.addProcessor(input);
        /*
         * Set up some input handlers for panning with dragging.
         */
		inputMultiplexer.addProcessor(new InputAdapter() {

			int originX;
			int originY;

			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				originX = screenX;
				originY = screenY;


				Vector3 worldCoords = GameManager.get().getCamera().unproject(new Vector3(screenX, screenY, 0));
				mouseHandler.handleMouseClick(worldCoords.x, worldCoords.y);

				return true;
			}

			@Override
			public boolean touchDragged(int screenX, int screenY, int pointer) {

				originX -= screenX;
				originY -= screenY;

				// invert the y axis
				originY = -originY;

				originX += GameManager.get().getCamera().position.x;
				originY += GameManager.get().getCamera().position.y;

				GameManager.get().getCamera().translate(originX - GameManager.get().getCamera().position.x, originY - GameManager.get().getCamera().position.y);

				originX = screenX;
				originY = screenY;

				return true;
			}
		});

		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	/**
	 * Renderer thread
	 * Must update all displayed elements using a Renderer
	 */
	@Override
	public void render () {

		// Fire waiting game ticks
		while (TimeUtils.millis() >= nextGameTick) {
			if (ticking) {

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

        /*
         * Create a new render batch.
         * At this stage we only want one but perhaps we need more for HUDs etc
         */
		SpriteBatch batch = new SpriteBatch();



        /*
         * Update the camera
         */
		GameManager.get().getCamera().update();
		batch.setProjectionMatrix(GameManager.get().getCamera().combined);

        /*
         * Clear the entire display as we are using lazy rendering
         */
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /* Render the tiles first */
		BatchTiledMapRenderer tileRenderer = renderer.getTileRenderer(batch);
		tileRenderer.setView(GameManager.get().getCamera());
		tileRenderer.render();

		/*
         * Use the selected renderer to render objects onto the map
         */
		renderer.render(batch);

		stage.act();
		stage.draw();

		/* Dispose of the spritebatch to not have memory leaks */
		batch.dispose();

		/* Display FPS in window title */
		Gdx.graphics.setTitle("DECO2800 " + this.getClass().getCanonicalName() +  " - FPS: "+ Gdx.graphics.getFramesPerSecond());
	}


	/**
	 * Resizes the viewport
	 * @param width
	 * @param height
	 */
	@Override
	public void resize(int width, int height) {
		GameManager.get().getCamera().viewportWidth = width;
		GameManager.get().getCamera().viewportHeight = height;
		GameManager.get().getCamera().update();

		stage.getViewport().update(width, height, true);
		window.setPosition(0, stage.getHeight());
	}

	/**
	 * Disposes of assets etc when the rendering system is stopped.
	 */
	@Override
	public void dispose () {
		// Don't need this at the moment
	}
	
	
}
