package com.deco2800.hcg.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector3;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.InputManager;
import com.deco2800.hcg.util.Box3D;
import com.deco2800.hcg.worlds.AbstractWorld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Entity for the playable character.
 * 
 * @author leggy
 *
 */
public class Player extends Character implements Tickable {

	private static final Logger LOGGER = LoggerFactory.getLogger(Player.class);

	boolean collided;
	private int xpThreshold;
	float lastSpeedX;
	float lastSpeedY;

	public Player(float posX, float posY, float posZ) {

		super(posX, posY, posZ, 0.5f, 0.5f, 0.5f, true);

		InputManager input = (InputManager) GameManager.get().getManager(InputManager.class);

		input.addKeyDownListener(this::handleKeyDown);
		input.addKeyUpListener(this::handleKeyUp);
		input.addTouchDownListener(this::handleTouchDown);

		collided = false;
		this.setTexture("spacman");
		
		// for slippery
		lastSpeedX = 0;
		lastSpeedY = 0;

	}

	private void handleTouchDown(int screenX, int screenY, int pointer, int button) {
		Vector3 worldCoords = GameManager.get().getCamera().unproject(new Vector3(screenX, screenY, 0));
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

		//if (speedX == 0.0f && speedY == 0.0f) {
		//	return;
		//}

		// set speed is the multiplier due to the ground
		float speed = 1.0f;
		collided = false;
		float slippery = 0;
		
		
		// current world and layer
		TiledMapTileLayer layer;
		AbstractWorld world = GameManager.get().getWorld();

		// get speed of current tile. this is done before checking if a tile 
		// exists so a slow down tile next to the edge wouldn't cause problems.
		if (world.getTiledMapTileLayerAtPos((int)newPosY, (int)newPosX) == null) {
			collided = true;
		}
		else {
			// set the layer, and get the speed of the tile on the layer. Also name for logging.
			layer = world.getTiledMapTileLayerAtPos((int)newPosY, (int)newPosX);
			speed = layer.getProperties().get("speed", float.class);
			String name = layer.getProperties().get("name", String.class);
			
			// see if current tile is slippery. Save the slippery value if it is
			if (layer.getProperties().get("slippery", float.class) != null) {
				slippery = layer.getProperties().get("slippery", float.class);
			}
			
			// log
			LOGGER.info(this + " moving on terrain" + name + " withspeed multiplier of " + speed);

		}
		
		// handle slippery movement
		if (slippery != 0) {
			
			// first factor is for slowing down, second is for speeding up
			float slipperyFactor = slippery * 0.005f;
			float slipperyFactor2 = slippery * 0.06f;

			// speed up user in X dirn
			if (speedX > 0) {
				lastSpeedX = Math.min(lastSpeedX + speedX * speed* slipperyFactor2, speedX * speed);
			}
			else if (speedX < 0) {
				lastSpeedX = Math.max(lastSpeedX + speedX * speed * slipperyFactor2, speedX * speed);
			}
			else {
				// slow down user
				if (Math.abs(lastSpeedX) > slipperyFactor) {
					lastSpeedX = lastSpeedX - Math.signum(lastSpeedX) * slipperyFactor;
				}
				else {
					// ensure that speed eventually goes to zero
					lastSpeedX = 0;
				}
			}
			
			// speed up user in Y dirn
			if (speedY > 0) {
				lastSpeedY = Math.min(lastSpeedY + speedY * speed * slipperyFactor2, speedY * speed);
			}
			else if (speedY < 0) {
				lastSpeedY = Math.max(lastSpeedY + speedY * speed * slipperyFactor2, speedY * speed);
			}
			else {
				// slow down user
				if (Math.abs(lastSpeedY) > slipperyFactor) {
					lastSpeedY = lastSpeedY - Math.signum(lastSpeedY) * slipperyFactor;
				}
				else {
					lastSpeedY = 0;
				}
			}
			
		}
		else {
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

		if (!collided) {
			this.setPosition(newPosX, newPosY, 1);
		}

		checkXp();
	}

	/**
	 * Initialise a new player. Will be used after the user has created their character in the character creation screen
	 * @param strength
	 * @param vitality
	 * @param agility
	 * @param charisma
	 * @param intellect
	 * @param meleeSkill
	 */
	public void initialiseNewPlayer(int strength, int vitality, int agility, int charisma, int intellect,
									int meleeSkill) {
		setAttributes(strength, vitality, agility, charisma, intellect);
		setSkills(meleeSkill);
	}

	/**
	 * Checks if the player's xp has reached the amount of xp required for levelling up
	 */
	private void checkXp() {
		if (xp >= xpThreshold) {
			levelUp();
		}
	}

	/**
	 * Increases the player's level by one, increases the xpThreshold.
	 */
	private void levelUp() {
		xpThreshold *= 1.2;
		level++;
		//TODO: enter level up screen
	}

	public void gainXp(int xp){
		this.xp += xp;
	}

	/**
	 * Handle movement when wasd keys are pressed down
	 * 
	 * @param keycode
	 */
	private void handleKeyDown(int keycode) {
		switch (keycode) {
		case Input.Keys.W:
			speedY -= movementSpeed;
			speedX += movementSpeed;
			break;
		case Input.Keys.S:
			speedY += movementSpeed;
			speedX -= movementSpeed;
			break;
		case Input.Keys.A:
			speedX -= movementSpeed;
			speedY -= movementSpeed;
			break;
		case Input.Keys.D:
			speedX += movementSpeed;
			speedY += movementSpeed;
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
		switch (keycode) {
		case Input.Keys.W:
			speedY += movementSpeed;
			speedX -= movementSpeed;
			break;
		case Input.Keys.S:
			speedY -= movementSpeed;
			speedX += movementSpeed;
			break;
		case Input.Keys.A:
			speedX += movementSpeed;
			speedY += movementSpeed;
			break;
		case Input.Keys.D:
			speedX -= movementSpeed;
			speedY -= movementSpeed;
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
