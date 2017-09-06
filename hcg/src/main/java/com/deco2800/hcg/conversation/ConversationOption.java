package com.deco2800.hcg.conversation;

import java.util.ArrayList;
import java.util.List;

public class ConversationOption {

    private ConversationNode parent;
    private Conversation grandparent;
    private String optionText;
    private ConversationNode target;    // null if this option will end the Conversation
    private List<AbstractConversationAction> actions;

    public ConversationOption(ConversationNode parent, String optionText, ConversationNode target,
                              List<AbstractConversationAction> actions) {
        this.parent = parent;
        this.grandparent = parent.getParent();
        this.optionText = optionText;
        this.target = target;
        this.actions = new ArrayList<>(actions);
    }

    public void activate() {

        // Execute all attached actions
        for (AbstractConversationAction action : actions) {
            action.executeAction();
        }

        // Move to the target Conversation Node
        if (target != null) {
            grandparent.changeNode(target);
        } else {
            grandparent.endConversation();
        }

    }

    public ConversationNode getParent() {
        return parent;
    }

    public String getOptionText() {
        return optionText;
    }

}
