package com.deco2800.hcg.entities.turrets;

import java.util.List;
import java.util.Observable;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.util.WorldUtil;

public class WaterTurret extends AbstractTurret {
	
	private int seconds;
	private final static int DIE = 30;
	private final static int RANGE = 3;

	public WaterTurret(Corpse master) {
		super(master, "Water");
	}

	@Override
	public void update(Observable o, Object arg) {
		if(++seconds == DIE) {
			GameManager.get().getWorld().removeEntity(master);
			o.deleteObserver(this);
			return;
		}
		List<AbstractEntity> players = WorldUtil.allEntitiesToPosition(master.getPosX(), master.getPosY(), 
				RANGE, Player.class);
		for (AbstractEntity entity : players) {
			Player player = (Player)entity;
			player.takeDamage(-1);
		}
		

	}

	@Override
	public String getThisTexture() {
		// TODO Auto-generated method stub
		return "tree";
	}

}
