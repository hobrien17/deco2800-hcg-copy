package com.deco2800.hcg.entities.enemy_entities;

import com.badlogic.gdx.math.Vector3;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Bullet;
import com.deco2800.hcg.entities.Character;
import com.deco2800.hcg.entities.Harmable;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.garden_entities.plants.Lootable;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.util.Box3D;
import com.deco2800.hcg.util.Effect;
import com.deco2800.hcg.util.Effects;
import com.deco2800.hcg.weapons.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.Random;

import static java.lang.Math.*;

public abstract class Enemy extends Character implements Lootable, Harmable {
    
    // logger for this class
    private static final Logger LOGGER = LoggerFactory.getLogger(Enemy.class);
    protected PlayerManager playerManager;
    protected int level;
    // Current status of enemy. 1 : New Born, 2 : Chasing 3 : Annoyed
    protected int status;
    protected int ID;
    protected transient Map<String, Double> lootRarity;
    protected float speedX;
    protected float speedY;
    protected float randomX;
    protected float randomY;
    protected float lastPlayerX;
    protected float lastPlayerY;
    protected float normalSpeed;
    protected float movementSpeed;

	// Effects container
	protected Effects myEffects;

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
     * @param ID the enemy ID
     */
    public Enemy(float posX, float posY, float posZ, float xLength, float yLength, float zLength, boolean centered,
                   int health, int strength, int ID) {
        super(posX, posY, posZ, xLength, yLength, zLength, centered);
        this.playerManager = (PlayerManager) GameManager.get().getManager(PlayerManager.class);
        status = 1;
        if (ID >= 0) {
            this.ID = ID;
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
        this.speedX = 0;
        this.speedY = 0;
        this.level = 1;
        this.movementSpeed = (float)(this.level * 0.03);

		// Effects container 
		myEffects = new Effects(this);
    }

    /**
     * Gets the enemy ID
     *
     * @return the integer ID of the enemy
     */
    public int getID() { return ID; }

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
     * Take the damage inflicted by the other entities
     * 
     * @param damage: the amount of the damage
     * @exception: throw IllegalArgumentException if damage is a negative integer
     */
    public void takeDamage(int damage) {
        if (damage < 0){
            throw new IllegalArgumentException();
        }
        else if (damage > healthCur){
            healthCur = 0;
        }
        else {
            healthCur -= damage;
        }
    }
    
    /**
     * 
     * @return the strength of the enemy
     */
    public int getStrength() {
        return this.getAttribute("strength");
    }
    

    /**
     * Change the health level of the enemy i.e can increase or decrease the health level (depending on whether the amount is positive or negative)
     * 
     * @param amount: the amount that the health level will change by
     */
    public void changeHealth(int amount) {
        healthCur = Math.max(healthCur + amount, 0);
    }

    /**
     * Attack the player
     *
     */
    public void causeDamage(Player player) {
        //we have to use this because at the moment the Player class has no takeDamage method yet. We are advised that they will implement it soon
        player.takeDamage(10);
    }

    @Override
    public Map<String, Double> getRarity() {
        return lootRarity;
    }

    /**
     * Returns a list of new loot items where 0 <= length(\result) <=
     * length(this.getLoot()) Loot may vary based on rarity and other factors
     * Possible to return an empty array
     * <p>
     * Currently only supports lists of 1 item
     *
     * @return A list of items
     */
    @Override
    public Item[] loot() {
        return new Item[0];
    }

    /**
     * Gets a list of all possible loot dropped by this plant
     *
     * @return An array of all possible loot
     */
    public String[] getLoot() {
        return lootRarity.keySet().toArray(new String[lootRarity.size()]);
    }

    /**
     * Sets up the loot rarity map for a plant
     */
    abstract void setupLoot();
    //Use this in special enemy classes to set up drops

    /**
     * Generates a random item based on the loot rarity
     *
     * @return A random item string in the plant's loot map
     */
    public String randItem() {
        Double prob = Math.random();
        Double total = 0.0;
        for (Map.Entry<String, Double> entry : lootRarity.entrySet()) {
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
     * To detect player's position. If player is near enemy, return 1.
     * @return: false: Undetected
     *          true: Detected player
     *
     */
    public void detectPlayer(){
        float distance = this.distance(playerManager.getPlayer());
        if(distance <= 10 * this.level){
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
    public Box3D randomMove() {
        float radius;
        float distance;
        float currPosX = this.getPosX();
        float currPosY = this.getPosY();
        float nextPosX;
        float nextPosY;
        float nextPosZ;
        float tempX;
        float tempY;
        //Get direction of next position. Randomly be chosen between 0 and 360.
        radius = Math.abs(new Random().nextFloat()) * 400 % 360;
        //Get distance to next position which is no more than maximum.
        distance = Math.abs(new Random().nextFloat()) * this.level + 5;
        nextPosX = (float) (currPosX + distance * cos(radius));
        nextPosY = (float) (currPosY + distance * sin(radius));
        tempX = nextPosX;
        tempY = nextPosY;
        //Keep enemy continue to next position.
        if((abs(this.randomX - currPosX) > 1) &&
                (abs(this.randomY - currPosY) > 1)){
            nextPosX = this.randomX;
            nextPosY = this.randomY;
        } else {
            this.randomX = tempX;
            this.randomY = tempY;
        }
        if (currPosX < nextPosX) {
            currPosX += movementSpeed;
        } else if (currPosX > nextPosX) {
            currPosX -= movementSpeed;
        }
        if (this.getPosY() < nextPosY) {
            currPosY += movementSpeed;
        } else if (this.getPosY() > nextPosY) {
            currPosY -= movementSpeed;
        }
        //this.setPosX(currPosX);
        //this.setPosY(currPosY);
        Box3D newPos = getBox3D();
        newPos.setX(currPosX);
        newPos.setY(currPosY);
        return newPos;
    }

    /**
     * Move enemy to player.
     *
     */
    public Box3D moveToPlayer(){
        float currPosX = this.getPosX();
        float currPosY = this.getPosY();
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
        //this.setPosX(currPosX);
        //this.setPosY(currPosY);
        Box3D newPos = getBox3D();
        newPos.setX(currPosX);
        newPos.setY(currPosY);
        return newPos;
    }

    /**
     * Move enemy to pointed position. Use for going to the player's last position.
     *
     */
    public Box3D moveTo(float posX, float posY){
        float currPosX = this.getPosX();
        float currPosY = this.getPosY();
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
        //this.setPosX(currPosX);
        //this.setPosY(currPosY);
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
    public void move(float currPosX, float currPosY){
        this.setPosX(currPosX);
        this.setPosY(currPosY);
    }

    /**
     * Shoot the entity
     *
     */
    public void shoot() {
        /*Vector3 worldCoords = GameManager.get().getCamera()
                .unproject(new Vector3(playerManager.getPlayer().getPosX(), playerManager.getPlayer().getPosY(), 0));
        Bullet bullet = new Bullet(this.getPosX(), this.getPosY(), this.getPosZ(), worldCoords.x, worldCoords.y, thisEnemy);
        GameManager.get().getWorld().addEntity(bullet);
    */
        enemyWeapon.updateAim((int)playerManager.getPlayer().getPosX(), (int)playerManager.getPlayer().getPosY());
        //System.out.println("123   " + playerManager.getPlayer().getPosX());
        enemyWeapon.openFire();
    }
    
    /**
     * Changes the speed by modifier amount
     * 
     * @param modifier 
     * 			the amount to change the speed (<1 to slow,  >1 to speed)
     */
    public void changeSpeed(float modifier) {
    	this.movementSpeed *= modifier;
    }
    
    /**
     * Sets the movement speed of the enemy
     * 
     * @param speed
     * 			the new movement speed
     */
    public void setSpeed(float speed) {
    	this.movementSpeed = speed;
    }
    
    /**
     * Sets the enemy's speed to its original value
     * 
     */
    public void resetSpeed() {
    	this.movementSpeed = normalSpeed;
    }
	
	// TEMPORARY METHODS to comply with temporary harmable implementations to get the Effects class working
	@Override
    public void giveEffect(Effect effect) {
        myEffects.addEffect(effect);
    }

    @Override
    public void giveEffect(Collection<Effect> effects) {
        myEffects.addAllEffects(effects);
    }
}
