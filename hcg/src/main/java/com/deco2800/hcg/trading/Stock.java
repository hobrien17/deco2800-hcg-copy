package com.deco2800.hcg.trading;

import com.deco2800.hcg.items.Item;

public class Stock implements Item {
	String name;
	boolean wearable;
	int weight;
	int value;
	
	public Stock(String name, boolean wearable, int weight, int value){
		
	}
	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getMaxStackSize() {
		return 1;
	}

	@Override
	public boolean isWearable() {
		return wearable;
	}

	@Override
	public int getWeight() {
		return weight;
	}

	@Override
	public int getBaseValue() {
		return value;
	}

	@Override
	public boolean isTradable() {
		return true;
	}

	@Override
	public void setTexture(String texture) {
		

	}

	@Override
	public boolean addToStack(int number) {
		// TODO Auto-generated method stub
		return false;
	}

}
