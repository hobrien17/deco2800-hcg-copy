package com.deco2800.hcg.multiplayer;

/**
 * Used to determine how to handle a given message
 * 
 * @author Max Crofts
 * @author Duc (Ethan) Phan
 *
 */
public enum MessageType {
	ACK,	            // sent after receiving message
	DISCOVERY,      // sent to discover hosts
	HOST,           // sent after receiving a discover message
	JOINING,	        // sent from peer when joining host
	JOINED,         // sent from host when a peer has joined
	START,          // sent from host when starting game
	CHARACTER,
	WORLD_MAP,
	LEVEL_START,
	LEVEL_END,
	INPUT,          // sent after input
	CHAT	            // sent when a peer enters a chat message
}
