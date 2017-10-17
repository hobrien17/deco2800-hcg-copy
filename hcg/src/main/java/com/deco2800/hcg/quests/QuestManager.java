package com.deco2800.hcg.quests;

import com.deco2800.hcg.conversation.GiveItemsAction;
import com.deco2800.hcg.managers.ResourceLoadException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Quest manager handles quest interaction in the game
 *
 * @author Harry Guthrie
 */
public class QuestManager {
    HashMap<String,Quest> quests;

    ArrayList<QuestArchive> questLog;

    /**
     *  Creates a new quest manager
     */
    public QuestManager() {
        quests = new HashMap<>();
        questLog = new ArrayList<>();
    }

    /**
     * Returns the loaded Quest associated with the Quest Name
     *
     * @param questName - name of the quest in the .json file
     * @return the Quest object if it exists if not null
     */
    public Quest getQuest(String questName) {
        return quests.getOrDefault(questName, null);
    }

    public void completeQuest(String questName) {
        Quest q = quests.getOrDefault(questName, null);
        if (q == null) {
            //Do nothing if the quest dosen't exist
            return;
        }





    }














    /**
     *  ----------------------------- Functions for the initial load of quests -----------------------------
     */

    /**
     * Replaces currently loaded quests (if they exist) and replaces them with a new set of quests
     */
    public void loadAllQuests() {
        HashMap<String,Quest> tmpQ = new HashMap<>();
        QuestReader qr = new QuestReader();
        quests = qr.loadAllQuests();
    }

    /**
     * Loads a single quest in as specified from the fp (filepath argument)
     *
     * @param fp - the full path of the json file of the quest
     */
    public void loadQuest(String fp) {
        QuestReader qr = new QuestReader();
        Quest tmpQ = qr.loadQuest(fp);
        if (quests.containsKey(tmpQ.getTitle())) {
            throw new ResourceLoadException("Quest (" + tmpQ.getTitle() + ") already added from fp (" + fp + ")");
        }
    }








}
