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

    /**
     * Constructor for ConversationOptions
     * @param parent ConversationNode which contains this Option
     * @param optionText Text to display on this Option
     * @param target The node that clicking this ption will take the player to
     * @param condition A condition object which decides whether this Option is visible, or null
     * @param actions A list of actions to take when this option is selected
     */
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

    /**
     * Called by a ConversationContext when the player clicks this Option
     */
    public void activate() {

        // Execute all attached actions
        for (AbstractConversationAction action : actions) {
            action.executeAction(grandparent.getTalkingTo());
        }

        // Move to the target Conversation Node
        if (target != null) {
            grandparent.changeNode(target);
        } else {
            grandparent.endConversation();
        }

    }

    /**
     * Get the ConversationNode which contains this Option
     * @return The parent ConversationNode
     */
    public ConversationNode getParent() {
        return parent;
    }

    /**
     * Get the dialog associated with this Option
     * @return String of dialog
     */
    public String getOptionText() {
        return optionText;
    }

    /**
     * Call .testCondition() on this Option's condition and return the result
     * @return whether this node should be visible
     */
    public boolean testCondition() {
        if (condition != null) {
            return condition.testCondition(grandparent.getTalkingTo());
        } else {
            return true;
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
