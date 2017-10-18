package com.deco2800.hcg.conversation;

import com.deco2800.hcg.entities.npc_entities.QuestNPC;

public class FinishQuestAction extends AbstractConversationAction {

    public FinishQuestAction() {}

    @Override
    void executeAction(QuestNPC talkingTo) {
        talkingTo.finishQuest();
    }

    @Override
    public String toString() {
        return "finishCurrentQuest";
    }

}
