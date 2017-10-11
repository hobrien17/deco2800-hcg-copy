package com.deco2800.hcg.conversation;

import com.deco2800.hcg.contexts.ConversationContext;
import com.deco2800.hcg.entities.npc_entities.NPC;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic data structure to hold conversations that will be utilised by the NPC class and the UI
 * @author Blake Bodycote
 * @author Richy McGregor
 */
public class Conversation {

	private List<ConversationNode> conversationNodes;  // this must not contain duplicates
	private ConversationNode initialNode;
	private ConversationNode currentNode;
	private ConversationContext conversationContext;
	private ContextManager contextManager;
	private NPC talkingTo;

	/**
	 * No-Argument constructor
	 * If this constructor is used, setup must be called before using
	 * any other method.
	 */ 
	public Conversation() {
		// Get necessary managers
		GameManager gameManager = GameManager.get();
		contextManager = (ContextManager) gameManager.getManager(ContextManager.class);
	}

	/**
	 * Full constructor
	 * initialNode MUST be in conversationNodes
	 * @param conversationNodes list of all nodes in this conversation
	 * @param initialNode reference to the first node to display
	 */
	public Conversation(List<ConversationNode> conversationNodes,
			ConversationNode initialNode) {
		this();
		setup(conversationNodes, initialNode);
	}

	/**
	 * Initialise references to conversationNodes
	 * This should be called once after using the no-argument constructor
	 * @param conversationNodes list of all nodes in this conversation
	 * @param initialNode reference to the first node to display
	 */
	public void setup(List<ConversationNode> conversationNodes,
			ConversationNode initialNode) {
		this.conversationNodes = new ArrayList<>(conversationNodes);
		this.initialNode = initialNode;
	}

	/**
	 * Begin presenting the conversation to the player
	 * @param talkingTo reference to the NPC the [player is talking to
	 */
	public void initiateConversation(NPC talkingTo) {
		currentNode = initialNode;
		this.talkingTo = talkingTo;
		conversationContext = new ConversationContext(this, talkingTo.getFaceImage());
		conversationContext.displayNode(currentNode);
		contextManager.pushContext(conversationContext);
	}

	// Called by ConversationNodes
	void changeNode(ConversationNode target) {
		if (target != null) {
			currentNode = target;
			conversationContext.displayNode(currentNode);
		} else {
			endConversation();
		}
	}

	// Called by ConversationNodes
	void endConversation() {
		contextManager.popContext();
		conversationContext = null;
		currentNode = null;
		talkingTo = null;
	}

	// Called by ConversationOptions
	NPC getTalkingTo() {
		return talkingTo;
	}
	
	// Needed for serialisation
	ConversationNode getInitialNode() {
		return initialNode;
	}
	
	// Needed for serialisation
	List<ConversationNode> getConversationNodes() {
		return new ArrayList<>(conversationNodes);
	}

}
