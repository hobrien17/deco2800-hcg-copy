package com.deco2800.hcg.items.tools;

import java.util.ArrayList;

import com.deco2800.hcg.entities.garden_entities.plants.AbstractGardenPlant;
import com.deco2800.hcg.entities.garden_entities.plants.Pot;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.ItemRarity;

public class Shovel extends Tool {

	public Shovel() {
		this.itemName = "Shovel";
		this.texture = "shovel";
		this.maxStackSize = 1;
		this.currentStackSize = 1;
		this.baseValue = 0;
	}

	@Override
	public ArrayList<String> getInformation() {
		ArrayList<String> list = new ArrayList<>();
		list.add("Dig up a plant you've finished with");
		list.add("Drops loot if the plant is fully-grown");
		list.add("Can be used as many times as you like");
		return list;
	}

	@Override
	public Item copy() {
		return new Shovel();
	}

	@Override
	public void effect(Pot pot) {
		if (pot.getPlant().getStage().equals(AbstractGardenPlant.Stage.LARGE)) {
			pot.getPlant().loot();
		} else {
			pot.removePlant();
		}
	}
	
	@Override
	public ItemRarity getRarity() {
		return ItemRarity.COMMON;
	}

}
