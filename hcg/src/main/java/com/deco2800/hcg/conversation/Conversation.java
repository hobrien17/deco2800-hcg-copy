package com.deco2800.hcg.conversation;

import com.deco2800.hcg.contexts.ConversationContext;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic data structure to hold conversations that will be utilised by the NPC class and the UI
 * @author Blake Bodycote
 */
public class Conversation {

	private List<ConversationNode> nodes;
	private ConversationNode currentNode;
	private ConversationContext conversationContext;
	private ContextManager contextManager;

	public Conversation() { //TODO write a proper constructor
		// Get necessary managers
		GameManager gameManager = GameManager.get();
		contextManager = (ContextManager)
				gameManager.getManager(ContextManager.class);

		nodes = new ArrayList<>();
		currentNode = null;
	}

	public void initiateConversation() {
		conversationContext = new ConversationContext();
		conversationContext.displayNode(currentNode);
		contextManager.pushContext(conversationContext);
	}

	void changeNode(ConversationNode target) {
		currentNode = target;
		conversationContext.displayNode(currentNode);
	}

	void endConversation() {
		contextManager.popContext();
	}

}
