package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.managers.TextureManager;

/**
 * DeathContext is shown when the character dies.
 *
 * @author thatsokay
 */
public class DeathContext extends UIContext {
    
    private GameManager gameManager;
    private ContextManager contextManager;
    private TextureManager textureManager;
    
    private Skin skin;
    private Table masterTable;
    private Texture title;
    private Texture deadman;
    private TextButton button;
    
    public DeathContext() {
        // Get managers
        gameManager = GameManager.get();
        contextManager = (ContextManager) gameManager.getManager(ContextManager.class);
        textureManager = (TextureManager) gameManager.getManager(TextureManager.class);

        // Initialise class attributes
        skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));
        
        masterTable = new Table(skin);
        
        title = textureManager.getTexture("you_died");
        deadman = textureManager.getTexture("deadman");
        
        button = new TextButton("Quit", skin);
        
        // Set up master table
        masterTable.setFillParent(true);
        masterTable.setBackground(new Image(textureManager.getTexture("main_menu_background")).getDrawable());
        
        // Arrange actors
        masterTable.add(new Image(title)).pad(20);
        masterTable.row();
        masterTable.add(new Image(deadman)).pad(20);
        masterTable.row();
        masterTable.add(button);
        stage.addActor(masterTable);
        
        // Add button listener
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                for (int i = 0; i < 2; i++) {
                    contextManager.popContext();
                }
            }
        });
    }

}
