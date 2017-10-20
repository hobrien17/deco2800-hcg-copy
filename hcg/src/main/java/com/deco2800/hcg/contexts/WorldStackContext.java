package com.deco2800.hcg.contexts;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.hcg.entities.worldmap.WorldMap;
import com.deco2800.hcg.entities.worldmap.WorldStackEntity;
import com.deco2800.hcg.entities.worldmap.WorldStackMapEntity;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.InputManager;
import com.deco2800.hcg.managers.NetworkManager;
import com.deco2800.hcg.managers.TextureManager;
import com.deco2800.hcg.managers.WorldManager;
import com.deco2800.hcg.multiplayer.WorldMapMessage;

/**
 * Holds the WorldStackContext information. Used to navigate and view the WorldStack when the player is in the game.
 * 
 * @author Ivo
 */
public class WorldStackContext extends UIContext {
	// Managers used by the game
	private GameManager gameManager;
	private ContextManager contextManager;
	private TextureManager textureManager;
	private WorldManager worldManager;
	private NetworkManager networkManager;

	private InputMultiplexer inputMultiplexer;

	private ArrayList<WorldStackMapEntity> allWorldMaps;
	private ArrayList<WorldStackMapEntity> hiddenWorldMaps;

	private Window window;
	
	Skin skin;

	/**
	 * Constructor to create a new WorldStackContext
	 */
	public WorldStackContext() {
		gameManager = GameManager.get();
		
		gameManager.setStackContext(this);

		textureManager = (TextureManager) gameManager.getManager(TextureManager.class);
		contextManager = (ContextManager) gameManager
				.getManager(ContextManager.class);
		worldManager = (WorldManager) gameManager.getManager(WorldManager.class);
		networkManager = (NetworkManager) gameManager.getManager(NetworkManager.class);
		InputManager inputManager = new InputManager();

		// Setup UI + Buttons
		skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));
		window = new Window("Menu", skin);

		Button quitButton = new TextButton("Quit", skin);

		window.add(quitButton);
		window.pack();
		window.setMovable(false); // So it doesn't fly around the screen
		window.setPosition(0, stage.getHeight());

		allWorldMaps = new ArrayList<>();
		hiddenWorldMaps = new ArrayList<>();

		for (WorldMap map : gameManager.getWorldStack().getWorldStack()) {
			WorldStackMapEntity worldEntry = new WorldStackMapEntity(map);
			if (map.getWorldPosition() == 0) {
				map.setUnlocked();
			} else {
				hiddenWorldMaps.add(worldEntry);
			}
			allWorldMaps.add(worldEntry);
		}
		
		updateWorldDisplay();

		quitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				contextManager.popContext();
			}
		});

		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(stage); // Add the UI as a processor
		inputMultiplexer.addProcessor(inputManager);

		inputManager.addTouchUpListener(this::handleTouchUp);
	}
	
	/**
	 * Handles a mouse click up on the stage. If the mouse is over an unlocked WorldMap, the user is brought into this
	 * WorldMap.
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

		for (int i = 0; i < allWorldMaps.size(); i++) {
			WorldStackMapEntity worldEntity = allWorldMaps.get(i);
			float worldStartX = worldEntity.getXPos();
			float worldEndX = worldEntity.getXPos() + worldEntity.getWidth();
			float worldStartY = worldEntity.getYPos();
			float worldEndY = worldEntity.getYPos() + worldEntity.getHeight();
			if (mouseStage.x >= worldStartX && mouseStage.x <= worldEndX
					&& mouseStage.y >= worldStartY && mouseStage.y <= worldEndY
					&& worldEntity.getWorldMap().isUnlocked()
					&& !(worldEntity.getWorldMap().isCompleted())) {
				// send to peers
				if (networkManager.isMultiplayerGame()) {
					networkManager.queueMessage(new WorldMapMessage(i));
				}
				/*
				 * Simply loading in the world file caused bugs with movement
				 * due to the same world being loaded multiple times. This seems
				 * to fix that problem.
				 */
				worldManager.setWorldMap(i);
				contextManager.pushContext(new WorldMapContext(worldEntity.getWorldMap()));
			}
		}
	}

	/**
	 * Updates the display of the WorldMap nodes on the world stack. Handles making locked worlds unlocked and
	 * accessible to the user.
	 */
	public void updateWorldDisplay() {
		updateUnlockedWorlds();
		stage.clear();
		stage.addActor(new WorldStackEntity());
		for(WorldStackMapEntity worldEntry : allWorldMaps) {
			if(worldEntry.getWorldMap().isUnlocked()) {
				worldEntry.updateTexture();
				if(worldEntry.getWorldMap().isCompleted()) {
					if (worldEntry.getWorldMap().getWorldType() == 1) {
						worldEntry.setWorldTexture(
								textureManager.getTexture("ws_urban_completed"));
					} else if (worldEntry.getWorldMap().getWorldType() == 2) {
						worldEntry.setWorldTexture(
								textureManager.getTexture("ws_forest_completed"));
					} else {
						worldEntry.setWorldTexture(
								textureManager.getTexture("ws_fungi_completed"));
					}
				}
			} else if (worldEntry.getWorldMap().getWorldType() == 2) {
				worldEntry.setWorldTexture(
						textureManager.getTexture("ws_forest_locked"));
			} else {
				worldEntry.setWorldTexture(
						textureManager.getTexture("ws_fungi_locked"));
			}

			stage.addActor(worldEntry);
		}
		stage.addActor(window);
	}

	/**
	 * Updates the lock status of the WorldMap node objects in the WorldStack.
	 */
	private void updateUnlockedWorlds() {
		for (WorldMap world : gameManager.getWorldStack().getWorldStack()) {
			if (!world.isCompleted()) {
				continue;
			}
			
			for (WorldMap otherWorld : gameManager.getWorldStack()
					.getWorldStack()) {
				if (otherWorld.getWorldPosition() == (world.getWorldPosition() + 1)
						&& !otherWorld.isUnlocked()) {
					otherWorld.setUnlocked();
					hiddenWorldMaps.remove(otherWorld);
				}
			}

		}
	}

	/**
	 * Adds a win message when the game is won by the player.
	 */
	public void endOfGame() {
		Window youWin = new Window("You win!", skin);
		Button okButton = new TextButton("OK", skin);
		okButton.pad(5, 10, 5, 10);
		
		okButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				youWin.remove();
			}
		});
		
		youWin.add(okButton);
		youWin.pack();
		youWin.setMovable(false); // So it doesn't fly around the screen
		youWin.setPosition(stage.getWidth() / 2, stage.getHeight() / 2);
		
		stage.addActor(youWin);
	}

	@Override
	public void show() {
		// Capture user input
		Gdx.input.setInputProcessor(inputMultiplexer);
	}
}
