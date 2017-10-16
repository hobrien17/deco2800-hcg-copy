package com.deco2800.hcg.conversation;

import com.deco2800.hcg.entities.npc_entities.QuestNPC;

public class SetRelationshipAction extends AbstractConversationAction {

    String relationshipState;

    public SetRelationshipAction(String relationshipState) { //TODO (hard!) validate relationshipState
        this.relationshipState = relationshipState;
    }

    @Override
    void executeAction(QuestNPC talkingTo) {
        talkingTo.setRelationship(relationshipState);
    }

    @Override
    public String toString() {
        return "setRelationship" + '|' + relationshipState;
    }

}



