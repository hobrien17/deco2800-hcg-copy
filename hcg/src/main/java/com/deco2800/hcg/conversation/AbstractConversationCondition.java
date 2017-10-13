package com.deco2800.hcg.conversation;

import com.deco2800.hcg.entities.npc_entities.QuestNPC;

public abstract class AbstractConversationCondition {

    abstract boolean testCondition(QuestNPC talkingTo);

    @Override
    public abstract String toString();

}
