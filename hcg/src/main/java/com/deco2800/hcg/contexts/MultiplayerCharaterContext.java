package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.NetworkManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.managers.TextureManager;

public class MultiplayerCharaterContext extends UIContext{
    private Table main;

    public MultiplayerCharaterContext() {
        GameManager gameManager = GameManager.get();
        ContextManager contextManager = (ContextManager) gameManager.getManager(ContextManager.class);
        NetworkManager networkManager = (NetworkManager) gameManager.getManager(NetworkManager.class);
        PlayerManager playerManager = (PlayerManager) gameManager.getManager(PlayerManager.class);
        TextureManager textureManager = (TextureManager) gameManager.getManager(TextureManager.class);
        Skin skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));
    }

}
