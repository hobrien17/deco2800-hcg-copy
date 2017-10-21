package com.deco2800.hcg.items.stackable;

import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.StackableItem;

public class Key extends StackableItem {
	
	public Key() {
		this.baseValue = 1;
        this.itemWeight = 1;
        this.itemName = "Key";
        this.texture = "key_icon";
        this.currentStackSize = 1;
        this.maxStackSize = 100;
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
		return new Key();
	}

}
