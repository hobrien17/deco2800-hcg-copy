package com.deco2800.hcg.entities.turrets;

import java.util.List;
import java.util.Observable;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.enemy_entities.Enemy;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.util.WorldUtil;

/**
 * Explosive Turret
 * After a few seconds, destroys itself and all enemies in a limited range
 * 
 * @author Henry O'Brien
 *
 */
public class ExplosiveTurret extends AbstractTurret {

	private int seconds;
	private static final int BLOW = 5;
	private static final int RANGE = 3;
	
	ParticleEffect exp;
	
	public ExplosiveTurret(Corpse master) {
		super(master, "Explosive");
		seconds = 0;
		exp = new ParticleEffect();
	}

	@Override
	public void update(Observable o, Object arg) {
		if(++seconds == BLOW) {
			
			List<AbstractEntity> entities = WorldUtil.allEntitiesToPosition(master.getPosX(), 
					master.getPosY(), RANGE, Enemy.class);
			for(AbstractEntity entity : entities) {
				GameManager.get().getWorld().removeEntity(entity);
			}
			//GameManager.get().getWorld().removeEntity(master);
		}	
	}
	
	@Override
	public String getThisTexture() {
		return "tree";
	}

}
