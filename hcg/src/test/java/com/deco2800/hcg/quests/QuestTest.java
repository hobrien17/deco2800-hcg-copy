package com.deco2800.hcg.quests;

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
		String name = "Test Quest";
		String instruction = "Go find me the item at the end of the map";
		Item itemRequested = new TestItem();
		Item itemRewarded = new TestUniqueItem("x", 5);
		Quest quest = new Quest(name, instruction, itemRequested, itemRewarded);
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
	public void testEmptyStringInstructionConstructor(){
		String name = "Test";
		String instruction = "";
		Item itemRequested = new TestItem();
		Item itemRewarded = new TestUniqueItem("x", 5);
		Quest quest = new Quest(name, instruction, itemRequested, itemRewarded);
		questToTest = quest;
	}

	@Test (expected = IllegalArgumentException.class)
	public void testEmptyStringNameConstructor(){
		String name = "";
		String instruction = "Test";
		Item itemRequested = new TestItem();
		Item itemRewarded = new TestUniqueItem("x", 5);
		Quest quest = new Quest(name, instruction, itemRequested, itemRewarded);
		questToTest = quest;
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testNullItemRequestedConstructor(){
		String name = "Test";
		String instruction = "Go find me the item at the end of the map";
		Item itemRequested = null;
		Item itemRewarded = new TestUniqueItem("x", 5);
		Quest quest = new Quest(name, instruction, itemRequested, itemRewarded);
		questToTest = quest;
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testNullItemRewardedConstructor(){
		String name = "Test";
		String instruction = "Go find me the item at the end of the map";
		Item itemRequested = new TestItem();
		Item itemRewarded = null;
		Quest quest = new Quest(name, instruction, itemRequested, itemRewarded);
		questToTest = quest;
	}
	
}
