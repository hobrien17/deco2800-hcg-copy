package com.deco2800.hcg.entities;

import java.util.*;

import com.deco2800.hcg.contexts.*;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.entities.enemyentities.Enemy;
import com.deco2800.hcg.entities.enemyentities.Hedgehog;
import com.deco2800.hcg.entities.npc_entities.NPC;
import com.deco2800.hcg.entities.npc_entities.QuestNPC;
import com.deco2800.hcg.entities.npc_entities.ShopNPC;
import com.deco2800.hcg.util.Effect;
import com.deco2800.hcg.util.Effects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;
import com.deco2800.hcg.inventory.Inventory;
import com.deco2800.hcg.inventory.PlayerEquipment;
import com.deco2800.hcg.inventory.WeightedInventory;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.WeaponItem;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.InputManager;
import com.deco2800.hcg.managers.PlayerInputManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.managers.SoundManager;
import com.deco2800.hcg.multiplayer.InputType;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.ConversationManager;
import com.deco2800.hcg.trading.Shop;
import com.deco2800.hcg.util.Box3D;
import com.deco2800.hcg.util.WorldUtil;
import com.deco2800.hcg.weapons.Weapon;
import com.deco2800.hcg.weapons.WeaponBuilder;
import com.deco2800.hcg.weapons.WeaponType;
import com.deco2800.hcg.worlds.World;
import com.deco2800.hcg.contexts.ShopMenuContext;
import com.deco2800.hcg.contexts.PerksSelectionScreen;
import com.deco2800.hcg.entities.bullets.Bullet;
import com.deco2800.hcg.entities.enemyentities.Squirrel;
import com.deco2800.hcg.entities.garden_entities.plants.Pot;

/**
 * Entity for the playable character.
 *
 * @author leggy
 */
public class Player extends Character implements Tickable {

	private static final Logger LOGGER = LoggerFactory.getLogger(Player.class);

	// string to contain filepath for character's HUD display
	private String displayImage;

	private GameManager gameManager;
	private SoundManager soundManager;
	private ContextManager contextManager;
	private PlayerInputManager playerInputManager;
	private PlayerManager playerManager;
	private ConversationManager conversationManager;

	private boolean collided;
	private boolean onExit = false;
	private boolean exitMessageDisplayed = false;
	private boolean sprinting;
	private boolean levelUp = false;
	private int xpThreshold = 200;
	private float lastSpeedX;
	private float lastSpeedY;

	// List containing skills that can be specialised in
	private List<String> SPECIALISED_SKILLS = Arrays.asList( "meleeSkill", "gunsSkill", "energyWeaponsSkill");

	//Specialised skills map
	private Map<String, Boolean> specialisedSkills;


	
    // Records the current frame number for player's move animation 
    private int spriteFrame; 

	// current tile name
	private String name = "";

	// 1 if player is moving, 0 if not
	private int move = -1;

	private Inventory inventory;
	private PlayerEquipment equippedItems;

	private int skillPoints;
	private HashMap<String, Boolean> movementDirection = new HashMap<>();

	private int id;

	/**
	 * Creates a new player at specified position.
	 *
	 * @param id
	 *            peer that controls player
	 * @param posX
	 *            beginning player X position
	 * @param posY
	 *            beginning player Y position
	 * @param posZ
	 *            beginning player Z position
	 */
	public Player(int id, float posX, float posY, float posZ) {
		super(posX, posY, posZ, 0.5f, 0.5f, 0.5f, true);

		//Set up specialised skills map
		this.specialisedSkills = new HashMap<String, Boolean>();
		for (String attribute: SPECIALISED_SKILLS) {
			specialisedSkills.put(attribute, false);
		}
		// Get necessary managers
		gameManager = GameManager.get();
		this.contextManager = (ContextManager) gameManager.getManager(ContextManager.class);

		this.playerManager = (PlayerManager) gameManager.getManager(PlayerManager.class);

		this.conversationManager = new ConversationManager();

		this.id = id;
		if (id == 0) {
			InputManager localInput = (InputManager) GameManager.get().getManager(InputManager.class);
			localInput.addKeyDownListener(this::handleLocalKeyDown);
			localInput.addKeyUpListener(this::handleLocalKeyUp);
			localInput.addTouchDownListener(this::handleLocalTouchDown);
			localInput.addTouchDraggedListener(this::handleLocalTouchDragged);
			localInput.addTouchUpListener(this::handleLocalTouchUp);
			localInput.addMouseMovedListener(this::handleLocalMouseMoved);
		}
		playerInputManager = (PlayerInputManager) GameManager.get().getManager(PlayerInputManager.class);
		playerInputManager.addKeyDownListener(id, this::handleKeyDown);
		playerInputManager.addKeyUpListener(id, this::handleKeyUp);
		playerInputManager.addTouchDownListener(id, this::handleTouchDown);
		playerInputManager.addTouchDraggedListener(id, this::handleTouchDragged);
		playerInputManager.addTouchUpListener(id, this::handleTouchUp);
		playerInputManager.addMouseMovedListener(id, this::handleMouseMoved);

		this.myEffects = new Effects(this);

		collided = false;
		sprinting = false;
		this.setTexture("hcg_character");
		this.soundManager = (SoundManager) GameManager.get().getManager(SoundManager.class);
		this.contextManager = (ContextManager) GameManager.get().getManager(ContextManager.class);
		
		this.spriteFrame = 0;

		// HUD display
		displayImage = "resources/ui/player_status_hud/player_display_one.png";

		// for slippery
		lastSpeedX = 0;
		lastSpeedY = 0;

		// for direction of movement
		movementDirection.put("left", false);
		movementDirection.put("right", false);
		movementDirection.put("up", false);
		movementDirection.put("down", false);

		// inventory
		inventory = new WeightedInventory(100);
		equippedItems = PlayerEquipment.getPlayerEquipment();

		// Add weapons to inventory
		Weapon shotgun = new WeaponBuilder().setWeaponType(WeaponType.SHOTGUN).setUser(this).setRadius(0.7).build();
		Weapon starfall = new WeaponBuilder().setWeaponType(WeaponType.STARFALL).setUser(this).setRadius(0.7).build();
		Weapon machinegun = new WeaponBuilder().setWeaponType(WeaponType.MACHINEGUN).setUser(this).setRadius(0.7)
				.build();
		equippedItems.addItem(new WeaponItem(shotgun, "Shotgun", 10));
		equippedItems.addItem(new WeaponItem(starfall, "Starfall", 10));
		equippedItems.addItem(new WeaponItem(machinegun, "Machine Gun", 10));
	}

	/**
	 * Creates a new player at specified position.
	 *
	 * @param posX
	 *            beginning player X position
	 * @param posY
	 *            beginning player Y position
	 * @param posZ
	 *            beginning player Z position
	 */
	public Player(float posX, float posY, float posZ) {
		// 0 is local player
		this(0, posX, posY, posZ);
	}

	/**
	 * Sends input when a touch input is made.
	 *
	 * @param screenX
	 *            the x position being clicked on the screen
	 * @param screenY
	 *            the y position being clicked on the screen
	 * @param pointer
	 *            <unknown>
	 * @param button
	 *            <unknown>
	 */
	private void handleLocalTouchDown(int screenX, int screenY, int pointer, int button) {
		playerInputManager.queueLocalAction(InputType.TOUCH_DOWN.ordinal(), screenX, screenY, pointer, button);
	}

	/**
	 * Sends input when a drag input is made.
	 *
	 * @param screenX
	 *            the x position on the screen that mouse is dragged to
	 * @param screenY
	 *            the y position on the screen that mouse is dragged to
	 * @param pointer
	 *            <unknown>
	 */
	private void handleLocalTouchDragged(int screenX, int screenY, int pointer) {
		playerInputManager.queueLocalAction(InputType.TOUCH_DRAGGED.ordinal(), screenX, screenY, pointer);
	}

	/**
	 * Sends input when a touch input is released.
	 *
	 * @param screenX
	 *            the x position mouse is being released on the screen
	 * @param screenY
	 *            the y position mouse is being released on the screen
	 * @param pointer
	 *            <unknown>
	 * @param button
	 *            <unknown>
	 */
	private void handleLocalTouchUp(int screenX, int screenY, int pointer, int button) {
		playerInputManager.queueLocalAction(InputType.TOUCH_UP.ordinal(), screenX, screenY, pointer, button);
	}

	/**
	 * Sends the processes involved when a mouse movement is made.
	 *
	 * @param screenX
	 *            the x position of mouse movement on the screen
	 * @param screenY
	 *            the y position of mouse movement on the screen
	 */
	private void handleLocalMouseMoved(int screenX, int screenY) {
		// FIXME: mouse inputs currently saturate the server
		handleMouseMoved(screenX, screenY);
	}

	/**
	 * Sends input when keys are pressed.
	 *
	 * @param keycode
	 *            the keycode of the key pressed
	 */
	private void handleLocalKeyDown(int keycode) {
		playerInputManager.queueLocalAction(InputType.KEY_DOWN.ordinal(), keycode);
	}

	/**
	 * Sends input when keys are released.
	 *
	 * @param keycode
	 *            the keycode of the key released
	 */
	private void handleLocalKeyUp(int keycode) {
		playerInputManager.queueLocalAction(InputType.KEY_UP.ordinal(), keycode);
	}

	/**
	 * sets the image to be displayed for the health and stamina display
	 *
	 * @param image
	 *            must be a valid internal file path
	 */

	public void setDisplayImage(String image) {
		displayImage = image;
	}

	/**
	 * returns the filepath to the image being used for character HUD display
	 *
	 * @returns string containing filepath for character image
	 */
	public String getDisplayImage() {
		return displayImage;
	}

	/**
	 * Handles the processes involved when a touch input is made.
	 *
	 * @param screenX
	 *            the x position being clicked on the screen
	 * @param screenY
	 *            the y position being clicked on the screen
	 * @param pointer
	 *            <unknown>
	 * @param button
	 *            <unknown>
	 */
	private void handleTouchDown(int screenX, int screenY, int pointer, int button) {
		if (this.getEquippedWeapon() != null) {
			Vector3 position = GameManager.get().screenToWorld(screenX, screenY);
			LOGGER.info("Position: " + position);
			this.getEquippedWeapon().updateAim(position);
			this.getEquippedWeapon().openFire();
		}
	}

	/**
	 * Handles the processes involved when a drag input is made.
	 *
	 * @param screenX
	 *            the x position on the screen that mouse is dragged to
	 * @param screenY
	 *            the y position on the screen that mouse is dragged to
	 * @param pointer
	 *            <unknown>
	 */
	private void handleTouchDragged(int screenX, int screenY, int pointer) {
		if (this.getEquippedWeapon() != null) {
			Vector3 position = GameManager.get().screenToWorld(screenX, screenY);
			this.getEquippedWeapon().updateAim(position);
			// TODO: remove
			this.getEquippedWeapon().updatePosition(position.x, position.y);
		}
	}

	/**
	 * Handles the processes involved when a touch input is released.
	 *
	 * @param screenX
	 *            the x position mouse is being released on the screen
	 * @param screenY
	 *            the y position mouse is being released on the screen
	 * @param pointer
	 *            <unknown>
	 * @param button
	 *            <unknown>
	 */
	private void handleTouchUp(int screenX, int screenY, int pointer, int button) {
		if (this.getEquippedWeapon() != null) {
			this.getEquippedWeapon().ceaseFire();
		}
	}

	/**
	 * Handles the processes involved when a mouse movement is made.
	 *
	 * @param screenX
	 *            the x position of mouse movement on the screen
	 * @param screenY
	 *            the y position of mouse movement on the screen
	 */
	private void handleMouseMoved(int screenX, int screenY) {
		if (this.getEquippedWeapon() != null) {
			Vector3 position = GameManager.get().screenToWorld(screenX, screenY);
			// TODO: remove
			this.getEquippedWeapon().updatePosition(position.x, position.y);
		}
	}

	/**
	 * Handles sound effect based on terrain type
	 *
	 * @param terrain
	 *            name of current tile
	 */
	private void handleSound(String terrain) {
		if (terrain != null && move == 1) {
			// if player is moving
			if (!terrain.equals(name)) {
				// if player moved to a different tile
				if (!name.equals("")) {
					// stop old sound effect if there were
					soundStop(name);
				}
				// play new sound effect
				soundPlay(terrain);
			}
			name = terrain;
		} else if (move == 0) {
			// if player not moving, terminate sound effect && empty tile name
			soundStop(name);
			name = "";
		}
	}

	/**
	 * Checks player's proximity to NPCs to see if an interaction can be
	 * initiated.
	 */
	private void checkForInteraction() {
		LOGGER.info(this + " attempted to initiate an interaction with a NPC");
		Box3D interactionRadius = getBox3D();
		List<AbstractEntity> entities = GameManager.get().getWorld().getEntities();
		for (AbstractEntity entity : entities) {
			if (!this.equals(entity) && (interactionRadius.distance(entity.getBox3D()) < 3.0f)) {
				if (entity instanceof NPC) {
					LOGGER.info(this + " initiated a interaction with " + entity);
					this.NPCInteraction(entity);
				}
			}
		}
	}

	/**
	 * Handles the interaction between player and a NPC
	 *
	 * @param npc
	 *            the NPC (as an entity) that you wish to interact with
	 */
	private void NPCInteraction(AbstractEntity npc) {
		if (npc instanceof QuestNPC) {
			((QuestNPC) npc).interact();
			LOGGER.info("Quest NPC Interaction Started");
			;
		} else if (npc instanceof ShopNPC) {
			LOGGER.info("Shop NPC Interaction Started");
			((ShopNPC) npc).interact();
		} else {
			LOGGER.info("Other NPC Interaction Started");
		}
	}

	/**
	 * On Tick handler
	 *
	 * @param gameTickCount
	 *            Current game tick
	 */
	@Override
	public void onTick(long gameTickCount) {
		float oldPosX = this.getPosX();
		float oldPosY = this.getPosY();

		// Apply any active effects
		myEffects.apply();

		// Center the camera on the player
		updateCamera();

		// update the players stamina
		handleStamina();

		// set speed is the multiplier due to the ground
		float speed = 1.0f;
		collided = false;
		float slippery = 0;

		// current world and layer
		TiledMapTileLayer layer;
		World world = GameManager.get().getWorld();

		Box3D newPos = getBox3D();

		// removes the exit message if the player exits the exit zone
		if (!onExit && exitMessageDisplayed) {
			PlayContext play = (PlayContext) contextManager.currentContext();
			play.removeExitWindow();
			exitMessageDisplayed = false;
		}

		// get speed of current tile. this is done before checking if a tile
		// exists so a slow down tile next to the edge wouldn't cause problems.
		if (world.getTiledMapTileLayerAtPos((int) oldPosY, (int) oldPosX) == null) {
			collided = true;
		} else {
			// set the layer, and get the speed of the tile on the layer. Also
			// name for logging.
			layer = world.getTiledMapTileLayerAtPos((int) oldPosY, (int) oldPosX);
			speed = Float.parseFloat((String) layer.getProperties().get("speed"));

			// see if current tile is a Gateway
			if (layer.getProperties().get("playerX") != null && layer.getProperties().get("playerY") != null) {
				oldPosX = Float.parseFloat((String) layer.getProperties().get("playerX"));
				oldPosY = Float.parseFloat((String) layer.getProperties().get("playerY"));
			}

			if (layer.getProperties().get("name", String.class) != null) {
				String newName = layer.getProperties().get("name", String.class);
				if (move == 1) {
					if (newName.equals("exit")) {
						if (!onExit) {
							PlayContext play = (PlayContext) contextManager.currentContext();
							play.addExitWindow();
							onExit = true;
							exitMessageDisplayed = true;
						}
					} else {
						onExit = false;
					}
				}
				// handle sound effects
				handleSound(newName);

				// handle terrain effect
				handleTerrain(newName);
			}

			// if current tile is a gateway, load new map
			if (layer.getProperties().get("newMap") != null) {
				GameManager.get().setWorld(new World((String) layer.getProperties().get("newMap")));
				playerManager.spawnPlayers();
				this.setPosition(oldPosX, oldPosY, 1);
			}

			// see if current tile is slippery. Save the slippery value if it is
			if (layer.getProperties().get("slippery") != null) {
				slippery = Float.parseFloat((String) layer.getProperties().get("slippery"));
			}

			// -1 for none, 0 for enemy, 1 for both, 2 for player
			int damagetype = -1;

			if (layer.getProperties().get("damagetype") != null) {
				damagetype = Integer.parseInt((String) layer.getProperties().get("damagetype"));
			}

			// damage player
			if (layer.getProperties().get("damage") != null && damagetype > 0) {
				//this.takeDamage(Integer.parseInt((String) layer.getProperties().get("damage")));
				myEffects.addEffect(new Effect("Damage", 10, Integer.parseInt((String) layer.getProperties().get("damage")), 1, 0, 1, 0));
			}
			// log
			LOGGER.info(this + " moving on terrain" + name + " withspeed multiplier of " + speed);
		}
		// get our updated position based on the speed and slippery of the tile
		handleMovement(newPos, speed, slippery);

		// now check if a tile exists at this new position
		if (world.getTiledMapTileLayerAtPos((int) (newPos.getY()), (int) (newPos.getX())) == null) {
			collided = true;
		}
		List<AbstractEntity> entities = GameManager.get().getWorld().getEntities();
		for (AbstractEntity entity : entities) {
			if (!this.equals(entity) && !(entity instanceof Squirrel) && !(entity instanceof Hedgehog)
					&& newPos.overlaps(entity.getBox3D()) && !(entity instanceof Bullet)
							&& !(entity instanceof Weapon) && !(entity instanceof Corpse)) {
				LOGGER.info(this + " colliding with " + entity);
				collided = true;
			}
		}
		if (!collided) {
			this.setPosition(newPos.getX(), newPos.getY(), 1);
		}

		checkXp();
		this.checkDeath();
	}

	/**
	 * Handles the movement of the player in the onTick method based on the
	 * slippery and speed factors of the tiles.
	 *
	 * @param newPos
	 *            position player will move to if it is empty
	 * @param speed
	 *            speed factor of the current tile
	 * @param slippery
	 *            slippery factor of the current tile
	 */
	private void handleMovement(Box3D newPos, float speed, float slippery) {
		// handle slippery movement
		float sprintMultiplier = 1;
		if (sprinting) {
			sprintMultiplier = 2f;
		}
		if (Math.abs(slippery) > 0.05f) {
			// first factor is for slowing down, second is for speeding up
			float slipperyFactor = slippery * 0.005f;
			float slipperyFactor2 = slippery * 0.06f;

			// created helper function to avoid duplicate code
			lastSpeedX = slipperySpeedHelper(speedX * sprintMultiplier, lastSpeedX, speed, slipperyFactor,
					slipperyFactor2);
			lastSpeedY = slipperySpeedHelper(speedY * sprintMultiplier, lastSpeedY, speed, slipperyFactor,
					slipperyFactor2);
		} else {
			// non slippery movement
			lastSpeedX = 0;
			lastSpeedY = 0;

			// store our last speed
			if (speedX != 0) {
				lastSpeedX = speedX * speed * sprintMultiplier;
			}
			if (speedY != 0) {
				lastSpeedY = speedY * speed * sprintMultiplier;
			}

		}
		// change box coords
		newPos.setX(this.getPosX() + lastSpeedX);
		newPos.setY(this.getPosY() + lastSpeedY);
	}

	/**
	 * handle terrain effects on player
	 */
	private void handleTerrain(String terrain) {
		switch (terrain) {
		case "water-deep":
			this.setTexture("hcg_character_swim");
			break;
		case "water-shallow":
			this.setTexture("hcg_character_sink");
			break;
		default:
			updateSprite(this.direction);
			break;
		}
	}

	/**
	 * Initialize a new player. Will be used after the user has created their
	 * character in the character creation screen
	 */
	public void initialiseNewPlayer(int strength, int vitality, int agility, int charisma, int intellect,
			int meleeSkill, int gunsSkill, int energyWeaponsSkill, String name) {
		setAttributes(strength, vitality, agility, charisma, intellect);
		setSkills(meleeSkill, gunsSkill, energyWeaponsSkill);
		setName(name);
		healthMax = 50 * vitality;
		healthCur = healthMax;
		staminaMax = 50 * agility;
		staminaCur = staminaMax;
		skillPoints = 4 + 2 * intellect;
	}

	public void setSpecialisedSkills(Map specialisedSkills) {
		this.specialisedSkills = specialisedSkills;
	}

	/**
	 * Checks if the player is dead and ends the game if true.
	 */
	private void checkDeath() {
		if (healthCur <= 0) {
			this.contextManager.pushContext(new DeathContext());
			healthCur = healthMax;
		}
	}

	/**
	 * Checks if the player's xp has reached the amount of xp required for
	 * levelling up
	 */
	private void checkXp() {
		if (xp >= xpThreshold) {
			//TODO: You have levelled up pop up
			levelUp = true;
		}
	}

	/**
	 * Increases the player's level by one, increases the xpThreshold, increases
	 * health and stamina based on player agility and vitality
	 */
	private void levelUp() {
		xpThreshold *= 1.5;
		level++;

		// Increase health by vitality points
		int vitality = attributes.get("vitality");
		healthMax += vitality;
		healthCur += vitality;

		// Increase stamina by agility points
		int agility = attributes.get("agility");
		staminaMax += agility;
		staminaCur += agility;

		skillPoints = 4 + attributes.get("intellect") * 2;
		// TODO: enter level up screen
	}


	/**
	 * Increases the xp of the player by the given amount
	 *
	 * @param amount
	 *            the amount of xp to gain
	 */
	public void gainXp(int amount) {
		this.xp += amount;
		checkXp();
	}

//	/**
//	 * Decrease the current health of the player by the given amount
//	 *
//	 * @param amount
//	 *            the amount of health to lose
//	 */
//	public void takeDamage(int amount) {
//		// if user is taking damage
//		if (amount > 0) {
//			this.healthCur = Math.max(this.healthCur - amount, 0);
//			return;
//		}
//		// otherwise user is being healed
//		this.healthCur = Math.min(this.healthCur - amount, this.healthMax);
//
//	}

	/**
	 * Stamina determines how the player can use additional movement mechanics
	 * when sprinting or dodge rolling, the player loses stamina that they
	 * recover over time.
	 *
	 */
	private void handleStamina() {
		// conditionals to handle players sprint
		if (sprinting && move == 1) {
			/*
			 * if the player is sprinting they will be exerting themselves and
			 * running out of stamina, hence it is drained on tick. Otherwise,
			 * they will be recovering, gaining stamina back.
			 */
			staminaCur -= 5;
		} else {
			if (staminaCur < staminaMax) {
				// recovering
				staminaCur += 1;
			}
			if (staminaCur > staminaMax) {
				// over recovered, so revert to max.
				staminaCur = staminaMax;
			}
		}
		if (staminaCur <= 1 && sprinting) {
			// if the player is out of stamina, return them to the normal
			// movement
			// speed and set their sprinting conditional to false.
			sprinting = false;
		}
	}

	/**
	 * Handle movement when wasd keys are pressed down. As well as other
	 * possible actions on key press. Such as NPC interaction.
	 */
	private void handleKeyDown(int keycode) {

		switch (keycode) {
		case Input.Keys.T:
			this.getEquippedWeapon().switchBullet();
			break;
		case Input.Keys.P:
			this.contextManager.pushContext(new PerksSelectionScreen());
			break;
		case Input.Keys.C:
			if (levelUp) {
				levelUp = false;
				levelUp();
				this.contextManager.pushContext(new LevelUpContext());
			} else {
				this.contextManager.pushContext(new CharacterStatsContext());
			}
			break;
		case Input.Keys.SHIFT_LEFT:
			if (staminaCur > 0) {
				sprinting = true;
			}
			break;
		case Input.Keys.W:
			movementDirection.put("up", true);
			break;
		case Input.Keys.S:
			movementDirection.put("down", true);
			break;
		case Input.Keys.A:
			movementDirection.put("left", true);
			break;
		case Input.Keys.D:
			movementDirection.put("right", true);
			break;
		case Input.Keys.E:
			checkForInteraction();
			break;
		case Input.Keys.R:
			if (this.getEquippedWeapon() != null) {
				GameManager.get().getWorld().removeEntity(this.getEquippedWeapon());
			}
			this.equippedItems.cycleEquippedSlot();
			if (this.getEquippedWeapon() != null) {
				GameManager.get().getWorld().addEntity(this.getEquippedWeapon());
			}
			break;
		case Input.Keys.ESCAPE:
			contextManager.popContext();
			break;
		case Input.Keys.I:
			// Display Inventory
			System.out.println("Access player inventory");
			contextManager.pushContext(new PlayerInventoryContext(this));
			break;
		case Input.Keys.U:
			Optional<AbstractEntity> closest = WorldUtil.closestEntityToPosition(this.getPosX(), this.getPosY(), 
					1.5f, Pot.class);
			if(closest.isPresent()) {
				Pot pot = (Pot)closest.get();
				pot.unlock();
			}
		default:
			break;
		}
		handleDirectionInput();
		handleNoInput();
	}

	/**
	 * Handle movement when wasd keys are released
	 */
	private void handleKeyUp(int keycode) {
		switch (keycode) {
		case Input.Keys.SHIFT_LEFT:
			sprinting = false;
			movementSpeed = movementSpeedNorm;
			break;
		case Input.Keys.W:
			movementDirection.put("up", false);
			break;
		case Input.Keys.S:
			movementDirection.put("down", false);
			break;
		case Input.Keys.A:
			movementDirection.put("left", false);
			break;
		case Input.Keys.D:
			movementDirection.put("right", false);
			break;
		default:
			break;
		}
		handleDirectionInput();
		handleNoInput();
	}

	/**
	 * Sets the player's movement speed based on the combination of keys being
	 * pressed, and Set move to true
	 */
	private void handleDirectionInput() {
		float diagonalSpeed = (float) Math.sqrt(2 * (movementSpeed * movementSpeed)) / 2;
		if (movementDirection.get("up") && movementDirection.get("right")) {
			speedX = movementSpeed;
			speedY = 0;
			move = 1;
			this.direction = 2;
		} else if (movementDirection.get("up") && movementDirection.get("left")) {
			speedY = -movementSpeed;
			speedX = 0;
			move = 1;
			this.direction = 0;
		} else if (movementDirection.get("down") && movementDirection.get("right")) {
			speedY = movementSpeed;
			speedX = 0;
			move = 1;
			this.direction = 4;
		} else if (movementDirection.get("down") && movementDirection.get("left")) {
			speedX = -movementSpeed;
			speedY = 0;
			move = 1;
			this.direction = 6;
		} else if (movementDirection.get("up") && movementDirection.get("down")) {
			speedX = 0;
			speedY = 0;
			move = 1;
		} else if (movementDirection.get("left") && movementDirection.get("right")) {
			speedX = 0;
			speedY = 0;
			move = 1;
		} else if (movementDirection.get("up")) {
			speedY = -diagonalSpeed;
			speedX = diagonalSpeed;
			move = 1;
			this.direction = 1;
		} else if (movementDirection.get("down")) {
			speedY = diagonalSpeed;
			speedX = -diagonalSpeed;
			move = 1;
			this.direction = 5;
		} else if (movementDirection.get("left")) {
			speedX = -diagonalSpeed;
			speedY = -diagonalSpeed;
			move = 1;
			this.direction = 7;
		} else if (movementDirection.get("right")) {
			speedX = diagonalSpeed;
			speedY = diagonalSpeed;
			move = 1;
			this.direction = 3;
		}
	}

	/**
	 * Updates the player's sprite based on its direction.
	 * 
	 * @param direction 
     *            Direction the player is facing. Integer between 0 and 3.
	 */
	private void updateSprite(int direction) {
	    StringBuilder spriteName = new StringBuilder("player_"); 
        spriteName.append(direction);
        if (this.speedX == 0 && this.speedY == 0) { 
            // Player is not moving 
            spriteName.append("_stand"); 
        } else { 
            // Player is moving 
            if (this.spriteFrame == 0 || this.spriteFrame == 2) { 
                spriteName.append("_stand"); 
            } else if (this.spriteFrame == 1) { 
                spriteName.append("_move1"); 
            } else if (this.spriteFrame == 3) { 
                spriteName.append("_move2");       
            } 
            this.spriteFrame = ++this.spriteFrame % 4; 
        }
        this.setTexture(spriteName.toString());
	}

	/**
	 * Sets the player's movement speed to zero and Set move to false if no keys
	 * are pressed.
	 */
	private void handleNoInput() {
		if (!movementDirection.get("up") && !movementDirection.get("down") && !movementDirection.get("left")
				&& !movementDirection.get("right")) {
			speedX = 0;
			speedY = 0;
			move = 0;
		}
	}

	/**
	 * Does logic for slowly increasing players speed and also slowly decreasing
	 * players speed. Used to avoid repetitive code.
	 *
	 * @param speed
	 *            Input speed of player
	 * @param tileSpeed
	 *            Speed multiplier from tile
	 * @param slipperyFactor
	 *            Scalar for slowing down player
	 * @param slipperyFactor2
	 *            Scalar for speeding up player
	 * @return New lastSpeed
	 */
	private float slipperySpeedHelper(float speed, float lastSpeed, float tileSpeed, float slipperyFactor,
			float slipperyFactor2) {
		// speed up user in X dirn
		float lastSpeedNew;
		if (speed > 0) {
			lastSpeedNew = Math.min(lastSpeed + speed * tileSpeed * slipperyFactor2, speed * tileSpeed);
		} else if (speed < 0) {
			lastSpeedNew = Math.max(lastSpeed + speed * tileSpeed * slipperyFactor2, speed * tileSpeed);
		} else {
			// slow down user
			if (Math.abs(lastSpeed) > slipperyFactor) {
				lastSpeedNew = lastSpeed - Math.signum(lastSpeed) * slipperyFactor;
			} else {
				// ensure that speed eventually goes to zero
				lastSpeedNew = 0;
			}
		}
		return lastSpeedNew;
	}

	/**
	 * Updates the game camera so that it is centered on the player
	 */
	private void updateCamera() {
		// don't follow co-op players
		if (id > 0) {
			return;
		}
		int worldLength = GameManager.get().getWorld().getLength();
		int worldWidth = GameManager.get().getWorld().getWidth();
		int tileWidth = (int) GameManager.get().getWorld().getMap().getProperties().get("tilewidth");
		int tileHeight = (int) GameManager.get().getWorld().getMap().getProperties().get("tileheight");
		float baseX = tileWidth * (worldWidth / 2.0f - 0.5f);
		float baseY = -tileHeight / 2 * worldLength + tileHeight / 2f;

		float cartX = this.getPosX();
		float cartY = (worldWidth - 1) - this.getPosY();

		float isoX = baseX + ((cartX - cartY) / 2.0f * tileWidth);
		float isoY = baseY + ((cartX + cartY) / 2.0f) * tileHeight;

		if (GameManager.get().getCamera() != null) {
			GameManager.get().getCamera().position.x += (isoX - GameManager.get().getCamera().position.x) * .09f;
			GameManager.get().getCamera().position.y += (isoY - GameManager.get().getCamera().position.y) * .09f;
			GameManager.get().getCamera().update();
		}
	}

	/**
	 * play corresponding sound effect on terrain
	 */
	private void soundPlay(String name) {
		if (soundManager != null) {
			soundManager.loopSound(name);
		}
	}

	/**
	 * stop playing sound effect
	 */
	private void soundStop(String name) {
		if (soundManager != null) {
			soundManager.stopSound(name);
		}
	}

	@Override
	public String toString() {
		return "The player";
	}

	public Inventory getInventory() {
		return inventory;
	}

	public PlayerEquipment getEquippedItems() {
		return equippedItems;
	}

	public boolean addItemToInventory(Item item) {
		return inventory.addItem(item);
	}

	@Override
	public Item getCurrentEquippedItem() {
		return this.equippedItems.getCurrentEquippedItem();
	}

	/**
	 * Returns the player's currently equipped weapon
	 *
	 * @return the player's currently equipped weapon
	 */
	public Weapon getEquippedWeapon() {
		Item item = this.getCurrentEquippedItem();
		if (item != null && item instanceof WeaponItem) {
			return ((WeaponItem) item).getWeapon();
		}
		return null;
	}

	public int getXpThreshold() {
		return xpThreshold;
	}

	public Map<String, Boolean> getSpecialisedSkills() {
		return specialisedSkills;
	}

	public List<String> getSpecialisedSkillsList() {
		return SPECIALISED_SKILLS;
	}
}
