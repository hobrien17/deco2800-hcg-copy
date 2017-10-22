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

	/**
	 * Creates a new Quest with the given parameteres
	 * @param instruction the instructions of the quest
	 * @param itemRequested the item required to complete the quest
	 * @param itemToReward the item being rewarded by the quest
	 * @throws IllegalArgumentException
	 */
	public Quest(String instruction, Item itemRequested, Item itemToReward) {
		if("".equals(instruction) || itemRequested == null || itemToReward == null){
			throw new IllegalArgumentException(); 
		}
		this.instruction = instruction;
		this.itemRequested = itemRequested;
		this.itemToReward = itemToReward;
	}
	/**
	 * Used by NPC to establish whether or not a quest has been completed by the player.
	 * 
	 * @return true if player has completed quest, false otherwise.
	 */
	public boolean isQuestComplete(){
		return completed;
	}
	
	/**
	 * Returns the item that is needed in order to fulfill the Quest
	 * 
	 * @return item needed to fulfill quest
	 */
	public Item itemNeeded(){
		return itemRequested;
	}
	
	/**
	 * Returns the rewarded item, the rewarded item will then be placed in the player's inventory.
	 * 
	 * @return the rewarded item
	 */
	public Item itemRewarded(){
		return itemToReward;
	}
	
	/**
	 * Returns the instruction of the quest, utilised by the UI in order to display a quest .
	 * 
	 * @return the instruction for the quest
	 */
	public String getInstruction(){
		return instruction;
	}
	
	
	
	
}
