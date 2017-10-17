package com.deco2800.hcg.quests;

import com.deco2800.hcg.managers.ResourceLoadException;

import java.util.HashMap;


public class QuestManager {
    HashMap<String,Quest> quests;

    public QuestManager() {
        quests = new HashMap<>();
    }

    public void loadAllQuests() {
        HashMap<String,Quest> tmpQ = new HashMap<>();
        QuestReader qr = new QuestReader();
        quests = qr.loadAllQuests();
    }

    public void loadQuest(String fp) {
        QuestReader qr = new QuestReader();
        Quest tmpQ = qr.loadQuest(fp);
        if (quests.containsKey(tmpQ.getTitle())) {
            throw new ResourceLoadException("Quest (" + tmpQ.getTitle() + ") already added from fp (" + fp + ")");
        }
    }








}
