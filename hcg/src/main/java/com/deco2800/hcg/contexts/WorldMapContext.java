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
import com.deco2800.hcg.renderers.Render2D;
import com.deco2800.hcg.renderers.Renderer;

import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.Tickable;
import com.deco2800.hcg.worlds.DemoWorld;

/**
 * Provides implementation of the World Map Context, which instansiates a
 * constructor for UIContext.
 *
 * @author jakedunn
 */
public class WorldMapContext extends UIContext {

    // Managers used by the game
    private GameManager gameManager;

    private SoundManager soundManager;
    private PlayerManager playerManager;
    private TimeManager timeManager;
    private ContextManager contextManager;

    private Window window;
    //FIXME mouseHandler is never assigned
    private MouseHandler mouseHandler;

    // Multiplexer to take input and distrubute it
    InputMultiplexer inputMultiplexer;

    // Is the game paused?
    private boolean unpaused = true;

    private Renderer renderer = new Render2D();

    public WorldMapContext() {
        gameManager = GameManager.get();
        soundManager = (SoundManager) gameManager
                .getManager(SoundManager.class);
        timeManager = (TimeManager) gameManager.getManager(TimeManager.class);
        playerManager = (PlayerManager) gameManager
                .getManager(PlayerManager.class);
        contextManager = (ContextManager) gameManager
                .getManager(ContextManager.class);

        // Setup GUI
        Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));
        window = new Window("Menu", skin);

		/* Add a quit button to the menu */
        Button quitButton = new TextButton("Quit", skin);

		/* Add another button to the menu */
        Button startButton = new TextButton("Start Level 1", skin);

        window.add(quitButton);
        window.add(startButton);
        window.pack();
        window.setMovable(false); // So it doesn't fly around the screen
        window.setPosition(0,
                stage.getHeight());
        stage.addActor(window);

        /* Add a programmatic listener to the quit button */
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                contextManager.popContext();
            }
        });

        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                setUpLevel();
                contextManager.pushContext(new PlayContext());
            }
        });


    }

    public void setUpLevel() {
        gameManager.setWorld(new DemoWorld());
        // Set up a player
        Player player = new Player(5, 10, 0);
        player.initialiseNewPlayer(5, 5, 5, 5, 5, 20);
        playerManager.setPlayer(player);
        gameManager.getWorld().addEntity(playerManager.getPlayer());
        contextManager.pushContext(new PlayContext());
    }
}