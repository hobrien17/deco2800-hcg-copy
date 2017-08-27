package com.deco2800.hcg.multiplayer;

import java.io.ByteArrayInputStream;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deco2800.hcg.managers.GameManager;
import java.lang.*;
/**
 * Represents a datagram packet sent via the networking system
 * 
 * @author Max Crofts
 * Edit: Duc (Ethan) Phan
 *
 */
public class Message {
	static final Logger LOGGER = LoggerFactory.getLogger(GameManager.class);
	
	private static final byte[] HEADER = "H4RDC0R3".getBytes();
	
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
			String idTemp = "";
			for (int i = 0; i < 4; i++){
				idTemp += (char)(stream.readByte());
			}
			this.id = Long.valueOf(idTemp);
		
			// next byte indicates type
			String tmp = "";
			for (int i=0; i < 4; i++){
				tmp += (char)(stream.readByte());
			}
			for (MessageType e: MessageType.values()){
				if (e.toString().equals(tmp)){
					this.type = MessageType.valueOf(tmp);
				}
			}	
			if (this.type == null){
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
