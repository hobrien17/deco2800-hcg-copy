package com.deco2800.hcg.weapons;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Tickable;
import com.deco2800.hcg.items.ItemRarity;

/**
 * The Machinegun class represents a machine gun type weapon
 */
public class Machinegun extends Weapon implements Tickable {

    /**
     * Creates a new Machinegun at the given position
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
     */
    public Machinegun(float posX, float posY, float posZ, float xLength,
            float yLength, float zLength, WeaponType weaponType,
            AbstractEntity user, double radius, String texture, int cooldown) {
        super(posX, posY, posZ, xLength, yLength, zLength, weaponType, user, radius,
                texture, cooldown);
        this.pellets = 1;
    }
    
    @Override
    protected void playFireSound() {
        String soundName = "gun-rifle-shoot";
        soundManager.stopSound(soundName);
        soundManager.playSound(soundName);
    }
    
    @Override
    protected void fire() {
        shootBullet(this.getPosX(), this.getPosY(), this.getPosZ(),
                this.aim.x, this.aim.y);
        playFireSound();

        // Muzzle flash
        muzzleFlashEnabled = 1;
        muzzleFlashSize = 3;
        muzzleFlashStartTime = System.currentTimeMillis();
    }

    public ItemRarity getRarity() {
        return ItemRarity.UNCOMMON;
    }
}
