package com.deco2800.hcg.entities.turrets;

import java.util.Observable;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.deco2800.hcg.entities.Fireball;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.managers.GameManager;

/**
 * Fire turret 
 * Fires four massive fireballs in different directions
 *
 * @author Henry
 *
 */
public class FireTurret extends AbstractTurret {

	private int seconds;
	private final static int RANGE = 10;

	public FireTurret(Corpse master) {
		super(master, "Fire");
		seconds = 0;
	     
	}

	@Override
	public void update(Observable o, Object arg) {
<<<<<<< HEAD
		float[][] pos = { { master.getPosX() + RANGE, master.getPosY() + RANGE}, 
				{ master.getPosX() + RANGE, Math.max(0, master.getPosY() - RANGE) },
				{ Math.max(0, master.getPosX() - RANGE), master.getPosY() + RANGE }, 
				{ Math.max(0, master.getPosX() - RANGE), Math.max(0, master.getPosY() - RANGE) } };
=======
		float[][] pos = { { master.getPosX() + RANGE, master.getPosY() },
				{ master.getPosX(), master.getPosY() + RANGE },
				{ Math.max(0, master.getPosX() - RANGE), master.getPosY() },
				{ master.getPosX(), Math.max(0, master.getPosY() - RANGE) } };
>>>>>>> origin/master
		if (seconds == 5) {
			GameManager.get().getWorld().removeEntity(master);
		} else if(seconds > 0 && seconds < 5) {
			Fireball fire = new Fireball(master.getPosX(), master.getPosY(),
					master.getPosZ(), pos[seconds-1][0], pos[seconds-1][1], master.getPosZ(),
					master, -1);
			GameManager.get().getWorld().addEntity(fire);
		}
		seconds++;
	}

	@Override
	public String getThisTexture() {
		return "tree";
	}

}

