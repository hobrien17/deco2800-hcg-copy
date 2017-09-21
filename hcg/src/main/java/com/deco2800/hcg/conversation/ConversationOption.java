package com.deco2800.hcg.conversation;

import java.util.ArrayList;
import java.util.List;

public class ConversationOption {

    private ConversationNode parent;
    private Conversation grandparent;
    private String optionText;
    private ConversationNode target;    // null if this option will end the Conversation
    private AbstractConversationCondition condition;    // null if the option is always displayed
    private List<AbstractConversationAction> actions;

    public ConversationOption(ConversationNode parent, String optionText,
                ConversationNode target, AbstractConversationCondition condition,
                List<AbstractConversationAction> actions) {
        this.parent = parent;
        this.grandparent = parent.getParent();
        this.optionText = optionText;
        this.target = target;
        this.condition = condition;
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

    public boolean testCondition() {
        if (condition == null) {
            return true;
        } else {
            return condition.testCondition();
        }
    }

    // Needed for serialisation
	ConversationNode getTarget() {
		return target;
	}

    // Needed for serialisation
    AbstractConversationCondition getCondition() {
        return condition;
    }

    // Needed for serialisation
	ArrayList<AbstractConversationAction> getActions() {
		return new ArrayList<>(actions);
	}
}
