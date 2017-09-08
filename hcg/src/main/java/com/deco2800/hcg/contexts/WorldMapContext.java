package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.hcg.entities.worldmap.MapNode;
import com.deco2800.hcg.entities.worldmap.MapNodeEntity;
import com.deco2800.hcg.entities.worldmap.WorldMapEntity;
import com.deco2800.hcg.handlers.MouseHandler;
import com.deco2800.hcg.managers.*;
import com.deco2800.hcg.worlds.AbstractWorld;

import java.util.ArrayList;

/**
 * Provides the World Map Context, which controls building and rendering of the WorldMap screen.
 *
 * Some testing buttons have been included in a UI frame, these are:
 *
 * quitButton: Pops the current context from the context manager stack
 * startButton: Pushes a new DemoWorld level to the context manager stack, and places the player in that level
 * discoveredButton: Toggles displaying all nodes in the map, and only those discovered.
 *
 * Currently there is no method to discover new nodes, as nodes will only be discovered when a level is completed, and
 * there is not yet a way to complete a level in the game.
 *
 * Drawing edges between the nodes is still a WIP, but the MapNode class keeps track of the edges which exist between
 * nodes.
 *
 * @author jakedunn
 */
public class WorldMapContext extends UIContext {

    // Managers used by the game
    private GameManager gameManager;
    private PlayerManager playerManager;
    private ContextManager contextManager;

    //TODO mouseHandler is never assigned
    private MouseHandler mouseHandler;

    private boolean showAllNodes;

    private ArrayList<MapNodeEntity> hiddenNodes;

    /**
     * Constructor to create a new WorldMapContext
     */
    public WorldMapContext() {
        gameManager = GameManager.get();

        // Not currently used, but might be later
        SoundManager soundManager = (SoundManager) gameManager.getManager(SoundManager.class);
        playerManager = (PlayerManager) gameManager.getManager(PlayerManager.class);
        contextManager = (ContextManager) gameManager.getManager(ContextManager.class);

        showAllNodes = false;

        // Setup UI + Buttons
        Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));
        Window window = new Window("Menu", skin);

        Button quitButton = new TextButton("Quit", skin);
        Button startButton = new TextButton("Start Level 1", skin);
        Button discoveredButton = new TextButton("Show all nodes", skin);

        window.add(quitButton);
        window.add(startButton);
        window.add(discoveredButton);
        window.pack();
        window.setMovable(false); // So it doesn't fly around the screen
        window.setPosition(0, stage.getHeight());

        stage.addActor(new WorldMapEntity());

        hiddenNodes = new ArrayList<>();

        for (MapNode node : gameManager.getWorldMap().getContainedNodes()) {
            MapNodeEntity nodeEntry = new MapNodeEntity(node);
            if (!node.isDiscovered()) {
                hiddenNodes.add(nodeEntry);
                nodeEntry.setVisible(false);
            }
            stage.addActor(nodeEntry);
        }

        stage.addActor(window);

        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                contextManager.popContext();
            }
        });

        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameManager.setWorld(new AbstractWorld("resources/maps/initial-map-test.tmx"));
                playerManager.spawnPlayers();
                contextManager.pushContext(new PlayContext());
            }
        });

        discoveredButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showAllNodes = !showAllNodes;

                for (MapNodeEntity node:hiddenNodes) {
                    if (showAllNodes) {
                        node.setVisible(true);
                    } else {
                        if (!node.getNode().isDiscovered()) {
                            node.setVisible(false);
                        }
                    }
                }
            }
        });
    }
}