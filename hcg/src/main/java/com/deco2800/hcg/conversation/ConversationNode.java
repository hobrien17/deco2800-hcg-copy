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

    public Conversation getParent() {
        return parent;
    }

    public String getNodeText() {
        return nodeText;
    }

    public List<ConversationOption> getOptions() {
        return options;
    }

    public List<ConversationOption> getValidOptions() {
        List<ConversationOption> validOptions = new ArrayList<>();
        for (ConversationOption option : options) {
            if (option.testCondition()) {
                validOptions.add(option);
            }
        }
        return options;
    }
}
