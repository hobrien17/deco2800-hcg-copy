package com.deco2800.hcg.quests;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.TestItem;
import com.deco2800.hcg.items.TestUniqueItem;

public class QuestTest {
	
	private Quest questToTest;
	
	
	@Before
	public void initialiseConstructor(){
		String instruction = "Go find me the item at the end of the map";
		Item itemRequested = new TestItem();
		Item itemRewarded = new TestUniqueItem("x", 5);
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
		assert(questToTest.itemNeeded().sameItem(new TestItem()) == true);
		assert(questToTest.itemRewarded().sameItem(new TestUniqueItem("x", 5)) == true);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testEmptyStringConstructor(){
		String instruction = "";
		Item itemRequested = new TestItem();
		Item itemRewarded = new TestUniqueItem("x", 5);
		Quest quest = new Quest(instruction, itemRequested, itemRewarded);
		questToTest = quest;
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testNullItemRequestedConstructor(){
		String instruction = "Go find me the item at the end of the map";
		Item itemRequested = null;
		Item itemRewarded = new TestUniqueItem("x", 5);
		Quest quest = new Quest(instruction, itemRequested, itemRewarded);
		questToTest = quest;
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testNullItemRewardedConstructor(){
		String instruction = "Go find me the item at the end of the map";
		Item itemRequested = new TestItem();
		Item itemRewarded = null;
		Quest quest = new Quest(instruction, itemRequested, itemRewarded);
		questToTest = quest;
	}
	
}
