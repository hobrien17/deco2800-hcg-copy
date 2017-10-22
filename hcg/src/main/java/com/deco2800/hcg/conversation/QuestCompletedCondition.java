package com.deco2800.hcg.conversation;

import com.deco2800.hcg.entities.npc_entities.QuestNPC;

public class QuestCompletedCondition extends AbstractConversationCondition {

    boolean negate;
    String questName;

    public QuestCompletedCondition(boolean negate, String questName) {
        this.negate = negate;
        this.questName = questName;
    }

    @Override
    public boolean testCondition(QuestNPC talkingTo) {
        if (!negate) {
            return talkingTo.isQuestCompleted(questName);
        } else {
            return !talkingTo.isQuestCompleted(questName);
        }
    }

    @Override
    public String toString() {
        if (!negate) {
            return "questCompleted|" + questName;
        } else {
            return "!questCompleted|" + questName;
        }
    }

}
