package com.deco2800.hcg.multiplayer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Message {
	private int id;
	private MessageType type;
	private byte[] payload;
	
	public Message(MessageType type, byte[] payload) {
		// need a better way to generate IDs
		this.id = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
		this.type = type;
		this.payload = payload;
	}
	
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
	
	public int getId() {
		return id;
	}
	
	public byte[] getIdInBytes() {
		return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(id).array();
	}
	
	public MessageType getType() {
		return type;
	}
	
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
	
	public byte[] getPayloadByteArray() {
		return payload;
	}
	
	public int getPayloadInt() {
		return ((payload[3] << 24) + (payload[2] << 16) + (payload[1] << 8) + (payload[0] << 0));
	}
	
	public String getPayloadString() {
		return new String(payload);
	}
}
