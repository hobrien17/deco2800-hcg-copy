package com.deco2800.hcg.quests;

import com.deco2800.hcg.entities.enemyentities.Enemy;
import com.deco2800.hcg.entities.enemyentities.EnemyType;
import com.deco2800.hcg.items.Item;
import java.util.HashMap;

/**
 * A base class for Quests that will be used by players/NPCs
 *
 * @author Blake Bodycote and Harry Guthrie
 *
 */

public class Quest {
	private String title; //Name of the quest to be displayed
	private HashMap<String,Integer> rewards; // items to amount for reward
	private HashMap<Integer,HashMap<EnemyType, Integer>> killRequirement; //Kills for enemy ID required
	private HashMap<String, Integer> itemRequirement; //Item required to complete quest
	private String description;

	/**
	 * Creates a new Quest with the given parameteres
	 * @param title
	 * @param rewards
	 * @param killRequirement
	 * @param itemRequirement
	 */
	public Quest(String title, HashMap<String, Integer> rewards, HashMap<Integer,
			HashMap<EnemyType, Integer>> killRequirement, HashMap<String, Integer> itemRequirement,
		    String description) {

		this.title = title;
		this.rewards = rewards;
		this.killRequirement = killRequirement;
		this.itemRequirement = itemRequirement;
		this.description = description;
	}

	/**
	 * Returns the title of the quest, utilised by the UI in order to display a quest.
	 *
	 * @return the name of the quest
	 */
	public String getTitle() {
		return title;
	}

	public HashMap<String, Integer> getRewards() {
		return rewards;
	}

	public HashMap<Integer, HashMap<EnemyType, Integer>> getKillRequirement() {
		return killRequirement;
	}

	public HashMap<String, Integer> getItemRequirement() {
		return itemRequirement;
	}


	/**
	 * Using the requirements create the description of the quest
	 *
	 * @return a description for the quest created from the requirements
	 */
	public String getDescription() {
		if (this.description != "") {
			return this.description;
		}

		String desc = "";
		boolean bothReq = false;

		if (killRequirement.size() > 0) {
			bothReq = true;

			for (Integer node: killRequirement.keySet()) {
				//For each world
				desc += "Goto world " + node.toString();
				//Todo get a human friendly node name
				for (EnemyType enemyID: killRequirement.get(node).keySet()) {
					//For each enemy ID get the amount of kills required
					desc += " and kill " + killRequirement.get(node).get(enemyID).toString() + enemyID.toString() + ".";
					//Todo get a human friendly enemy name
				}
			}
		}

		if (itemRequirement.size() > 0) {
			if(bothReq) {
				desc += "\n\n Additionally collect";
			} else {
				desc = "Collect";
			}

			for (String i: itemRequirement.keySet()) {
				desc += " " + itemRequirement.get(i).toString() + i + ",";
			}
			desc = desc.substring(0,desc.length() - 1); //Remove the trailing ','
			desc += ".";
		}
		return desc;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
