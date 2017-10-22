package com.deco2800.hcg.multiplayer;

import java.net.SocketAddress;

import com.deco2800.hcg.contexts.LobbyContext;
import com.deco2800.hcg.managers.ContextManager;
import com.deco2800.hcg.managers.GameManager;

/**
 * This class represents a message sent when a player has joined the game.
 * 
 * @author Max Crofts
 */
public class JoinedMessage extends Message {
	private final ContextManager contextManager =
			(ContextManager) GameManager.get().getManager(ContextManager.class);
	
	public JoinedMessage() {
		super(MessageType.JOINED);
	}
	
	public JoinedMessage(SocketAddress address) {
		super(address);
	}
	
	@Override
	public void process() {
		// TODO we need to communicate how many other players are already in the
		//      game as well as their state
		contextManager.pushContext(new LobbyContext());
	}
}
