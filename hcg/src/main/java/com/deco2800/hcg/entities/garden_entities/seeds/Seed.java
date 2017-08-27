package com.deco2800.hcg.entities.garden_entities.seeds;

import java.lang.reflect.InvocationTargetException;

import com.deco2800.hcg.entities.garden_entities.plants.AbstractGardenPlant;
import com.deco2800.hcg.entities.garden_entities.plants.Cactus;
import com.deco2800.hcg.entities.garden_entities.plants.Grass;
import com.deco2800.hcg.entities.garden_entities.plants.Ice;
import com.deco2800.hcg.entities.garden_entities.plants.Inferno;
import com.deco2800.hcg.entities.garden_entities.plants.Pot;
import com.deco2800.hcg.entities.garden_entities.plants.Sunflower;
import com.deco2800.hcg.entities.garden_entities.plants.Water;
import com.deco2800.hcg.items.Item;

public class Seed implements Item {
	
	/**
	 * Stores the many different types of seeds in the game	 *
	 */
	public enum Type {
		SUNFLOWER ("gardening_seed", Sunflower.class),
		EXPLOSIVE ("explosive_seed", Cactus.class),
		FIRE ("fire_seed", Inferno.class),
		GRASS ("grass_seed", Grass.class),
		ICE ("ice_seed", Ice.class),
		WATER ("water_seed", Water.class);
		
		private String texture;
		
		/**
		 * Constructor for creating a new seed type
		 */
		private Class<? extends AbstractGardenPlant> plant;
		Type(String textureName, Class<? extends AbstractGardenPlant> plantClass) {
			texture = textureName;
			plant = plantClass;
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
	}
	
	private Type type;
	
	/**
	 * Constructor for a new seed with Type type
	 * 
	 * @param type the type of seed
	 */
	public Seed(Type type) {
		this.type = type;
	}

	@Override
	public boolean isStackable() {
		return false;
	}

	@Override
	public String getName() {
		return type.toString();
	}

	@Override
	public int getStackSize() {
		return 1;
	}

	@Override
	public int getMaxStackSize() {
		return 0;
	}

	@Override
	public boolean isWearable() {
		return false;
	}

	@Override
	public boolean isEquippable() {
		return false;
	}

	@Override
	public int getWeight() {
		return 0;
	}

	@Override
	public int getBaseValue() {
		return 0;
	}

	@Override
	public boolean isTradable() {
		return true;
	}

	@Override
	public void setTexture(String texture) throws IllegalArgumentException {
		//to implement
	}

	@Override
	public boolean addToStack(int number) {
		return false;
	}

	@Override
	public void setStackSize(int number) throws IllegalArgumentException {

	}

	@Override
	public boolean sameItem(Item item) throws IllegalArgumentException {
		return false;
	}

	@Override
	public boolean equals(Item item) throws IllegalArgumentException {
		if(item instanceof Seed) {
			Seed other = (Seed)item;
			return type.toString().equals(other.getType().toString());
		}
		return false;
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
	 * @param pot The pot this plant is to be planted in
	 * @return A new plant
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public AbstractGardenPlant getNewPlant(Pot pot) throws InstantiationException, IllegalAccessException, 
						IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return type.getPlant().getDeclaredConstructor(Pot.class).newInstance(pot);
	}

}
