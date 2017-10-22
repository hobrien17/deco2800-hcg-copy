package com.deco2800.hcg.managers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.garden_entities.seeds.Seed;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.WeaponItem;
import com.deco2800.hcg.items.stackable.HealthPotion;
import com.deco2800.hcg.items.stackable.Key;
import com.deco2800.hcg.items.stackable.MagicMushroom;
import com.deco2800.hcg.items.stackable.SmallMushroom;
import com.deco2800.hcg.items.stackable.SpeedPotion;
import com.deco2800.hcg.items.tools.BugSpray;
import com.deco2800.hcg.items.tools.Fertiliser;
import com.deco2800.hcg.items.tools.Hoe;
import com.deco2800.hcg.items.tools.Trowel;
import com.deco2800.hcg.weapons.Weapon;
import com.deco2800.hcg.weapons.WeaponBuilder;
import com.deco2800.hcg.weapons.WeaponType;

/**
 * An class that creates new items based on the inputed parameters
 *
 * @author Henry O'Brien
 */
public class ItemManager extends Manager {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameManager.class);

    private Player player;
    
    /**
     * Returns a new item, defined by the given name
     *
     * @param name The name of the item to return
     * @return A new item
     */
    public Item getNew(String name) {
    	player = ((PlayerManager) GameManager.get().getManager(PlayerManager.class)).getPlayer();
    	
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
            case "large_mushroom":
                return new MagicMushroom();
            case "small_mushroom":
                return new SmallMushroom();
            case "fertiliser":
            	return new Fertiliser();
            case "sausage":
            	return new HealthPotion(100);
            case "snag":
            	return new SpeedPotion();
            case "bug_spray":
            	return new BugSpray();
            case "hoe":
            	return new Hoe();
            case "trowel":
            	return new Trowel();
            case "bunnings_snag":
                return new SpeedPotion();
            case "magic_mushroom":
                return new MagicMushroom();
            case "key":
                return new Key();
            case "multigun":
            	Weapon multigun = new WeaponBuilder().setWeaponType(WeaponType.MULTIGUN).setUser(player)
            			.setRadius(0.7).build();
            	return new WeaponItem(multigun, "Multi-gun", 10);
            case "stargun":
            	Weapon stargun = new WeaponBuilder().setWeaponType(WeaponType.STARGUN).setUser(player)
    					.setRadius(0.7).build();
            	return new WeaponItem(stargun, "Starfall", 10);
            case "shotgun":
            	Weapon shotgun = new WeaponBuilder().setWeaponType(WeaponType.SHOTGUN).setUser(player)
    					.setRadius(0.7).build();
            	return new WeaponItem(shotgun, "Shotgun", 10);
            case "machinegun":
                Weapon machinegun = new WeaponBuilder().setWeaponType(WeaponType.MACHINEGUN).setUser(player)
                        .setRadius(0.7).build();
                return new WeaponItem(machinegun, "Machine Gun", 10);
            default:
                LOGGER.warn("Unable to find given class, returning null");
                return null;
        }
    }
}
