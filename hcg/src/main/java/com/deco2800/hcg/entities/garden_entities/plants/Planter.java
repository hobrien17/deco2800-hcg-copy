package com.deco2800.hcg.entities.garden_entities.plants;

import java.util.Optional;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.observers.KeyUpObserver;
import com.deco2800.hcg.util.WorldUtil;

public class Planter implements KeyUpObserver {

	@Override
	public void notifyKeyUp(int keycode) {
		if(keycode >= 8 && keycode <= 12) {
			PlayerManager pm = (PlayerManager) GameManager.get().getManager(PlayerManager.class);
			Player player = pm.getPlayer();
			float px = player.getPosX();
			float py = player.getPosY();

			Optional<AbstractEntity> closest = WorldUtil.closestEntityToPosition(px, py, 1.5f, Pot.class);
			if (closest.isPresent() &&  closest.get() instanceof Pot) {
				Pot pot = (Pot)closest.get();
				if(keycode == 8) {
					pot.addPlant(new Sunflower(pot));
				} else if(keycode == 9) {
					pot.addPlant(new Cactus(pot));
				} else if(keycode == 10) {
					pot.addPlant(new Water(pot));
				} else if(keycode == 11) {
					pot.addPlant(new Grass(pot));
				} else if(keycode == 12) {
					pot.addPlant(new Inferno(pot));
				}
				
			} 
		}
	}

}