package com.deco2800.hcg.entities.terrain_entities;

import java.util.Collection;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Harmable;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.util.Effect;

public class DestructableTree extends AbstractEntity implements Harmable {

	public DestructableTree(float posX, float posY, float posZ) {
		super(posX, posY, posZ, 1f, 1f, 1f, 1f, 1f, false);
		this.setTexture("plant_02");
	}

	@Override
	public void giveEffect(Effect effect) {
		if(effect.getName().equals("Shot")) {
			GameManager.get().getWorld().removeEntity(this);
		}
	}

	@Override
	public void giveEffect(Collection<Effect> effects) {
		for(Effect effect : effects) {
			giveEffect(effect);
		}
		
	}

	
}
