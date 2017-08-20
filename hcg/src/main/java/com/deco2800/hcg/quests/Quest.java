package com.deco2800.hcg.quests;

import com.deco2800.hcg.items.Item;

/**
 * A base class for Quests that will be used by players/NPCs 
 * 
 * @author Blake Bodycote
 *
 */
public class Quest {
	private String instruction; //what the player must do to complete the quest
	private Item itemToReward; //what the player will receive for doing the quest
	private Item itemRequested; //what the player needs to have in order to complete the quest
	private boolean completed; //whether the quest is completed or not 
	
	public Quest(String instruction, Item itemRequested, Item itemToReward){
		this.instruction = instruction;
		this.itemRequested = itemRequested;
		this.itemToReward = itemToReward;
	}
	
	public boolean isQuestComplete(){
		return completed;
	}
	
	public Item itemNeeded(){
		return itemRequested;
	}
	
	public Item itemRewarded(){
		return itemToReward;
	}
	
	
}
