package com.deco2800.hcg.conversation;

import java.util.ArrayList;
import java.util.List;

public class ConversationOption {

    private ConversationNode parent;
    private Conversation grandparent;
    private List<AbstractConversationCondition> conditions;    // empty if the option is always displayed
    private String optionText;
    private List<AbstractConversationAction> actions;
    private ConversationNode target;    // null if this option will end the Conversation

    /**
     * Constructor for ConversationOptions
     * @param parent ConversationNode which contains this Option
     * @param optionText Text to display on this Option
     * @param target The node that clicking this ption will take the player to
     * @param conditions A list of condition objects which decide whether this Option is visible, or null
     * @param actions A list of actions to take when this option is selected
     */
    public ConversationOption(ConversationNode parent, List<AbstractConversationCondition> conditions,
                              String optionText, List<AbstractConversationAction> actions, ConversationNode target) {
        this.parent = parent;
        this.grandparent = parent.getParent();
        this.conditions = conditions;
        this.optionText = optionText;
        this.actions = new ArrayList<>(actions);
        this.target = target;
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
     * Call .testCondition() on this Option's conditions and return the combined result
     * @return whether this node should be visible
     */
    public boolean testConditions() {
        boolean result = true;
        for (AbstractConversationCondition condition : conditions) {
            if (!condition.testCondition(grandparent.getTalkingTo())) {
                return false;
            }
        }
        return true;
    }

    // Needed for serialisation
	ConversationNode getTarget() {
		return target;
	}

    // Needed for serialisation
    List<AbstractConversationCondition> getConditions() {
        return new ArrayList<>(conditions);
    }

    // Needed for serialisation
	ArrayList<AbstractConversationAction> getActions() {
		return new ArrayList<>(actions);
	}
}
