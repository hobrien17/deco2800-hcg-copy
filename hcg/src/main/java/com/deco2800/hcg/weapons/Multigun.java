package com.deco2800.hcg.weapons;

import java.util.ArrayList;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Tickable;
import com.deco2800.hcg.items.ItemRarity;

/**
 * Stargun represents the a weapon that shoots stars as bullets
 */
public class Multigun extends Weapon implements Tickable {
    
    private float arc;
    private ArrayList<Integer> ignoredBullets = new ArrayList<Integer>();
    
    /**
     * Creates a new Stargun at the given position
     * @param posX the x position
     * @param posY the y position
     * @param posZ the z position
     * @param xLength the length in terms of the x axis
     * @param yLength the length in terms of the y axis
     * @param zLength the length in terms of the z axis
     * @param weaponType the type of the weapon
     * @param user the user of the weapon
     * @param radius the area of effect of the weapon
     * @param texture the tecture of the weapon
     * @param cooldown the cooldown period of the weapon
     * @param pellets the pellets that the weapon uses
     */
    public Multigun(float posX, float posY, float posZ, float xLength,
            float yLength, float zLength, WeaponType weaponType,
            AbstractEntity user, double radius, String texture,
            int cooldown, int pellets, float arc) {
        super(posX, posY, posZ, xLength, yLength, zLength, weaponType, user, radius,
                texture, cooldown);
        this.arc = arc;
        this.pellets = pellets;
    }
    
    @Override
    protected void playFireSound() {
        String soundName = "gun-shotgun-shoot";
        soundManager.stopSound(soundName);
        soundManager.playSound(soundName);
    }
    
    public void ignoreBullet(int bullet) {
        ignoredBullets.add(Integer.valueOf(bullet));
    }
    
    public void resetBullets() {
        ignoredBullets.clear();
    }
    
    @Override
    protected void fire() {
        float deltaX = getPosX() - this.aim.x;
        float deltaY = getPosY() - this.aim.y;
        float angle = ((float) (Math.atan2(deltaY, deltaX)) + (float) (Math.PI)) - arc/2f;
        float shotAngle = arc / (pellets - 1);
        for(int i = 0; i < this.pellets; i++) {
            if(!ignoredBullets.contains(Integer.valueOf(i))) {
                shootBullet(this.getPosX(),
                        this.getPosY(),
                        this.getPosZ(),
                        (float) (this.getPosX() + 10 * Math.cos(angle)),
                        (float) (this.getPosY() + 10 * Math.sin(angle)));
            }
            angle = angle + shotAngle;
        }
        playFireSound();

        // Muzzle flash
        muzzleFlashEnabled = 1;
        muzzleFlashSize = 4;
        muzzleFlashStartTime = System.currentTimeMillis();
    }

    public ItemRarity getRarity() {
        return ItemRarity.RARE;
    }
}
