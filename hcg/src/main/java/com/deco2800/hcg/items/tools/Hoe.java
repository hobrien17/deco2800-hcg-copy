package com.deco2800.hcg.items.tools;

import java.util.Optional;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.garden_entities.plants.Pot;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.util.WorldUtil;

public class Hoe extends Tool {

	public Hoe() {
		super(3);
		this.itemName = "Hoe";
        this.texture = nextTexture();
        this.maxStackSize = 1;
        this.currentStackSize = 1;
	}
	
	private String nextTexture() {
		switch(uses) {
		case 3:
			return "hoe";
		case 2:
			return "hoe_mid";
		case 1:
			return "hoe_broken";
		}
		
		return null;
	}

	@Override
	public Item copy() {
		// TODO Auto-generated method stub
		return new Hoe();
	}

	@Override
	public void use() {
		uses--;
		this.texture = nextTexture();
		Optional<AbstractEntity> closest = WorldUtil.closestEntityToPosition(player.getPosX(), player.getPosY(), 
				1.5f, Pot.class);
		if(closest.isPresent()) {
			Pot pot = (Pot)closest.get();
			if(!pot.isEmpty()) {
				pot.getPlant().harvest();
			}
		}
	}

}
