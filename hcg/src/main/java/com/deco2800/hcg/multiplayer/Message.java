package com.deco2800.hcg.multiplayer;

import java.net.SocketAddress;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * This class represents a basic message to be sent by NetworkManager.
 * 
 * @author Max Crofts
 */
public class Message {
	private static final byte[] HEADER = "H4RDC0R3".getBytes();
	
	private static int sequenceNumber = 0;
	
	private int id;
	private MessageType type;
	private byte entries = 0; // FIXME Currently unused
	
	/**
	 * Constructs an empty message
	 */
	public Message() {
		//deliberately empty
	}
	
	/**
	 * Constructs a new message with the specified type
	 * @param type Message type
	 */
	Message(MessageType type) {
		this.id = sequenceNumber++;
		this.type = type;
	}
	
	/**
	 * Packs data into a buffer
	 * @param buffer ByteBuffer to pack
	 */
	public void packData(ByteBuffer buffer) {
		// put header
		buffer.put(HEADER);
		// put id
		buffer.putInt(id);
		// put type
		buffer.put((byte) type.ordinal());
		// put number of entries
		buffer.put((byte) entries);
	}
	
	/**
	 * Unpacks message data from specified buffer
	 * @param buffer ByteBuffer to unpack
	 * @throws MessageFormatException
	 */
	public void unpackData(ByteBuffer buffer) throws MessageFormatException {
		try {
			// get header
			byte[] header = new byte[8];
			buffer.get(header);
			if (!Arrays.equals(header, HEADER)) {
				throw new MessageFormatException();
			}
			// get id
			id = buffer.getInt();
			// get type
			type = MessageType.values()[buffer.get()];
			// get number of entries
			entries = buffer.get();
		} catch (BufferUnderflowException|BufferOverflowException e) {
			throw new MessageFormatException(e);
		}
	}
	
	/**
	 * Processes the received information
	 * @param address The address from which the message was received
	 * @require <code>unpackData</code> must have been called first
	 */
	public void process(SocketAddress address) {
		// Do nothing
	}
	
	/**
	 * Gets message ID
	 * @return Message ID
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Gets message type
	 * @return Message type
	 */
	public MessageType getType() {
		return type;
	}
	
	/**
	 * Gets whether message should be acknowledged
	 * @return <code>true</code> if message should be acknowledged
	 */
	public boolean shouldAck() {
		return true;
	}
	
	/**
	 * Gets message ID from specified ByteBuffer
	 * @param buffer ByteBuffer to read from
	 * @return Message ID
	 */
	public static int getId(ByteBuffer buffer) {
		buffer.mark();
		buffer.position(buffer.position() + 8);
		int id = buffer.getInt();
		buffer.reset();
		return id;
	}
	
	/**
	 * Gets message type from specified ByteBuffer
	 * @param buffer ByteBuffer to read from
	 * @return Message type
	 */
	public static MessageType getType(ByteBuffer buffer) {
		buffer.mark();
		buffer.position(buffer.position() + 12);
		MessageType type = MessageType.values()[buffer.get()];
		buffer.reset();
		return type;
	}
}
