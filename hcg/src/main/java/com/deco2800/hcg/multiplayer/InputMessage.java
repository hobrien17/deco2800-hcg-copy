package com.deco2800.hcg.multiplayer;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import com.deco2800.hcg.managers.GameManager;
import com.deco2800.hcg.managers.NetworkManager;
import com.deco2800.hcg.managers.PlayerInputManager;

/**
 * This class represents a message to be sent when a player has performed an input.
 * 
 * @author Max Crofts
 */
public class InputMessage extends Message {
	private static final NetworkManager networkManager =
			(NetworkManager) GameManager.get().getManager(NetworkManager.class);
	private static final PlayerInputManager playerInputManager =
			(PlayerInputManager) GameManager.get().getManager(PlayerInputManager.class);
	
	private long tick;
	private int[] args;
	
	public InputMessage() {}
	
	public InputMessage(long tick, int[] args) {
		super(MessageType.INPUT);
		this.tick = tick;
		this.args = args;
	}
	
	@Override
	public void packData(ByteBuffer buffer) {
		super.packData(buffer);
		buffer.putLong(tick);
		buffer.put((byte) args.length);
		buffer.asIntBuffer().put(args);
		buffer.position(buffer.position() + args.length * 4);
	}
	
	@Override
	public void unpackData(ByteBuffer buffer) throws MessageFormatException {
		super.unpackData(buffer);
		
		try {
			tick = buffer.getLong();
			byte length = buffer.get();
			args = new int[length];
			for (int i = 0; i < length; i++) {
				args[i] = buffer.getInt();
			}
		} catch (ArrayIndexOutOfBoundsException|BufferUnderflowException|BufferOverflowException e) {
			throw new MessageFormatException();
		}
	}
	
	@Override
	public void process() {
		try {
			InputType inputType = InputType.values()[args[0]];
			// TODO: handle input for more than one player
			switch (inputType) {
			case KEY_DOWN:
				playerInputManager.queueAction(
						tick,
						1,
						inputType.ordinal(),
						args[1]);
				break;
			case KEY_UP:
				playerInputManager.queueAction(
						tick,
						1,
						inputType.ordinal(),
						args[1]);
				break;
			case MOUSE_MOVED:
				playerInputManager.queueAction(
						tick, 
						1,
						inputType.ordinal(),
						args[1],
						args[2]);
				break;
			case TOUCH_DOWN:
				playerInputManager.queueAction(
						tick,
						1,
						inputType.ordinal(),
						args[1],
						args[2],
						args[3],
						args[4]);
				break;
			case TOUCH_DRAGGED:
				playerInputManager.queueAction(
						tick,
						1,
						inputType.ordinal(),
						args[1],
						args[2],
						args[3]);
				break;
			case TOUCH_UP:
				playerInputManager.queueAction(
						tick,
						1,
						inputType.ordinal(),
						args[1],
						args[2],
						args[3],
						args[4]);
				break;
			default:
				break;
			}
			networkManager.updatePeerTickCount(0, tick);
		} catch (ArrayIndexOutOfBoundsException e) {}
	}
}