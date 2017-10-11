package com.deco2800.hcg.conversation;

import com.deco2800.hcg.entities.npc_entities.NPC;

public abstract class AbstractConversationCondition {

    abstract boolean testCondition(NPC talkingTo);

    @Override
    public abstract String toString();

}
