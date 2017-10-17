package com.deco2800.hcg.conversation;

import java.util.ArrayList;
import java.util.List;

public class ConversationNode {

    private Conversation parent;
    private String nodeText;
    private List<ConversationOption> options;

    /**
     * Option-less constructor for use in deserialisation.
     * You must call setup before any other method if you use this constructor.
     * @param parent The conversation which owns this node
     * @param nodeText Text to display for this node
     */
    public ConversationNode(Conversation parent, String nodeText) {
        this.parent = parent;
        this.nodeText = nodeText;
        this.options = new ArrayList<>();
    }

    /**
     * Full constructor.
     * @param parent The conversation which owns this node
     * @param nodeText Text to display for this node
     * @param options The available dialog options
     */
    public ConversationNode(Conversation parent, String nodeText, List<ConversationOption> options) {
        this(parent, nodeText);
        setup(options);
    }

    /**
     * Initialise references to ConversationOptions
     * @param options The available dialog options
     */
    public void setup(List<ConversationOption> options) {
        this.options = new ArrayList<>(options);
    }

    /**
     * Get the Conversation this node is a part of
     * @return A reference to the parent Conversation
     */
    public Conversation getParent() {
        return parent;
    }

    /**
     * Get this node's dialog text
     * @return A string of dialog
     */
    public String getNodeText() {
        return nodeText;
    }

    /**
     * Return all dialog options associated with this node unconditionally
     * @return List of Conversation Options
     */
    public List<ConversationOption> getAllOptions() {
        return options;
    }

    /**
     * Get all associated dialog options whose condition is currently True
     * @return List of Conversation Options
     */
    public List<ConversationOption> getValidOptions() {
        List<ConversationOption> validOptions = new ArrayList<>();
        for (ConversationOption option : options) {
            if (option.testConditions()) {
                validOptions.add(option);
            }
        }
        return validOptions;
    }
}
