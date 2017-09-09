package com.deco2800.hcg.multiplayer;

/**
 * Used to determine how to handle a given message
 * 
 * @author Max Crofts
 * @author Duc (Ethan) Phan
 *
 */
public enum MessageType {
	CONFIRMATION,	// sent after receiving message
	JOINING,			// sent from client when joining
	JOINED,			// sent from server after client has joined
	INPUT,			// sent after input
	CHAT				// sent when a peer enters a chat message
}
