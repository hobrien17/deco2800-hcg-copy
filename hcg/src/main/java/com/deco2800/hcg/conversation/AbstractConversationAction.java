package com.deco2800.hcg.conversation;

import com.deco2800.hcg.entities.npc_entities.NPC;

public abstract class AbstractConversationAction {

    abstract void executeAction(NPC talkingTo);

    @Override
    public abstract String toString();

}
