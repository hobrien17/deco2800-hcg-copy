package com.deco2800.hcg.entities.garden_entities.plants;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.deco2800.hcg.entities.garden_entities.seeds.SunflowerSeed;
import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.managers.ItemManager;

/**
 * Represents a basic plant which drops basic loot
 * 
 * @author Henry O'Brien
 *
 */
public class Sunflower extends AbstractGardenPlant {

	public Sunflower(float posX, float posY, float posZ) {
		super(posX, posY, posZ, 0.5f, 0.5f, 1, 1, 1, false, Stage.SPROUT);
		this.advanceStage();
		this.advanceStage();
	}

	@Override
	public void setThisTexture() {
		switch (this.getStage()) {
		case SPROUT:
			this.setTexture("plant");
			break;
		case SMALL:
			this.setTexture("plant2");
			break;
		case LARGE:
			this.setTexture("tree");
			break;
		}

	}

	@Override
	public void onTick(long gameTickCount) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void setupLoot() {
		lootRarity = new HashMap<>();
		
		lootRarity.put("sunflower_seed", 1.0);
		
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
	public String[] getLoot() {
		return lootRarity.keySet().toArray(new String[lootRarity.size()]);
	}
	
	String randItem() {
		Double prob = Math.random();
		Double total = 0.0;
		for(Map.Entry<String, Double> entry : lootRarity.entrySet()) {
			total += entry.getValue();
			if(total > prob) {
				return entry.getKey();
			}
		}
		LOGGER.warn("No item has been selected, returning null");
		return null;
	}

	@Override
	public Item[] loot() {
		Item[] arr = new Item[1];
		arr[0] = ItemManager.getNew(randItem());
		
		return arr;
	}

}
