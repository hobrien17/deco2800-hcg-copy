package com.deco2800.hcg.multiplayer;

import java.net.SocketAddress;
import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.NetworkManager;
import com.deco2800.hcg.managers.PlayerInputManager;
import com.deco2800.hcg.managers.PlayerInputManager.Input;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class represents a message to be sent when a player has performed an input.
 * 
 * @author Max Crofts
 */
public class InputMessage extends Message {

	private static final Logger LOGGER = LoggerFactory.getLogger(InputMessage.class);
	private static final NetworkManager networkManager =
			(NetworkManager) GameManager.get().getManager(NetworkManager.class);
	private static final PlayerInputManager playerInputManager =
			(PlayerInputManager) GameManager.get().getManager(PlayerInputManager.class);
	
	private long tick;
	private InputType type;
	private int[] ints;
	private float[] floats;
	
	public InputMessage(long tick, Input input) {
		super(MessageType.INPUT);
		this.tick = tick;
		this.type = input.getType();
		this.ints = input.getInts();
		this.floats = input.getFloats();
	}
	
	public InputMessage(SocketAddress address) {
		super(address);
	}
	
	@Override
	public void packData(ByteBuffer buffer) {
		super.packData(buffer);
		buffer.putLong(tick);
		buffer.put((byte) type.ordinal());
		buffer.put((byte) ints.length);
		buffer.put((byte) floats.length);
		for (int i = 0; i < ints.length; i++) {
			buffer.putShort((short) ints[i]);
		}
		for (int i = 0; i < floats.length; i++) {
			buffer.putFloat(floats[i]);
		}
	}
	
	@Override
	public void unpackData(ByteBuffer buffer) throws MessageFormatException {
		super.unpackData(buffer);
		try {
			tick = buffer.getLong();
			type = InputType.values()[buffer.get()];
			ints = new int[buffer.get()];
			floats = new float[buffer.get()];
			for (int i = 0; i < ints.length; i++) {
				ints[i] = (int) buffer.getShort();
			}
			for (int i = 0; i < floats.length; i++) {
				floats[i] = buffer.getFloat();
			}
		} catch (ArrayIndexOutOfBoundsException|BufferUnderflowException|BufferOverflowException e) {
			throw new MessageFormatException(e);
		}
	}
	
	@Override
	public void process() {
		try {
			// TODO: handle input for more than one player
			Input input = playerInputManager.new Input(type, 1, ints, floats);
			playerInputManager.queueInput(tick, input);
			
			// MOUSE_MOVED messages will always be the last input sent for a given tick
			if (type == InputType.MOUSE_MOVED) {
				networkManager.updatePeerTickCount(0, tick);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			LOGGER.error(String.valueOf(e));
		}
	}
}
