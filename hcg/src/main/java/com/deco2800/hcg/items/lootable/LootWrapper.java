package com.deco2800.hcg.items.lootable;

import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.WeaponItem;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.ItemManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.weapons.Weapon;
import com.deco2800.hcg.weapons.WeaponBuilder;
import com.deco2800.hcg.weapons.WeaponType;

/**
 * A wrapper class that keeps track of one or more pieces of loot
 * 
 * @author Henry O'Brien
 *
 */
public class LootWrapper {
	private static final Player PLAYER = ((PlayerManager) GameManager.get().getManager(PlayerManager.class)).getPlayer();
	private String name;
	private int amount;
	private float rarity;
	Item item;
	
	/**
	 * Stores a single piece of loot of type name
	 * 
	 * @param name
	 * 			the name of the loot
	 * @param rarity
	 * 			the rarity of the loot contents
	 */
	public LootWrapper(String name, float rarity) {
		if (name == null) {
			throw new IllegalArgumentException("Name cannot be null.");
		}
		if (rarity < 0f || rarity > 1.0f) {
			throw new IllegalArgumentException("Rarity must be between 0 and 1.");
		}
		this.name = name;
		item = ((ItemManager)GameManager.get().getManager(ItemManager.class)).getNew(name);
		if (item instanceof WeaponItem) {
			applyWeaponRarity();
		}

		this.amount = 1;
		this.rarity = rarity;
	}
	
	/**
	 * Stores between minAmount and maxAmount pieces of loot
	 * 
	 * @param name
	 * 			the name of the loot
	 * @param minAmount
	 * 			the min number of pieces to store
	 * @param maxAmount
	 * 			the max number of pieces to store
	 */
	public LootWrapper(String name, int minAmount, int maxAmount) {
		this.name = name;
		amount = (int)(Math.random() * ((maxAmount - minAmount) + 1)) + minAmount;
		item = ((ItemManager)GameManager.get().getManager(ItemManager.class)).getNew(name);
		if (amount > 1) {
			item.setStackSize(amount);
		}
	}

	/**
	 * Modifies the weapon stored in the WeaponItem depending on the rarity of the loot.
	 * The rarer the loot, the better the weapon stats will be.
	 */
	private void applyWeaponRarity() {
		Weapon newWeapon = null;
		int newCooldown;
		int newPellets;
		float newArc;
		String prefix;

		// Adjust the weapon stats
		switch(((WeaponItem) item).getWeapon().getWeaponType()) {
			case MACHINEGUN:
				if (rarity <= 0.1f) {
					newCooldown = 4;
				} else if (rarity <= 0.5f) {
					newCooldown = 6;
				} else {
					newCooldown = 10;
				}
				newWeapon = new WeaponBuilder().setWeaponType(WeaponType.MACHINEGUN).setUser(PLAYER)
						.setCooldown(newCooldown).build();
				break;
			case SHOTGUN:
				if (rarity <= 0.1f) {
					newCooldown = 15;
					newPellets = 10;
				} else if (rarity <= 0.5f) {
					newCooldown = 22;
					newPellets = 8;
				} else {
					newCooldown = 30;
					newPellets = 6;
				}
				newWeapon = new WeaponBuilder().setWeaponType(WeaponType.SHOTGUN).setUser(PLAYER)
						.setCooldown(newCooldown).setPellets(newPellets).build();
				break;
			case MULTIGUN:
				if (rarity <= 0.1f) {
					newPellets = 5;
					newArc = (float) Math.PI / 3.8f;
				} else if (rarity <= 0.5f) {
					newPellets = 4;
					newArc = (float) Math.PI / 3.9f;
				} else {
					newPellets = 3;
					newArc = (float) Math.PI / 4.0f;
				}
				newWeapon = new WeaponBuilder().setWeaponType(WeaponType.MULTIGUN).setUser(PLAYER).setArc(newArc)
						.setPellets(newPellets).build();
				break;
			case STARGUN:
				if (rarity <= 0.1f) {
					newPellets = 40;
				} else if (rarity <= 0.5f) {
					newPellets = 35;
				} else {
					newPellets = 30;
				}
				newWeapon = new WeaponBuilder().setWeaponType(WeaponType.STARGUN).setUser(PLAYER)
						.setPellets(newPellets).build();
				break;
		}
		if (newWeapon != null) {
			((WeaponItem) item).setWeapon(newWeapon);
		}

		// Adjust the WeaponItem prefix
		if (rarity <= 0.1f) {
			prefix = "Godly";
		} else if (rarity <= 0.5f) {
			prefix = "Good";
		} else {
			prefix = "Bad";
		}

		String name = String.format("%s %s", prefix, ((WeaponItem) item).getItemName());
		((WeaponItem) item).setItemName(name);
	}
	
	/**
	 * Returns the name of the loot
	 * 
	 * @return the loot's name
	 */
	public String getName() {
		return name;
	}
		
	/**
	 * Returns a loot item
	 * 
	 * @return an item corresponding to the wrapper's loot type
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * Returns the loot rarity.
	 * @return a float denoting the rarity.
	 */
	public float getRarity() {
		return rarity;
	}
	
	public void modAmount(float mod) {
		amount *= mod;
		if(amount <= 0) {
			amount = 1;
		}
		item.setStackSize(amount);
	}
	
	@Override
	public boolean equals(Object o) {
		float epsilon = 0.00001f;
		if(o instanceof LootWrapper) {
			LootWrapper wrapper = (LootWrapper)o;
			return wrapper.getName().equals(this.getName()) &&
					Math.abs(wrapper.getRarity() - this.getRarity()) <= epsilon;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
