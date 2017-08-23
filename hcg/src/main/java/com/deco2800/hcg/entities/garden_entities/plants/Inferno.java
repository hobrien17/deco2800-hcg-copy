package com.deco2800.hcg.entities.garden_entities.plants;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.deco2800.hcg.entities.garden_entities.seeds.FireSeed;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.ItemManager;

/**
 * Represents the Inferno plant which drops random loot
 * 
 * @author Reilly Lundin
 *
 */
public class Inferno extends AbstractGardenPlant {

	public Inferno(Pot master) {
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
	public void setupLoot() {
		lootRarity = new HashMap<>();
		
		lootRarity.put("fire_seed", 1.0);
		
		double sum = 0.0;
		for(Double rarity : lootRarity.values()) {
			if(rarity < 0.0 || rarity > 1.0) {
				LOGGER.error("Rarity should be between 0 and 1");
			}
			sum += rarity;
		}
		if(sum != 1.0) {
			LOGGER.warn("Total rarity should be 1");
		}
	}

	@Override
	public Item[] loot() {
		Item[] arr = new Item[1];
		arr[0] = ItemManager.getNew(randItem());
		
		return arr;
	}

}
