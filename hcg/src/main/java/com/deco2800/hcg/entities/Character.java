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
     *            characters go
     * @param intellect
     *            The character's intellect. Effect TBD
     * @param meleeSkill
     *            The character's meleeSkill, determines how much damage is done with melee weapons
     */
    public Character(float posX, float posY, float posZ, float xLength, float yLength, float zLength,
                     float xRenderLength, float yRenderLength, boolean centered, float movementSpeed,
                     float speedX, float speedY, int level, int xp, int health, int stamina, int strength,
                     int vitality, int agility, int charisma, int intellect, int meleeSkill) {
        super(posX, posY, posZ, xLength, yLength, zLength, xRenderLength, yRenderLength, centered);
        this.movementSpeed = movementSpeed;
        this.speedX = speedX;
        this.speedY = speedY;
        this.level = level;
        this.xp = xp;
        this.health = health;
        this.stamina = stamina;
        this.strength = strength;
        this.vitality = vitality;
        this.agility = agility;
        this.charisma = charisma;
        this.intellect = intellect;
        this.meleeSkill = meleeSkill;
    }

}
