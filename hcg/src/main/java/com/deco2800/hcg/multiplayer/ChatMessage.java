package com.deco2800.hcg.multiplayer;

import java.net.SocketAddress;
import java.nio.ByteBuffer;

import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.MessageManager;

/**
 * This class represents a message sent when a player has entered a chat message.
 * 
 * @author Max Crofts
 */
public class ChatMessage extends Message {
	private final MessageManager messageManager =
			(MessageManager) GameManager.get().getManager(MessageManager.class);
	
	private String string;
	
	public ChatMessage(String string) {
		super(MessageType.CHAT);
		this.string = string;
	}
	
	public ChatMessage(SocketAddress address) {
		super(address);
	}
	
	@Override
	public void packData(ByteBuffer buffer) {
		super.packData(buffer);
		buffer.put(string.getBytes());
	}
	
	@Override
	public void unpackData(ByteBuffer buffer) throws MessageFormatException {
		super.unpackData(buffer);
		byte[] bytes = new byte[buffer.remaining()];
		buffer.get(bytes);
		string = new String(bytes);
	}
	
	@Override
	public void process() {
		messageManager.chatMessageReceieved(string);
	}
}
