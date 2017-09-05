package com.deco2800.hcg.entities.turrets;

import java.util.List;
import java.util.Observable;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Enemy;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.util.WorldUtil;

public class IceTurret extends AbstractTurret {

	private int seconds;
	private List<AbstractEntity> near;
	private List<AbstractEntity> far;
	private static final int BLOW = 5;
	private static final int RESET = BLOW + 10;
	private static final int CLOSE_RANGE = 5;
	private static final int FAR_RANGE = 30;
	
	public IceTurret(Corpse master) {
		super(master, "Ice");
		seconds = 0;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(++seconds == BLOW) {
			near = WorldUtil.allEntitiesToPosition(this.getCorpse().getPosX(), 
					this.getCorpse().getPosY(), CLOSE_RANGE, Enemy.class);
			far = WorldUtil.allEntitiesToPosition(this.getCorpse().getPosX(), 
					this.getCorpse().getPosY(), FAR_RANGE, Enemy.class);
			for(AbstractEntity entity : near) {
				Enemy enemy = (Enemy)entity;
				enemy.setSpeed(0);
			}
			for(AbstractEntity entity : far) {
				if(!near.contains(entity)) {
					Enemy enemy = (Enemy)entity;
					enemy.changeSpeed(0.5f);
				}
			}
		}
		if(seconds == RESET) {
			for(AbstractEntity entity : near) {
				Enemy enemy = (Enemy)entity;
				enemy.resetSpeed();
			}
			for(AbstractEntity entity : far) {
				Enemy enemy = (Enemy)entity;
				enemy.resetSpeed();
			}
		}
	}

	@Override
	public String getThisTexture() {
		return "tree";
	}

}
