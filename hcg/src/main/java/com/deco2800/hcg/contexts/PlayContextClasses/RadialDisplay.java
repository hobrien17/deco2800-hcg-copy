package com.deco2800.hcg.contexts.PlayContextClasses;

import java.util.Optional;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.hcg.managers.*;
import com.deco2800.hcg.util.WorldUtil;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.entities.garden_entities.plants.Pot;
import com.deco2800.hcg.entities.garden_entities.seeds.Seed;

/**
 * Displays a radial menu, used for planting in pots and corpses
 * 
 * @author Reilly Lundin
 *
 */
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

    /**
     * Creates a new radial display in the center of the screen
     * 
     * @param stage
     */
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
                addBugSpray();
                radialDisplay.remove();
            }
        });
        
        fertiliserButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                addFertiliser();
                radialDisplay.remove();
            }
        });
        
    }

    /**
     * Adds the radial menu to the given stage
     * 
     * @param stage
     * 			the stage to add this to
     */
    public void addRadialMenu(Stage stage) {
        stage.addActor(radialDisplay);
    }

    /**
     * Removes the radial menu
     */
    public void removeRadialMenu() {
        radialDisplay.remove();
    }
    
    /**
     * Plants the given seed inside a nearby pot or corpse
     * 
     * @param seed
     * 			the seed to plant
     */
    private void plant(Seed seed) {
    	
    	Optional<AbstractEntity> closestPot = getClosestPot();
		if(closestPot.isPresent()) {
			Pot pot = (Pot)closestPot.get();
			if(pot.plantInside(seed)) {
				plantManager.addPlants(pot.getPlant());
				plantManager.updateLabel();
			}
			return;
		}
		
		Optional<AbstractEntity> closestCorpse = getClosestCorpse();
		if(closestCorpse.isPresent()) {
			Corpse corpse = (Corpse)closestCorpse.get();
			corpse.plantInside(seed);
			return;
		}
    }
    
    private void addBugSpray() {
    	Optional<AbstractEntity> closest = getClosestPot();
    	if(closest.isPresent()) {
    		Pot pot = (Pot)closest.get();
    		if(!pot.isEmpty()) {
    			pot.getPlant().increaseRarity(0.1, 0.05);
    		}
    	}
    }
    
    private void addFertiliser() {
    	Optional<AbstractEntity> closest = getClosestPot();
    	if(closest.isPresent()) {
    		Pot pot = (Pot)closest.get();
    		if(!pot.isEmpty()) {
    			pot.getPlant().changeDelay(0.8f);
    		}
    	}
    }
    
    /**
     * Returns the closest pot to the player
     * 
     * @return the closedst pot to the player
     */
    private static Optional<AbstractEntity> getClosestPot() {
    	PlayerManager pm = (PlayerManager) GameManager.get().getManager(PlayerManager.class);
		Player player = pm.getPlayer();
		float px = player.getPosX();
		float py = player.getPosY();
		
		return WorldUtil.closestEntityToPosition(px, py, 1.5f, Pot.class);
    }
    
    /**
     * Returns the closest corpse to the player
     * 
     * @return the closest corpse to the player
     */
    private static Optional<AbstractEntity> getClosestCorpse() {
    	PlayerManager pm = (PlayerManager) GameManager.get().getManager(PlayerManager.class);
		Player player = pm.getPlayer();
		float px = player.getPosX();
		float py = player.getPosY();
		
		return WorldUtil.closestEntityToPosition(px, py, 1.5f, Corpse.class);
    }
    
    /**
     * Determines whether a plantable pot or a corpse is nearby
     * A pot is defined as plantable if it is unlocked (regardless of whether it is empty or not)
     * A corpse must be empty to be plantable
     * 
     * @return
     * 		true if a plantable pot or corpse is nearby, otherwise false
     */
    public static boolean plantableNearby() {				
		Optional<AbstractEntity> closestPot = getClosestPot();
		Optional<AbstractEntity> closestCorpse = getClosestCorpse();
		return (closestPot.isPresent() && !((Pot)closestPot.get()).isLocked()) || 
				(closestCorpse.isPresent() && ((Corpse)closestCorpse.get()).isEmpty());
    }
}