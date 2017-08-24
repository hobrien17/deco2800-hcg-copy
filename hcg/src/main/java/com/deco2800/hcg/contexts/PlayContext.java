package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.deco2800.hcg.handlers.MouseHandler;
import com.deco2800.hcg.managers.*;
import com.deco2800.hcg.renderers.Render3D;
import com.deco2800.hcg.renderers.Renderer;

/**
 * Context representing the playable game itself. Most of the code here was
 * lifted directly out of Hardcor3Gard3ning.java PlayContext should only be
 * instantiated once.
 */
public class PlayContext extends Context {

    // Managers used by the game
    private GameManager gameManager;
    private SoundManager soundManager;
    private PlayerManager playerManager;
    private TimeManager timeManager;
    private ContextManager contextManager;

    //FIXME mouseHandler is never assigned
    private MouseHandler mouseHandler;

    // Multiplexer to take input and distrubute it
    InputMultiplexer inputMultiplexer;

    // Is the game paused?
    private boolean unpaused = true;

    /**
     * Set the renderer. 3D is for Isometric worlds 2D is for Side Scrolling
     * worlds Check the documentation for each renderer to see how it handles
     * WorldEntity coordinates
     */
    private Renderer renderer = new Render3D();

    // Stage and actors for game UI
    //TODO Game UI should probably be moved to a separate file
    private Stage stage;
    private Window window;
    private Label clockLabel;

    /**
     * Create the PlayContext
     */
    public PlayContext() {

        // Set up managers for this game
        gameManager = GameManager.get();
        soundManager = (SoundManager) gameManager
                .getManager(SoundManager.class);
        timeManager = (TimeManager) gameManager.getManager(TimeManager.class);
        playerManager = (PlayerManager) gameManager
                .getManager(PlayerManager.class);
        contextManager = (ContextManager) gameManager
                .getManager(ContextManager.class);

		/* Setup the camera and move it to the center of the world */
        GameManager.get().setCamera(new OrthographicCamera(1920, 1080));
        GameManager.get().getCamera()
                .translate(GameManager.get().getWorld().getWidth() * 32, 0);

        // Setup GUI
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

		/* Add a programmatic listener to the quit button */
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                contextManager.popContext();
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
        window.setPosition(0,
                stage.getHeight()); // Place it in the top left of the screen

		/* Add the window to the stage */
        stage.addActor(window);

		/*
         * Setup inputs for the buttons and the game itself
		 */
        /* Setup an Input Multiplexer so that input can be handled by both the UI and the game */
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage); // Add the UI as a processor
        InputManager input = (InputManager) GameManager.get()
                .getManager(InputManager.class);
        inputMultiplexer.addProcessor(input);

		/*
		 * Set up some input handlers for panning with dragging.
		 */
        inputMultiplexer.addProcessor(new InputAdapter() {

            int originX;
            int originY;

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer,
                    int button) {
                originX = screenX;
                originY = screenY;

                Vector3 worldCoords = GameManager.get().getCamera()
                        .unproject(new Vector3(screenX, screenY, 0));
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

                GameManager.get().getCamera()
                        .translate(originX - GameManager.get()
                                        .getCamera().position.x,
                                originY - GameManager.get()
                                        .getCamera().position.y);

                originX = screenX;
                originY = screenY;

                return true;
            }
        });
    }

    /**
     * Renderer thread Must update all displayed elements using a Renderer
     */
    @Override
    public void render(float delta) {

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

		/* Render the tiles first */
        BatchTiledMapRenderer tileRenderer = renderer.getTileRenderer(batch);
        tileRenderer.setView(GameManager.get().getCamera());
        tileRenderer.render();

		/*
		 * Use the selected renderer to render objects onto the map
		 */
        renderer.render(batch);

        // Update and draw the stage
        stage.act();
        stage.draw();

		/* Dispose of the spritebatch to not have memory leaks */
        batch.dispose();
    }

    /**
     * Resizes the viewport
     *
     * @param width The new window width.
     * @param height The new window height.
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
    public void dispose() {
        // Don't need this at the moment
    }

    @Override
    public void show() {
        // Capture user input
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void hide() {
        // Release user input
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void pause() {
        unpaused = false;
    }

    @Override
    public void resume() {
        unpaused = true;
    }

    @Override
    public void onTick(long gameTickCount) {
        // Do nothing
    }

    @Override
    public boolean ticksRunning() {
        return unpaused;
    }
}
