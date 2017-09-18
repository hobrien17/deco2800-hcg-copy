package com.deco2800.hcg.entities.turrets;

import java.util.List;
import java.util.Observable;

import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.enemy_entities.Enemy;
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
	private final static int STOP = 20;
	private final static int RANGE = 10;
	
	public GrassTurret(Corpse master) {
		super(master, "Ice");
		seconds = 0;
	}

	@Override
	public void update(Observable o, Object arg) {
		seconds++;
		List<AbstractEntity> entities = WorldUtil.allEntitiesToPosition(master.getPosX(), master.getPosY(), RANGE, Enemy.class);
		for(AbstractEntity entity : entities) {
			Enemy enemy = (Enemy)entity;
			enemy.getMoveToPos(master.getPosX(), master.getPosY());
		}
	}

	@Override
	public String getThisTexture() {
		return "tree";
	}

}
