package com.deco2800.hcg.multiplayer;

import java.net.SocketAddress;
import java.nio.ByteBuffer;

import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.WorldManager;

/**
 * This class represents a message to be sent when the host chooses a world map.
 * 
 * @author Max Crofts
 */
public class WorldMapMessage extends Message {
	private final GameManager gameManager = GameManager.get();
	private final WorldManager worldManager = (WorldManager) gameManager.getManager(WorldManager.class);
	
	private int worldMapIndex;
	
	public WorldMapMessage() {
		// Default constructor
	}
	
	public WorldMapMessage(int worldMapIndex) {
		super(MessageType.WORLD_MAP);
		this.worldMapIndex = worldMapIndex;
	}
	
	@Override
	public void packData(ByteBuffer buffer) {
		super.packData(buffer);
		buffer.putInt(worldMapIndex);
	}
	
	@Override
	public void unpackData(ByteBuffer buffer) throws MessageFormatException {
		super.unpackData(buffer);
		worldMapIndex = buffer.getInt();
	}
	
	@Override
	public void process(SocketAddress address) {
		worldManager.setWorldMap(worldMapIndex);
	}
}
