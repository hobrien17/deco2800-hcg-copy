package com.deco2800.hcg.entities.garden_entities.plants;

import java.util.Optional;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.entities.garden_entities.seeds.Seed;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.PlantManager;
import com.deco2800.hcg.managers.PlayerManager;
import com.deco2800.hcg.observers.KeyUpObserver;
import com.deco2800.hcg.util.WorldUtil;

/**
 * A key observer that handles keypresses and puts plants in pots depending on these presses
 * 
 * @author Henry O'Brien
 *
 */
public class Planter implements KeyUpObserver {
	
	private PlantManager plantManager;
	
	public Planter() {
		plantManager = (PlantManager) GameManager.get().getManager(PlantManager.class);
	}

    
	@Override
	public void notifyKeyUp(int keycode) {
		if((keycode >= 8 && keycode <= 13) || keycode == 49) {
			PlayerManager pm = (PlayerManager) GameManager.get().getManager(PlayerManager.class);
			Player player = pm.getPlayer();
			float px = player.getPosX();
			float py = player.getPosY();

			Optional<AbstractEntity> closest = WorldUtil.closestEntityToPosition(px, py, 1.5f, Pot.class);
			if (closest.isPresent() &&  closest.get() instanceof Pot) {
				Pot pot = (Pot)closest.get();
				if(keycode == 8) {
					pot.addPlant(new Sunflower(pot));
					updateManager(pot);
				} else if(keycode == 9) {
					pot.addPlant(new Cactus(pot));
					updateManager(pot);
				} else if(keycode == 10) {
					pot.addPlant(new Water(pot));
					updateManager(pot);
				} else if(keycode == 11) {
					pot.addPlant(new Grass(pot));
					updateManager(pot);
				} else if(keycode == 12) {
					pot.addPlant(new Inferno(pot));  
					updateManager(pot);
				} else if(keycode == 13) {
					pot.addPlant(new Ice(pot));
					updateManager(pot);
				} else if(keycode == 49) {
					pot.unlock();
				}
			} 
		} else if((keycode >= 14 && keycode <= 18) || keycode == 7 || keycode == 69 || keycode == 70) {
			PlayerManager pm = (PlayerManager) GameManager.get().getManager(PlayerManager.class);
			Player player = pm.getPlayer();
			float px = player.getPosX();
			float py = player.getPosY();
			
			Optional<AbstractEntity> closest = WorldUtil.closestEntityToPosition(px, py, 1.5f, Corpse.class);
			if (closest.isPresent() &&  closest.get() instanceof Corpse) {
				Corpse corpse = (Corpse)closest.get();
				if(keycode == 14) {
					corpse.plantInside(new Seed(Seed.Type.ICE));
				} else if(keycode == 15) {
					corpse.plantInside(new Seed(Seed.Type.FIRE));
				} else if(keycode == 16) {
					corpse.plantInside(new Seed(Seed.Type.EXPLOSIVE));
				} else if(keycode == 7) {
					corpse.plantInside(new Seed(Seed.Type.SUNFLOWER));
				} else if(keycode == 69) {
					corpse.plantInside(new Seed(Seed.Type.WATER));
				} else if(keycode == 70) {
					corpse.plantInside(new Seed(Seed.Type.GRASS));
				}
			}
		}
	}
	
	private void updateManager(Pot pot) {
		if(!pot.isEmpty()) {
			plantManager.addPlants(pot.getPlant());
			plantManager.updateLabel();
		}
	}

}
