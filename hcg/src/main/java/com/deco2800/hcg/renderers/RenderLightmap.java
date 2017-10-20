package com.deco2800.hcg.renderers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.ShaderManager;
import com.deco2800.hcg.managers.TextureManager;
import com.deco2800.hcg.shading.LightEmitter;

/**
 * Isometric Lightmap renderer
 */
public class RenderLightmap implements Renderer {

    BitmapFont font;

    /**
     * Renders the lightmap for the world.
     *
     * @param batch Batch to render onto
     */
    @Override
    public void render(SpriteBatch batch) {
        if (font == null) {
            font = new BitmapFont();
            font.getData().setScale(0.25f);
        }
        List<AbstractEntity> renderables = GameManager.get().getWorld()
                .getEntities();
        int worldLength = GameManager.get().getWorld().getLength();
        int worldWidth = GameManager.get().getWorld().getWidth();

        int tileWidth = (int) GameManager.get().getWorld().getMap()
                .getProperties().get("tilewidth");
        int tileHeight = (int) GameManager.get().getWorld().getMap()
                .getProperties().get("tileheight");

        float baseX = tileWidth * (worldWidth / 2.0f - 0.5f); // bad

        float baseY = -tileHeight / 2 * worldLength + tileHeight / 2f; // good

        List<AbstractEntity> lights = new ArrayList<>();

        for (Renderable r : renderables) {
            if (r instanceof LightEmitter) {
                lights.add((AbstractEntity) r);
            }
        }

        TextureManager reg = (TextureManager) GameManager.get()
                .getManager(TextureManager.class);
        Texture lightMap = reg.getTexture("lightmap");
        float lightAspect = (float) (lightMap.getWidth()) / (float) (tileWidth);
        ShaderManager shaders = (ShaderManager) GameManager.get().getManager(ShaderManager.class);
        batch.begin();
        
        ShaderProgram shader = batch.getShader();
        Color preColour = batch.getColor();
        shaders.bindLightShader(batch);
        
        for (int index = 0; index < lights.size(); index++) {
            Renderable entity = lights.get(index);

            float cartX = entity.getPosX();
            float cartY = (worldWidth - 1) - entity.getPosY();

            float isoX = baseX + ((cartX - cartY) / 2.0f * tileWidth);
            float isoY = baseY + ((cartX + cartY) / 2.0f) * tileHeight;

            // We want to keep the aspect ratio of the image so...
            
            Color colour = ((LightEmitter)entity).getLightColour();
            float intensity = ((LightEmitter)entity).getLightPower();
            
            batch.setColor(colour);
            
            float lightWidth = intensity * tileWidth * entity.getXRenderLength();
            float lightHeight = intensity * (lightMap.getHeight() / lightAspect) * entity.getYRenderLength();
            
            float width = tileWidth * entity.getXRenderLength();
            batch.draw(lightMap, 
                    isoX - (lightWidth / 2) + (width / 2), 
                    isoY - (lightHeight / 2),
                    lightWidth, lightHeight);
        }
        
        batch.setColor(preColour);
        batch.setShader(shader);
        
        batch.end();

    }

    /**
     * Returns the correct tile renderer for the given rendering engine
     *
     * @param batch The current sprite batch
     * @return A TiledMapRenderer for the current engine
     */
    @Override
    public BatchTiledMapRenderer getTileRenderer(SpriteBatch batch) {
        return new IsometricTiledMapRenderer(
                GameManager.get().getWorld().getMap(), 1, batch);
    }
}
