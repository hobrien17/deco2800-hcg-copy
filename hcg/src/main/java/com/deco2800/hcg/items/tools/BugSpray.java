package com.deco2800.hcg.items.tools;

import java.util.ArrayList;

import com.deco2800.hcg.entities.garden_entities.plants.Pot;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.ItemRarity;

public class BugSpray extends Tool {

	public BugSpray() {
		this.itemName = "Bug Spray";
		this.texture = "bug_spray";
		this.maxStackSize = 10;
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
		return new BugSpray();
	}

	@Override
	public ArrayList<String> getInformation() {
		ArrayList<String> list = new ArrayList<>();
		list.add("Kills those pesky bugs that are destroying your loot");
		list.add("Increases the chance that a plant drops rare loot");
		return list;
	}

	@Override
	public void effect(Pot pot) {
		pot.getPlant().increaseRarity(0.1, 0.05);
		player.getInventory().removeItem(this);
	}
	
	@Override
	public ItemRarity getRarity() {
		return ItemRarity.UNCOMMON;
	}

}
