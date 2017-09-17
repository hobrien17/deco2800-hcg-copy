package com.deco2800.hcg.multiplayer;

import java.io.ByteArrayInputStream;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a datagram packet sent via the networking system
 * 
 * @author Max Crofts
 * @author Duc (Ethan) Phan
 *
 */
public class Message {
	static final Logger LOGGER = LoggerFactory.getLogger(Message.class);
	
	private static final byte[] HEADER = "H4RDC0R3".getBytes();
	
	private long id; // effectively size of an integer
	private MessageType type;
	private byte[] payload;
	
	/**
	 * Constructor used for sending messages containing an array of integers
	 * @param type Type of message
	 * @param args Content of message
	 */
	public Message(MessageType type, int... args) {
		// call other constructor to generate id
		this(type, new byte[0]);
		this.payload = new byte[args.length * 4];
		// iterate through arguments and add them to payload
		for (int i = 0; i < args.length; i++) {
			byte[] argBytes = getIntInBytes(args[i]);
			this.payload[i * 4 + 0] = argBytes[0];
			this.payload[i * 4 + 1] = argBytes[1];
			this.payload[i * 4 + 2] = argBytes[2];
			this.payload[i * 4 + 3] = argBytes[3];
		}
	}
	
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
	 * @throws MessageFormatException
	 */
	public Message(byte[] received) throws MessageFormatException {
		try {
			DataInputStream stream = new DataInputStream(new ByteArrayInputStream(received));
			// message begins with header
			byte[] header = new byte[8];
			stream.read(header, 0, 8);
			if (!Arrays.equals(header, HEADER)) {
				throw new MessageFormatException();
			}
			// next four bytes are id
			this.id = stream.readInt();
			// next byte indicates type
			byte typeByte = stream.readByte();
			if (typeByte >= 0 && typeByte <= MessageType.values().length) {
				this.type = MessageType.values()[typeByte];
			} else {
				throw new MessageFormatException();
			}
			// rest is payload
			this.payload = new byte[stream.available()];
			int bytesRead = stream.read(this.payload);
			if (bytesRead < 1 || bytesRead > 1024) {
				throw new MessageFormatException();
			}
		} catch (IOException e) {
			LOGGER.error("Failed to convert byte[] to Message", e);
		}
	}
	
	/**
	 * Converts given int to byte array
	 * @return int as byte[4]
	 */
	private byte[] getIntInBytes(int integer) {
		byte[] intBytes = new byte[4];
		intBytes[0] = (byte) ((integer >> 24) & 0xFF);
		intBytes[1] = (byte) ((integer >> 16) & 0xFF);
		intBytes[2] = (byte) ((integer >> 8) & 0xFF);
		intBytes[3] = (byte) (integer & 0xFF);
		return intBytes;
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
		return getIntInBytes((int) id);
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
			stream.write(HEADER);
			stream.write(idBytes);
			stream.write(typeByte);
			stream.write(payload);
		} catch (IOException e) {
			LOGGER.error("Failed to convert Message to byte[]", e);
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
	 * @param index The integer to get
	 * @return Integer contained in nth four payload bytes
	 */
	public int getPayloadInt(int index) {
		final ByteBuffer byteBuffer = ByteBuffer.wrap(payload);
		return byteBuffer.getInt(index * 4);
	}
	
	/**
	 * Gets the content of Message as an Integer
	 * @return Integer contained in first four payload bytes
	 */
	public Integer getPayloadInteger() {
		return new Integer((int) getPayloadInt(0));
	}
	
	/**
	 * Gets the content of Message as a String
	 * @return String contained in payload
	 */
	public String getPayloadString() {
		return new String(payload);
	}
}
