package com.deco2800.hcg.entities.turrets;

import java.util.List;
import java.util.Observable;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.enemy_entities.Enemy;
import com.deco2800.hcg.entities.corpse_entities.BasicCorpse;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.util.WorldUtil;

/**
 * Grass Turret
 * Draws enemies towards it?
 * 
 * @author Henry O'Brien
 *
 */
public class GrassTurret extends AbstractTurret {

	private int seconds;
	private final static int GROW = 5;
	
	public GrassTurret(Corpse master) {
		super(master, "Grass");
		seconds = 0;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(++seconds == GROW) {
			GameManager.get().getWorld().removeEntity(master);
		}
	}

	@Override
	public String getThisTexture() {
		return "grass_corpse";
	}

}
