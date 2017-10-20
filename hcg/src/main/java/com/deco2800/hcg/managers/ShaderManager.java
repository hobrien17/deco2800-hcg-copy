package com.deco2800.hcg.managers;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.deco2800.hcg.renderers.RenderLightmap;
import com.deco2800.hcg.renderers.Renderer;
import com.deco2800.hcg.shading.ShaderState;

public class ShaderManager extends Manager implements Observer {
    private FileHandle preVertexShader;
    private FileHandle postVertexShader;
    private FileHandle preFragShader;
    private FileHandle postFragShader;
    private FileHandle lightFragShader;
    
    private ShaderProgram preShader;
    private ShaderProgram postShader;
    private ShaderProgram lightShader;

    private Logger LOGGER;

    private ShaderState state;

    private FrameBuffer renderTarget;
    private TextureRegion scene;
    private SpriteBatch preBatch;
    private SpriteBatch postBatch;
    private BatchTiledMapRenderer tileRenderer;

    private GameManager gameManager;
    private PlayerManager playerManager;
    
    private RenderLightmap lightRenderer;

    //Flag for custom overlay renders
    private ArrayList<customShader> customRenders;
    
    private boolean enabled = true;

    private float health;

    private class customShader {
        Float contrast;
        Float bloom;
        Float heat;
        Color color;
        StopwatchManager duration;
        int durationTime;
    }

    public ShaderManager() {
        this.preVertexShader = Gdx.files.internal("resources/shaders/vertex_pre.glsl");
        this.postVertexShader = Gdx.files.internal("resources/shaders/vertex_post.glsl");
        
        this.preFragShader = Gdx.files.internal("resources/shaders/fragment_pre.glsl");
        this.postFragShader = Gdx.files.internal("resources/shaders/fragment_post.glsl");
        
        this.lightFragShader =  Gdx.files.internal("resources/shaders/lighting.glsl");

        this.preShader = new ShaderProgram(preVertexShader, preFragShader);
        this.postShader = new ShaderProgram(postVertexShader, postFragShader);
        this.lightShader = new ShaderProgram(postVertexShader, lightFragShader);

        this.gameManager = GameManager.get();
        this.playerManager = (PlayerManager) gameManager.getManager(PlayerManager.class);

        LOGGER = LoggerFactory.getLogger(ShaderManager.class);
        if(!preShader.isCompiled()) {
            LOGGER.error("Shader failed to compile.");
            System.out.println(preShader.getLog());
            LOGGER.error(preShader.getLog());
            
            preShader = null;
        }
        
        if(!postShader.isCompiled()) {
            LOGGER.error("Post shader failed to compile");
            System.out.println(postShader.getLog());
            LOGGER.error(postShader.getLog());
            
            postShader = null;
        }
        
        if(!lightShader.isCompiled()) {
            LOGGER.error("Light shader failed to compile");
            System.out.println(lightShader.getLog());
            LOGGER.error(lightShader.getLog());
            
            lightShader = null;
        }
        
        this.state = new ShaderState(new Color(1, 1, 1, 1), new Color(0.3F, 0.3F, 0.8F, 1));

        this.state.setBloom(true);

        this.state.setHeat(false);
        this.state.setContrast(0.2F);

        //Max of 5 custom renders
        customRenders = new ArrayList<>();
        
        this.lightRenderer = new RenderLightmap();
    }
    
    public boolean shadersCompiled() {
        return !(this.preShader == null || this.postShader == null || this.lightShader == null);
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
        
        // Begin processing ////////////////////////////////////////////////////////////////////////////////////////
        this.preShader.begin();
        checkCustomDurations();
        //this.preShader.setUniformf("u_globalColor", state.getGlobalLightColour());
        if (customRenders.size() > 0) {
            Color baseLight = state.getGlobalLightColour();
            for (int i = 0; i < customRenders.size(); i++) {
                //this.preShader.setUniformf("u_globalColor", customRenders.get(i).color);
                baseLight.mul(customRenders.get(i).color);
            }
            //this.preShader.setUniformf("u_globalColor", baseLight);
        }
        this.preShader.setUniformf("u_globalLight", this.state.getGlobalLightColour());
        this.preBatch = new SpriteBatch(1001, preShader);
        this.preBatch.setProjectionMatrix(GameManager.get().getCamera().combined);
        this.preBatch.enableBlending();
            
        this.tileRenderer = renderer.getTileRenderer(preBatch);
        this.tileRenderer.setView(GameManager.get().getCamera());
            
        // Draw onto render target ////////////////////////////////////
        this.renderTarget.begin();
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
            
        this.tileRenderer.render();
        lightRenderer.render(preBatch);
        renderer.render(preBatch);
            
        this.renderTarget.end();
        // Finish drawing onto render target //////////////////////////
            
        this.preShader.end();
        this.preBatch.dispose();
            
        // Begin post-processing ///////////////////////////////////////////////////////////////////////////////////
        this.postShader.begin();
        
        int location = this.postShader.getUniformLocation("u_lightmap");
        this.postShader.setUniformi(location, 1);
        this.postShader.setUniformf("u_time", (float)(Math.PI * timeManager.getSeconds() / 60.0F));
        this.postShader.setUniformf("u_heat", state.getHeat());
        this.postShader.setUniformf("u_bloom", state.getBloom());
        this.postShader.setUniformf("u_contrast", state.getContrast());
        this.postShader.setUniformf("u_sick", state.getSick());

        this.health = ((float) playerManager.getPlayer().getHealthCur())
                / ((float) playerManager.getPlayer().getHealthMax());
        this.postShader.setUniformf("u_health", this.health);
        // Apply custom effects over the top of the regular effects
        if (customRenders.size() > 0) {
            float baseHeat = state.getHeat();
            float baseBloom = state.getBloom();
            float baseContrast = state.getContrast();
            for (int i = 0; i < customRenders.size(); i++) {
                baseContrast += customRenders.get(i).contrast;
                baseHeat += customRenders.get(i).heat;
                baseBloom += customRenders.get(i).bloom;
            }
            this.postShader.setUniformf("u_heat", baseHeat);
            this.postShader.setUniformf("u_bloom", baseBloom);
            this.postShader.setUniformf("u_contrast", baseContrast);
        }

        this.postShader.end();
        
        this.postBatch = new SpriteBatch(1, this.postShader);
            
        // Draw onto screen ///////////////////////////////////////////
        postBatch.begin();
        
        postBatch.enableBlending();
          
        postBatch.draw(scene, 0, 0, width, height);
            
        postBatch.end();
        // Finish drawing onto screen /////////////////////////////////
            
        this.postBatch.dispose();
        this.renderTarget.dispose();
    }

    public void setOvercast(float overcast) {
        state.setContrast(overcast);
    }

    /** Function used for placing overlay effects on the render */
    public void setCustom(float contrast, float heat, float bloom, Color color, int duration) {
        customShader shader = new customShader();
        shader.contrast = contrast;
        shader.heat = heat;
        shader.bloom = bloom;
        shader.color = color;
        shader.duration = (StopwatchManager) GameManager.get().getManager(StopwatchManager.class);
        shader.duration.addObserver(this);
        shader.durationTime = duration;
        shader.duration.startTimer(duration);
        customRenders.add(shader);
    }

    public void checkCustomDurations() {
        for (int i = 0; i < customRenders.size(); i++) {
            if (customRenders.get(i).durationTime < 0) {
                //Shader is finished
                customRenders.remove(i);
            }
        }
    }

    /** Needed for stopwatch manager */
    @Override
    public void update(Observable o, Object arg) {
        int time = (int) (float) arg;
        for (int i = 0; i < customRenders.size(); i++) {
            customRenders.get(i).durationTime--;
        }

    }
    
    public void bindLightShader(SpriteBatch batch) {
        batch.setShader(this.lightShader);
    }
    
    public boolean shadersEnabled() {
        return this.enabled;
    }
    
    public void toggleShaders() {
        this.enabled = !this.enabled;
    }
}
