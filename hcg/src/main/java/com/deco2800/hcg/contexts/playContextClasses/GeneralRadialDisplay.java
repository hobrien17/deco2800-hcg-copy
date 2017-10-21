package com.deco2800.hcg.contexts.playcontextclasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.entities.garden_entities.plants.Pot;
import com.deco2800.hcg.entities.garden_entities.seeds.Seed;
import com.deco2800.hcg.entities.bullets.BulletType;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlantManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.managers.TextureManager;
import com.deco2800.hcg.managers.SoundManager;
import com.deco2800.hcg.util.WorldUtil;
import com.deco2800.hcg.inventory.Inventory;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.stackable.ConsumableItem;
import com.deco2800.hcg.items.stackable.HealthPotion;
import com.deco2800.hcg.items.stackable.SmallMushroom;
import com.deco2800.hcg.items.stackable.SpeedPotion;
import com.deco2800.hcg.items.stackable.MagicMushroom;
import com.deco2800.hcg.items.tools.Fertiliser;
import com.deco2800.hcg.items.tools.Tool;
import com.deco2800.hcg.items.tools.BugSpray;
import com.deco2800.hcg.inventory.PlayerEquipment;

public class GeneralRadialDisplay extends Group {
	private PlantManager plantManager;
    private TextureManager textureManager;
    private GameManager gameManager;
    private SoundManager soundManager;
    private PlayerManager playerManager;

    private boolean active;
    private PlayerEquipment equippedItems;
    private Inventory inventory;
    private BulletType bulletType;
    
    private List<ImageButton> buttons;
    private List<Label> counts;
    private Image outline;
    private LabelStyle style;
    
    private Stage stage;
    private Group display;
    
    private List<String> items;
    private Map<String, String> sprites;
    private Map<String, ChangeListener> listeners;
    
    private float xSize;
    private float ySize;
    
    private static final float X_SIZE_MAX = 80f;
    private static final float Y_SIZE_MAX = 80f;
    private static final int MAX_BTNS = 11;
    private static final float OUTLINE_SIZE = 350f;
    private static final float DISTANCE = 165f;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(GameManager.class);
	
	public GeneralRadialDisplay(Stage stage, List<String> items) {
		this.items = new ArrayList<>(items);
		buttons = new ArrayList<>();
		counts = new ArrayList<>();
		
		int scale = this.items.size()/MAX_BTNS + 1;
		xSize = X_SIZE_MAX/(scale);
		ySize = Y_SIZE_MAX/(scale);
		
		gameManager = GameManager.get();
        textureManager = (TextureManager) gameManager.getManager(TextureManager.class);
        plantManager = (PlantManager) gameManager.getManager(PlantManager.class);
        soundManager = (SoundManager) gameManager.getManager(SoundManager.class);
        playerManager = (PlayerManager) gameManager.getManager(PlayerManager.class);

        this.stage = stage;
        this.active = false;

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("resources/ui/radial_menu/advent_pro.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 40;
		BitmapFont font = generator.generateFont(parameter);
		style = new LabelStyle(font, Color.valueOf("#FFFFFF"));
		generator.dispose();

		inventory = ((PlayerManager)GameManager.get().getManager(PlayerManager.class)).getPlayer().getInventory();
		playerManager.getPlayer().getEquippedWeapon().setBulletType(BulletType.BASIC);
		
        setupSprites();
        setupListeners();
        
        display = new Group();

        outline = getImage("outline");
        
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


	}
	
	@Override
	public float getWidth() {
		return display.getWidth();
	}
	
	@Override
	public float getHeight() {
		return display.getHeight();
	}
	
	@Override
	public float getX() {
		return display.getX();
	}
	
	@Override
	public float getY() {
		return display.getY();
	}
	
	private void setupSprites() {
		sprites = new HashMap<>();
		sprites.put("sunflower", "sunflower_btn");
		sprites.put("water", "water_btn");
		sprites.put("ice", "ice_btn");
		sprites.put("explosive", "explosive_btn");
		sprites.put("fire", "fire_btn");
		sprites.put("grass", "grass_btn");
		sprites.put("sunflowerC", "sunflower_btn");
		sprites.put("waterC", "water_btn");
		sprites.put("iceC", "ice_btn");
		sprites.put("explosiveC", "explosive_btn");
		sprites.put("fireC", "fire_btn");
		sprites.put("grassC", "grass_btn");
		sprites.put("outline", "radialOutline");
		sprites.put("machineGun", "machineGun");
		sprites.put("shotgun", "shotgun");
		sprites.put("scatterGun", "scatterGun");
		sprites.put("starfall", "starfall");
		sprites.put("fertiliser", "fertiliser_btn");
		sprites.put("bug_spray", "bugspray_btn");
		sprites.put("snag", "snag_btn");
		sprites.put("sausage", "sausage_btn");
		sprites.put("magic_mushroom", "magicMushroom");
		sprites.put("small_mushroom", "smallMushroom");
		sprites.put("hoe", "hoe");
		sprites.put("trowel", "trowel");
		sprites.put("shovel", "shovel");
	}
	
	private void setupListeners() {
		listeners = new HashMap<>();
		
		listeners.put("sunflower", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
				plant(new Seed(Seed.Type.SUNFLOWER));
				hide();
            }
        });
		
		listeners.put("water", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	plant(new Seed(Seed.Type.WATER));
				hide();
            }
        });
		
		listeners.put("ice", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	plant(new Seed(Seed.Type.ICE));
				hide();
            }
        });
		
		listeners.put("fire", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
				plant(new Seed(Seed.Type.FIRE));
				hide();
            }
        });
		
		listeners.put("explosive", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	plant(new Seed(Seed.Type.EXPLOSIVE));
				hide();
            }
        });
		
		listeners.put("grass", new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	plant(new Seed(Seed.Type.GRASS));
				hide();
            }
        });

		listeners.put("sunflowerC", new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				playerManager.getPlayer().getEquippedWeapon().setBulletType(BulletType.BASIC);
				hide();
			}
		});

		listeners.put("waterC", new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				playerManager.getPlayer().getEquippedWeapon().setBulletType(BulletType.HOMING);
				hide();
			}
		});

		listeners.put("iceC", new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				playerManager.getPlayer().getEquippedWeapon().setBulletType(BulletType.ICE);
				hide();
			}
		});

		listeners.put("fireC", new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				playerManager.getPlayer().getEquippedWeapon().setBulletType(BulletType.FIRE);
				hide();
			}
		});

		listeners.put("explosiveC", new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				playerManager.getPlayer().getEquippedWeapon().setBulletType(BulletType.EXPLOSION);
				hide();
			}
		});

		listeners.put("grassC", new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				playerManager.getPlayer().getEquippedWeapon().setBulletType(BulletType.GRASS);
				hide();
			}
		});

		listeners.put("machineGun", new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				playerManager.getPlayer().setEquipped(0);
				hide();
			}
		});

		listeners.put("shotgun", new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				playerManager.getPlayer().setEquipped(1);
				hide();
			}
		});

		listeners.put("scatterGun", new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				playerManager.getPlayer().setEquipped(2);
				hide();
			}
		});

		listeners.put("starfall", new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
			    playerManager.getPlayer().setEquipped(3);
				hide();
			}
		});

		listeners.put("fertiliser", new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				useItem(new Fertiliser());
				hide();
			}
		});

		listeners.put("bug_spray", new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				useItem(new BugSpray());
				hide();
			}
		});

		listeners.put("snag", new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				useItem(new HealthPotion(100));
				hide();
			}
		});

		listeners.put("sausage", new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				useItem(new SpeedPotion());
				hide();
			}
		});

		listeners.put("magic_mushroom", new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				useItem(new MagicMushroom());
				hide();
			}
		});

		listeners.put("small_mushroom", new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				useItem(new SmallMushroom());
				hide();
			}
		});

		listeners.put("hoe", new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//useItem(new Hoe());
				hide();
			}
		});

		listeners.put("trowel", new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				//useItem(new Trowel ());
				hide();
			}
		});

		listeners.put("shovel", new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Optional<AbstractEntity> closest = GeneralRadialDisplay.getClosestPot();
				if(closest.isPresent() && !((Pot)closest.get()).isEmpty()) {
					((Pot)closest.get()).removePlant();
				}
				hide();
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

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean input) {
		this.active = input;
	}
	
	public void hide() {
		display.setVisible(false);
		this.setActive(false);
	}
	
	public void show() {
		update();
		display.setVisible(true);
		this.setActive(true);
	}
	
	public void addRadialMenu(Stage stage) {
        stage.addActor(display);
        this.setActive(true);
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
				playPlantSound();
				inventory.removeItem(seed);
			}
			update();
			return;
		}
		
		Optional<AbstractEntity> closestCorpse = getClosestCorpse();
		if(closestCorpse.isPresent()) {
			Corpse corpse = (Corpse)closestCorpse.get();
			corpse.plantInside(seed);
			inventory.removeItem(seed);
			update();
			return;
		}
    }
    
    private void useItem(Item item) {
    	if(item instanceof Tool) {
    		if(getClosestPot().isPresent() && !((Pot)getClosestPot().get()).isEmpty()) {
    			((Tool)item).use();
    		}
    	} else if(item instanceof ConsumableItem) {
    		Player player = ((PlayerManager)GameManager.get().getManager(PlayerManager.class)).getPlayer();
    		((ConsumableItem)item).consume(player);
    	}
    	inventory.removeItem(item);
    	update();
    }
    
    /**
     * Returns the closest pot to the player
     * 
     * @return the closedst pot to the player
     */
    public static Optional<AbstractEntity> getClosestPot() {
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
    public static Optional<AbstractEntity> getClosestCorpse() {
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

	protected void playPlantSound() {
		String soundName = "plantingPot";
		soundManager.stopSound(soundName);
		soundManager.playSound(soundName);
	}

	protected void playPlantLilySound() {
		String soundName = "plantingLily";
		soundManager.stopSound(soundName);
		soundManager.playSound(soundName);
	}
	
	private int getCount(String texture) {
		int itemCount = 0;
		for(int i = 0; i < inventory.getNumItems(); i++) {
			if(inventory.getItem(i).getTexture().equals(texture)) {
				itemCount += inventory.getItem(i).getStackSize();
			}
		}
		return itemCount;
	}
	
	private void update() {
		for(Label lbl : counts) {
			display.removeActor(lbl);
		}
		for(int i = 0; i < buttons.size(); i++) {
			ImageButton button = buttons.get(i);
			String type = items.get(i);
			int count = -1;
			if(type.equals("sunflower") || type.equals("water") || type.equals("cactus") || type.equals("ice") || 
					type.equals("fire") || type.equals("explosive") || type.equals("grass")) {
				count = getCount(type + "_seed");
			} else if(type.equals("sunflowerC") || type.equals("waterC") || type.equals("cactusC") || type.equals("iceC") || 
					type.equals("fireC") || type.equals("explosiveC") || type.equals("grassC")) {
				count = getCount(type.substring(0, type.length() - 1) + "_seed");
			} else if(type.equals("machineGun") || type.equals("starfall") || type.equals("scatterGun") || type.equals("shotgun") ||
					type.equals("hoe") || type.equals("trowel") || type.equals("shovel"));
			else if(type.equals("health_potion")) {
				count = getCount("red_potion");
			} else if(type.equals("speed_potion")) {
				count = getCount("purple_potion");
			}
			else{
				count = getCount(type);
			}
			
			if(count == 0) {
				button.getImage().setColor(Color.valueOf("#505050"));
				button.setDisabled(true);
			} else {
				if(count > 1) {
					Label lbl = new Label(String.valueOf(count), style);
					counts.add(lbl);
					display.addActor(lbl);
					lbl.setX(button.getX() + button.getWidth() - 20);
					lbl.setY(button.getY() - 10);
				}
				button.getImage().setColor(Color.valueOf("#FFFFFF"));
				button.setDisabled(false);
			}
		}
	}
}
