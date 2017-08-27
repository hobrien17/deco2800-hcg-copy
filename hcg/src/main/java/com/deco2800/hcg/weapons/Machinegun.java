package com.deco2800.hcg.weapons;

import java.util.Random;
import com.badlogic.gdx.math.Vector3;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Tickable;
import com.deco2800.hcg.managers.GameManager;

public class Machinegun extends Weapon implements Tickable {

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
        shootBullet(this.getPosX(), this.getPosY(), this.getPosZ(),
                worldCoords.x, worldCoords.y);
    }
    
}
