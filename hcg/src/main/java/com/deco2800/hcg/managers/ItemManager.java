package com.deco2800.hcg.managers;

import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.garden_entities.seeds.Seed;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.WeaponItem;
import com.deco2800.hcg.items.stackable.HealthPotion;
import com.deco2800.hcg.items.stackable.MagicMushroom;

import com.deco2800.hcg.weapons.Weapon;
import com.deco2800.hcg.weapons.WeaponBuilder;
import com.deco2800.hcg.weapons.WeaponType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An class that creates new items based on the inputed parameters
 *
 * @author Henry O'Brien
 */
public class ItemManager extends Manager {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameManager.class);
    private static final Player PLAYER = ((PlayerManager) GameManager.get().getManager(PlayerManager.class)).getPlayer();

    /**
     * Returns a new item, defined by the given name
     *
     * @param name The name of the item to return
     * @return A new item
     */
    public Item getNew(String name) {
        switch (name) {
            case "sunflower_seed":
                return new Seed(Seed.Type.SUNFLOWER);
            case "explosive_seed":
                return new Seed(Seed.Type.EXPLOSIVE);
            case "fire_seed":
                return new Seed(Seed.Type.FIRE);
            case "grass_seed":
                return new Seed(Seed.Type.GRASS);
            case "ice_seed":
                return new Seed(Seed.Type.ICE);
            case "water_seed":
                return new Seed(Seed.Type.WATER);
            case "health_potion":
                return new HealthPotion(100);
            case "magic_mushroom":
                return new MagicMushroom();
            case "multigun":
            case "stargun":
            case "shotgun":
            case "machinegun":
                Weapon machinegun = new WeaponBuilder().setWeaponType(WeaponType.MACHINEGUN).setUser(PLAYER)
                        .setRadius(0.7).build();
                return new WeaponItem(machinegun, "Machine Gun", 10);
            default:
                LOGGER.warn("Unable to find given class, returning null");
                return null;
        }
    }
}
