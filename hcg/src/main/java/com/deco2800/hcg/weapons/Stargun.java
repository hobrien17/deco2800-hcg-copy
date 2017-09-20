package com.deco2800.hcg.weapons;

import java.util.Random;
import com.badlogic.gdx.math.Vector3;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Tickable;
import com.deco2800.hcg.managers.GameManager;

/**
 * Stargun represents the a weapon that shoots stars as bullets
 */
public class Stargun extends Weapon implements Tickable {
    protected int pellets;

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
    protected void fireWeapon() {
        Vector3 worldCoords = GameManager.get().getCamera()
                .unproject(new Vector3(this.aimX, this.aimY, 0));
        Random random = new Random();
        float projX;
        float projY;
        projX = worldCoords.x / 55f;
        projY = -(worldCoords.y - 32f / 2f) / 32f + projX;
        projX -= projY - projX;
        // Spawn bullets at random locations around cursor
        for(int i = 0; i < this.pellets; i++) {
            shootBullet(projX + 5 * (float) random.nextGaussian(),
                    projY + 5 * (float) random.nextGaussian(),
                    this.getPosZ(), worldCoords.x, worldCoords.y);
        }
        playFireSound();
    }
    
}
