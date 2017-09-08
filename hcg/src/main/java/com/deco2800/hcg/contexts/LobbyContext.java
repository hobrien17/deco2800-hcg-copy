package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.managers.TextureManager;
import com.deco2800.hcg.multiplayer.NetworkState;
import com.deco2800.hcg.worlds.DemoWorld;

/**
 * UI for Multiplayer Lobby. Made by Leon Zheng from Team 9
 */
public class LobbyContext extends UIContext{

    private Image background;
    private ImageButton start, back, send;
    private Table main;
    private Container playerArea, chatArea;
    private Label lobbyNameLabel, playerLabel;
    private TextButton backTest;

    /**
     * Lobby UI constructor, initializes the entire UI
     */
    public LobbyContext() {

        GameManager gameManager = GameManager.get();
        ContextManager contextManager = (ContextManager)
                gameManager.getManager(ContextManager.class);
        PlayerManager playerManager = (PlayerManager)
                gameManager.getManager(PlayerManager.class);
        TextureManager textureManager = (TextureManager)
                gameManager.getManager(TextureManager.class);

        Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));

        main = new Table();
        main.setFillParent(true);
        main.setDebug(true); //display lines for debugging
        playerArea = new Container();
        chatArea = new Container();
        backTest = new TextButton("back", skin);

        lobbyNameLabel = new Label("Lobby Name: ", skin);

        playerLabel = new Label("Players", skin);

        main.add(lobbyNameLabel);
        main.row();
        main.add(playerLabel);
        main.row();
        main.add(backTest);

        stage.addActor(main);

        backTest.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                contextManager.popContext();
            }
        });
    }
}
