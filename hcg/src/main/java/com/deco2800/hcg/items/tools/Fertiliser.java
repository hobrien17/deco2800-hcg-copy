package com.deco2800.hcg.items.tools;

import java.util.ArrayList;

import com.deco2800.hcg.entities.garden_entities.plants.Pot;
import com.deco2800.hcg.items.Item;

public class Fertiliser extends Tool {

	public Fertiliser() {
		this.itemName = "Fertiliser";
		this.texture = "fertiliser";
		this.maxStackSize = 20;
		this.currentStackSize = 1;
		this.baseValue = 5;
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
		return new Fertiliser();
	}

	@Override
	public ArrayList<String> getInformation() {
		ArrayList<String> list = new ArrayList<>();
		list.add("Give a plant the boost it deserves");
		list.add("Speeds up the growth of a plant");
		return list;
	}

	@Override
	public void effect(Pot pot) {
		pot.getPlant().changeDelay(0.75f);
		player.getInventory().removeItem(this);
	}

}
