package com.deco2800.hcg.entities.turrets;

import java.util.List;
import java.util.Observable;

import com.badlogic.gdx.graphics.Color;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.entities.enemyentities.Enemy;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.SoundManager;
import com.deco2800.hcg.types.Weathers;
import com.deco2800.hcg.util.WorldUtil;

/**
 * Ice turret
 * Freezes enemies in a short range
 * Slows down enemies in a longer range
 * 
 * @author Henry O'Brien
 *
 */
public class IceTurret extends AbstractTurret {

	private int seconds;
	private List<AbstractEntity> near;
	private List<AbstractEntity> far;
	private int closeRange;
	private int farRange;
	private static final int BLOW = 10;
	private static final int RESET = BLOW + 25;
	private static final int NORMAL_CLOSE_RANGE = 10;
	private static final int INCREASED_CLOSE_RANGE = 20;
	private static final int REDUCED_CLOSE_RANGE = 0;
	private static final int NORMAL_FAR_RANGE = 30;
	
	/**
	 * Creates a new ice turret inside the given corpse
	 * 
	 * @param master
	 * 			the corpse to plant the turret inside
	 */
	public IceTurret(Corpse master) {
		super(master, "Ice");
		seconds = 0;
		if(GameManager.get().getWorld().getWeatherType().equals(Weathers.SNOW)) {
			closeRange = INCREASED_CLOSE_RANGE;
		} else if(GameManager.get().getWorld().getWeatherType().equals(Weathers.SANDSTORM)) {
			closeRange = REDUCED_CLOSE_RANGE;
		} else {
			closeRange = NORMAL_CLOSE_RANGE;
		}
		farRange = NORMAL_FAR_RANGE;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(++seconds == BLOW) {
			((SoundManager)GameManager.get().getManager(SoundManager.class)).playSound("freeze");
			near = WorldUtil.allEntitiesToPosition(master.getPosX(), 
					master.getPosY(), closeRange, Enemy.class);
			far = WorldUtil.allEntitiesToPosition(master.getPosX(), 
					master.getPosY(), farRange, Enemy.class);
			far.removeAll(near);
			for(AbstractEntity entity : near) {
				Enemy enemy = (Enemy)entity;
				enemy.setSpeed(0);
				enemy.setTint(Color.valueOf("#B3FBFC"));
			}
			for(AbstractEntity entity : far) {
				Enemy enemy = (Enemy)entity;
				enemy.changeSpeed(0.5f);
				enemy.setTint(Color.valueOf("#B3FBFC"));
			}
		}
		if(seconds == RESET) {
			for(AbstractEntity entity : near) {
				Enemy enemy = (Enemy)entity;
				enemy.resetSpeed();
				enemy.removeTint();
			}
			for(AbstractEntity entity : far) {
				Enemy enemy = (Enemy)entity;
				enemy.resetSpeed();
				enemy.removeTint();
			}
			GameManager.get().getWorld().removeEntity(master);
		}
		if(seconds == BLOW - 1) {
			master.setTexture("ice_corpse_03");
		} else if(seconds == BLOW - 2) {
			master.setTexture("ice_corpse_02");
		}
	}

	@Override
	public String getThisTexture() {
		return "ice_corpse_01";
	}

	@Override
	public int getGlowStrength() {
		return 2;
	}

	@Override
	public Color getGlowColor() {
		return Color.valueOf("#B3FBFC");
	}

}
