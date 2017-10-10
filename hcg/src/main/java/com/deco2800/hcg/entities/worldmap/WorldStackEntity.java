package com.deco2800.hcg.entities.worldmap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.TextureManager;

/**
 * WorldStackEntity holds the WorldStack menu background.
 * 
 * @author Ivo
 */
public class WorldStackEntity extends Actor {
	private Texture worldStackBackground;
	
	public WorldStackEntity() {
        GameManager gameManager = GameManager.get();
        TextureManager textureManager = (TextureManager) gameManager.getManager(TextureManager.class);
        worldStackBackground = textureManager.getTexture("ws_purp_bg");
    }

    @Override
    public void draw(Batch batch, float alpha){
    	// Grab the current viewport dimensions
    	int viewPortX = Gdx.graphics.getWidth();
        int viewPortY = Gdx.graphics.getHeight();
        batch.draw(worldStackBackground, 0, 0, viewPortX, viewPortY);
    }
}
