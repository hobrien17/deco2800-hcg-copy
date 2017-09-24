package com.deco2800.hcg.entities.enemyentities;

import com.badlogic.gdx.math.Vector3;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Character;
import com.deco2800.hcg.entities.Harmable;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.garden_entities.plants.Lootable;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.ItemManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.managers.SoundManager;
import com.deco2800.hcg.util.Box3D;
import com.deco2800.hcg.util.Effect;
import com.deco2800.hcg.util.Effects;
import com.deco2800.hcg.weapons.*;
import com.deco2800.hcg.worlds.World;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
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
    protected Random random;
    protected boolean collided;
    protected boolean collidedPlayer;
    protected Box3D newPos;

	// Effects container
	protected Effects myEffects;

    protected Weapon enemyWeapon;
    
    // Sound manager
    private SoundManager soundManager;

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
        this.randomX = 0;
        this.randomY = 0;
        this.speedX = 0;
        this.speedY = 0;
        this.level = 1;
        this.normalSpeed = this.movementSpeed = (float)(this.level * 0.03);
        this.random = new Random();
        this.random.setSeed(this.getID());
        this.setCollided(false);
        this.setCollidedPlayer(false);
        this.newPos = getBox3D();

		// Effects container 
        myEffects = new Effects(this);
        
        this.soundManager = (SoundManager) GameManager.get().getManager(SoundManager.class);        
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
        player.takeDamage(1);
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
        Item[] arr = new Item[1];
        arr[0] = ((ItemManager)GameManager.get().getManager(ItemManager.class)).getNew(this.randItem());

        return arr;
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
        float nextPosZ;
        float tempX;
        float tempY;
        //Get direction of next position. Randomly be chosen between 0 and 360.
        radius = Math.abs(random.nextFloat()) * 400 % 360;
        //Get distance to next position which is no more than maximum.
        distance = Math.abs(random.nextFloat()) * this.level * 3;
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
     * Move enemy to pointed position. Use for going to the player's last position.
     *
     */
    public Box3D getMoveToPos(float posX, float posY){
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
        enemyWeapon.updateAim(new Vector3(playerManager.getPlayer().getPosX(), playerManager.getPlayer().getPosY(), 0));
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
    
    @Override
    public float getMovementSpeed() {
    	return this.movementSpeed;
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
