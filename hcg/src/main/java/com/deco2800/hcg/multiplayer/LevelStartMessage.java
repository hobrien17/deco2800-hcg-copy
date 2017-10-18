package com.deco2800.hcg.multiplayer;

import java.net.SocketAddress;
import java.nio.ByteBuffer;

import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.WorldManager;

/**
 * This class represents a message to be sent when the host chooses a level.
 * 
 * @author Max Crofts
 */
public class LevelStartMessage extends Message {
	private final GameManager gameManager = GameManager.get();
	private final WorldManager worldManager = (WorldManager) gameManager.getManager(WorldManager.class);
	
	private int nodeIndex;
	
	public LevelStartMessage() {
		// Default constructor
	}
	
	public LevelStartMessage(int nodeIndex) {
		super(MessageType.LEVEL_START);
		this.nodeIndex = nodeIndex;
	}
	
	@Override
	public void packData(ByteBuffer buffer) {
		super.packData(buffer);
		buffer.putInt(nodeIndex);
	}
	
	@Override
	public void unpackData(ByteBuffer buffer) throws MessageFormatException {
		super.unpackData(buffer);
		nodeIndex = buffer.getInt();
	}
	
	@Override
	public void process(SocketAddress address) {
		worldManager.selectNode(nodeIndex);
	}
}
