package com.deco2800.hcg.multiplayer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Represents a datagram packet sent via the networking system
 * 
 * @author Max Crofts
 *
 */
public class Message {
	private long id; // effectively size of an integer
	private MessageType type;
	private byte[] payload;
	
	/**
	 * Constructor used for sending messages
	 * @param type Type of message
	 * @param payload Content of message
	 */
	public Message(MessageType type, byte[] payload) {
		// need a better way to generate IDs
		this.id = System.currentTimeMillis() % Integer.MAX_VALUE;
		this.type = type;
		this.payload = payload;
	}
	
	/**
	 * Constructor used for receiving messages
	 * @param received Bytes received from server
	 */
	public Message(byte[] received) {
		try {
			DataInputStream stream = new DataInputStream(new ByteArrayInputStream(received));
			// first four bytes are id
			this.id = stream.readInt();
			// next byte indicates type
			this.type = MessageType.values()[stream.readByte()];
			// rest is payload
			this.payload = new byte[stream.available()];
			stream.read(this.payload);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets message ID of instance
	 * @return ID of message as a long
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * Gets message ID of instance
	 * @return ID of message as byte[4]
	 */
	public byte[] getIdInBytes() {
		byte[] idBytes = new byte[4];
		idBytes[0] = (byte) ((id >> 24) & 0xFF);
		idBytes[1] = (byte) ((id >> 16) & 0xFF);
		idBytes[2] = (byte) ((id >> 8) & 0xFF);
		idBytes[3] = (byte) (id & 0xFF);
		return idBytes;
	}
	
	/**
	 * Gets message type of instance
	 * @return Type of message
	 */
	public MessageType getType() {
		return type;
	}
	
	/**
	 * Converts class representation to byte array
	 * @return Byte array of sufficient length to hold message instance
	 */
	public byte[] toByteArray() {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			byte[] idBytes = getIdInBytes();
			byte typeByte = (byte) type.ordinal();
			stream.write(idBytes);
			stream.write(typeByte);
			stream.write(payload);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stream.toByteArray();
	}
	
	/**
	 * Gets the content of Message as a byte array
	 * @return Byte array of payload data
	 */
	public byte[] getPayloadByteArray() {
		return payload;
	}
	
	/**
	 * Gets the content of Message as an Integer
	 * @return Integer contained in first four payload bytes
	 */
	public Integer getPayloadInteger() {
		long payloadLong = 0;
		for (int i = 0; i < 4; i++) {
			// prevent sign extension, then shift byte
			payloadLong |= (((long) payload[i]) & 0x00000000000000FFL) << ((3 - i) * 8);
		}
		return new Integer((int) payloadLong);
	}
	
	/**
	 * Gets the content of Message as a String
	 * @return String contained in payload
	 */
	public String getPayloadString() {
		return new String(payload);
	}
}
