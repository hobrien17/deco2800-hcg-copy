package com.deco2800.hcg.quests;

import com.deco2800.hcg.managers.ResourceLoadException;

import java.util.HashMap;


public class QuestManager {
    HashMap<String,Quest> quests;

    /**
     *  Creates a new quest manager
     */
    public QuestManager() {
        quests = new HashMap<>();
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
