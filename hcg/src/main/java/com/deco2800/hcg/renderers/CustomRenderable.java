package com.deco2800.hcg.renderers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deco2800.hcg.managers.TextureManager;

/**
 * A renderable object that requires more complex rendering than just drawing a
 * predefined sprite.
 * 
 * @author wcsti
 *
 */
public interface CustomRenderable extends Renderable {
    
    /**
     * Draw the object.
     * 
     * @param posX
     *            The x coordinate of the drawing location.
     * @param posY
     *            The y coordinate of the drawing location.
     * @param tileWidth
     *            The width of the world tiles.
     * @param tileHeight
     *            The height of the world tiles.
     * @param aspect
     *            The aspect ratio in which to draw.
     * @param textureManager
     *            A reference to the texture manager.
     */
    void customDraw(SpriteBatch batch, float posX, float posY, float tileWidth, float tileHeight, float aspect,
            TextureManager textureManager);
}
