package com.deco2800.hcg.conversation;

import com.deco2800.hcg.entities.npc_entities.QuestNPC;

public class QuestActiveCondition extends AbstractConversationCondition {

    boolean negate;
    String questName;

    public QuestActiveCondition(boolean negate, String questName) {
        this.negate = negate;
        this.questName = questName;
    }

    @Override
    public boolean testCondition(QuestNPC talkingTo) {
        if (!negate) {
            return talkingTo.isQuestActive(questName);
        } else {
            return !talkingTo.isQuestActive(questName);
        }
    }

    @Override
    public String toString() {
        if (!negate) {
            return "questActive|" + questName;
        } else {
            return "!questActive|" + questName;
        }
    }

}
