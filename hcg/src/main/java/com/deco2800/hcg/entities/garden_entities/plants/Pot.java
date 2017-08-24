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
 *
 */
public class Pot extends AbstractEntity implements Clickable, Selectable  {
	
	AbstractGardenPlant plant;

	public Pot(float posX, float posY, float posZ) {
		super(posX, posY, posZ, 1.2f, 1.2f, 1, 1.5f, 1.5f, false);
		plant = null;
		setThisTexture();
	}

	@Override
	public boolean isSelected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deselect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Button getButton() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void buttonWasPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick() {
		// TODO Auto-generated method stub
		
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
	
}
