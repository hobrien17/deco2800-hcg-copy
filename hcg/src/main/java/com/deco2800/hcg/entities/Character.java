package com.deco2800.hcg.entities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.directory.InvalidAttributesException;

import com.deco2800.hcg.items.Item;

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
public abstract class Character extends AbstractEntity {
<<<<<<< HEAD
    // TODO: Change class implementation to use a single method  to get skills and attributes without using a map.
    public final static List<String> CHARACTER_ATTRIBUTES = Arrays.asList("stamina", "carryWeight",
=======
    // TODO: Change class implementation to use a map to store the skills and attributes instead of having multiple redundant methods.
    // Below made protected as we have getters and setters and we don't want other classes to be able to mutate this
    protected final static List<String> CHARACTER_ATTRIBUTES = Arrays.asList("level", "xp", "health", "stamina", "carryWeight",
>>>>>>> master
            "strength", "vitality", "agility", "charisma", "intellect");

    protected float movementSpeed;
    protected float movementSpeedNorm;
    protected float speedX;
    protected float speedY;

    protected int level;
    protected int xp;
    protected int healthMax;
    protected int healthCur;
    //Attributes map
    protected Map<String,Integer> attributes;

    // Skills
    // TODO: Message weapons team to find out what categories of weapons they will implement
    protected int meleeSkill;

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
        this.speedX = 0.0f;
        this.speedY = 0.0f;
        this.movementSpeed = 0.02f * attributes.get("agility");
        this.movementSpeedNorm = movementSpeed;

        this.level = 1;
        this.xp = 1;
        this.healthMax = 20;
        this.healthCur = healthMax;
        this.meleeSkill = 1;

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
    protected void setSkills(int meleeSkill) {

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
     *
     * @param health
     */
    protected void setHealthMax(int health) {
        this.healthMax = health;
    }
    
    /**
    *
    * @param health
    */
   protected void setHealthCur(int health) {
       if (health > this.healthMax) {
           this.healthCur = healthMax;
       } else {
           this.healthCur = health;
       }
   }

    /**
     *
     * @param attribute is in CHARACTER_ATTRIBUTES
     */
    protected void setAttribute(String attribute,int value){
        if ((CHARACTER_ATTRIBUTES.contains(attribute))) {
            this.attributes.put(attribute, value);
        }
    }
    /**
     *
     * @param meleeSkill
     */
    protected void setMeleeSkill(int meleeSkill) {
        this.meleeSkill = meleeSkill;
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
     * @return
     */
    public float getMovementSpeed() {
        return movementSpeed;
    }

    /**
     *
     * @return
     */
    public float getSpeedX() {
        return speedX;
    }

    /**
     *
     * @return
     */
    public float getSpeedY() {
        return speedY;
    }

    /**
     *
     * @return
     */
    public int getLevel() {
        return level;
    }

    /**
     *
     * @return
     */
    public int getXp() {
        return xp;
    }

    /**
     *
     * @return
     */
    public int getHealthMax() {
        return healthMax;
    }
    
    /**
    *
    * @return
    */
   public int getHealthCur() {
       return healthCur;
   }

    /**
     * @param attribute is an attribute in CHARACTER_ATTRIBUTES
     * @return value of attribute
     */
    public int getAttribute(String attribute) {
        if ((CHARACTER_ATTRIBUTES.contains(attribute))) {
            return new Integer(attributes.get(attribute));
        }
        return -1;
    }

    /**
     *
     * @return
     */
    public int getMeleeSkill() {
        return meleeSkill;
    }
}
