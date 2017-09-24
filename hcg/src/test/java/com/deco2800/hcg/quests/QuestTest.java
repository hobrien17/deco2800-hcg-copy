package com.deco2800.hcg.quests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.deco2800.hcg.items.Item;
import com.deco2800.hcg.items.stackable.TestItem;
import com.deco2800.hcg.items.single.TestUniqueItem;

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
		assertFalse(questToTest.isQuestComplete());
	}
	
	@Test
	public void testGetInstruction(){
		assert(questToTest.getInstruction().equals("Go find me the item at the end of the map"));
	}
	
	@Test
	public void testGetterMethods(){
		//Removed the equals true from below as the result is already boolean, so this is redundant as true is true
		// or false is false
		assertTrue(questToTest.itemNeeded().sameItem(new TestItem()));
		assertTrue(questToTest.itemRewarded().sameItem(new TestUniqueItem("x", 5)));
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
