package com.deco2800.hcg.weapons;

import com.badlogic.gdx.math.Vector3;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.bullets.*;
import com.deco2800.hcg.entities.Tickable;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.SoundManager;

/**
 * Weapon class containing all values and methods required for
 * use of weapons within Hardcor3Gard3ning
 *
 * Currently takes updates on user inputs from the Player Class
 * to allow input to be handled before used by the weapon
 *
 * Public methods alter local variables so the weapon state
 * within the game world is only changed by onTick and private
 * helpers
 *
 * Some methods will be changed when Weapon is
 * turned into an Interface to be used with a factory pattern
 * to remove excess conditional statements and duplicate code
 *
 * @author Bodhi Howe - Sinquios
 */

public abstract class Weapon extends AbstractEntity implements Tickable {

    protected float followX;
    protected float followY;
    protected Vector3 aim;
    protected double radius;
    protected boolean shoot;
    protected int counter;
    protected int cooldown;
    protected WeaponType weaponType;
    protected AbstractEntity user;
    protected int bulletType;
    protected int pellets;

    private SoundManager soundManager;    

    /**
     * Constructor for Weapon objects.
     * Extends AbstractEntity for rendering
     * on the game world
     *
     * @param posX float starting X position of weapon withing gameworld
     * @param posY float starting Y position of weapon withing gameworld
     * @param posZ float starting Z position of weapon withing gameworld
     * @param weaponType WeaponType Enum with basic weapon information
     *
     */
    public Weapon(float posX, float posY, float posZ,
                  float xLength, float yLength, float zLength,
                  WeaponType weaponType, AbstractEntity user,
                  double radius, String texture, int cooldown) {
        super(posX, posY, posZ, xLength, yLength, zLength);

        this.shoot = false;
        this.counter = 0;
        this.followX = 0;
        this.followY = 0;
        this.aim = new Vector3(0, 0, 0);
        this.bulletType = 0;

        this.weaponType = weaponType;
        this.user = user;
        this.radius = radius;
        // TODO: Get proper weapon textures
        this.setTexture(texture);
        this.cooldown = cooldown;
        this.soundManager = (SoundManager) GameManager.get().getManager(SoundManager.class);        
    }

    /**
     * Returns the type of the weapon
     * @return the type of the weapon
     */
    public WeaponType getWeaponType() {
        return weaponType;
    }

    /**
     * Returns the weapon's user
     * @return the user of the weapon
     */
    public AbstractEntity getUser() {
        return user;
    }

    /**
     * Tells Weapon to start firing
     */
    public void openFire() {
        this.shoot = true;
    }

    /**
     * Tells Weapon to stop firing
     */
    public void ceaseFire() {
        this.shoot = false;
    }

    protected void playFireSound() {
        String soundName;
        switch (weaponType) {
            case MACHINEGUN:
                soundName = "gun-rifle-shoot";
                break;
            case SHOTGUN:
                soundName = "gun-shotgun-shoot";
                break;
            case STARFALL:
                soundName = "gun-stargun-shoot";
                break;
            default:
                soundName = "gun-rifle-shoot";
        }
        soundManager.stopSound(soundName);
        soundManager.playSound(soundName);
    }

    /**
     * Updates the weapon's coordinates for bullets to be fired to
     *
     * @param aim the aim vector
     */
    public void updateAim(Vector3 aim) {
        this.aim = new Vector3(aim);
    }

    /**
     * Updates the position the weapon is attempting to reach
     * within the game world
     *
     * @param worldX int x coordinate for weapon location
     * @param worldY int y coordinate for weapon location
     */
    //TODO: Remove
    public void updatePosition(float worldX, float worldY) {
        this.followX = worldX;
        this.followY = worldY;
    }
    

    /**
     * Creates a new bullet at given position
     * and traveling to given position
     *
     * @param posX float x coordinate of bullet start
     * @param posY float y coordinate of bullet start
     * @param posZ float z coordinate of bullet start
     * @param goalX float x coordinate of bullet end
     * @param goalY float y coordinate of bullet end
     */
    protected void shootBullet(float posX, float posY, float posZ,
                               float goalX, float goalY) {
        Bullet bullet = this.createBullet(posX, posY, posZ,
                goalX, goalY);
        GameManager.get().getWorld().addEntity(bullet);
    }

    /**
     * Fires weapon using different firing method based on weapon type
     * Takes local variables aimX and aimY as firing coordinates
     */
    protected void fireWeapon() {}

    /**
     * Changes the position of the weapon in the gameworld
     * sets at certain distance from player character facing cursor
     */
    //TODO: remove
    protected void setPosition() {
        // Calculate the angle between the cursor and player
        float deltaX = this.user.getPosX() - this.followX;
        float deltaY = this.user.getPosY() - this.followY;
        float angle = (float) (Math.atan2(deltaY, deltaX)) +
                (float) (Math.PI);
        // Set weapon position along angle
        setPosX(this.user.getPosX() +
                (float) (this.radius * Math.cos(angle)));
        setPosY(this.user.getPosY() +
                (float) (this.radius * Math.sin(angle)));
    }

    public void switchBullet() {
        bulletType++;
        if(bulletType > 3) {
            bulletType = 0;
        }
    }

    //TODO: incorporate into Bullet constructor
    public Bullet createBullet(float posX, float posY, float posZ,
                        float goalX, float goalY) {
        Bullet bullet;
        switch (bulletType) {
            case 0:
                bullet = new Bullet(posX, posY, posZ,
                        goalX, goalY, this.user, 1);
                break;
            case 1:
                bullet = new IceBullet(posX, posY, posZ,
                        goalX, goalY, this.user, 1);
                break;
            case 2:
                bullet = new FireBullet(posX, posY, posZ,
                        goalX, goalY, this.user, 1);
                break;
            default:
                bullet = new Bullet(posX, posY, posZ,
                        goalX, goalY, this.user, 1);
                break;
        }
        return bullet;
    }

    /**
     * Updates position of the weapon in the game world and
     * shoots weapon if weapon cooldown is reached and
     * button is pressed
     */
    @Override
    public void onTick(long gameTickCount) {
        //TODO: remove
        setPosition();

        if(this.counter < this.cooldown) {
            this.counter++;
        } else if(shoot) {
            this.counter = 0;
            fireWeapon();
        }
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Weapon)){
            return false;
        }
        Weapon weapon = (Weapon) o;
        if(weaponType!=weapon.weaponType){
            return false;
        }
        if(cooldown!=weapon.cooldown){
            return false;
        }
        if(pellets != weapon.pellets) {
            return false;
        }

        return super.equals(weapon);
    }

    @Override
    public int hashCode() {
        int result = 11;
        result = 31 * result + weaponType.hashCode();
        result = 31 * result + cooldown;
        result = 31 * result + pellets;
        result = 31 * result + super.hashCode();
        return result;
    }
}

