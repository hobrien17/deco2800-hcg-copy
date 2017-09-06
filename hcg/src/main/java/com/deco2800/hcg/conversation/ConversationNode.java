package com.deco2800.hcg.conversation;

import java.util.ArrayList;
import java.util.List;

public class ConversationNode {

    private Conversation parent;
    private String nodeText;
    private List<ConversationOption> options;

    public ConversationNode(Conversation parent, String nodeText, List<ConversationOption> options) {
        this.parent = parent;
        this.nodeText = nodeText;
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

}
