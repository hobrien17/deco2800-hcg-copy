package com.deco2800.hcg.entities.garden_entities.plants;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.deco2800.hcg.entities.garden_entities.seeds.WaterSeed;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.ItemManager;

/**
 * Represents the Water plant which drops random loot
 * 
 * @author Reilly Lundin
 *
 */
public class Water extends AbstractGardenPlant {

	public Water(float posX, float posY, float posZ) {
		super(posX, posY, posZ, 0.5f, 0.5f, 1, 1, 1, false, Stage.SPROUT);
		this.advanceStage();
		this.advanceStage();
	}

	@Override
	public void setThisTexture() {
		switch (this.getStage()) {
		case SPROUT:
			//Put in proper sprite
			//this.setTexture("");
			break;
		case SMALL:
			//Put in proper sprite
			//this.setTexture("");
			break;
		case LARGE:
			//Put in proper sprite
			//this.setTexture("");
			break;
		}

	}

	@Override
	public void onTick(long gameTickCount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Item[] loot() {
		Item[] arr = new Item[1];
		arr[0] = ItemManager.getNew(randItem());
		
		return arr;
	}

}
