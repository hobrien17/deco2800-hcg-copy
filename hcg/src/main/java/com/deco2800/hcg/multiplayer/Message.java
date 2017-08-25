package com.deco2800.hcg.multiplayer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Represents a datagram packet sent via the networking system
 * 
 * @author Max Crofts
 *
 */
public class Message {
	private int id;
	private MessageType type;
	private byte[] payload;
	
	/**
	 * Constructor used for sending messages
	 * @param type Type of message
	 * @param payload Content of message
	 */
	public Message(MessageType type, byte[] payload) {
		// need a better way to generate IDs
		this.id = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
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
	 * @return ID of message as int
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Gets message ID of instance
	 * @return ID of message as byte[4]
	 */
	public byte[] getIdInBytes() {
		return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(id).array();
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
	 * Gets the content of Message as an int
	 * @return Int contained in first four payload bytes
	 */
	public int getPayloadInt() {
		return ((payload[3] << 24) + (payload[2] << 16) + (payload[1] << 8) + (payload[0] << 0));
	}
	
	/**
	 * Gets the content of Message as a String
	 * @return String contained in payload
	 */
	public String getPayloadString() {
		return new String(payload);
	}
}
