package com.deco2800.hcg.weapons;

import java.util.Random;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Tickable;

/**
 * The Shotgun class represents a shotgun weapon
 */
public class Grenadelauncher extends Weapon implements Tickable {
    /**
     * Creates a new Shotgun at the given position
     * @param posX the x position
     * @param posY the y position
     * @param posZ the z position
     * @param xLength the length in terms of the x axis
     * @param yLength the length in terms of the y axis
     * @param zLength the length in terms of the z axis
     * @param weaponType the weapon type
     * @param user the weapon user
     * @param radius the area of effect of the weapon
     * @param texture the texture of the weapon
     * @param cooldown the cooldown period of the weapon
     * @param pellets the pellet type of the weapon
     */
    public Grenadelauncher(float posX, float posY, float posZ, float xLength,
            float yLength, float zLength, WeaponType weaponType,
            AbstractEntity user, double radius, String texture,
            int cooldown) {
        super(posX, posY, posZ, xLength, yLength, zLength, weaponType, user, radius,
                texture, cooldown);
    }
    
    @Override
    protected void playFireSound() {
        String soundName = "gun-grenadelauncher-shoot";
        soundManager.stopSound(soundName);
        soundManager.playSound(soundName);
    }
    
    @Override
    protected void fireWeapon() {
        shootBullet(this.getPosX(), this.getPosY(), this.getPosZ(),
                this.aim.x, this.aim.y);
        playFireSound();
    }
    
}
