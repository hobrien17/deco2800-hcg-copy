package com.deco2800.hcg.entities;

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
 * @author avryn
 */
public abstract class Character extends AbstractEntity {

    // TODO: Change class implementation to use a map to store the skills andattributes instead of having multiple redundant methods.
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
        this.speedX = 0.0f;
        this.speedY = 0.0f;
        this.level = 1;
        this.xp = 1;
        this.health = 20;
        this.stamina = 20;
        this.strength = 1;
        this.vitality = 1;
        this.agility = 5;
        this.charisma = 1;
        this.intellect = 1;
        this.meleeSkill = 1;
        this.carryWeight = 15 * strength;
        this.movementSpeed = 0.02f * agility;
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
        this.strength = strength;
        this.vitality = vitality;
        this.agility = agility;
        this.charisma = charisma;
        this.intellect = intellect;
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
    protected void setHealth(int health) {
        this.health = health;
    }

    /**
     *
     * @param stamina
     */
    protected void setStamina(int stamina) {
        this.stamina = stamina;
    }

    /**
     *
     * @param strength
     */
    protected void setStrength(int strength) {
        this.strength = strength;
    }

    /**
     *
     * @param vitality
     */
    protected void setVitality(int vitality) {
        this.vitality = vitality;
    }

    /**
     *
     * @param agility
     */
    protected void setAgility(int agility) {
        this.agility = agility;
    }

    /**
     *
     * @param charisma
     */
    protected void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    /**
     *
     * @param intellect
     */
    protected void setIntellect(int intellect) {
        this.intellect = intellect;
    }

    /**
     *
     * @param meleeSkill
     */
    protected void setMeleeSkill(int meleeSkill) {
        this.meleeSkill = meleeSkill;
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
    public int getHealth() {
        return health;
    }

    /**
     *
     * @return
     */
    public int getStamina() {
        return stamina;
    }

    /**
     *
     * @return
     */
    public int getStrength() {
        return strength;
    }

    /**
     *
     * @return
     */
    public int getVitality() {
        return vitality;
    }

    /**
     *
     * @return
     */
    public int getAgility() {
        return agility;
    }

    /**
     *
     * @return
     */
    public int getCharisma() {
        return charisma;
    }

    /**
     *
     * @return
     */
    public int getIntellect() {
        return intellect;
    }

    /**
     *
     * @return
     */
    public int getMeleeSkill() {
        return meleeSkill;
    }
}
