package com.deco2800.hcg.quests;

import org.junit.Before;
import org.junit.Test;

import com.deco2800.hcg.items.BasicNonstackableItem;
import com.deco2800.hcg.items.Item;

public class QuestTest {
	private Quest questToTest;
	
	@Before
	public void initialiseConstructor(){
		String instruction = "Go find me the item at the end of the map";
		Item itemRequested = new BasicNonstackableItem("ItemRequested", 5);
		Item itemRewarded = new BasicNonstackableItem("ItemRewarded", 5);
		Quest quest = new Quest(instruction, itemRequested, itemRewarded);
		questToTest = quest;
	}
	
	@Test
	public void testNotComplete(){
		assert(questToTest.isQuestComplete() == false);
	}
	
	@Test
	public void testGetInstruction(){
		assert(questToTest.getInstruction().equals("Go find me the item at the end of the map"));
	}
	
	@Test
	public void testGetterMethods(){
	}
}
