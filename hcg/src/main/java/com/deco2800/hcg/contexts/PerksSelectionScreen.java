package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.TextureManager;

/**
 * The PerksSelectionScreen represents a context where users can
 * select their player's perks
 */
public class PerksSelectionScreen extends UIContext{

    private ImageButton quitButton1;
    private Table masterTable;
    private Table branch1;
    private Table branch2;
    private Table branch3;

    /**
     * Creates a new PerksSelectionScreen
     */
    public PerksSelectionScreen() {

        // Get necessary managers
        GameManager gameManager = GameManager.get();
        ContextManager contextManager = (ContextManager)
                gameManager.getManager(ContextManager.class);
        TextureManager textureManager = (TextureManager)
                gameManager.getManager(TextureManager.class);

        Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));

        masterTable = new Table(skin);
        masterTable.setFillParent(true);
        masterTable.setBackground("white");

        branch1 = new Table(skin);
        branch2 = new Table(skin);
        branch3 = new Table(skin);

        branch1.setBackground(new Image(textureManager.getTexture("red_tree_path")).getDrawable());
        branch2.setBackground(new Image(textureManager.getTexture("green_tree_path")).getDrawable());
        branch3.setBackground(new Image(textureManager.getTexture("purple_tree_path")).getDrawable());

        quitButton1 = new ImageButton(new Image(textureManager.getTexture("perk_place_holder")).getDrawable());

        masterTable.add(branch1);
        masterTable.add(branch2);
        masterTable.add(branch3);
        stage.addActor(masterTable);

        quitButton1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                contextManager.popContext();
            }
        });
    }

}
