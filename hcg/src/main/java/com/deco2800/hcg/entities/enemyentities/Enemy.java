package com.deco2800.hcg.entities.enemyentities;

import com.badlogic.gdx.math.Vector3;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Character;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.lootable.LootWrapper;
import com.deco2800.hcg.items.lootable.Lootable;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;
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

public abstract class Enemy extends Character implements Lootable {

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
    protected Random random;
    protected boolean collided;
    protected boolean collidedPlayer;
    protected Box3D newPos;
    protected int direction;

    //Multiple players
    private int numPlayers;
    private Player closestPlayer;

    protected Box3D prevPos;

    protected Weapon enemyWeapon;

    /**
     * Creates a new enemy at the given position
     * @param posX the x position
     * @param posY the y position
     * @param posZ the z position
     * @param xLength the length of the enemy in terms of thw x-axis
     * @param yLength the length of the enemy in terms of the y axis
     * @param zLength the length of the enemy in terms if the x axis
     * @param centered whether the enemy is centered or not
     * @param health the health of the enemy
     * @param strength the strength of the enemy
     * @param id the enemy ID
     */
    public Enemy(float posX, float posY, float posZ, float xLength, float yLength, float zLength, boolean centered,
                 int health, int strength, int id) {
        super(posX, posY, posZ, xLength, yLength, zLength, centered);
        this.playerManager = (PlayerManager) GameManager.get().getManager(PlayerManager.class);
        status = 1;
        if (id >= 0) {
            this.id = id;
        } else {
            throw new IllegalArgumentException();
        }
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
        this.movementSpeedNorm = this.movementSpeed = (float)(this.level * 0.03);
        this.random = new Random();
        this.random.setSeed(this.getID());
        this.setCollided(false);
        this.setCollidedPlayer(false);
        this.newPos = getBox3D();
        this.prevPos = getBox3D();

        // Effects container
        myEffects = new Effects(this);
    }

    /**
     * Gets the enemy ID
     *
     * @return the integer ID of the enemy
     */
    public int getID() { return id; }

    /**
     * Gets the last position X of player.
     *
     * @return the last position X of player
     */
    public float getLastPlayerX(){
        return this.lastPlayerX;
    }

    /**
     * Gets the last position Y of player.
     *
     * @return the last position Y of player
     */
    public float getLastPlayerY(){
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
     * @param status
     */
    public void setCollided(boolean status){
        this.collided = status;
    }

    /**
     * Set player collision status.
     * @param status
     */
    public void setCollidedPlayer(boolean status){
        this.collidedPlayer = status;
    }

    /**
     * Return collision status.
     * @return
     */
    public boolean getCollided(){
        return this.collided;
    }

    
    
    /**
     * Attack the player
     *
     */
    public void causeDamage(Player player) {
        //we have to use this because at the moment the Player class has no takeDamage method yet. We are advised that they will implement it soon
        player.takeDamage(1);
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
		for(LootWrapper wrapper : lootRarity.keySet().toArray(new LootWrapper[lootRarity.size()])) {
			items.add(wrapper.getItem());
		}
		return items;
	}
    
    public void loot() {
    	//TODO implement this
    }

    /**
     * Sets up the loot rarity map for a plant
     */
    abstract void setupLoot();
    //Use this in special enemy classes to set up drops

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
    public int getStatus(){
        return this.status;
    }

    /**
     * Set new status of enemy.
     * @param newStatus
     */
    public void setStatus(int newStatus){
        this.status = newStatus;
    }

    /**
     * Detects the number of players in the current game. Returns number of elements in players list.
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
     * Changes status of enemy based on the closest player's position via least distance.
     * Used when there are multiple players in the same game.
     *
     * @author Elvin - Team 9
     */
    public void detectPlayers() {

        List<Player> players;
        HashMap<Float, Player> playerHashMap = new HashMap<Float, Player>();

        int playerCount = 0;
        float[] distances = new float[numPlayers];
        float closestDistance;

        getNumberPlayers();
        players = playerManager.getPlayers();

        //Iterates through all players and puts distance from enemy to each player into an array
        //Puts all players and their respective distances into a hash map
        for (Player player : players) {
            distances[playerCount] = this.distance(player);
            playerHashMap.put(distances[playerCount], player);
            playerCount++;
        }

        //Finds the smallest distance in the distance array
        closestDistance = distances[0];
        for (int j = 0; j < distances.length; j++) {
            if (distances[j] < closestDistance) {
                closestDistance = distances[j];
            }
        }

        //Gets the player with closest distance from the enemy and assigns to variable
        closestPlayer = playerHashMap.get(closestDistance);

        //Following is a modification of detectPlayer
        if (closestDistance <= 5 * this.level) {
            this.setStatus(2);
            this.lastPlayerX = closestPlayer.getPosX();
            this.lastPlayerY = closestPlayer.getPosY();
        } else if (this.getStatus() == 2){
            this.setStatus(3);
        } else {
            this.setStatus(1);
        }

    }
        

    /**
     * To detect player's position and set enemy's status.
     *
     */
    public void detectPlayer(){
        float distance = this.distance(playerManager.getPlayer());
        if(distance <= 5 * this.level){
            //Annoyed by player.
            this.setStatus(2);
            this.lastPlayerX = playerManager.getPlayer().getPosX();
            this.lastPlayerY = playerManager.getPlayer().getPosY();
        }else{
            if (this.getStatus() == 2){
                this.setStatus(3);
            } else {
                this.setStatus(1);
            }
        }
    }

    /**
     * Randomly go to next position which is inside the circle(Maximum distance depends on enemy level.)
     * based on current position.
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
        //Get direction of next position. Randomly be chosen between 0 and 360.
        radius = Math.abs(random.nextFloat()) * 400 % 360;
        //Get distance to next position which is no more than maximum.
        distance = Math.abs(random.nextFloat()) * this.level * 5;
        if (distance < 3){
            distance += 3;
        }
        nextPosX = (float) (currPosX + distance * cos(radius));
        nextPosY = (float) (currPosY + distance * sin(radius));
        tempX = nextPosX;
        tempY = nextPosY;
        if(abs(this.randomX) <= 0.005 && abs(this.randomY) <= 0.005){
            this.randomX = tempX;
            this.randomY = tempY;
        }
        //Keep enemy continue to next position.
        if((abs(this.randomX - currPosX) > 1) &&
                (abs(this.randomY - currPosY) > 1) &&
                !this.getCollided()){
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
        Box3D newPos = getBox3D();
        newPos.setX(currPosX);
        newPos.setY(currPosY);
        return newPos;
    }

    /**
     * Move enemy to player.
     *
     */
    public Box3D getToPlayerPos(){
        float currPosX = this.getPosX();
        float currPosY = this.getPosY();
        prevPos.setX(currPosX);
        prevPos.setY(currPosY);
        if((abs(this.getPosX() - playerManager.getPlayer().getPosX()) > 1)||
                (abs(this.getPosY() - playerManager.getPlayer().getPosY()) > 1)){
            this.setCollided(false);
            this.setCollidedPlayer(false);
        }
        if(this.getPosX() < playerManager.getPlayer().getPosX()){
            currPosX += movementSpeed;
        }
        else if(this.getPosX() > playerManager.getPlayer().getPosX()){
            currPosX -= movementSpeed;
        }
        if(this.getPosY() < playerManager.getPlayer().getPosY()){
            currPosY += movementSpeed;
        }
        else if(this.getPosY() > playerManager.getPlayer().getPosY()){
            currPosY -= movementSpeed;
        }
        newPos.setX(currPosX);
        newPos.setY(currPosY);
        return newPos;
    }

    /**
     * Copied and modified from getToPlayerPos().
     * Added an input argument to allow function to work on any player passed in, rather than the single
     * 'hard coded' player in playerManager.
     *
     * @return Box3D
     * @author Elvin - Team 9
     */
    public Box3D moveToPlayer(Player player){
        float currPosX = this.getPosX();
        float currPosY = this.getPosY();
        if((abs(this.getPosX() - player.getPosX()) > 1)||
                (abs(this.getPosY() - player.getPosY()) > 1)){
            this.setCollided(false);
            this.setCollidedPlayer(false);
        }
        if(this.getPosX() < player.getPosX()){
            currPosX += movementSpeed;
        }
        else if(this.getPosX() > player.getPosX()){
            currPosX -= movementSpeed;
        }
        if(this.getPosY() < player.getPosY()){
            currPosY += movementSpeed;
        }
        else if(this.getPosY() > player.getPosY()){
            currPosY -= movementSpeed;
        }
        newPos.setX(currPosX);
        newPos.setY(currPosY);
        return newPos;
    }

    /**
     * Move enemy to pointed position. Use for going to the player's last position.
     *
     */
    public Box3D getMoveToPos(float posX, float posY){
        float currPosX = this.getPosX();
        float currPosY = this.getPosY();
        prevPos.setX(currPosX);
        prevPos.setY(currPosY);
        if(this.getPosX() < posX){
            currPosX += movementSpeed;
        }
        else if(this.getPosX() > posX){
            currPosX -= movementSpeed;
        }
        if(this.getPosY() < posY){
            currPosY += movementSpeed;
        }
        else if(this.getPosY() > posY){
            currPosY -= movementSpeed;
        }
        if((abs(posX - currPosX) < 1) &&
                (abs(posY - currPosY) < 1)){
            this.setStatus(1);
        }
        Box3D newPos = getBox3D();
        newPos.setX(currPosX);
        newPos.setY(currPosY);
        return newPos;
    }

    /**
     * Move enemy by different situation.
     *
     */
    public void setMove(float currPosX, float currPosY){
        this.setPosX(currPosX);
        this.setPosY(currPosY);
    }

    public void moveAction(){
        if (!this.getCollided()) {
            this.setMove(newPos.getX(), newPos.getY());
            enemyWeapon.updatePosition((int)this.getPosX(), (int)this.getPosY());
        }
    }

    /**
     * Detect collision with entity.
     */
    public void detectCollision(){
        World world = GameManager.get().getWorld();
        if (world.getTiledMapTileLayerAtPos((int) newPos.getX(), (int) newPos.getY()) == null) {
            this.setCollided(true);
        }
        List<AbstractEntity> entities = GameManager.get().getWorld().getEntities();
        for (AbstractEntity entity : entities) {
            if (!this.equals(entity) && newPos.overlaps(entity.getBox3D())) {
                if(entity instanceof Player) {
                    this.causeDamage((Player)entity);
                    this.setCollidedPlayer(true);
                }
                this.setCollided(true);
            }
        }
    }


    /**
     * Set new position by different situation.
     */
    public void setNewPos(){
        switch(this.getStatus()){
            case 1://Status: New Born
                newPos = this.getRandomPos();
                break;
            case 2://Status: Chasing player
                newPos = this.getToPlayerPos();
                this.shoot();
                break;
            case 3://Status: Annoyed/Lost player
                newPos = this.getMoveToPos(this.getLastPlayerX(), this.getLastPlayerY());
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
     * Set new position by different situations, modified for multiple players
     *
     * @author Elvin - Team 9
     */
    public void setNewPosMultiplayer() {
        switch(this.getStatus()) {
            case 1: //Status: New born enemy
                newPos = this.getRandomPos();
                break;
            case 2: //Status: Chasing closest player
                newPos = this.moveToPlayer(closestPlayer);
                this.shoot();
                break;
            case 3: //Status: Annoyed/Lost player
                newPos = this.getMoveToPos(this.getLastPlayerX(), this.getLastPlayerY());
                break;
            default:
                newPos = this.getRandomPos();
                break;
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Enemy)) {
            return false;
        }
        Enemy anotherEnemy = (Enemy) obj;
        if (this.id == anotherEnemy.id) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        // We create a polynomial hash-code based on start, end and capacity
        final int prime = 31; // an odd base prime
        int result = 1; // the hash code under construction
        result = prime * result + this.id;
        return result;
    }
}