package com.deco2800.hcg.observers;

@FunctionalInterface
public interface MessageObserver {

	/**
     * Runs when a chat message is received
     *
     * @param string The chat message that was received
     */
    void notifyChatMessage(String string);
    
}
