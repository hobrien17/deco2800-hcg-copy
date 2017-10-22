package com.deco2800.hcg.entities.turrets;

import java.util.List;
import java.util.Observable;

import com.badlogic.gdx.graphics.Color;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Player;
import com.deco2800.hcg.entities.bullets.Fireball;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.entities.enemyentities.Enemy;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.types.Weathers;
import com.deco2800.hcg.util.WorldUtil;

/**
 * Fire turret 
 * Fires four massive fireballs in different directions
 *
 * @author Henry O'Brien
 *
 */
public class FireTurret extends AbstractTurret {

	private int seconds;
	private boolean infinite;
	private static final int RANGE = 10;
	private static final int EXP_RANGE = 2;

	/**
	 * Creates a new fire turret in the given corpse
	 * 
	 * @param master
	 * 			the corpse to plant the turret inside
	 */
	public FireTurret(Corpse master) {
		super(master, "Inferno");
		seconds = 0;
		if(GameManager.get().getWorld().getWeatherType().equals(Weathers.RAIN)) {
			infinite = false;
		} else {
			infinite = true;
		}
	}

	/**
	 * Updates the turret, shooting the next fireball in the turret's sequence or destroying itself
	 * 	if the sequence is complete
	 * 
	 * @param o
	 * 			the Observable object calling the update method (should be an instance of StopwatchManager)
	 * @param arg
	 * 			the argument passed by the Observable object (should be the stopwatch's current time)
	 */
	@Override
	public void update(Observable o, Object arg) {
		
		float[][] pos = { { master.getPosX() + RANGE, master.getPosY() + RANGE },
				{ master.getPosX() + RANGE, Math.max(0, master.getPosY() - RANGE) },
				{ Math.max(0, master.getPosX() - RANGE), master.getPosY() + RANGE },
				{ Math.max(0, master.getPosX() - RANGE), Math.max(0, master.getPosY() - RANGE) } };
		if (seconds == 13) {
			if(GameManager.get().getWorld().getWeatherType().equals(Weathers.SANDSTORM)) {
				Explosion exp = new Explosion(master.getPosX()+1, master.getPosY(), 0, 0.25f);
				GameManager.get().getWorld().addEntity(exp);
				List<AbstractEntity> entities = WorldUtil.allEntitiesToPosition(master.getPosX(), 
						master.getPosY(), EXP_RANGE, Enemy.class);
				entities.addAll(WorldUtil.allEntitiesToPosition(master.getPosX(), master.getPosY(), EXP_RANGE, Player.class));
				for(AbstractEntity entity : entities) {
					if(entity instanceof Player) {
						((Player) entity).takeDamage(10);
						continue;
					}
					GameManager.get().getWorld().removeEntity(entity);
				}
			}
			o.deleteObserver(this);
			GameManager.get().getWorld().removeEntity(master);
		} else if (seconds > 0) {
			Fireball fire = new Fireball(master.getPosX(), master.getPosY(), master.getPosZ(), pos[(seconds - 1) % 4][0],
					pos[(seconds - 1) % 4][1], master.getPosZ(), master, infinite, 1000);
			GameManager.get().getWorld().addEntity(fire);
		}
		seconds++;
	}

	@Override
	public String getThisTexture() {
		return "fire_corpse";
	}

	@Override
	public int getGlowStrength() {
		return 3;
	}

	@Override
	public Color getGlowColor() {
		return Color.RED;
	}

}
