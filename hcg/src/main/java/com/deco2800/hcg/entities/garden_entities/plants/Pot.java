package com.deco2800.hcg.entities.garden_entities.plants;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.deco2800.hcg.entities.AbstractEntity;
import com.deco2800.hcg.entities.Clickable;
import com.deco2800.hcg.entities.Selectable;
import com.deco2800.hcg.entities.Tickable;
import com.deco2800.hcg.entities.garden_entities.plants.AbstractGardenPlant.Stage;
import com.deco2800.hcg.util.Box3D;

public class Pot extends AbstractEntity implements Clickable, Selectable  {
	
	AbstractGardenPlant plant;

	public Pot(float posX, float posY, float posZ) {
		super(posX, posY, posZ, 0.5f, 0.5f, 1, 1, 1, false);
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
	
	public void addPlant(AbstractGardenPlant plant) {
		this.plant = plant;
		setThisTexture();
	}
	
	public void setThisTexture() {
		System.out.println(plant);
		if(plant == null) {
			System.out.println("hello");
			this.setTexture("pot");
		} else {
			this.setTexture(plant.getThisTexture());
		}
	}
	
}
