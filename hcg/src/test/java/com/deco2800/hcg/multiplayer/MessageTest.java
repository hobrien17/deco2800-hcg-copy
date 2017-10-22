package com.deco2800.hcg.multiplayer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.junit.Test;

public class MessageTest {
	
	ByteBuffer buffer = ByteBuffer.allocateDirect(1400).order(ByteOrder.LITTLE_ENDIAN);

	@Test
	public void testMessageFormats() throws MessageFormatException {
		testMessageFormat(new CharacterMessage(3));
		testMessageFormat(new ChatMessage("Hello world!"));
		testMessageFormat(new DiscoveryMessage());
		testMessageFormat(new HostMessage("Hello world!"));
		testMessageFormat(new JoinedMessage());
		testMessageFormat(new JoiningMessage());
		testMessageFormat(new LevelEndMessage());
		testMessageFormat(new LevelStartMessage(1));
		testMessageFormat(new StartMessage(1));
		testMessageFormat(new WorldMapMessage(1));
	}
	
	private void testMessageFormat(Message message) throws MessageFormatException {
		message.packData(buffer);
		buffer.flip();
		message.unpackData(buffer);
		buffer.clear();
	}
	
}
