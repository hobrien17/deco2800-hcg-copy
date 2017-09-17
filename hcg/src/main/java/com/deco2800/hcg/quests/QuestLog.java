package com.deco2800.hcg.quests;
import com.deco2800.hcg.entities.npc_entities.NPC;
import com.deco2800.hcg.entities.npc_entities.QuestNPC;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sun.xml.internal.bind.v2.TODO;

import java.util.HashMap;

/**
 * Object for storing quests
 *
 * @author RLonergan
 */
public class QuestLog {
    HashMap<String, Quest> questLog;
    HashMap<String, NPC> questGivers;
    HashMap<String, Boolean> questsComplete;

    /**
     * Constructs a new Quest NPC
     *
     */
    public QuestLog() {
        this.questLog = new HashMap<String, Quest> ();
        this.questGivers = new HashMap<String, NPC>();
    }

    /**
     * Add a quest to the quest log. This function will reject duplicate quests (ie, same name and description).
     *
     * @param quest Quest to be added to the quest log
     * @return Returns true if the quest is successfully added.
     */
    public boolean addQuest(Quest quest, NPC questGiver) {

        if(this.questLog.containsValue(quest)) {
            //Duplicate Quest found
            return false;
        }

        //Add quest to map.
        this.questLog.put(quest.getName(), quest);
        this.questGivers.put(quest.getName(), questGiver);

        return true;
    }

    //#TODO Update Quests functionlity - relating to items required/other quests requirements.
}
