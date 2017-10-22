package com.deco2800.hcg.entities.garden_entities.seeds;

import java.lang.reflect.InvocationTargetException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.entities.garden_entities.plants.*;
import com.deco2800.hcg.entities.turrets.*;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.StackableItem;

public class Seed extends StackableItem {

	private static final Logger LOGGER = LoggerFactory.getLogger(Seed.class);

	/**
	 * Stores the many different types of seeds in the game *
	 */
	public enum Type {
		SUNFLOWER("sunflower_seed", 1, Sunflower.class, SunflowerTurret.class), 
		EXPLOSIVE("explosive_seed", 3, Cactus.class, ExplosiveTurret.class), 
		FIRE("fire_seed", 3, Inferno.class, FireTurret.class), 
		GRASS("grass_seed", 2,	Grass.class, GrassTurret.class), 
		ICE("ice_seed", 2, Ice.class, IceTurret.class), 
		WATER("water_seed", 2, Water.class, WaterTurret.class);

		private String texture;
		private int value;
		private Class<? extends AbstractGardenPlant> plant;
		private Class<? extends AbstractTurret> turret;

		/**
		 * Constructor for creating a new seed type
		 */
		Type(String textureName, int value, Class<? extends AbstractGardenPlant> plantClass,
				Class<? extends AbstractTurret> turretClass) {
			texture = textureName;
			plant = plantClass;
			turret = turretClass;
			this.value = value;
		}

		/**
		 * Returns the seed's texture
		 * 
		 * @return the texture name
		 */
		private String getTexture() {
			return texture;
		}

		/**
		 * Returns the type of plant that this seed grows into
		 * 
		 * @return A plant class
		 */
		private Class<? extends AbstractGardenPlant> getPlant() {
			return plant;
		}

		/**
		 * Gets the turret that this seed grows into
		 * 
		 * @return A turret class
		 */
		private Class<? extends AbstractTurret> getTurret() {
			return turret;
		}
		
		/**
		 * Returns the shop value of this item
		 * 
		 * @return the shop value
		 */
		private int getValue() {
			return value;
		}
	}

	private Type type;

	/**
	 * Constructor for a new seed with Type type
	 * 
	 * @param type
	 *            the type of seed
	 */
	public Seed(Type type) {
		this.type = type;
		this.texture = type.getTexture();
		this.itemWeight = 0;
		this.itemName = type.toString().toLowerCase() + " Seed";
		this.itemName = this.itemName.substring(0, 1).toUpperCase() + this.itemName.substring(1);
		this.maxStackSize = 255;
		this.currentStackSize = 1;
		this.baseValue = type.getValue();
	}

	/**
	 * Returns the type of seed
	 * 
	 * @return the seed's type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Returns a new plant, depending on the seed's type
	 * 
	 * @param pot
	 *            The pot this plant is to be planted in
	 */
	public AbstractGardenPlant getNewPlant(Pot pot) {
		try {
			return type.getPlant().getDeclaredConstructor(Pot.class).newInstance(pot);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException ex) {
			LOGGER.error("Error creating new object: ", ex);
			return null;
		}
	}

	/**
	 * Returns a new plant, depending on the seed's type
	 * 
	 * @param corpse
	 *            The corpse this plant is to be planted in
	 */
	public AbstractTurret getNewTurret(Corpse corpse) {
		try {
			return type.getTurret().getDeclaredConstructor(Corpse.class).newInstance(corpse);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException ex) {
			LOGGER.error("Error creating new object: ", ex);
			return null;
		}
	}

	@Override
	public boolean isEquippable() {
		return false;
	}

	@Override
	public boolean isTradable() {
		return true;
	}

	@Override
	public Item copy() {
		Seed newSeed = new Seed(type);
		newSeed.setStackSize(this.getStackSize());
		return newSeed;
	}
	
	@Override
	public String getName() {
		return this.itemName;
	}
	
	@Override
	public boolean equals(Object other) {
		if(other instanceof Seed) {
			return ((Seed) other).getType().toString().equals(this.getType().toString());
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.itemName.hashCode();
	}

}
