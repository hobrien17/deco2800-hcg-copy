package com.deco2800.hcg.managers;

import java.util.ArrayList;

import com.deco2800.hcg.observers.MessageObserver;

public class MessageManager extends Manager {
	
	private ArrayList<MessageObserver> chatMessageListeners = new ArrayList<>();

	/**
	 * Constructor for the MessageManager
	 */
	public MessageManager() {}
	
	/**
	 * Adds a chat Message listener
	 * @param observer The MessageObserver to be added
	 */
	public void addChatMessageListener(MessageObserver observer) {
		chatMessageListeners.add(observer);
	}

	/**
	 * Removes the specified listener from the list of chat Message listeners
	 * @param observer The MessageObserver to be removed
	 */
	public void removeChatMessageListener(MessageObserver observer) {
		chatMessageListeners.remove(observer);
	}
	
	/**
	 * Called when a chat message is received
	 * @param message The Message that was received
	 */
	public void chatMessageReceieved(String message) {
		for (MessageObserver observer : chatMessageListeners) {
			observer.notifyChatMessage(message);
		}
	}

}
