package com.deco2800.hcg.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;
import com.deco2800.hcg.inventory.FixedSizeInventory;
import com.deco2800.hcg.inventory.Inventory;
import com.deco2800.hcg.inventory.WeightedInventory;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.InputManager;
import com.deco2800.hcg.managers.SoundManager;
import com.deco2800.hcg.managers.TimeManager;
import com.deco2800.hcg.util.Box3D;
import com.deco2800.hcg.worlds.AbstractWorld;

import java.util.HashMap;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Entity for the playable character.
 *
 * @author leggy
 */
public class Player extends Character implements Tickable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Player.class);

    private SoundManager soundManager;
    private TimeManager timeManager;

    private boolean collided;
    private int xpThreshold = 200;
    private float lastSpeedX;
    private float lastSpeedY;
    private int oldTime = -1;
    private int newTime = -1;
    
    private Inventory inventory;

    private int skillPoints;
    private HashMap<String, Boolean> movementDirection = new HashMap<>();
    
    private Weapon equippedWeapon;
    
    private Weapon peashooter = new Weapon(getPosX(),
            getPosY(), getPosZ(), WeaponType.MACHINEGUN, this);
    private Weapon shotgun = new Weapon(getPosX(),
            getPosY(), getPosZ(), WeaponType.SHOTGUN, this);
    private Weapon stargun = new Weapon(getPosX(),
            getPosY(), getPosZ(), WeaponType.STARFALL, this);

    /**
     * Creates a new player at specified position.
     * 
     * @param posX beginning player X position
     * @param posY beginning player Y position
     * @param posZ beginning player Z position
     */
    public Player(float posX, float posY, float posZ) {

        super(posX, posY, posZ, 0.5f, 0.5f, 0.5f, true);

        InputManager input = (InputManager) GameManager.get()
                .getManager(InputManager.class);

        input.addKeyDownListener(this::handleKeyDown);
        input.addKeyUpListener(this::handleKeyUp);
        input.addTouchDownListener(this::handleTouchDown);
        input.addTouchDraggedListener(this::handleTouchDragged);
        input.addTouchUpListener(this::handleTouchUp);
        input.addMouseMovedListener(this::handleMouseMoved);

        collided = false;
        this.setTexture("hcg_character");
        this.soundManager = (SoundManager) GameManager.get()
                .getManager(SoundManager.class);
        this.timeManager = (TimeManager) GameManager.get()
                .getManager(TimeManager.class);

        // for slippery
        lastSpeedX = 0;
        lastSpeedY = 0;

        // Set equipped weapon and enter game world
        equippedWeapon = peashooter;
        GameManager.get().getWorld().addEntity(equippedWeapon);

        // for direction of movement
        movementDirection.put("left", false);
        movementDirection.put("right", false);
        movementDirection.put("up", false);
        movementDirection.put("down", false);

        //inventory
        inventory = new WeightedInventory(100);

    }

    /**
     * Handles the processes involved when a touch input is made.
     * @param screenX the x position being clicked on the screen
     * @param screenY the y position being clicked on the screen
     * @param pointer <unknown>
     * @param button <unknown>
     */    
    private void handleTouchDown(int screenX, int screenY, int pointer,
            int button) {
        equippedWeapon.updateAim(screenX, screenY);
        equippedWeapon.openFire();
    }
    
    /**
     * Handles the processes involved when a drag input is made.
     * @param screenX the x position on the screen that mouse is dragged to
     * @param screenY the y position on the screen that mouse is dragged to
     * @param pointer <unknown>
     */   
    private void handleTouchDragged(int screenX, int screenY, int pointer) {
        equippedWeapon.updatePosition(screenX, screenY);
        equippedWeapon.updateAim(screenX, screenY);
    }
    
    /**
     * Handles the processes involved when a touch input is released.
     * @param screenX the x position mouse is being released on the screen
     * @param screenY the y position mouse is being released on the screen
     * @param pointer <unknown>
     * @param button <unknown>
     */   
    private void handleTouchUp(int screenX, int screenY, int pointer,
            int button) {
        equippedWeapon.ceaseFire();
    }
    
    /**
     * Handles the processes involved when a mouse movement is made.
     * @param screenX the x position of mouse movement on the screen
     * @param screenY the y position of mouse movement on the screen
     */   
    private void handleMouseMoved(int screenX, int screenY) {
        equippedWeapon.updatePosition(screenX, screenY);
    }

    /**
     * On Tick handler
     *
     * @param gameTickCount Current game tick
     */
    @Override
    public void onTick(long gameTickCount) {
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
        if (world.getTiledMapTileLayerAtPos((int) newPosY, (int) newPosX)
                == null) {
            collided = true;
        } else {
            // set the layer, and get the speed of the tile on the layer. Also
            // name for logging.
            layer = world
                    .getTiledMapTileLayerAtPos((int) newPosY, (int) newPosX);
            speed = Float
                    .parseFloat((String) layer.getProperties().get("speed"));
            String name = layer.getProperties().get("name", String.class);

            // see if current tile is slippery. Save the slippery value if it is
            if (layer.getProperties().get("slippery") != null) {
              // this works but .get("slippery", Float.class) doesn't?
              slippery = Float.parseFloat
                  ((String) layer.getProperties().get("slippery"));
          }

            // see if current tile is deep water, if so, set player to swim
            ifSwim(name);
            
            // -1 for none, 0 for enemy, 1 for both, 2 for player
            int damagetype = -1;
            
            if (layer.getProperties().get("damagetype") != null) {
                damagetype = Integer.parseInt
                    ((String) layer.getProperties().get("damagetype"));
            }

            // damage player
            if (layer.getProperties().get("damage") != null && damagetype > 0 ) {
                this.setHealth(this.getHealth() - Integer.parseInt
                    ((String) layer.getProperties().get("damage")));      
            }

            // log
            LOGGER.info(this + " moving on terrain" + name
                    + " withspeed multiplier of " + speed);

        }

        // handle slippery movement (fixed for floating point)
        if (Math.abs(slippery) > 0.05f) {

            // first factor is for slowing down, second is for speeding up
            float slipperyFactor = slippery * 0.005f;
            float slipperyFactor2 = slippery * 0.06f;

            // created helper function to avoid duplicate code
            lastSpeedX = slipperySpeedHelper(speedX, lastSpeedX, speed,
                    slipperyFactor,
                    slipperyFactor2);
            lastSpeedY = slipperySpeedHelper(speedY, lastSpeedY, speed,
                    slipperyFactor,
                    slipperyFactor2);

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
        if (world.getTiledMapTileLayerAtPos((int) (newPosY), (int) (newPosX))
                == null) {
            collided = true;
        }

        Box3D newPos = getBox3D();
        newPos.setX(newPosX);
        newPos.setY(newPosY);

        List<AbstractEntity> entities = GameManager.get().getWorld()
                .getEntities();
        for (AbstractEntity entity : entities) {
            if (!this.equals(entity) && !(entity instanceof Squirrel) && newPos
                    .overlaps(entity.getBox3D())
                    && !(entity instanceof Bullet)
                    && !(entity instanceof Weapon)) {
                LOGGER.info(this + " colliding with " + entity);
                collided = true;

            }
        }

        if (!collided) {
            this.setPosition(newPosX, newPosY, 1);
        }

        //checkXp();
    }

    /**
     * Initialise a new player. Will be used after the user has created their
     * character in the character creation screen
     */
    public void initialiseNewPlayer(int strength, int vitality, int agility,
            int charisma, int intellect, int meleeSkill) {
        setAttributes(strength, vitality, agility, charisma, intellect);
        setSkills(meleeSkill);
        health = 4 * vitality;
        attributes.put("stamina", 4* agility);
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
     * Increases the player's level by one, increases the xpThreshold, increases health and stamina based on player
     * agility and vitality
     */
    private void levelUp() {
        xpThreshold *= 1.3;
        level++;
        attributes.put("stamina", attributes.get("stamina") + attributes.get("agility"));
        health = health + attributes.get("vitality");
        skillPoints = 4 + attributes.get("intellect");
        // TODO: enter level up screen
    }

    /**
     * Increases the xp of the player by the given amount
     * @param amount the amount of xp to gain
     */
    public void gainXp(int amount) {

        this.xp += amount;
        checkXp();
    }

    /**
     * Handle movement when wasd keys are pressed down
     */
    private void handleKeyDown(int keycode) {
        switch (keycode) {
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
            case Input.Keys.R:
                if(equippedWeapon.equals(peashooter)) {
                    GameManager.get().getWorld().removeEntity(equippedWeapon);
                    equippedWeapon = shotgun;
                    GameManager.get().getWorld().addEntity(equippedWeapon);
                } else if(equippedWeapon.equals(shotgun)){
                    GameManager.get().getWorld().removeEntity(equippedWeapon);
                    equippedWeapon = stargun;
                    GameManager.get().getWorld().addEntity(equippedWeapon);
                } else {
                    GameManager.get().getWorld().removeEntity(equippedWeapon);
                    equippedWeapon = peashooter;
                    GameManager.get().getWorld().addEntity(equippedWeapon);
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
     * pressed
     */
    private void handleDirectionInput() {

        float diagonalSpeed = (float) Math.sqrt(2*(movementSpeed*movementSpeed))/2;

        if (movementDirection.get("up") && movementDirection.get("right")) {
            speedX = movementSpeed;
            speedY= 0;
        } else if (movementDirection.get("up") && movementDirection.get("left")) {
            speedY = -movementSpeed;
            speedX = 0;
        } else if (movementDirection.get("down") && movementDirection.get("right")) {
            speedY = movementSpeed;
            speedX = 0;
        } else if (movementDirection.get("down") && movementDirection.get("left")) {
            speedX = -movementSpeed;
            speedY = 0;
        } else if (movementDirection.get("up") && movementDirection.get("down")) {
            speedX = 0;
            speedY = 0;
        } else if (movementDirection.get("left") && movementDirection.get("right")) {
            speedX = 0;
            speedY = 0;
        } else if (movementDirection.get("up")) {
            speedY = -diagonalSpeed;
            speedX = diagonalSpeed;
        } else if (movementDirection.get("down")) {
            speedY = diagonalSpeed;
            speedX = -diagonalSpeed;
        } else if (movementDirection.get("left")) {
            speedX = -diagonalSpeed;
            speedY = -diagonalSpeed;
        } else if (movementDirection.get("right")) {
            speedX = diagonalSpeed;
            speedY = diagonalSpeed;
        }
    }

    /**
     * Sets the player's movement speed to zero if no keys are pressed.
     */
    private void handleNoInput() {
        if (!movementDirection.get("up") && !movementDirection.get("down") &&
                !movementDirection.get("left") && !movementDirection.get("right")) {
            speedX = 0;
            speedY = 0;
        }
    }

    /**
     * Does logic for slowly increasing players speed and also slowly decreasing
     * players speed. Used to avoid repetitive code.
     *
     * @param speed Input speed of player
     * @param tileSpeed Speed multiplier from tile
     * @param slipperyFactor Scalar for slowing down player
     * @param slipperyFactor2 Scalar for speeding up player
     * @return New lastSpeed
     */
    private float slipperySpeedHelper(float speed, float lastSpeed,
            float tileSpeed,
            float slipperyFactor,
            float slipperyFactor2) {
        // speed up user in X dirn
        float lastSpeedNew;
        if (speed > 0) {
            lastSpeedNew = Math
                    .min(lastSpeed + speed * tileSpeed * slipperyFactor2,
                            speed * tileSpeed);
        } else if (speed < 0) {
            lastSpeedNew = Math
                    .max(lastSpeed + speed * tileSpeed * slipperyFactor2,
                            speed * tileSpeed);
        } else {
            // slow down user
            if (Math.abs(lastSpeed) > slipperyFactor) {
                lastSpeedNew =
                        lastSpeed - Math.signum(lastSpeed) * slipperyFactor;
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

        int worldLength = GameManager.get().getWorld().getLength();
        int worldWidth = GameManager.get().getWorld().getWidth();
        int tileWidth = (int) GameManager.get().getWorld().getMap()
                .getProperties()
                .get("tilewidth");
        int tileHeight = (int) GameManager.get().getWorld().getMap()
                .getProperties()
                .get("tileheight");

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
     * Update texture object of player, play sound effect when player is in deep
     * water
     *
     * @param name name of current tile
     */
    private void ifSwim(String name) {
      if (name != null) {
        if ("water-deep".equals(name)) {
            this.setTexture("hcg_character_swim");
            playSound("swimming");
            
        } else {
            this.setTexture("hcg_character");
            soundManager.stopSound("swimming");
        }
      }
    }

    /**
     * Plays the sound. If the sound is already playing, it will wait until it
     * finish
     *
     * @param sound name of the sound ID
     */

    private void playSound(String sound) {
        if (oldTime == -1) {
            oldTime = timeManager.getMinutes(); //wait for TimeManager to fix
            soundManager.playSound(sound);
        } else {
            newTime = timeManager.getMinutes();//wait for TimeManager to fix
            int timePass = newTime - oldTime;
            if ((timePass > 0 && timePass >= 2) || (timePass < 0
                    && timePass > -58)) {
                oldTime = timeManager.getMinutes();//wait for TimeManager to fix
                soundManager.playSound(sound);
            }
        }
    }

    @Override
    public String toString() {
        return "The player";
    }

    public Inventory getInventory(){
		return inventory;
    }
    
    public boolean addItemToInventory(Item item){
    	return inventory.addItem(item);
    }

}
