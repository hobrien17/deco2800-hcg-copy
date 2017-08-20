package com.deco2800.hcg.managers;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deco2800.hcg.entities.garden_entities.seeds.SunflowerSeed;
import com.deco2800.hcg.items.Item;

public class ItemManager extends Manager {
	private static final Logger LOGGER = LoggerFactory.getLogger(GameManager.class);
	
	public static Item getNew(String name) {
		if(name == "sunflower_seed") {
			return new SunflowerSeed();
		}
		
		LOGGER.warn("Unable to find given class, returning null");
		return null;
	}
}
