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
    private float posX = 0;
    private float posY = 0;
    private float posZ = 0;
    private float xLength = 1;
    private float yLength = 1;
    private float zLength = 1;
    private double radius = 0;
    private String texture = "";
    private int cooldown = -1;
    private int pellets = -1;
    
    public WeaponBuilder() {}
    
    public WeaponBuilder setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
        return this;
    }
    
    public WeaponBuilder setUser(AbstractEntity user) {
        this.user = user;
        return this;
    }
    
    public WeaponBuilder setPosX(float posX) {
        this.posX = posX;
        return this;
    }
    
    public WeaponBuilder setPosY(float posY) {
        this.posY = posY;
        return this;
    }
    
    public WeaponBuilder setPosZ(float posZ) {
        this.posZ = posZ;
        return this;
    }
    
    public WeaponBuilder setXLength(float xLength) {
        this.xLength = xLength;
        return this;
    }
    
    public WeaponBuilder setYLength(float yLength) {
        this.yLength = yLength;
        return this;
    }
    
    public WeaponBuilder setZLength(float zLength) {
        this.zLength = zLength;
        return this;
    }
    
    public WeaponBuilder setRadius(double radius) {
        this.radius = radius;
        return this;
    }
    
    public WeaponBuilder setTexture(String texture) {
        this.texture = texture;
        return this;
    }
    
    public WeaponBuilder setCooldown(int cooldown) {
        this.cooldown = cooldown;
        return this;
    }
    
    public WeaponBuilder setPellets(int pellets) {
        this.pellets = pellets;
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
        if(weaponType == null || user == null) {
            return null;
        } else if(weaponType == WeaponType.MACHINEGUN) {
            if(texture.equals("")) {
                //TODO: Get proper texture
                texture = "battle_seed";
            }
            
            if(cooldown == -1) {
                cooldown = 10;
            }
            
            return new Machinegun(posX, posY, posZ, xLength, yLength, zLength,
                weaponType, user, radius, texture, cooldown);
        } else if(weaponType == WeaponType.SHOTGUN) {
            if(texture.equals("")) {
                //TODO: Get proper texture
                texture = "battle_seed";
            }
            
            if(cooldown == -1) {
                cooldown = 30;
            }
            
            if(pellets == -1) {
                pellets = 6;
            }
            
            return new Shotgun(posX, posY, posZ, xLength, yLength, zLength,
                    weaponType, user, radius, texture, cooldown, pellets);
        } else if(weaponType == WeaponType.STARFALL) {
            if(texture.equals("")) {
                //TODO: Get proper texture
                texture = "battle_seed";
            }
            
            if(cooldown == -1) {
                cooldown = 50;
            }
            
            if(pellets == -1) {
                pellets = 30;
            }
            
            return new Stargun(posX, posY, posZ, xLength, yLength, zLength,
                    weaponType, user, radius, texture, cooldown, pellets);
        }
        
        return null;
    }
}
