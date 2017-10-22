package com.deco2800.hcg.weapons;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Tickable;
import com.deco2800.hcg.items.ItemRarity;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.NetworkManager;

/**
 * Stargun represents the a weapon that shoots stars as bullets
 */
public class Stargun extends Weapon implements Tickable {

	private NetworkManager networkManager = (NetworkManager) GameManager.get()
			.getManager(NetworkManager.class);
	
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
    public Stargun(float posX, float posY, float posZ, float xLength,
            float yLength, float zLength, WeaponType weaponType,
            AbstractEntity user, double radius, String texture,
            int cooldown, int pellets) {
        super(posX, posY, posZ, xLength, yLength, zLength, weaponType, user, radius,
                texture, cooldown);
        this.pellets = pellets;
    }
    
    @Override
    protected void playFireSound() {
        String soundName = "gun-stargun-shoot";
        soundManager.stopSound(soundName);
        soundManager.playSound(soundName);
    }
    
    @Override
    protected void fire() {
        for(int i = 0; i < this.pellets; i++) {
            shootBullet(this.aim.x + 5 * (float) networkManager.getNextGaussian(),
                    this.aim.y + 5 * (float) networkManager.getNextGaussian(),
                    this.getPosZ(), this.aim.x, this.aim.y);
        }
        playFireSound();

        // Muzzle flash
        muzzleFlashEnabled = 0;
    }

    public ItemRarity getRarity() {
        return ItemRarity.LEGENDARY;
    }
}
