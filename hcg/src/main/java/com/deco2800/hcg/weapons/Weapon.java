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
    protected BulletType bulletType;
    protected int pellets;
    protected SoundManager soundManager;    

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
        this.bulletType = BulletType.BASIC;

        this.weaponType = weaponType;
        this.user = user;
        this.radius = radius;
        // TODO: Get proper weapon textures
        this.setTexture(texture);
        this.cooldown = cooldown;
        this.soundManager = (SoundManager) GameManager.get().getManager(SoundManager.class);        
    }
    
    /**
     * Sets the type of bullet fired
     * 
     * @param BulletType bulletType
     */
    public void setBulletType(BulletType bulletType) {
        this.bulletType = bulletType;
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
    
    /**
     * Makes Sound Manager play firing sound for the weapon
     * Stops the sound first if it is already being played
     */
    protected void playFireSound() {
        String soundName = "gun-rifle-shoot";
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
        Bullet bullet;
        if(this.weaponType == WeaponType.GRENADELAUNCHER) {
            bullet = new Grenade(posX, posY, posZ, goalX, goalY,
                    0, 0.6f, 0.6f, 1, this.user, -1);
        } else {
            switch (bulletType) {
                case BASIC:
                    bullet = new Bullet(posX, posY, posZ,
                            goalX, goalY, this.user, 1);
                    break;
                case ICE:
                    bullet = new IceBullet(posX, posY, posZ,
                            goalX, goalY, this.user, 1);
                    break;
                case FIRE:
                    bullet = new FireBullet(posX, posY, posZ,
                            goalX, goalY, this.user, 1);
                    break;
                case EXPLOSION:
                    bullet = new ExplosionBullet(posX, posY, posZ,
                            goalX, goalY, this.user, 1);
                    break;
                default:
                    bullet = new Bullet(posX, posY, posZ,
                            goalX, goalY, this.user, 1);
                    break;
            }
        }
        GameManager.get().getWorld().addEntity(bullet);
    }
    
    public void switchBullet() {
        switch (bulletType) {
        case BASIC:
            bulletType = BulletType.ICE;
            break;
        case ICE:
            bulletType = BulletType.FIRE;
            break;
        case FIRE:
            bulletType = BulletType.EXPLOSION;
            break;
        case EXPLOSION:
            bulletType = BulletType.BASIC;
            break;
        default:
            bulletType = BulletType.BASIC;
            break;
    }
    }

    /**
     * Fires weapon using different firing method based on weapon type
     * Takes local variables aimX and aimY as firing coordinates
     */
    protected void fireWeapon() {
        shootBullet(this.getPosX(), this.getPosY(), this.getPosZ(),
                this.aim.x, this.aim.y);
        playFireSound();
    }

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

