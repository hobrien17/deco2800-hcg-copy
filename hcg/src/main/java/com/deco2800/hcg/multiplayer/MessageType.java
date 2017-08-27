package com.deco2800.hcg.multiplayer;

/**
 * Used to determine how to handle a given message
 * 
 * @author Max Crofts
 * @edit: Duc (Ethan) Phan
 *
 */
public enum MessageType {
	CONFIRMATION, // sent after receiving message
	JOIN,         // sent from client when joining
	COMMAND,      // not used (yet)
	CHAT          // sent when a peer enters a chat message
}
