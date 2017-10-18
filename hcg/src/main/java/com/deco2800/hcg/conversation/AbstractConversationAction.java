package com.deco2800.hcg.conversation;

import com.deco2800.hcg.entities.npc_entities.QuestNPC;

public abstract class AbstractConversationAction {

    abstract void executeAction(QuestNPC talkingTo);

    @Override
    public abstract String toString();

}
