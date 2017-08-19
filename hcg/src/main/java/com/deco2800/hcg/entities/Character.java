package com.deco2800.hcg.entities;

import com.deco2800.hcg.util.Box3D;

/**
 * The Character abstract class is to be extended by all Characters, both playable and non playable.
 * Character extends AbstractEntity so that the characters may be rendered to the game world.
 *
 * Characters have attributes and skills as listed below, skills and attributes are used to calculate numerous stats
 * in the background such as movement speed, health, stamina, and damages with various weapons.
 *
 * NPCs do not have to gain xp or level up but they should have a level (possibly displayed in game) that represents
 * its difficulty.
 *
 * level
 * 			  The character's level. Will increment by 1 once the player's xp reaches the level up
 * 			  threshold.
 * xp
 * 			  The character's current experience. Will tick over to 0 once the level up threshold
 * 			  is reached (level up thresholds TBD).
 * health
 * 		      The characters's health. The player's health is determined by the player's level and
 * 		      vitality attribute.
 * stamina
 * 			  The character's stamina. The player's stamina is determined by the player's level and
 * 			  agility attribute.
 * strength
 * 			  The character's strength.  Will increase the character's damage with melee with melee weapons,
 * 			  determines how much the player can have in their inventory.
 * vitality
 *            The character's vitality. Determines how much health is gained per level.
 * agility
 *            The character's agility. Determines how much the character's stamina is increased by each level,
 *            and also determines the character's movement speed
 * charisma
 *            The character's charisma. Determines how well the character's interactions with other
 *            characters go.
 * intellect
 *            The character's intellect. Determines how many skill points the player has to distribute to their
 *            skills each level.
 * meleeSkill
 *            The character's meleeSkill, determines how much damage is done with melee weapons.
 *
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
    protected int carryWeight;

    // Attributes
    protected int strength;
    protected int vitality;
    protected int agility;
    protected int charisma;
    protected int intellect;

    // Skills
    // TODO: Message weapons team to find out what categories of weapons they will implement
    protected int meleeSkill;

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
        this.carryWeight = 1;
        this.strength = 1;
        this.vitality = 1;
        this.agility = 1;
        this.charisma = 1;
        this.intellect = 1;
        this.meleeSkill = 1;
    }

    //Set all the attributes in one go
    private void setAttributes(int strength, int vitality, int agility, int charisma, int intellect) {
        this.strength = strength;
        this.vitality = vitality;
        this.agility = agility;
        this.charisma = charisma;
        this.intellect = intellect;
    }

    //Set all the skills in one go
    private void setSkills(int meleeSkill) {


    }

    private void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    private void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    private void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    private void setLevel(int level) {
        this.level = level;
    }

    private void setXp(int xp) {
        this.xp = xp;
    }

    private void setHealth(int health) {
        this.health = health;
    }

    private void setStamina(int stamina) {
        this.stamina = stamina;
    }

    private void setStrength(int strength) {
        this.strength = strength;
    }

    private void setVitality(int vitality) {
        this.vitality = vitality;
    }

    private void setAgility(int agility) {
        this.agility = agility;
    }

    private void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    private void setIntellect(int intellect) {
        this.intellect = intellect;
    }

    private void setMeleeSkill(int meleeSkill) { this.meleeSkill = meleeSkill; }

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
