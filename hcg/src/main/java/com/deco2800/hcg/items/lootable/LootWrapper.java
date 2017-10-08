package com.deco2800.hcg.items.lootable;

import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.ItemManager;

public class LootWrapper {
	private String name;
	private int amount;
	Item item;
	
	public LootWrapper(String name) {
		this.name = name;
		item = ((ItemManager)GameManager.get().getManager(ItemManager.class)).getNew(name);
		this.amount = 1;
	}
	
	public LootWrapper(String name, int minAmount, int maxAmount) {
		this.name = name;
		amount = (int)(Math.random() * ((maxAmount - minAmount) + 1)) + minAmount;
		item = ((ItemManager)GameManager.get().getManager(ItemManager.class)).getNew(name);
		if (amount > 1) {
			item.addToStack(amount);
		}
	}
	
	public String getName() {
		return name;
	}
		
	public Item getItem() {
		return item;
	}
	
	@Override
	public boolean equals(Object o) {
		System.out.println("eq");
		if(o instanceof LootWrapper) {
			LootWrapper wrapper = (LootWrapper)o;
			System.out.println(getName());
			return wrapper.getName().equals(this.getName());
		}
		return false;
	}
}
