package com.deco2800.hcg.items.tools;

import java.util.Optional;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.garden_entities.plants.Pot;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.util.WorldUtil;

public class Fertiliser extends Tool {

	public Fertiliser() {
		super(1);
		this.itemName = "Fertiliser";
        this.texture = "fertiliser";
        this.maxStackSize = 10;
        this.currentStackSize = 1;
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
	public void use() {
		Optional<AbstractEntity> closest = WorldUtil.closestEntityToPosition(player.getPosX(), player.getPosY(), 
				1.5f, Pot.class);
		if(closest.isPresent()) {
			Pot pot = (Pot)closest.get();
			if(!pot.isEmpty()) {
				pot.getPlant().changeDelay(0.8f);
			}
		}
	}
	
}
