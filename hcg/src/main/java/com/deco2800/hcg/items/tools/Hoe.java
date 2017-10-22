package com.deco2800.hcg.items.tools;

import java.util.ArrayList;

import com.deco2800.hcg.entities.garden_entities.plants.AbstractGardenPlant;
import com.deco2800.hcg.entities.garden_entities.plants.Pot;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.ItemRarity;

public class Hoe extends Tool {

	public Hoe() {
		this.itemName = "Hoe";
        this.texture = "hoe";
        this.maxStackSize = 10;
        this.currentStackSize = 1;
        this.baseValue = 10;
	}

	@Override
	public Item copy() {
		return new Hoe();
	}
	
	@Override
	public ArrayList<String> getInformation() {
		ArrayList<String> list = new ArrayList<>();
		list.add("Harvest a plant, giving you more loot than usual");
		list.add("Only works on fully-grown plants");
		list.add("Has 3 uses");
		return list;
	}
	
	@Override
	public void effect(Pot pot) {
		if(pot.getPlant().getStage() == AbstractGardenPlant.Stage.LARGE) {
			pot.getPlant().modNumLoot(2);
			pot.getPlant().loot();
			player.getInventory().removeItem(this);
		}
	}
	
	@Override
	public ItemRarity getRarity() {
		return ItemRarity.RARE;
	}

}
