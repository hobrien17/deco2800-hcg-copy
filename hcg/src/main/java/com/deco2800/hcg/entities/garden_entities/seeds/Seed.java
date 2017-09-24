package com.deco2800.hcg.entities.garden_entities.seeds;

import java.lang.reflect.InvocationTargetException;

import com.badlogic.gdx.graphics.Texture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.entities.garden_entities.plants.*;
import com.deco2800.hcg.entities.turrets.*;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.StackableItem;
import com.deco2800.hcg.managers.GameManager;

public class Seed extends StackableItem {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameManager.class);

	/**
	 * Stores the many different types of seeds in the game *
	 */
	public enum Type {
		SUNFLOWER("gardening_seed", Sunflower.class, SunflowerTurret.class), 
		EXPLOSIVE("explosive_seed", Cactus.class, ExplosiveTurret.class), 
		FIRE("fire_seed", Inferno.class, FireTurret.class), 
		GRASS("grass_seed",	Grass.class, GrassTurret.class), 
		ICE("ice_seed", Ice.class, IceTurret.class), 
		WATER("water_seed", Water.class, WaterTurret.class);

		private String texture;
		private Class<? extends AbstractGardenPlant> plant;
		private Class<? extends AbstractTurret> turret;

		/**
		 * Constructor for creating a new seed type
		 */
		Type(String textureName, Class<? extends AbstractGardenPlant> plantClass,
				Class<? extends AbstractTurret> turretClass) {
			texture = textureName;
			plant = plantClass;
			turret = turretClass;
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

		private Class<? extends AbstractTurret> getTurret() {
			return turret;
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
		this.baseValue = 1;
		this.itemWeight = 0;
		this.itemName = type.toString().toLowerCase();
		this.maxStackSize = 256;
		this.currentStackSize = 1;
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
			LOGGER.error("Error creating new object: " + ex.getStackTrace());
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
			LOGGER.error("Error creating new object: " + ex.getStackTrace());
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
		return type.toString().toLowerCase();
	}


}
