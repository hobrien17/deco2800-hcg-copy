package com.deco2800.hcg.multiplayer;

import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.NetworkManager;

/**
 * This class represents a message sent when trying to join a game.
 * 
 * @author Max Crofts
 */
public class JoiningMessage extends Message {
	private final NetworkManager networkManager =
			(NetworkManager) GameManager.get().getManager(NetworkManager.class);
	
	public JoiningMessage() {
		//deliberately empty
	}
	
	public JoiningMessage(int id) {
		super(MessageType.JOINING);
	}
	
	@Override
	public void process() {
		// send joined message
		networkManager.queueMessage(new JoinedMessage(networkManager.getNextRandomInt()));
	}
}
