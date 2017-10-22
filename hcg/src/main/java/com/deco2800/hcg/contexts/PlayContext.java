package com.deco2800.hcg.contexts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import java.util.List;
import java.util.Arrays;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.deco2800.hcg.actors.ParticleEffectActor;
import com.deco2800.hcg.contexts.playContextClasses.*;
import com.deco2800.hcg.handlers.MouseHandler;
import com.deco2800.hcg.managers.*;
import com.deco2800.hcg.multiplayer.LevelEndMessage;
import com.deco2800.hcg.renderers.Render3DLights;
import com.deco2800.hcg.renderers.Renderer;
import com.deco2800.hcg.worlds.World;

/**
 * Context representing the playable game itself. Most of the code here was
 * lifted directly out of Hardcor3Gard3ning.java PlayContext should only be
 * instantiated once.
 */
public class PlayContext extends Context {

    // Managers used by the game
    private GameManager gameManager;
    private WeatherManager weatherManager;
    private ParticleEffectManager particleManager;
    private ContextManager contextManager;
    private TimeManager timeManager;
    private PlayerManager playerManager;
    private PlayerInputManager playerInputManager;
    private ShaderManager shaderManager;
    private PlantManager plantManager;
    private WorldManager worldManager;
    private TextureManager textureManager;

    private Table pauseMenu;

    private boolean pauseMenuDisplayed;

    // FIXME mouseHandler is never assigned
    private MouseHandler mouseHandler;

    // Multiplexer to take input and distrubute it
    InputMultiplexer inputMultiplexer;

    // Is the game paused?
    private boolean unpaused = true;

    /**
     * Set the renderer. 3D is for Isometric worlds 2D is for Side Scrolling worlds
     * Check the documentation for each renderer to see how it handles WorldEntity
     * coordinates
     */
    private Renderer renderer = new Render3DLights();

    // Stage and actors for game UI
    // TODO Game UI should probably be moved to a separate file

    //HUDs
    private PlayerStatusDisplay playerStatus;
    private NetworkManager networkManager;
    private ClockDisplay clockDisplay;
    private SoundManager soundManager;
    private ChatStack chatStack;
    private GeneralRadialDisplay weaponRadialDisplay;
    private GeneralRadialDisplay seedRadialDisplay;
    private GeneralRadialDisplay consumableRadialDisplay;
    private GeneralRadialDisplay plantRadialDisplay;
    private PotUnlockDisplay potUnlock;
    private Button plantButton;
    private String[] weaponItems;
    private String[] seedItems;
    private String[] consumableItems;
    private String[] plantItems;

    private Window plantWindow;
    private boolean exitDisplayed = false;

    private Window exitWindow;

    private Stage stage;
    private Skin skin;
    private Skin plantSkin;

    /**
     * Create the PlayContext
     */
    public PlayContext() {

        // Set up managers for this game
        gameManager = GameManager.get();
        weatherManager = (WeatherManager) gameManager.getManager(WeatherManager.class);
        particleManager = (ParticleEffectManager) gameManager.getManager(ParticleEffectManager.class);
        contextManager = (ContextManager) gameManager.getManager(ContextManager.class);
        networkManager = (NetworkManager) gameManager.getManager(NetworkManager.class);
        timeManager = (TimeManager) gameManager.getManager(TimeManager.class);
        playerManager = (PlayerManager) gameManager.getManager(PlayerManager.class);
        playerInputManager = (PlayerInputManager) gameManager.getManager(PlayerInputManager.class);
        shaderManager = (ShaderManager) gameManager.getManager(ShaderManager.class);
        soundManager = (SoundManager) gameManager.getManager(SoundManager.class);
        plantManager = (PlantManager) gameManager.getManager(PlantManager.class);
        worldManager = (WorldManager) gameManager.getManager(WorldManager.class);
        textureManager = (TextureManager) gameManager.getManager(TextureManager.class);

        /* Setup the camera and move it to the center of the world */
        GameManager.get().setCamera(new OrthographicCamera(1920, 1080));
        GameManager.get().getCamera().translate(GameManager.get().getWorld().getWidth() * 32, 0);

        // Setup GUI
        stage = new Stage(new ScreenViewport());
        stage.getBatch().enableBlending();
        skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));
        plantSkin = new Skin(Gdx.files.internal("resources/ui/plant_ui/flat-earth-ui.json"));
        plantSkin.add("cactus",new Texture("resources/ui/plant_ui/cactus.png"));
        plantSkin.add("grass",new Texture("resources/ui/plant_ui/grass.png"));
        plantSkin.add("ice",new Texture("resources/ui/plant_ui/ice.png"));
        plantSkin.add("inferno",new Texture("resources/ui/plant_ui/inferno.png"));
        plantSkin.add("lily",new Texture("resources/ui/plant_ui/lily.png"));
        plantSkin.add("sunflower",new Texture("resources/ui/plant_ui/sunflower.png"));

        plantItems = new String[]{"sunflower", "water", "ice", "explosive","fire","grass"};
        List<String> plantList = Arrays.asList(plantItems);
        seedItems = new String[]{"sunflowerC", "waterC", "iceC", "explosiveC","fireC","grassC"};
        List<String> seedList = Arrays.asList(seedItems);
        weaponItems = new String[]{"machinegun", "shotgun", "multigun", "starfall"};
        List<String> weapList = Arrays.asList(weaponItems);
        consumableItems = new String[]{"fertiliser", "bug_spray", "snag", "sausage",
                "magic_mushroom", "small_mushroom", "hoe", "trowel", "shovel"};
        List<String> consumableList = Arrays.asList(consumableItems);

        weaponRadialDisplay = new GeneralRadialDisplay(stage, weapList);
        seedRadialDisplay = new GeneralRadialDisplay(stage, seedList);
        consumableRadialDisplay = new GeneralRadialDisplay(stage, consumableList);
        plantRadialDisplay = new GeneralRadialDisplay(stage, plantList);
        createExitWindow();
        createPauseWindow();
        clockDisplay = new ClockDisplay();
        playerStatus = new PlayerStatusDisplay();
        plantWindow = new PlantWindow(plantSkin);
        chatStack = new ChatStack(stage);
        plantButton = new Button(plantSkin.getDrawable("checkbox"));
        plantManager.setPlantButton(plantButton);
        potUnlock = new PotUnlockDisplay(stage, plantSkin);
        

        /* Add ParticleEffectActor that controls weather. */
        stage.addActor(weatherManager.getActor());

        stage.addActor(particleManager.getActor());
        stage.addActor(chatStack);
        chatStack.setVisible(false);
        stage.addActor(clockDisplay);
        stage.addActor(playerStatus);
        
        if(gameManager.getWorld().equals(World.SAFEZONE)) {
        	stage.addActor(plantWindow);
        	stage.addActor(plantButton);
        }
        
        weaponRadialDisplay.addRadialMenu(stage);
        seedRadialDisplay.addRadialMenu(stage);
        consumableRadialDisplay.addRadialMenu(stage);
        plantRadialDisplay.addRadialMenu(stage);
        weaponRadialDisplay.hide();
        seedRadialDisplay.hide();
        consumableRadialDisplay.hide();
        plantRadialDisplay.hide();

        /*
         * Setup an Input Multiplexer so that input can be handled by both the UI and
         * the game
         */
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage); // Add the UI as a processor
        InputManager input = (InputManager) GameManager.get().getManager(InputManager.class);
        inputMultiplexer.addProcessor(input);

        input.addKeyDownListener(this::handleKeyDown);
        input.addKeyUpListener(this::handleKeyUp);

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

                GameManager.get().getCamera().translate(originX - GameManager.get().getCamera().position.x,
                        originY - GameManager.get().getCamera().position.y);

                originX = screenX;
                originY = screenY;

                return true;
            }
        });

        /* set initial time */
        timeManager.setDateTime(0, 0, 5, 1, 1, 2047);
        
        /* reset input tick */
        playerInputManager.resetInputTick();

        pauseMenuDisplayed = false;
    }

    /**
     * Renderer thread Must update all displayed elements using a Renderer
     */
    @Override
    public void render(float delta) {
        /*
         * All sorts of fun things happen in here. This will likely get its own method
         * eventually but for now: If any of the shaders fail to compile we default to
         * default SpriteBatch behaviour; a SpriteBatch is initalised with no parameters
         * to draw the game directly to the screen. No extra effects at all.
         *
         * If all shaders are go, we draw the game properly. We initialise a SpriteBatch
         * with our pre-processing shader attached to it, which handles things like
         * day/night cycle etc and we use that to draw the game to a FrameBuffer. We
         * then get rid of our first SpriteBatch because we don't need it anymore and
         * initalise a new one with our post-processing shader attached to it and use it
         * to draw our FrameBuffer to the screen with nice shiny effects in tow.
         */
        GameManager.get().getCamera().update();

        if(!shaderManager.shadersCompiled() || !shaderManager.shadersEnabled()) {
            // Default drawing behaviour. Default to this if any shaders fail to compile.
            SpriteBatch batch = new SpriteBatch();

            batch.setProjectionMatrix(GameManager.get().getCamera().combined);
            BatchTiledMapRenderer tileRenderer = renderer.getTileRenderer(batch);

            tileRenderer.setView(GameManager.get().getCamera());
            tileRenderer.render();

            renderer.render(batch);

            batch.dispose();
        } else {
            shaderManager.render(timeManager, renderer);
        }

        // Update and draw the stage
        stage.act();
        stage.draw();
    }

    /**
     * Resizes the viewport
     *
     * @param width
     *            The new window width.
     * @param height
     *            The new window height.
     */
    @Override
    public void resize(int width, int height) {
        GameManager.get().getCamera().viewportWidth = width;
        GameManager.get().getCamera().viewportHeight = height;
        GameManager.get().getCamera().update();

        stage.getViewport().update(width, height, true);
        playerStatus.setPosition(30f, stage.getHeight()-200f);
        clockDisplay.setPosition(stage.getWidth()-220f, 20f);
        plantWindow.setPosition(stage.getWidth(), stage.getHeight());
        plantButton.setPosition(stage.getWidth()-26, stage.getHeight()-29);
        potUnlock.setPosition(stage.getWidth() / 2f-150f, stage.getHeight() / 2f+100f);
        exitWindow.setPosition(stage.getWidth() / 2, stage.getHeight() / 2);
        weatherManager.resize();
        seedRadialDisplay.setPosition(stage.getWidth() / 2, stage.getHeight() / 2);
        weaponRadialDisplay.setPosition(stage.getWidth() / 2, stage.getHeight() / 2);
        consumableRadialDisplay.setPosition(stage.getWidth() / 2, stage.getHeight() / 2);
        plantRadialDisplay.setPosition(stage.getWidth() / 2, stage.getHeight() / 2);
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
        if (!networkManager.isMultiplayerGame()) {
            unpaused = false;
            soundManager.pauseWeatherSounds();
        }
    }

    @Override
    public void resume() {
        if (!networkManager.isMultiplayerGame()) {
            unpaused = true;
            soundManager.unpauseWeatherSounds();
        }
    }

    @Override
    public void onTick(long gameTickCount) {
        playerStatus.updatePlayerStatus();

    }

    @Override
    public boolean ticksRunning() {
        return unpaused;
    }

    // Handle switching to World Map by pressing "m" or opening the radial display
    private void handleKeyDown(int keycode) {

        if (pauseMenuDisplayed) {
            if (keycode == Input.Keys.ESCAPE) {
                removePauseWindow();
            }
            return;
        }
    	if(keycode == Input.Keys.U && potUnlock.isOpen()) {
    		potUnlock.close();
    	} else if(keycode == Input.Keys.U) {
    		potUnlock.open();
    	} else {
    		potUnlock.close();
    	}
        if (keycode == Input.Keys.J && !weaponRadialDisplay.getActive()) {
            seedRadialDisplay.hide();
            consumableRadialDisplay.hide();
            plantRadialDisplay.hide();
            weaponRadialDisplay.show();
        } else if (keycode == Input.Keys.H && !seedRadialDisplay.getActive()) {
            weaponRadialDisplay.hide();
            consumableRadialDisplay.hide();
            plantRadialDisplay.hide();
            seedRadialDisplay.show();
        } else if (keycode == Input.Keys.K && !consumableRadialDisplay.getActive()) {
        	seedRadialDisplay.hide();
            consumableRadialDisplay.show();
            weaponRadialDisplay.hide();
            plantRadialDisplay.hide();
        } else if (keycode == Input.Keys.G && GeneralRadialDisplay.plantableNearby()
                && !plantRadialDisplay.getActive()) {
            seedRadialDisplay.hide();
            consumableRadialDisplay.hide();
            weaponRadialDisplay.hide();
            plantRadialDisplay.show();
		} else if (keycode == Input.Keys.T) {
            chatStack.setVisible(!chatStack.isVisible());
        } else if ((keycode == Input.Keys.SPACE) && exitDisplayed) {
            exit();
        } else if(keycode == Input.Keys.ESCAPE) {
            playerManager.getPlayer().setPauseDisplayed(true);
    	    addPauseWindow();
        }
	}

	private void handleKeyUp(int keycode) {
        if(keycode == Input.Keys.H) {
            seedRadialDisplay.hide();
        } else if(keycode == Input.Keys.J) {
            weaponRadialDisplay.hide();
        } else if(keycode == Input.Keys.K) {
            consumableRadialDisplay.hide();
        } else if(keycode == Input.Keys.G) {
            plantRadialDisplay.hide();
        }
    }

	private void exit() {
    	if (networkManager.isMultiplayerGame()) {
		networkManager.queueMessage(new LevelEndMessage());
    	}
    	worldManager.completeLevel();
    	exitDisplayed = false;
    }

    private void createExitWindow() {
        exitWindow = new Window("Complete Level?", skin);
        Button yesButton = new TextButton("Yes", skin);
        yesButton.pad(5, 10, 5, 10);

        /* Add a programmatic listener to the buttons */
        yesButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	exit();
            }
        });
        exitWindow.add(yesButton);
        exitWindow.pack();
        exitWindow.setMovable(false); // So it doesn't fly around the screen
        exitWindow.setWidth(150);
    }

    public void addExitWindow() {
        if(exitWindow.getStage() == null) {
            /* Add the window to the stage */
            stage.addActor(exitWindow);
            exitDisplayed = true;
            soundManager.pauseWeatherSounds();
        }
    }

    public void removeExitWindow() {
        exitWindow.remove();
        exitDisplayed = false;
        soundManager.unpauseWeatherSounds();
    }

    public void addParticleEffect(ParticleEffectActor actor) {
        stage.addActor(actor);
    }

    private void createPauseWindow() {
        pauseMenu = new Table();
        ImageButton quit = new ImageButton(new Image(textureManager.getTexture("menu_quit_button")).getDrawable());
        ImageButton instructions = new ImageButton(new Image(textureManager.getTexture("menu_instructions_button")).getDrawable());
        ImageButton options = new ImageButton(new Image(textureManager.getTexture("menu_options_button")).getDrawable());
        ImageButton back = new ImageButton(new Image(textureManager.getTexture("menu_back_button")).getDrawable());
        ImageButton resume = new ImageButton(new Image(textureManager.getTexture("menu_resume_button")).getDrawable());
        quit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.exit(0);
            }
        });
        instructions.addListener(new ChangeListener() {
           @Override
           public void changed(ChangeEvent event, Actor actor) {
               contextManager.pushContext(new InstructionsMenuContext());
           }
        });
        resume.addListener(new ChangeListener() {
           @Override
           public void changed(ChangeEvent event, Actor actor) {
               removePauseWindow();
           }
        });
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                removePauseWindow();
                playerManager.despawnPlayers();
                contextManager.popContext();
            }
        });
        pauseMenu.add(resume);
        pauseMenu.row();
        pauseMenu.add(instructions);
        pauseMenu.row();
        pauseMenu.add(options);
        pauseMenu.row();
        pauseMenu.add(back);
        pauseMenu.row();
        pauseMenu.add(quit);
        pauseMenu.setPosition(stage.getWidth()/2, stage.getHeight()/2);
        pauseMenuDisplayed = true;
    }

    public void addPauseWindow() {
        if (pauseMenu.getStage() == null){
			/* Add the window to the stage */
            stage.addActor(pauseMenu);
            pauseMenuDisplayed = true;
            soundManager.pauseWeatherSounds();
        }
    }

    public void removePauseWindow() {
        playerManager.getPlayer().setPauseDisplayed(false);
        pauseMenu.remove();
        pauseMenuDisplayed = false;
        soundManager.unpauseWeatherSounds();
    }
}
