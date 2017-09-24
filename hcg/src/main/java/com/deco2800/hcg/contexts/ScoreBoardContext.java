package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.hcg.managers.*;
import java.util.*;
import java.util.List;

/**
 * Score board UI for 1...n players
 *
 * @author Ethan Phan, Duc Thuan Chu
 */
public class ScoreBoardContext extends UIContext {

    protected Table masterTable;
    protected Table topRowInfoTable;

    List<Window> playerWindows;
    protected Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));
    TextButton backButton;


    protected GameManager gameManager;
    protected ContextManager contextManager;
    protected TextureManager textureManager;
    protected PlayerManager playerManager;

    public ScoreBoardContext() {
        // Get necessary managers
        gameManager = GameManager.get();
        contextManager = (ContextManager) gameManager.getManager(ContextManager.class);
        textureManager = (TextureManager) gameManager.getManager(TextureManager.class);
        playerManager = (PlayerManager) gameManager.getManager(PlayerManager.class);

        masterTable = new Table(skin);
        masterTable.setFillParent(true);
        masterTable.setBackground(new Image(textureManager.getTexture("main_menu_background")).getDrawable());
        stage.addActor(masterTable);
//        System.out.println(playerManager.getPlayers().size());
        playerWindows = new ArrayList<Window>();

        display_top_row_info();
        display_player_windows();
    }

    public void display_top_row_info() {
        topRowInfoTable = new Table(skin);
        backButton = new TextButton("Back", skin);
        topRowInfoTable.add(backButton).left().expandX();
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                contextManager.popContext();
            }
        });
        masterTable.add(topRowInfoTable).top().left().expandX().fillX().colspan(2).padBottom(15);
        masterTable.row();
    }

    public void display_player_windows() {
        int numPlayers = playerManager.getPlayers().size();
        for (int i = 0; i < numPlayers; i++) {
            playerWindows.add(new Window(playerManager.getPlayers().get(i).toString(), skin));
            Texture backgroundTexture = textureManager.getTexture("ccWindow_Border_White");
            playerWindows.get(i).setBackground(new Image(backgroundTexture).getDrawable());
            masterTable.add(playerWindows.get(i)).top().left().expandX().expandY().fillX().fillY().padBottom(15);
        }
    }



}
