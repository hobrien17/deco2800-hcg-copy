package com.deco2800.hcg.quests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import com.deco2800.hcg.entities.enemyentities.EnemyType;

import org.junit.Before;
import org.junit.Test;

import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.stackable.Key;
import com.deco2800.hcg.items.stackable.TestItem;
import com.deco2800.hcg.items.single.TestUniqueItem;

public class QuestTest {
	
	private HashMap<String,Integer> requiredItems;
	private HashMap<String,Integer> rewards;
	private HashMap<Integer,HashMap<EnemyType,Integer>> globalKillRequirement;
	private Quest quest;
	
	@Before
	public void initialiseConstructor(){
	    requiredItems = new HashMap<>();
	    rewards = new HashMap<>();
	    globalKillRequirement = new HashMap<>();
	    
	    String title = "Quest Title";
        String key = "Key";
        
        String potion = "MagicMushroom";
        requiredItems.put(key,1);
        rewards.put(potion, 3);
        
        HashMap<EnemyType,Integer> enemyKillCount = new HashMap<>();
        enemyKillCount.put(EnemyType.HEDGEHOG, 5);
        enemyKillCount.put(EnemyType.CRAB, 100);
        enemyKillCount.put(EnemyType.MUSHROOMTURRET, 50);
        globalKillRequirement.put(1, enemyKillCount);
        this.quest = new Quest(title, rewards, globalKillRequirement,requiredItems ,"");
	}
	
	@Test
	public void testConstructor(){
		//todo blake, fix this
        assertTrue(quest.getItemRequirement().equals(requiredItems));
        assertTrue(quest.getKillRequirement().equals(globalKillRequirement));
        assertTrue(quest.getRewards().equals(rewards));
	    
	}
}