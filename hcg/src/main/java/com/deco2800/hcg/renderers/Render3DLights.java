package com.deco2800.hcg.renderers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.HasProgress;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.ShaderManager;
import com.deco2800.hcg.managers.TextureManager;
import com.deco2800.hcg.shading.LightEmitter;

/**
 * A simple isometric renderer for DECO2800 games
 *
 * @Author Tim Hadwen
 */
public class Render3DLights implements Renderer {

    BitmapFont font;

    /**
     * Renders onto a batch, given a renderables with entities It is expected
     * that AbstractWorld contains some entities and a Map to read tiles from
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

        List<AbstractEntity> entities = new ArrayList<>();

        /* Gets a list of all entities in the renderables */
        for (Renderable r : renderables) {
            if (r instanceof AbstractEntity) {
                entities.add((AbstractEntity) r);
            }
        }

        /* Sort these so that the objects don't render in the wrong order */
        Collections.sort(entities);

        batch.begin();

        // Get a refernce to the texture manager 
        TextureManager reg = (TextureManager) GameManager.get()
                .getManager(TextureManager.class);
        Texture lightMap = reg.getTexture("lightmap");
        float lightAspect = (float) (lightMap.getWidth()) / (float) (tileWidth);
        
        ShaderManager shaders = (ShaderManager) GameManager.get().getManager(ShaderManager.class);
        
        /* Render each entity (backwards) in order to retain objects at the front */
        for (int index = 0; index < entities.size(); index++) {
        	Color tint;
        	if((tint = entities.get(index).getTint()) != null) {
        		batch.setColor(tint);
        	} else {
        		batch.setColor(Color.WHITE);
        	}
        	
            Renderable entity = entities.get(index);

            String textureString = entity.getTexture();
            Texture tex = reg.getTexture(textureString);

            float cartX = entity.getPosX();
            float cartY = (worldWidth - 1) - entity.getPosY();

            float isoX = baseX + ((cartX - cartY) / 2.0f * tileWidth);
            float isoY = baseY + ((cartX + cartY) / 2.0f) * tileHeight;

            // We want to keep the aspect ratio of the image so...
            float aspect = (float) (tex.getWidth()) / (float) (tileWidth);

            ShaderProgram shader = batch.getShader();
            Color preColour = batch.getColor();
            
            if(entity instanceof LightEmitter && ((LightEmitter)entity).getLightPower() > 0) {
                shaders.bindLightShader(batch);
            }
               
            batch.setColor(Color.WHITE);
            if(entity instanceof CustomRenderable) {
                ((CustomRenderable) entity).customDraw(batch, isoX, isoY, tileWidth, tileHeight, aspect, reg);
            } else {
                batch.draw(tex, isoX, isoY, tileWidth * entity.getXRenderLength(),
                        (tex.getHeight() / aspect) * entity.getYRenderLength());
            }
            
            batch.setColor(preColour);
            batch.setShader(shader);
        }

        for (int index = 0; index < entities.size(); index++) {
            Renderable entity = entities.get(index);

            float cartX = entity.getPosX();
            float cartY = (worldWidth - 1) - entity.getPosY();

            float isoX = baseX + ((cartX - cartY) / 2.0f * tileWidth);
            float isoY = baseY + ((cartX + cartY) / 2.0f) * tileHeight;

            if (entity instanceof HasProgress && ((HasProgress) entity)
                    .showProgress()) {
                font.draw(batch, String.format("%d%%",
                        ((HasProgress) entity).getProgress()),
                        isoX + tileWidth / 2 - 5, isoY + 50);
            }
        }
        
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
