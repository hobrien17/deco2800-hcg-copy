package com.deco2800.hcg.entities.turrets;

import java.util.Observer;

import com.badlogic.gdx.graphics.Color;
import com.deco2800.hcg.entities.corpse_entities.Corpse;
import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.StopwatchManager;

/**
 * An abstract class representing a turret planted in a corpse
 * 
 * @author Henry O'Brien
 *
 */
public abstract class AbstractTurret implements Observer {
	
	private String name;
	protected Corpse master;
	
	/**
	 * Constructor for AbstractTurret, creates a new turret inside the given corpse with name name
	 * 
	 * @param master
	 * 			the corpse to be planted in
	 * @param name
	 * 			the name of the plant
	 */
	public AbstractTurret(Corpse master, String name) {
		this.name = name;
		this.master = master;
		StopwatchManager manager = (StopwatchManager)GameManager.get().getManager(StopwatchManager.class);
        manager.addObserver(this);
	}
	
	/**
	 * Gets the name of the plant
	 * 
	 * @return the plant's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the initial texture of this object, based on what type of turret it is
	 * 
	 * @return the texture name of this object
	 */
	public abstract String getThisTexture();
	
	/**
	 * Gets the glow strength of this turret
	 * 
	 * @return the turret's glow
	 */
	public abstract int getGlowStrength();
	
	/**
	 * Gets the glow colour of this turret
	 * 
	 * @return the turret's colour
	 */
	public abstract Color getGlowColor();
	
}
