package com.deco2800.hcg.items.tools;

import java.util.Optional;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.garden_entities.plants.Pot;
import com.deco2800.hcg.items.StackableItem;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.util.WorldUtil;

public abstract class Tool extends StackableItem {
	
	protected Player player;
	
	public Tool() {		
		PlayerManager manager = (PlayerManager)GameManager.get().getManager(PlayerManager.class);
        player = manager.getPlayer();
	}

	@Override
	public boolean isWearable() {
		return false;
	}

	@Override
	public boolean isEquippable() {
		return true;
	}

	@Override
	public boolean isTradable() {
		return true;
	}
	
	public void use() {
		Optional<AbstractEntity> closest = WorldUtil.closestEntityToPosition(player.getPosX(), player.getPosY(), 
				1.5f, Pot.class);
		if(closest.isPresent()) {
			Pot pot = (Pot)closest.get();
			if(!pot.isEmpty()) {
				effect(pot);
			}
		}
	}
	
	public abstract void effect(Pot pot);
	
}
