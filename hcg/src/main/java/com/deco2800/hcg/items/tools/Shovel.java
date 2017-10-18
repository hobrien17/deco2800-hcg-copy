package com.deco2800.hcg.items.tools;

import java.util.Optional;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.garden_entities.plants.Pot;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.util.WorldUtil;

public class Shovel extends Tool {

	
	public Shovel() {
		super(-1);
        this.itemName = "Shovel";
        this.texture = "shovel";
        this.maxStackSize = 1;
        this.currentStackSize = 1;
	}

	@Override
	public Item copy() {
		// TODO Auto-generated method stub
		return new Shovel();
	}

	@Override
	public void use() {
		Optional<AbstractEntity> closest = WorldUtil.closestEntityToPosition(player.getPosX(), player.getPosY(), 
				1.5f, Pot.class);
		if(closest.isPresent()) {
			Pot pot = (Pot)closest.get();
			if(!pot.isEmpty()) {
				pot.removePlant();
			}
		}
	}
	
}
