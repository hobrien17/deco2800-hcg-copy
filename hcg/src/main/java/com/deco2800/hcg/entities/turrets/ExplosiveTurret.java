package com.deco2800.hcg.entities.turrets;

import java.util.List;
import java.util.Observable;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Enemy;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.StopwatchManager;
import com.deco2800.hcg.util.WorldUtil;

public class ExplosiveTurret extends AbstractTurret {

	private int seconds;
	private static final int BLOW = 5;
	private static final int RANGE = 3;
	
	public ExplosiveTurret() {
		super("Explosive");
		this.setTexture("tree");
		seconds = 0;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(++seconds == BLOW) {
			List<AbstractEntity> entities = WorldUtil.allEntitiesToPosition(this.getPosX(), this.getPosY(), RANGE, 
					Enemy.class);
			for(AbstractEntity entity : entities) {
				GameManager.get().getWorld().removeEntity(entity);
			}
			GameManager.get().getWorld().removeEntity(this);
		}	
	}
	
	

}
