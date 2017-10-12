package com.deco2800.hcg.conversation;

import com.deco2800.hcg.contexts.ConversationContext;
import com.deco2800.hcg.entities.npc_entities.NPC;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Basic data structure to hold conversations that will be utilised by the NPC class and the UI
 * @author Blake Bodycote
 * @author Richy McGregor
 */
public class Conversation {

	private String initialRelationship;
	private Map<String, ConversationNode> relationshipNodes;
	private List<ConversationNode> conversationNodes;  // this must not contain duplicates
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
	 * relationshipNodes.values() MUST be in conversationNodes
	 * @param initialRelationship default relationship level
	 * @param relationshipNodes mapping from relationship levels to starting nodes
	 * @param conversationNodes list of all nodes in this conversation
	 */
	public Conversation(String initialRelationship, Map<String, ConversationNode> relationshipNodes,
						List<ConversationNode> conversationNodes) {
		this();
		setup(initialRelationship, relationshipNodes, conversationNodes);
	}

	/**
	 * Initialise references to conversationNodes
	 * This should be called once after using the no-argument constructor
	 * @param initialRelationship default relationship level
	 * @param relationshipNodes mapping from relationship levels to starting nodes
	 * @param conversationNodes list of all nodes in this conversation
	 */
	public void setup(String initialRelationship, Map<String, ConversationNode> relationshipNodes,
					  List<ConversationNode> conversationNodes) {
		this.initialRelationship = initialRelationship;
		this.relationshipNodes = relationshipNodes;
		this.conversationNodes = new ArrayList<>(conversationNodes);
	}

	/**
	 * Begin presenting the conversation to the player
	 * @param talkingTo reference to the NPC the player is talking to
	 */
	public void initiateConversation(NPC talkingTo) {
		currentNode = relationshipNodes.get(initialRelationship); //TODO get NPC relationship state
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
	String getInitialRelationship() {
		return initialRelationship;
	}

	// Needed for serialisation
	Map<String, ConversationNode> getRelationshipNodes() {
		return relationshipNodes;
	}
	
	// Needed for serialisation
	List<ConversationNode> getConversationNodes() {
		return new ArrayList<>(conversationNodes);
	}

}
