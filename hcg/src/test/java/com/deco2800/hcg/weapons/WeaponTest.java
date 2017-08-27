package com.deco2800.hcg.weapons;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.*;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.managers.GameManager;

public class WeaponTest {
    
    private Player player = mock(Player.class);
    
    private Weapon weapon = new WeaponBuilder()
            .setWeaponType(WeaponType.MACHINEGUN)
            .setUser(player).setCooldown(5).build();
    
    
    @Test
    public void valueCheck() {
        Weapon weapon2 = new WeaponBuilder()
                .setWeaponType(WeaponType.SHOTGUN)
                .setUser(player)
                .setCooldown(10)
                .setPellets(15)
                .setPosX(10)
                .setPosY(10)
                .setPosZ(10)
                .setRadius(2.5)
                .setTexture("storm_bolter")
                .setXLength(20)
                .setYLength(20)
                .setZLength(20)
                .build();
        Weapon star = new WeaponBuilder()
                .setWeaponType(WeaponType.STARFALL)
                .setUser(player)
                .setRadius(10)
                .build();
        Shotgun weapon3 = (Shotgun) weapon2;
        Stargun weapon4 = (Stargun) star;
        assertEquals(weapon.cooldown, 5);
        assertTrue(weapon.radius == 0);
        assertEquals(weapon2.cooldown, 10);
        assertEquals(weapon.aimX, 0);
        assertEquals(weapon.aimY, 0);
        assertTrue(weapon.followX == weapon2.followX);
        assertTrue(weapon.radius == 0);
        assertEquals(weapon.shoot, false);
        assertEquals(weapon.user, player);
        assertEquals(weapon.user, weapon2.user);
        assertEquals(weapon.user, weapon.getUser());
        assertFalse(weapon.weaponType == weapon2.weaponType);
        assertTrue(weapon.weaponType == weapon.getWeaponType());
        assertEquals(weapon2.getWeaponType(), WeaponType.SHOTGUN);
        assertEquals(weapon3.pellets, 15);
        assertEquals(weapon2, weapon3);
        assertTrue(weapon2.getPosX() == 10);
        assertTrue(weapon2.getPosY() == 10);
        assertTrue(weapon2.getPosZ() == 10);
        assertTrue(weapon2.radius == 2.5);
        assertEquals(weapon2.getTexture(), "storm_bolter");
        assertTrue(weapon2.getXLength() == 20);
        assertTrue(weapon2.getYLength() == 20);
        assertTrue(weapon2.getZLength() == 20);
        assertFalse(weapon2.equals(star));
        assertEquals(star, weapon4);
    }
    
    @Test
    public void firingCheck() {
        weapon.updateAim(20, 20);
        weapon.openFire();
        assertTrue(weapon.shoot);
        assertTrue(weapon.aimX == 20);
        assertTrue(weapon.aimY == 20);
    }

}
