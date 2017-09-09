package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.deco2800.hcg.handlers.MouseHandler;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.InputManager;
import com.deco2800.hcg.managers.MessageManager;
import com.deco2800.hcg.managers.PlantManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.managers.SoundManager;
import com.deco2800.hcg.managers.TimeManager;
import com.deco2800.hcg.multiplayer.Message;
import com.deco2800.hcg.multiplayer.NetworkState;
import com.deco2800.hcg.renderers.Render3D;
import com.deco2800.hcg.renderers.Renderer;
import com.deco2800.hcg.shading.ShaderState;

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
	private PlantManager plantManager;
	private MessageManager messageManager;

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
	private Renderer renderer = new Render3D();

	// Stage and actors for game UI
	// TODO Game UI should probably be moved to a separate file
	private Stage stage;
	private Window window;
	private Window plantWindow;
	private Table chatWindow;
	private Label plantInfo;
	private Label clockLabel;
	private Label dateLabel;
	private Label chatLabel;
	private TextField chatTextField;
	private TextArea chatTextArea;
	private  Button chatButton;
	
	// TODO make sure this doesn't stay here.
	private ShaderProgram shader;
	private ShaderProgram postShader;

    /**
     * Create the PlayContext
     */
    public PlayContext() {

		// Set up managers for this game
		gameManager = GameManager.get();
		soundManager = (SoundManager) gameManager.getManager(SoundManager.class);
		timeManager = (TimeManager) gameManager.getManager(TimeManager.class);
		playerManager = (PlayerManager) gameManager.getManager(PlayerManager.class);
		contextManager = (ContextManager) gameManager.getManager(ContextManager.class);
        plantManager = (PlantManager) gameManager.getManager(PlantManager.class);
        messageManager = (MessageManager) gameManager.getManager(MessageManager.class);

		/* Setup the camera and move it to the center of the world */
		GameManager.get().setCamera(new OrthographicCamera(1920, 1080));
		GameManager.get().getCamera().translate(GameManager.get().getWorld().getWidth() * 32, 0);

		// Setup GUI
		stage = new Stage(new ScreenViewport());
		Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));
		window = new Window("Menu", skin);
        plantWindow = new Window("Plants", skin);

		/* Add a quit button to the menu */
		Button button = new TextButton("Quit", skin);

		/* Add clock. */
		Image clockImage = new Image(new
				Texture(Gdx.files.internal("resources/ui/clock_outline.png")));
		// clockImage.setPosition(stage.getWidth() - 215, 10);
		clockLabel = new Label(timeManager.getTime(), skin);
		dateLabel = new Label(timeManager.getDate(), skin);
		timeManager.setTimeLabel(clockLabel);
		timeManager.setDateLabel(dateLabel);

		/* Add a programmatic listener to the quit button */
		button.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				contextManager.popContext();
			}
		});


		/* Add all buttons to the menu */
		window.add(button);
		window.add(clockLabel);
		window.pack();
		window.setMovable(false); // So it doesn't fly around the screen
		window.setPosition(0, stage.getHeight()); // Place it in the top left of the screen

		/* Add the window to the stage */
		stage.addActor(window);
		
		/* Create clock GUI and add it to the stage */
        Group group = new Group();
        group.setPosition(stage.getWidth() - 220, 20);
        group.addActor(clockImage);
        clockLabel.setPosition(58, 95);
        clockLabel.setFontScale((float)2.1);
        dateLabel.setPosition(65, 60);
        dateLabel.setFontScale((float)0.9);
        group.addActor(clockLabel);
        group.addActor(dateLabel);
        stage.addActor(group);

        /* Create the window for plant. */
        plantInfo = new Label("null",skin);
        plantManager.setPlantLabel(plantInfo);
        plantManager.setPlantWindow(plantWindow);
        plantWindow.add(plantInfo);
        plantManager.updateLabel();
        plantWindow.pack();
        plantWindow.setMovable(false);
        plantWindow.setPosition(stage.getWidth(), stage.getHeight());
        stage.addActor(plantWindow);

        /* Create window for chat and all components */
		chatWindow = new Table(skin);
		chatWindow.setPosition(0, 0);
		chatWindow.setSize(350,250);
		chatLabel = new Label("Say: ", skin);
		chatLabel.setColor(new Color().GRAY);
        chatTextArea = new TextArea("", skin);
        chatTextField = new TextField("", skin);
        chatTextArea.setDisabled(true);
        chatTextArea.setText("");
        chatButton = new TextButton("Send", skin);
        chatWindow.add(chatTextArea).expand().fill().height(210).colspan(3);
        chatWindow.row().height(40);
        chatWindow.add(chatLabel).left().prefWidth(10);
        chatWindow.add(chatTextField).prefWidth(350);
        chatWindow.add(chatButton);
        chatWindow.setDebug(false);//display lines for debugging


        stage.addActor(chatWindow);

		/*
		 * Setup inputs for the buttons and the game itself
		 */
        
        chatButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				if (NetworkState.isInitialised()) {
					String chatMessage = NetworkState.sendChatMessage(chatTextField.getText());
					chatTextField.setText("");
					chatTextArea.appendText(chatMessage + "\n");
					stage.setKeyboardFocus(null);
				}
			}
		});
        
        messageManager.addChatMessageListener(this::handleChatMessage);
        
		/*
		 * Setup an Input Multiplexer so that input can be handled by both the UI and
		 * the game
		 */
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(stage); // Add the UI as a processor
		InputManager input = (InputManager) GameManager.get().getManager(InputManager.class);
		inputMultiplexer.addProcessor(input);

        input.addKeyDownListener(this::handleKeyDown);

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
		
		/* This won't stay here forever - loading and compiling shaders here is super inefficienct so we should
		 * do that somewhere else but for testing purposes this is fine. */
        FileHandle vertexShader = Gdx.files.internal("resources/shaders/vertex.glsl");
        FileHandle postVertexShader = Gdx.files.internal("resources/shaders/vertex_post.glsl");
        FileHandle fragmentShader = Gdx.files.internal("resources/shaders/fragment_default.glsl");
        FileHandle heatFragShader = Gdx.files.internal("resources/shaders/fragment_heat.glsl");
        shader = new ShaderProgram(vertexShader, fragmentShader);
        postShader = new ShaderProgram(postVertexShader, heatFragShader);
        
        if(!shader.isCompiled()) {
            System.out.println("Shader failed to compile.");
            shader = null;
        }
        
        if(!postShader.isCompiled()) {
            System.out.println("Post shader failed to compile");
            postShader = null;
        }
	}
	
	
	private void handleChatMessage(Message message) {
		chatTextArea.appendText(message.getPayloadString() + "\n");
	}

	/**
	 * Renderer thread Must update all displayed elements using a Renderer
	 */
	@Override
	public void render(float delta) {

		/*
		 * Create a new render batch. At this stage we only want one but perhaps we need
		 * more for HUDs etc
		 */
	    
	    GameManager.get().getCamera().update();
	    
	    if(shader == null || postShader == null) {
	        SpriteBatch batch = new SpriteBatch();
	        
	        batch.setProjectionMatrix(GameManager.get().getCamera().combined);
	        BatchTiledMapRenderer tileRenderer = renderer.getTileRenderer(batch);
	        
	        tileRenderer.setView(GameManager.get().getCamera());
	        tileRenderer.render();
	        
	        renderer.render(batch);
	        
	        batch.dispose();
	    } else {
	        ShaderState state = new ShaderState(new Color(1, 1, 1, 1), new Color(0.3F, 0.3F, 0.8F, 1));
            state.setTime(timeManager);
            
            // Uncomment this line to enable the crappy heat distortion
            //state.postEffects |= ShaderState.POST_HEAT;
            
            Color color = state.getGlobalLightColour();
            int width = Gdx.graphics.getWidth();
            int height = Gdx.graphics.getHeight();
            FrameBuffer renderTarget = new FrameBuffer(Format.RGB565, width, height, false);
            TextureRegion scene = new TextureRegion(renderTarget.getColorBufferTexture());
            scene.flip(false, true);
            
            // Begin processing ////////////////////////////////////////////////////////////////////////////////////////
            shader.begin();
            
            shader.setUniformf("u_globalColor", color);
            
            SpriteBatch preBatch = new SpriteBatch(1000, shader);
            preBatch.setProjectionMatrix(GameManager.get().getCamera().combined);
            
            BatchTiledMapRenderer tileRenderer = renderer.getTileRenderer(preBatch);
            tileRenderer.setView(GameManager.get().getCamera());
            
            // Draw onto render target ////////////////////////////////////
            renderTarget.begin();
            
            tileRenderer.render();
            renderer.render(preBatch);
            
            renderTarget.end();
            // Finish render target ///////////////////////////////////////
            
            shader.end();
            preBatch.dispose();
            
            // Begin post-processing ///////////////////////////////////////////////////////////////////////////////////
            postShader.begin();
            
            postShader.setUniformf("u_time", (float)(Math.PI * timeManager.getSeconds() / 60.0F));
            postShader.setUniformi("u_effects", state.postEffects);
            
            SpriteBatch postBatch = new SpriteBatch(1, postShader);
            
            postBatch.begin();
            
            postBatch.draw(scene, 0, 0, width, height);
            
            postBatch.end();
            
            postShader.end();
            postBatch.dispose();
            renderTarget.dispose();
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
		window.setPosition(0, stage.getHeight());
	}

	/**
	 * Disposes of assets etc when the rendering system is stopped.
	 */
	@Override
	public void dispose() {
	    if(shader != null) {
	        shader.dispose();
	        postShader.dispose();
	    }
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
		if (!NetworkState.isInitialised()) {
			unpaused = false;
		}
	}

	@Override
	public void resume() {
		if (!NetworkState.isInitialised()) {
			unpaused = true;
		}
	}

	@Override
	public void onTick(long gameTickCount) {
		// Do nothing
	}

    @Override
    public boolean ticksRunning() {
        return unpaused;
    }

    // Handle switching to World Map by pressing "m"
    private void handleKeyDown(int keycode) {
        if (keycode == Input.Keys.M) {
            contextManager.pushContext(new WorldMapContext());
        }
    }
}
