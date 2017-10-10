package com.deco2800.hcg.contexts.playContextClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.entities.garden_entities.plants.Pot;
import com.deco2800.hcg.entities.garden_entities.seeds.Seed;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlantManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.managers.TextureManager;
import com.deco2800.hcg.util.WorldUtil;


public class GeneralRadialDisplay extends Group {
	private PlantManager plantManager;
    private TextureManager textureManager;
    private GameManager gameManager;
    
    private List<ImageButton> buttons;
    private ImageButton closeButton;
    private Image outline;
    
    private Stage stage;
    private Group display;
    
    private List<String> items;
    private Map<String, String> sprites;
    private Map<String, ChangeListener> listeners;
    
    private float xSize;
    private float ySize;
    
    private final static float X_SIZE_MAX = 80f;
    private final static float Y_SIZE_MAX = 80f;
    private final static int MAX_BTNS = 11;
    private final static float OUTLINE_SIZE = 350f;
    private final static float DISTANCE = 165f;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(GameManager.class);
	
	public GeneralRadialDisplay(Stage stage, List<String> items) {
		this.items = new ArrayList<>(items);
		buttons = new ArrayList<>();
		
		int scale = this.items.size()/MAX_BTNS + 1;
		xSize = X_SIZE_MAX/(scale);
		ySize = Y_SIZE_MAX/(scale);
		
		gameManager = GameManager.get();
        textureManager = (TextureManager) gameManager.getManager(TextureManager.class);
        plantManager = (PlantManager) gameManager.getManager(PlantManager.class);
        this.stage = stage;
		
        setupSprites();
        setupListeners();
        
        display = new Group();
        outline = getImage("outline");
        closeButton = new ImageButton(getImage("close").getDrawable());
        
        outline.setSize(OUTLINE_SIZE, OUTLINE_SIZE);
        outline.setPosition(display.getWidth() / 2f - outline.getWidth() / 2f, 
        		display.getHeight() / 2f - outline.getHeight() / 2f);
        display.setPosition(this.stage.getWidth() / 2, this.stage.getHeight() / 2);
        display.addActor(outline);
        
		for(int i = 0; i < this.items.size(); i++) {
			String item = this.items.get(i);
			ImageButton button = new ImageButton(getImage(item).getDrawable());
			buttons.add(button);
			button.setSize(xSize, ySize);
			button.setPosition(getButtonX(i), getButtonY(i));
			button.addListener(listeners.get(item));
			display.addActor(button);
		}
		
		closeButton = new ImageButton(getImage("close").getDrawable());
		closeButton.setSize(X_SIZE_MAX, Y_SIZE_MAX);
		closeButton.setPosition(display.getWidth()/2f - X_SIZE_MAX/2f, 
				display.getHeight()/2f - Y_SIZE_MAX/2f);
		display.addActor(closeButton);
		
		closeButton.addListener(new ChangeListener() {
	            @Override
	            public void changed(ChangeEvent event, Actor actor) {
	                display.remove();
	            }
	        });
	}
	
	private void setupSprites() {
		sprites = new HashMap<>();
		sprites.put("sunflower", "sunflower_btn");
		sprites.put("water", "water_btn");
		sprites.put("ice", "ice_btn");
		sprites.put("explosive", "explosive_btn");
		sprites.put("fire", "fire_btn");
		sprites.put("grass", "grass_btn");
		sprites.put("outline", "radialOutline");
		sprites.put("close", "menuClose");
	}
	
	private void setupListeners() {
		listeners = new HashMap<>();
		
		listeners.put("sunflower", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	plant(new Seed(Seed.Type.SUNFLOWER));
                display.remove();
            }
        });
		
		listeners.put("water", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	plant(new Seed(Seed.Type.WATER));
                display.remove();
            }
        });
		
		listeners.put("ice", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	plant(new Seed(Seed.Type.ICE));
                display.remove();
            }
        });
		
		listeners.put("fire", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	plant(new Seed(Seed.Type.FIRE));
                display.remove();
            }
        });
		
		listeners.put("explosive", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	plant(new Seed(Seed.Type.EXPLOSIVE));
                display.remove();
            }
        });
		
		listeners.put("grass", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	plant(new Seed(Seed.Type.GRASS));
                display.remove();
            }
        });
	}
	
	private Image getImage(String name) {
		String sprite = sprites.get(name);
		if(sprite == null) {
			LOGGER.error("Button name '" + name + "' is invalid");
		}
		return new Image(textureManager.getTexture(sprite));
	}
	
	private float getAngle(int index) {
		int total = items.size();
		float angle = (360f * index)/total + 90f;
		return angle;
	}
	
	private float getButtonX(int index) {
		float angle = (float)(getAngle(index) * Math.PI / 180.0);
		return (float)(display.getWidth()/2f - xSize/2f + DISTANCE*Math.cos(angle));
	}
	
	private float getButtonY(int index) {
		float angle = (float)(getAngle(index) * Math.PI / 180.0);
		return (float)(display.getHeight()/2f - xSize/2f + DISTANCE*Math.sin(angle));
	}
	
	public void addRadialMenu(Stage stage) {
        stage.addActor(display);
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
