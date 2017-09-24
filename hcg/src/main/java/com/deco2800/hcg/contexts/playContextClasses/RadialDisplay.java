package com.deco2800.hcg.contexts.playContextClasses;

import java.util.Optional;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.deco2800.hcg.managers.*;
import com.deco2800.hcg.util.WorldUtil;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.entities.garden_entities.plants.Planter;
import com.deco2800.hcg.entities.garden_entities.plants.Pot;
import com.deco2800.hcg.entities.garden_entities.seeds.Seed;
import com.deco2800.hcg.items.*;


public class RadialDisplay extends Group {

    private PlantManager plantManager;
    private TextureManager textureManager;
    private GameManager gameManager;

    private ImageButton normalButton;
    private ImageButton explosiveButton;
    private ImageButton fireButton;
    private ImageButton grassButton;
    private ImageButton iceButton;
    private ImageButton waterButton;
    private ImageButton xButton;
    private ImageButton fertiliserButton;
    private ImageButton sprayButton;
    private Image radialOutline;

    private Stage stageInput;
    private Skin skin;

    private Group radialDisplay;

    public RadialDisplay(Stage stage) {

        gameManager = GameManager.get();
        textureManager = (TextureManager) gameManager.getManager(TextureManager.class);
        plantManager = (PlantManager) gameManager.getManager(PlantManager.class);
        skin = new Skin(Gdx.files.internal("resources/ui/uiskin.json"));
        stageInput = stage;

        radialOutline = new Image(textureManager.getTexture("radialOutline"));

        radialDisplay = new Group();
        
        normalButton = new ImageButton(new Image(textureManager.getTexture("sunflower_btn")).getDrawable());
        waterButton = new ImageButton(new Image(textureManager.getTexture("water_btn")).getDrawable());
        iceButton = new ImageButton(new Image(textureManager.getTexture("ice_btn")).getDrawable());
        fireButton = new ImageButton(new Image(textureManager.getTexture("fire_btn")).getDrawable());
        grassButton = new ImageButton(new Image(textureManager.getTexture("grass_btn")).getDrawable());
        explosiveButton = new ImageButton(new Image(textureManager.getTexture("explosive_btn")).getDrawable());
        sprayButton = new ImageButton(new Image(textureManager.getTexture("bugspray_btn")).getDrawable());
        fertiliserButton = new ImageButton(new Image(textureManager.getTexture("fertiliser_btn")).getDrawable());
        xButton = new ImageButton(new Image(textureManager.getTexture("menuClose")).getDrawable());

        radialOutline.setSize(350, 350);
        radialOutline.setPosition(radialDisplay.getWidth() / 2f - radialOutline.getWidth() / 2f, radialDisplay.getHeight() / 2f - radialOutline.getHeight() / 2f);
        radialDisplay.setPosition(stageInput.getWidth() / 2, stageInput.getHeight() / 2);
        //radialDisplay.setSize(350, 350);
        radialDisplay.addActor(radialOutline);
        
        normalButton.setSize(80, 80);
        waterButton.setSize(80, 80);
        iceButton.setSize(80, 80);
        fireButton.setSize(80, 80);
        grassButton.setSize(80, 80);
        explosiveButton.setSize(80, 80);
        sprayButton.setSize(80, 80);
        fertiliserButton.setSize(80, 80);
        xButton.setSize(80,80);
        
        normalButton.setPosition(radialDisplay.getWidth()/2f - normalButton.getWidth()/2f - 150, 
        		radialDisplay.getWidth()/2f - normalButton.getHeight()/2f);
        waterButton.setPosition(radialDisplay.getWidth()/2f - waterButton.getWidth()/2f + 150,
        		radialDisplay.getHeight()/2f - waterButton.getHeight()/2f);
        iceButton.setPosition(radialDisplay.getWidth()/2f - iceButton.getWidth()/2f + 106,
        		radialDisplay.getHeight()/2f - iceButton.getHeight()/2f + 106);
        fireButton.setPosition(radialDisplay.getWidth()/2f - fireButton.getWidth()/2f,
        		radialDisplay.getHeight()/2f - fireButton.getHeight()/2f + 150);
        grassButton.setPosition(radialDisplay.getWidth()/2f - grassButton.getWidth()/2f + 106,
        		radialDisplay.getHeight()/2f - grassButton.getHeight()/2f - 106);
        explosiveButton.setPosition(radialDisplay.getWidth()/2f - explosiveButton.getWidth()/2f - 106,
        		radialDisplay.getHeight()/2f - explosiveButton.getHeight()/2f + 106);
        sprayButton.setPosition(radialDisplay.getWidth()/2f - sprayButton.getWidth()/2f,
        		radialDisplay.getHeight()/2f - sprayButton.getHeight()/2f - 150);
        fertiliserButton.setPosition(radialDisplay.getWidth()/2f - fertiliserButton.getWidth()/2f - 106,
        		radialDisplay.getHeight()/2f - fertiliserButton.getHeight()/2f - 106);
        xButton.setPosition(radialDisplay.getWidth()/2f - xButton.getWidth()/2f,
        		radialDisplay.getHeight()/2f - xButton.getHeight()/2f);
        
        radialDisplay.addActor(normalButton);
        radialDisplay.addActor(fireButton);
        radialDisplay.addActor(waterButton);
        radialDisplay.addActor(explosiveButton);
        radialDisplay.addActor(iceButton);
        radialDisplay.addActor(grassButton);
        radialDisplay.addActor(sprayButton);
        radialDisplay.addActor(fertiliserButton);
        radialDisplay.addActor(xButton);
        
        xButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                radialDisplay.remove();
            }
        });
        
        normalButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	plant(new Seed(Seed.Type.SUNFLOWER));
                radialDisplay.remove();
            }
        });
        
        fireButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	plant(new Seed(Seed.Type.FIRE));
                radialDisplay.remove();
            }
        });
        
        waterButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	plant(new Seed(Seed.Type.WATER));
                radialDisplay.remove();
            }
        });
        
        iceButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	plant(new Seed(Seed.Type.ICE));
                radialDisplay.remove();
            }
        });
        
        grassButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	plant(new Seed(Seed.Type.GRASS));
                radialDisplay.remove();
            }
        });
        
        explosiveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	plant(new Seed(Seed.Type.EXPLOSIVE));
                radialDisplay.remove();
            }
        });
        
        sprayButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //TODO
                radialDisplay.remove();
            }
        });
        
        fertiliserButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //TODO
                radialDisplay.remove();
            }
        });
        
    }


    public void addRadialMenu(Stage stage) {
        stage.addActor(radialDisplay);
    }

    public void removeRadialMenu() {
        radialDisplay.remove();
    }
    
    private void plant(Seed seed) {
    	PlayerManager pm = (PlayerManager) GameManager.get().getManager(PlayerManager.class);
		Player player = pm.getPlayer();
		float px = player.getPosX();
		float py = player.getPosY();
		
		Optional<AbstractEntity> closestPot = WorldUtil.closestEntityToPosition(px, py, 1.5f, Pot.class);
		if(closestPot.isPresent() && closestPot.get() instanceof Pot) {
			Pot pot = (Pot)closestPot.get();
			if(pot.plantInside(seed)) {
				plantManager.addPlants(pot.getPlant());
				plantManager.updateLabel();
			}
			return;
		}
		
		Optional<AbstractEntity> closestCorpse = WorldUtil.closestEntityToPosition(px, py, 1.5f, Corpse.class);
		if(closestCorpse.isPresent() && closestCorpse.get() instanceof Corpse) {
			Corpse corpse = (Corpse)closestCorpse.get();
			corpse.plantInside(seed);
			return;
		}
    }
}