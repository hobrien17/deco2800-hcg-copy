package com.deco2800.hcg.conversation;

import com.deco2800.hcg.entities.npc_entities.NPC;

public class GiveItemsConversationAction extends AbstractConversationAction {

    private String itemName;
    private int itemQuantity;

    public GiveItemsConversationAction(String itemName, int itemQuantity) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }

    @Override
    void executeAction(NPC talkingTo) {
        throw new UnsupportedOperationException();  //TODO
    }

    @Override
    public String toString() {
        return "GiveItems" + '|' + itemName + '|' + itemQuantity;
    }

}



