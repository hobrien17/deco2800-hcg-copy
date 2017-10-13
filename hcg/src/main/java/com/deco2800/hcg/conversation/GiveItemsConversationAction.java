package com.deco2800.hcg.conversation;

import com.deco2800.hcg.entities.npc_entities.QuestNPC;

public class GiveItemsConversationAction extends AbstractConversationAction {

    private String itemName;
    private int itemQuantity;

    public GiveItemsConversationAction(String itemName, int itemQuantity) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }

    @Override
    void executeAction(QuestNPC talkingTo) {
        throw new UnsupportedOperationException();  //TODO
    }

    @Override
    public String toString() {
        return "GiveItems" + '|' + itemName + '|' + itemQuantity;
    }

}



