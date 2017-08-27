package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.TextureManager;

public class PerksSelectionScreen extends UIContext{

    private ImageButton quitButton1;
    private Table Mastertable;
    private Table Branch1;
    private Table Branch2;
    private Table Branch3;

    public PerksSelectionScreen() {

        // Get necessary managers
        GameManager gameManager = GameManager.get();
        ContextManager contextManager = (ContextManager)
                gameManager.getManager(ContextManager.class);
        TextureManager textureManager = (TextureManager)
                gameManager.getManager(TextureManager.class);

        Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));

        Mastertable = new Table();
        Branch1 = new Table(); //Branch1.setFillParent(true);
        Branch2 = new Table(); //Branch2.setFillParent(true);
        Branch3 = new Table(); //Branch3.setFillParent(true);

        Branch1.setBackground(new Image(textureManager.getTexture("red_tree_path")).getDrawable());
        Branch2.setBackground(new Image(textureManager.getTexture("green_tree_path")).getDrawable());
        Branch3.setBackground(new Image(textureManager.getTexture("purple_tree_path")).getDrawable());

        quitButton1 = new ImageButton(new Image(textureManager.getTexture("perk_place_holder")).getDrawable());

        for (int i = 0; i < 7; i++) {
            Branch1.add(quitButton1);
            Branch1.row();
        }
        for (int i = 0; i < 3; i++) {
            Branch2.add(quitButton1);
            Branch2.row();
        }
        for (int i = 0; i < 9; i++) {
            Branch3.add(quitButton1);
            Branch3.row();
        }

        Mastertable.add(Branch1);
        stage.addActor(Mastertable);
        quitButton1.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                contextManager.popContext();
            }
        });

    }

}
