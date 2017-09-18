package com.deco2800.hcg.contexts;

import com.badlogic.gdx.Gdx;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;
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
    
    public DeathContext() {
        gameManager = GameManager.get();
        contextManager = (ContextManager) gameManager.getManager(ContextManager.class);
        textureManager = (TextureManager) gameManager.getManager(TextureManager.class);
    }

}
