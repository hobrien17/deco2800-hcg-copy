package com.deco2800.hcg.weapons;

import java.util.Random;
import com.badlogic.gdx.math.Vector3;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Tickable;
import com.deco2800.hcg.managers.GameManager;

public class Shotgun extends Weapon implements Tickable {
    protected int pellets;
    
    public Shotgun(float posX, float posY, float posZ, float xLength,
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
        // Shoot bullets at random locations around cursor
        for(int i = 0; i < this.pellets; i++) {
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
    }
    
}
