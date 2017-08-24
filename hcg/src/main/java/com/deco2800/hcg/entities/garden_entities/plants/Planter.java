package com.deco2800.hcg.entities.garden_entities.plants;

import java.util.Optional;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Clickable;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.observers.KeyUpObserver;
import com.deco2800.hcg.util.WorldUtil;
import com.deco2800.hcg.worlds.AbstractWorld;
import com.deco2800.hcg.worlds.DemoWorld;

public class Planter implements KeyUpObserver {

	@Override
	public void notifyKeyUp(int keycode) {
		if(keycode >= 8 && keycode <= 10) {
			System.out.println("You pressed the 1 key!");
			PlayerManager pm = (PlayerManager) GameManager.get().getManager(PlayerManager.class);
			Player player = pm.getPlayer();
			float px = player.getPosX();
			float py = player.getPosY();
			System.out.println("x " + px + " y " + py);

			Optional<AbstractEntity> closest = WorldUtil.closestEntityToPosition(px, py, 1.5f, Pot.class);
			if (closest.isPresent() &&  closest.get() instanceof Pot) {
				Pot pot = (Pot)closest.get();
				if(keycode == 8) {
					pot.addPlant(new Sunflower(pot));
				} else if(keycode == 9) {
					pot.addPlant(new Cactus(pot));
				} else if(keycode == 10) {
					pot.addPlant(new Water(pot));
				}
				
			} 
		}
	}

}
