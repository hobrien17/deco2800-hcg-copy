package com.deco2800.hcg.entities;

import java.util.*;

import com.deco2800.hcg.entities.enemyentities.Enemy;
import com.deco2800.hcg.entities.enemyentities.EnemyType;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.quests.QuestManager;
import com.deco2800.hcg.util.Effect;
import com.deco2800.hcg.util.Effects;

/**
 * The Character abstract class is to be extended by all Characters, both
 * playable and non playable. Character extends AbstractEntity so that the
 * characters may be rendered to the game world.
 *
 * Characters have attributes and skills as listed below, skills and attributes
 * are used to calculate numerous stats in the background such as movement
 * speed, health, stamina, and damages with various weapons.
 *
 * NPCs do not have to gain xp or level up but they should have a level
 * (possibly displayed in game) that represents its difficulty.
 *
 * The character's level will increment by 1 once the player's xp reaches
 * the level up threshold.
 * The character's experience will tick over to 0 once the level up threshold is reached (level up thresholds TBD).
 * The character's health is determined by the character's level and vitality attribute.
 * The character's stamina is determined by the character's level and agility attribute.
 * The character's strength will increase the character's damage with with melee weapons and also determines how much
 * the player can have in their inventory.
 * Vitality determines how much health is gained per level.
 * Agility Determines how much the character's stamina is increased by each level, and also determines the
 * character's movement speed.
 * Charisma  Determines how well the character's interactions with other characters go.
 * Intellect determines how many skill points the player has to distribute to their skills each level.
 * meleeSkill determines how much damage the character does with melee weapons.
 *
 * @author avryn, trent_s
 */
public abstract class Character extends AbstractEntity implements Harmable, Tickable {
	// TODO: Change class implementation to use a map to store the skills and attributes instead of having multiple redundant methods.
	// Below made protected as we have getters and setters and we don't want other classes to be able to mutate this
	protected static final List<String> CHARACTER_ATTRIBUTES = Arrays.asList( "level", "xp", "carryWeight",
            "strength", "vitality", "agility", "charisma", "intellect", "meleeSkill", "gunsSkill", "energyWeaponsSkill");

	protected String name;

    protected float movementSpeed;
    protected float movementSpeedNorm;
    protected float speedX;
    protected float speedY;
    
    // Direction that the character is facing. Direction starts at 0 representing ↖ and successive values
    // incrementing 45 degrees clockwise.
    protected int direction;

    protected int level;
    protected int xp;
    protected int healthMax;
    protected int healthCur;
    protected int staminaMax;
    protected int staminaCur;
    //Attributes map
    protected Map<String,Integer> attributes;

    // Effects container
    protected Effects myEffects;

    // Skills
    // TODO: Message weapons team to find out what categories of weapons they will implement


    //Kill Log with worlds
    //Main level is a mapping between the world ID to Enemies and their amount of kills
    private HashMap<EnemyType, Integer> killLog;

    /**
     * Creates a new Character at the given position.
     * @param posX the x position
     * @param posY the y position
     * @param posZ the z position
     * @param xLength the length of the character in regard to the x axis
     * @param yLength the length of the character in regard to the y axis
     * @param zLength the length of the character in regard to the z axis
     * @param centered specifies if the entity is centered at the position or
     * not
     */
    public Character(float posX, float posY, float posZ, float xLength,
            float yLength, float zLength,
            boolean centered) {
        super(posX, posY, posZ, xLength, yLength, zLength, 1, 1, centered);
        // Set to 'reasonable' default characters.  Will need to be set using the setter methods when instantiating
        // a character
        this.attributes = new HashMap<String,Integer>();
        for (String attribute: CHARACTER_ATTRIBUTES) {
            attributes.put(attribute, 10);
        }

        this.name = "No Name";

        this.speedX = 0.0f;
        this.speedY = 0.0f;
        this.movementSpeed = 0.02f * attributes.get("agility");
        this.movementSpeedNorm = movementSpeed;

        this.level = 1;
        this.xp = 1;
        this.healthMax = 20;
        this.healthCur = healthMax;

        //Initialize the empty kill log
        killLog = new HashMap<>();

        myEffects = new Effects(this);
        
        this.direction = 0;
    }

    /**
     * Set the attributes of the character
     * @param strength the character's strength
     * @param vitality the character's vitality
     * @param agility the character's agility
     * @param charisma the character's charisma
     * @param intellect the character's intellect
     */
    protected void setAttributes(int strength, int vitality, int agility,
            int charisma, int intellect) {
         this.attributes.put("strength", strength);
         this.attributes.put("vitality", vitality);
         this.attributes.put("agility", agility);
         this.attributes.put("charisma", charisma);
         this.attributes.put("intellect", intellect);
    }

    /**
     * Sets the character's skills
     * @param meleeSkill
     */
    protected void setSkills(int meleeSkill, int gunsSkill, int energyWeaponsSkill) {
        this.attributes.put("meleeSkill", meleeSkill);
        this.attributes.put("gunsSkill", gunsSkill);
        this.attributes.put("energyWeaponsSkill", energyWeaponsSkill);
    }

    /**
     * Sets the character's name
     * @param name
     */
    protected void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param movementSpeed
     */
    protected void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    /**
     *
     * @param speedX
     */
    protected void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    /**
     *
     * @param speedY
     */
    protected void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    /**
     *
     * @param level
     */
    protected void setLevel(int level) {
        this.level = level;
    }

    /**
     *
     * @param xp
     */
    protected void setXp(int xp) {
        this.xp = xp;
    }

    /**
     * Sets the character's maximum health to the passed value. Defaults to
     * zero if passed a negative number.
     * 
     * @param health The value to set maximum health to
     */
    public void setHealthMax(int health) {
        if (health < 0) {
            this.healthMax = 0;
        } else {
            this.healthMax = health;
        }
    }

    /**
     * Take the damage inflicted by the other entities
     *
     * @param damage: the amount of the damage
     */
    public void takeDamage(int damage) {
        if (damage < 0){
            heal(Math.abs(damage));
        }
        else if (damage > healthCur){
            healthCur = 0;
        }
        else {
            healthCur -= damage;
        }
    }

    /**
     * Heal the player by a specified amount.
     *
     * @param amount: the amount to heal the player
     * @exception: throw IllegalArgumentException if amount is negative
     */
    public void heal(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Heal amount must be positive.");
        }
        if (healthCur + amount > healthMax) {
            healthCur = healthMax;
        } else {
            healthCur += amount;
        }
    }

    /**
     * Changes the speed by modifier amount
     *
     * @param modifier
     * 			the amount to change the speed (<1 to slow,  >1 to speed)
     */
    public void changeSpeed(float modifier) {
        if (!(modifier >= 0)) {
            throw new IllegalArgumentException("Invalid speed modifier being applied.");
        }
        setSpeed(movementSpeed * modifier);
    }

    /**
     * Sets the enemy's speed to its original value
     *
     */
    public void resetSpeed() {
        this.movementSpeed = movementSpeedNorm;
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
    * Sets the character's current health to the passed value. Defaults to
    * zero if passed a negative number. Defaults to the character's
    * maximum health if passed a value greater than it.
    * 
    * @param health The value to set current health to
    */
     public void setHealthCur(int health) {
        if (health < 0) {
            this.healthCur = 0;
        } else if (health > this.healthMax) {
            this.healthCur = this.healthMax;
        } else {
            this.healthCur = health;
        }
    }

    /**
     * Change the health level of the character i.e can increase or decrease the
     * health level (depending on whether the amount is positive or negative).
     *
     * @param amount: the amount that the health level will change by
     */
    public void changeHealth(int amount) {
        healthCur = Math.max(healthCur + amount, 0);
    }
   
    /**
    * Sets the character's maximum stamina to the passed value. Defaults to
    * zero if passed a negative number.
    * 
    * @param stamina The value to set maximum stamina to
    */
    public void setStaminaMax(int stamina) {
        if (stamina < 0) {
            this.staminaMax = 0;
        } else {
            this.staminaMax = stamina;
        }
    }
    
    /**
    * Sets the character's current stamina to the passed value. Defaults to
    * zero if passed a negative number. Defaults to the character's
    * maximum stamina if passed a value greater than it.
    * 
    * @param stamina The value to set current stamina to
    */
    public void setStaminaCur(int stamina) {
        if (stamina < 0) {
            this.staminaCur = 0;
        } else if (stamina > this.staminaMax) {
            this.staminaCur = this.staminaMax;
        } else {
            this.staminaCur = stamina;
        }
    }

    /**
     *
     * @param attribute is in CHARACTER_ATTRIBUTES
     */
    public void setAttribute(String attribute,int value){
        if (CHARACTER_ATTRIBUTES.contains(attribute)) {
            this.attributes.put(attribute, value);
        }
    }

    
    /**
     * Fetches the item this character currently has equipped, or null if this
     * character has no item equipped.
     * 
     * @return This character's equipped item.
     */
    public Item getCurrentEquippedItem() {
        return null;
    }

    /**
     *
     * @return The character's current movement speed.
     */
    public float getMovementSpeed() {
        return movementSpeed;
    }

    /**
     *
     * @return The character's speed in the X direction.
     */
    public float getSpeedX() {
        return speedX;
    }

    /**
     *
     * @return The character's speed in the Y direction.
     */
    public float getSpeedY() {
        return speedY;
    }
    
    /**
     * @return The direction that the character is facing.
     */
    public int getDirection() {
        return direction;
    }

    /**
     *
     * @return The character's level.
     */
    public int getLevel() {
        return level;
    }

    /**
     *
     * @return The character's experience.
     */
    public int getXp() {
        return xp;
    }

    /**
     *
     * @return The character's maximum health.
     */
    public int getHealthMax() {
        return healthMax;
    }
    
    /**
    *
    * @return The character's current health.
    */
    public int getHealthCur() {
        return healthCur;
    }

    /**
    *
    * @return The character's maximum stamina.
    */
    public int getStaminaMax() {
        return staminaMax;
    }
   
    /**
     *
     * @return The character's current stamina.
     */
    public int getStaminaCur() {
        return staminaCur;
    }

    /**
     * @param attribute is an attribute in CHARACTER_ATTRIBUTES
     * @return value of attribute
     */

    public int getAttribute(String attribute) {
        if (CHARACTER_ATTRIBUTES.contains(attribute)) {
            return new Integer(attributes.get(attribute));
        }
        return -1;
    }

    public int getStrength() {
        return attributes.get("strength");
    }

    /**
     * Add a kill for the specified enemy ID. If it has not being killed before, add it to the kill log and
     * set its kill count to 1.
     *
     * @param enemyType the type of enemy that was killed.
     */
    public void killLogAdd(EnemyType enemyType) {
        //Add the enemy ID to that world if it has not already being added
        killLog.putIfAbsent(enemyType, 0);
        killLog.put(enemyType,1 + killLog.get(enemyType));
        updateQuestLog();
    }


    /**
     * Gets the amount of kills logged for the specified enemy ID in the current Node position
     * If it has not being killed before at the location it returns 0 kills for that enemy.
     *
     * @param enemyType the type of enemy that was killed.
     * @return The amount of times the specified enemy has being killed in an area.
     */
    public int killLogGet(EnemyType enemyType) {
        return killLog.getOrDefault(enemyType,0);
    }

    /**
     * Used to determine if a particular enemy type has being killed at the current node.
     * More useful for determining if bosses or the like have being killed.
     *
     * @param enemyType the type of enemy that was killed.
     * @return if the specified enemy has being killed before.
     */
    public boolean killLogContains(EnemyType enemyType) {
        return killLog.containsKey(enemyType);
    }

    /**
     * Used to tell the quest log that things have changed. Which will be useful if 'notifications'
     * are enabled to tell the player that they have competed a quest.
     */
    public void updateQuestLog() {
        //Todo: Add the quest log function for updating.



    }

    /**
     * @return the position in the nodal map where the character currently is
     */
    private int getCurrentNodeID() {
        return GameManager.get().getCurrentNode().getNodeID();
    }

    /**
     * Triggered when an AbstractEntity has died as the result of an effect caused by this character.
     *
     * @param victim the character that has died.
     */
    public void killAlert(AbstractEntity victim) {
        if (victim instanceof Enemy) {
            Enemy enemyVictim = (Enemy) victim;
            this.killLogAdd(enemyVictim.getEnemyType());
        }
    }

    @Override
    public void giveEffect(Effect effect) {
        myEffects.addEffect(effect);
    }

    @Override
    public void giveEffect(Collection<Effect> effects) {
        myEffects.addAllEffects(effects);
    }

    /**
     * On tick is called periodically (time dependant on the world settings)
     *
     * @param gameTickCount Current game tick
     */
    @Override
    public void onTick(long gameTickCount) {
        myEffects.apply();
    }
}
