package com.deco2800.hcg.observers;

import com.deco2800.hcg.multiplayer.Message;

public interface MessageObserver {

	/**
     * Runs when a chat message is received
     *
     * @param message The chat message that was received
     */
    void notifyChatMessage(Message message);
    
}
