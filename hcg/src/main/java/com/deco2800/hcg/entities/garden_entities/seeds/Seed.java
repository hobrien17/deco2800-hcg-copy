package com.deco2800.hcg.entities.garden_entities.seeds;

import com.deco2800.hcg.entities.garden_entities.plants.AbstractGardenPlant;
import com.deco2800.hcg.entities.garden_entities.plants.Cactus;
import com.deco2800.hcg.entities.garden_entities.plants.Grass;
import com.deco2800.hcg.entities.garden_entities.plants.Ice;
import com.deco2800.hcg.entities.garden_entities.plants.Inferno;
import com.deco2800.hcg.entities.garden_entities.plants.Sunflower;
import com.deco2800.hcg.entities.garden_entities.plants.Water;
import com.deco2800.hcg.items.Item;

public class Seed implements Item {
	
	public enum Type {
		SUNFLOWER ("gardening_seed", Sunflower.class),
		EXPLOSIVE ("explosive_seed", Cactus.class),
		FIRE ("fire_seed", Inferno.class),
		GRASS ("grass_seed", Grass.class),
		ICE ("ice_seed", Ice.class),
		WATER ("water_seed", Water.class);
		
		private String texture;
		private Class<? extends AbstractGardenPlant> plant;
		Type(String textureName, Class<? extends AbstractGardenPlant> plantClass) {
			texture = textureName;
			plant = plantClass;
		}
		
		private String getTexture() {
			return texture;
		}
		
		private Class<? extends AbstractGardenPlant> getPlant() {
			return plant;
		}
	}
	
	private Type type;
	
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
	
	public Type getType() {
		return type;
	}
	
	public AbstractGardenPlant getNewPlant() throws InstantiationException, IllegalAccessException {
		return type.getPlant().newInstance();
	}

}
