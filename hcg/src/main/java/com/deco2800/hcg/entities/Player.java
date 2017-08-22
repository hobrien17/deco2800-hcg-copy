package com.deco2800.hcg.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.InputManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.util.Box3D;
import com.deco2800.hcg.worlds.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Entity for the playable character.
 * 
 * @author leggy
 *
 */
public class Player extends AbstractEntity implements Tickable {

	private static final Logger LOGGER = LoggerFactory.getLogger(Player.class);

	private float movementSpeed;
	private float speedx;
	private float speedy;
	boolean collided;

    /**
     * A boolean to check if this player is in the WorldMap or not
     * if it is true, then should restrict the movement
     */
    boolean isInWorldMap = false;

	/**
	 * Creates a new Player instance.
	 *
	 * @param posX
	 *            The x-coordinate.
	 * @param posY
	 *            The y-coordinate.
	 * @param posZ
	 *            The z-coordinate.
	 */
	public Player(float posX, float posY, float posZ) {
		super(posX, posY, posZ, 0.5f, 0.5f, 1, 1, 1, false);
		movementSpeed = 0.1f;
		this.speedx = 0.0f;
		this.speedy = 0.0f;

		InputManager input = (InputManager) GameManager.get().getManager(InputManager.class);

		input.addKeyDownListener(this::handleKeyDown);
		input.addKeyUpListener(this::handleKeyUp);
		input.addTouchDownListener(this::handleTouchDown);

		collided = false;
		this.setTexture("spacman");
	}

	private void handleTouchDown(int screenX, int screenY, int pointer, int button) {
		Vector3 worldCoords = GameManager.get().getCamera().unproject(new Vector3(screenX, screenY, 0));
        // if the player is in WorldMap, restrict the movement. In the future, will change this
        if (this.isInWorldMap){
        	// move the player to the position of the mouse click
        	// transfer the (screenX,screenY) to (posX, posY) to fit the game
			// based on the shooting seed code. Not perfect at the moment
			float projX, projY;
			projX = worldCoords.x/55f;
			projY = -(worldCoords.y - 32f / 2f) / 32f + projX;
			projX -= projY - projX;
            this.setPosition(projX, projY, 1);
            return;
        }
		Bullet bullet = new Bullet(this.getPosX(), this.getPosY(), this.getPosZ(), worldCoords.x, worldCoords.y);
		GameManager.get().getWorld().addEntity(bullet);
	}

	/**
	 * On Tick handler
	 * @param gameTickCount Current game tick
	 */
	@Override
	public void onTick(long gameTickCount) {
		float newPosX = this.getPosX();
		float newPosY = this.getPosY();

		if (speedx == 0.0f && speedy == 0.0f) {
			return;
		}

		// set speed is the multiplier due to the ground
		float speed = 1.0f;
		collided = false;

		// current world and layer
		AbstractWorld world = GameManager.get().getWorld();

		// get speed of current tile. this is done before checking if a tile 
		// exists so a slow down tile next to the edge wouldn't cause
		// problems. also note that layer should never be null here,
		// provided the player starts somewhere valid.
		TiledMapTileLayer layer = world.getTiledMapTileLayerAtPos((int)newPosY, (int)newPosX);
		speed = layer.getProperties().get("speed", float.class);
		String name = layer.getProperties().get("name", String.class);

		// log
		LOGGER.info(this + " moving on terrain" + name + " withspeed multiplier of " + speed);

		// set new postition based on this speed
		newPosX += speedx * speed;
		newPosY += speedy * speed;

		// now check if a tile exists at this new position
		if (world.getTiledMapTileLayerAtPos((int)(newPosY), (int)(newPosX)) == null){
			collided = true;
		}

		Box3D newPos = getBox3D();
		newPos.setX(newPosX);
		newPos.setY(newPosY);

		List<AbstractEntity> entities = GameManager.get().getWorld().getEntities();
		for (AbstractEntity entity : entities) {
			if (!this.equals(entity) && !(entity instanceof Squirrel)
					&& newPos.overlaps(entity.getBox3D()) && !(entity instanceof Bullet)) {
				LOGGER.info(this + " colliding with " + entity);
				collided = true;

			}
		}
		/* if in WorldMap, you can't collide with anything */
		if (world instanceof WorldMapWorld) {
			collided = false;
		}
		if (!collided) {
			this.setPosition(newPosX, newPosY, 1);
		}
	}

	/**
	 * Handle movement when wasd keys are pressed down
	 * 
	 * @param keycode
	 */
	private void handleKeyDown(int keycode) {
	    // if user press M, trigger the WorldMap
	    if (keycode == Input.Keys.M){
	    	if (!(GameManager.get().getWorld() instanceof  WorldMapWorld)){   // <-- If we are not in the worldmap
				GameManager.get().setWorld(new WorldMapWorld());  // <-- Should not be new WorldMapWorld, need to find a way to store this info
				PlayerManager playerManager = (PlayerManager)GameManager.get().getManager(PlayerManager.class);
				playerManager.getPlayer().isInWorldMap = true;
				GameManager.get().getWorld().addEntity(playerManager.getPlayer());
			} else {
				GameManager.get().setWorld(new DemoWorld());  // <-- Should not be new DemoWorld, need to find a way to store this info
				PlayerManager playerManager = (PlayerManager)GameManager.get().getManager(PlayerManager.class);
				playerManager.getPlayer().isInWorldMap = false;
				GameManager.get().getWorld().addEntity(playerManager.getPlayer());
			}

            return;
        }

        // if the player is in WorldMap, restrict the movement. In the future, will change this
        if (isInWorldMap){
	        return;
        }

		switch (keycode) {
		case Input.Keys.W:
			speedy -= movementSpeed;
			speedx += movementSpeed;
			break;
		case Input.Keys.S:
			speedy += movementSpeed;
			speedx -= movementSpeed;
			break;
		case Input.Keys.A:
			speedx -= movementSpeed;
			speedy -= movementSpeed;
			break;
		case Input.Keys.D:
			speedx += movementSpeed;
			speedy += movementSpeed;
			break;
		default:
			break;
		}
	}

	/**
	 * Handle movement when wasd keys are released
	 * 
	 * @param keycode
	 */
	private void handleKeyUp(int keycode) {
        // if the player is in WorldMap, restrict the movement. In the future, will change this
        if (isInWorldMap){
            return;
        }

		switch (keycode) {
		case Input.Keys.W:
			speedy += movementSpeed;
			speedx -= movementSpeed;
			break;
		case Input.Keys.S:
			speedy -= movementSpeed;
			speedx += movementSpeed;
			break;
		case Input.Keys.A:
			speedx += movementSpeed;
			speedy += movementSpeed;
			break;
		case Input.Keys.D:
			speedx -= movementSpeed;
			speedy -= movementSpeed;
			break;
		default:
			break;
		}
	}

	@Override
	public String toString() {
		return "The player";
	}

}
