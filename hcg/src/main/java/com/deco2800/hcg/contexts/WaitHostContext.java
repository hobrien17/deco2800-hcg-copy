package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.TextureManager;


public class WaitHostContext extends UIContext{
    private Table main;
    private TextButton cancel;
    private Label waitHost;

    public WaitHostContext(int toggle) {

        GameManager gameManager = GameManager.get();
        ContextManager contextManager = (ContextManager) gameManager.getManager(ContextManager.class);
        TextureManager textureManager = (TextureManager) gameManager.getManager(TextureManager.class);
        Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));

        main = new Table();
        main.setFillParent(true);
        main.setBackground(new Image(textureManager.getTexture("multi_menu_background")).getDrawable());
        main.setDebug(false); //display lines for debugging
        cancel = new TextButton("cancel", skin);
        waitHost = new Label("", skin);
        if (toggle == 1) {
            waitHost.setText("Waiting for other players...");
        } else if (toggle == 0) {
            waitHost.setText("Waiting for Host...");
        }
        waitHost.setSize(100, 50);

        main.row();
        main.add(waitHost).expand().center();
        main.row();
        main.add(cancel).center();
        stage.addActor(main);

        cancel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                contextManager.popContext();
            }
        });
    }
}
