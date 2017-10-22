package com.deco2800.hcg.weapons;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.Tickable;
import com.deco2800.hcg.entities.bullets.Bullet;
import com.deco2800.hcg.entities.bullets.BulletType;
import com.deco2800.hcg.entities.bullets.ExplosionBullet;
import com.deco2800.hcg.entities.bullets.FireBullet;
import com.deco2800.hcg.entities.bullets.GrassBullet;
import com.deco2800.hcg.entities.bullets.HomingBullet;
import com.deco2800.hcg.entities.bullets.IceBullet;
import com.deco2800.hcg.entities.garden_entities.seeds.Seed;
import com.deco2800.hcg.inventory.Inventory;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.ItemRarity;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.SoundManager;
import com.deco2800.hcg.shading.LightEmitter;

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

public abstract class Weapon extends AbstractEntity implements Tickable, LightEmitter {

    private static final int MUZZLE_FLASH_TIME = 50;
    protected Vector3 follow;
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
    protected String texture;
    protected int muzzleFlashEnabled;
    protected float muzzleFlashSize;
    protected long muzzleFlashStartTime;

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
        this.follow = new Vector3(0, 0, 0);
        this.aim = new Vector3(0, 0, 0);
        this.bulletType = BulletType.BASIC;

        this.weaponType = weaponType;
        this.user = user;
        this.radius = radius;
        this.setTexture("blank");
        this.texture = texture;
        this.cooldown = cooldown;
        this.muzzleFlashEnabled = 0;
        this.muzzleFlashSize = 3;
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
    public void updatePosition(Vector3 position) {
        this.follow = new Vector3(position);
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
        switch (bulletType) {
            case BASIC:
                bullet = new Bullet(posX, posY, posZ,
                        goalX, goalY, this.user, 1, 0.5f, 200);
                break;
            case ICE:
                bullet = new IceBullet(posX, posY, posZ,
                        goalX, goalY, this.user, 1, 0.5f, 0);
                break;
            case FIRE:
                bullet = new FireBullet(posX, posY, posZ,
                        goalX, goalY, this.user, 1, 0.5f, 250);
                break;
            case EXPLOSION:
                bullet = new ExplosionBullet(posX, posY, posZ,
                        goalX, goalY, this.user, 1, 0.5f, 500);
                break;
            case GRASS:
                bullet = new GrassBullet(posX, posY, posZ,
                        goalX, goalY, this.user, 1, 0.5f, 250);
                break;
            case HOMING:
                bullet = new HomingBullet(posX, posY, posZ,
                        goalX, goalY, this.user, 1, 0.5f, 250);
                break;
            default:
                bullet = new Bullet(posX, posY, posZ,
                        goalX, goalY, this.user, 1, 0.5f, 250);
                break;
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
                bulletType = BulletType.GRASS;
                break;
            case GRASS:
                bulletType = BulletType.HOMING;
                break;
            case HOMING:
                bulletType = BulletType.BASIC;
                break;
            default:
                bulletType = BulletType.BASIC;
                break;
        }
    }
    
    protected Seed bulletToSeed() {
    	switch(bulletType) {
    	case BASIC:
            return new Seed(Seed.Type.SUNFLOWER);
        case ICE:
        	return new Seed(Seed.Type.ICE);
        case FIRE:
        	return new Seed(Seed.Type.FIRE);
        case EXPLOSION:
        	return new Seed(Seed.Type.EXPLOSIVE);
        case GRASS:
        	return new Seed(Seed.Type.GRASS);
        case HOMING:
        	return new Seed(Seed.Type.WATER);
        default:
            return null;
    	}
    }
    
    /**
     * Caries out a fire sequence
     */
    protected void fire() {
    	shootBullet(this.getPosX(), this.getPosY(), this.getPosZ(),
                this.aim.x, this.aim.y);
        playFireSound();
        // Muzzle flash
        muzzleFlashEnabled = 1;
        muzzleFlashStartTime = System.currentTimeMillis();
    }

    /**
     * Fires weapon using different firing method based on weapon type
     * Takes local variables aimX and aimY as firing coordinates
     */
    protected void fireWeapon() {
    	if(!(user instanceof Player)) {
    		fire();
    	} else {
        	Inventory inventory = ((Player)user).getInventory();
        	Seed seed = bulletToSeed();
        	int count = 0;
        	for(Item item : inventory) {
        		if(item instanceof Seed && ((Seed) item).getType().equals(seed.getType())) {
        			count += item.getStackSize();
        		}
        	}
        	System.out.println(count);
        	System.out.println(pellets);
        	if(count >= this.pellets) {
        		fire();
        		for(int i = 0; i < this.pellets; i++) {
        			inventory.removeItem(seed);
        		}
        	}
    	}
    }

    /**
     * Changes the position of the weapon in the gameworld
     * sets at certain distance from player character facing cursor
     */
    protected void setPosition() {
        // Calculate the angle between the cursor and player
        float deltaX = this.user.getPosX() - this.follow.x;
        float deltaY = this.user.getPosY() - this.follow.y;
        if(deltaX < 0.01 && deltaX > -0.01 && deltaY < 0.01 && deltaY > -0.01) {
            setPosX(this.user.getPosX());
            setPosY(this.user.getPosY());
            return;
        }
        float angle = (float) (Math.atan2(deltaY, deltaX)) +
                (float) (Math.PI);
        
        if(!"blank".equals(texture)) {
        //Update texture for angle
            if(15 * Math.PI / 8 < angle || angle < Math.PI / 8) {
                this.setTexture(texture + "_ne");
                this.growRender(-(this.getXRenderLength() - 0.6f), -(this.getYRenderLength() - 0.6f));
            } else if(Math.PI / 8 <= angle && angle <= 3 * Math.PI / 8) {
                this.setTexture(texture + "_e");
                this.growRender(-(this.getXRenderLength() - 0.7f), -(this.getYRenderLength() - 0.7f));
            } else if(3 * Math.PI / 8 < angle && angle < 5 * Math.PI / 8) {
                this.setTexture(texture + "_se");
                this.growRender(-(this.getXRenderLength() - 0.6f), -(this.getYRenderLength() - 0.6f));
            } else if(5 * Math.PI / 8 <= angle && angle <= 7 * Math.PI / 8) {
                this.setTexture(texture + "_s");
                this.growRender(-(this.getXRenderLength() - 0.15f), -(this.getYRenderLength() - 0.07f));
            } else if(7 * Math.PI / 8 < angle && angle < 9 * Math.PI / 8) {
                this.setTexture(texture + "_sw");
                this.growRender(-(this.getXRenderLength() - 0.6f), -(this.getYRenderLength() - 0.6f));
            }  else if(9 * Math.PI / 8 <= angle && angle <= 11 * Math.PI / 8) {
                this.setTexture(texture + "_w");
                this.growRender(-(this.getXRenderLength() - 0.7f), -(this.getYRenderLength() - 0.7f));
            } else if(11 * Math.PI / 8 < angle && angle < 13 * Math.PI / 8) {
                this.setTexture(texture + "_nw");
                this.growRender(-(this.getXRenderLength() - 0.6f), -(this.getYRenderLength() - 0.6f));
            } else if(13 * Math.PI / 8 <= angle && angle <= 15 * Math.PI / 8) {
                this.setTexture(texture + "_n");
                this.growRender(-(this.getXRenderLength() - 0.15f), -(this.getYRenderLength() - 0.07f));
            }
        }
        
        // Set weapon position along angle
        setPosX(this.user.getPosX() +
                (float) (this.radius * Math.cos(angle)));
        setPosY(this.user.getPosY() +
                (float) (this.radius * Math.sin(angle)));
    }

    /**
     * Grab the current light colour of this entity.
     *
     * @return This entity's current light colour.
     */
    @Override
    public Color getLightColour() {
        return Color.YELLOW;
    }

    /**
     * Grab the current light power of this entity. If it shouldn't emit light right
     * now, return 0.
     *
     * @return This entity's current light power.
     */
    @Override
    public float getLightPower() {
//        return Float.MAX_VALUE * muzzleFlash;
        return muzzleFlashSize * muzzleFlashEnabled;
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

        // Handle muzzle flash
        if (muzzleFlashEnabled == 1) {
            if (System.currentTimeMillis() - muzzleFlashStartTime >= MUZZLE_FLASH_TIME) {
                muzzleFlashEnabled = 0;
            }
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
    
    public ItemRarity getRarity() {
        return ItemRarity.COMMON;
    }
}