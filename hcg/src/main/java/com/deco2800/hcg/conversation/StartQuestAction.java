package com.deco2800.hcg.conversation;

import com.deco2800.hcg.entities.npc_entities.QuestNPC;

public class StartQuestAction extends AbstractConversationAction {

    private String questName;

    public StartQuestAction(String questName) {
        this.questName = questName;
    }

    @Override
    void executeAction(QuestNPC talkingTo) {
        talkingTo.startQuest(questName);
    }

    @Override
    public String toString() {
        return "startQuest" + '|' + questName;
    }

}
