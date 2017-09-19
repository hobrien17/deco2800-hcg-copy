package com.deco2800.hcg.observers;

public interface MessageObserver {

	/**
     * Runs when a chat message is received
     *
     * @param message The chat message that was received
     */
    void notifyChatMessage(String string);
    
}
