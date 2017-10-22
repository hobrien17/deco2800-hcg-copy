package com.deco2800.hcg.items.tools;

import java.util.ArrayList;

import com.deco2800.hcg.entities.garden_entities.plants.Pot;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.ItemRarity;

public class Trowel extends Tool {

	public Trowel() {
		this.itemName = "Trowel";
		this.texture = "trowel";
		this.maxStackSize = 10;
		this.currentStackSize = 1;
		this.baseValue = 10;
	}

	@Override
	public Item copy() {
		return new Trowel();
	}

	@Override
	public ArrayList<String> getInformation() {
		ArrayList<String> list = new ArrayList<>();
		list.add("Carefully dig up a plant");
		list.add("All stages of growth may drop loot this way");
		list.add("Has 3 uses");
		return list;
	}

	@Override
	public void effect(Pot pot) {
		pot.getPlant().harvest();
		player.getInventory().removeItem(this);
	}
	
	@Override
	public ItemRarity getRarity() {
		return ItemRarity.RARE;
	}

}
