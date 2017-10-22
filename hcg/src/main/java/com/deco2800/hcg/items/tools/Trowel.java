package com.deco2800.hcg.items.tools;

import java.util.Optional;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.garden_entities.plants.AbstractGardenPlant;
import com.deco2800.hcg.entities.garden_entities.plants.Pot;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.util.WorldUtil;

public class Trowel extends Tool {

	public Trowel() {
		super(3);
		this.itemName = "Trowel";
        this.texture = nextTexture();
        this.maxStackSize = 1;
        this.currentStackSize = 1;
        this.baseValue = 10;
	}
	
	private String nextTexture() {
		switch(uses) {
		case 3:
			return "trowel";
		case 2:
			return "trowel_mid";
		case 1:
			return "trowel_broken";
		}
		
		return null;
	}

	@Override
	public Item copy() {
		// TODO Auto-generated method stub
		return new Trowel();
	}

	@Override
	public void use() {
		uses--;
		this.texture = nextTexture();
		Optional<AbstractEntity> closest = WorldUtil.closestEntityToPosition(player.getPosX(), player.getPosY(), 
				1.5f, Pot.class);
		if(closest.isPresent()) {
			Pot pot = (Pot)closest.get();
			if(!pot.isEmpty() && pot.getPlant().getStage() == AbstractGardenPlant.Stage.LARGE) {
				pot.getPlant().modNumLoot(2);
				pot.getPlant().loot();
			}
		}
	}

}
