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

	public Water(Pot master) {
		super(master);
		this.advanceStage();
		this.advanceStage();
	}

	@Override
	public String getThisTexture() {
		switch (this.getStage()) {
		case SPROUT:
			return null;
		case SMALL:
			return null;
		case LARGE:
			return null;
		}
		return null;
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

	@Override
	void setupLoot() {
		// TODO Auto-generated method stub
		
	}

}
