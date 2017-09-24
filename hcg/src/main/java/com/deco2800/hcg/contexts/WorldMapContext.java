package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.hcg.entities.worldmap.MapNode;
import com.deco2800.hcg.entities.worldmap.MapNodeEntity;
import com.deco2800.hcg.entities.worldmap.WorldMap;
import com.deco2800.hcg.entities.worldmap.WorldMapEntity;
import com.deco2800.hcg.managers.*;
import com.deco2800.hcg.worlds.World;
import java.util.ArrayList;

/**
 * Provides the World Map Context, which controls building and rendering of the
 * WorldMap screen.
 *
 * Some testing buttons have been included in a UI frame, these are:
 *
 * quitButton: Pops the current context from the context manager stack
 * startButton: Pushes a new DemoWorld level to the context manager stack, and
 * places the player in that level discoveredButton: Toggles displaying all
 * nodes in the map, and only those discovered.
 *
 * Currently there is no method to discover new nodes, as nodes will only be
 * discovered when a level is completed, and there is not yet a way to complete
 * a level in the game.
 *
 * Drawing edges between the nodes is still a WIP, but the MapNode class keeps
 * track of the edges which exist between nodes.
 *
 * @author jakedunn
 */
public class WorldMapContext extends UIContext {

	// Managers used by the game
	private GameManager gameManager;
	private PlayerManager playerManager;
	private ContextManager contextManager;

	private InputMultiplexer inputMultiplexer;

	// Lists of the nodes in the map, the hidden nodes is there for demo purposes
	private ArrayList<MapNodeEntity> allNodes;
	private ArrayList<MapNodeEntity> hiddenNodes;

	private Window window;
	private Window exitWindow;
	private Stage menuStage;
	private Skin skin;

	private TextureRegion lineTexture;
	// used for demo purposes
	private boolean showAllNodes;


	/**
	 * Constructor to create a new WorldMapContext
	 */
	public WorldMapContext() {
		gameManager = GameManager.get();
		gameManager.setMapContext(this);
		
		TextureManager textureManager = (TextureManager) gameManager.getManager(TextureManager.class);
		lineTexture = new TextureRegion(textureManager.getTexture("black_px"));
		playerManager = (PlayerManager) gameManager
				.getManager(PlayerManager.class);
		contextManager = (ContextManager) gameManager
				.getManager(ContextManager.class);
		InputManager inputManager = new InputManager();

		showAllNodes = false;
		
		menuStage = new Stage();

		// Setup UI + Buttons
		skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));
		window = new Window("Menu", skin);

		Button quitButton = new TextButton("Quit", skin);
		Button discoveredButton = new TextButton("Show all nodes", skin);

		window.add(quitButton);
		window.add(discoveredButton);
		window.pack();
		window.setMovable(false); // So it doesn't fly around the screen
		window.setPosition(0, stage.getHeight());

		stage.addActor(new WorldMapEntity());
		
		createExitWindow();

		allNodes = new ArrayList<>();
		hiddenNodes = new ArrayList<>();
		
		for (MapNode node : gameManager.getWorldMap().getContainedNodes()) {
			MapNodeEntity nodeEntry = new MapNodeEntity(node);
			if (!node.isDiscovered()) {
				hiddenNodes.add(nodeEntry);
				nodeEntry.setVisible(false);
			}
			allNodes.add(nodeEntry);
		}
		
		menuStage.addActor(window);

		quitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				contextManager.popContext();
			}
		});

		discoveredButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				showAllNodes = !showAllNodes;

				for (MapNodeEntity node : hiddenNodes) {
					if (showAllNodes) {
						node.setVisible(true);
					} else if (!node.getNode().isDiscovered()) {
						node.setVisible(false);
					}
				}
			}
		});

		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(menuStage); // Add the user options as a processor
		inputMultiplexer.addProcessor(stage); // Add the UI as a processor
		inputMultiplexer.addProcessor(inputManager);

		inputManager.addTouchUpListener(this::handleTouchUp);
		//inputManager.addMouseMovedListener(this::handleMouseMoved);
	}

	/*
	// when hovering the node, change the mouse cursor, delete if not needed
	private void handleMouseMoved(int screenX, int screenY){

		Vector2 mouseScreen = new Vector2(screenX, screenY);
		Vector2 mouseStage = stage.screenToStageCoordinates(mouseScreen);
		for (MapNodeEntity nodeEntity : allNodes) {
			float nodeStartX = nodeEntity.getXPos();
			float nodeEndX = nodeEntity.getXPos() + nodeEntity.getWidth();
			float nodeStartY = nodeEntity.getYPos();
			float nodeEndY = nodeEntity.getYPos() + nodeEntity.getHeight();
			if (mouseStage.x >= nodeStartX && mouseStage.x <= nodeEndX
					&& mouseStage.y >= nodeStartY && mouseStage.y <= nodeEndY
					&& nodeEntity.getNode().isDiscovered()
					&& !(nodeEntity.getNode().getNodeType() == 2)) {

				// online free png https://dribbble.com/shots/815059-Basic-Cursor-PNG-Pack
				// for design team: create a 'cursor' png file with:
				//        a "power of 2" width px (256, 512,...)
				//        a "RGBA8888" format
				// otherwise, the code below will break

				Pixmap pixmap = new Pixmap(Gdx.files.internal("resources/cursor-hand.png"));
				Gdx.graphics.setCursor(Gdx.graphics.newCursor(pixmap, 0, 0));
				pixmap.dispose();
				//Gdx.graphics.setSystemCursor(SystemCursor.Hand);  // according to the library, this only works in LWJG3
			} else {
				// this line should set the current cursor back to normal. but I don't know how to do. will look into this
				// at the moment it's kind of automatically change back to normal when you no longer hovering

				//Gdx.graphics.setSystemCursor(SystemCursor.Arrow);  // according to the library, this only works in LWJG3

			}
		}
	}*/

	private void handleTouchUp(int screenX, int screenY, int pointer,
			int button) {
		Vector2 mouseScreen = new Vector2(screenX, screenY);
		Vector2 mouseStage = stage.screenToStageCoordinates(mouseScreen);

		for (MapNodeEntity nodeEntity : allNodes) {
			float nodeStartX = nodeEntity.getXPos();
			float nodeEndX = nodeEntity.getXPos() + nodeEntity.getWidth();
			float nodeStartY = nodeEntity.getYPos();
			float nodeEndY = nodeEntity.getYPos() + nodeEntity.getHeight();
			if (mouseStage.x >= nodeStartX && mouseStage.x <= nodeEndX
					&& mouseStage.y >= nodeStartY && mouseStage.y <= nodeEndY
					&& nodeEntity.getNode().isDiscovered()
					&& !(nodeEntity.getNode().getNodeType() == 2)) {
				/*
				 * Simply loading in the world file caused bugs with movement
				 * due to the same world being loaded multiple times. This seems
				 * to fix that problem.
				 */
				gameManager.setOccupiedNode(nodeEntity.getNode());

				// delete stopwatches
                ((StopwatchManager) GameManager.get().getManager(StopwatchManager.class)).deleteObservers();
                
                // create new world
				World newWorld = new World(nodeEntity.getNode()
                    .getNodeLinkedLevel().getWorld().getLoadedFile());
				
                // add the new weather effects
                ((WeatherManager) GameManager.get().getManager(WeatherManager.class)).
                  setWeather(newWorld.getWeatherType());
                
				gameManager.setWorld(newWorld);
				playerManager.spawnPlayers();
				contextManager.pushContext(new PlayContext());
			}
		}
	}

	void updateMapDisplay() {
		updateNodesDisplayed();
		stage.clear();
		stage.addActor(new WorldMapEntity());
		hiddenNodes.clear();
		for (MapNode node : gameManager.getWorldMap().getContainedNodes()) {
			MapNodeEntity nodeEntry = new MapNodeEntity(node);
			if (!node.isDiscovered()) {
				hiddenNodes.add(nodeEntry);
				nodeEntry.setVisible(false);
			}
		}
		menuStage.addActor(window);
	}

	private void updateNodesDisplayed() {
		for (MapNode node : gameManager.getWorldMap().getContainedNodes()) {
			if (node.getNodeType() != 2) {
				continue;
			}
			
			for (MapNode nodeProceeding : node.getProceedingNodes()) {
				nodeProceeding.discoverNode();
			}
		}
	}


	@Override
	public void show() {
		// Capture user input
		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	/**
	 * Adds a new line to be drawn to the rendering batch. The line is drawn between the two given (x, y) pairs
	 * @param batch the instance of the sprite batch to add the lines to.
	 * @param x1 x co-ordinate of the first point
	 * @param y1 y co-ordinate of the first point
	 * @param x2 x co-ordinate of the second point
	 * @param y2 y co-ordinate of the second point
	 */
	private void drawLine(Batch batch, int x1, int y1, int x2, int y2) {
		int dx = x2 - x1;
		int dy = y2 - y1;
		// Length of line segment between two points
		float length = (float)Math.sqrt((double)dx*dx + dy*dy);
		// Theta (rads)
		float rotation = (float) Math.asin(dy/length);
		float thickness = 4;
		// Convert to degrees
		rotation = rotation * 180/(float)Math.PI;
		batch.draw(lineTexture, x1, y1, 2, 2, length, thickness, 1, 1, rotation);
	}

	/**
	 * Adds a new pot to be drawn to the rendering batch.
	 * @param batch the sprite batch instance to group pots into
	 * @param node the node which needs to be drawn
	 */
	private void drawPot(SpriteBatch batch, MapNodeEntity node) {
		batch.draw(node.getNodeTexture(), node.getXPos(), node.getYPos(), node.getWidth(), node.getHeight());
	}

	/**
	 * Creates separate render batches for the lines and pots, in order to get the layering done correctly.
	 * @param delta the time step in between stage.act() calls.
	 */
	@Override
	public void render(float delta) {
		super.render(delta);
		Batch lineBatch = new SpriteBatch();
		SpriteBatch potBatch = new SpriteBatch();

		// Render all the lines first
		lineBatch.begin();
		for (MapNodeEntity nodeEntity : allNodes) {
			for (MapNode proceedingNode : nodeEntity.getNode().getProceedingNodes()) {
				if (nodeEntity.getNode().isDiscovered() && proceedingNode.isDiscovered() || showAllNodes) {
					drawLine(lineBatch, nodeEntity.getNode().getXPos(), nodeEntity.getNode().getYPos() - 10,
							proceedingNode.getXPos(), proceedingNode.getYPos() - 10);
				}
			}
		}
		lineBatch.end();

		// Render all the pots second, in order to ensure they are rendered on top of the lines.
		potBatch.begin();
		for (MapNodeEntity nodeEntity : allNodes) {
			if (nodeEntity.getNode().isDiscovered() || showAllNodes) {
				nodeEntity.updateTexture();
				drawPot(potBatch, nodeEntity);
			}
		}
		potBatch.end();

		// dispose of the batches to prevent memory leaks
		lineBatch.dispose();
		potBatch.dispose();
		menuStage.draw();
	}
	
	private void createExitWindow() {
    	exitWindow = new Window("Complete World?", skin);
    	Button yesButton = new TextButton("Yes", skin);
    	yesButton.pad(5, 10, 5, 10);
    	Button noButton = new TextButton("No", skin);
    	noButton.pad(5, 10, 5, 10);
    	
    	/* Add a programmatic listener to the buttons */
		yesButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				endWorld();
			}
		});

		noButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Button completeWorldButton = new TextButton("Complete World", skin);
				window.remove();
				window.add(completeWorldButton);
				window.pack();
				menuStage.addActor(window);
				exitWindow.remove();
				
				completeWorldButton.addListener(new ChangeListener() {
					@Override
					public void changed(ChangeEvent event, Actor actor) {
						endWorld();
					}
				});
			}
		});
    	exitWindow.add(yesButton);
    	exitWindow.add(noButton);
    	exitWindow.pack();
		exitWindow.setMovable(false); // So it doesn't fly around the screen
		exitWindow.setPosition(stage.getWidth() / 2, stage.getHeight() / 2);
    }
    
    public void addEndOfContext() {
    	if(exitWindow.getStage() == null) {
    		/* Add the window to the stage */
    		menuStage.addActor(exitWindow);
    	}
    }
    
    private void endWorld() {
    	for(WorldMap map : gameManager.getWorldStack().getWorldStack()) {
    		if(map.getWorldPosition() == gameManager.getWorldMap().getWorldPosition() + 1) {
    			map.setUnlocked();
    		}
    	}
    	gameManager.getWorldMap().toggleCompleted();
    	WorldStackContext context = gameManager.getStackContext();
    	context.updateWorldDisplay();
    	if(gameManager.getWorldMap().getWorldPosition() == 2) {
    		context.endOfGame();
    	}
    	contextManager.popContext();
    }
}