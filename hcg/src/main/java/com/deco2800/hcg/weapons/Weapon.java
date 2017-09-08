package com.deco2800.hcg.weapons;

import com.badlogic.gdx.math.Vector3;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Bullet;
import com.deco2800.hcg.entities.Tickable;
import com.deco2800.hcg.managers.GameManager;

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
    protected int aimX;
    protected int aimY;
    protected double radius;
    protected boolean shoot;
    protected int counter;
    protected int cooldown;
    protected WeaponType weaponType;
    protected AbstractEntity user;
    
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
        this.aimX = 0;
        this.aimY = 0;
        
        this.weaponType = weaponType;
        this.user = user;
        this.radius = radius;
        // TODO: Get proper weapon textures
        this.setTexture(texture);
        this.cooldown = cooldown;
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
     * Updates the weapon's coordinates for bullets to be fired to
     * 
     * @param screenX int x coordinate for aim location
     * @param screenY int y coordinate for aim location
     */
    public void updateAim(int screenX, int screenY) {
        this.aimX = screenX;
        this.aimY = screenY;
    }
    
    /**
     * Updates the position the weapon is attempting to reach
     * within the game world
     * 
     * @param screenX int x coordinate for weapon location
     * @param screenY int y coordinate for weapon location
     */
    public void updatePosition(int screenX, int screenY) {
        // Convert screen coordinates into game world coordinates
        Vector3 worldCoords = GameManager.get().getCamera()
                .unproject(new Vector3(screenX, screenY, 0));
        
        float projX;
        float projY;

        projX = worldCoords.x / 55f;
        projY = -(worldCoords.y - 32f / 2f) / 32f + projX;
        projX -= projY - projX;
        
        this.followX = projX;
        this.followY = projY;
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
        Bullet bullet = new Bullet(posX, posY, posZ,
                goalX, goalY, this.user);
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
    	if((int)Double.doubleToLongBits(radius)!=(int)Double.doubleToLongBits(weapon.radius)){
    		return false;
    	}
    	
        return super.equals(weapon);
    }
    
    @Override
    public int hashCode() {
        int result = 11;
        result = 31 * result + weaponType.hashCode();
        result = 31 * result + cooldown;
        result = 31 * result + (int)Double.doubleToLongBits(radius);
        result = 31 * result + super.hashCode();
        return result;
    }
}
