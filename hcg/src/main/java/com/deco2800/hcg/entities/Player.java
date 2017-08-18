package com.deco2800.hcg.entities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.InputManager;
import com.deco2800.hcg.util.Box3D;
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

	public Player(float posX, float posY, float posZ, float movementSpeed, int level, int xp, int health,
				  int stamina, int strength, int vitality, int agility, int charisma, int intellect, int meleeSkill) {

		super(posX, posY, posZ, 0.5f, 0.5f, 0.5f, true);


		InputManager input = (InputManager) GameManager.get().getManager(InputManager.class);

		input.addKeyDownListener(this::handleKeyDown);
		input.addKeyUpListener(this::handleKeyUp);
		input.addTouchDownListener(this::handleTouchDown);

		collided = false;
		this.setTexture("spacman_blue");
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

		Box3D newPos = getBox3D();
		newPos.setX(newPosX);
		newPos.setY(newPosY);

		List<AbstractEntity> entities = GameManager.get().getWorld().getEntities();
		collided = false;
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

	public float getMovementSpeed() {
		return movementSpeed;
	}

	public float getSpeedX() {
		return speedX;
	}

	public float getSpeedY() {
		return speedY;
	}

	public int getLevel() {
		return level;
	}

	public int getXp() {
		return xp;
	}

	public int getStrength() {
		return strength;
	}

	public int getVitality() {
		return vitality;
	}

	public int getAgility() {
		return agility;
	}

	public int getCharisma() {
		return charisma;
	}

	public int getIntellect() {
		return intellect;
	}

	public int getMeleeSkill() {
		return meleeSkill;
	}

	public void setMovementSpeed(float movementSpeed) {
		this.movementSpeed = movementSpeed;
	}

	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}

	public void setSpeedY(float speedY) {
		this.speedY = speedY;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setXp(int xp) {
		this.xp = xp;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public void setVitality(int vitality) {
		this.vitality = vitality;
	}

	public void setAgility(int agility) {
		this.agility = agility;
	}

	public void setCharisma(int charisma) {
		this.charisma = charisma;
	}

	public void setIntellect(int intellect) {
		this.intellect = intellect;
	}

	public void setMeleeSkill(int meleeSkill) {
		this.meleeSkill = meleeSkill;
	}

	@Override
	public String toString() {
		return "The player";
	}

}
