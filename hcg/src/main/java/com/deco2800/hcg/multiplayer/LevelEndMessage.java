package com.deco2800.hcg.multiplayer;

import java.net.SocketAddress;

import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.WorldManager;

/**
 * This class represents a message to be sent when a player ends the current level.
 * 
 * @author Max Crofts
 */
public class LevelEndMessage extends Message {
	private final WorldManager worldManager =
			(WorldManager) GameManager.get().getManager(WorldManager.class);
	
	public LevelEndMessage() {
		// Default constructor
	}
	
	public LevelEndMessage(int seed) {
		super(MessageType.LEVEL_END);
	}
	
	@Override
	public void process(SocketAddress address) {
		worldManager.completeLevel();
	}
}
