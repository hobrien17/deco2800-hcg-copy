package com.deco2800.hcg.conversation;

import com.deco2800.hcg.contexts.ConversationContext;
import com.deco2800.hcg.entities.npc_entities.QuestNPC;
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
	private Map<String, ConversationNode> relationshipMap;
	private List<ConversationNode> conversationNodes;  // this must not contain duplicates
	private ConversationNode currentNode;
	private ConversationContext conversationContext;
	private ContextManager contextManager;
	private QuestNPC talkingTo;

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
	 * @param relationshipMap mapping from relationship levels to starting nodes
	 * @param conversationNodes list of all nodes in this conversation
	 */
	public Conversation(String initialRelationship, Map<String, ConversationNode> relationshipMap,
						List<ConversationNode> conversationNodes) {
		this();
		setup(initialRelationship, relationshipMap, conversationNodes);
	}

	/**
	 * Initialise references to conversationNodes
	 * This should be called once after using the no-argument constructor
	 * @param initialRelationship default relationship level
	 * @param relationshipMap mapping from relationship levels to starting nodes
	 * @param conversationNodes list of all nodes in this conversation
	 */
	public void setup(String initialRelationship, Map<String, ConversationNode> relationshipMap,
					  List<ConversationNode> conversationNodes) {
		this.initialRelationship = initialRelationship;
		this.relationshipMap = relationshipMap;
		this.conversationNodes = new ArrayList<>(conversationNodes);
	}

	/**
	 * Begin presenting the conversation to the player
	 * @param talkingTo reference to the NPC the player is talking to
	 */
	public void initiateConversation(QuestNPC talkingTo) {
		currentNode = relationshipMap.get(talkingTo.getRelationship()); //TODO get NPC relationship state
		this.talkingTo = talkingTo;
		conversationContext = new ConversationContext(this,talkingTo.getFirstName(), talkingTo.getFaceImage());
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
	QuestNPC getTalkingTo() {
		return talkingTo;
	}

	/**
	 * Fetch the relationship state (most) NPCs using this conversation will start with
	 * @return Relationship string
	 */
	public String getInitialRelationship() {
		return initialRelationship;
	}

	// Needed for serialisation
	Map<String, ConversationNode> getRelationshipMap() {
		return relationshipMap;
	}
	
	// Needed for serialisation
	List<ConversationNode> getConversationNodes() {
		return new ArrayList<>(conversationNodes);
	}

}
