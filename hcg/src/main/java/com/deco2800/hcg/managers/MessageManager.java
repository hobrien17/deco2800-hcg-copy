package com.deco2800.hcg.managers;

import java.util.ArrayList;

import com.deco2800.hcg.multiplayer.Message;
import com.deco2800.hcg.observers.MessageObserver;

public class MessageManager extends Manager {
	
	private ArrayList<MessageObserver> messageListeners = new ArrayList<>();

	public MessageManager() {}
	
	/**
	 * Adds a message listener
	 * @param observer The MessageObserver to be added
	 */
	public void addMessageListener(MessageObserver observer) {
		messageListeners.add(observer);
	}

	/**
	 * Removes the specified listener from the list of message listeners
	 * @param observer The MessageObserver to be removed
	 */
	public void removeMessageListener(MessageObserver observer) {
		messageListeners.remove(observer);
	}
	
	/**
	 * Called when a chat message is received
	 * @param message The Message that was received
	 */
	public void chatMessageReceieved(Message message) {
		for (MessageObserver observer : messageListeners) {
			observer.notifyChatMessage(message);
		}
	}

}
