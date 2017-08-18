package com.deco2800.hcg.entities;

import com.deco2800.hcg.util.Box3D;

/**
 * @author avryn
 */
public abstract class Character extends AbstractEntity {

    protected float movementSpeed;
    protected float speedX;
    protected float speedY;

    protected int level;
    protected int xp;
    protected int health;
    protected int stamina;

    // Attributes
    protected int strength;
    protected int vitality;
    protected int agility;
    protected int charisma;
    protected int intellect;

    // Skills
    // TODO: Message weapons team to find out what categories of weapons they will implement

    protected int meleeSkill;

    /**
     * Creates a new Player instance.
     *
     * @param posX
     *            The x-coordinate.
     * @param posY
     *            The y-coordinate.
     * @param posZ
     *            The z-coordinate.
     * @param xLength
     *            ???
     * @param yLength
     *             ???
     * @param zLength
     *             ???
     * @param xRenderLength
     *              ???
     * @param yRenderLength
     *              ???
     * @param centered
     *              ???
     * @param movementSpeed
     *              ???
     * @param speedX
     *              ???
     * @param speedY
     *              ???
     * @param level
     * 			  The character's level. Will increment by 1 once the player's xp reaches the level up
     * 			  threshold.
     * @param xp
     * 			  The character's current experience. Will tick over to 0 once the level up threshold
     * 			  is reached (level up thresholds TBD).
     * @param health
     * 		      The characters's health. The player's health is determined by the player's level and
     * 		      vitality attribute.
     * @param stamina
     * 			  The character's stamina. The player's stamina is determined by the player's level and
     * 			  agility attribute.
     * @param strength
     * 			  The character's strength.  Will increase the character's damage with melee with melee weapons,
     * 			  determines how much the player can have in their inventory.
     * @param vitality
     *            The character's vitality. Determines how much health is gained per level.
     * @param agility
     *            The character's agility. Determines how much the character's stamina is increased by each level,
     *            and also determines the character's movement speed
     * @param charisma
     *            The character's charisma. Determines how well the character's interactions with other
     *            characters go.
     * @param intellect
     *            The character's intellect. Determines how many skill points the player has to distribute to their
     *            skills each level.
     * @param meleeSkill
     *            The character's meleeSkill, determines how much damage is done with melee weapons.
     */
    public Character(float posX, float posY, float posZ, float xLength, float yLength, float zLength,
                     boolean centered) {
        super(posX, posY, posZ, xLength, yLength, zLength, 1, 1, centered);
        // Set to 'reasonable' default characters.  Will need to be set using the setter methods when instantiating
        // a character
        this.movementSpeed = 0.1f;
        this.speedX = 0.0f;
        this.speedY = 0.0f;
        this.level = 1;
        this.xp = 1;
        this.health = 1;
        this.stamina = 1;
        this.strength = 1;
        this.vitality = 1;
        this.agility = 1;
        this.charisma = 1;
        this.intellect = 1;
        this.meleeSkill = 1;
    }

    //Set all the attributes in one go
    public void setAttributes(int strength, int vitality, int agility, int charisma, int intellect) {
        this.strength = strength;
        this.vitality = vitality;
        this.agility = agility;
        this.charisma = charisma;
        this.intellect = intellect;
    }

    //Set all the skills in one go
    public void setSkills(int meleeSkill) {


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

    public void setHealth(int health) {
        this.health = health;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
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

    public int getHealth() {
        return health;
    }

    public int getStamina() {
        return stamina;
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
}
