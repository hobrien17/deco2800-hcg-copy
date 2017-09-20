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
import com.deco2800.hcg.managers.StackInputManager;
import com.deco2800.hcg.managers.TextureManager;

public class WorldStackContext extends UIContext {
	// Managers used by the game
	private GameManager gameManager;
	private ContextManager contextManager;
	private TextureManager textureManager;

	private InputMultiplexer inputMultiplexer;

	private ArrayList<WorldStackMapEntity> allWorldMaps;
	private ArrayList<WorldStackMapEntity> hiddenWorldMaps;

	private Window window;

	/**
	 * Constructor to create a new WorldStackContext
	 */
	public WorldStackContext() {
		gameManager = GameManager.get();
		
		gameManager.setStackContext(this);

		textureManager = (TextureManager) gameManager.getManager(TextureManager.class);
		contextManager = (ContextManager) gameManager
				.getManager(ContextManager.class);
		StackInputManager inputManager = new StackInputManager();

		// Setup UI + Buttons
		Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));
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

	private void handleTouchUp(int screenX, int screenY, int pointer,
			int button) {
		Vector2 mouseScreen = new Vector2(screenX, screenY);
		Vector2 mouseStage = stage.screenToStageCoordinates(mouseScreen);

		for (WorldStackMapEntity worldEntity : allWorldMaps) {
			float worldStartX = worldEntity.getXPos();
			float worldEndX = worldEntity.getXPos() + worldEntity.getWidth();
			float worldStartY = worldEntity.getYPos();
			float worldEndY = worldEntity.getYPos() + worldEntity.getHeight();
			if (mouseStage.x >= worldStartX && mouseStage.x <= worldEndX
					&& mouseStage.y >= worldStartY && mouseStage.y <= worldEndY
					&& worldEntity.getWorldMap().isUnlocked()
					&& !(worldEntity.getWorldMap().isCompleted())) {
				/*
				 * Simply loading in the world file caused bugs with movement
				 * due to the same world being loaded multiple times. This seems
				 * to fix that problem.
				 */
				gameManager.setWorldMap(worldEntity.getWorldMap());
				contextManager.pushContext(new WorldMapContext());
			}
		}
	}

	public void updateWorldDisplay() {
		updateUnlockedWorlds();
		stage.clear();
		stage.addActor(new WorldStackEntity());
		for(WorldStackMapEntity worldEntry: allWorldMaps) {
			if(worldEntry.getWorldMap().isUnlocked()) {
				worldEntry.updateTexture();
				if(worldEntry.getWorldMap().isCompleted()) {
					worldEntry.setWorldTexture(textureManager.getTexture("completed_node"));
				}
			} else {
				worldEntry.setWorldTexture(textureManager.getTexture("fungi_node"));
			}
			stage.addActor(worldEntry);
		}
		stage.addActor(window);
	}

	private void updateUnlockedWorlds() {
		for (WorldMap world : gameManager.getWorldStack().getWorldStack()) {
			if (world.isCompleted()) {
				for(WorldMap otherWorld : gameManager.getWorldStack().getWorldStack()) {
					if(otherWorld.getWorldPosition() == (world.getWorldPosition() + 1) && !otherWorld.isUnlocked()) {
						otherWorld.setUnlocked();
						hiddenWorldMaps.remove(otherWorld);
					}
				}
			}
		}
	}


	@Override
	public void show() {
		// Capture user input
		Gdx.input.setInputProcessor(inputMultiplexer);
	}
}
