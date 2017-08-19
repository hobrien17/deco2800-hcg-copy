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

	public Player(float posX, float posY, float posZ) {

		super(posX, posY, posZ, 0.5f, 0.5f, 0.5f, true);

		InputManager input = (InputManager) GameManager.get().getManager(InputManager.class);

		input.addKeyDownListener(this::handleKeyDown);
		input.addKeyUpListener(this::handleKeyUp);
		input.addTouchDownListener(this::handleTouchDown);

		collided = false;
		this.setTexture("spacman");
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

		if (speedX == 0.0f && speedY == 0.0f) {
			return;
		}

		newPosX += speedX;
		newPosY += speedY;

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
		newPosX += speedX * speed;
		newPosY += speedY * speed;

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

	public void initialiseNewPlayer(int strength, int vitality, int agility, int charisma, int intellect,
									int meleeSkill) {
		setAttributes(strength, vitality, agility, charisma, intellect);
		setSkills(meleeSkill);
	}

	//Checks if the player's xp has reached the amount of xp required for levelling up
	private void checkXp() {
		if (xp >= xpThreshold) {
			levelUp();
		}
	}

	//Increases the player's level by one, increases the xpThreshold.
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
