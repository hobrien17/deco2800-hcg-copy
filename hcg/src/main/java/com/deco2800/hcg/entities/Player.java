package com.deco2800.hcg.entities;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.deco2800.hcg.inventory.Inventory;
import com.deco2800.hcg.inventory.PlayerEquipment;
import com.deco2800.hcg.inventory.WeightedInventory;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.WeaponItem;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.InputManager;
import com.deco2800.hcg.managers.PeerInputManager;
import com.deco2800.hcg.managers.SoundManager;
import com.deco2800.hcg.multiplayer.InputType;
import com.deco2800.hcg.multiplayer.NetworkState;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.trading.GeneralShop;
import com.deco2800.hcg.trading.Shop;
import com.deco2800.hcg.util.Box3D;
import com.deco2800.hcg.weapons.Weapon;
import com.deco2800.hcg.weapons.WeaponBuilder;
import com.deco2800.hcg.weapons.WeaponType;
import com.deco2800.hcg.worlds.AbstractWorld;
import com.deco2800.hcg.contexts.ShopMenuContext;

/**
 * Entity for the playable character.
 *
 * @author leggy
 */
public class Player extends Character implements Tickable {

	private static final Logger LOGGER = LoggerFactory.getLogger(Player.class);

	private SoundManager soundManager;
	private ContextManager contextManager;

	private boolean collided;
	private int xpThreshold = 200;
	private float lastSpeedX;
	private float lastSpeedY;
	private boolean sprinting;

	// current tile name
	private String name = "";

	// 1 if player is moving, 0 if not
	private int move = -1;

	private Inventory inventory;
	private PlayerEquipment equippedItems;

	private int skillPoints;
	private HashMap<String, Boolean> movementDirection = new HashMap<>();

	// private Weapon equippedWeapon;
	
	private int peerId;

	/**
	 * Creates a new player at specified position.
	 * 
	 * @param peerId
	 *            peer that controls player
	 * @param posX
	 *            beginning player X position
	 * @param posY
	 *            beginning player Y position
	 * @param posZ
	 *            beginning player Z position
	 */
	public Player(int peerId, float posX, float posY, float posZ) {
		super(posX, posY, posZ, 0.5f, 0.5f, 0.5f, true);

		// Get necessary managers
		GameManager gameManager = GameManager.get();
		this.contextManager = (ContextManager) gameManager.getManager(ContextManager.class);

		this.peerId = peerId;
		if (peerId == -1) {
			InputManager input = (InputManager) GameManager.get().getManager(InputManager.class);
			input.addKeyDownListener(this::handleKeyDown);
			input.addKeyUpListener(this::handleKeyUp);
			input.addTouchDownListener(this::handleTouchDown);
			input.addTouchDraggedListener(this::handleTouchDragged);
			input.addTouchUpListener(this::handleTouchUp);
			input.addMouseMovedListener(this::handleMouseMoved);
		} else {
			PeerInputManager input = (PeerInputManager) GameManager.get().getManager(PeerInputManager.class);
			input.addKeyDownListener(peerId, this::handleKeyDown);
			input.addKeyUpListener(peerId, this::handleKeyUp);
			input.addTouchDownListener(peerId, this::handleTouchDown);
			input.addTouchDraggedListener(peerId, this::handleTouchDragged);
			input.addTouchUpListener(peerId, this::handleTouchUp);
			input.addMouseMovedListener(peerId, this::handleMouseMoved);
		}

		collided = false;
		sprinting = false;
		this.setTexture("hcg_character");
		this.soundManager = (SoundManager) GameManager.get().getManager(SoundManager.class);
		this.contextManager = (ContextManager) GameManager.get().getManager(ContextManager.class);

		// for slippery
		lastSpeedX = 0;
		lastSpeedY = 0;

		// Set equipped weapon and enter game world
		// GameManager.get().getWorld().addEntity(weaponToEquip);
		// GameManager.get().getWorld().removeEntity(weaponToEquip);

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
		// -1 is local player
		this(-1, posX, posY, posZ);
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
		if (NetworkState.isInitialised() && peerId == -1) {
			NetworkState.sendInputMessage(InputType.TOUCH_DOWN.ordinal(), screenX, screenY, pointer, button);
		}
		
		if (this.getEquippedWeapon() != null) {
			this.getEquippedWeapon().updateAim(screenX, screenY);
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
		if (NetworkState.isInitialised() && peerId == -1) {
			NetworkState.sendInputMessage(InputType.TOUCH_DRAGGED.ordinal(), screenX, screenY, pointer);
		}
		
		if (this.getEquippedWeapon() != null) {
			this.getEquippedWeapon().updatePosition(screenX, screenY);
			this.getEquippedWeapon().updateAim(screenX, screenY);
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
		if (NetworkState.isInitialised() && peerId == -1) {
			NetworkState.sendInputMessage(InputType.TOUCH_UP.ordinal(), screenX, screenY, pointer, button);
		}
		
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
		if (NetworkState.isInitialised() && peerId == -1) {
			NetworkState.sendInputMessage(InputType.MOUSE_MOVED.ordinal(), screenX, screenY);
		}
		
		if (this.getEquippedWeapon() != null) {
			this.getEquippedWeapon().updatePosition(screenX, screenY);
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
			if (!this.equals(entity) & (interactionRadius.distance(entity.getBox3D()) < 3.0f)) {
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
	/**
	 * Handles the interaction between player and a NPC
	 * 
	 * @param npc
	 *            the NPC (as an entity) that you wish to interact with
	 */
	private void NPCInteraction(AbstractEntity npc) {
		if (((NPC) npc).getNPCType() == NPC.Type.Shop) {

			LOGGER.info("Shop NPC Interaction Started");
			contextManager.pushContext(new ShopMenuContext());
			Shop shop = new GeneralShop();
			shop.open(0, this);

		} else if (((NPC) npc).getNPCType() == NPC.Type.Quest) {
			LOGGER.info("Quest NPC Interaction Started");

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
	    // TODO: Refactor this monstrosity
		float newPosX = this.getPosX();
		float newPosY = this.getPosY();

		// Center the camera on the player
		updateCamera();

		// set speed is the multiplier due to the ground
		float speed = 1.0f;
		collided = false;
		float slippery = 0;

		// current world and layer
		TiledMapTileLayer layer;
		AbstractWorld world = GameManager.get().getWorld();

		// get speed of current tile. this is done before checking if a tile
		// exists so a slow down tile next to the edge wouldn't cause problems.
		if (world.getTiledMapTileLayerAtPos((int) newPosY, (int) newPosX) == null) {
			collided = true;
		} else {
			// set the layer, and get the speed of the tile on the layer. Also
			// name for logging.
			layer = world.getTiledMapTileLayerAtPos((int) newPosY, (int) newPosX);
			speed = Float.parseFloat((String) layer.getProperties().get("speed"));
			String newname = layer.getProperties().get("name", String.class);

			// if player is moving
			if (newname != null && move == 1) {

				// if moved to a different tile and not a first move, switch
				// sound effect
				if (!newname.equals(name) && !name.equals("")) {
					soundStop(name);
					soundPlay(newname);
				} else if (name.equals("")) {
					// if it is a first move, play sound effect
					soundPlay(newname);
				}
				name = newname;
				// if player in deep water, set it swim
				if (name.equals("water-deep")) {
					this.setTexture("hcg_character_swim");
				} else {
					this.setTexture("hcg_character");
				}
			} else if (move == 0) {
				// if player stop moving, terminate sound effect
				// and initialize tile name
				soundStop(name);
				name = "";

			}

			// see if current tile is slippery. Save the slippery value if it is
			if (layer.getProperties().get("slippery") != null) {
				// this works but .get("slippery", Float.class) doesn't?
				slippery = Float.parseFloat((String) layer.getProperties().get("slippery"));
			}

			// -1 for none, 0 for enemy, 1 for both, 2 for player
			int damagetype = -1;

			if (layer.getProperties().get("damagetype") != null) {
				damagetype = Integer.parseInt((String) layer.getProperties().get("damagetype"));
			}

			// damage player
			if (layer.getProperties().get("damage") != null && damagetype > 0) {
				this.takeDamage(Integer.parseInt((String) layer.getProperties().get("damage")));

			}

			// log
			LOGGER.info(this + " moving on terrain" + name + " withspeed multiplier of " + speed);

		}

		// handle slippery movement (fixed for floating point)
		if (Math.abs(slippery) > 0.05f)

		{

			// first factor is for slowing down, second is for speeding up
			float slipperyFactor = slippery * 0.005f;
			float slipperyFactor2 = slippery * 0.06f;

			// created helper function to avoid duplicate code
			lastSpeedX = slipperySpeedHelper(speedX, lastSpeedX, speed, slipperyFactor, slipperyFactor2);
			lastSpeedY = slipperySpeedHelper(speedY, lastSpeedY, speed, slipperyFactor, slipperyFactor2);

		} else {
			// non slippery movement
			lastSpeedX = 0;
			lastSpeedY = 0;

			// store our last speed
			if (speedX != 0) {
				lastSpeedX = speedX * speed;
			}
			if (speedY != 0) {
				lastSpeedY = speedY * speed;
			}

		}

		// add this speed to our current position
		newPosX += lastSpeedX;
		newPosY += lastSpeedY;

		// now check if a tile exists at this new position
		if (world.getTiledMapTileLayerAtPos((int) (newPosY), (int) (newPosX)) == null) {
			collided = true;
		}

		Box3D newPos = getBox3D();
		newPos.setX(newPosX);
		newPos.setY(newPosY);

		List<AbstractEntity> entities = GameManager.get().getWorld().getEntities();
		for (AbstractEntity entity : entities) {

			if (!this.equals(entity) && !(entity instanceof Squirrel) && newPos.overlaps(entity.getBox3D())
					&& !(entity instanceof Bullet) && !(entity instanceof Weapon)) {
				LOGGER.info(this + " colliding with " + entity);
				collided = true;

			}
		}

		if (!collided) {
			this.setPosition(newPosX, newPosY, 1);
		}

	}

	/**
	 * Initialize a new player. Will be used after the user has created their
	 * character in the character creation screen
	 */
	public void initialiseNewPlayer(int strength, int vitality, int agility, int charisma, int intellect,
			int meleeSkill) {
		setAttributes(strength, vitality, agility, charisma, intellect);
		setSkills(meleeSkill);
		healthMax = 4 * vitality;
		healthCur = healthMax;
		staminaMax = 4 * agility;
		staminaCur = staminaMax;
	}

	/**
	 * Checks if the player's xp has reached the amount of xp required for
	 * levelling up
	 */
	private void checkXp() {
		if (xp >= xpThreshold) {
			levelUp();
		}
	}

	/**
	 * Increases the player's level by one, increases the xpThreshold, increases
	 * health and stamina based on player agility and vitality
	 */
	private void levelUp() {
		xpThreshold *= 1.3;
		level++;
		
		// Increase health by vitality points
		int vitality = attributes.get("vitality");
		healthMax += vitality;
		healthCur += vitality;
		
		// Increase stamina by agility points
		int agility = attributes.get("agility");
		staminaMax += agility;
		staminaCur += agility;
		
		skillPoints = 4 + attributes.get("intellect");
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

	/**
	 * Decrease the current health of the player by the given amount
	 * 
	 * @param amount
	 *            the amount of health to lose
	 */
	public void takeDamage(int amount) {

		if (amount < this.healthCur) {
			this.healthCur -= amount;
		} else {
			this.healthCur = 0;
		}
	}

	/**
	 * Handle movement when wasd keys are pressed down. As well as other
	 * possible actions on key press. Such as NPC interaction.
	 */
	private void handleKeyDown(int keycode) {
		if (NetworkState.isInitialised() && peerId == -1) {
			NetworkState.sendInputMessage(InputType.KEY_DOWN.ordinal(), keycode);
		}
		
		if (sprinting) {
		    // TODO: Should this be in OnTick?
			this.setStaminaCur(this.getStaminaCur() - 10);
		} else {
		    this.setStaminaCur(this.getStaminaCur() + 10);
		}
		switch (keycode) {
		// case Input.Keys.P:
		// this.contextManager.pushContext(new PerksSelectionScreen());
		case Input.Keys.SHIFT_LEFT:
			if (staminaCur > 0) {
                sprinting = true;
				setMovementSpeed(getMovementSpeed() * 3);
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
		default:
			break;
		}
		if (staminaCur <= 0) {
			sprinting = false;
			// TODO: I don't think this works as intended
			setMovementSpeed(movementSpeedNorm);
		}
		handleDirectionInput();
		handleNoInput();
	}

	/**
	 * Handle movement when wasd keys are released
	 */
	private void handleKeyUp(int keycode) {
		if (NetworkState.isInitialised() && peerId == -1) {
			NetworkState.sendInputMessage(InputType.KEY_UP.ordinal(), keycode);
		}
		
		switch (keycode) {
		case Input.Keys.SHIFT_LEFT:
			sprinting = false;
			setMovementSpeed(movementSpeedNorm);
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
		} else if (movementDirection.get("up") && movementDirection.get("left")) {
			speedY = -movementSpeed;
			speedX = 0;
			move = 1;
		} else if (movementDirection.get("down") && movementDirection.get("right")) {
			speedY = movementSpeed;
			speedX = 0;
			move = 1;
		} else if (movementDirection.get("down") && movementDirection.get("left")) {
			speedX = -movementSpeed;
			speedY = 0;
			move = 1;
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
		} else if (movementDirection.get("down")) {
			speedY = diagonalSpeed;
			speedX = -diagonalSpeed;
			move = 1;
		} else if (movementDirection.get("left")) {
			speedX = -diagonalSpeed;
			speedY = -diagonalSpeed;
			move = 1;
		} else if (movementDirection.get("right")) {
			speedX = diagonalSpeed;
			speedY = diagonalSpeed;
			move = 1;
		}
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
		if (peerId >= 0) {
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
			GameManager.get().getCamera().position.x = isoX;
			GameManager.get().getCamera().position.y = isoY;
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
}
