package com.deco2800.hcg.items.tools;

import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.items.SingleItem;
import com.deco2800.hcg.items.StackableItem;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;

public abstract class Tool extends StackableItem {
	
	protected int uses;
	protected Player player;
	
	public Tool(int uses) {
		this.uses = uses;
		
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
	
	public boolean hasInfiniteUses() {
		return uses < 0;
	}
	
	public abstract void use();
	
}
