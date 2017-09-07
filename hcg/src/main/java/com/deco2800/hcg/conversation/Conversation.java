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

	private List<ConversationNode> conversationNodes;  // this must not contain duplicates
	private ConversationNode currentNode;
	private ConversationContext conversationContext;
	private ContextManager contextManager;

	/**
	 * No-Argument constructor
	 * If this constructor is used, setup must be called before using
	 * any other method.
	 */ 
	public Conversation() {
		// Get necessary managers
		GameManager gameManager = GameManager.get();
		contextManager = (ContextManager)
				gameManager.getManager(ContextManager.class);
	}

	/**
	 * Full constructor
	 * currentNode MUST be in conversationNodes
	 * @param conversationNodes list of all nodes in this conversation
	 * @param currentNode reference to the first node to display
	 */
	public Conversation(List<ConversationNode> conversationNodes,
			ConversationNode initalNode) {
		this();
		setup(conversationNodes, initalNode);
	}

	/**
	 * Initialise references to conversationNodes
	 * This should be called once after using the no-argument constructor
	 * @param conversationNodes list of all nodes in this conversation
	 * @param currentNode reference to the first node to display
	 */
	public void setup(List<ConversationNode> conversationNodes,
			ConversationNode initalNode) {
		this.conversationNodes = new ArrayList<>(conversationNodes);
		currentNode = initalNode;
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
	
	// Needed for serialisation
	List<ConversationNode> getConversationNodes() {
		return new ArrayList<>(conversationNodes);
	}

}
