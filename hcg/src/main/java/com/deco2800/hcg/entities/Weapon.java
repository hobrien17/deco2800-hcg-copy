package com.deco2800.hcg.entities;

import java.util.Random;

import com.badlogic.gdx.math.Vector3;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.InputManager;
import com.deco2800.hcg.managers.PlayerManager;

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
 * Weapon(float, float, float, WeaponType)
 * openFire()
 * ceaseFire()
 * updateAim(int, int)
 * updatePosition(int, int)
 * shootBullet(float, float, float, float, float)
 * fireWeapon()
 * setPosition()
 * onTick(long)
 * 
 * @author Bodhi Howe - Sinquios
 */

public class Weapon extends AbstractEntity implements Tickable {
    
    private PlayerManager playerManager;
    
    private float mouseFollowX;
    private float mouseFollowY;
    private int aimX;
    private int aimY;
    private float angle;
    private double radius;
    private boolean shoot;
    int counter;
    private WeaponType weaponType;
    private AbstractEntity user;
    
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
            WeaponType weaponType, AbstractEntity user) {        
        super(posX, posY, posZ, 0.6f, 0.6f, 1);
        
        this.radius = 0.7;
        this.shoot = false;
        this.counter = 0;
        this.weaponType = weaponType;
        this.user = user;
        this.setTexture("battle_seed");
        
        this.playerManager = (PlayerManager) GameManager.get()
                .getManager(PlayerManager.class);
    }

    public void setUser(AbstractEntity user){
        this.user = user;
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
        
        this.mouseFollowX = projX;
        this.mouseFollowY = projY;
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
    private void shootBullet(float posX, float posY, float posZ,
            float goalX, float goalY) {
        Bullet bullet = new Bullet(posX, posY, posZ,
                goalX, goalY, user);
        GameManager.get().getWorld().addEntity(bullet);
    }
    
    /**
     * Fires weapon using different firing method based on weapon type
     * Takes local variables aimX and aimY as firing coordinates
     */
    private void fireWeapon() {
        Vector3 worldCoords = GameManager.get().getCamera()
                .unproject(new Vector3(this.aimX, this.aimY, 0));
       
        // Conditional will be removed when Weapon is
        // converted into Interface/Factory
        if(weaponType == WeaponType.MACHINEGUN) {
            shootBullet(this.getPosX(), this.getPosY(), this.getPosZ(),
                    worldCoords.x, worldCoords.y);
        } else if(weaponType == WeaponType.SHOTGUN) {
            Random random = new Random();
            // Shoot bullets at random locations around cursor
            for(int i = 0; i < weaponType.getPellets(); i++) {
                shootBullet(this.getPosX() + 0.2f *
                        (float) random.nextGaussian(),
                        this.getPosY() + 0.2f *
                        (float) random.nextGaussian(),
                        this.getPosZ(),
                        worldCoords.x + 20 *
                        (float) random.nextGaussian(),
                        worldCoords.y + 20 *
                        (float) random.nextGaussian());    
            }            
        } else if(weaponType == WeaponType.STARFALL) {
            Random random = new Random();
            float projX;
            float projY;
            projX = worldCoords.x / 55f;
            projY = -(worldCoords.y - 32f / 2f) / 32f + projX;
            projX -= projY - projX;
            // Spawn bullets at random locations around cursor
            for(int i = 0; i < weaponType.getPellets(); i++) {
                shootBullet(projX + 5 *
                        (float) random.nextGaussian(),
                        projY + 5 *
                        (float) random.nextGaussian(),
                        this.getPosZ(),
                        worldCoords.x,
                        worldCoords.y);
            }            
        }
    }
    
    /**
     * Changes the position of the weapon in the gameworld
     * sets at certain distance from player character facing cursor
     */
    private void setPosition() {
        // Calculate the angle between the cursor and player
        float deltaX = playerManager.getPlayer().getPosX() -
                this.mouseFollowX;
        float deltaY = playerManager.getPlayer().getPosY() -
                this.mouseFollowY;
        this.angle = (float) (Math.atan2(deltaY, deltaX)) +
                (float) (Math.PI);
        // Set weapon position along angle
        setPosX(playerManager.getPlayer().getPosX() +
                (float) (radius * Math.cos(angle)));
        setPosY(playerManager.getPlayer().getPosY() +
                (float) (radius * Math.sin(angle)));
    }
    
    /**
     * Updates position of the weapon in the game world and
     * shoots weapon if weapon cooldown is reached and
     * button is pressed
     */
    @Override
    public void onTick(long gameTickCount) {
        setPosition();
        
        if(counter < weaponType.getCounter()) {
            counter++;
        } else if(shoot) {  
            counter = 0;
            fireWeapon();
        }
    }
    
}
