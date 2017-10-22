package com.deco2800.hcg.conversation;

import com.deco2800.hcg.entities.npc_entities.QuestNPC;

public class QuestNotStartedCondition extends AbstractConversationCondition {

    boolean negate;
    String questName;

    public QuestNotStartedCondition(boolean negate, String questName) {
        this.negate = negate;
        this.questName = questName;
    }

    @Override
    public boolean testCondition(QuestNPC talkingTo) {
        if (!negate) {
            return talkingTo.isQuestNotStarted(questName);
        } else {
            return !talkingTo.isQuestNotStarted(questName);
        }
    }

    @Override
    public String toString() {
        if (!negate) {
            return "questNotStarted|" + questName;
        } else {
            return "!questNotStarted|" + questName;
        }
    }

}
