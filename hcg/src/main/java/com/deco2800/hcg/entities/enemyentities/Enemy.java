package com.deco2800.hcg.entities.enemyentities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.deco2800.hcg.buffs.Perk;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Character;
import com.deco2800.hcg.entities.ItemEntity;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.lootable.LootWrapper;
import com.deco2800.hcg.items.lootable.Lootable;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.shading.LightEmitter;
import com.deco2800.hcg.util.Box3D;
import com.deco2800.hcg.util.Effects;
import com.deco2800.hcg.weapons.*;
import com.deco2800.hcg.worlds.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static java.lang.Math.*;

public abstract class Enemy extends Character implements Lootable, LightEmitter {

	// logger for this class
	private static final Logger LOGGER = LoggerFactory.getLogger(Enemy.class);
	protected PlayerManager playerManager;
	protected int level;
	// Current status of enemy. 1 : New Born, 2 : Chasing 3 : Annoyed
	protected int status;
	protected int id;
	protected transient Map<LootWrapper, Double> lootRarity;
	protected float speedX;
	protected float speedY;
	protected float randomX;
	protected float randomY;
	protected float lastPlayerX;
	protected float lastPlayerY;
	protected float lostPlayerX;
	protected float lostPlayerY;
	protected Random random;
	protected boolean collided;
	protected boolean collidedPlayer;
	protected Box3D newPos;
	protected int direction;
	protected boolean boss;
	protected float defaultSpeed;
	protected EnemyType enemyType;
	private Player target;

	// Multiple players
	protected int numPlayers;
	protected Player closestPlayer;

	protected Box3D prevPos;
	protected Weapon enemyWeapon;

	protected int spriteCount;

	/**
	 * Creates a new enemy at the given position
	 * 
	 * @param posX
	 *            the x position
	 * @param posY
	 *            the y position
	 * @param posZ
	 *            the z position
	 * @param xLength
	 *            the length of the enemy in terms of thw x-axis
	 * @param yLength
	 *            the length of the enemy in terms of the y axis
	 * @param zLength
	 *            the length of the enemy in terms if the x axis
	 * @param centered
	 *            whether the enemy is centered or not
	 * @param health
	 *            the health of the enemy
	 * @param strength
	 *            the strength of the enemy
	 * @param id
	 *            the enemy ID
	 */
	public Enemy(float posX, float posY, float posZ, float xLength, float yLength, float zLength, boolean centered,
			int health, int strength, int id, EnemyType enemyType) {
		super(posX, posY, posZ, xLength, yLength, zLength, centered);
		this.playerManager = (PlayerManager) GameManager.get().getManager(PlayerManager.class);
		status = 1;
		if (id >= 0) {
			this.id = id;
		} else {
			throw new IllegalArgumentException();
		}
		this.enemyType = enemyType;
		this.healthCur = health;
		this.attributes.put("strength", strength);
		this.attributes.put("vitality", 1);
		this.attributes.put("agility", 1);
		this.attributes.put("charisma", 1);
		this.attributes.put("intellect", 1);
		this.healthMax = 1000;
		this.randomX = 0;
		this.randomY = 0;
		this.speedX = 0;
		this.speedY = 0;
		this.level = 1;
		this.movementSpeedNorm = this.movementSpeed = (float) (this.level * 0.03);
		this.random = new Random();
		this.random.setSeed(this.getID());
		this.setCollided(false);
		this.setCollidedPlayer(false);
		this.newPos = getBox3D();
		this.prevPos = getBox3D();
		this.target = null;
		this.spriteCount = 0;

		// Effects container
		myEffects = new Effects(this);
		this.setupLoot();
	}

	/**
	 * Checks whether the enemy is a boss or not
	 *
	 * @return true or false depending on whether the enemy is a boss
	 */
	public boolean isBoss() {
		return boss;
	}

	/**
	 * Gets the enemy ID
	 *
	 * @return the integer ID of the enemy
	 */
	public int getID() {
		return id;
	}

	/**
	 * Gets the enemy type
	 *
	 * @return the type of the enemy
	 */
	public EnemyType getEnemyType() {
		return enemyType;
	}

	/**
	 * Gets the last position X of player.
	 *
	 * @return the last position X of player
	 */
	public float getLastPlayerX() {
		return this.lastPlayerX;
	}

	/**
	 * Gets the last position Y of player.
	 *
	 * @return the last position Y of player
	 */
	public float getLastPlayerY() {
		return this.lastPlayerY;
	}

	/**
	 *
	 * @return the strength of the enemy
	 */
	public int getStrength() {
		return this.getAttribute("strength");
	}

	/**
	 * Set collision status.
	 * 
	 * @param status
	 */
	public void setCollided(boolean status) {
		this.collided = status;
	}

	/**
	 * Set player collision status.
	 * 
	 * @param status
	 */
	public void setCollidedPlayer(boolean status) {
		this.collidedPlayer = status;
	}

	/**
	 * Return collision status.
	 * 
	 * @return
	 */
	public boolean getCollided() {
		return this.collided;
	}

	/**
	 * Return collision of player status.
	 * 
	 * @return
	 */
	public boolean getPlayerCollided() {
		return this.collidedPlayer;
	}

	/**
	 * Set player target for the enemy
	 * 
	 * @param target
	 */
	public void setTarget(Player target) {
		this.target = target;
	}

	/**
	 * Return player target for the enemy
	 * 
	 * @return
	 */
	public Player getTarget() {
		return this.target;
	}

	/**
	 * Attack the player
	 *
	 */
	public void causeDamage(Player player) {
		//Damages the player based on enemy type
		// MUSHROOMTURRET & SNAIL don't damage the player this way

		//Perk - Kalerate
		Perk kaleRaTe = playerManager.getPlayer().getPerk(Perk.perk.KALERATE);
		//int to store outcome of dodge, 1 if player is hit, 0 otherwise.
		int dodged = 1;
		//if perk is active check to see if the player dodged the attack
			if (kaleRaTe.isActive()) {
				double dodgechance = 0.075 * kaleRaTe.getCurrentLevel();
				if (!(Math.random() < dodgechance)) {
					dodged = 0;
				}
			}
			else {
			switch(enemyType) {
				case SQUIRREL:
					player.takeDamage(10 * dodged * player.getLevel());
					break;
				case HEDGEHOG:
					player.takeDamage(20 * dodged * player.getLevel());
					break;
				case CRAB:
					player.takeDamage(50 * dodged * player.getLevel());
					break;
				default:
					player.takeDamage(10);
					break;
			}
		}


		//Perk - BRAMBLE_AM
		Perk brambleAm = player.getPerk(Perk.perk.BRAMBLE_AM);
		if (brambleAm.isActive()) {
			switch (brambleAm.getCurrentLevel()) {
				case 0:
					break;
				case 1:
					this.takeDamage((int)(3 + 0.5 * player.getLevel()));
					break;
				case 2:
					this.takeDamage((int)(5 + 0.7 * player.getLevel()));
					break;
				case 3:
					this.takeDamage(7 + player.getLevel());
					break;
				case 4:
					this.takeDamage((int) (10 + 1.2 * player.getLevel()));
			}
		}

	}

	@Override
	public Map<LootWrapper, Double> getRarity() {
		return lootRarity;
	}

	@Override
	public List<Item> getLoot() {
		List<Item> result = new ArrayList<>();
		result.add(randItem().getItem());
		return result;
	}

	@Override
	public List<Item> getAllLoot() {
		List<Item> items = new ArrayList<>(lootRarity.size());
		for (LootWrapper wrapper : lootRarity.keySet().toArray(new LootWrapper[lootRarity.size()])) {
			items.add(wrapper.getItem());
		}
		return items;
	}

	public void loot() {
		Item drop = this.getLoot().get(0);
		ItemEntity itemEntity = new ItemEntity(this.getPosX(), this.getPosY(), 0f, drop);
		GameManager.get().getWorld().addEntity(itemEntity);
	}

	/**
	 * Sets up the loot rarity map for a plant
	 */
	abstract void setupLoot();
	// Use this in special enemy classes to set up drops

	@Override
	public LootWrapper randItem() {
		Double prob = Math.random();
		Double total = 0.0;
		for (Map.Entry<LootWrapper, Double> entry : lootRarity.entrySet()) {
			total += entry.getValue();
			if (total > prob) {
				return entry.getKey();
			}
		}
		LOGGER.warn("No item has been selected, returning null");
		return null;
	}

	/**
	 * Checks that the loot rarity is valid
	 *
	 * @return true if the loot rarity is valid, otherwise false
	 */
	public boolean checkLootRarity() {
		double sum = 0.0;
		for (Double rarity : lootRarity.values()) {
			if (rarity < 0.0 || rarity > 1.0) {
				LOGGER.error("Rarity should be between 0 and 1");
				return false;
			}
			sum += rarity;
		}
		if (Double.compare(sum, 1.0) != 0) {
			LOGGER.warn("Total rarity should be 1");
			return false;
		}
		return true;
	}

	/**
	 * Return enemy's status.
	 */
	public int getStatus() {
		return this.status;
	}

	/**
	 * Set new status of enemy.
	 * 
	 * @param newStatus
	 */
	public void setStatus(int newStatus) {
		this.status = newStatus;
	}

	/**
	 * Detects the number of players in the current game. Returns number of
	 * elements in players list.
	 *
	 * @return: int numPlayers - number of players in game.
	 * @author Elvin - Team 9
	 */
	public int getNumberPlayers() {
		numPlayers = playerManager.getPlayers().size();
		return numPlayers;
	}

	/**
	 * Returns the player entity that is nearest to the enemy entity
	 * 
	 * @return Player object with the smallest distance
	 */
	public Player getClosestPlayer() {
		return closestPlayer;
	}

	/**
	 * Returns the player entity that is nearest to the enemy entity
	 *
	 * @return Player object with the smallest distance
	 */
	public void setClosestPlayer(Player closestPlayer) {
		this.closestPlayer = closestPlayer;
	}

	/**
	 * Changes status of enemy based on the closest player's position via least
	 * distance. Used when there are multiple players in the same game.
	 *
	 * Meraged with detectPlayer()
	 *
	 * @author Elvin - Team 9
	 */
	public void detectPlayers() {
		if (this.getNumberPlayers() > 1) {
			float closestDistance = findClosestPlayer();

			// Following is a modification of detectPlayer
			if (closestDistance <= 5 * this.level) {
				this.setStatus(2);
				this.lastPlayerX = closestPlayer.getPosX();
				this.lastPlayerY = closestPlayer.getPosY();
			} else if (this.getStatus() == 2) {
				this.setStatus(3);
			} else {
				this.setStatus(1);
			}

			return;
		}

		float distance = this.distance(playerManager.getPlayer());
		this.closestPlayer = playerManager.getPlayer();
		if (distance <= 5 * this.level) {
			// Annoyed by player.
			this.setStatus(2);
			this.lastPlayerX = playerManager.getPlayer().getPosX();
			this.lastPlayerY = playerManager.getPlayer().getPosY();
			return;
		}

		if (this.getStatus() == 2) {
			// Lost player
			this.lostPlayerX = this.getLastPlayerX();
			this.lostPlayerY = this.getLastPlayerY();
			this.setStatus(3);
		} else if (this.getStatus() == 3) {
			if ((abs(this.lostPlayerX - this.getPosX()) < 1) && (abs(this.lostPlayerY - this.getPosY()) < 1)) {
				this.setStatus(1);
			}

		} else {
			this.setStatus(1);
		}
	}

	/**
	 * Randomly go to next position which is inside the circle(Maximum distance
	 * depends on enemy level.) based on current position.
	 *
	 * Go to the player's position if detected player during moving.
	 *
	 */
	public Box3D getRandomPos() {
		float radius;
		float distance;
		float currPosX = this.getPosX();
		float currPosY = this.getPosY();
		float nextPosX;
		float nextPosY;
		float tempX;
		float tempY;
		prevPos.setX(currPosX);
		prevPos.setY(currPosY);
		// Get direction of next position. Randomly be chosen between 0 and 360.
		radius = Math.abs(random.nextFloat()) * 400 % 360;
		// Get distance to next position which is no more than maximum.
		distance = Math.abs(random.nextFloat()) * this.level * 5;
		if (distance < 3) {
			distance += 3;
		}
		nextPosX = (float) (currPosX + distance * cos(radius));
		nextPosY = (float) (currPosY + distance * sin(radius));
		tempX = nextPosX;
		tempY = nextPosY;
		if (abs(this.randomX) <= 0.005 && abs(this.randomY) <= 0.005) {
			this.randomX = tempX;
			this.randomY = tempY;
		}
		// Keep enemy continue to next position.
		if ((abs(this.randomX - currPosX) > 1) && (abs(this.randomY - currPosY) > 1) && !this.getCollided()) {
			nextPosX = this.randomX;
			nextPosY = this.randomY;
		} else {
			this.setCollided(false);
			this.setCollidedPlayer(false);
			this.randomX = tempX;
			this.randomY = tempY;
		}
		if (currPosX < nextPosX) {
			currPosX += movementSpeed * 0.25;
		} else if (currPosX > nextPosX) {
			currPosX -= movementSpeed * 0.25;
		}
		if (this.getPosY() < nextPosY) {
			currPosY += movementSpeed * 0.25;
		} else if (this.getPosY() > nextPosY) {
			currPosY -= movementSpeed * 0.25;
		}
		Box3D newPosition = getBox3D();
		newPosition.setX(currPosX);
		newPosition.setY(currPosY);
		return newPosition;
	}

	/**
	 * Move enemy to player. Modified for multiplayer by Elvin - Team 9
	 * 
	 * @return Box3D
	 */
	public Box3D getToPlayerPos(Player player) {
		float currPosX = this.getPosX();
		float currPosY = this.getPosY();
		if ((abs(this.getPosX() - player.getPosX()) > 1) || (abs(this.getPosY() - player.getPosY()) > 1)) {
			this.setCollided(false);
			this.setCollidedPlayer(false);
		}
		if (this.getPosX() < player.getPosX()) {
			currPosX += movementSpeed;
		} else if (this.getPosX() > player.getPosX()) {
			currPosX -= movementSpeed;
		}
		if (this.getPosY() < player.getPosY()) {
			currPosY += movementSpeed;
		} else if (this.getPosY() > player.getPosY()) {
			currPosY -= movementSpeed;
		}
		newPos.setX(currPosX);
		newPos.setY(currPosY);
		return newPos;
	}

	/**
	 * Move enemy to pointed position. Use for going to the player's last
	 * position.
	 *
	 */
	public Box3D getMoveToPos(float posX, float posY) {
		float currPosX = this.getPosX();
		float currPosY = this.getPosY();
		prevPos.setX(currPosX);
		prevPos.setY(currPosY);
		if (this.getPosX() < posX) {
			currPosX += movementSpeed;
		} else if (this.getPosX() > posX) {
			currPosX -= movementSpeed;
		}
		if (this.getPosY() < posY) {
			currPosY += movementSpeed;
		} else if (this.getPosY() > posY) {
			currPosY -= movementSpeed;
		}
		if ((abs(posX - currPosX) < 1) && (abs(posY - currPosY) < 1)) {
			this.setStatus(1);
		}
		Box3D newPosition = getBox3D();
		newPosition.setX(currPosX);
		newPosition.setY(currPosY);
		return newPosition;
	}

	/**
	 * Move enemy by different situation.
	 *
	 */
	public void setMove(float currPosX, float currPosY) {
		this.setPosX(currPosX);
		this.setPosY(currPosY);
	}

	public void moveAction() {
		if (!this.getCollided()) {
			this.setMove(newPos.getX(), newPos.getY());
			Vector3 position = new Vector3(this.getPosX(), this.getPosY(), 0);
			enemyWeapon.updatePosition(position);
		}
	}

	/**
	 * Detect collision with entity.
	 */
	public void detectCollision() {
		World world = GameManager.get().getWorld();
		if (world.getTiledMapTileLayerAtPos((int) newPos.getX(), (int) newPos.getY()) == null) {
			this.setCollided(true);
		}
		List<AbstractEntity> entities = GameManager.get().getWorld().getEntities();
		for (AbstractEntity entity : entities) {
			if (!this.equals(entity) && this.collidesWith(entity)) {

				if (entity instanceof Player) {
					this.setTarget((Player) entity);
					this.setCollidedPlayer(true);
				}
				this.setCollided(true);
				this.detour();
			}
		}
	}

	/**
	 * Generate new random pos when collied with non-player
	 *
	 */
	void detour() {
		do {
			if (this.getCollided() && !this.getPlayerCollided()) {
				newPos = this.getRandomPos();
			}
		} while (this.getCollided() && !this.getPlayerCollided());
		if (!this.getPlayerCollided()) {
			this.setCollided(false);
		}
	}

	/**
	 * Set new position by different situation.
	 *
	 * Modified for multiplayer by Elvin - Team 9
	 */
	public void setNewPos() {
		switch (this.getStatus()) {
		case 1:// Status: New Born
			newPos = this.getRandomPos();
			break;
		case 2:// Status: Chasing player
			newPos = this.getToPlayerPos(closestPlayer);
			this.shoot();
			break;
		case 3:// Status: Annoyed/Lost player

			newPos = this.getMoveToPos(this.lostPlayerX, this.lostPlayerY);
			break;
		default:
			newPos = this.getRandomPos();
		}
	}

	public void setDirection() {
		float xMove = newPos.getX() - prevPos.getX();
		float yMove = newPos.getY() - prevPos.getY();
		if (yMove > 0 && xMove > 0) {
			this.direction = 1;
		} else if (yMove < 0 && xMove > 0) {
			this.direction = 2;
		} else if (yMove < 0 && xMove < 0) {
			this.direction = 3;
		} else {
			this.direction = 4;
		}
	}

	/**
	 * Shoot the entity
	 *
	 */
	public void shoot() {
		enemyWeapon.updateAim(new Vector3(playerManager.getPlayer().getPosX(), playerManager.getPlayer().getPosY(), 0));
		enemyWeapon.openFire();
	}

	/**
	 * Update the texture based on the current texture of the sprite
	 * 
	 * @param texture:
	 *            the base texture
	 */
	private void updateTexture(String texture) {
		if (this.getTexture() == texture) {
			this.setTexture(texture + "2");
		} else {
			this.setTexture(texture);
		}
	}

	/**
	 * Update the sprites based on the direction
	 * 
	 * @param directionTextures:
	 *            the list of the sprite textures
	 */
	protected void updateSprite(String[] directionTextures) {
		if (spriteCount % 4 == 0) {
			switch (this.direction) {
			case 1:
				updateTexture(directionTextures[0]);
				break;
			case 2:
				updateTexture(directionTextures[1]);
				break;
			case 3:
				updateTexture(directionTextures[2]);
				break;
			case 4:
				updateTexture(directionTextures[3]);
				break;
			default:
				break;
			}
		}
		spriteCount++;
	}

	/**
	 * Logic for Squirrel
	 *
	 */
	void squirrel() {
		this.setMovementSpeed((float) (playerManager.getPlayer().getMovementSpeed() * 0.5));
		this.defaultSpeed = this.getMovementSpeed();
		if (this.getNumberPlayers() > 1) {
			float closestDistance = findClosestPlayer();
			if (closestDistance <= 10 * this.level) {
				newPos.setX(2 * this.getPosX() - this.closestPlayer.getPosX());
				newPos.setY(2 * this.getPosY() - this.closestPlayer.getPosY());
				if ((this.getHealthCur() < this.getHealthMax()) && (this.getHealthCur() > this.getHealthMax() * 0.85)) {
					this.setMovementSpeed((float) (this.defaultSpeed * 1.2));
				} else if ((this.getHealthCur() < this.getHealthMax() * 0.85)
						&& (this.getHealthCur() > this.getHealthMax() * 0.5)) {
					this.setMovementSpeed((float) (this.defaultSpeed * 1.4));
				} else if ((this.getHealthCur() < this.getHealthMax() * 0.5)
						&& (this.getHealthCur() > this.getHealthMax() * 0.25)) {
					this.setMovementSpeed((float) (this.defaultSpeed * 1.6));
				} else {
					this.setMovementSpeed((float) (this.defaultSpeed * 1.8));
				}
			} else {
				newPos = this.getRandomPos();
			}

		} else {
			this.closestPlayer = playerManager.getPlayer();
			float distance = this.distance(playerManager.getPlayer());
			if (distance <= 10 * this.level) {
				newPos.setX(2 * this.getPosX() - this.closestPlayer.getPosX());
				newPos.setY(2 * this.getPosY() - this.closestPlayer.getPosY());
			} else {
				newPos = this.getRandomPos();
			}
		}
		this.detectCollision();
		this.moveAction();

	}

	/**
	 * Find the closest player and the distance between the closest player and
	 * this enemy in multiplayer mode
	 * 
	 * @return the distance between the closest player and this enemy
	 */
	protected float findClosestPlayer() {
		List<Player> players;
		HashMap<Float, Player> playerHashMap = new HashMap<Float, Player>();
		int playerCount = 0;
		float[] distances = new float[numPlayers];
		float closestDistance;
		// Detect players
		players = playerManager.getPlayers();
		// Iterates through all players and puts distance from enemy to each
		// player into an array
		// Puts all players and their respective distances into a hash map
		for (Player player : players) {
			distances[playerCount] = this.distance(player);
			playerHashMap.put(distances[playerCount], player);
			playerCount++;
		}

		// Finds the smallest distance in the distance array
		closestDistance = distances[0];
		for (int j = 0; j < distances.length; j++) {
			if (distances[j] < closestDistance) {
				closestDistance = distances[j];
			}
		}

		// Gets the player with closest distance from the enemy and assigns to
		// variable
		this.closestPlayer = playerHashMap.get(closestDistance);
		return closestDistance;
	}

	/**
	 * Logic for Tree
	 *
	 */
	void tree() {
		this.setMovementSpeed(0);
		this.defaultSpeed = 0;

		GameManager.get().getWorld().getWidth();
		GameManager.get().getWorld().getLength();
		if ((this.getHealthCur() < this.getHealthMax()) && (this.getHealthCur() > this.getHealthMax() * 0.8)) {
			// bottom

			this.setPosX((float) (GameManager.get().getWorld().getWidth() * 0.5));
			this.setPosY(0);
		} else if ((this.getHealthCur() < this.getHealthMax() * 0.8)
				&& (this.getHealthCur() > this.getHealthMax() * 0.6)) {
			// left
			this.setPosX(0);
			this.setPosY((float) (GameManager.get().getWorld().getLength() * 0.5));
		} else if ((this.getHealthCur() < this.getHealthMax() * 0.6)
				&& (this.getHealthCur() > this.getHealthMax() * 0.4)) {
			// right
			this.setPosX(GameManager.get().getWorld().getWidth());
			this.setPosY((float) (GameManager.get().getWorld().getLength() * 0.5));
		} else if ((this.getHealthCur() < this.getHealthMax() * 0.4)
				&& (this.getHealthCur() > this.getHealthMax() * 0.2)) {
			// top
			this.setPosX((float) (GameManager.get().getWorld().getWidth() * 0.5));
			this.setPosY(GameManager.get().getWorld().getLength());
		} else {
			// middle
			this.setPosX((float) (GameManager.get().getWorld().getWidth() * 0.5));
			this.setPosY((float) (GameManager.get().getWorld().getLength() * 0.5));
		}
	}

	public Color getLightColour() {
		if (this.getTint() == null) {
			return Color.WHITE;
		}
		return this.getTint();
	}

	public float getLightPower() {
		if (this.getTint() == null) {
			return 0;
		} else if (this.getMovementSpeed() == 0) {
			return 3;
		} else {
			return 1;
		}
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Enemy)) {
			return false;
		}
		Enemy anotherEnemy = (Enemy) object;
		if (this.id == anotherEnemy.id) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31; // an odd base prime
		int result = 1; // the hash code under construction
		result = prime * result + this.id;
		return result;
	}
}