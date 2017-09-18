package com.deco2800.hcg.managers;
import com.deco2800.hcg.entities.npc_entities.NPC;
import com.deco2800.hcg.quests.Quest;
import com.deco2800.hcg.quests.QuestLog;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Object for storing quests
 *
 * @author Guthers
 */
public class QuestManager {
    HashMap<Integer,ArrayList<QuestArchive>> questManager;

    /**
     * Constructs a new Quest NPC
     *
     */
    public QuestManager() {
        this.questManager = new HashMap<>();
    }

    /**
     * Add a quest to the quest log. This function will reject duplicate quests (ie, same name and description).
     *
     * @param quest Quest to be added to the quest log
     * @return Returns true if the quest is successfully added.
     */
    public boolean addQuest(int playerIdentifier, Quest quest, NPC questGiver) {
        if(questExists(quest)) {
            //Duplicate Quest found
            return false;
        }
        //Check if the log is already created for the player
        addArchive(playerIdentifier, new QuestArchive(quest,questGiver));
        return true;
    }

    public void updateQuests(int playerIdentifier) {
        if (this.questManager.containsKey(playerIdentifier)) {
            for (QuestArchive qa: this.questManager.get(playerIdentifier)) {
                qa.update();
            }
        }
    }

    public ArrayList<QuestArchive> getLog(int playerIdentifier) {
        if (this.questManager.containsKey(playerIdentifier)) {
            return this.questManager.get(playerIdentifier);
        }
        return null;
    }

    public boolean questExists(Quest q) {
        for (ArrayList ql :questManager.values()) {
            //Check if the quest exists
            if (ql.contains(q)) {
                return true;
            }
        }
        return false;
    }

    private void addArchive(int playerID, QuestArchive qa) {
        //Create the empty log if not added
        this.questManager.putIfAbsent(playerID,new ArrayList<>());
        //Add the QA to the array list
        ArrayList<QuestArchive> ql = this.questManager.get(playerID);
        ql.add(qa);
    }

    //#TODO Update Quests functionlity - relating to items required/other quests requirements.


    /**
     * Used for storing the information per quest which is managed in the manager
     */
    private class QuestArchive {
        Quest quest;
        NPC questGiver;
        Boolean completed = false;
        Boolean handedIn = false;

        public QuestArchive(Quest quest,NPC questGiver) {
            this.quest = quest;
            this.questGiver = questGiver;
        }

        public void update() {

        }






    }
}
