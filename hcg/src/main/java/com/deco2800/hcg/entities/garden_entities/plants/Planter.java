package com.deco2800.hcg.entities.garden_entities.plants;

import java.util.Optional;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlantManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.observers.KeyUpObserver;
import com.deco2800.hcg.util.WorldUtil;

public class Planter implements KeyUpObserver {

    private PlantManager plantManager = (PlantManager) GameManager.get().getManager(PlantManager.class);
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
					pot.getPlant().setName("Sunflower");
					plantManager.addPlants(pot.getPlant());
					plantManager.updateLabel();
				} else if(keycode == 9) {
					pot.addPlant(new Cactus(pot));
                    pot.getPlant().setName("Cactus");
                    plantManager.addPlants(pot.getPlant());
                    plantManager.updateLabel();
				} else if(keycode == 10) {
					pot.addPlant(new Water(pot));
                    pot.getPlant().setName("Water");
                    plantManager.addPlants(pot.getPlant());
                    plantManager.updateLabel();
				} else if(keycode == 11) {
					pot.addPlant(new Grass(pot));
                    pot.getPlant().setName("Grass");
                    plantManager.addPlants(pot.getPlant());
                    plantManager.updateLabel();
				} else if(keycode == 12) {
					pot.addPlant(new Inferno(pot));
                    pot.getPlant().setName("Inferno");
                    plantManager.addPlants(pot.getPlant());
                    plantManager.updateLabel();
				}
				
			} 
		}
	}

}
