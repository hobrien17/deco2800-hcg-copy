package com.deco2800.hcg.conversation;

import com.deco2800.hcg.entities.npc_entities.QuestNPC;

public class CheckRelationshipCondition extends AbstractConversationCondition {

    boolean negate;
    String relationshipState;

    public CheckRelationshipCondition(boolean negate, String relationshipState) {
        this.negate = negate;
        this.relationshipState = relationshipState;
    }

    @Override
    public boolean testCondition(QuestNPC talkingTo) {
        if (!negate) {
            return relationshipState.equals(talkingTo.getRelationship());
        } else {
            return !relationshipState.equals(talkingTo.getRelationship());
        }
    }

    @Override
    public String toString() {
        if (!negate) {
            return "checkRelationship|" + relationshipState;
        } else {
            return "!checkRelationship|" + relationshipState;
        }
    }

}
