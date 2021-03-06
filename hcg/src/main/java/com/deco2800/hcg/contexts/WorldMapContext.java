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
import com.deco2800.hcg.entities.worldmap.WorldStackBlackoutEntity;
import com.deco2800.hcg.entities.worldmap.PlayerMapEntity;
import com.deco2800.hcg.managers.*;
import com.deco2800.hcg.multiplayer.LevelStartMessage;
import java.util.ArrayList;

/**
 * Provides the World Map Context, which controls building and rendering of the
 * WorldMap screen.
 *
 * Some testing buttons have been included in a UI frame, these are:
 * quitButton: Pops the current context from the context manager stack
 * discoveredButton: Toggles displaying all nodes in the map, and only those discovered.
 * 
 * @author jakedunn
 */
public class WorldMapContext extends UIContext {

	// Managers used by the game
	private GameManager gameManager;
	private ContextManager contextManager;
	private WorldManager worldManager;
	private NetworkManager networkManager;

	private InputMultiplexer inputMultiplexer;

	// Lists of the nodes in the map, the hidden nodes is there for demo purposes
	private ArrayList<MapNodeEntity> allNodes;
	private ArrayList<MapNodeEntity> hiddenNodes;

	private Window window;
	private Window exitWindow;
	private Stage menuStage;
	private Skin skin;

	private TextureRegion lineTexture;
	private PlayerMapEntity playerMapEntity;
	
	private WorldMap currentWorld;


	/**
	 * Constructor to create a new WorldMapContext
	 */
	public WorldMapContext(WorldMap worldMap) {
		gameManager = GameManager.get();
		gameManager.setMapContext(this);
		
		TextureManager textureManager = (TextureManager) gameManager.getManager(TextureManager.class);
		lineTexture = new TextureRegion(textureManager.getTexture("black_px"));
		contextManager = (ContextManager) gameManager
				.getManager(ContextManager.class);
		worldManager = (WorldManager) gameManager
				.getManager(WorldManager.class);
		networkManager = (NetworkManager) gameManager
				.getManager(NetworkManager.class);
		InputManager inputManager = new InputManager();
		
		currentWorld = worldMap;
		
		menuStage = new Stage();

		// Setup UI + Buttons
		skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));
		window = new Window("Menu", skin);

		Button quitButton = new TextButton("Quit", skin);

		window.add(quitButton);
		window.pack();
		window.setMovable(false); // So it doesn't fly around the screen
		window.setPosition(0, stage.getHeight());

		stage.addActor(new WorldMapEntity(currentWorld.getWorldType()));
		
		createExitWindow();

		allNodes = new ArrayList<>();
		hiddenNodes = new ArrayList<>();
		
		for (MapNode node : gameManager.getWorldMap().getContainedNodes()) {
			MapNodeEntity nodeEntry = new MapNodeEntity(node, worldMap);
			if (!node.isDiscovered()) {
				hiddenNodes.add(nodeEntry);
				nodeEntry.setVisible(false);
			}
			allNodes.add(nodeEntry);
		}

		playerMapEntity = new PlayerMapEntity();
		// set the player Map Entity render position to be at the starting node
		MapNodeEntity entryMapNode = new MapNodeEntity(gameManager.getWorldMap().getContainedNodes().get(0), worldMap);
		playerMapEntity.updatePosByNodeEntity(entryMapNode);

		menuStage.addActor(window);

		quitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				contextManager.popContext();
			}
		});

		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(menuStage); // Add the user options as a processor
		inputMultiplexer.addProcessor(stage); // Add the UI as a processor

		inputManager.addTouchUpListener(this::handleTouchUp);
		
		if(!gameManager.getTutorialWorldMessageDisplayed() && currentWorld.getWorldType() == 1) {
			createTutorialTip(inputManager);
		} else {
			inputMultiplexer.addProcessor(inputManager);
		}
	}
	
	private void createTutorialTip(InputManager inputManager) {
		menuStage.addActor(new WorldStackBlackoutEntity());
		Window okWindow = new Window("Use WASD to move and E to interact with NPCs", skin);
    	Button okButton = new TextButton("OK", skin);
    	okButton.pad(5, 10, 5, 10);
  
    	/* Add a programmatic listener to the buttons */
		okButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				okWindow.remove();
				inputMultiplexer.addProcessor(inputManager);
				updateMapDisplay(currentWorld);
			}
		});		
    	okWindow.add(okButton);
    	okWindow.pack();
		okWindow.setMovable(false); // So it doesn't fly around the screen
		okWindow.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		menuStage.addActor(okWindow);
		gameManager.setTutorialWorldMessageDisplayed();
	}
	
	/**
	 * Handles the mouse click up on a node entity. If the node is a clickable entity (not completed or hidden), the
	 * player is taken into that node's level.
	 * @param screenX
	 *     The X position of the cursor on the screen.
	 * @param screenY
	 *     The Y position of the cursor on the screen.
	 * @param pointer
	 *     Not used.
	 * @param button
	 *     Not used.
	 */
	private void handleTouchUp(int screenX, int screenY, int pointer,
			int button) {
		Vector2 mouseScreen = new Vector2(screenX, screenY);
		Vector2 mouseStage = stage.screenToStageCoordinates(mouseScreen);

		for (int i = 0; i < allNodes.size(); i++) {
			MapNodeEntity nodeEntity = allNodes.get(i);
			float nodeStartX = nodeEntity.getXPos();
			float nodeEndX = nodeEntity.getXPos() + nodeEntity.getWidth();
			float nodeStartY = nodeEntity.getYPos();
			float nodeEndY = nodeEntity.getYPos() + nodeEntity.getHeight();
			if (mouseStage.x >= nodeStartX && mouseStage.x <= nodeEndX
					&& mouseStage.y >= nodeStartY && mouseStage.y <= nodeEndY
					&& nodeEntity.getNode().isDiscovered()
					&& !(nodeEntity.getNode().getNodeType() == 2)) {
                // set the PlayerMapEntity position
				playerMapEntity.updatePosByNodeEntity(nodeEntity);
				// send to peers
				if (networkManager.isMultiplayerGame()) {
					networkManager.queueMessage(new LevelStartMessage(i));
				}
				// select the current node
				worldManager.selectNode(i);
				return;
			}
		}
	}
	
	/**
	 * Updates the display of the nodes on the world map. Handles making hidden nodes not visible to the user.
	 */
	public void updateMapDisplay(WorldMap currentWorld) {
		updateNodesDisplayed();
		stage.clear();
		menuStage.clear();
		stage.addActor(new WorldMapEntity(currentWorld.getWorldType()));
		hiddenNodes.clear();
		for (MapNode node : gameManager.getWorldMap().getContainedNodes()) {
			MapNodeEntity nodeEntry = new MapNodeEntity(node, currentWorld);
			if (node.isDiscovered()) {
				continue;
			}
			
			hiddenNodes.add(nodeEntry);
			nodeEntry.setVisible(false);
		}
		menuStage.addActor(window);
	}
	
	/**
	 * Discovers nodes which come after completed nodes.
	 */
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

	private void drawPlayer(SpriteBatch batch, PlayerMapEntity playerEntity) {
		batch.draw(playerEntity.getPlayerTexture(), playerEntity.getXPos(), playerEntity.getYPos(),
				playerEntity.getWidth(), playerEntity.getHeight());
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
		SpriteBatch playerBatch = new SpriteBatch();

		// Render all the lines first
		lineBatch.begin();
		for (MapNodeEntity nodeEntity : allNodes) {
			for (MapNode proceedingNode : nodeEntity.getNode().getProceedingNodes()) {
				if (nodeEntity.getNode().isDiscovered() && proceedingNode.isDiscovered()) {
					drawLine(lineBatch, nodeEntity.getNode().getXPos(), nodeEntity.getNode().getYPos() - 10,
							proceedingNode.getXPos(), proceedingNode.getYPos() - 10);
				}
			}
		}
		lineBatch.end();

		// Render all the pots second, in order to ensure they are rendered on top of the lines.
		potBatch.begin();
		for (MapNodeEntity nodeEntity : allNodes) {
			if (nodeEntity.getNode().isDiscovered()) {
				nodeEntity.updateTexture(currentWorld);
				drawPot(potBatch, nodeEntity);
			}
		}
		potBatch.end();

		playerBatch.begin();
		drawPlayer(playerBatch, playerMapEntity);
		playerBatch.end();

		// dispose of the batches to prevent memory leaks
		lineBatch.dispose();
		potBatch.dispose();
		playerBatch.dispose();
		menuStage.draw();
	}
	
	/**
	 * Adds the "Complete World?" pop-up to the game.
	 */
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
    
	/**
	 * Adds the "Complete World?" pop-up to the stage.
	 */
    public void addEndOfContext() {
    	if(exitWindow.getStage() == null) {
    		/* Add the window to the stage */
    		menuStage.addActor(exitWindow);
    	}
    }
    
    /**
     * Ends the current instance of the world and moves back to the WorldStackContext.
     */
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