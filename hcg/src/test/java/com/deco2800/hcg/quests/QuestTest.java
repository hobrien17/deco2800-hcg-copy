package com.deco2800.hcg.quests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.stackable.Key;
import com.deco2800.hcg.items.stackable.TestItem;
import com.deco2800.hcg.items.single.TestUniqueItem;

public class QuestTest {
	
	private HashMap<String,Integer> requiredItems;
	private HashMap<String,Integer> rewards;
	private HashMap<Integer,HashMap<Integer,Integer>> globalKillRequirement;
	private Quest quest;
	
	@Before
	public void initialiseConstructor(){
	    requiredItems = new HashMap<String,Integer>();
	    rewards = new HashMap<String,Integer>();
	    globalKillRequirement = new HashMap<Integer,HashMap<Integer,Integer>>();
	    
	    String title = "Quest Title";
        String key = "Key";
        
        String potion = "MagicMushroom";
        requiredItems.put(key,1);
        rewards.put(potion, 3);
        
        HashMap<Integer,Integer> enemyKillCount = new HashMap<Integer,Integer>();
        enemyKillCount.put(1, 5);
        enemyKillCount.put(2, 100);
        enemyKillCount.put(3, 50);
        globalKillRequirement.put(1, enemyKillCount);
        this.quest = new Quest(title, requiredItems, globalKillRequirement, rewards);
	}
	
	@Test
	public void testConstructor(){
        assertTrue(quest.getItemRequirement().equals(requiredItems));
        assertTrue(quest.getKillRequirement().equals(globalKillRequirement));
        assertTrue(quest.getRewards().equals(rewards));
	    
	}
}