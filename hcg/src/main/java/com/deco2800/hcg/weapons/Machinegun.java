package com.deco2800.hcg.weapons;

import com.badlogic.gdx.math.Vector3;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Tickable;
import com.deco2800.hcg.managers.GameManager;

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
    }
    
    @Override
    protected void fireWeapon() {
        Vector3 worldCoords = GameManager.get().getCamera()
                .unproject(new Vector3(this.aimX, this.aimY, 0));

        System.out.println(worldCoords.x);
        shootBullet(this.getPosX(), this.getPosY(), this.getPosZ(),
                worldCoords.x, worldCoords.y);
        playFireSound();
    }
    
}
