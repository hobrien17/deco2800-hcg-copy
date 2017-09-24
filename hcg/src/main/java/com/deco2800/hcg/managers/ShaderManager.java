package com.deco2800.hcg.managers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deco2800.hcg.managers.TimeManager;
import com.deco2800.hcg.renderers.RenderLightmap;
import com.deco2800.hcg.renderers.Renderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.deco2800.hcg.shading.ShaderState;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;

public class ShaderManager extends Manager {
    private FileHandle preVertexShader;
    private FileHandle postVertexShader;
    private FileHandle preFragShader;
    private FileHandle postFragShader;
    
    private ShaderProgram preShader;
    private ShaderProgram postShader;

    private Logger LOGGER;

    private ShaderState state;

    private FrameBuffer renderTarget;
    private TextureRegion scene;
    private FrameBuffer lightTarget;
    private TextureRegion lightMap;
    private SpriteBatch lightBatch;
    private SpriteBatch preBatch;
    private SpriteBatch postBatch;
    private BatchTiledMapRenderer tileRenderer;
    private RenderLightmap lightRenderer;
    
    public ShaderManager() {
        this.preVertexShader = Gdx.files.internal("resources/shaders/vertex_pre.glsl");
        this.postVertexShader = Gdx.files.internal("resources/shaders/vertex_post.glsl");
        this.preFragShader = Gdx.files.internal("resources/shaders/fragment_pre.glsl");
        this.postFragShader = Gdx.files.internal("resources/shaders/fragment_post.glsl");

        this.preShader = new ShaderProgram(preVertexShader, preFragShader);
        this.postShader = new ShaderProgram(postVertexShader, postFragShader);
        
        LOGGER = LoggerFactory.getLogger(ShaderManager.class);
        if(!preShader.isCompiled()) {
            LOGGER.error("Shader failed to compile.");
            LOGGER.error(preShader.getLog());
            
            // For the time being
            System.out.println("Shader failed to compile.");
            System.out.println(preShader.getLog());
            preShader = null;
        }
        
        if(!this.postShader.isCompiled()) {
            LOGGER.error("Post preShader failed to compile");
            LOGGER.error(this.postShader.getLog());
            
            // For the time being
            System.out.println("Post preShader failed to compile");
            System.out.println(this.postShader.getLog());
            this.postShader = null;
        }
        
        this.state = new ShaderState(new Color(1, 1, 1, 1), new Color(0.3F, 0.3F, 0.8F, 1));

        this.state.setBloom(true);
        this.state.setHeat(false);
        this.state.setContrast(0.8F);
        
        this.lightRenderer = new RenderLightmap();
    }
    
    public boolean shadersCompiled() {
        return !(this.preShader == null || this.postShader == null);
    }

    public void render(TimeManager timeManager, Renderer renderer) {
        this.state.setTime(timeManager);
        int width = Gdx.graphics.getWidth();
        int height = Gdx.graphics.getHeight();
            
        // This is our render target. We draw onto this first and then draw this
        // directly to the screen using a post processing shader
        this.renderTarget = new FrameBuffer(Format.RGB565, width, height, false);
        this.scene = new TextureRegion(renderTarget.getColorBufferTexture());
        this.scene.flip(false, true);
        
        // Begin lightmap //////////////////////////////////////////////////////////////////////////////////////////
        
        this.lightTarget = new FrameBuffer(Format.RGB565, width, height, false);
        this.lightMap = new TextureRegion(lightTarget.getColorBufferTexture());
        this.lightMap.flip(false,  true);
        
        this.lightBatch = new SpriteBatch();
        this.lightBatch.setProjectionMatrix(GameManager.get().getCamera().combined);
        
        // Draw onto light target //////////////////////////////////////
        this.lightTarget.begin();
        Gdx.gl.glClearColor(1, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        
        this.lightRenderer.render(this.lightBatch);
        
        this.lightTarget.end();
        this.lightBatch.dispose();
        
        // Begin processing ////////////////////////////////////////////////////////////////////////////////////////
        this.preShader.begin();
            
        this.preShader.setUniformf("u_globalColor", state.getGlobalLightColour());
            
        this.preBatch = new SpriteBatch(1001, preShader);
        this.preBatch.setProjectionMatrix(GameManager.get().getCamera().combined);
            
        this.tileRenderer = renderer.getTileRenderer(preBatch);
        this.tileRenderer.setView(GameManager.get().getCamera());
            
        // Draw onto render target ////////////////////////////////////
        this.renderTarget.begin();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
            
        this.tileRenderer.render();
        renderer.render(preBatch);
            
        this.renderTarget.end();
        // Finish drawing onto render target //////////////////////////
            
        this.preShader.end();
        this.preBatch.dispose();
            
        // Begin post-processing ///////////////////////////////////////////////////////////////////////////////////
        this.postShader.begin();
            
        this.postShader.setUniformf("u_time", (float)(Math.PI * timeManager.getSeconds() / 60.0F));
        this.postShader.setUniformf("u_heat", state.getHeat());
        this.postShader.setUniformf("u_bloom", state.getBloom());
        this.postShader.setUniformf("u_contrast", state.getContrast());
            
        this.postBatch = new SpriteBatch(1, this.postShader);
            
        // Draw onto screen ///////////////////////////////////////////
        postBatch.begin();
            
        postBatch.draw(scene, 0, 0, width, height);
            
        postBatch.end();
        // Finish drawing onto screen /////////////////////////////////
            
        this.postShader.end();
        this.postBatch.dispose();
        this.renderTarget.dispose();
        this.lightTarget.dispose();
    }

    public void setOvercast(float overcast) {
        state.setContrast(overcast);
    }
}
