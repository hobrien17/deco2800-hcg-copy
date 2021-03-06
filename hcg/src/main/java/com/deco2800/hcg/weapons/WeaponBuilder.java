package com.deco2800.hcg.weapons;

import com.deco2800.hcg.entities.AbstractEntity;


/**
 * WeaponBuilder is a Builder Pattern design made for creating weapons
 * Call the constructor and then each method with valid input to set
 * the variables, otherwise they will go to default values.
 * weaponType and user are required to be set.
 * 
 * @author Bodhi Howe - @sinquios
 */
public class WeaponBuilder {
    private WeaponType weaponType = null;
    private AbstractEntity user = null;
    private float posX = 0.0f;
    private float posY = 0.0f;
    private float posZ = 0.0f;
    private float xLength = 1.0f;
    private float yLength = 1.0f;
    private float zLength = 1.0f;
    private double radius = 0.0f;
    private String texture = "";
    private int cooldown = -1;
    private int pellets = -1;
    private float arc = 0.0f;

    /**
     * Initialises a new WeaponBuilder
     */
    public WeaponBuilder() {
        //deliberately empty
    }

    /**
     * Sets the weapon type of the weapon builder
     * @param weaponType the type to set the weapon builder to
     * @return the weapon builder
     */
    public WeaponBuilder setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
        return this;
    }

    /**
     * Sets the current user of the weapon builder
     * @param user the user to set the weapon builder to
     * @return the weapon builder
     */
    public WeaponBuilder setUser(AbstractEntity user) {
        this.user = user;
        return this;
    }

    /**
     * Sets the x position of the weapon builder
     * @param posX the new x position
     * @return the weapon builder
     */
    public WeaponBuilder setPosX(float posX) {
        this.posX = posX;
        return this;
    }

    /**
     * Sets the y position of the weapon builder
     * @param posY the new y position
     * @return the weapon builder
     */
    public WeaponBuilder setPosY(float posY) {
        this.posY = posY;
        return this;
    }

    /**
     * Sets the x position of the weapon builder
     * @param posZ the new z position
     * @return the weapon builder
     */
    public WeaponBuilder setPosZ(float posZ) {
        this.posZ = posZ;
        return this;
    }

    /**
     * Sets the x-length of the weapon builder
     * @param xLength the x length to set it to
     * @return the weapon builder
     */
    public WeaponBuilder setXLength(float xLength) {
        this.xLength = xLength;
        return this;
    }

    /**
     * Sets the y-length of the weapon builder
     * @param yLength the y length to set it to
     * @return the weapon builder
     */
    public WeaponBuilder setYLength(float yLength) {
        this.yLength = yLength;
        return this;
    }

    /**
     * Sets the z-length of the weapon builder
     * @param zLength the z length to set it to
     * @return the weapon builder
     */
    public WeaponBuilder setZLength(float zLength) {
        this.zLength = zLength;
        return this;
    }

    /**
     * Sets the radius of the weapon builder
     * @param radius the radius to set it to
     * @return the weapon builder
     */
    public WeaponBuilder setRadius(double radius) {
        this.radius = radius;
        return this;
    }

    /**
     * Sets the texture of the weapon builder
     * @param texture the texture to set it to
     * @return the weapon builder
     */
    public WeaponBuilder setTexture(String texture) {
        this.texture = texture;
        return this;
    }

    /**
     * Sets the cooldown period of the weapon builder
     * @param cooldown the cooldown period to set it to
     * @return the weapon builder
     */
    public WeaponBuilder setCooldown(int cooldown) {
        this.cooldown = cooldown;
        return this;
    }

    /**
     * Sets the pellets of the weapon builder
     * @param pellets the pellet type to set it to
     * @return the weapon builder
     */
    public WeaponBuilder setPellets(int pellets) {
        this.pellets = pellets;
        return this;
    }
    
    /**
     * Sets the arc of the weapon builder
     * @param arc the arc to set it to
     * @return the weapon builder
     */
    public WeaponBuilder setArc(float arc) {
        this.arc = arc;
        return this;
    }
    
    /**
     * Call this method after setting desired values to return
     * a new Weapon object.
     * 
     * @ensure Weapon = null if weaponType == null || user == null
     * 
     * @return Weapon object
     */
    public Weapon build() {
        if(weaponType == null) {
            return null;
        }

        //Should not use equality on floating point numbers
        if((int) (radius * 100) == 0) {
            radius = 0.1f;
        }
        
        if(weaponType == WeaponType.MACHINEGUN) {
            if("".equals(texture)) {
                texture = "machinegun";
            }
            
            if(cooldown == -1) {
                cooldown = 10;
            }
            
            return new Machinegun(posX, posY, posZ, xLength, yLength, zLength,
                weaponType, user, radius, texture, cooldown);
        } else if(weaponType == WeaponType.SHOTGUN) {
            if("".equals(texture)) {
                texture = "shotgun";
            }
            
            if(cooldown == -1) {
                cooldown = 30;
            }
            
            if(pellets == -1) {
                pellets = 6;
            }
            
            return new Shotgun(posX, posY, posZ, xLength, yLength, zLength,
                    weaponType, user, radius, texture, cooldown, pellets);
        } else if(weaponType == WeaponType.STARGUN) {
            if("".equals(texture)) {
                texture = "stargun";
            }
            
            if(cooldown == -1) {
                cooldown = 50;
            }
            
            if(pellets == -1) {
                pellets = 30;
            }
            
            return new Stargun(posX, posY, posZ, xLength, yLength, zLength,
                    weaponType, user, radius, texture, cooldown, pellets);
        } else if(weaponType == WeaponType.MULTIGUN) {
            if("".equals(texture)) {
                texture = "multigun";
            }
            
            if(cooldown == -1) {
                cooldown = 20;
            }
            
            if(pellets == -1) {
                pellets = 3;
            }
            
            if(arc <= 0.5f || arc >= 2.0f * Math.PI) {
                arc = (float) (Math.PI / 4.0f);
            }
            
            return new Multigun(posX, posY, posZ, xLength, yLength, zLength,
                    weaponType, user, radius, texture, cooldown, pellets, arc);
        }
        
        return null;
    }
}
