package com.deco2800.hcg.multiplayer;

import java.net.SocketAddress;

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
		super(MessageType.JOINING);
	}
	
	public JoiningMessage(SocketAddress address) {
		super(address);
	}
	
	@Override
	public void process() {
		// add peer to lobby
		networkManager.addPeer(address);
		// send joined message
		networkManager.queueMessage(new JoinedMessage());
	}
}
