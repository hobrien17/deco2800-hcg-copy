package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.managers.TextureManager;

/**
 * Abstract class to be extended by the character creation and character stats contexts as they share a lot of common
 * code
 *
 * @author avryn
 */
public abstract class CharacterContext extends UIContext {

    protected Table masterTable;

    protected Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));
    protected GameManager gameManager;
    protected ContextManager contextManager;
    protected TextureManager textureManager;
    protected PlayerManager playerManager;


    protected void getManagers() {
        gameManager = GameManager.get();
        contextManager = (ContextManager) gameManager.getManager(ContextManager.class);
        textureManager = (TextureManager) gameManager.getManager(TextureManager.class);
    }

    protected void initMasterTable() {
        masterTable = new Table(skin);
        masterTable.setFillParent(true);
        masterTable.setBackground(new Image(textureManager.getTexture("main_menu_background")).getDrawable());
        stage.addActor(masterTable);
    }

}
