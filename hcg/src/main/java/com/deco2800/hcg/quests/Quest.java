package com.deco2800.hcg.quests;


import com.deco2800.hcg.entities.enemyentities.EnemyType;
import java.util.Map;

/**
 * A base class for Quests that will be used by players/NPCs
 *
 * @author Blake Bodycote and Harry Guthrie
 *
 */

public class Quest {
	private String title; //Name of the quest to be displayed
	private Map<String,Integer> rewards; // items to amount for reward
	private Map<EnemyType, Integer> killRequirement; //Kills for enemy ID required
	private Map<String, Integer> itemRequirement; //Item required to complete quest
	private String description;

	/**
	 * Creates a new Quest with the given parameteres
	 * @param title
	 * @param rewards
	 * @param killRequirement
	 * @param itemRequirement
	 */
	public Quest(String title, Map<String, Integer> rewards, Map<EnemyType,
                Integer> killRequirement, Map<String, Integer> itemRequirement,
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

	public Map<String, Integer> getRewards() {
		return rewards;
	}

	public Map<EnemyType, Integer> getKillRequirement() {
		return killRequirement;
	}

	public Map<String, Integer> getItemRequirement() {
		return itemRequirement;
	}


	/**
	 * Using the requirements create the description of the quest
	 *
	 * @return a description for the quest created from the requirements
	 */
	public String getDescription() {
		if (!this.description.equals("")) {
			return this.description;
		}
		String desc = "";
		boolean bothReq = false;

		if (killRequirement.size() > 0) {
			bothReq = true;
			desc += "Kill ";

			for (Map.Entry<EnemyType,Integer> entry: killRequirement.entrySet()) {
				//For each enemy ID get the amount of kills required
				desc += entry.getValue().toString() + " " + entry.getKey().toString().toLowerCase() + " and ";
			}
			//Remove trailing and
			desc = desc.substring(0,desc.length() - "and ".length()); //Remove the trailing ','
			desc += ".";
		}

		if (itemRequirement.size() > 0) {
			if(bothReq) {
				desc += "\n Additionally collect";
			} else {
				desc = "Collect";
			}
			for (Map.Entry<String,Integer> i: itemRequirement.entrySet()) {
				desc += " " + i.getValue().toString().toLowerCase() + " " + i.getKey() + ",";
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
