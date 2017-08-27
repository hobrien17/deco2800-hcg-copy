package com.deco2800.hcg.conversation;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic data structure to hold conversations that will be utilised by the NPC class and the UI
 * @author Blake Bodycote
 */
public class Conversation {
	private List<String> conversation; // The sequence of questions asked by NPC if player chooses yes
	private String greeting; // what the NPC says when the conversation is started
	private String goodbye; // what the NPC says when the conversation is ended
	private boolean active; // condition to check whether conversation is active
	private int iterator; // used to iterate over the conversation

	/**
	 * Constructs a new conversation with the specified sentences/phrases. 
	 * 
	 * @param greeting - the generic greeting when conversation starts
	 * @param goodbye - the generic goodbye when conversation stops
	 * @param conversation - the collection of questions/sentences that will be iterated over when user answers yes
	 */
	public Conversation(String greeting, String goodbye, List<String> conversation) {
		this.conversation = new ArrayList<String>();
		for (String string : conversation) {
			this.conversation.add(string);
		}
		this.greeting = greeting;
		this.goodbye = goodbye;
		active = false;
		iterator = -1;
		this.greeting = greeting;
	}

	/**
	 * Used to evaluate whether or not the UI has to display the current
	 * sentence
	 * 
	 * @return whether or not the conversation is active
	 */
	public boolean conversationActive() {
		return this.active;
	}

	/**
	 * Used to activate the conversation
	 */
	public void activateConversation() {
		this.active = true;
	}

	/**
	 * Used to activate the conversation
	 */
	public void deactivateConversation() {
		this.active = false;
	}

	/**
	 * Return the specific greeting associated with the conversation
	 * 
	 * @return the greeting stored
	 */
	public String greet() {
		return this.greeting;
	}

	/**
	 * Move the conversation along to the next sentence/phrase and return it
	 * 
	 * @param answer
	 *            the input given by the user on the UI. If user selects yes on
	 *            the UI, answer == true. answer == false if user selects no.
	 * @return the next sentence of the conversation
	 */
	public String nextSentence(boolean answer) {
		if (answer == false) {
			deactivateConversation();
			return goodbye;
		}
		if (iterator == conversation.size() - 1) {
			deactivateConversation();
			iterator = -1;
			return goodbye;
		}
		iterator++;
		return conversation.get(iterator);
	}

}
