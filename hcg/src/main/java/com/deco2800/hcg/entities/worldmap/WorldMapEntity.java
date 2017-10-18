package com.deco2800.hcg.entities.worldmap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.TextureManager;

/**
 * WorldMap entity creates the actor which renders the background image of the WorldMap screen. Also contains the draw
 * method to render the bg image in the WorldMap context.
 *
 * @author jakedunn
 */
public class WorldMapEntity extends Actor {

    private Texture mapBackground;

	/**
	 * Constructor loads the correct texture from the TextureManager
	 */
	public WorldMapEntity() {
        GameManager gameManager = GameManager.get();
        TextureManager textureManager = (TextureManager) gameManager.getManager(TextureManager.class);
        mapBackground = textureManager.getTexture("wm_green_bg");
    }

    @Override
    public void draw(Batch batch, float alpha){
    	// Grab the current viewport dimensions
    	int viewPortX = Gdx.graphics.getWidth();
        int viewPortY = Gdx.graphics.getHeight();
        batch.draw(mapBackground, 0, 0, viewPortX, viewPortY);
    }
}
