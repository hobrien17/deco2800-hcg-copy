package com.deco2800.hcg.items.lootable;

import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.ItemManager;

/**
 * A wrapper class that keeps track of one or more pieces of loot
 * 
 * @author Henry O'Brien
 *
 */
public class LootWrapper {
	private String name;
	private int amount;
	Item item;
	
	/**
	 * Stores a single piece of loot of type name
	 * 
	 * @param name
	 * 			the name of the loot
	 */
	public LootWrapper(String name) {
		this.name = name;
		item = ((ItemManager)GameManager.get().getManager(ItemManager.class)).getNew(name);
		this.amount = 1;
	}
	
	/**
	 * Stores between minAmount and maxAmount pieces of loot
	 * 
	 * @param name
	 * 			the name of the loot
	 * @param minAmount
	 * 			the min number of pieces to store
	 * @param maxAmount
	 * 			the max number of pieces to store
	 */
	public LootWrapper(String name, int minAmount, int maxAmount) {
		this.name = name;
		amount = (int)(Math.random() * ((maxAmount - minAmount) + 1)) + minAmount;
		item = ((ItemManager)GameManager.get().getManager(ItemManager.class)).getNew(name);
		if (amount > 1) {
			item.addToStack(amount);
		}
	}
	
	/**
	 * Returns the name of the loot
	 * 
	 * @return the loot's name
	 */
	public String getName() {
		return name;
	}
		
	/**
	 * Returns a loot item
	 * 
	 * @return an item corresponding to the wrapper's loot type
	 */
	public Item getItem() {
		return item;
	}
	
	public void modAmount(float mod) {
		amount *= mod;
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
