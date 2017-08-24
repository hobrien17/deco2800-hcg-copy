package com.deco2800.hcg.entities.garden_entities.plants;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Clickable;
import com.deco2800.hcg.entities.Selectable;
import com.deco2800.hcg.entities.Tickable;
import com.deco2800.hcg.entities.garden_entities.plants.AbstractGardenPlant.Stage;
import com.deco2800.hcg.util.Box3D;

/**
 * An entity that contains a plant
 *
 * @author Henry O'Brien
 */
public class Pot extends AbstractEntity implements Tickable  {
	
	AbstractGardenPlant plant;

	public Pot(float posX, float posY, float posZ) {
		super(posX, posY, posZ, 0.7f, 0.7f, 1, 1f, 1f, false);
		plant = null;
		setThisTexture();
	}

	
	
	/**
	 * Adds a plant to the pot, if empty
	 * 
	 * @param the plant to be added
	 * @return true if the plant was added, false if it could not be added
	 */
	public boolean addPlant(AbstractGardenPlant plant) {
		if(this.plant == null) {
			this.plant = plant;
			setThisTexture();
			return true;
		}
		return false;
		
	}
	
	/**
	 * Sets the texture of the pot based on the plant inside
	 */
	public void setThisTexture() {
		if(plant == null) {
			this.setTexture("pot");
		} else {
			this.setTexture(plant.getThisTexture());
		}
	}

	@Override
	public void onTick(long gameTickCount) {
		if(plant != null) {
			plant.checkGrow();
		}
		
	}
}
