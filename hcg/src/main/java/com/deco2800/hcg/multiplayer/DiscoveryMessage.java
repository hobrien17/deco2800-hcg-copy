package com.deco2800.hcg.multiplayer;

import java.io.IOException;
import java.net.SocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.NetworkManager;


/**
 * This class represents a message sent when trying to discover a game.
 * 
 * @author Max Crofts
 */
public class DiscoveryMessage extends Message {
	private final NetworkManager networkManager =
			(NetworkManager) GameManager.get().getManager(NetworkManager.class);
	private static final Logger LOGGER = LoggerFactory.getLogger(DiscoveryMessage.class);

	public DiscoveryMessage() {
		// Default constructor
		
	}
	
	public DiscoveryMessage(int id) {
		super(MessageType.DISCOVERY);
	}
	
	@Override
	public void process(SocketAddress address) {
		if (networkManager.isHost()) {
			try {
				// send host message
				networkManager.sendOnce(new HostMessage(networkManager.getLobbyName()), address);
			} catch (IOException e) {LOGGER.info(String.valueOf(e));}
		}
	}
	
	@Override
	public boolean shouldAck() {
		return false;
	}
}
